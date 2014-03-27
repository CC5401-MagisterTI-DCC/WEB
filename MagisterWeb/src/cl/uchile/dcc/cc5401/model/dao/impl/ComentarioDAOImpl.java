package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.ComentarioDAO;
import cl.uchile.dcc.cc5401.model.dto.ComentarioDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class ComentarioDAOImpl implements ComentarioDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM comentario "
					+ " WHERE id_postulacion=?";
	
	private static final String SQL_SELECT_USER =
			"SELECT *"
					+ " FROM comentario "
					+ " WHERE id_postulacion=? AND id_usuario=?";
	
	private static final String SQL_INSERT =
			"INSERT INTO comentario ("
					+ " id_postulacion, texto, fecha, es_sugerencia, id_usuario "
					+ ") VALUES (?,?,?,?,?)";
	

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	
	private List<ComentarioDTO> getResults(ResultSet rs) throws SQLException {
		List<ComentarioDTO> results = new ArrayList<ComentarioDTO>();
		while (rs.next()) {
			ComentarioDTO comentarioDTO= new ComentarioDTO();
			comentarioDTO.setId(rs.getInt("id"));
			comentarioDTO.setIdPostulacion(rs.getInt("id_postulacion"));
			comentarioDTO.setTexto(rs.getString("texto"));
			comentarioDTO.setFecha(rs.getDate("fecha"));
			comentarioDTO.setSugerencia(rs.getBoolean("es_sugerencia"));
			comentarioDTO.setIdUsuario(rs.getInt("id_usuario"));
			results.add(comentarioDTO);
		}
		return results;
	}
	
	@Override
	public List<ComentarioDTO> get(int idPostulacion) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idPostulacion);
			resultSet = ptmt.executeQuery();
			List<ComentarioDTO> results = getResults(resultSet);
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
	public void agregar(ComentarioDTO comentario) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, comentario.getIdPostulacion());
			ptmt.setString(2, comentario.getTexto());
			ptmt.setDate(3,new Date(comentario.getFecha().getTime()));
			ptmt.setBoolean(4, comentario.isSugerencia());
			ptmt.setInt(5, comentario.getIdUsuario());
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
	public List<ComentarioDTO> get(int idPostulacion, int idUsuario) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_USER);
			ptmt.setInt(1, idPostulacion);
			ptmt.setInt(2, idUsuario);
			resultSet = ptmt.executeQuery();
			List<ComentarioDTO> results = getResults(resultSet);
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

}
