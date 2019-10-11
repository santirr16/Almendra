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
import logica.Usuario;

public class VentanaAjuste extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;

	/**
	 * Create the frame.
	 */
	public VentanaAjuste(Conexion conexion, Usuario user) {

		this.conexion = conexion;
		this.user = user;

		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAjuste.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Ajuste de Inventario");
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

		JButton btnIngresar = new JButton("Ingresar Productos");
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
				VentanaIngresarAjuste ajuste = new VentanaIngresarAjuste(conexion, user);
				ajuste.setVisible(true);
				dispose();
			}
		});
		btnIngresar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnIngresar.setBounds(10, 175, 200, 39);
		panel.add(btnIngresar);

		JButton btnRetirar = new JButton("Retirar Productos");
		btnRetirar.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnRetirar.doClick();
				}
			}
		});
		btnRetirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRetirarAjuste ajuste= new VentanaRetirarAjuste(conexion, user);
				ajuste.setVisible(true);
				dispose();
			}
		});
		btnRetirar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnRetirar.setBounds(220, 175, 194, 39);
		panel.add(btnRetirar);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaAjuste.class.getResource("/imagenes/Logo.png")));
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
				VentanaMenuAdministrar ventana = new VentanaMenuAdministrar(conexion, user);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnVolver.setBounds(118, 225, 194, 39);
		panel.add(btnVolver);
	}
}
