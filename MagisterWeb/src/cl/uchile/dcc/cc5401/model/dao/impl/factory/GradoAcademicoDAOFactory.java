package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.GradoAcademicoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.GradoAcademicoDAOImpl;

public class GradoAcademicoDAOFactory {

	public static GradoAcademicoDAO getGradoAcademicoDAO(){
		return new GradoAcademicoDAOImpl();
	}
	
}
