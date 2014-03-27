package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.PaisDAO;
import cl.uchile.dcc.cc5401.model.dto.PaisDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class PaisDAOImpl implements PaisDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT_All =
			"SELECT *"
					+ " FROM pais";
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM pais"
					+ " WHERE id=?";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	private List<PaisDTO> getResults(ResultSet rs) throws SQLException {
		List<PaisDTO> results = new ArrayList<PaisDTO>();
		while (rs.next()) {
			PaisDTO paisDTO= new PaisDTO();
			paisDTO.setId(rs.getInt("id"));
			paisDTO.setNombre(rs.getString("nombre"));
			
			
			results.add(paisDTO);

		}
		return results;
	}

	
	@Override
	public List<PaisDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_All);
			resultSet = ptmt.executeQuery();
			List<PaisDTO> results = getResults(resultSet);
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
	public PaisDTO get(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			List<PaisDTO> results = getResults(resultSet);
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
