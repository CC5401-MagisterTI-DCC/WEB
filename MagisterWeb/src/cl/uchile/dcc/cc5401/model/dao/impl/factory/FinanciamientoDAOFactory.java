package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.FinanciamientoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.FinanciamientoDAOImpl;

public class FinanciamientoDAOFactory {
	
	public static FinanciamientoDAO getFinanciamientoDAO(){
		return new FinanciamientoDAOImpl();
	}

}
