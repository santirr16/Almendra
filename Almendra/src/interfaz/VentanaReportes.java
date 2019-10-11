package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import logica.Conexion;
import logica.Logica;
import logica.Usuario;

public class VentanaReportes extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	Logica logica = new Logica();
	private JTextField txtCliente;
	private JTextField txtProveedor;
	private JComboBox comboBox = new JComboBox();
	JDateChooser dateChooser = new JDateChooser();
	JDateChooser dateChooser_1 = new JDateChooser();

	/**
	 * Create the frame.
	 */
	public VentanaReportes(Conexion conexion, Usuario user) {

		this.conexion = conexion;
		this.user = user;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaReportes.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Reportes");
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

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaReportes.class.getResource("/imagenes/Logo.png")));
		lblNewLabel.setBounds(118, 11, 185, 116);
		panel.add(lblNewLabel);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setToolTipText("int");
		txtCliente.setBounds(214, 238, 122, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		JButton btnReportes = new JButton("Generar");
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser file = new JFileChooser();
					FileNameExtensionFilter filtroExcel=new FileNameExtensionFilter("XLS","xls");
					file.setFileFilter(filtroExcel);
					file.showSaveDialog(null);
					File guarda = file.getSelectedFile();
					if (comboBox.getSelectedItem().equals("Inventario")) {
						String[] encabezado = { "Codigo", "Nombre", "Descripción", "Proveedor", "Documento", "Usuario",
								"Cantidad", "Valor Costo", "Valor Público" };
						ArrayList<ArrayList> datos = logica.obtenerInventario(conexion);
						logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
					} else if (comboBox.getSelectedItem().equals("Ventas")) {

						Date date = dateChooser.getDate();
						Date fecha = dateChooser_1.getDate();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
						String fechaInicial = String.valueOf(sdf.format(date));
						String fechaFinal = String.valueOf(sdf.format(fecha));

						if (txtCliente.getText().equals("")) {
							String[] encabezado = { "Identificador", "Fecha", "Usuario", "Producto", "Nombre",
									"Descripción", "Cantidad", "Valor Unitario", "Valor Venta", "Ciente",
									"Nombre Cliente" };
							ArrayList<ArrayList> datos = logica.obtenerVentas(conexion, fechaInicial, fechaFinal, -1);
							logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
						} else if (logica.buscarCliente(Long.parseLong(txtCliente.getText()), conexion) != null) {
							String[] encabezado = { "Identificador", "Fecha", "Usuario", "Producto", "Nombre",
									"Descripción", "Cantidad", "Valor Unitario", "Valor Venta", "Cliente",
									"Nombre Cliente" };
							ArrayList<ArrayList> datos = logica.obtenerVentas(conexion, fechaInicial, fechaFinal,
									Long.parseLong(txtCliente.getText()));
							logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
						} else {
							JOptionPane.showMessageDialog(null, "Cliente no encontrado");
						}
					} else if (comboBox.getSelectedItem().equals("Compras")) {

						Date date = dateChooser.getDate();
						Date fecha = dateChooser_1.getDate();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
						String fechaInicial = String.valueOf(sdf.format(date));
						String fechaFinal = String.valueOf(sdf.format(fecha));

						if (txtProveedor.getText().equals("")) {
							String[] encabezado = { "Identificador", "Fecha", "Usuario", "Producto", "Nombre",
									"Descripción", "Cantidad", "Valor Unitario", "Valor Venta", "Proveedor",
									"Nombre Proveedor" };
							ArrayList<ArrayList> datos = logica.obtenerCompras(conexion, fechaInicial, fechaFinal, -1);
							logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
						} else if (logica.buscarProveedor(Long.parseLong(txtProveedor.getText()), conexion) != null) {
							String[] encabezado = { "Identificador", "Fecha", "Usuario", "Producto", "Nombre",
									"Descripción", "Cantidad", "Valor Unitario", "Valor Venta", "Proveedor",
									"Nombre Proveedor" };
							ArrayList<ArrayList> datos = logica.obtenerCompras(conexion, fechaInicial, fechaFinal,
									Long.parseLong(txtProveedor.getText()));
							logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
						} else {
							JOptionPane.showMessageDialog(null, "Proveedor no encontrado");
						}
					} else {
						Date date = dateChooser.getDate();
						Date fecha = dateChooser_1.getDate();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
						String fechaInicial = String.valueOf(sdf.format(date));
						String fechaFinal = String.valueOf(sdf.format(fecha));
						
						String[] encabezado = { "Codigo", "Descripción", "Cantidad", "Valor Total", "Usuario",
								"Fecha" };
						ArrayList<ArrayList> datos = logica.obtenerGastos(conexion, fechaInicial, fechaFinal);
						logica.exportarExcel(guarda.getPath() + ".xls", encabezado, datos);
					}
				} catch (Exception ev) {
					JOptionPane.showMessageDialog(null, "Verifique la información ingresada");
				}
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
				VentanaMenuAdministrar menu = new VentanaMenuAdministrar(conexion, user);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(238, 304, 176, 39);
		panel.add(btnVolver);
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == comboBox) {

					if (comboBox.getSelectedItem().equals("Inventario")) {
						if (comboBox.getSelectedItem().equals("Inventario")) {
							txtCliente.setEditable(false);
							txtProveedor.setEditable(false);
							dateChooser.getCalendarButton().setEnabled(false);
							dateChooser.setEnabled(false);
							dateChooser_1.getCalendarButton().setEnabled(false);
							dateChooser_1.setEnabled(false);
						} else if (comboBox.getSelectedItem().equals("Ventas")) {
							txtCliente.setEditable(true);
							txtProveedor.setEditable(false);
							dateChooser.getCalendarButton().setEnabled(true);
							dateChooser.setEnabled(true);
							dateChooser_1.getCalendarButton().setEnabled(true);
							dateChooser_1.setEnabled(true);
						} else if (comboBox.getSelectedItem().equals("Compras")) {
							txtCliente.setEditable(false);
							txtProveedor.setEditable(true);
							dateChooser.getCalendarButton().setEnabled(true);
							dateChooser.setEnabled(true);
							dateChooser_1.getCalendarButton().setEnabled(true);
							dateChooser_1.setEnabled(true);
						} else {
							txtCliente.setEditable(false);
							txtProveedor.setEditable(false);
							dateChooser.getCalendarButton().setEnabled(true);
							dateChooser.setEnabled(true);
							dateChooser_1.getCalendarButton().setEnabled(true);
							dateChooser_1.setEnabled(true);
						}
					}
				}
			}
		});
		comboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					if (comboBox.getSelectedItem().equals("Inventario")) {
						txtCliente.setEditable(false);
						txtProveedor.setEditable(false);
						dateChooser.getCalendarButton().setEnabled(false);
						dateChooser.setEnabled(false);
						dateChooser_1.getCalendarButton().setEnabled(false);
						dateChooser_1.setEnabled(false);
					} else if (comboBox.getSelectedItem().equals("Ventas")) {
						txtCliente.setEditable(true);
						txtProveedor.setEditable(false);
						dateChooser.getCalendarButton().setEnabled(true);
						dateChooser.setEnabled(true);
						dateChooser_1.getCalendarButton().setEnabled(true);
						dateChooser_1.setEnabled(true);
					} else if (comboBox.getSelectedItem().equals("Compras")) {
						txtCliente.setEditable(false);
						txtProveedor.setEditable(true);
						dateChooser.getCalendarButton().setEnabled(true);
						dateChooser.setEnabled(true);
						dateChooser_1.getCalendarButton().setEnabled(true);
						dateChooser_1.setEnabled(true);
					} else {
						txtCliente.setEditable(false);
						txtProveedor.setEditable(false);
						dateChooser.getCalendarButton().setEnabled(true);
						dateChooser.setEnabled(true);
						dateChooser_1.getCalendarButton().setEnabled(true);
						dateChooser_1.setEnabled(true);
					}
				}
			}
		});

		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Inventario", "Ventas", "Compras", "Gastos" }));
		comboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		comboBox.setBounds(82, 138, 275, 20);
		panel.add(comboBox);

		dateChooser.getCalendarButton().setEnabled(false);
		dateChooser.setEnabled(false);
		dateChooser.setBounds(82, 200, 122, 20);
		panel.add(dateChooser);

		dateChooser_1.getCalendarButton().setEnabled(false);
		dateChooser_1.setEnabled(false);
		dateChooser_1.setBounds(214, 200, 122, 20);
		panel.add(dateChooser_1);

		JLabel lblFechaInicial = new JLabel("Fecha Inicial");
		lblFechaInicial.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblFechaInicial.setBounds(102, 178, 84, 14);
		panel.add(lblFechaInicial);

		JLabel lblFechaFinal = new JLabel("Fecha Final");
		lblFechaFinal.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblFechaFinal.setBounds(231, 178, 72, 14);
		panel.add(lblFechaFinal);

		txtProveedor = new JTextField();
		txtProveedor.setEditable(false);
		txtProveedor.setColumns(10);
		txtProveedor.setBounds(214, 263, 122, 20);
		panel.add(txtProveedor);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblCliente.setBounds(102, 241, 102, 14);
		panel.add(lblCliente);

		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblProveedor.setBounds(102, 269, 102, 14);
		panel.add(lblProveedor);
	}
}
