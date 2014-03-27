package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PaisDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;
import cl.uchile.dcc.cc5401.util.Genero;

public class PostulanteDAOImpl implements PostulanteDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;

	private static final String SQL_INSERT =
			"INSERT INTO postulante ("
					+ "primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, id_identificacion, "
					+" nacionalidad, id_genero, fecha_nacimiento, telefono , celular , pais_residencia, email, direccion"
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_ID =
			"SELECT * "
					+ " FROM postulante po"
					+ " JOIN pais pa ON po.nacionalidad = pa.id"
					+ " JOIN pais pa2 ON po.pais_residencia = pa2.id"
					+ " JOIN identificacion i ON po.id_identificacion = i.id"
					+ " LEFT JOIN pais pa3 ON i.pais = pa3.id"
					+ " WHERE"
					+ " po.id = ?";
	private static final String SQL_SELECT_NAME =
			"SELECT * "
					+ " FROM postulante po"
					+ " JOIN pais pa ON po.nacionalidad = pa.id"
					+ " JOIN pais pa2 ON po.pais_residencia = pa2.id"
					+ " JOIN identificacion i ON po.id_identificacion = i.id"
					+ " LEFT JOIN pais pa3 ON i.pais = pa3.id"
					+ " WHERE primer_nombre = ?, segundo_nombre = ?, apellido_paterno = ?, apellido_materno = ?";
	private static final String SQL_SELECT_EMAIL =
			"SELECT * "
					+ " FROM postulante po"
					+ " JOIN pais pa ON po.nacionalidad = pa.id"
					+ " JOIN pais pa2 ON po.pais_residencia = pa2.id"
					+ " JOIN identificacion i ON po.id_identificacion = i.id"
					+ " LEFT JOIN pais pa3 ON i.pais = pa3.id"
					+ " WHERE email = ?";
	private static final String SQL_SELECT_All =
			"SELECT *"
					+ " FROM postulante po"
					+ " JOIN pais pa ON po.nacionalidad = pa.id"
					+ " JOIN pais pa2 ON po.pais_residencia = pa2.id"
					+ " JOIN identificacion i ON po.id_identificacion = i.id"
					+ " LEFT JOIN pais pa3 ON i.pais = pa3.id";
	private static final String SQL_UPDATE =
			"UPDATE postulante SET "
					+ "primer_nombre = ?, segundo_nombre = ?, apellido_paterno = ?, apellido_materno = ?, id_identificacion = ?,"
					+ " nacionalidad = ?, id_genero = ?, fecha_nacimiento = ?, telefono = ?, celular = ?, pais_residencia = ?, email = ?, direccion = ?"
					+ " WHERE "
					+ "id = ? ";
	private static final String SQL_LAST_ID =
			"SELECT MAX(id) AS id FROM postulante";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	private List<PostulanteDTO> getResults(ResultSet rs) throws SQLException {
		List<PostulanteDTO> results = new ArrayList<PostulanteDTO>();
		while (rs.next()) {
			PostulanteDTO postulante = new PostulanteDTO();
			postulante.setId(rs.getInt("id"));
			postulante.setPrimerNombre(rs.getString("primer_nombre"));
			postulante.setSegundoNombre(rs.getString("segundo_nombre"));
			postulante.setApellidoPaterno(rs.getString("apellido_paterno"));
			postulante.setApellidoMaterno(rs.getString("apellido_materno"));
			postulante.setDireccion(rs.getString("direccion"));
			postulante.setGenero(Genero.getValue(rs.getInt("id_genero")));
			postulante.setTelefono(rs.getString("telefono"));
			postulante.setCelular(rs.getString("celular"));
			postulante.setEmail(rs.getString("email"));
			postulante.setPaisResidencia(new PaisDTO(rs.getInt(17),rs.getString(18)));
			postulante.setNacimiento(rs.getDate("fecha_nacimiento"));
			postulante.setNacionalidad(new PaisDTO(rs.getInt(15),rs.getString(16)));
			IdentificacionDTO identificacion = new IdentificacionDTO(rs.getInt(19), rs.getString(20), null, true);
			if(!rs.getBoolean(21)){
				identificacion.setPais(new PaisDTO(rs.getInt(23), rs.getString(24)));
				identificacion.setEsRut(false);
			} 
			postulante.setIdentificacion(identificacion);
			results.add(postulante);

		}
		return results;
	}

	@Override
	public int agregar(PostulanteDTO postulante) {
		int id = 0;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setString(1, postulante.getPrimerNombre());
			if(postulante.getSegundoNombre() == null)
				ptmt.setNull(2, java.sql.Types.VARCHAR);
			else
				ptmt.setString(2, postulante.getSegundoNombre());
			ptmt.setString(3, postulante.getApellidoPaterno());
			if(postulante.getApellidoMaterno() == null)
				ptmt.setNull(4, java.sql.Types.VARCHAR);
			else
				ptmt.setString(4, postulante.getApellidoMaterno());
			ptmt.setInt(5, postulante.getIdentificacion().getId());
			ptmt.setInt(6, postulante.getNacionalidad().getId());
			ptmt.setInt(7, postulante.getGenero().getId());
			ptmt.setDate(8, new Date(postulante.getNacimiento().getTime()));
			ptmt.setString(9, postulante.getTelefono());
			ptmt.setString(10, postulante.getCelular());
			ptmt.setInt(11, postulante.getPaisResidencia().getId());
			ptmt.setString(12, postulante.getEmail());
			ptmt.setString(13, postulante.getDireccion());

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
	public void actualizar(PostulanteDTO postulante) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setString(1, postulante.getPrimerNombre());
			if(postulante.getSegundoNombre() == null)
				ptmt.setNull(2, java.sql.Types.VARCHAR);
			else
				ptmt.setString(2, postulante.getSegundoNombre());
			ptmt.setString(3, postulante.getApellidoPaterno());
			if(postulante.getApellidoMaterno() == null)
				ptmt.setNull(4, java.sql.Types.VARCHAR);
			else
				ptmt.setString(4, postulante.getApellidoMaterno());
			ptmt.setInt(5, postulante.getIdentificacion().getId());
			ptmt.setInt(6, postulante.getNacionalidad().getId());
			ptmt.setInt(7, postulante.getGenero().getId());
			ptmt.setDate(8, new Date(postulante.getNacimiento().getTime()));
			ptmt.setString(9, postulante.getTelefono());
			ptmt.setString(10, postulante.getCelular());
			ptmt.setInt(11, postulante.getPaisResidencia().getId());
			ptmt.setString(12, postulante.getEmail());
			ptmt.setString(13, postulante.getDireccion());
			ptmt.setInt(14, postulante.getId());

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
	public List<PostulanteDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_All);
			resultSet = ptmt.executeQuery();
			List<PostulanteDTO> results = getResults(resultSet);
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
	public PostulanteDTO get(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_ID);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			List<PostulanteDTO> results = getResults(resultSet);
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
	public PostulanteDTO getByName(String name) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_NAME);
			String separator = " ";
			String[] fullname = name.split(separator);
			ptmt.setString(1, fullname[0]);
			ptmt.setString(2, fullname[1]);
			ptmt.setString(3, fullname[2]);
			ptmt.setString(4, fullname[3]);
			resultSet = ptmt.executeQuery();
			List<PostulanteDTO> results = getResults(resultSet);
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
	public PostulanteDTO getByEmail(String email) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_EMAIL);
			ptmt.setString(1, email);
			resultSet = ptmt.executeQuery();
			List<PostulanteDTO> results = getResults(resultSet);
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
