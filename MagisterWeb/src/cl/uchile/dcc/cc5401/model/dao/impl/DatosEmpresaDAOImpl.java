package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.DatosEmpresaDAO;
import cl.uchile.dcc.cc5401.model.dto.DatosEmpresaDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class DatosEmpresaDAOImpl implements DatosEmpresaDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;
	
	private static final String SQL_SELECT =
			"SELECT *"
					+ " FROM datos_empresa"
					+ " WHERE id_postulante=?";
	private static final String SQL_INSERT =
			"INSERT INTO datos_empresa ("
					+ " id_postulante, nombre_empresa, cargo_empresa, direccion_empresa, telefono"
					+ " ) VALUES (?,?,?,?,?)";
	private static final String SQL_UPDATE =
			"UPDATE datos_empresa SET"
					+ " id_postulante = ?, nombre_empresa = ?, cargo_empresa = ?, direccion_empresa = ?, telefono = ? "
					+ " WHERE id = ?";
	
	private static final String SQL_LAST_ID = 
			"SELECT MAX(id) AS id FROM datos_empresa";
	

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	private List<DatosEmpresaDTO> getResults(ResultSet rs) throws SQLException {
		List<DatosEmpresaDTO> results = new ArrayList<DatosEmpresaDTO>();
		while (rs.next()) {
			DatosEmpresaDTO datosEmpresaDTO= new DatosEmpresaDTO();
			datosEmpresaDTO.setId(rs.getInt("id"));
			datosEmpresaDTO.setIdPostulante(rs.getInt("id_postulante"));
			datosEmpresaDTO.setNombre(rs.getString("nombre_empresa"));
			datosEmpresaDTO.setCargo(rs.getString("cargo_empresa"));
			datosEmpresaDTO.setDireccion(rs.getString("direccion_empresa"));
			datosEmpresaDTO.setTelefono(rs.getString("telefono"));
			results.add(datosEmpresaDTO);

		}
		return results;
	}
	
	@Override
	public DatosEmpresaDTO get(int idPostulante) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT);
			ptmt.setInt(1, idPostulante);
			resultSet = ptmt.executeQuery();
			List<DatosEmpresaDTO> results = getResults(resultSet);
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
	public int agregar(DatosEmpresaDTO datosEmpresa) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setInt(1, datosEmpresa.getIdPostulante());
			ptmt.setString(2,datosEmpresa.getNombre());
			ptmt.setString(3,datosEmpresa.getCargo());
			ptmt.setString(4, datosEmpresa.getDireccion());
			ptmt.setString(5, datosEmpresa.getTelefono());
			
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

	@Override
	public void actualizar(DatosEmpresaDTO datosEmpresa) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setInt(1, datosEmpresa.getIdPostulante());
			ptmt.setString(2,datosEmpresa.getNombre());
			ptmt.setString(3,datosEmpresa.getCargo());
			ptmt.setString(4, datosEmpresa.getDireccion());
			ptmt.setString(5, datosEmpresa.getTelefono());
			ptmt.setInt(6, datosEmpresa.getId());
			
			ptmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} try {
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


