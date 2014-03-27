package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

public class HistorialDTO {

	private int id;
	private int idPostulacion;
	private String accion;
	private Date fecha;
	private String comentario;
	
	public HistorialDTO(){}

	public HistorialDTO(int id, int idPostulacion, String accion, Date fecha,
			String comentario) {
		super();
		this.id = id;
		this.idPostulacion = idPostulacion;
		this.accion = accion;
		this.fecha = fecha;
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPostulacion() {
		return idPostulacion;
	}

	public void setIdPostulacion(int idPostulacion) {
		this.idPostulacion = idPostulacion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	
	
}
