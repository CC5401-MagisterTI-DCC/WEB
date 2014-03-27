package cl.uchile.dcc.cc5401.util;

public enum Genero {
	MASCULINO(1),FEMENINO(2);
	
	private final int id;
	
	Genero(int id){this.id=id;}
	
	public int getId(){
		return this.id;
	}
	
	public static Genero getValue(int id){
		for(Genero v : values()){
			if(v.getId()==id)
				return v;
		}
		return null;
	}
	
	
}
