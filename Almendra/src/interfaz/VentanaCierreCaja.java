package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaCierreCaja extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	Logica logica = new Logica();
	JLabel lblValor;
	JLabel label;
	JLabel label_2;
	long turno;

	/**
	 * Create the frame.
	 */
	public VentanaCierreCaja(Conexion conexion, Usuario user, long turno,JLabel lblValor,JLabel label,JLabel label_2) {

		this.conexion = conexion;
		this.user = user;
		this.turno = turno;
		this.lblValor=lblValor;
		this.label=label;
		this.label_2=label_2;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaCierreCaja.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Cierre de Caja");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 467, 300);
		setSize(467, 300);
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
		btnVolver.setBounds(225, 217, 132, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaMenuVender menu = new VentanaMenuVender(conexion, user, turno);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		panel.add(btnVolver);

		// TODO ingresae valores a los labels con condiciones.

//		if (logica.obtenerValorCompra(conexion, turno) != 0) {
//			NumberFormat formato = NumberFormat.getCurrencyInstance();
//			label.setText(formato.format(logica.obtenerValorCompra(conexion, turno)) + "");
////			lblValor.setText(formato.format((long) formato.parse(lblValor.getText())
////					+ (long) (cantidad * producto.getValorPublico())) + "");
//		}
//		if (logica.obtenerValorVenta(conexion, turno) != 0) {
//			NumberFormat formato = NumberFormat.getCurrencyInstance();
//			lblValor.setText(formato.format(logica.obtenerValorCompra(conexion, turno)) + "");
//		}

		JButton btnEliminar = new JButton("Cierre de Turno");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumberFormat formato = NumberFormat.getCurrencyInstance();
				try {
					long valorCompra = (long) formato.parse(label.getText());
					long valorVenta = (long) formato.parse(lblValor.getText());
					long totalDinero = (long) formato.parse(label_2.getText());
					logica.cerrarCaja(turno, valorCompra, valorVenta, totalDinero, conexion);
					dispose();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEliminar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnEliminar.doClick();
				}
			}
		});
		btnEliminar.setBounds(84, 217, 132, 23);
		btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		panel.add(btnEliminar);

		JLabel lblTotal = new JLabel("Total Ventas");
		lblTotal.setBounds(10, 29, 132, 39);
		lblTotal.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		panel.add(lblTotal);

		lblValor.setFont(new Font("Comic Sans MS", Font.PLAIN, 23));
		lblValor.setBounds(167, 29, 200, 39);
		panel.add(lblValor);

		JLabel lblDevolucin = new JLabel("Total Compras");
		lblDevolucin.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblDevolucin.setBounds(10, 156, 147, 34);
		panel.add(lblDevolucin);

		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 23));
		label.setBounds(167, 156, 200, 34);
		panel.add(label);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(VentanaCierreCaja.class.getResource("/imagenes/Logo.png")));
		label_1.setBounds(256, 37, 185, 116);
		panel.add(label_1);
		
	}
}