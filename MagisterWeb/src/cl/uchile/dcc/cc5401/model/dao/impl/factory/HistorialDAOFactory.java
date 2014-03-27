package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.HistorialDAOImpl;

public class HistorialDAOFactory {

	public static HistorialDAO getHistorialDAO(){
		return new HistorialDAOImpl();
	}
}
