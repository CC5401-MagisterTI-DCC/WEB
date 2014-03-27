package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;

public interface HistorialDAO {

	public List<HistorialDTO> get(int idPostulacion);

	public List<HistorialDTO> getAll();
	
	public int agregar(HistorialDTO historial);
}
