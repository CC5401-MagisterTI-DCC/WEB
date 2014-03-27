package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.FinanciamientoDAO;
import cl.uchile.dcc.cc5401.model.dto.FinanciamientoDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;
import cl.uchile.dcc.cc5401.util.TipoFinanciamiento;

public class FinanciamientoDAOImpl implements FinanciamientoDAO{

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM financiamiento"
					+ " WHERE id=?";
	private static final String SQL_INSERT = 
			"INSERT INTO financiamiento(tipo, detalle) " +
			"VALUES(?,?)";
	private static final String SQL_LAST_ID = 
			"SELECT MAX(id) AS id FROM financiamiento";
	private static final String SQL_UPDATE = 
			"UPDATE financiamiento SET " +
			"tipo = ?, detalle = ? " +
			"WHERE id = ?";
	

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private List<FinanciamientoDTO> getResults(ResultSet rs) throws SQLException {
		List<FinanciamientoDTO> results = new ArrayList<FinanciamientoDTO>();
		while (rs.next()) {
			FinanciamientoDTO financiamientoDTO= new FinanciamientoDTO();
			financiamientoDTO.setId(rs.getInt("id"));
			financiamientoDTO.setTipo(TipoFinanciamiento.getValue(rs.getInt("tipo")));
			financiamientoDTO.setDetalle(rs.getString("detalle"));
			
			results.add(financiamientoDTO);

		}
		return results;
	}
	
	@Override
	public FinanciamientoDTO get(int idFinanciamiento) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idFinanciamiento);
			resultSet = ptmt.executeQuery();
			List<FinanciamientoDTO> results = getResults(resultSet);
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
	public int agregar(FinanciamientoDTO financiamiento) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, financiamiento.getTipo().getId());
			if(financiamiento.getDetalle() != null){
				ptmt.setString(2, financiamiento.getDetalle());
			}else{
				ptmt.setNull(2, java.sql.Types.VARCHAR);
			}

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
	public void actualizar(FinanciamientoDTO financiamiento) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setInt(1, financiamiento.getTipo().getId());
			if(financiamiento.getDetalle() != null){
				ptmt.setString(2, financiamiento.getDetalle());
			}else{
				ptmt.setNull(2, java.sql.Types.VARCHAR);
			}
			ptmt.setInt(3, financiamiento.getId());

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
