package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.GradoAcademicoDTO;

public interface GradoAcademicoDAO {

	public List<GradoAcademicoDTO> get(int idPostulante);
	
	public int agregar(GradoAcademicoDTO gradoAcademico);
	
	public void actualizar(GradoAcademicoDTO gradoAcademico);
	
	public GradoAcademicoDTO getFromId(int id);
	
}
