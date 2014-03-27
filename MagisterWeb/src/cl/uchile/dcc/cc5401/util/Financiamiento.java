package cl.uchile.dcc.cc5401.util;

public enum Financiamiento {
	PARTICULAR(1),BECA(2),EMPRESA(3);
	
	private final int id;
	
	Financiamiento(int id){this.id = id;}
	
	public int getId() { return this.id; }
}
