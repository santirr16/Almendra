package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaMenuVender extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	long turno;
	Logica logica = new Logica();

	/**
	 * Create the frame.
	 */
	public VentanaMenuVender(Conexion conexion, Usuario user, long turno) {

		this.conexion = conexion;
		this.user = user;
		this.turno = turno;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaMenuVender.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Men\u00FA Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
		setSize(450, 391);
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

		JButton btnIngresarProductos = new JButton("Ingresar Productos");
		btnIngresarProductos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnIngresarProductos.doClick();
				}
			}
		});
		btnIngresarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaIngresarProductos ingresarProductos = new VentanaIngresarProductos(conexion, user, turno);
				ingresarProductos.setVisible(true);
				dispose();
			}
		});
		btnIngresarProductos.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnIngresarProductos.setBounds(215, 148, 176, 39);
		panel.add(btnIngresarProductos);

		JButton btnVender = new JButton("Vender Productos");
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaVenderProductos ventana = new VentanaVenderProductos(conexion, user, turno);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnVender.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnVender.doClick();
				}
			}
		});
		btnVender.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVender.setBounds(29, 148, 176, 39);
		panel.add(btnVender);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaMenuVender.class.getResource("/imagenes/Logo.png")));
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
				VentanaMenu menu = new VentanaMenu(conexion, user);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(29, 296, 362, 38);
		panel.add(btnVolver);

		JButton btnCerrarCaja = new JButton("Cerrar Caja");
		btnCerrarCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel lblValor = new JLabel("$0,00");
				JLabel label = new JLabel("$0,00");
				JLabel label_2 = new JLabel("$0,00");
				NumberFormat formato = NumberFormat.getCurrencyInstance();
				if (logica.obtenerValorCompra(conexion, turno) != 0) {
					label.setText(formato.format(logica.obtenerValorCompra(conexion, turno)) + "");
				}
				if (logica.obtenerValorVenta(conexion, turno) != 0) {
					lblValor.setText(formato.format(logica.obtenerValorVenta(conexion, turno)) + "");
				}
				try {
					label_2.setText(formato.format(
							(long) formato.parse(lblValor.getText()) - (long) formato.parse(label.getText())) + "");
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				VentanaCierreCaja cierre = new VentanaCierreCaja(conexion, user, turno, lblValor, label, label_2);
				cierre.setVisible(true);
				dispose();
			}
		});
		btnCerrarCaja.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnCerrarCaja.doClick();
				}
			}
		});
		btnCerrarCaja.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCerrarCaja.setBounds(215, 247, 176, 38);
		panel.add(btnCerrarCaja);

		JButton btnImprimirCodigos = new JButton("Imprimir Codigos");
		btnImprimirCodigos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnImprimirCodigos.doClick();
				}
			}
		});
		btnImprimirCodigos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaImprimirCodigos ventana= new VentanaImprimirCodigos(conexion, user, turno);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnImprimirCodigos.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnImprimirCodigos.setBounds(29, 198, 176, 38);
		panel.add(btnImprimirCodigos);
		
		JButton btnInventario = new JButton("Inventario");
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				FileNameExtensionFilter filtroExcel=new FileNameExtensionFilter("XLS","xls");
				file.setFileFilter(filtroExcel);
				file.showSaveDialog(null);
				File guarda = file.getSelectedFile();				
				String[] encabezado = { "Codigo", "Nombre", "Descripción", "Proveedor", "Documento", "Usuario",
						"Cantidad", "Valor Costo", "Valor Público" };
				ArrayList<ArrayList> datos = logica.obtenerInventario(conexion);
				logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
			}
		});
		btnInventario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnInventario.doClick();
				}
			}
		});
		btnInventario.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnInventario.setBounds(29, 247, 176, 38);
		panel.add(btnInventario);
		
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
				VentanaMenuAdministrarVender ventana= new VentanaMenuAdministrarVender(conexion, user, turno);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnAdministrar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnAdministrar.setBounds(215, 198, 176, 38);
		panel.add(btnAdministrar);
	}
}
