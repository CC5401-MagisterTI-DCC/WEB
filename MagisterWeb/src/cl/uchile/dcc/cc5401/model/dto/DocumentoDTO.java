package cl.uchile.dcc.cc5401.model.dto;

public class DocumentoDTO {

	private int id;
	private String direccion;
	private String nombre;
	private String comentario;
	
	public DocumentoDTO(){}
	
	public DocumentoDTO(int id, String direccion, String nombre,
			String comentario) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.nombre = nombre;
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	
}
