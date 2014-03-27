package cl.uchile.dcc.cc5401.util;

import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;

public class PostulacionPostulante {

	private PostulacionDTO postulacion;
	private PostulanteDTO postulante;
	
	public PostulacionPostulante(PostulacionDTO postulacion,
			PostulanteDTO postulante) {
		super();
		this.postulacion = postulacion;
		this.postulante = postulante;
	}
	
	public PostulacionDTO getPostulacion() {
		return postulacion;
	}
	
	public void setPostulacion(PostulacionDTO postulacion) {
		this.postulacion = postulacion;
	}
	
	public PostulanteDTO getPostulante() {
		return postulante;
	}
	
	public void setPostulante(PostulanteDTO postulante) {
		this.postulante = postulante;
	}
	
	
}
