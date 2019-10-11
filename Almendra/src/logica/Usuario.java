package logica;

public class Usuario {
	private String nombre;
	private String apellidos;
	private String usuario;
	private long cedula;
	private int rol;
	private String contraseña;
	
	public Usuario(String nombre, String apellidos, String usuario, long cedula, int rol, String contraseña) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.cedula = cedula;
		this.rol = rol;
		this.contraseña=contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public long getCedula() {
		return cedula;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	
	
}
