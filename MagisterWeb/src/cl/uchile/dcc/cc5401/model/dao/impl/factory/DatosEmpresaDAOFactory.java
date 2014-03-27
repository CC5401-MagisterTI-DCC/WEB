package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.DatosEmpresaDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.DatosEmpresaDAOImpl;

public class DatosEmpresaDAOFactory {

	public static DatosEmpresaDAO getDatosEmpresaDAO(){
		return new DatosEmpresaDAOImpl();
	}
}
