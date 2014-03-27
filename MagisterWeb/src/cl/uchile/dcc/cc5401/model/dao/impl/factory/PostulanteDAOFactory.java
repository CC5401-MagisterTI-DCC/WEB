package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.PostulanteDAOImpl;

public class PostulanteDAOFactory {

	public static PostulanteDAO getPostulanteDAO(){
		return new PostulanteDAOImpl();
	}
}
