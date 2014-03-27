package cl.uchile.dcc.cc5401.model.dto;

import cl.uchile.dcc.cc5401.util.TipoVoto;

public class VotoDTO {

	private int id;
	private int idPostulacion;
	private int idUsuario;
	private TipoVoto tipoVoto;
	
	public VotoDTO(int id, int idPostulacion, int idUsuario, TipoVoto tipoVoto) {
		super();
		this.id = id;
		this.idPostulacion = idPostulacion;
		this.idUsuario = idUsuario;
		this.tipoVoto = tipoVoto;
	}
	
	public VotoDTO() {
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
	
	public TipoVoto getTipoVoto() {
		return tipoVoto;
	}
	
	public void setTipoVoto(TipoVoto tipoVoto) {
		this.tipoVoto = tipoVoto;
	}
	
	
	
	
	
	
}
