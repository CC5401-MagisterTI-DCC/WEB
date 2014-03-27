package cl.uchile.dcc.cc5401.util;

public enum TipoFinanciamiento {
	PARTICULAR(1),BECA(2),EMPRESA(3);
	
	private final int id;
	
	TipoFinanciamiento(int id){this.id = id;}
	
	public int getId() { return this.id; }
	
	public static TipoFinanciamiento getValue(int id){
		for(TipoFinanciamiento v : values()){
			if(v.getId()==id)
				return v;
		}
		return null;
	}
	
}
