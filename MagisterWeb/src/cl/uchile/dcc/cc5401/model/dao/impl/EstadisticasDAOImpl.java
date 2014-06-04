package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cl.uchile.dcc.cc5401.model.dao.EstadisticasDAO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class EstadisticasDAOImpl implements EstadisticasDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;

	private static final String SQL_LISTA_POSTULACIONES = "SELECT fecha_ingreso, genero.nombre AS genero, tipo_financiamiento.tipo AS financiamiento, pais.nombre AS pais, tipo_resolucion.nombre AS resolucion " 
											 + "FROM (((((((postulacion INNER JOIN postulante ON postulacion.id_postulante=postulante.id) "
												+ "INNER JOIN financiamiento ON postulacion.id_financiamiento=financiamiento.id) "
												+ "INNER JOIN tipo_financiamiento ON tipo_financiamiento.id=financiamiento.tipo) "
												+ "INNER JOIN pais ON pais.id=postulante.nacionalidad) "
												+ "INNER JOIN genero ON genero.id=postulante.id_genero) "
												+ "LEFT JOIN resolucion ON resolucion.id_postulacion=postulacion.id) "
												+ "LEFT JOIN tipo_resolucion ON tipo_resolucion.id=resolucion.id_tipo_resolucion)";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	@Override
	/**
	 * Se crea un arreglo en formato JSON para incrustarlo en el html.
	 * */
	public String getListaPostulaciones() {
		
		try {
			String lista = "[  ";
			
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_LISTA_POSTULACIONES);
			
			resultSet = ptmt.executeQuery();
			
			while (resultSet.next()) {
				lista = lista + "{";
				
	            lista = lista + "'fecha_ingreso':'" + resultSet.getDate("fecha_ingreso").toString() + "', ";
	            lista = lista + "'genero':'" + resultSet.getString("genero") + "', ";
	            lista = lista + "'financiamiento':'" + resultSet.getString("financiamiento") + "', ";
	            lista = lista + "'pais':'" + resultSet.getString("pais") + "', ";
	            lista = lista + "'resolucion':'" + resultSet.getString("resolucion")+ "'";
	            
	            lista = lista + "}, ";
	        }
			// se quita coma extra.
			lista = lista.substring(0, lista.length()-2);
			lista = lista + "]";
			
			return lista;
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
