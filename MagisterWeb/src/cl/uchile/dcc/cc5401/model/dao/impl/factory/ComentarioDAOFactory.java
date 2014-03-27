package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.ComentarioDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.ComentarioDAOImpl;

public class ComentarioDAOFactory {

	public static ComentarioDAO getComentarioDAO(){
		return new ComentarioDAOImpl();
	}
	
}
