package cl.uchile.dcc.cc5401.model.dto;

public class DatosEmpresaDTO {

	private int id;
	private int idPostulante;
	private String nombre;
	private String cargo;
	private String direccion;
	private String telefono;

	public DatosEmpresaDTO(){}

	public DatosEmpresaDTO(int id, int idPostulante, String nombre,
			String cargo, String direccion, String telefono) {
		super();
		this.id = id;
		this.idPostulante = idPostulante;
		this.nombre = nombre;
		this.cargo = cargo;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPostulante() {
		return idPostulante;
	}

	public void setIdPostulante(int idPostulante) {
		this.idPostulante = idPostulante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	
}
