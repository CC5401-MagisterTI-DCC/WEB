package cl.uchile.dcc.cc5401.model.dao;

import java.util.List;

import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;

public interface DocumentoDAO {

	public DocumentoDTO get(int idDocumento);
	
	public List<DocumentoDTO> getExtras(int idPostulacion);
	
	public void agregarExtra(int idPostulacion, DocumentoDTO documento);
	
	public int agregar(DocumentoDTO documento);
	
	public void actualizar(DocumentoDTO documento);
}
