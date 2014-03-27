package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.PaisDTO;

public interface PaisDAO {

	public List<PaisDTO> getAll();
	
	public PaisDTO get(int id);
	
}
