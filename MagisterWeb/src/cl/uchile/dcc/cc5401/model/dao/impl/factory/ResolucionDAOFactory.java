package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.ResolucionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.ResolucionDAOImpl;

public class ResolucionDAOFactory {

	public static ResolucionDAO getResolucionDAO(){
		return new ResolucionDAOImpl();
	}
	
}
