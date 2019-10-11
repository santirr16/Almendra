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
import java.util.Currency;
import java.util.Locale;

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
import logica.Usuario;

public class VentanaGastosAdicionales extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	Conexion conexion;
	Usuario user;
	private JTable table;
	Logica logica = new Logica();
	private JTextField textField_2;
	JLabel label = new JLabel("$0,00");

	/**
	 * Create the frame.
	 */
	public VentanaGastosAdicionales(Conexion conexion, Usuario user) {

		this.conexion = conexion;
		this.user = user;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaIngresarAjuste.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Gastos Adicionales");
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

		JLabel lblIdentificador = new JLabel("Descripci\u00F3n");
		lblIdentificador.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblIdentificador.setBounds(10, 86, 74, 14);
		panel.add(lblIdentificador);

		JButton btnCrear = new JButton("Ingresar");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getRowCount() > 0) {
					for (int i = 0; i < table.getRowCount(); i++) {
						String descripcion = (String) table.getValueAt(i, 0);
						int cantidad = (int) (table.getValueAt(i, 1));
						int valorTotal = (int) (table.getValueAt(i, 2));
						logica.ingresarGasto(descripcion, cantidad, valorTotal, user.getUsuario(), conexion);
					}
					for (int i = table.getRowCount() - 1; i >= 0; i--) {
						((DefaultTableModel) table.getModel()).removeRow(i);
					}
					label.setText("$0,00");
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
				VentanaMenuAdministrar ajuste = new VentanaMenuAdministrar(conexion, user);
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
					NumberFormat formato = NumberFormat.getCurrencyInstance();
					if (table.getSelectedRow() != -1) {
						if ((int) (table.getValueAt(table.getSelectedRow(), 1)) == 1) {
							label.setText(formato.format((long) formato.parse(label.getText())
									- (int) (table.getValueAt(table.getSelectedRow(), 2))
											/ (int) (table.getValueAt(table.getSelectedRow(), 1)))
									+ "");
							((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
						} else {
							int cantidad = (int) (table.getValueAt(table.getSelectedRow(), 1));
							int valorTotal = (int) (table.getValueAt(table.getSelectedRow(), 2));
							label.setText(
									formato.format((long) formato.parse(label.getText()) - valorTotal / cantidad) + "");
							((DefaultTableModel) table.getModel()).setValueAt(
									(int) (((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 1))
											- 1,
									table.getSelectedRow(), 1);

							((DefaultTableModel) table.getModel()).setValueAt(
									(int) (((DefaultTableModel) table.getModel()).getValueAt(table.getSelectedRow(), 2))
											- valorTotal / cantidad,
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
		btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnEliminar.setBounds(127, 217, 89, 23);
		panel.add(btnEliminar);

		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField.setBounds(94, 84, 122, 20);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_1.setBounds(94, 121, 122, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(226, 11, 324, 195);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Descripción", "Cantidad", "Valor Total" }));
		table.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		scrollPane.setViewportView(table);

		JLabel lblValorUnitario = new JLabel("Valor Unitario");
		lblValorUnitario.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblValorUnitario.setBounds(10, 165, 89, 14);
		panel.add(lblValorUnitario);

		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					char tecla = e.getKeyChar();
					if (tecla == KeyEvent.VK_ENTER) {
						if (textField.getText().equals("") && textField_1.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Por favor Ingrese la información");
						} else {
							llenarTabla(textField.getText(), Integer.parseInt(textField_1.getText()),
									Integer.parseInt(textField_2.getText()));
							textField.setText("");
							textField_1.setText("");
							textField_2.setText("");
						}
					}
				} catch (Exception er) {
					JOptionPane.showMessageDialog(null, "Los campos cantidad y valor unitario son numéricos");
				}
			}
		});
		textField_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(94, 162, 122, 20);
		panel.add(textField_2);

		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label.setBounds(94, 36, 122, 23);
		panel.add(label);

		JLabel label_1 = new JLabel("Total");
		label_1.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		label_1.setBounds(10, 36, 56, 23);
		panel.add(label_1);
	}

	public void llenarTabla(String descripcion, int cantidad, int valor) {
		try {
			NumberFormat formato = NumberFormat.getCurrencyInstance();
//			Locale locale = new Locale("es","CO");
//			NumberFormat formato = NumberFormat.getCurrencyInstance(locale);
			Object[] fila = new Object[table.getModel().getColumnCount()];
			fila[0] = descripcion;
			fila[1] = cantidad;
			fila[2] = valor * cantidad;
			((DefaultTableModel) table.getModel()).addRow(fila);
			long numero = (long) formato.parse(label.getText());
			long valor1 = (long) (cantidad * Integer.parseInt(textField_2.getText()));
			long total = numero + valor1;
			label.setText(formato.format(total) + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}