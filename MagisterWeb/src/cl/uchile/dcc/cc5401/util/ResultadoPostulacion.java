package cl.uchile.dcc.cc5401.util;


//TODO: Agregar más
public enum ResultadoPostulacion {
	ACEPTADO(1),RECHAZADO(2), ACEPTADO_CONDICIONAL(3);
	
	private final int id;
	
	ResultadoPostulacion(int id){this.id = id;}
	
	public int getId() { return this.id; }
	
	public static ResultadoPostulacion getValue(int id){
		for(ResultadoPostulacion v : values()){
			if(v.getId()==id)
				return v;
		}
		return null;
	}
}
