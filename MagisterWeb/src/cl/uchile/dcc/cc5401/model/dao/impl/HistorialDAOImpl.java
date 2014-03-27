package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class HistorialDAOImpl implements HistorialDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM historial"
					+ " WHERE id_postulacion=?";
	
	private static final String SQL_SELECT_All =
			"SELECT *"
					+ " FROM historial";
	
	private static final String SQL_INSERT =
			"INSERT INTO historial ("
					+ " id_postulacion, accion, fecha, comentario"
					+ ") VALUES (?,?,?,?)";

	private static final String SQL_LAST_ID = 
			"SELECT MAX(id) AS id FROM historial";
	
	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	private List<HistorialDTO> getResults(ResultSet rs) throws SQLException {
		List<HistorialDTO> results = new ArrayList<HistorialDTO>();
		while (rs.next()) {
			HistorialDTO historialDTO= new HistorialDTO();
			historialDTO.setId(rs.getInt("id"));
			historialDTO.setIdPostulacion(rs.getInt("id_postulacion"));
			historialDTO.setAccion(rs.getString("accion"));
			historialDTO.setFecha(rs.getDate("fecha"));
			historialDTO.setComentario(rs.getString("comentario"));
			
			results.add(historialDTO);

		}
		return results;
	}

	
	
	@Override
	public List<HistorialDTO> get(int idPostulacion) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idPostulacion);
			resultSet = ptmt.executeQuery();
			List<HistorialDTO> results = getResults(resultSet);
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
	public List<HistorialDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_All);
			resultSet = ptmt.executeQuery();
			List<HistorialDTO> results = getResults(resultSet);
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
	public int agregar(HistorialDTO historial) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, historial.getIdPostulacion());
			ptmt.setString(2, historial.getAccion());
			ptmt.setDate(3, new Date(historial.getFecha().getTime()));
			ptmt.setString(4, historial.getComentario());

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

}
