package cl.uchile.dcc.cc5401.model.dao;

import cl.uchile.dcc.cc5401.model.dto.FinanciamientoDTO;

public interface FinanciamientoDAO {
	
	public int agregar(FinanciamientoDTO financiamiento);
	
	public void actualizar(FinanciamientoDTO financiamiento);

	public FinanciamientoDTO get(int idFinanciamiento);
	
}
