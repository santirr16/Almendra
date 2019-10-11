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
import logica.Proveedor;
import logica.Usuario;

public class VentanaCrearProveedorMenu extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	Conexion conexion;
	Usuario user;
	private JTextField textField_4;
	Logica logica = new Logica();

	/**
	 * Create the frame.
	 */
	public VentanaCrearProveedorMenu(Conexion conexion, Usuario user) {

		this.conexion = conexion;
		this.user = user;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaCrearProveedorMenu.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Administrar Proveedores");
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

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblTelefono.setBounds(10, 98, 74, 14);
		panel.add(lblTelefono);

		JLabel lblIdentificador = new JLabel("Nit o Cedula");
		lblIdentificador.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblIdentificador.setBounds(10, 22, 74, 14);
		panel.add(lblIdentificador);

		JLabel lblDireccion = new JLabel("Direcci\u00F3n");
		lblDireccion.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblDireccion.setBounds(10, 137, 74, 14);
		panel.add(lblDireccion);

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
					int telefono = Integer.parseInt(textField_2.getText());
					String direccion = textField_3.getText();
					String correoElectronico = textField_4.getText();
					if (!nombre.equals("")) {
						logica.crearProveedor(identificador, nombre, telefono, direccion, correoElectronico, conexion);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor complete la informaci�n");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Los campos identificador y telefono son numericos");
				}
			}
		});
		btnCrear.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrear.setBounds(110, 213, 95, 23);
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
		btnVolver.setBounds(325, 213, 89, 23);
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
					long telefono = Long.parseLong(textField_2.getText());
					String direccion = textField_3.getText();
					String correoElectronico = textField_4.getText();
					if (!nombre.equals("")) {
						logica.editarProveedor(identificador, nombre, telefono, direccion, correoElectronico, conexion);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor complete la informaci�n");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Los campos identificador y telefono son numericos");
				}
			}
		});
		btnEditar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnEditar.setBounds(215, 213, 100, 23);
		panel.add(btnEditar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(VentanaCrearProveedorMenu.class.getResource("/imagenes/Logo.png")));
		label.setBounds(239, 60, 185, 116);
		panel.add(label);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				try {
					if (tecla == KeyEvent.VK_ENTER) {
						Proveedor proveedor = logica.buscarProveedor(Long.parseLong(textField.getText()), conexion);
						if (proveedor != null) {
							textField_1.setText(proveedor.getNombre());
							textField_2.setText(proveedor.getTelefono() + "");
							textField_3.setText(proveedor.getDireccion());
							textField_4.setText(proveedor.getCorreo());
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Verifique la informaci�n ingresada");
				}
			}
		});
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField.setBounds(107, 22, 122, 20);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_1.setBounds(107, 59, 122, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_2.setBounds(107, 98, 122, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_3.setBounds(107, 136, 122, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblCorreo = new JLabel("Correo Electronico");
		lblCorreo.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		lblCorreo.setBounds(10, 176, 95, 14);
		panel.add(lblCorreo);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(107, 173, 122, 20);
		panel.add(textField_4);
	}
}
