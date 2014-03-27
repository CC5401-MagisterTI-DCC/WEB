package cl.uchile.dcc.cc5401.model.dto;

import java.util.List;

public class UserDTO {

	private int id;
	private String username;
	private String password;
	private String email;
	private String rol;
	private int idRol;
	private List<String> permisos;
	
	public UserDTO(){
		
	}
	
	public UserDTO(int id, String username, String password, String email, String rol, int idRol,
			List<String> permisos) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.idRol = idRol;
		this.rol = rol;
		this.permisos = permisos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<String> getPermisos() {
		return permisos;
	}
	
	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	
	public boolean hasPermisos(String permiso){
		for(String p : this.permisos){
			if(p.equalsIgnoreCase(permiso))
				return true;
		}
		return false;
	}

	public void setPermisos(List<String> permisos) {
		this.permisos = permisos;
	}


	
	
	
	
	
	
}
