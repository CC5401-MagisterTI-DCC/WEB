package cl.uchile.dcc.cc5401.model.dao;

import cl.uchile.dcc.cc5401.model.dto.ResolucionDTO;

public interface ResolucionDAO {

	public ResolucionDTO get(int idPostulacion);
	
	public int agregar(ResolucionDTO resolucion);
	
}
