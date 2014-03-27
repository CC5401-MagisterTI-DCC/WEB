package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

import cl.uchile.dcc.cc5401.util.Estado;

public class PostulacionDTO {

	private int id;
	private int idPostulante;
	private int idFinanciamiento;
	//TODO: necesidad de nombre postulante
	private String nombrePostulante;
	private int idCarta1;
	private int idCarta2;
	private int idCV;
	private int idCartaPresentacion;
	private Estado estado;
	private Date deadline;
	private Date fechaIngreso;

	public PostulacionDTO(){

	}

	public PostulacionDTO(int id, int idPostulante, String nombrePostulante, int financiamiento, 
			int idCarta1, int idCarta2,	int idCV, int idCartaPresentacion, Estado estado, Date deadline,
			Date fechaIngreso) {
		super();
		this.id = id;
		this.idPostulante = idPostulante;
		this.nombrePostulante = nombrePostulante;
		this.idFinanciamiento = financiamiento;
		this.idCarta1 = idCarta1;
		this.idCarta2 = idCarta2;
		this.idCV = idCV;
		this.idCartaPresentacion = idCartaPresentacion;
		this.estado = estado;
		this.deadline = deadline;
		this.fechaIngreso = fechaIngreso;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdFinanciamiento() {
		return idFinanciamiento;
	}

	public void setIdFinanciamiento(int financiamiento) {
		this.idFinanciamiento = financiamiento;
	}

	public int getIdPostulante() {
		return idPostulante;
	}

	public void setIdPostulante(int idPostulante) {
		this.idPostulante = idPostulante;
	}
	
	

	public String getNombrePostulante() {
		return nombrePostulante;
	}

	public void setNombrePostulante(String nombrePostulante) {
		this.nombrePostulante = nombrePostulante;
	}

	public int getIdCarta1() {
		return idCarta1;
	}

	public void setIdCarta1(int idCarta1) {
		this.idCarta1 = idCarta1;
	}

	public int getIdCarta2() {
		return idCarta2;
	}

	public void setIdCarta2(int idCarta2) {
		this.idCarta2 = idCarta2;
	}

	public int getIdCV() {
		return idCV;
	}

	public void setIdCV(int idCV) {
		this.idCV = idCV;
	}

	public int getIdCartaPresentacion() {
		return idCartaPresentacion;
	}

	public void setIdCartaPresentacion(int idCartaPresentacion) {
		this.idCartaPresentacion = idCartaPresentacion;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public int getId() {
		return id;
	}

	






}
