package cl.uchile.dcc.cc5401.model.dto;

public class IdentificacionDTO {
	
	private int id;
	private String identificacion;
	private PaisDTO pais;
	private boolean esRut;
	
	public IdentificacionDTO(){
		
	}
	
	public IdentificacionDTO(int id, String identificacion, PaisDTO pais, boolean esRut){
		this.id = id;
		this.identificacion = identificacion;
		this.pais = pais;
		this.esRut = esRut;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public PaisDTO getPais() {
		return pais;
	}
	public void setPais(PaisDTO pais) {
		this.pais = pais;
	}
	public boolean isEsRut() {
		return esRut;
	}
	public void setEsRut(boolean esRut) {
		this.esRut = esRut;
	}
	
	
}
