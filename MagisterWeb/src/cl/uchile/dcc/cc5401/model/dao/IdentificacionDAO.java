package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;

public interface IdentificacionDAO {
	
	public List<IdentificacionDTO> getAll();
	
	public IdentificacionDTO get(int id);
	
	public int agregar(IdentificacionDTO identificacion);
	
	public void actualizar(IdentificacionDTO identificacion);

	public IdentificacionDTO get(IdentificacionDTO identificacion);

}
