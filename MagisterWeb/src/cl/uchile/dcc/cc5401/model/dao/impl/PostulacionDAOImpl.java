package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;
import cl.uchile.dcc.cc5401.util.Estado;
import cl.uchile.dcc.cc5401.util.RolUsuario;

public class PostulacionDAOImpl implements PostulacionDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	public static String MD5 = "MD5";

	private static final String SQL_INSERT =
			"INSERT INTO postulacion ("
					+ "id_postulante, id_financiamiento, id_carta1, id_carta2, id_cv, id_carta_presentacion, id_estado, deadline, fecha_ingreso"
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_SELECT =
			"SELECT * "
					+ " FROM postulacion JOIN postulante ON postulacion.id_postulante = postulante.id"
					+ " WHERE postulacion.id = ?";
	
	private static final String SQL_SELECT_HASH =
			"SELECT * "
					+ " FROM postulacion JOIN postulante ON postulacion.id_postulante = postulante.id"
					+ " WHERE postulacion.track_hash = ?";
	
	private static final String SQL_SELECT_All =
			"SELECT *"
					+ " FROM postulacion JOIN postulante ON postulacion.id_postulante = postulante.id";
	private static final String SQL_UPDATE =
			"UPDATE postulacion SET "
					+ "id_postulante = ?, id_financiamiento = ?, id_carta1 = ?, id_carta2 = ?, id_cv = ?, id_carta_presentacion = ?, id_estado = ?, deadline = ?, fecha_ingreso = ?"
					+ " WHERE "
					+ "id = ? ";
	private static final String SQL_DELETE =
			"UPDATE postulacion SET id_estado = ? " +
			"WHERE id = ?";
	
	private static final String SQL_LAST_ID =
			"SELECT MAX(id) AS id FROM postulacion";
	
	private static final String SQL_HASH_INSERT =
			"UPDATE postulacion SET "
					+ "track_hash = MD5(id*10+id_postulante) "
					+ "WHERE id=?";
	
	private static final String SQL_SELECT_TRACK_EMAIL = 
			"SELECT * FROM postulacion p " +
			"JOIN postulante po ON p.id_postulante = po.id " +
			"WHERE p.track_hash = ? AND po.email = ? AND id_estado != ?";
	
	private static final String SQL_ACTIVE_RUT = "SELECT p.*, po.* FROM postulacion p " +
			"LEFT JOIN resolucion r ON p.id = r.id_postulacion " +
			"JOIN postulante po ON p.id_postulante = po.id " +
			"JOIN identificacion i ON po.id_identificacion = i.id " +
			"WHERE r.id IS NULL AND p.id_estado != ? " +
			"AND identificacion = ? AND pais IS NULL AND esrut = 1";
	
	private static final String SQL_ACTIVE_PASAPORTE = "SELECT p.*,po.* FROM postulacion p " +
			"LEFT JOIN resolucion r ON p.id = r.id_postulacion " +
			"JOIN postulante po ON p.id_postulante = po.id " +
			"JOIN identificacion i ON po.id_identificacion = i.id " +
			"WHERE r.id IS NULL AND p.id_estado != ? " +
			"AND identificacion = ? AND pais = ? AND esrut = 0";
	
	private static final String SQL_RESUELTA = "SELECT p.*, po.* FROM postulacion p " +
			"JOIN postulante po ON p.id_postulante = po.id " +
			"JOIN identificacion i ON po.id_identificacion = i.id " +
			"AND i.identificacion = ? ORDER BY fecha_ingreso DESC LIMIT 1";
	
	// Cambiar el LIKE.Me interesa lo que se pide actualmente para sacar la fecha
	//de rechazo/aceptacion, pero tb necesito los comentarios de votacion. Ojo que ahi va a salir directo quien hizo el comentario
	//la accion la puedo sacar tal cual y el comentario lo puedo poner en italica
	private static final String SQL_HISTORIAL_ID = "SELECT * FROM historial WHERE id_postulacion = ? AND " +
			"(accion LIKE '%Postulación Rechazada%' OR accion LIKE '%Postulación Aceptada%' OR accion LIKE '%: Votación:%') ORDER BY id DESC";
	
	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private List<PostulacionDTO> getResults(ResultSet rs) throws SQLException {
		List<PostulacionDTO> results = new ArrayList<PostulacionDTO>();
		while (rs.next()) {
			PostulacionDTO postulacionDTO = new PostulacionDTO();
			postulacionDTO.setId(rs.getInt("id"));
			postulacionDTO.setIdPostulante(rs.getInt("id_postulante"));
			postulacionDTO.setDeadline(rs.getDate("deadline"));
			postulacionDTO.setFechaIngreso(rs.getDate("fecha_ingreso"));
			postulacionDTO.setIdCarta1(rs.getInt("id_carta1"));
			postulacionDTO.setIdCarta2(rs.getInt("id_carta2"));
			postulacionDTO.setIdCV(rs.getInt("id_cv"));
			postulacionDTO.setIdCartaPresentacion(rs.getInt("id_carta_presentacion"));
			postulacionDTO.setEstado(Estado.getValue(rs.getInt("id_estado")));
			postulacionDTO.setIdFinanciamiento(rs.getInt("id_financiamiento"));
			String nombrePostulante = "";
			if(rs.getString("segundo_nombre") == null || rs.getString("segundo_nombre").equals("null"))
				nombrePostulante += rs.getString("primer_nombre");
			else
				nombrePostulante += rs.getString("primer_nombre")+" "+rs.getString("segundo_nombre");
			if(rs.getString("apellido_materno") == null || rs.getString("apellido_materno").equals("null"))
				nombrePostulante += " " + rs.getString("apellido_paterno");
			else
				nombrePostulante += " " + rs.getString("apellido_paterno") +  " "+ rs.getString("apellido_materno");
			postulacionDTO.setNombrePostulante(nombrePostulante);
			results.add(postulacionDTO);

		}
		return results;
	}
	
	private List<HistorialDTO> getResultsHistorial(ResultSet rs) throws SQLException {
		List<HistorialDTO> results = new ArrayList<HistorialDTO>();
		while (rs.next()) {
			HistorialDTO historialDTO= new HistorialDTO();
			historialDTO.setId(rs.getInt("id"));
			historialDTO.setIdPostulacion(rs.getInt("id_postulacion"));
			historialDTO.setAccion(rs.getString("accion"));
			historialDTO.setFecha(rs.getDate("fecha"));
			historialDTO.setComentario(rs.getString("comentario"));
			historialDTO.setRolAutor(RolUsuario.getValue(rs.getInt("id_usuario_rol")));
			
			results.add(historialDTO);

		}
		return results;
	}

	@Override
	public PostulacionDTO getPostulacion(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			List<PostulacionDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public PostulacionDTO getPostulacion(String track) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_HASH);
			ptmt.setString(1, track);
			resultSet = ptmt.executeQuery();
			List<PostulacionDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<PostulacionDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_All);
			resultSet = ptmt.executeQuery();
			List<PostulacionDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int agregar(PostulacionDTO postulacion) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, postulacion.getIdPostulante());
			ptmt.setInt(2, postulacion.getIdFinanciamiento());
			ptmt.setInt(3, postulacion.getIdCarta1());
			ptmt.setInt(4, postulacion.getIdCarta2());
			ptmt.setInt(5, postulacion.getIdCV());
			ptmt.setInt(6, postulacion.getIdCartaPresentacion());
			ptmt.setInt(7, postulacion.getEstado().getId());
			if(postulacion.getDeadline() == null)
				ptmt.setNull(8, java.sql.Types.DATE);
			else
				ptmt.setDate(8, new Date(postulacion.getDeadline().getTime()));
			ptmt.setDate(9, new Date (postulacion.getFechaIngreso().getTime()));
			ptmt.executeUpdate();
			
			ptmt = connection.prepareStatement(SQL_LAST_ID);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			id = resultSet.getInt("id");
			
			ptmt = connection.prepareStatement(SQL_HASH_INSERT);
			ptmt.setInt(1, id);
			ptmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return id;

	}

	@Override
	public void actualizar(PostulacionDTO postulacion) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setInt(1, postulacion.getIdPostulante());
			ptmt.setInt(2, postulacion.getIdFinanciamiento());
			ptmt.setInt(3, postulacion.getIdCarta1());
			ptmt.setInt(4, postulacion.getIdCarta2());
			ptmt.setInt(5, postulacion.getIdCV());
			ptmt.setInt(6, postulacion.getIdCartaPresentacion());
			ptmt.setInt(7, postulacion.getEstado().getId());
			if(postulacion.getDeadline()!=null)
				ptmt.setDate(8, new Date(postulacion.getDeadline().getTime()));
			else
				ptmt.setDate(8, null);
			ptmt.setDate(9, new Date (postulacion.getFechaIngreso().getTime()));
			ptmt.setInt(10, postulacion.getId());
			ptmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void eliminar(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_DELETE);
			ptmt.setInt(1, Estado.ELIMINADA.getId());
			ptmt.setInt(2, id);
			ptmt.executeUpdate();
			System.out.println("Data deleted Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public PostulacionDTO getPostulacionActiva(IdentificacionDTO identificacion){
		try {
			connection = getConnection();
			if(identificacion.isEsRut()){
				ptmt = connection.prepareStatement(SQL_ACTIVE_RUT);
				ptmt.setInt(1, Estado.ELIMINADA.getId());
				ptmt.setString(2, identificacion.getIdentificacion());
			}else{
				ptmt = connection.prepareStatement(SQL_ACTIVE_PASAPORTE);
				ptmt.setInt(1, Estado.ELIMINADA.getId());
				ptmt.setString(2, identificacion.getIdentificacion());
				ptmt.setInt(3, identificacion.getPais().getId());
			}
			resultSet = ptmt.executeQuery();
			List<PostulacionDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private List<PostulacionDTO> getPostulacionesResueltas(IdentificacionDTO identificacion){
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_RESUELTA);
			ptmt.setString(1, identificacion.getIdentificacion());
		
			resultSet = ptmt.executeQuery();
			List<PostulacionDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<HistorialDTO> getHistorialPostulacionesResueltas(IdentificacionDTO identificacion){
		List<PostulacionDTO> postulaciones = getPostulacionesResueltas(identificacion);
		if(postulaciones != null){
			try {
				List<HistorialDTO> historialPostulaciones = new ArrayList<HistorialDTO>();
				connection = getConnection();
				for(PostulacionDTO p : postulaciones){
					ptmt = connection.prepareStatement(SQL_HISTORIAL_ID);
					ptmt.setInt(1, p.getId());
					resultSet = ptmt.executeQuery();
					historialPostulaciones.addAll(getResultsHistorial(resultSet));
				}
				if (historialPostulaciones.size() > 0) {
					return historialPostulaciones;
				} else {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (resultSet != null)
						resultSet.close();
					if (ptmt != null)
						ptmt.close();
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		return null;
	}

	@Override
	public PostulacionDTO getPostulacion(String track, String email) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_TRACK_EMAIL);
			ptmt.setString(1, track);
			ptmt.setString(2, email);
			ptmt.setInt(3, Estado.ELIMINADA.getId());
			resultSet = ptmt.executeQuery();
			List<PostulacionDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) 
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
