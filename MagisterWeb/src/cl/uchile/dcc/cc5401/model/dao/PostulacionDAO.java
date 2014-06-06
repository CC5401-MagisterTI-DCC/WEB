package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;

public interface PostulacionDAO {

	public PostulacionDTO getPostulacion(int id);
	
	public PostulacionDTO getPostulacion(String track);
	
	public List<PostulacionDTO> getAll();
	
	public int agregar(PostulacionDTO postulacion);
	
	public void actualizar(PostulacionDTO postulacion);
	
	public void eliminar(int id);

	public PostulacionDTO getPostulacionActiva(IdentificacionDTO identificacion);
	
	public PostulacionDTO getPostulacion(String track, String email);
	
	public List<HistorialDTO> getHistorialPostulacionesResueltas(IdentificacionDTO identificacion);
	
}
