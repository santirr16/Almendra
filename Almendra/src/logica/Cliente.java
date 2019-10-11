package logica;

public class Cliente {

	private long identificador;
	private String nombre;
	private long telefono;

	public Cliente(long identificador, String nombre, long telefono) {
		this.identificador = identificador;
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

}
