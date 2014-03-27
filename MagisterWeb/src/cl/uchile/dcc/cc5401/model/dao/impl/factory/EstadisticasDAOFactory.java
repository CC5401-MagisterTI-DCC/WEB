package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.EstadisticasDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.EstadisticasDAOImpl;

public class EstadisticasDAOFactory {

	public static EstadisticasDAO getEstadisticasDAO(){
		return new EstadisticasDAOImpl();
	}
}
