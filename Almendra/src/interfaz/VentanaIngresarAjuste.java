package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

public class VentanaIngresarAjuste extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	Conexion conexion;
	Usuario user;
	private JTable table;
	Logica logica = new Logica();

	/**
	 * Create the frame.
	 */
	public VentanaIngresarAjuste(Conexion conexion, Usuario user) {
		this.conexion = conexion;
		this.user = user;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaGastosAdicionales.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Ingresar Productos");
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

		JButton btnCrear = new JButton("Ingresar");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getRowCount() > 0) {
					for (int i = 0; i < table.getRowCount(); i++) {
						// TODO
						Producto product = logica.buscarProducto((long) (table.getValueAt(i, 0)), conexion);
						long producto = (long) (table.getValueAt(i, 0));
						int cantidad = (int) (table.getValueAt(i, 2));
						logica.editarCantidadProducto(producto, product.getCantidad() + cantidad, user.getUsuario(),
								conexion);
					}
					for (int i = table.getRowCount() - 1; i >= 0; i--) {
						((DefaultTableModel) table.getModel()).removeRow(i);
					}
					JOptionPane.showMessageDialog(null, "Ajuste Ingresado");
				} else {
					JOptionPane.showMessageDialog(null, "Por favor ingrese productos a la tabla");
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
				VentanaAjuste ajuste = new VentanaAjuste(conexion, user);
				ajuste.setVisible(true);
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
					if (table.getSelectedRow() != -1) {
						if ((int) (table.getValueAt(table.getSelectedRow(), 2)) == 1) {
							((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
						} else {
							((DefaultTableModel) table.getModel()).setValueAt(
									(int) (((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 2))
											- 1,
									table.getSelectedRow(), 2);
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
							JOptionPane.showMessageDialog(null, "Por favor Ingrese la informaci�n");
						} else {
							llenarTabla(Long.parseLong(textField.getText()), 1);
							textField.setText("");
						}
					}
				} catch (Exception er) {
					JOptionPane.showMessageDialog(null, "El campo identificador es num�rico");
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
							JOptionPane.showMessageDialog(null, "Por favor Ingrese la informaci�n");
						} else {
							llenarTabla(Long.parseLong(textField.getText()), Integer.parseInt(textField_1.getText()));
							textField.setText("");
							textField_1.setText("");
						}
					}
				} catch (Exception er) {
					JOptionPane.showMessageDialog(null, "Los campos identificador y cantidad son num�ricos");
				}
			}
		});
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_1.setBounds(94, 121, 122, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

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
						}
					}
					if (contar == 0) {
						Object[] fila = new Object[table.getModel().getColumnCount()];
						fila[0] = identificador;
						fila[1] = producto.getNombre();
						fila[2] = cantidad;
						fila[3] = producto.getValorCosto();
						((DefaultTableModel) table.getModel()).addRow(fila);
					}
				} else {
					Object[] fila = new Object[table.getModel().getColumnCount()];
					fila[0] = identificador;
					fila[1] = producto.getNombre();
					fila[2] = cantidad;
					fila[3] = producto.getValorCosto();
					((DefaultTableModel) table.getModel()).addRow(fila);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}