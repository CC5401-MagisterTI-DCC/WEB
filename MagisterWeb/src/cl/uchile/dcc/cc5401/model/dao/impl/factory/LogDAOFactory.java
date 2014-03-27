package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.LogDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.LogDAOImpl;

public class LogDAOFactory {

	public LogDAO getLogDAO(){
		return new LogDAOImpl();
	}
}
