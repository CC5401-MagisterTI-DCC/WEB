package cl.uchile.dcc.cc5401.util;

import javax.servlet.http.Part;

public class Validacion {

	public static boolean validateRut(String rut) {
		String[] datos = rut.split("-");
		if(datos.length != 2 || !rut.matches("(\\d+-(\\d|k|K))"))			
			return false;
		int n = Integer.parseInt(datos[0]);
		int m = 2, s = 0;
        for(int i = 0; i<datos[0].length(); i++){
        	s += (n%10)*m;
        	n = n/10;
        	m++;
        	if(m==8)
        		m=2;
        }
        int dv = 11 - s%11;
        try{
	        if((dv == 11 && datos[1].equals("0")) || (dv == 10 && datos[1].equalsIgnoreCase("k")) || dv == Integer.parseInt(datos[1]))
	        	return true;
	        else
	        	return false;
        }catch(Exception e){
        	return false;
        }
	}

	public static boolean validateName(String name) {
		return !name.matches(".*\\d+.*") && name.matches("[\\wáéíóúÁÉÍÓÚñÑ]+[\\s[\\wwáéíóúÁÉÍÓÚñÑ]+]*");   
		
	}

	public static boolean validatePhone(String phone) {
		return phone.matches("(\\+?\\d+\\s?\\d+)") || phone.matches("((\\(\\+?\\d+\\))\\s?\\d+))");
	}
	
	public static boolean isPdfType(Part documentPart) {
		return documentPart.getContentType().equalsIgnoreCase("application/pdf");
	}
	
	public static boolean isDocumentEmpty(Part documentPart){
		return documentPart.getSize() == 0;
	}

	public static boolean validateDocumentPart(Part documentPart){
		return documentPart != null && isPdfType(documentPart) && !isDocumentEmpty(documentPart);
	}
}
