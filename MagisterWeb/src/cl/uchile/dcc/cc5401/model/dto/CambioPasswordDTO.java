package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

public class CambioPasswordDTO {

	private static final long serialVersionUID = 1L;

	private int IdUsuario;
	private String codigo;
	private Date fechaHora;
	
	public CambioPasswordDTO() {
		super();
	}

	public CambioPasswordDTO(int idUsuario, String codigo, Date fechaHora) {
		IdUsuario = idUsuario;
		this.codigo = codigo;
		this.fechaHora = fechaHora;
	}

	public int getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	

}
