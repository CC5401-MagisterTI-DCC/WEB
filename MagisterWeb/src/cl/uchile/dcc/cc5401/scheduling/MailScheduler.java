package cl.uchile.dcc.cc5401.scheduling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dao.VotoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.UserDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.VotoDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.MailHelper;

public class MailScheduler implements Job {

	private PostulacionDAO postulacionDAO;
	private UserDAO userDAO;
	private VotoDAO votoDAO;
	private MailHelper mailHelper;
	private String body;
	private String subject;
	private String[] comision;
	private String[] jefePEC;
	private String[] coordinador;
	private String[] asistente;

	// Función auxiliar encargada de inicializar las variables.
	private void initDAOs(JobDataMap jdm) {
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		userDAO = UserDAOFactory.getUserDAO();
		votoDAO = VotoDAOFactory.getVotoDAO();

		mailHelper = new MailHelper(jdm.getString("usernameMail"),
				jdm.getString("passwordMail"), jdm.getString("hostMail"),
				jdm.getString("portMail"), true);

		subject = jdm.getString("SubjectMensaje");
		body = jdm.getString("BodyMensaje");

		jefePEC = jdm.getString("jefe_pec").split(",");
		comision = jdm.getString("comision").split(",");
		coordinador = jdm.getString("coordinador").split(",");
		asistente = jdm.getString("asistente").split(",");
	}

	// Función auxiliar encargada de enviar emails.
	private void sendMail(UserDTO usuario, String nPostulaciones) {
		String msjBody = body.replaceAll("@usuario", usuario.getUsername());
		msjBody = msjBody.replaceAll("@numero_postulacion", nPostulaciones);

		String email = usuario.getEmail();

		try {
			mailHelper.sendMail(email, subject, msjBody);
			System.out.println(new Date() + "[INFO] Enviando mail a " + email
					+ ": " + nPostulaciones + " pendientes.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("NO SE PUDO MANDAR MAIL A " + email);
		}
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
		Date moment = new Date();
		System.out.println("Se ejecuta tarea programada (CRON):" +sdf.format(moment.getTime()));
		
		this.initDAOs(context.getMergedJobDataMap());

		List<PostulacionDTO> postulaciones = postulacionDAO.getAll();
		// Inicializamos las listas de postulaciones
		List<PostulacionDTO> revision = new ArrayList<PostulacionDTO>();
		List<PostulacionDTO> validacion = new ArrayList<PostulacionDTO>();
		List<PostulacionDTO> consideracion = new ArrayList<PostulacionDTO>();
		List<PostulacionDTO> evaluacion = new ArrayList<PostulacionDTO>();
		List<PostulacionDTO> decision = new ArrayList<PostulacionDTO>();
		List<PostulacionDTO> esperaNotificacion = new ArrayList<PostulacionDTO>();

		// Rellenamos las listas con respecto a su estado
		for (PostulacionDTO p : postulaciones) {
			switch (p.getEstado()) {
			case REVISION:
				revision.add(p);
				break;
			case EN_VALIDACION:
				validacion.add(p);
				break;
			case EN_CONSIDERACION:
				consideracion.add(p);
				break;
			case EN_EVALUACION:
				evaluacion.add(p);
				break;
			case DECISION:
				decision.add(p);
				break;
			case EN_ESPERA:
				esperaNotificacion.add(p);
				break;
			case RESUELTA:
				break;
			default:
				break;
			}
		}

		int nCoordinador = 0;
		int nAsistente = 0;
		int nComision = 0;
		int nJefePEC = 0;

		// Revisamos si las listas contienen algo, si es así aumentamos el
		// contador de postulaciones pendientes para las entidades
		if (revision.size() > 0) {
			nAsistente += revision.size();
		}
		if (validacion.size() > 0) {
			nJefePEC += validacion.size();
		}
		if (consideracion.size() > 0) {
			nCoordinador += consideracion.size();
		}
		if (evaluacion.size() > 0) {
			nComision += evaluacion.size();
			nCoordinador += evaluacion.size();
		}
		if (decision.size() > 0) {
			nCoordinador += decision.size();
		}
		if (esperaNotificacion.size() > 0) {
			nAsistente += esperaNotificacion.size();
		}

		// Enviamos los emails
		if (nJefePEC > 0) {
			// Le enviamos un mail a cada jefe del PEC (en caso de existir mas
			// de uno)
			for (String u : jefePEC) {
				UserDTO usuario = userDAO.getUser(u);
				String nPostulaciones = String.valueOf(nJefePEC);
				this.sendMail(usuario, nPostulaciones);
			}
		}
		if (nCoordinador > 0) {
			// Le enviamos un mail a cada coordinador (en caso de existir mas de
			// uno)
			for (String u : coordinador) {
				UserDTO usuario = userDAO.getUser(u);
				int nPendientes = nCoordinador;
				// Caso en que ya haya votado
				for (PostulacionDTO p : evaluacion) {
					if (votoDAO.get(p.getId(), usuario.getId()) != null) {
						nPendientes--;
					}
				}
				String nPostulaciones = String.valueOf(nPendientes);
				if (nPendientes != 0)
					this.sendMail(usuario, nPostulaciones);
			}
		}
		if (nAsistente > 0) {
			// Le enviamos un mail a cada asistente
			for (String u : asistente) {
				UserDTO usuario = userDAO.getUser(u);
				String nPostulaciones = String.valueOf(nAsistente);
				this.sendMail(usuario, nPostulaciones);
			}
		}
		if (nComision > 0) {
			// Le enviamos un mail a cada miembro de la comisión (excepto al
			// coordinador que ya le enviamos)
			for (String u : comision) {
				UserDTO usuario = userDAO.getUser(u);
				int nPendientes = nComision;
				// Caso en que ya haya votado
				for (PostulacionDTO p : evaluacion) {
					if (votoDAO.get(p.getId(), usuario.getId()) != null) {
						nPendientes--;
					}
				}
				if (!u.equals(coordinador)) {
					String nPostulaciones = String.valueOf(nPendientes);
					if (nPendientes != 0)
						this.sendMail(usuario, nPostulaciones);
				}
			}
		}

	}
}
