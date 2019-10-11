package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaMenuAdministrar extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	Logica logica = new Logica();
	
	/**
	 * Create the frame.
	 */
	public VentanaMenuAdministrar(Conexion conexion, Usuario user) {
		
		this.conexion=conexion;
		this.user=user;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaMenuAdministrar.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Men\u00FA Administraci\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 403);
		setSize(450, 403);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnCrearUsuario = new JButton("Administrar Usuarios");
		btnCrearUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnCrearUsuario.doClick();
				}
			}
		});
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCrearUsuario crearUsuario= new VentanaCrearUsuario(conexion,user);
				crearUsuario.setVisible(true);
				dispose();
			}
		});
		btnCrearUsuario.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrearUsuario.setBounds(10, 155, 176, 39);
		panel.add(btnCrearUsuario);
		
		JButton btnCrearOEliminar = new JButton("Administrar Productos");
		btnCrearOEliminar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnCrearOEliminar.doClick();
				}
			}
		});
		btnCrearOEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCrearProducto crearProducto= new VentanaCrearProducto(conexion,user);
				crearProducto.setVisible(true);
				dispose();
			}
		});
		btnCrearOEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrearOEliminar.setBounds(238, 155, 176, 39);
		panel.add(btnCrearOEliminar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaMenuAdministrar.class.getResource("/imagenes/Logo.png")));
		lblNewLabel.setBounds(118, 11, 185, 116);
		panel.add(lblNewLabel);
		
		JButton btnReportes = new JButton("Reportes");
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaReportes ventana= new VentanaReportes(conexion, user);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnReportes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnReportes.doClick();
				}
			}
		});
		btnReportes.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnReportes.setBounds(10, 304, 176, 39);
		panel.add(btnReportes);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnVolver.doClick();
				}
			}
		});
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaMenu menu=new VentanaMenu(conexion,user);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(238, 304, 176, 39);
		panel.add(btnVolver);
		
		JButton btnAdministrarProveedores = new JButton("Administrar Proveedores");
		btnAdministrarProveedores.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnAdministrarProveedores.doClick();
				}
			}
		});
		btnAdministrarProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCrearProveedorMenu ventanaProveedor = new VentanaCrearProveedorMenu(conexion, user);
				ventanaProveedor.setVisible(true);
				dispose();
			}
		});
		btnAdministrarProveedores.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		btnAdministrarProveedores.setBounds(10, 205, 176, 39);
		panel.add(btnAdministrarProveedores);
		
		JButton btnAdministrarClientes = new JButton("Administrar Clientes");
		btnAdministrarClientes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnAdministrarClientes.doClick();
				}
			}
		});
		btnAdministrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCrearClienteMenu ventanaCliente= new VentanaCrearClienteMenu(conexion, user);
				ventanaCliente.setVisible(true);
				dispose();
			}
		});
		btnAdministrarClientes.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnAdministrarClientes.setBounds(238, 205, 176, 39);
		panel.add(btnAdministrarClientes);
		
		JButton btnAjustarInventario = new JButton("Ajustar Inventario");
		btnAjustarInventario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnAjustarInventario.doClick();
				}
			}
		});
		btnAjustarInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAjuste ajuste= new VentanaAjuste(conexion, user);
				ajuste.setVisible(true);
				dispose();
			}
		});
		btnAjustarInventario.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnAjustarInventario.setBounds(238, 255, 176, 39);
		panel.add(btnAjustarInventario);
		
		JButton btnGastos = new JButton("Gastos Adicionales");
		btnGastos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnGastos.doClick();
				}
			}
		});
		btnGastos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaGastosAdicionales ventana= new VentanaGastosAdicionales(conexion, user);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnGastos.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnGastos.setBounds(10, 254, 176, 39);
		panel.add(btnGastos);
	}
}
