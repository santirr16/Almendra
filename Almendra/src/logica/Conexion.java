package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Conexion {
	String user;
	String pass;
	String url;
	private Connection conex;
	private Statement stm;

	public Conexion() {
		user = "almendra";
		pass = "almendra";
		url = "jdbc:oracle:thin:@localhost:1521:xe";
//		url = "jdbc:oracle:thin:@192.168.1.8:1521:xe";
		conex = null;
		stm = null;

		
	}

	public void conectar() {

		try {
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			conex = DriverManager.getConnection(url, user, pass);
			stm=conex.createStatement();
//			JOptionPane.showMessageDialog(null, "Conexon realizada");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se realizo la conexion");
		}

	}

//		public void crearTabla() throws SQLException {
//			stm = conex.createStatement();
//			int r=0;
//			String cadSql;
//			try {
//				cadSql = "create table ALUMNOS (ID VARCHAR2(2) PRIMARY KEY, NOMBRE VARCHAR2(20), EDAD NUMBER(2))";
//				
//				r=stm.executeUpdate(cadSql);
//				
//				JOptionPane.showMessageDialog(null, (r+1)+" tabla creada");
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "La tabla no se creo");
//			}
//
//		}

	public String leerDatosBD() throws SQLException {
		stm = conex.createStatement();
		String cadSql = "";
		try {
			cadSql = "select * from USUARIOS";
			ResultSet rs = stm.executeQuery(cadSql);
			String datos = "";
			while (rs.next()) {
				datos += (rs.getString("ID") + " " + rs.getString(2));
			}

			return datos;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "La tabla no existe");
			return null;
		}

	}

//
//		public void ingresarDatosBD(String id, String nombre, int edad)
//				throws SQLException {
//
//			int r;
//			String cadSql;
//			stm = conex.createStatement();
//
//			try {
//
//				cadSql = "insert into ALUMNOS values ('" + id + "', '" + nombre
//						+ "','" + edad + "')";
//				r = stm.executeUpdate(cadSql);
//				JOptionPane.showMessageDialog(null, r + " registro agregado");
//
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "Registro no agregado");
//			}
//
//		}
//
//	public boolean buscarBoolean(String consulta) throws SQLException {
//		stm = conex.createStatement();
//
//		try {
//			String nombre = "";
//			ResultSet rs = stm.executeQuery(consulta);
//			while (rs.next()) {
//
//				nombre = rs.getString("ID");
//
//			}
//			if (nombre.equals("")) {
//				return false;
//			} else {
//				return true;
//			}
//
//		} catch (Exception e) {
////				JOptionPane.showMessageDialog(null, "El usuario no existe");
//
//			return false;
//		}
//
//	}

//	public String buscarDatosBD(String usuario, String contraseña) throws SQLException {
//		stm = conex.createStatement();
//
//		String cadSql;
//
//		try {
//			String nombre = "";
//			cadSql = "select * from USUARIOS where USUARIO='" + usuario + "' and contraseña='" + contraseña + "'";
//			ResultSet rs = stm.executeQuery(cadSql);
//			while (rs.next()) {
//
//				nombre = rs.getString("USUARIO") + " " + rs.getString("CONTRASEÑA");
////							+ rs.getInt(3);
//
//			}
//			if (nombre.equals("")) {
//				JOptionPane.showMessageDialog(null, "El codigo no existe");
//			} else {
//				JOptionPane.showMessageDialog(null, "El usuario existe");
//			}
//			return nombre;
//
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "El codigo no existe");
//
//			return null;
//		}
//
//	}
//
//		public void eliminarDatos(String id) throws SQLException {
//			int r;
//			String cadSql;
//			stm = conex.createStatement();
//			try {
//				cadSql = "delete from ALUMNOS where ID='" + id + "'";
//				r = stm.executeUpdate(cadSql);
//				JOptionPane.showMessageDialog(null, r + " registro eliminado");
//				;
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "No se encontro el registro");
//			}
//		}
//
//		public void eliminarTabla() throws SQLException {
//			int r;
//			String cadSql;
//			stm = conex.createStatement();
//			try {
//				cadSql = "drop table ALUMNOS";
//				r = stm.executeUpdate(cadSql);
//				JOptionPane.showMessageDialog(null, (r + 1) + "tabla eliminada");
//				;
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "No se encontro la tabla");
//
//			}
//		}

	public Connection getConex() {
		return conex;
	}

	public Statement getStm() {
		return stm;
	}

	public void setStm(Statement stm) {
		this.stm = stm;
	}

	public void setConex(Connection conex) {
		this.conex = conex;
	}

}
