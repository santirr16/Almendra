package logica;

public class Producto {
	private long id;
	private String nombre;
	private long valorCosto;
	private long valorPublico;
	private long proveedor;
	private String descripcion;
	private int cantidad;

	public Producto(long id, String nombre, long valorCosto, long valorPublico, long proveedor, String descripcion,
			int cantidad) {
		this.id = id;
		this.nombre = nombre;
		this.valorCosto = valorCosto;
		this.valorPublico = valorPublico;
		this.proveedor = proveedor;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getValorCosto() {
		return valorCosto;
	}

	public void setValorCosto(long valorCosto) {
		this.valorCosto = valorCosto;
	}

	public long getValorPublico() {
		return valorPublico;
	}

	public void setValorPublico(long valorPublico) {
		this.valorPublico = valorPublico;
	}

	public long getProveedor() {
		return proveedor;
	}

	public void setProveedor(long proveedor) {
		this.proveedor = proveedor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
