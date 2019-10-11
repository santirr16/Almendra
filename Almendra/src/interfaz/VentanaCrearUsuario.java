package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaCrearUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField textField_4;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	Conexion conexion;
	Usuario user;
	Logica logica = new Logica();

	/**
	 * Create the frame.
	 */
	public VentanaCrearUsuario(Conexion conexion, Usuario user) {

		this.conexion = conexion;
		this.user = user;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaCrearUsuario.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Crear, Editar o Eliminar Usuario");
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
		lblNombre.setBounds(10, 50, 48, 14);
		panel.add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblApellidos.setBounds(10, 89, 74, 14);
		panel.add(lblApellidos);

		JLabel lblCedula = new JLabel("Cedula");
		lblCedula.setFont(new Font("Comic Sans MS+", Font.BOLD, 12));
		lblCedula.setBounds(10, 13, 48, 14);
		panel.add(lblCedula);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblUsuario.setBounds(10, 128, 48, 14);
		panel.add(lblUsuario);

		JRadioButton rdbtnAdministrador = new JRadioButton("Administrador");
		rdbtnAdministrador.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		buttonGroup.add(rdbtnAdministrador);
		rdbtnAdministrador.setBackground(Color.WHITE);
		rdbtnAdministrador.setBounds(50, 203, 109, 23);
		panel.add(rdbtnAdministrador);

		JRadioButton rdbtnVendedor = new JRadioButton("Vendedor");
		rdbtnVendedor.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		buttonGroup.add(rdbtnVendedor);
		rdbtnVendedor.setBackground(Color.WHITE);
		rdbtnVendedor.setBounds(184, 203, 109, 23);
		panel.add(rdbtnVendedor);

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
					long cedula = Long.parseLong(textField.getText());
					String nombre = textField_1.getText();
					String apellido = textField_2.getText();
					String usuario = textField_3.getText();
					String contraseña = "" + new String(textField_4.getPassword());
					if (!nombre.equals("") && !apellido.equals("") && !usuario.equals("") && !contraseña.equals("")) {
						if (rdbtnAdministrador.isSelected()) {
							logica.crearUsuario(cedula, nombre, apellido, usuario, contraseña, 1, conexion);
						} else if (rdbtnVendedor.isSelected()) {
							logica.crearUsuario(cedula, nombre, apellido, usuario, contraseña, 2, conexion);
						} else {
							JOptionPane.showMessageDialog(null, "Por favor seleccione una opción");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Por favor complete la información");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "El campo cedula es numerico");
				}
			}
		});
		btnCrear.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrear.setBounds(28, 242, 89, 23);
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
				VentanaMenuAdministrar menu = new VentanaMenuAdministrar(conexion, user);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(325, 242, 89, 23);
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
					long cedula = Long.parseLong(textField.getText());
					String nombre = textField_1.getText();
					String apellido = textField_2.getText();
					String usuario = textField_3.getText();
					String contraseña = new String(textField_4.getPassword());
					if (!nombre.equals("") && !apellido.equals("") && !usuario.equals("")) {
						if (rdbtnAdministrador.isSelected()) {
							logica.editarUsuario(cedula, nombre, apellido, usuario, contraseña, 1, conexion);
						} else if (rdbtnVendedor.isSelected()) {
							logica.editarUsuario(cedula, nombre, apellido, usuario, contraseña, 2, conexion);
						} else {
							JOptionPane.showMessageDialog(null, "Por favor seleccione una opción");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Por favor complete la información");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "El campo cedula es numerico");
				}
			}
		});
		btnEditar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnEditar.setBounds(127, 242, 89, 23);
		panel.add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnEliminar.doClick();
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long cedula = Long.parseLong(textField.getText());
					logica.eliminarUsuario(cedula, conexion);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "El campo cedula es numerico");
				}
			}
		});
		btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnEliminar.setBounds(226, 242, 89, 23);
		panel.add(btnEliminar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(VentanaCrearUsuario.class.getResource("/imagenes/Logo.png")));
		label.setBounds(229, 50, 185, 116);
		panel.add(label);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					try {
						Usuario usuario = logica.buscarUsuario(Long.parseLong(textField.getText()), conexion);
						if (usuario != null) {
							textField_1.setText(usuario.getNombre());
							textField_2.setText(usuario.getApellidos());
							textField_3.setText(usuario.getUsuario());
							if (usuario.getRol() == 1) {
								rdbtnAdministrador.setSelected(true);
							} else {
								rdbtnVendedor.setSelected(true);
							}
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Verifique la información ingresada");
					}
				}
			}
		});
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField.setBounds(94, 11, 122, 20);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_1.setBounds(94, 48, 122, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_2.setBounds(94, 87, 122, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_3.setBounds(94, 125, 122, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JPasswordField();
		textField_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_4.setBounds(94, 162, 122, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblContrasea.setBounds(10, 165, 74, 14);
		panel.add(lblContrasea);

	}
}
