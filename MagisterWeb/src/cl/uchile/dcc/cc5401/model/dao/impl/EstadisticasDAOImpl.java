package cl.uchile.dcc.cc5401.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import cl.uchile.dcc.cc5401.model.dao.EstadisticasDAO;
import cl.uchile.dcc.cc5401.model.jdbc.ConnectionFactory;

public class EstadisticasDAOImpl implements EstadisticasDAO {

	private Connection connection = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultSet = null;

	private static final String SQL_N_MUJERES =
			"SELECT COUNT(*) AS Mujeres FROM postulante"
					+ " WHERE id_genero=2";

	private static final String SQL_N_HOMBRES =
			"SELECT COUNT(*) AS Hombres FROM postulante"
					+ " WHERE id_genero=1";

	private static final String SQL_N_FINANCIAMIENTO =
			"SELECT COUNT(*) AS Total FROM financiamiento"
					+ " WHERE tipo=?";

	private static final String SQL_N_POSTULACIONES_FECHA =
			"SELECT COUNT(*) AS nPostulaciones " 
					+ " FROM postulacion " 
					+ " WHERE YEAR(fecha_ingreso) = ? AND MONTH(fecha_ingreso) = ?";

	private static final String SQL_N_RESOLUCIONES_ESTADO =
			"SELECT COUNT(*) AS nResoluciones"
					+ " FROM resolucion"
					+ " WHERE id_tipo_resolucion=?";

	private static final String SQL_N_NACIONALES =
			"SELECT COUNT(*) AS nNacionales"
					+ " FROM postulante"
					+ " WHERE nacionalidad=152";

	private static final String SQL_N_EXTRANJEROS =
			"SELECT COUNT(*) AS nExtranjeros"
					+ " FROM postulante"
					+ " WHERE nacionalidad!=152";

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	@Override
	public HashMap<String, Integer> getNacionalVsExtranjeros() {
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_N_NACIONALES);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nNacionales = resultSet.getInt("nNacionales");
			ptmt = connection.prepareStatement(SQL_N_EXTRANJEROS);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nExtranjeros = resultSet.getInt("nExtranjeros");
			map.put("Nacionales", nNacionales);
			map.put("Extranjeros", nExtranjeros);
			return map;
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
	public HashMap<String, Integer> getPostulacionesAlMes(int fromMonth, int fromYear) {
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			connection = getConnection();
			Calendar c = Calendar.getInstance();
			int yearToday=c.get(Calendar.YEAR);
			int monthToday=c.get(Calendar.MONTH)+1;
			int currentYear = fromYear;
			int currentMonth = fromMonth;

			System.out.println(yearToday);
			System.out.println(monthToday);
			System.out.println(currentYear);
			System.out.println(currentMonth);
			
			while(true){
				if(currentYear>yearToday){
					break;
				}
				if(currentYear==yearToday){
					for(int m = currentMonth; m<=monthToday;m++){
						ptmt = connection.prepareStatement(SQL_N_POSTULACIONES_FECHA);
						ptmt.setInt(1, currentYear);
						ptmt.setInt(2, m);
						resultSet = ptmt.executeQuery();
						resultSet.first();
						int nPostulaciones = resultSet.getInt("nPostulaciones");
						map.put(currentYear+"-"+(m<10? "0"+ m : m), nPostulaciones);
					}
				}
				else{
					for(int m = currentMonth; m<=12;m++){
						ptmt = connection.prepareStatement(SQL_N_POSTULACIONES_FECHA);
						ptmt.setInt(1, currentYear);
						ptmt.setInt(2, m);
						resultSet = ptmt.executeQuery();
						resultSet.first();
						int nPostulaciones = resultSet.getInt("nPostulaciones");
						map.put(currentYear+"-"+(m<10? "0"+ m : m), nPostulaciones);
					}
				}
				currentMonth=0;
				currentYear++;
			}
			return map;
			
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
	public HashMap<String, Integer> getEstadisticaResolucion() {
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_N_RESOLUCIONES_ESTADO);
			ptmt.setInt(1, 1);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nAceptados = resultSet.getInt("nResoluciones");
			ptmt = connection.prepareStatement(SQL_N_RESOLUCIONES_ESTADO);
			ptmt.setInt(1, 2);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nRechazados = resultSet.getInt("nResoluciones");
			ptmt = connection.prepareStatement(SQL_N_RESOLUCIONES_ESTADO);
			ptmt.setInt(1, 3);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nAceptadosCond = resultSet.getInt("nResoluciones");


			map.put("Aceptados", nAceptados);
			map.put("Aceptados Condicional", nAceptadosCond);
			map.put("Rechazados", nRechazados);
			return map;

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
	public HashMap<String, Integer> getEstadisticaGenero() {
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_N_MUJERES);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nMujeres = resultSet.getInt("Mujeres");
			ptmt = connection.prepareStatement(SQL_N_HOMBRES);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nHombres = resultSet.getInt("Hombres");
			map.put("Mujeres", nMujeres);
			map.put("Hombres", nHombres);
			return map;
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
	public HashMap<String, Integer> getEstadisticaFinanciamiento() {
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			connection = getConnection();
			ptmt = connection.prepareStatement(SQL_N_FINANCIAMIENTO);
			ptmt.setInt(1, 1);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nParticular = resultSet.getInt("Total");
			ptmt = connection.prepareStatement(SQL_N_FINANCIAMIENTO);
			ptmt.setInt(1, 2);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nBeca = resultSet.getInt("Total");
			ptmt = connection.prepareStatement(SQL_N_FINANCIAMIENTO);
			ptmt.setInt(1, 3);
			resultSet = ptmt.executeQuery();
			resultSet.first();
			int nEmpresa = resultSet.getInt("Total");


			map.put("Particular", nParticular);
			map.put("Beca", nBeca);
			map.put("Empresa", nEmpresa);
			return map;

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
	public double getPromedioResolucion() {
		// TODO Auto-generated method stub
		return 0;
	}

}
