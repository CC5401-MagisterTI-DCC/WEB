package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.IdentificacionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PaisDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class IdentificacionDAOImpl implements IdentificacionDAO{

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_INSERT = "INSERT INTO identificacion(identificacion, pais, esRut) " +
			"VALUES (?, ?, ?)";
	
	private static final String SQL_SELECT_All =
			"SELECT *"
					+ " FROM identificacion";
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM identificacion"
					+ " WHERE id=?";
	
	private static final String SQL_LAST_ID = 
			"SELECT MAX(id) AS id FROM identificacion";
	
	private static final String SQL_UPDATE = 
			"UPDATE identificacion SET " +
			"identificacion = ?, pais = ?, esRut = ? " +
			"WHERE id = ?";
	
	private static final String SQL_SELECT_PAS = 
			"SELECT * FROM identificacion " +
			"WHERE identificacion = ? AND pais = ? AND esRut = ?";
	
	private static final String SQL_SELECT_RUT = 
			"SELECT * FROM identificacion " +
			"WHERE identificacion = ? AND ISNULL(pais) AND esRut = 1"; 
	

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	private List<IdentificacionDTO> getResults(ResultSet rs) throws SQLException {
		List<IdentificacionDTO> results = new ArrayList<IdentificacionDTO>();
		while (rs.next()) {
			IdentificacionDTO identificacionDTO= new IdentificacionDTO();
			identificacionDTO.setId(rs.getInt("id"));
			identificacionDTO.setIdentificacion(rs.getString("identificacion"));
			identificacionDTO.setPais(PaisDAOFactory.getPaisDAO().get(rs.getInt("pais")));
			identificacionDTO.setEsRut(rs.getBoolean("esRut"));
			
			
			results.add(identificacionDTO);

		}
		return results;
	}

	
	@Override
	public List<IdentificacionDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_All);
			resultSet = ptmt.executeQuery();
			List<IdentificacionDTO> results = getResults(resultSet);
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

	public int agregar(IdentificacionDTO identificacion){
		
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setString(1, identificacion.getIdentificacion());
			if(identificacion.isEsRut()){
				ptmt.setNull(2, java.sql.Types.INTEGER);
				ptmt.setInt(3, 1);
			}else{
				ptmt.setInt(2, identificacion.getPais().getId());
				ptmt.setInt(3, 0);
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
	public IdentificacionDTO get(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			List<IdentificacionDTO> results = getResults(resultSet);
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
	public void actualizar(IdentificacionDTO identificacion) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setString(1, identificacion.getIdentificacion());
			if(identificacion.isEsRut()){
				ptmt.setNull(2, java.sql.Types.INTEGER);
				ptmt.setInt(3, 1);
			}else{
				ptmt.setInt(2, identificacion.getPais().getId());
				ptmt.setInt(3, 0);
			}
			ptmt.setInt(4, identificacion.getId());
			
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

	@Override
	public IdentificacionDTO get(IdentificacionDTO identificacion) {
		try {
			connection = getConnection();
			if(identificacion.isEsRut())
				ptmt = connection.prepareStatement(SQL_SELECT_RUT);
			else{
				ptmt = connection.prepareStatement(SQL_SELECT_PAS);
				ptmt.setInt(2, identificacion.getPais().getId());
				ptmt.setInt(3, 0);
			}
			ptmt.setString(1, identificacion.getIdentificacion());
			resultSet = ptmt.executeQuery();
			List<IdentificacionDTO> results = getResults(resultSet);
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
