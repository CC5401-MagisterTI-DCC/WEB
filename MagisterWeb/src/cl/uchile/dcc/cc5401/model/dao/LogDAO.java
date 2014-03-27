package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.LogDTO;

public interface LogDAO {

	public void agregar(LogDTO log);
	
	public List<LogDTO> getAll();
	
	public LogDTO get(int id);
	
}
