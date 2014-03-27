package cl.uchile.dcc.cc5401.model.dto;

import java.io.Serializable;
import java.util.Date;

public class ComentarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private int idPostulacion;
	private int idUsuario;
	private String texto;
	private Date fecha;
	private boolean sugerencia;
	
	public ComentarioDTO(){
		
	}

	public ComentarioDTO(int id, int idPostulacion, int idUsuario,
			String texto, Date fecha, boolean sugerencia) {
		super();
		this.id = id;
		this.idPostulacion = idPostulacion;
		this.idUsuario = idUsuario;
		this.texto = texto;
		this.fecha = fecha;
		this.sugerencia = sugerencia;
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

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isSugerencia() {
		return sugerencia;
	}

	public void setSugerencia(boolean sugerencia) {
		this.sugerencia = sugerencia;
	}
	

	
	
	
	
}
