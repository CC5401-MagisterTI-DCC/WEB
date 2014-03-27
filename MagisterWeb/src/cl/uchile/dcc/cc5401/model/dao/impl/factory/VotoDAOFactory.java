package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.VotoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.VotoDAOImpl;

public class VotoDAOFactory {

	public static VotoDAO getVotoDAO(){
		return new VotoDAOImpl();
	}
}
