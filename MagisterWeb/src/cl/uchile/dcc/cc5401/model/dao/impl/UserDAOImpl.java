package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;
import cl.uchile.dcc.cc5401.util.RolUsuario;

public class UserDAOImpl implements UserDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;

	private static final String SQL_INSERT = "INSERT INTO usuario ("
			+ " username, password, mail, id_rol " + ") VALUES (?,?,?,?)";

	private static final String SQL_SELECT_ID = "SELECT *"
			+ " FROM usuario us "
			+ " JOIN rol_permiso rp ON us.id_rol = rp.id_rol"
			+ " JOIN permiso pe ON rp.id_permiso = pe.id" + " WHERE us.id=?";

	private static final String SQL_SELECT_ALL_ID = "SELECT id"
			+ " FROM usuario";

	private static final String SQL_SELECT_COMISION = "SELECT id"
			+ " FROM usuario" + " WHERE id_rol=3 OR id_rol=2";

	private static final String SQL_SELECT_USERNAME = "SELECT *"
			+ " FROM usuario us " 
			+ " JOIN rol_permiso rp ON us.id_rol = rp.id_rol"
			+ " JOIN permiso pe ON rp.id_permiso = pe.id"
			+ " WHERE us.username=?";

	private static final String SQL_DELETE = "DELETE FROM usuario"
			+ " WHERE id=?";

	private static final String SQL_UPDATE = "UPDATE usuario SET"
			+ " username = ?, password = ?, mail = ?, id_rol = ? "
			+ "  WHERE id = ?";

	private static final String SQL_UPDATE_PASSWORD = "UPDATE usuario SET"
			+ " password = ? " + " WHERE id = ?";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	private UserDTO getResult(ResultSet rs) throws SQLException {
		UserDTO userDTO = new UserDTO();
		List<String> permisos = new ArrayList<String>();
		boolean empty = true;
		while (rs.next()) {
			empty = false;
			userDTO.setId(rs.getInt("id"));
			userDTO.setEmail(rs.getString("mail"));
			userDTO.setUsername(rs.getString("username"));
			userDTO.setPassword(rs.getString("password"));
			userDTO.setRol(RolUsuario.getValue(rs.getInt("id_rol")));
			permisos.add(rs.getString("permiso"));
		}
		if (empty)
			return null;
		userDTO.setPermisos(permisos);
		return userDTO;
	}

	@Override
	public UserDTO getUser(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_ID);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			UserDTO results = getResult(resultSet);
			if (results != null) {
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
	public UserDTO getUser(String username) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_USERNAME);
			ptmt.setString(1, username);
			resultSet = ptmt.executeQuery();
			UserDTO results = getResult(resultSet);
			if (results != null) {
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
	public void agregar(UserDTO user) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_INSERT);
			ptmt.setString(1, user.getUsername());
			ptmt.setString(2, user.getPassword());
			ptmt.setString(3, user.getEmail());
			ptmt.setInt(4, user.getRol().getId());
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
	public void eliminar(int id) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_DELETE);
			ptmt.setInt(1, id);
			ptmt.executeUpdate();
			System.out.println("Data deleted Successfully");
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
	public void actualizarPassword(UserDTO user) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE_PASSWORD);
			ptmt.setString(1, user.getPassword());
			ptmt.setInt(2, user.getId());
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
	public List<Integer> getComisionIds() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_COMISION);
			resultSet = ptmt.executeQuery();
			List<Integer> list = new ArrayList<Integer>();
			while (resultSet.next()) {
				list.add(resultSet.getInt("id"));
			}
			if (list.size() != 0) {
				return list;
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
	public List<UserDTO> getAll() {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_SELECT_ALL_ID);
			resultSet = ptmt.executeQuery();
			List<Integer> list = new ArrayList<Integer>();
			List<UserDTO> users = new ArrayList<UserDTO>();
			while (resultSet.next()) {
				list.add(resultSet.getInt("id"));
			}
			if (list.size() != 0) {
				for (int i : list) {
					users.add(this.getUser(i));
				}
				return users;
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
	public void actualizar(UserDTO user) {
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_UPDATE);
			ptmt.setString(1, user.getUsername());
			ptmt.setString(2, user.getPassword());
			ptmt.setString(3, user.getEmail());
			ptmt.setInt(4, user.getRol().getId());
			ptmt.setInt(5, user.getId());
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
