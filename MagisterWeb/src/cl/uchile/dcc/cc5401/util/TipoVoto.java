package cl.uchile.dcc.cc5401.util;

public enum TipoVoto {
	POSITIVO(1),NEGATIVO(2);
	private final int value;
	
	TipoVoto(int value){this.value = value;}
	
	public int getId() { return this.value; }
	
	public static TipoVoto getValue(int id){
		for(TipoVoto v : values()){
			if(v.getId()==id)
				return v;
		}
		return null;
	}
}
