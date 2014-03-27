package cl.uchile.dcc.cc5401.model.dto;

import cl.uchile.dcc.cc5401.util.TipoFinanciamiento;

public class FinanciamientoDTO {
	
	private int id;
	private TipoFinanciamiento tipo;
	private String detalle;
	
	public FinanciamientoDTO(){
		
	}
	
	public FinanciamientoDTO(int id, TipoFinanciamiento tipo, String detalle){
		this.id = id;
		this.tipo = tipo;
		this.detalle = detalle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoFinanciamiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoFinanciamiento tipo) {
		this.tipo = tipo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	
}
