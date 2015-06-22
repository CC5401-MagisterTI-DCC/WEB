package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import cl.uchile.dcc.cc5401.model.dao.CambioPasswordDAO;
import cl.uchile.dcc.cc5401.model.dto.CambioPasswordDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class CambioPasswordDAOImpl implements CambioPasswordDAO {
	
	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM cambio_password "
					+ " WHERE usuario=? AND codigo=?";

	private static final String SQL_CLEANUP =
			"DELETE FROM cambio_password"
					+ " WHERE fechahora < ?";

	private static final String SQL_DELETE =
			"DELETE FROM cambio_password"
					+ " WHERE usuario=? AND codigo=?";

	private static final String SQL_INSERT =
			"INSERT INTO cambio_password (usuario, codigo, fechahora)"
					+ " VALUES (?, ?, ?)";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private CambioPasswordDTO getResult(ResultSet rs) throws SQLException {
		CambioPasswordDTO cambioPasswordDTO = new CambioPasswordDTO();
		boolean empty = true;
		while (rs.next()) {
			empty = false;
			cambioPasswordDTO.setIdUsuario(rs.getInt("usuario"));
			cambioPasswordDTO.setCodigo(rs.getString("codigo"));
			cambioPasswordDTO.setFechaHora(rs.getTimestamp("fechahora"));
		}
		return empty? null : cambioPasswordDTO;
	}


	@Override
	public CambioPasswordDTO get(int idUsuario, String codigo) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idUsuario);
			ptmt.setString(2, codigo);
			resultSet = ptmt.executeQuery();
			CambioPasswordDTO result = getResult(resultSet);
			if(result==null){
				return null;
			}
			Date now = new Date();
			Date resultDate = result.getFechaHora();
			final int TWELVE_HOURS = 1000*60*60*12;
			if(now.getTime() - resultDate.getTime() > TWELVE_HOURS){
				ptmt.close();
				ptmt = connection.prepareStatement(SQL_DELETE);
				ptmt.setInt(1, idUsuario);
				ptmt.setString(2, codigo);
				ptmt.executeUpdate();
				return null;
			}
			return result;
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
	public void agregar(CambioPasswordDTO cambioPassword) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			Date now = new Date();
			ptmt.setInt(1, cambioPassword.getIdUsuario());
			ptmt.setString(2, cambioPassword.getCodigo());
			ptmt.setTimestamp(3, new Timestamp(now.getTime()));
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
	public void limpiar() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_CLEANUP);
			Date now = new Date();
			ptmt.setTimestamp(1, new Timestamp(now.getTime() - 1000*60*60*12));
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
	public void quitar(CambioPasswordDTO cambioPassword) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_DELETE);
			ptmt.setInt(1, cambioPassword.getIdUsuario());
			ptmt.setString(2, cambioPassword.getCodigo());
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
