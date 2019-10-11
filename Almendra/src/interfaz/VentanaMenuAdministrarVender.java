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

public class VentanaMenuAdministrarVender extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	Logica logica = new Logica();
	Long turno;
	
	/**
	 * Create the frame.
	 */
	public VentanaMenuAdministrarVender(Conexion conexion, Usuario user,Long turno) {
		
		this.conexion=conexion;
		this.user=user;
		this.turno=turno;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaMenuAdministrarVender.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Men\u00FA Administraci\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 403);
		setSize(450, 346);
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
				VentanaCrearProductoVender crearProducto= new VentanaCrearProductoVender(conexion,user,turno);
				crearProducto.setVisible(true);
				dispose();
			}
		});
		btnCrearOEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrearOEliminar.setBounds(10, 155, 176, 39);
		panel.add(btnCrearOEliminar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaMenuAdministrarVender.class.getResource("/imagenes/Logo.png")));
		lblNewLabel.setBounds(118, 11, 185, 116);
		panel.add(lblNewLabel);
		
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
				VentanaMenuVender menu=new VentanaMenuVender(conexion,user,turno);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(10, 255, 404, 39);
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
				VentanaCrearProveedorMenuVender ventanaProveedor = new VentanaCrearProveedorMenuVender(conexion, user,turno);
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
				VentanaCrearClienteMenuVender ventanaCliente= new VentanaCrearClienteMenuVender(conexion, user,turno);
				ventanaCliente.setVisible(true);
				dispose();
			}
		});
		btnAdministrarClientes.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnAdministrarClientes.setBounds(238, 155, 176, 39);
		panel.add(btnAdministrarClientes);
		
		JButton button = new JButton("Gastos Adicionales");
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					button.doClick();
				}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaGastosAdicionalesVender ventana= new VentanaGastosAdicionalesVender(conexion, user,turno);
				ventana.setVisible(true);
				dispose();
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		button.setBounds(238, 205, 176, 39);
		panel.add(button);
	}
}
