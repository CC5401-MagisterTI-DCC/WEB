package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.GradoAcademicoDAO;
import cl.uchile.dcc.cc5401.model.dto.GradoAcademicoDTO;
import cl.uchile.dcc.cc5401.model.dto.PaisDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class GradoAcademicoDAOImpl implements GradoAcademicoDAO {

	
	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM grado_academico ga"
					+ " JOIN pais pa ON ga.id_pais=pa.id"
					+ " WHERE ga.id_postulante=?";
	
	private static final String SQL_INSERT =
			"INSERT INTO grado_academico ("
					+ " id_postulante, nombre, institucion, ano_obtencion, id_pais, id_certificado_notas, id_certificado_titulo"
					+ " ) VALUES (?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE =
			"UPDATE grado_academico SET"
					+ " id_postulante = ?, nombre = ?, institucion = ?, ano_obtencion = ?, id_pais = ?, id_certificado_notas = ?, id_certificado_titulo = ?"
					+ " WHERE id=?";
	
	private static final String SQL_SELECT_ID =
			"SELECT * FROM grado_academico ga " +
			"JOIN pais pa ON ga.id_pais = pa.id " +
			"WHERE ga.id = ?";
	
	private static final String SQL_LAST_ID = 
			"SELECT MAX(id) AS id FROM grado_academico";
	
	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private List<GradoAcademicoDTO> getResults(ResultSet rs) throws SQLException {
		List<GradoAcademicoDTO> results = new ArrayList<GradoAcademicoDTO>();
		while (rs.next()) {
			GradoAcademicoDTO gradoAcademicoDTO= new GradoAcademicoDTO();
			gradoAcademicoDTO.setId(rs.getInt("id"));
			gradoAcademicoDTO.setNombre(rs.getString("nombre"));
			gradoAcademicoDTO.setIdPostulante(rs.getInt("id_postulante"));
			gradoAcademicoDTO.setInstitucion(rs.getString("institucion"));
			gradoAcademicoDTO.setAnoObtencion(rs.getInt("ano_obtencion"));
			gradoAcademicoDTO.setPais(new PaisDTO(rs.getInt(9),rs.getString(10)));
			gradoAcademicoDTO.setIdCertificadoNotas(rs.getInt("id_certificado_notas"));
			gradoAcademicoDTO.setIdCertificadoTitulo(rs.getInt("id_certificado_titulo"));
			
			results.add(gradoAcademicoDTO);

		}
		return results;
	}
	
	@Override
	public int agregar(GradoAcademicoDTO gradoAcademico) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, gradoAcademico.getIdPostulante());
			ptmt.setString(2, gradoAcademico.getNombre());
			ptmt.setString(3, gradoAcademico.getInstitucion());
			ptmt.setInt(4, gradoAcademico.getAnoObtencion());
			ptmt.setInt(5, gradoAcademico.getPais().getId());
			ptmt.setInt(6, gradoAcademico.getIdCertificadoNotas());
			ptmt.setInt(7, gradoAcademico.getIdCertificadoTitulo());

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
	public void actualizar(GradoAcademicoDTO gradoAcademico) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setInt(1, gradoAcademico.getIdPostulante());
			ptmt.setString(2, gradoAcademico.getNombre());
			ptmt.setString(3, gradoAcademico.getInstitucion());
			ptmt.setInt(4, gradoAcademico.getAnoObtencion());
			ptmt.setInt(5, gradoAcademico.getPais().getId());
			ptmt.setInt(6, gradoAcademico.getIdCertificadoNotas());
			ptmt.setInt(7, gradoAcademico.getIdCertificadoTitulo());
			ptmt.setInt(8, gradoAcademico.getId());
			
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
	public List<GradoAcademicoDTO> get(int idPostulante) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idPostulante);
			resultSet = ptmt.executeQuery();
			List<GradoAcademicoDTO> results = getResults(resultSet);
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
	public GradoAcademicoDTO getFromId(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_ID);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			List<GradoAcademicoDTO> results = getResults(resultSet);
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

}
