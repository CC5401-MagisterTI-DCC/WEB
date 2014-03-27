package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.ResolucionDAO;
import cl.uchile.dcc.cc5401.model.dto.ResolucionDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;
import cl.uchile.dcc.cc5401.util.ResultadoPostulacion;

public class ResolucionDAOImpl implements ResolucionDAO {

	
	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM resolucion"
					+ " WHERE id_postulacion=?";
	
	private static final String SQL_INSERT =
			"INSERT INTO resolucion ("
					+ " id_postulacion, comentario, id_tipo_resolucion, fecha"
					+ ") VALUES (?,?,?,?)";
	
	private static final String SQL_LAST_ID = 
			"SELECT MAX(id) AS id FROM resolucion";
	
	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private List<ResolucionDTO> getResults(ResultSet rs) throws SQLException {
		List<ResolucionDTO> results = new ArrayList<ResolucionDTO>();
		while (rs.next()) {
			ResolucionDTO resolucionDTO= new ResolucionDTO();
			resolucionDTO.setId(rs.getInt("id"));
			resolucionDTO.setIdPostulacion(rs.getInt("id_postulacion"));
			resolucionDTO.setComentario(rs.getString("comentario"));
			resolucionDTO.setFecha(rs.getDate("fecha"));
			
			resolucionDTO.setResultado(ResultadoPostulacion.getValue(rs.getInt("id_tipo_resolucion")));
			
			results.add(resolucionDTO);

		}
		return results;
	}
	
	@Override
	public ResolucionDTO get(int idPostulacion) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idPostulacion);
			resultSet = ptmt.executeQuery();
			List<ResolucionDTO> results = getResults(resultSet);
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
	public int agregar(ResolucionDTO resolucion) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, resolucion.getIdPostulacion());
			ptmt.setString(2,resolucion.getComentario());
			ptmt.setInt(3,resolucion.getResultado().getId());
			ptmt.setDate(4, new Date(resolucion.getFecha().getTime()));
			
			ptmt.executeUpdate();
			ptmt = connection.prepareStatement(SQL_LAST_ID);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		} try {
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
		
		return id;


	}

}
