package cl.uchile.dcc.cc5401.util;

import java.io.Serializable;

public class InputHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String label;
	private String name;
	private int idDocumento;
	
	public InputHelper(String id, String label, String name, int idDocumento) {
		super();
		this.id = id;
		this.label = label;
		this.name = name;
		this.idDocumento = idDocumento;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	
	
	
	
}
