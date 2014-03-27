package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.VotoDAO;
import cl.uchile.dcc.cc5401.model.dto.VotoDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;
import cl.uchile.dcc.cc5401.util.TipoVoto;

public class VotoDAOImpl implements VotoDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM votacion"
					+ " WHERE id_postulacion = ? AND usuario = ?";
	
	private static final String SQL_SELECT_ALL =
			"SELECT *"
					+ " FROM votacion ";
	
	private static final String SQL_INSERT =
			"INSERT INTO votacion ("
					+ " id_postulacion, usuario, id_tipo_voto "
					+ ") VALUES (?,?,?)";
	private static final String SQL_UPDATE =
			"UPDATE votacion SET"
					+ " id_postulacion = ?, usuario = ?, id_tipo_voto = ? "
					+ "  WHERE id_postulacion = ? AND usuario = ?";
	
	

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	private List<VotoDTO> getResults(ResultSet rs) throws SQLException {
		List<VotoDTO> results = new ArrayList<VotoDTO>();
		while (rs.next()) {
			VotoDTO votoDTO= new VotoDTO();
			votoDTO.setId(rs.getInt("id"));
			votoDTO.setIdPostulacion(rs.getInt("id_postulacion"));
			votoDTO.setIdUsuario(rs.getInt("usuario"));
			votoDTO.setTipoVoto(TipoVoto.getValue(rs.getInt("id_tipo_voto")));
			results.add(votoDTO);
		}
		return results;
	}
	
	@Override
	public VotoDTO get(int idPostulacion, int idUsuario) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idPostulacion);
			ptmt.setInt(2, idUsuario);
			resultSet = ptmt.executeQuery();
			List<VotoDTO> results = getResults(resultSet);
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
	public List<VotoDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_ALL);
			resultSet = ptmt.executeQuery();
			List<VotoDTO> results = getResults(resultSet);
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
	public void agregar(VotoDTO voto) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, voto.getIdPostulacion());
			ptmt.setInt(2, voto.getIdUsuario());
			ptmt.setInt(3,voto.getTipoVoto().getId());
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
	public void actualizar(VotoDTO voto, int idPostulacion, int idUsuario) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setInt(1, voto.getIdPostulacion());
			ptmt.setInt(2, voto.getIdUsuario());
			ptmt.setInt(3,voto.getTipoVoto().getId());
			ptmt.setInt(4,idPostulacion);
			ptmt.setInt(5,idUsuario);
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

}
