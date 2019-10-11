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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Conexion;
import logica.Logica;
import logica.Producto;
import logica.Usuario;

public class VentanaIngresarProductos extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	Conexion conexion;
	Usuario user;
	private JTable table;
	JLabel label = new JLabel("$0,00");
	Logica logica = new Logica();
	long turno;

	/**
	 * Create the frame.
	 */
	public VentanaIngresarProductos(Conexion conexion, Usuario user, long turno) {

		this.conexion = conexion;
		this.user = user;
		this.turno = turno;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaIngresarProductos.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Comprar Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 586, 300);
		setSize(586, 300);
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

		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblCantidad.setBounds(10, 124, 74, 14);
		panel.add(lblCantidad);

		JLabel lblIdentificador = new JLabel("C\u00F3digo");
		lblIdentificador.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblIdentificador.setBounds(10, 86, 74, 14);
		panel.add(lblIdentificador);

		JButton btnCrear = new JButton("Comprar");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumberFormat formato = NumberFormat.getCurrencyInstance();
				try {
					if (table.getRowCount() > 0) {
						SimpleDateFormat d = new SimpleDateFormat("dd/MM/yy");
						String date = d.format(new Date());
						long valorTotal = (long) formato.parse(label.getText());
						long proveedor = (long) logica.buscarProducto((long) (table.getValueAt(0, 0)), conexion)
								.getProveedor();
						long compra = logica.registrarCompra(proveedor, user.getUsuario(), date, valorTotal, turno,
								conexion);
						for (int i = 0; i < table.getRowCount(); i++) {
							// TODO
							Producto product = logica.buscarProducto((long) (table.getValueAt(i, 0)), conexion);
							long producto = (long) (table.getValueAt(i, 0));
							long valorVenta = ((long) (table.getValueAt(i, 3))) * ((int) (table.getValueAt(i, 2)));
							long valorUnitario = (long) (table.getValueAt(i, 3));
							int cantidad = (int) (table.getValueAt(i, 2));
							logica.registrarDetalleCompra(proveedor, producto, user.getUsuario(), valorVenta,
									valorUnitario, compra, cantidad, conexion);
							logica.editarCantidadProducto(producto, product.getCantidad() + cantidad, user.getUsuario(),
									conexion);

						}
						for (int i = table.getRowCount() - 1; i >= 0; i--) {
							((DefaultTableModel) table.getModel()).removeRow(i);
						}
						label.setText("$0,00");
					} else {
						JOptionPane.showMessageDialog(null, "Por favor ingrese productos a la tabla");
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCrear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					btnCrear.doClick();
				}
			}
		});
		btnCrear.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnCrear.setBounds(226, 217, 89, 23);
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
				VentanaMenuVender menu = new VentanaMenuVender(conexion, user, turno);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(325, 217, 89, 23);
		panel.add(btnVolver);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					NumberFormat formato = NumberFormat.getCurrencyInstance();
					if (table.getSelectedRow() != -1) {
						Producto producto = logica.buscarProducto((long) (table.getValueAt(table.getSelectedRow(), 0)),
								conexion);
						if ((int) (table.getValueAt(table.getSelectedRow(), 2)) == 1) {
							((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
							label.setText(formato.format(
									(long) formato.parse(label.getText()) - (long) producto.getValorCosto()) + "");
							System.out.println(label.getText());
						} else {
							((DefaultTableModel) table.getModel()).setValueAt(
									(int) (((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 2))
											- 1,
									table.getSelectedRow(), 2);
							label.setText(formato.format(
									(long) formato.parse(label.getText()) - (long) producto.getValorCosto()) + "");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Por favor seleccione un producto a eliminar de la lista");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
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
		btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnEliminar.setBounds(127, 217, 89, 23);
		panel.add(btnEliminar);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					char tecla = e.getKeyChar();
					if (tecla == KeyEvent.VK_ENTER) {
						if (textField.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Por favor Ingrese la información");
						} else {
							llenarTabla(Long.parseLong(textField.getText()), 1);
							textField.setText("");
						}
					}
				} catch (Exception er) {
					JOptionPane.showMessageDialog(null, "El campo identificador es numérico");
				}
			}
		});
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField.setBounds(94, 84, 122, 20);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					char tecla = e.getKeyChar();
					if (tecla == KeyEvent.VK_ENTER) {
						if (textField.getText().equals("") && textField_1.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Por favor Ingrese la información");
						} else {
							llenarTabla(Long.parseLong(textField.getText()), Integer.parseInt(textField_1.getText()));
							textField.setText("");
							textField_1.setText("");
						}
					}
				} catch (Exception er) {
					JOptionPane.showMessageDialog(null, "Los campo identificador y cantidad son numéricos");
				}
			}
		});
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_1.setBounds(94, 121, 122, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblTotal.setBounds(10, 28, 74, 14);
		panel.add(lblTotal);

		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label.setBounds(94, 29, 122, 14);
		panel.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(226, 11, 324, 195);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Identificador", "Producto", "Cantidad", "Valor Unitario" }));
		table.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
	}

	public void llenarTabla(long identificador, int cantidad) {
		Producto producto = logica.buscarProducto(identificador, conexion);
		NumberFormat formato = NumberFormat.getCurrencyInstance();
		int contar = 0;
		try {
			if (producto == null) {
				JOptionPane.showMessageDialog(null, "Producto no encontrado");
			} else {
				if (table.getRowCount() > 0) {
					if (producto.getProveedor() != logica.buscarProducto((long) (table.getValueAt(0, 0)), conexion)
							.getProveedor()) {
						JOptionPane.showMessageDialog(null,
								"El proveedor es distinto, debera registrarlo en otra compra");
					} else {
						for (int i = 0; i < table.getRowCount(); i++) {
							if (identificador == (long) (table.getValueAt(i, 0))) {
								table.setValueAt((int) (table.getValueAt(i, 2)) + cantidad, i, 2);
								contar = 1;
								label.setText(formato.format((long) formato.parse(label.getText())
										+ (long) (cantidad * producto.getValorCosto())) + "");
							}
						}
						if (contar == 0) {
							Object[] fila = new Object[table.getModel().getColumnCount()];
							fila[0] = identificador;
							fila[1] = producto.getNombre();
							fila[2] = cantidad;
							fila[3] = producto.getValorCosto();
							((DefaultTableModel) table.getModel()).addRow(fila);
							label.setText(formato.format((long) formato.parse(label.getText())
									+ (long) (cantidad * producto.getValorCosto())) + "");
						}
					}
				} else {
					Object[] fila = new Object[table.getModel().getColumnCount()];
					fila[0] = identificador;
					fila[1] = producto.getNombre();
					fila[2] = cantidad;
					fila[3] = producto.getValorCosto();
					((DefaultTableModel) table.getModel()).addRow(fila);
					label.setText(formato.format(
							(long) formato.parse(label.getText()) + (long) (cantidad * producto.getValorCosto())) + "");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
