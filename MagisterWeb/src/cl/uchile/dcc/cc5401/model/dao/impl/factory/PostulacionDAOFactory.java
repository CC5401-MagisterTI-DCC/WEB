package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.PostulacionDAOImpl;

public class PostulacionDAOFactory {

	public static PostulacionDAO getPostulacionDAO(){
		return new PostulacionDAOImpl();
	}
	
}
