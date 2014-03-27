package cl.uchile.dcc.cc5401.model.dao;

import cl.uchile.dcc.cc5401.model.dto.DatosEmpresaDTO;

public interface DatosEmpresaDAO {

	public DatosEmpresaDTO get(int idPostulante);
	
	public int agregar(DatosEmpresaDTO datosEmpresa);
	
	public void actualizar(DatosEmpresaDTO datosEmpresa);
}
