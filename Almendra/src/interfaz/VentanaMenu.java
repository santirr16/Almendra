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
import javax.swing.border.EmptyBorder;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaMenu extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	Logica logica = new Logica();

	/**
	 * Create the frame.
	 */
	public VentanaMenu(Conexion conexion, Usuario user) {

		this.conexion = conexion;
		this.user = user;
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaMenu.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Men\u00FA Principal");
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

		JButton btnAdministrar = new JButton("Administrar");
		btnAdministrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnAdministrar.doClick();
				}
			}
		});
		btnAdministrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getRol() == 1) {
					VentanaMenuAdministrar menuAdministrar = new VentanaMenuAdministrar(conexion, user);
					menuAdministrar.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "No esta autorizado");
				}
			}
		});
		btnAdministrar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnAdministrar.setBounds(10, 175, 200, 39);
		panel.add(btnAdministrar);

		JButton btnVender = new JButton("Vender o Ingresar Productos");
		btnVender.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnVender.doClick();
				}
			}
		});
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long turno = logica.buscarCajaAbieta(user.getUsuario(), conexion);
				if (turno != -1) {
					if (JOptionPane.showConfirmDialog(null, "¿Tiene un turno abierto desea continuar?", null,
							JOptionPane.YES_NO_OPTION, 1) == JOptionPane.YES_OPTION) {
						VentanaMenuVender menuVender = new VentanaMenuVender(conexion, user, turno);
						menuVender.setVisible(true);
						dispose();
					}
				} else {
					if (JOptionPane.showConfirmDialog(null, "Desea abrir un nuevo turno", null,
							JOptionPane.YES_NO_OPTION, 1) == JOptionPane.YES_OPTION) {
						long nuevoTurno = logica.abrirCaja(user.getUsuario(), conexion);
						VentanaMenuVender menuVender = new VentanaMenuVender(conexion, user, nuevoTurno);
						menuVender.setVisible(true);
						dispose();
					}
				}
			}
		});
		btnVender.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		btnVender.setBounds(220, 175, 194, 39);
		panel.add(btnVender);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaMenu.class.getResource("/imagenes/Logo.png")));
		lblNewLabel.setBounds(118, 11, 185, 116);
		panel.add(lblNewLabel);
	}
}
