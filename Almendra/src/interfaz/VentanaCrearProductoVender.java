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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.Conexion;
import logica.Logica;
import logica.Producto;
import logica.Usuario;

public class VentanaCrearProductoVender extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	Conexion conexion;
	Usuario user;
	private JTextField textField_5;
	Logica logica = new Logica();
	long turno;

	/**
	 * Create the frame.
	 */
	public VentanaCrearProductoVender(Conexion conexion, Usuario user,long turno) {

		this.conexion = conexion;
		this.user = user;
		this.turno=turno;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaCrearProductoVender.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Crear o Editar Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 325);
		setSize(450, 325);
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

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblNombre.setBounds(10, 60, 48, 14);
		panel.add(lblNombre);

		JLabel lblValorCosto = new JLabel("Valor Costo");
		lblValorCosto.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblValorCosto.setBounds(10, 98, 74, 14);
		panel.add(lblValorCosto);

		JLabel lblIdentificador = new JLabel("C\u00F3digo");
		lblIdentificador.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblIdentificador.setBounds(10, 22, 74, 14);
		panel.add(lblIdentificador);

		JLabel lblValorPublico = new JLabel("Valor P\u00FAblico");
		lblValorPublico.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblValorPublico.setBounds(10, 137, 74, 14);
		panel.add(lblValorPublico);

		JButton btnCrear = new JButton("Crear");
		btnCrear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnCrear.doClick();
				}
			}
		});
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long identificador = Long.parseLong(textField.getText());
					String nombre = textField_1.getText();
					int valorCosto = Integer.parseInt(textField_2.getText());
					int valorPublico = Integer.parseInt(textField_3.getText());
					Long proveedor = Long.parseLong(textField_5.getText());
					String descripcion = textField_4.getText();
					if (!nombre.equals("")) {
						if (logica.buscarProveedor(proveedor, conexion) != null) {
							logica.crearProducto(identificador, nombre, valorCosto, valorPublico, proveedor,
									descripcion, user.getUsuario(), conexion);
						} else {
							JOptionPane.showMessageDialog(null, "El proveedor no existe por favor creelo primero");
							VentanaCrearProveedor ventanaProveedor = new VentanaCrearProveedor(conexion, user);
							ventanaProveedor.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Por favor complete la información");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null,
							"Los campos identificador, valor costo y valor publico son numericos");
				}
			}
		});
		btnCrear.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrear.setBounds(67, 244, 95, 23);
		panel.add(btnCrear);

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
				VentanaMenuAdministrarVender menu = new VentanaMenuAdministrarVender(conexion, user,turno);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(282, 244, 89, 23);
		panel.add(btnVolver);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnEditar.doClick();
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long identificador = Long.parseLong(textField.getText());
					String nombre = textField_1.getText();
					int valorCosto = Integer.parseInt(textField_2.getText());
					int valorPublico = Integer.parseInt(textField_3.getText());
					long proveedor = Long.parseLong(textField_5.getText());
					String descripcion = textField_4.getText();
					if (!nombre.equals("")) {
						if (logica.buscarProveedor(proveedor, conexion) != null) {
							logica.editarProducto(identificador, nombre, valorCosto, valorPublico, proveedor,
									descripcion, user.getUsuario(), conexion);
						} else {
							JOptionPane.showMessageDialog(null, "El proveedor no existe por favor creelo primero");
							VentanaCrearProveedor ventanaProveedor = new VentanaCrearProveedor(conexion, user);
							ventanaProveedor.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Por favor complete la información");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null,
							"Los campos identificador, valor costo y valor publico son numericos");
				}
			}
		});
		btnEditar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnEditar.setBounds(172, 244, 100, 23);
		panel.add(btnEditar);

//		JButton btnEliminar = new JButton("Eliminar");
//		btnEliminar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				char tecla = e.getKeyChar();
//				if (tecla == KeyEvent.VK_ENTER) {
//					btnEliminar.doClick();
//				}
//			}
//		});
//		btnEliminar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					int identificador = Integer.parseInt(textField.getText());
//					logica.eliminarProducto(identificador, conexion);
//				} catch (NumberFormatException e1) {
//					JOptionPane.showMessageDialog(null,
//							"Los campos identificador, valor costo y valor publico son numericos");
//				}
//			}
//		});
//		btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
//		btnEliminar.setBounds(226, 244, 89, 23);
//		panel.add(btnEliminar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(VentanaCrearProductoVender.class.getResource("/imagenes/Logo.png")));
		label.setBounds(229, 49, 185, 116);
		panel.add(label);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					try {
						Producto producto = logica.buscarProducto(Long.parseLong(textField.getText()), conexion);
						if (producto != null) {
							textField_1.setText(producto.getNombre());
							textField_2.setText(producto.getValorCosto() + "");
							textField_3.setText(producto.getValorPublico() + "");
							textField_4.setText(producto.getDescripcion());
							textField_5.setText(producto.getProveedor() + "");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Verifique la información ingresada");
					}
				}
			}
		});
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField.setBounds(94, 20, 122, 20);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_1.setBounds(94, 57, 122, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_2.setBounds(94, 96, 122, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_3.setBounds(94, 134, 122, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_4.setBounds(94, 210, 320, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblDescripcion.setBounds(10, 212, 74, 14);
		panel.add(lblDescripcion);

		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblProveedor.setBounds(10, 176, 74, 14);
		panel.add(lblProveedor);

		textField_5 = new JTextField();
		textField_5.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_5.setColumns(10);
		textField_5.setBounds(94, 171, 122, 20);
		panel.add(textField_5);
	}
}
