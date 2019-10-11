package logica;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author santi
 *
 */
public class Logica {

	public Usuario login(String usuario, String contraseña, Conexion conexion) throws SQLException {
		String consulta = "Select * from usuarios where usuario=" + "'" + usuario + "'" + " and contraseña=" + "'"
				+ contraseña + "'";
		Usuario user = null;
		try {
			String nombre = "";
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			while (rs.next()) {
				nombre = rs.getString("ID");
				user = new Usuario(rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("USUARIO"),
						rs.getInt("CEDULA"), rs.getInt("ROL"), rs.getString("CONTRASEÑA"));
			}
			return user;

		} catch (Exception e) {
			return null;
		}
	}

	public void crearUsuario(long cedula, String nombre, String apellido, String usuario, String contraseña, int rol,
			Conexion conexion) {
		String consulta = "insert into usuarios (cedula,nombre,apellido,usuario,contraseña,rol) values (" + cedula
				+ ",'" + nombre + "','" + apellido + "','" + usuario + "','" + contraseña + "'," + rol + ")";
		try {
			if (buscarUsuario(cedula, conexion) == null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Usuario creado");
			} else {
				JOptionPane.showMessageDialog(null, "El usuario ya existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Usuario no creado");
		}
	}

	/**
	 * @param cedula
	 * @param conexion
	 * @return
	 */
	public Usuario buscarUsuario(long cedula, Conexion conexion) {

		String consulta = "select * from usuarios where cedula=" + cedula;
		Usuario user = null;
		try {
			String nombre = "";
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			while (rs.next()) {
				nombre = rs.getString("ID");
				user = new Usuario(rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("USUARIO"),
						rs.getLong("CEDULA"), rs.getInt("ROL"), rs.getString("CONTRASEÑA"));
			}
			return user;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param usuario
	 * @param contraseña
	 * @param rol
	 * @param conexion
	 */
	public void editarUsuario(long cedula, String nombre, String apellido, String usuario, String contraseña, int rol,
			Conexion conexion) {
		String consulta;
		try {
			Usuario user = buscarUsuario(cedula, conexion);
			if (user != null) {
				if (contraseña.equals("")) {
					consulta = "update usuarios set nombre='" + nombre + "', apellido='" + apellido + "', usuario='"
							+ usuario + "',contraseña='" + user.getContraseña() + "', rol=" + rol + "where cedula="
							+ cedula;
					conexion.getStm().executeUpdate(consulta);
					JOptionPane.showMessageDialog(null, "Usuario editado");
				} else {
					consulta = "update usuarios set nombre='" + nombre + "', apellido='" + apellido + "', usuario='"
							+ usuario + "',contraseña='" + contraseña + "', rol=" + rol + "where cedula=" + cedula;
					conexion.getStm().executeUpdate(consulta);
					JOptionPane.showMessageDialog(null, "Usuario editado");
				}
			} else {
				JOptionPane.showMessageDialog(null, "El usuario no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Usuario no editado");
		}
	}

	public void eliminarUsuario(long cedula, Conexion conexion) {
		String consulta = "delete from usuarios where cedula=" + cedula;
		try {
			if (buscarUsuario(cedula, conexion) != null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "El usuario no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Usuario no eliminado");
		}
	}

	public void crearProducto(long identificador, String nombre, int valorCosto, int valorPublico, Long proveedor,
			String descripcion, String usuario, Conexion conexion) {
		String consulta = "insert into producto (id,nombre,valorcosto,valorpublico,descripcion,usuario,proveedor,cantidad) values ("
				+ identificador + ",'" + nombre + "'," + valorCosto + "," + valorPublico + ",'" + descripcion + "','"
				+ usuario + "'," + proveedor + ",0)";
		try {
			if (buscarProducto(identificador, conexion) == null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Producto creado");
			} else {
				JOptionPane.showMessageDialog(null, "El producto ya existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Producto no creado");
		}
	}

	/**
	 * @param cedula
	 * @param conexion
	 * @return
	 */
	public Producto buscarProducto(long identificador, Conexion conexion) {

		String consulta = "select * from producto where id=" + identificador;
		Producto producto = null;
		try {
			String nombre = "";
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			while (rs.next()) {
				nombre = rs.getString("ID");
				producto = new Producto(rs.getLong("ID"), rs.getString("NOMBRE"), rs.getInt("VALORCOSTO"),
						rs.getInt("VALORPUBLICO"), rs.getLong("PROVEEDOR"), rs.getString("DESCRIPCION"),
						rs.getInt("CANTIDAD"));
			}
			return producto;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param usuario
	 * @param contraseña
	 * @param rol
	 * @param conexion
	 */
	public void editarProducto(long identificador, String nombre, double valorCosto, double valorPublico, long proveedor,
			String descripcion, String usuario, Conexion conexion) {

		String consulta = "update producto set nombre='" + nombre + "', valorcosto=" + valorCosto + ", valorpublico="
				+ valorPublico + ",usuario='" + usuario + "', descripcion='" + descripcion + "',proveedor=" + proveedor + " where id="
				+ identificador;
		try {
			if (buscarProducto(identificador, conexion) != null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Producto editado");
			} else {
				JOptionPane.showMessageDialog(null, "El producto no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Producto no editado");
		}
	}

	public void editarCantidadProducto(long identificador, int cantidad, String usuario, Conexion conexion) {

		String consulta = "update producto set usuario='" + usuario + "', cantidad=" + cantidad + " where id="
				+ identificador;
		try {
			if (buscarProducto(identificador, conexion) != null) {
				conexion.getStm().executeUpdate(consulta);
			} else {
				JOptionPane.showMessageDialog(null, "El producto no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarProducto(long identificador, Conexion conexion) {
		String consulta = "delete from producto where id=" + identificador;
		try {
			if (buscarProducto(identificador, conexion) != null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
			} else {
				JOptionPane.showMessageDialog(null, "El producto no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Producto no eliminado");
		}
	}

	public void crearProveedor(long identificador, String nombre, long telefono, String direccion, String correo,
			Conexion conexion) {
		String consulta = "insert into proveedor (identificador,nombre,telefono,direccion,correoelectronico) values ("
				+ identificador + ",'" + nombre + "'," + telefono + ",'" + direccion + "','" + correo + "')";
		try {
			if (buscarProveedor(identificador, conexion) == null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Proveedor creado");
			} else {
				JOptionPane.showMessageDialog(null, "El proveedor ya existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Proveedor no creado");
		}
	}

	/**
	 * @param cedula
	 * @param conexion
	 * @return
	 */
	public Proveedor buscarProveedor(long identificador, Conexion conexion) {

		String consulta = "select * from proveedor where identificador=" + identificador;
		Proveedor proveedor = null;
		try {
			String nombre = "";
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			while (rs.next()) {
				nombre = rs.getString("IDENTIFICADOR");
				proveedor = new Proveedor(rs.getLong("IDENTIFICADOR"), rs.getString("NOMBRE"), rs.getLong("TELEFONO"),
						rs.getString("DIRECCION"), rs.getString("CORREOELECTRONICO"));
			}
			return proveedor;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param usuario
	 * @param contraseña
	 * @param rol
	 * @param conexion
	 */
	public void editarProveedor(long identificador, String nombre, long telefono, String direccion, String correo,
			Conexion conexion) {

		String consulta = "update proveedor set nombre='" + nombre + "', telefono=" + telefono + ", direccion='"
				+ direccion + "',correoelectronico='" + correo + "' where identificador=" + identificador;
		try {
			if (buscarProveedor(identificador, conexion) != null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Producto editado");
			} else {
				JOptionPane.showMessageDialog(null, "El producto no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Producto no editado");
		}
	}

	public void crearCliente(long identificador, String nombre, long telefono, Conexion conexion) {
		String consulta = "insert into cliente (identificador,nombre,telefono) values (" + identificador + ",'" + nombre
				+ "'," + telefono + ")";
		try {
			if (buscarCliente(identificador, conexion) == null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Cliente creado");
			} else {
				JOptionPane.showMessageDialog(null, "El cliente ya existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cliente no creado");
		}
	}

	/**
	 * @param cedula
	 * @param conexion
	 * @return
	 */
	public Cliente buscarCliente(long identificador, Conexion conexion) {

		String consulta = "select * from cliente where identificador=" + identificador;
		Cliente cliente = null;
		try {
			String nombre = "";
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			while (rs.next()) {
				nombre = rs.getString("NOMBRE");
				cliente = new Cliente(rs.getLong("IDENTIFICADOR"), rs.getString("NOMBRE"), rs.getLong("TELEFONO"));
			}
			return cliente;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param usuario
	 * @param contraseña
	 * @param rol
	 * @param conexion
	 */
	public void editarCliente(long identificador, String nombre, long telefono, Conexion conexion) {

		String consulta = "update cliente set nombre='" + nombre + "', telefono=" + telefono + " where identificador="
				+ identificador;
		try {
			if (buscarCliente(identificador, conexion) != null) {
				conexion.getStm().executeUpdate(consulta);
				JOptionPane.showMessageDialog(null, "Cliente editado");
			} else {
				JOptionPane.showMessageDialog(null, "El cliente no existe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cliente no editado");
		}
	}

	public long obtenerIdentificadorVenta(Conexion conexion) {
		String consulta = "select max (IDENTIFICADOR) as identificador from venta";
		long indentificador = -1;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					indentificador = rs.getLong("IDENTIFICADOR") + 1;
				}
			}
			return indentificador;
		} catch (Exception e) {
			e.printStackTrace();
			return indentificador;
		}
	}

	public long obtenerIdentificadorCompra(Conexion conexion) {
		String consulta = "select max (IDENTIFICADOR) as identificador from compra";
		long indentificador = -1;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					indentificador = rs.getLong("IDENTIFICADOR") + 1;
				}
			}
			return indentificador;
		} catch (Exception e) {
			e.printStackTrace();
			return indentificador;
		}
	}

	public long registrarVenta(long cliente, String usuario, String fecha, long valorTotal, long turno,
			Conexion conexion) {
		long identificador = obtenerIdentificadorVenta(conexion);
		String consulta = "insert into venta (identificador,cliente,usuario,fecha,valortotal,turno) values ("
				+ identificador + ", " + cliente + ",'" + usuario + "','" + fecha + "'," + valorTotal + "," + turno
				+ ")";
		try {
			conexion.getStm().executeQuery(consulta);
			JOptionPane.showMessageDialog(null, "Venta Registrada");
			return identificador;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Venta no registrada");
			return (long) -1;
		}
	}

	public void registrarDetalleVenta(long cliente, long producto, String usuario, long valorVenta, long valorUnitario,
			long venta, int cantidad, Conexion conexion) {
		String consulta = "insert into detalleventa (cliente,producto,usuario,valorventa,valorunitario,venta,cantidad) values ("
				+ cliente + ", " + producto + ",'" + usuario + "'," + valorVenta + "," + valorUnitario + "," + venta
				+ "," + cantidad + ")";
		try {
			conexion.getStm().executeQuery(consulta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long registrarCompra(long proveedor, String usuario, String fecha, long valorTotal, long turno,
			Conexion conexion) {
		long identificador = obtenerIdentificadorCompra(conexion);
		String consulta = "insert into compra (identificador,proveedor,usuario,fecha,valortotal,turno) values ("
				+ identificador + ", " + proveedor + ",'" + usuario + "','" + fecha + "'," + valorTotal + "," + turno
				+ ")";
		try {
			conexion.getStm().executeQuery(consulta);
			JOptionPane.showMessageDialog(null, "Compra Registrada");
			return identificador;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Compra no registrada");
			return (long) -1;
		}
	}

	public void registrarDetalleCompra(long proveedor, long producto, String usuario, long valorCompra,
			long valorUnitario, long compra, int cantidad, Conexion conexion) {
		String consulta = "insert into detallecompra (proveedor,producto,usuario,valorcompra,valorunitario,compra,cantidad) values ("
				+ proveedor + ", " + producto + ",'" + usuario + "'," + valorCompra + "," + valorUnitario + "," + compra
				+ "," + cantidad + ")";
		try {
			conexion.getStm().executeQuery(consulta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long obtenerIdentificadorGasto(Conexion conexion) {
		String consulta = "select max (id) as id from gasto";
		long indentificador = -1;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					indentificador = rs.getLong("id") + 1;
				}
			}
			return indentificador;

		} catch (Exception e) {
			e.printStackTrace();
			return indentificador;
		}
	}

	public void ingresarGasto(String descripcion, int cantidad, int valorTotal, String usuario, Conexion conexion) {
		long identificador = obtenerIdentificadorGasto(conexion);
		SimpleDateFormat d = new SimpleDateFormat("dd/MM/yy");
		String fecha = d.format(new Date());
		String consulta = "insert into gasto (id,descripcion,cantidad,valortotal,usuario,fecha) values ("
				+ identificador + ", '" + descripcion + "'," + cantidad + "," + valorTotal + ",'" + usuario + "','"
				+ fecha + "')";
		try {
			conexion.getStm().executeQuery(consulta);
			JOptionPane.showMessageDialog(null, "Gasto Ingresado Correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Gasto no Ingresado");

		}
	}
	
	public long abrirCaja(String usuario, Conexion conexion) {
		long identificador = obtenerIdentificadorCaja(conexion);
		SimpleDateFormat d = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat h = new SimpleDateFormat("HH:mm:ss");
		String fecha = d.format(new Date());
		String hora = h.format(new Date());
		String consulta = "insert into turno (id,usuario,fechainicio,horainicio,estado) values (" + identificador + ",'"
				+ usuario + "','" + fecha + "','" + hora + "',1)";
		try {
			conexion.getStm().executeQuery(consulta);
			return identificador;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No fue posible abrir la caja");
			return (long) -1;
		}
	}

	public long buscarCajaAbieta(String usuario, Conexion conexion) {
		String consulta = "select id from turno where usuario='" + usuario + "' and estado=1";
		long indentificador = -1;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					indentificador = rs.getLong("id");
				}
			}
			return indentificador;
		} catch (Exception e) {
			e.printStackTrace();
			return indentificador;
		}
	}

	public long obtenerIdentificadorCaja(Conexion conexion) {
		String consulta = "select max (id) as id from turno";
		long indentificador = -1;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					indentificador = rs.getLong("id") + 1;
				}
			}
			return indentificador;
		} catch (Exception e) {
			e.printStackTrace();
			return indentificador;
		}
	}

	public void cerrarCaja(long identificador, long valorCompra, long valorVenta, long totalDinero, Conexion conexion) {
		SimpleDateFormat d = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat h = new SimpleDateFormat("HH:mm:ss");
		String fecha = d.format(new Date());
		String hora = h.format(new Date());
		String consulta = "update turno set valorcompra=" + valorCompra + ", valorventa=" + valorVenta
				+ ", totaldinero=" + totalDinero + ", fechafin='" + fecha + "', horafin='" + hora
				+ "', estado=2 where id=" + identificador;
		try {
			conexion.getStm().executeUpdate(consulta);
			JOptionPane.showMessageDialog(null, "Cerro caja exitosamente");

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No se cerro la caja");
		}
	}

	public long obtenerValorCompra(Conexion conexion, long turno) {
		String consulta = "select sum(valortotal) as compra from compra where turno=" + turno;
		long valorCompra = 0;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					valorCompra = rs.getLong("compra");
				}
			}
			return valorCompra;
		} catch (Exception e) {
			e.printStackTrace();
			return valorCompra;
		}
	}

	public long obtenerValorVenta(Conexion conexion, long turno) {
		String consulta = "select sum(valortotal) as venta from venta where turno=" + turno;
		long valorCompra = 0;
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					valorCompra = rs.getLong("venta");
				}
			}
			return valorCompra;
		} catch (Exception e) {
			e.printStackTrace();
			return valorCompra;
		}
	}

	public void exportarExcel(String rutaArchivo, String[] encabezado, ArrayList<ArrayList> datos) {
//		String rutaArchivo = System.getProperty("user.home") + "/ejemploExcelJava.xls";
//Con System.getProperty("user.home") obtenemos el directorio de trabajo del usuario actual, dentro del mismo crearemos el archivo de nombre "ejemploExcelJava.xls".

//Una vez hecho esto creamos un objeto de tipo File para que contenga el archivo...
		File archivoXLS = new File(rutaArchivo);
//...verificamos si existe dentro del sistema y de ser así lo eliminamos para crear una nueva copia de trabajo...
		if (archivoXLS.exists())
			archivoXLS.delete();
		try {
			archivoXLS.createNewFile();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
//Ya con el archivo físico creado y disponible comenzamos con el trabajo específico de Excel, para ello creamos el libro con el objeto Workbook:
		Workbook libro = new HSSFWorkbook();
//Posteriormente inicializamos el flujo de datos con el archivo que creamos previamente...
		FileOutputStream archivo;
		try {
			archivo = new FileOutputStream(archivoXLS);
//...y creamos la hoja dentro del libro dándole el nombre de "Mi hoja de trabajo 1"...
			Sheet hoja = libro.createSheet("Inventario");
//Inicializamos entonces un ciclo que haga 10 iteraciones...
			for (int f = 0; f <= datos.size(); f++) {
//Dentro del mismo creamos la primera fila con el objeto Row...
				Row fila = hoja.createRow(f);
//Ya con la fila creada comenzaremos a agregar las celdas, 5 por cada fila...
				for (int c = 0; c < encabezado.length; c++) {
					Cell celda = fila.createCell(c);
//Posteriormente validamos si la fila es la fila 0 entonces corresponde al encabezado, de lo contrario escribimos los datos necesarios...
					if (f == 0) {
						celda.setCellValue(encabezado[c]);
					} else {
						celda.setCellValue(datos.get(f - 1).get(c) + "");
					}
				}
			}
//Al finalizar ambos ciclos escribimos el archivo...
			try {
				libro.write(archivo);
//...y cerramos el flujo de datos...
				archivo.close();
//				Por último utilizamos la clase Desktop para visualizar el archivo con Excel...
				Desktop.getDesktop().open(archivoXLS);
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList> obtenerInventario(Conexion conexion) {
		String consulta = "select pr.id, pr.nombre, pr.descripcion, p.nombre as nproveedor, pr.proveedor, pr.usuario, pr.cantidad, pr.valorcosto,"
				+ " pr.valorpublico from producto pr join proveedor p on pr.proveedor=P.identificador";
		ArrayList<String> fila;
		ArrayList<ArrayList> resultado = new ArrayList<ArrayList>();
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					fila = new ArrayList<String>();
					fila.add(rs.getString("id"));
					fila.add(rs.getString("nombre"));
					fila.add(rs.getString("descripcion"));
					fila.add(rs.getString("nproveedor"));
					fila.add(rs.getString("proveedor"));
					fila.add(rs.getString("usuario"));
					fila.add(rs.getString("cantidad"));
					fila.add(rs.getString("valorcosto"));
					fila.add(rs.getString("valorpublico"));
					resultado.add(fila);
				}
			}
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<ArrayList> obtenerVentas(Conexion conexion, String fechaInicial, String fechaFinal, long cliente) {
		String consulta;
		if (cliente == -1) {
			consulta = "select v.identificador,v.FECHA,v.usuario,d.PRODUCTO,p.NOMBRE,p.DESCRIPCION,d.CANTIDAD,d.VALORUNITARIO,"
					+ "d.VALORVENTA,v.CLIENTE,c.NOMBRE as nombrecliente from venta v join detalleventa d on v.IDENTIFICADOR=d.VENTA "
					+ "join PRODUCTO p on d.PRODUCTO=p.id join CLIENTE c on v.CLIENTE=c.IDENTIFICADOR "
					+ "where fecha between '" + fechaInicial + "' and '" + fechaFinal + "' order by fecha";
		} else {
			consulta = "select v.identificador,v.FECHA,v.usuario,d.PRODUCTO,p.NOMBRE,p.DESCRIPCION,d.CANTIDAD,d.VALORUNITARIO,"
					+ "d.VALORVENTA,v.CLIENTE,c.NOMBRE as nombrecliente from venta v join detalleventa d on v.IDENTIFICADOR=d.VENTA "
					+ "join PRODUCTO p on d.PRODUCTO=p.id join CLIENTE c on v.CLIENTE=c.IDENTIFICADOR "
					+ "where fecha between '" + fechaInicial + "' and '" + fechaFinal + "' and v.cliente=" + cliente
					+ "order by fecha";
		}
		ArrayList<String> fila;
		ArrayList<ArrayList> resultado = new ArrayList<ArrayList>();
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					fila = new ArrayList<String>();
					fila.add(rs.getString("identificador"));
					fila.add(rs.getString("fecha"));
					fila.add(rs.getString("usuario"));
					fila.add(rs.getString("producto"));
					fila.add(rs.getString("nombre"));
					fila.add(rs.getString("descripcion"));
					fila.add(rs.getString("cantidad"));
					fila.add(rs.getString("valorunitario"));
					fila.add(rs.getString("valorventa"));
					fila.add(rs.getString("cliente"));
					fila.add(rs.getString("nombrecliente"));
					resultado.add(fila);
				}
			}
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<ArrayList> obtenerCompras(Conexion conexion, String fechaInicial, String fechaFinal,
			long proveedor) {
		String consulta;
		if (proveedor == -1) {
			consulta = "select v.identificador,v.FECHA,v.usuario,d.PRODUCTO,p.NOMBRE,p.DESCRIPCION,d.CANTIDAD,d.VALORUNITARIO,"
					+ "d.VALORCOMPRA,v.PROVEEDOR,c.NOMBRE as nombreproveedor from compra v join detallecompra d on v.IDENTIFICADOR=d.COMPRA"
					+ " join PRODUCTO p on d.PRODUCTO=p.id join PROVEEDOR c on d.PROVEEDOR=c.IDENTIFICADOR"
					+ " where fecha between '" + fechaInicial + "' and '" + fechaFinal + "' order by fecha";
		} else {
			consulta = "select v.identificador,v.FECHA,v.usuario,d.PRODUCTO,p.NOMBRE,p.DESCRIPCION,d.CANTIDAD,d.VALORUNITARIO,"
					+ "d.VALORCOMPRA,v.PROVEEDOR,c.NOMBRE as nombreproveedor from compra v join detallecompra d on v.IDENTIFICADOR=d.COMPRA"
					+ " join PRODUCTO p on d.PRODUCTO=p.id join PROVEEDOR c on d.PROVEEDOR=c.IDENTIFICADOR"
					+ " where fecha between '" + fechaInicial + "' and '" + fechaFinal + "' and v.cliente=" + proveedor
					+ "order by fecha";
		}
		ArrayList<String> fila;
		ArrayList<ArrayList> resultado = new ArrayList<ArrayList>();
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					fila = new ArrayList<String>();
					fila.add(rs.getString("identificador"));
					fila.add(rs.getString("fecha"));
					fila.add(rs.getString("usuario"));
					fila.add(rs.getString("producto"));
					fila.add(rs.getString("nombre"));
					fila.add(rs.getString("descripcion"));
					fila.add(rs.getString("cantidad"));
					fila.add(rs.getString("valorunitario"));
					fila.add(rs.getString("valorcompra"));
					fila.add(rs.getString("proveedor"));
					fila.add(rs.getString("nombreproveedor"));
					resultado.add(fila);
				}
			}
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<ArrayList> obtenerGastos(Conexion conexion,String fechaInicial, String fechaFinal) {
		String consulta = "select * from gasto where fecha between '" + fechaInicial + "' and '" + fechaFinal + "' order by fecha";
		ArrayList<String> fila;
		ArrayList<ArrayList> resultado = new ArrayList<ArrayList>();
		try {
			ResultSet rs = conexion.getStm().executeQuery(consulta);
			if (rs != null) {
				while (rs.next()) {
					fila = new ArrayList<String>();
					fila.add(rs.getString("id"));
					fila.add(rs.getString("descripcion"));
					fila.add(rs.getString("cantidad"));
					fila.add(rs.getString("valortotal"));
					fila.add(rs.getString("usuario"));
					fila.add(rs.getString("fecha"));
					resultado.add(fila);
				}
			}
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
//
//	public void crearTabla(Conexion conexion) throws SQLException {
//		int r = 0;
//		String cadSql;
//		try {
//			cadSql = "CREATE TABLE GASTO (ID NUMBER NOT NULL ENABLE, DESCRIPCION VARCHAR2(50 BYTE) NOT NULL ENABLE, CANTIDAD NUMBER NOT NULL ENABLE, "
//					+ "VALORTOTAL NUMBER NOT NULL ENABLE, USUARIO VARCHAR2(20 BYTE) NOT NULL ENABLE, FECHA DATE NOT NULL ENABLE, "
//					+ "CONSTRAINT GASTO_PK PRIMARY KEY (ID))";
//
//			r = conexion.getStm().executeUpdate(cadSql);
//
//			JOptionPane.showMessageDialog(null, (r + 1) + " tabla creada");
//		} catch (Exception e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "La tabla no se creo");
//		}
//	}
}
