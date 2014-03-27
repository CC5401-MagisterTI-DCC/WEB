package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.VotoDTO;

public interface VotoDAO {

	public VotoDTO get(int idPostulacion,int idUsuario);
	
	public List<VotoDTO> getAll();
	
	public void agregar(VotoDTO voto);
	
	public void actualizar(VotoDTO voto, int idPostulacion, int idUsuario);
}
