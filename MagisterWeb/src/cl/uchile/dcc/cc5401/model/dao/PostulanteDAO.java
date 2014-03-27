package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;

public interface PostulanteDAO {

	public int agregar(PostulanteDTO postulante);
	
	public void actualizar(PostulanteDTO postulante);
	
	public List<PostulanteDTO> getAll();
	
	public PostulanteDTO get(int id);
	
	public PostulanteDTO getByName(String name);
	
	public PostulanteDTO getByEmail(String email);
	
}
