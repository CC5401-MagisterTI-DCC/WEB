package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class DocumentoDAOImpl implements DocumentoDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM documento"
					+ " WHERE id=?";
	
	private static final String SQL_SELECT_EXTRAS =
			"SELECT do.id, do.direccion, do.nombre, do.comentario "
					+ " FROM doc_extra de JOIN documento do ON de.id_documento = do.id "
					+ " WHERE de.id_postulacion=?";
	
	private static final String SQL_INSERT =
			"INSERT INTO documento ("
					+ "direccion, nombre, comentario"
					+ ") VALUES (?,?,?)";
	
	private static final String SQL_INSERT_EXTRA =
			"INSERT INTO doc_extra ("
					+ "id_postulacion, id_documento"
					+ ") VALUES (?,?)";
	
	private static final String SQL_UPDATE=
			"UPDATE documento SET"
					+ " direccion = ?, nombre = ?, comentario = ?"
					+ " WHERE id=?";
	
	
	private static final String SQL_LAST_ID =
			"SELECT MAX(id) AS id FROM documento";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private List<DocumentoDTO> getResults(ResultSet rs) throws SQLException {
		List<DocumentoDTO> results = new ArrayList<DocumentoDTO>();
		while (rs.next()) {
			DocumentoDTO documentoDTO= new DocumentoDTO();
			documentoDTO.setId(rs.getInt("id"));
			documentoDTO.setDireccion(rs.getString("direccion"));
			documentoDTO.setNombre(rs.getString("nombre"));
			documentoDTO.setComentario(rs.getString("comentario"));
			results.add(documentoDTO);

		}
		return results;
	}
	
	
	@Override
	public DocumentoDTO get(int idDocumento) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idDocumento);
			resultSet = ptmt.executeQuery();
			List<DocumentoDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int agregar(DocumentoDTO documento) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setString(1, documento.getDireccion());
			ptmt.setString(2, documento.getNombre());
			if(documento.getComentario()==null)
				ptmt.setNull(3, java.sql.Types.VARCHAR);
			else
				ptmt.setString(3, documento.getComentario());
			
			ptmt.executeUpdate();
			
			ptmt = connection.prepareStatement(SQL_LAST_ID);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			id = resultSet.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return id;
	}

	@Override
	public void actualizar(DocumentoDTO documento) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setString(1, documento.getDireccion());
			ptmt.setString(2, documento.getNombre());
			if(documento.getComentario()==null)
				ptmt.setNull(3, java.sql.Types.VARCHAR);
			else
				ptmt.setString(3, documento.getComentario());
			ptmt.setInt(4, documento.getId());
			ptmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<DocumentoDTO> getExtras(int idPostulacion) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_EXTRAS);
			ptmt.setInt(1, idPostulacion);
			resultSet = ptmt.executeQuery();
			List<DocumentoDTO> results = getResults(resultSet);
			if (results.size() > 0) {
				return results;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void agregarExtra(int idPostulacion, DocumentoDTO documento) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setString(1, documento.getDireccion());
			ptmt.setString(2, documento.getNombre());
			if(documento.getComentario()==null)
				ptmt.setNull(3, java.sql.Types.VARCHAR);
			else
				ptmt.setString(3, documento.getComentario());
			
			ptmt.executeUpdate();
			
			ptmt = connection.prepareStatement(SQL_LAST_ID);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			id = resultSet.getInt("id");
			
			ptmt = connection.prepareStatement(SQL_INSERT_EXTRA);
			
			ptmt.setInt(1, idPostulacion);
			ptmt.setInt(2, id);
			ptmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}

}
