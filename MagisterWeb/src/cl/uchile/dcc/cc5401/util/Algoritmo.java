package cl.uchile.dcc.cc5401.util;

public enum Algoritmo{
	MD5("MD5"),SHA1("SHA-1");
	private final String value;
	
	Algoritmo(String value){this.value = value;}
	
	public String getValue() { return this.value; }
	
}
