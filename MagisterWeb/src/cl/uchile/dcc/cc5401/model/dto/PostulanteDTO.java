package cl.uchile.dcc.cc5401.model.dto;

import java.util.Date;

import cl.uchile.dcc.cc5401.util.Genero;

public class PostulanteDTO {

	private int id;
	private String primerNombre;
	private String segundoNombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private IdentificacionDTO identificacion;
	private PaisDTO nacionalidad;
	private Genero genero;
	private Date nacimiento;
	private String telefono;
	private String celular;
	private PaisDTO paisResidencia;
	private String email;
	private String direccion;
	
	
	public PostulanteDTO(){
		
	}


	public PostulanteDTO(String primerNombre, String segundoNombre,
			String apellidoPaterno, String apellidoMaterno, IdentificacionDTO identificacion, 
			PaisDTO nacionalidad,
			Genero genero, Date nacimiento, String telefono, String celular,
			PaisDTO paisResidencia, String email, String direccion) {
		super();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.identificacion = identificacion;
		this.nacionalidad = nacionalidad;
		this.genero = genero;
		this.nacimiento = nacimiento;
		this.telefono = telefono;
		this.celular = celular;
		this.paisResidencia = paisResidencia;
		this.email = email;
		this.direccion = direccion;
	}


	public IdentificacionDTO getIdentificacion() {
		return identificacion;
	}


	public void setIdentificacion(IdentificacionDTO identificacion) {
		this.identificacion = identificacion;
	}


	public int getId() {
		return id;
	}

	public String getCelular() {
		return celular;
	}


	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public void setId(int id) {
		this.id = id;
	}


	public String getPrimerNombre() {
		return primerNombre;
	}


	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}


	public String getSegundoNombre() {
		return segundoNombre;
	}


	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}


	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}


	public PaisDTO getNacionalidad() {
		return nacionalidad;
	}


	public void setNacionalidad(PaisDTO nacionalidad) {
		this.nacionalidad = nacionalidad;
	}


	public void setPaisResidencia(PaisDTO paisResidencia) {
		this.paisResidencia = paisResidencia;
	}


	public PaisDTO getPaisResidencia() {
		return paisResidencia;
	}


	public Genero getGenero() {
		return genero;
	}
	

	public void setGenero(Genero genero) {
		this.genero = genero;
	}


	public Date getNacimiento() {
		return nacimiento;
	}


	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	
	
	
}
