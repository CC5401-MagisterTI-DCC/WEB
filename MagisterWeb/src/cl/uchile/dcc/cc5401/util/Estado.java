package cl.uchile.dcc.cc5401.util;

public enum Estado {
	//1,2,3 No hay deadline
	REVISION(1),EN_VALIDACION(2),EN_CONSIDERACION(3),EN_EVALUACION(4),DECISION(5),EN_ESPERA(6),RESUELTA(7),ELIMINADA(8);

	private final int id;
	
	Estado(int id){this.id = id;}
	
	public int getId() { return this.id; }
	
	public static Estado getValue(int id){
		for(Estado v : values()){
			if(v.getId()==id)
				return v;
		}
		return null;
	}
	
	public static Estado getInicial(){
		return REVISION;
	}
}
