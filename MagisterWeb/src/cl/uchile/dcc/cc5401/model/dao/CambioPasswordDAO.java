package cl.uchile.dcc.cc5401.model.dao;

import cl.uchile.dcc.cc5401.model.dto.CambioPasswordDTO;


public interface CambioPasswordDAO {
	public CambioPasswordDTO get(int idUsuario, String codigo);
	
	public void agregar(CambioPasswordDTO cambioPassword);
	
	public void quitar(CambioPasswordDTO cambioPassword);
	
	public void limpiar();
}
