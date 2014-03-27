package cl.uchile.dcc.cc5401.model.dao.impl.factory;

import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.DocumentoDAOImpl;

public class DocumentoDAOFactory {

	public static DocumentoDAO getDocumentoDAO(){
		return new DocumentoDAOImpl();
	}
	
}
