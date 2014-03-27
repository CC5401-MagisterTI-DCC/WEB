package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

public class LogDTO {

	private int id;
	private Date fecha;
	private String accion;
	
	public LogDTO(int id, Date fecha, String accion) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.accion = accion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	
	
	
}
