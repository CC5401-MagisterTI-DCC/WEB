package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.IdentificacionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.IdentificacionDAOImpl;

public class IdentificacionDAOFactory {

	public static IdentificacionDAO getIdentificacionDAO(){
		return new IdentificacionDAOImpl();
		
	}
	
}
