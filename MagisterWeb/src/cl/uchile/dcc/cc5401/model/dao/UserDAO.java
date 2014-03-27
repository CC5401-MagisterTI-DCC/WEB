package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.UserDTO;

public interface UserDAO {

	public UserDTO getUser(int id);
	
	public UserDTO getUser(String username);
	
	public List<UserDTO> getAll();
	
	public List<Integer> getComisionIds();
	
	public void agregar(UserDTO user);
	
	public void eliminar(int id);
	
	public void actualizar(UserDTO user);
	
	public void actualizarPassword(UserDTO user);
	
	
}
