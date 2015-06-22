package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.CambioPasswordDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.CambioPasswordDAOImpl;

public class CambioPasswordDAOFactory {
	public static CambioPasswordDAO getCambioPasswordDAO(){
		return new CambioPasswordDAOImpl();
	}
}
