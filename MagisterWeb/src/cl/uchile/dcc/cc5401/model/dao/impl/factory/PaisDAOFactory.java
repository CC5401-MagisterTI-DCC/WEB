package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.PaisDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.PaisDAOImpl;

public class PaisDAOFactory {

	public static PaisDAO getPaisDAO(){
		return new PaisDAOImpl();
	}
	
}
