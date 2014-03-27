package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

public class GradoAcademicoDTO {

	private int id;
	private int idPostulante;
	private String nombre;
	private String institucion;
	private Date fechaObtencion;
	private PaisDTO pais;
	private int idCertificadoNotas;
	private int idCertificadoTitulo;
	
	public GradoAcademicoDTO(){
		super();
	}
	
	public GradoAcademicoDTO(int id, String nombre, String institucion,
			Date fechaObtencion, PaisDTO pais, int idCertificadoNotas,
			int idCertificadoTitulo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.institucion = institucion;
		this.fechaObtencion = fechaObtencion;
		this.pais = pais;
		this.idCertificadoNotas = idCertificadoNotas;
		this.idCertificadoTitulo = idCertificadoTitulo;
	}

	public int getIdPostulante() {
		return idPostulante;
	}

	public void setIdPostulante(int idPostulante) {
		this.idPostulante = idPostulante;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Date getFechaObtencion() {
		return fechaObtencion;
	}

	public void setFechaObtencion(Date fechaObtencion) {
		this.fechaObtencion = fechaObtencion;
	}

	public PaisDTO getPais() {
		return pais;
	}

	public void setPais(PaisDTO pais) {
		this.pais = pais;
	}

	public int getIdCertificadoNotas() {
		return idCertificadoNotas;
	}

	public void setIdCertificadoNotas(int idCertificadoNotas) {
		this.idCertificadoNotas = idCertificadoNotas;
	}

	public int getIdCertificadoTitulo() {
		return idCertificadoTitulo;
	}

	public void setIdCertificadoTitulo(int idCertificadoTitulo) {
		this.idCertificadoTitulo = idCertificadoTitulo;
	}

	public int getId() {
		return id;
	}
	
	
	
	
}
