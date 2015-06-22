package cl.uchile.dcc.cc5401.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cl.uchile.dcc.cc5401.model.dao.CambioPasswordDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.CambioPasswordDAOFactory;

public class CambioPasswordScheduler implements Job {
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
		Date moment = new Date();
		System.out.println("Se ejecuta tarea programada (CRON):" +sdf.format(moment.getTime()));
		
		CambioPasswordDAO cambioPasswordDAO = CambioPasswordDAOFactory.getCambioPasswordDAO();
		cambioPasswordDAO.limpiar();
	}
}
