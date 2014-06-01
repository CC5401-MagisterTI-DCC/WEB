package cl.uchile.dcc.cc5401.util.integrationtests;


import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public abstract class IntegrationTest {
	
	protected Connection connect = null;
	String dbUser = "root";
	String dbPwd = "x963x963";
	
	protected void openDBConnection() throws Exception{
		String driverClassName = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://localhost:3306/inscmtiDB";
		
		Class.forName(driverClassName);
		connect = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
	}
	
	protected void closeDBConnection() throws SQLException{	
		connect.close();	
	}
	
	protected void createDBDump() throws InterruptedException, IOException{
		
		//Dump para poder deshacer de todos los cambios
		String[] cmd = { "/bin/sh", "-c", "mysqldump -u"+dbUser+" -p"+dbPwd+" inscmtiDB > ~/backup1.sql"};
		Runtime.getRuntime().exec(cmd).waitFor();
	}
	
	protected void importDBDump() throws InterruptedException, IOException{
		
		//Deshacer los cambios
		String[] cmd = { "/bin/sh", "-c", "mysql -u"+dbUser+" -p"+dbPwd+" inscmtiDB < ~/backup1.sql"};
		Runtime.getRuntime().exec(cmd).waitFor();
	}
	
	protected void createUser(String adminUser, String adminPassword, String newUser, String newMail, String newPassword, String newRol) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		WebClient webClient = logIn(adminUser, adminPassword);
		webClient.getOptions().setJavaScriptEnabled(false);
	    
	    HtmlPage pageAdmin = webClient.getPage("http://localhost:8080/MagisterWeb/app/admin/userAdmin?action=new");
	    HtmlTextInput user = (HtmlTextInput) pageAdmin.getElementById("username");
	    user.setValueAttribute(newUser);
	    HtmlTextInput mail = (HtmlTextInput) pageAdmin.getElementById("email");
	    mail.setValueAttribute(newMail);
	    HtmlPasswordInput pass = (HtmlPasswordInput) pageAdmin.getElementById("password");
	    pass.setValueAttribute(newPassword);
	    HtmlSelect rol = (HtmlSelect) pageAdmin.getElementById("rol");
	    rol.setSelectedAttribute(newRol, true);
	    HtmlSubmitInput submitUser = (HtmlSubmitInput)pageAdmin.getElementById("aceptar");
	    submitUser.click();
	}
	
	public void newPostulante(String nombres, String apellidos, String tipoDoc, String idDoc,
			String idNacionalidad, boolean isMasculino, boolean isFemenino,String fechaNacimiento,
			String email, String telefono, String direccion, String grado, String institucion,
			String fechaObtencion, String idPaisGrado, String docPath) throws IOException{
		
		final WebClient webClientPostulante = new WebClient();
		
		//Desactivo javascript ya que si no se generan algunos erroes
		webClientPostulante.getOptions().setJavaScriptEnabled(false);
		//Voy a la pagina que necesito
	    final HtmlPage pagePostulante = webClientPostulante.getPage("http://localhost:8080/MagisterWeb/app/form");

	    
	    //Voy buscando cada uno de los elementos del formulario que necesito y los voy editando
	    HtmlTextInput nombre = (HtmlTextInput) pagePostulante.getElementById("nombre");
	    nombre.setValueAttribute(nombres);
	    HtmlTextInput apellido = (HtmlTextInput) pagePostulante.getElementById("apellido");
	    apellido.setValueAttribute(apellidos);
	    HtmlSelect doc = (HtmlSelect) pagePostulante.getElementById("tipoDoc");
	    doc.setSelectedAttribute(tipoDoc, true);
	    HtmlTextInput rut = (HtmlTextInput) pagePostulante.getElementById("rut");
	    rut.setValueAttribute(idDoc);
	    HtmlSelect nacionalidad = (HtmlSelect) pagePostulante.getElementById("nacionalidad");
	    nacionalidad.setSelectedAttribute(idNacionalidad, true);
	    HtmlRadioButtonInput genero = (HtmlRadioButtonInput) pagePostulante.getElementById("radioMasculino");
	    genero.setChecked(isMasculino);
	    HtmlRadioButtonInput gen = (HtmlRadioButtonInput) pagePostulante.getElementById("radioFemenino");
	    gen.setChecked(isFemenino);
	    HtmlTextInput fechanac = (HtmlTextInput)pagePostulante.getElementById("fecha_nac");
	    fechanac.setText(fechaNacimiento);
	    HtmlTextInput email1 = (HtmlTextInput) pagePostulante.getElementById("emailfield");
	    email1.setValueAttribute(email);
	    HtmlTextInput email2 = (HtmlTextInput) pagePostulante.getElementById("emailfield2");
	    email2.setValueAttribute(email);
	    HtmlTextInput telef = (HtmlTextInput) pagePostulante.getElementById("telefono_p");
	    telef.setValueAttribute(telefono);
	    HtmlTextArea dir = (HtmlTextArea) pagePostulante.getElementById("direccion");
	    dir.setText(direccion);
	    HtmlTextArea grad = (HtmlTextArea) pagePostulante.getElementById("grado");
	    grad.setText(grado);
	    HtmlTextArea inst = (HtmlTextArea) pagePostulante.getElementById("institucion");
	    inst.setText(institucion);
	    HtmlTextInput fechaob = (HtmlTextInput) pagePostulante.getElementById("fecha_ob");
	    fechaob.setText(fechaObtencion);
	    HtmlSelect paisgrado = (HtmlSelect) pagePostulante.getElementById("pais_grado");
	    paisgrado.setSelectedAttribute(idPaisGrado, true);
	    
	    //Sube los documentos
	    HtmlFileInput certTitulo = (HtmlFileInput)pagePostulante.getElementById("cert_titulo");
	    certTitulo.setValueAttribute(docPath);
	    HtmlFileInput certNotas = (HtmlFileInput)pagePostulante.getElementById("cert_notas");
	    certNotas.setValueAttribute(docPath);
	    HtmlFileInput cv = (HtmlFileInput)pagePostulante.getElementById("cv");
	    cv.setValueAttribute(docPath);
	    HtmlFileInput cartaPres = (HtmlFileInput)pagePostulante.getElementById("carta_pres");
	    cartaPres.setValueAttribute(docPath);
	    HtmlFileInput cartaRec1 = (HtmlFileInput)pagePostulante.getElementById("carta_rec_1");
	    cartaRec1.setValueAttribute(docPath);
	    HtmlFileInput cartaRec2 = (HtmlFileInput)pagePostulante.getElementById("carta_rec_2");
	    cartaRec2.setValueAttribute(docPath);
	    
	    //Acepta las condiciones
	    HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) pagePostulante.getElementById("checkbox");
	    checkbox.setChecked(true);
	    
	    //Submit
	    HtmlSubmitInput submit = (HtmlSubmitInput)pagePostulante.getElementById("submit_button");
	    submit.click();
	}
	
	protected int getPostulacionId(String rut) throws SQLException{
		
	    Statement statement = connect.createStatement();
		ResultSet idIdentificacion = statement.executeQuery("SELECT id FROM identificacion WHERE identificacion = '"+rut+"'");
		idIdentificacion.next();
		
		ResultSet idPostulante = statement.executeQuery("SELECT id FROM postulante WHERE id_identificacion = "+
		idIdentificacion.getInt("id"));
		idPostulante.next();
		
		ResultSet idPostulacion = statement.executeQuery("SELECT id FROM postulacion WHERE " +
				"id_postulante = "+idPostulante.getInt("id"));
		idPostulacion.next();
		
		return idPostulacion.getInt("id");
		
		
	}
	
	protected int getEstadoResolucion(int postulacionId) throws SQLException{
		
		Statement statement = connect.createStatement();
		ResultSet estadoResolucion = statement.executeQuery("SELECT id_tipo_resolucion FROM resolucion WHERE id_postulacion = "+
		postulacionId);
		estadoResolucion.next();
		
		return estadoResolucion.getInt("id_tipo_resolucion");
		
		
	}
	
	protected int getEstadoPostulacion(int postulacionId) throws SQLException{
		
		Statement statement = connect.createStatement();
		ResultSet estadoPostulacion = statement.executeQuery("SELECT id_estado FROM postulacion WHERE id_postulante = "+
		postulacionId);
		estadoPostulacion.next();
		
		return estadoPostulacion.getInt("id_estado");
		
		
	}
	
	public static WebClient logIn(String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		HtmlPage page = webClient.getPage("http://localhost:8080/MagisterWeb/app/login");
	    HtmlTextInput user = (HtmlTextInput) page.getElementById("username");
	    user.setValueAttribute(username);
	    HtmlPasswordInput pass = (HtmlPasswordInput) page.getElementById("password");
	    pass.setValueAttribute(password);
	    
	    HtmlSubmitInput submitLogin = (HtmlSubmitInput)page.getElementById("submit_button");
	    submitLogin.click();
	    
	    return webClient;
		
		
	}
	
	public void pasarRevision(WebClient webClient, int postulacionID,boolean decision ) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		if(decision){
			webClient.getPage("http://localhost:8080/" +
			"MagisterWeb/app/admin/estado?action=revision&id="+ postulacionID);
		}
		
	}
	
	public void pasarValidacion(WebClient webClient, int postulacionID,boolean decision ) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		if(decision){
			webClient.getPage("http://localhost:8080/" +
			"MagisterWeb/app/admin/estado?action=validacion&id="+ postulacionID);
		}
	}
	
	public void pasarConsideracion(WebClient webClient, int postulacionID,boolean decision ) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
	
		if(decision){
			webClient.getPage("http://localhost:8080/MagisterWeb/" +
					"app/admin/estado?action=consideracion&id="+postulacionID+"&comentario=&deadline=29%2F12%2F3000");
		}
		
		else{
			webClient.getPage("http://localhost:8080/" +
					"MagisterWeb/app/admin/estado?id="+postulacionID+"&action=decision&detalles=&decision=rechazado");	
		}
	}
	
	public void votar(WebClient webClient, int postulacionID,boolean decision ) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
	
		if(decision){
			webClient.getPage("http://localhost:8080/MagisterWeb/" +
					"app/admin/voto?id="+postulacionID+"&comentario=&decision=aceptado");	
		}
		
		else{
			webClient.getPage("http://localhost:8080/MagisterWeb/" +
					"app/admin/voto?id="+postulacionID+"&comentario=&decision=rechazado");
		}
		
	}
	
	public void pasarEvaluacion (WebClient webClient, int postulacionID) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		webClient.getPage("http://localhost:8080/MagisterWeb/app/admin/estado?action=evaluacion&id="+postulacionID);
		
		
	}
	
	public void pasarDecision(WebClient webClient, int postulacionID,boolean decision ) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		if(decision){
			webClient.getPage("http://localhost:8080/MagisterWeb/app/admin/estado?id="+
					postulacionID+"&action=decision&detalles=&decision=aceptado");	
		}
		
		else{
			webClient.getPage("http://localhost:8080/MagisterWeb/app/admin/estado?id="+
					postulacionID+"&action=decision&detalles=&decision=rechazado");	
		}
	}
}

