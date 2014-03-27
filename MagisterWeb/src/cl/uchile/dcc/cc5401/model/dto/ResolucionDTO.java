package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

import cl.uchile.dcc.cc5401.util.ResultadoPostulacion;

public class ResolucionDTO {

	private int id;
	private int idPostulacion;
	private String comentario;
	private ResultadoPostulacion resultado;
	private Date fecha;
	
	public ResolucionDTO(){
		
	}

	public ResolucionDTO(int id, int idPostulacion, String comentario,
			ResultadoPostulacion resultado, Date fecha) {
		super();
		this.id = id;
		this.idPostulacion = idPostulacion;
		this.comentario = comentario;
		this.resultado = resultado;
		this.fecha = fecha;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setIdPostulacion(int idPostulacion) {
		this.idPostulacion = idPostulacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public ResultadoPostulacion getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoPostulacion resultado) {
		this.resultado = resultado;
	}

	
	
	
	
}
