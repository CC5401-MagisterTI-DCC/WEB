package cl.uchile.dcc.cc5401.model.dao;

import java.util.HashMap;

public interface EstadisticasDAO {

	public HashMap<String, Integer> getNacionalVsExtranjeros();
	
	public HashMap<String, Integer> getPostulacionesAlMes(int fromMonth, int fromYear);
	
	public HashMap<String, Integer> getEstadisticaResolucion();
	
	public HashMap<String, Integer> getEstadisticaGenero();
	
	public HashMap<String, Integer> getEstadisticaFinanciamiento();
	
	public double getPromedioResolucion();
}
