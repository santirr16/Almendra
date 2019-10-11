package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
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

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import logica.Conexion;
import logica.Logica;
import logica.Producto;
import logica.Usuario;

public class VentanaVenderProductos extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	Conexion conexion;
	Usuario user;
	private JTextField textField_2;
	private JTable table;
	Logica logica = new Logica();
	JLabel lblValor = new JLabel("$0,00");
	JLabel label = new JLabel("$0,00");
	private JTextField textField_3;
	long turno;
	long venta = 0;
	int presione = 0;

	/**
	 * Create the frame.
	 */
	public VentanaVenderProductos(Conexion conexion, Usuario user, long turno) {

		this.conexion = conexion;
		this.user = user;
		this.turno = turno;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaVenderProductos.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Vender Productos");
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
		lblCantidad.setBounds(10, 124, 74, 14);
		lblCantidad.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		panel.add(lblCantidad);

		JLabel lblIdentificador = new JLabel("C\u00F3digo");
		lblIdentificador.setBounds(10, 86, 74, 14);
		lblIdentificador.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		panel.add(lblIdentificador);

		JButton btnVender = new JButton("Vender");
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (presione == 1) {
					NumberFormat formato = NumberFormat.getCurrencyInstance();
					try {
						if (logica.buscarCliente(Long.parseLong(textField_3.getText()), conexion) != null) {
							long cliente = Long.parseLong(textField_3.getText());
							int cantidadProductos = 0;
							if (table.getRowCount() > 0) {
								for (int i = 0; i < table.getRowCount(); i++) {
									Producto producto = logica.buscarProducto((long) (table.getValueAt(i, 0)),
											conexion);
									if (producto != null) {
										if (producto.getCantidad() >= (int) (table.getValueAt(i, 2))) {
											cantidadProductos++;
										} else {
											JOptionPane.showMessageDialog(null,
													"El producto " + table.getValueAt(i, 1)
															+ " tiene la siguiente cantidad de existencias: "
															+ producto.getCantidad());
										}
									}
								}
								if (cantidadProductos == table.getRowCount()) {
									SimpleDateFormat d = new SimpleDateFormat("dd/MM/yy");
									String date = d.format(new Date());
									long valorTotal = (long) formato.parse(lblValor.getText());
									venta = logica.registrarVenta(cliente, user.getUsuario(), date, valorTotal, turno,
											conexion);
									for (int i = 0; i < table.getRowCount(); i++) {
										Producto product = logica.buscarProducto((long) (table.getValueAt(i, 0)),
												conexion);
										long producto = (long) (table.getValueAt(i, 0));
										long valorVenta = ((long) (table.getValueAt(i, 3)))
												* ((int) (table.getValueAt(i, 2)));
										long valorUnitario = (long) (table.getValueAt(i, 3));
										int cantidad = (int) (table.getValueAt(i, 2));
										logica.registrarDetalleVenta(cliente, producto, user.getUsuario(), valorVenta,
												valorUnitario, venta, cantidad, conexion);
										logica.editarCantidadProducto(producto, product.getCantidad() - cantidad,
												user.getUsuario(), conexion);
									}

									if (JOptionPane.showConfirmDialog(null, "¿Desea imprimir la factura?", null,
											JOptionPane.YES_NO_OPTION, 1) == JOptionPane.YES_OPTION) {
										imprimirFactura();
									}

									for (int i = table.getRowCount() - 1; i >= 0; i--) {
										((DefaultTableModel) table.getModel()).removeRow(i);
									}
									textField_2.setText("");
									textField_3.setText("");
									label.setText("$0,00");
									lblValor.setText("$0,00");
								}
								presione = 0;
							} else {
								JOptionPane.showMessageDialog(null, "Por favor ingrese productos a la tabla");
							}
						} else {
							if (JOptionPane.showConfirmDialog(null, "¿El cliente no existe desea crearlo?", null,
									JOptionPane.YES_NO_OPTION, 1) == JOptionPane.YES_OPTION) {
								JTextField pasar = new JTextField(textField_3.getText());
								VentanaCrearCliente cliente = new VentanaCrearCliente(conexion, user, pasar);
								cliente.setVisible(true);
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Por favor verifique la información ingresada");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Por favor presione la tecla ENTER en el campo de Dinero recibido para efectuar la operación");
				}
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
		btnVender.setBounds(226, 217, 89, 23);
		btnVender.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		panel.add(btnVender);

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
		btnVolver.setBounds(325, 217, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaMenuVender menu = new VentanaMenuVender(conexion, user, turno);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
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
							lblValor.setText(formato.format(
									(long) formato.parse(lblValor.getText()) - producto.getValorPublico()) + "");
						} else {
							((DefaultTableModel) table.getModel()).setValueAt(
									(int) (((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 2))
											- 1,
									table.getSelectedRow(), 2);
							lblValor.setText(formato.format(
									(long) formato.parse(lblValor.getText()) - producto.getValorPublico()) + "");
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
		btnEliminar.setBounds(127, 217, 89, 23);
		btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
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
		textField.setBounds(94, 84, 122, 20);
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
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
					JOptionPane.showMessageDialog(null, "Los campos identificador y cantidad son numéricos");
				}
			}
		});
		textField_1.setBounds(94, 121, 122, 20);
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(10, 29, 48, 14);
		lblTotal.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		panel.add(lblTotal);

		lblValor.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		lblValor.setBounds(94, 30, 122, 14);
		panel.add(lblValor);

		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					// TODO
					NumberFormat formato = NumberFormat.getCurrencyInstance();
					try {
						label.setText(formato.format(
								-(long) formato.parse(lblValor.getText()) + Integer.parseInt(textField_2.getText()))
								+ "");
						presione = 1;
					} catch (NumberFormatException | ParseException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Este campo es numerico, sin puntuaciones");
					}
				}
			}
		});
		textField_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_2.setBounds(94, 186, 122, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblDineroRecibido = new JLabel("Dinero recibido");
		lblDineroRecibido.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		lblDineroRecibido.setBounds(10, 189, 89, 14);
		panel.add(lblDineroRecibido);

		JLabel lblDevolucin = new JLabel("Devoluci\u00F3n");
		lblDevolucin.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblDevolucin.setBounds(10, 54, 74, 14);
		panel.add(lblDevolucin);

		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label.setBounds(94, 55, 122, 14);
		panel.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(226, 11, 324, 195);
		panel.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Identificador", "Producto", "Cantidad", "Valor Unitario" }));
		scrollPane.setViewportView(table);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblCliente.setBounds(10, 163, 89, 14);
		panel.add(lblCliente);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(94, 160, 122, 20);
		panel.add(textField_3);
	}

	/**
	 * Metodo que sirve para llenar la tabla
	 * 
	 * @param identificador
	 * @param cantidad
	 */
	public void llenarTabla(long identificador, int cantidad) {
		Producto producto = logica.buscarProducto(identificador, conexion);
		NumberFormat formato = NumberFormat.getCurrencyInstance();
		int contar = 0;
		try {
			if (producto == null) {
				JOptionPane.showMessageDialog(null, "Producto no encontrado");
			} else {
				if (table.getRowCount() > 0) {
					for (int i = 0; i < table.getRowCount(); i++) {
						if (identificador == (long) (table.getValueAt(i, 0))) {
							table.setValueAt((int) (table.getValueAt(i, 2)) + cantidad, i, 2);
							contar = 1;
							// Si se desea forzar el formato español:
							// formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es","ES"));
//						System.out.println(formatoImporte.format(importe));
							lblValor.setText(formato.format((long) formato.parse(lblValor.getText())
									+ (long) (cantidad * producto.getValorPublico())) + "");
						}
					}
					if (contar == 0) {
						Object[] fila = new Object[table.getModel().getColumnCount()];
						fila[0] = identificador;
						fila[1] = producto.getNombre();
						fila[2] = cantidad;
						fila[3] = producto.getValorPublico();
						((DefaultTableModel) table.getModel()).addRow(fila);
						lblValor.setText(formato.format((long) formato.parse(lblValor.getText())
								+ (long) (cantidad * producto.getValorPublico())) + "");
					}
				} else {
					Object[] fila = new Object[table.getModel().getColumnCount()];
					fila[0] = identificador;
					fila[1] = producto.getNombre();
					fila[2] = cantidad;
					fila[3] = producto.getValorPublico();
					((DefaultTableModel) table.getModel()).addRow(fila);
					lblValor.setText(formato.format(
							(long) formato.parse(lblValor.getText()) + (long) (cantidad * producto.getValorPublico()))
							+ "");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimirFactura() {
		DecimalFormat formato = new DecimalFormat("###,###");
		NumberFormat formato1 = NumberFormat.getCurrencyInstance();
		SimpleDateFormat d = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat h = new SimpleDateFormat("hh:mm");
		String date = d.format(new Date());
		String hora = h.format(new Date());
		PrinterMatrix printer = new PrinterMatrix();
		Extenso e = new Extenso();
		e.setNumber(101.85);

		// Definir el tamanho del papel para la impresion aca 25 (antes 60 para 25
		// renglones) lineas y 80 columnas
		printer.setOutSize(table.getRowCount() + 32, 42);
		// Imprimir * de la 2da linea a 25 en la columna 1;
		// printer.printCharAtLin(2, 25, 1, "*");
		// Imprimir * 1ra linea de la columa de 1 a 80
//		printer.printCharAtCol(1, 1, 42, "_");
		// Imprimir Encabezado nombre del La EMpresa
//       printer.toImageFile("/imagenes/Logo viejo.png");
		printer.printTextWrap(0, 1, 11, 42, "ALMENDRA FIT MARKET");
		printer.printTextWrap(1, 1, 10, 42, "NO RESPONSABLE DE IVA");
		printer.printTextWrap(2, 1, 4, 42, "MARTHA PATRICIA LEGUIZAMO ORJUELA");
		printer.printTextWrap(3, 1, 12, 42, "NIT: 63.546.418-8");
		printer.printTextWrap(4, 1, 12, 42, "CALLE 10 No 10-26");
		printer.printTextWrap(5, 1, 12, 42, "SANTA ROSA DEL SUR");
		printer.printTextWrap(6, 1, 10, 42, "Telefono: 313-8859722");
		printer.printTextWrap(7, 1, 2, 42, "________________________________________");
		printer.printTextWrap(8, 1, 7, 42, "FACTURA DE VENTA   " + venta);
		printer.printTextWrap(9, 1, 2, 42, "FECHA	" + date);
		printer.printTextWrap(9, 1, 20, 42, "HORA	" + hora);
		printer.printTextWrap(10, 1, 2, 42,
				"FACTURADO A	" + logica.buscarCliente(Long.parseLong(textField_3.getText()), conexion).getNombre());
		printer.printTextWrap(11, 1, 2, 42, "IDENTIFICACION	" + textField_3.getText());
		printer.printTextWrap(12, 1, 2, 42, "========================================");
		printer.printTextWrap(13, 1, 2, 42, "Producto              Cant  Total");
		int i = 0;
		for (i = 0; i < table.getRowCount(); i++) {
//			printer.printTextWrap(8+i, 2, 2, 42, table.getValueAt(i, 1) + "					"
//					+ "" + table.getValueAt(i, 2));
//			printer.printTextWrap(15 + i, 1, 2, 42, table.getValueAt(i, 1) + "");
			printer.printTextWrap(15 + i, 1, 2, 42,
					logica.buscarProducto((long) table.getValueAt(i, 0), conexion).getDescripcion());
			printer.printTextWrap(15 + i, 1, 25, 42, "" + table.getValueAt(i, 2));
			printer.printTextWrap(15 + i, 1, 30, 42,
					"" + formato.format((long) (table.getValueAt(i, 3)) * ((int) (table.getValueAt(i, 2)))));

		}
		printer.printTextWrap(15 + i, 1, 2, 42, "========================================");
		try {
			String numero = formato.format(formato1.parse(lblValor.getText()));
			String numero1 = formato.format(formato1.parse(label.getText()));
			String numero2 = formato.format(Long.parseLong(textField_2.getText()));
			printer.printTextWrap(17 + i, 1, 2, 42, "	TOTAL A PAGAR " + numero);
			printer.printTextWrap(18 + i, 1, 2, 42, "	DINERO RECIBIDO " + numero2);
			printer.printTextWrap(19 + i, 1, 2, 42, "	CAMBIO " + numero1);
		} catch (ParseException e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}
		printer.printTextWrap(22 + i, 1, 10, 42, "GRACIAS POR SU COMPRA");

		printer.toFile("impresion.txt");

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("impresion.txt");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		if (inputStream == null) {
			return;
		}

		DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		SimpleDoc document = new SimpleDoc(inputStream, docFormat, null);

		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

		if (defaultPrintService != null) {
			DocPrintJob printJob = defaultPrintService.createPrintJob();
			try {
				printJob.print(document, attributeSet);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.err.println("No existen impresoras instaladas");
		}

		// inputStream.close();

	}
}
