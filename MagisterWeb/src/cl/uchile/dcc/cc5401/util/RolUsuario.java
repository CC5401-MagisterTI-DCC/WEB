package cl.uchile.dcc.cc5401.util;

public enum RolUsuario {
	POSTULANTE(0),ADMINISTRADOR(1),COORDINADOR(2),COMISIONADO(3),ASISTENTE(4),JEFE_PEC(5);
	private final int value;
	
	RolUsuario(int value){this.value = value;}
	
	public int getId() { return this.value; }
	
	public static RolUsuario getValue(int id){
		for(RolUsuario v : values()){
			if(v.getId()==id)
				return v;
		}
		return null;
	}
}
