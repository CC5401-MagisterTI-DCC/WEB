package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.UserDAOImpl;

public class UserDAOFactory {

	public static UserDAO getUserDAO(){
		return new UserDAOImpl();
	}
	
}
