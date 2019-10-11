package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField textField_1;
	JButton btnIngresar = new JButton("Ingresar");
	Logica logica = new Logica();
	static Conexion conexion = new Conexion();
	Usuario user;
//	static ImageIcon icono = new ImageIcon("C:\\Users\\santi\\eclipse-workspace\\Almendra\\src\\imagenes\\Logo JOption.png");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		conexion.conectar();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Inicio de sesi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
		setSize(450, 300);
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

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblUsuario.setBounds(10, 62, 84, 19);
		panel.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblContrasea.setBounds(10, 144, 107, 27);
		panel.add(lblContrasea);

		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		textField.setBackground(SystemColor.text);
		textField.setBounds(121, 54, 147, 42);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JPasswordField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnIngresar.doClick();
				}
			}
		});
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		textField_1.setBackground(SystemColor.text);
		textField_1.setBounds(121, 140, 147, 42);
		panel.add(textField_1);
		textField_1.setColumns(10);
		btnIngresar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnIngresar.doClick();
				}
			}
		});

		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = textField.getText();
				String contraseña = "" + new String(textField_1.getPassword());
				try {

					user = logica.login(usuario, contraseña, conexion);
					if (user != null) {
						VentanaMenu menu = new VentanaMenu(conexion, user);
						menu.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnIngresar.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		btnIngresar.setBounds(278, 198, 136, 42);
		panel.add(btnIngresar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/Logo.png")));
		label.setBounds(239, 31, 185, 116);
		panel.add(label);
	}
}
