package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.ComentarioDTO;

public interface ComentarioDAO {

	public List<ComentarioDTO> get(int idPostulacion);
	
	public List<ComentarioDTO> get(int idPostulacion, int idUsuario);
	
	public void agregar(ComentarioDTO comentario);
	
}
