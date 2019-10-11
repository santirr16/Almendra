package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import jbarcodebean.JBarcodeBean;
import logica.Conexion;
import logica.Logica;
import logica.Usuario;
import net.sourceforge.jbarcodebean.model.Interleaved25;

public class VentanaImprimirCodigos extends JFrame {

	private JPanel contentPane;
	Conexion conexion;
	Usuario user;
	long turno;
	Logica logica = new Logica();
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public VentanaImprimirCodigos(Conexion conexion, Usuario user, long turno) {

		this.conexion = conexion;
		this.user = user;
		this.turno = turno;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaImprimirCodigos.class.getResource("/imagenes/Logo viejo.png")));
		setTitle("Impresi\u00F3n Codigos");
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

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaImprimirCodigos.class.getResource("/imagenes/Logo.png")));
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
				VentanaMenuVender menu = new VentanaMenuVender(conexion, user, turno);
				menu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnVolver.setBounds(229, 186, 162, 38);
		panel.add(btnVolver);

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
				try {
					if (logica.buscarProducto(Long.parseLong(textField.getText()), conexion) != null) {
						generarCodigo();
					} else {
						JOptionPane.showMessageDialog(null, "El producto no existe");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"Verifique la información ingresada");
				}
			}
		});
		btnImprimirCodigos.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnImprimirCodigos.setBounds(29, 186, 167, 38);
		panel.add(btnImprimirCodigos);

		JLabel lblCodigoProducto = new JLabel("Codigo Producto");
		lblCodigoProducto.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblCodigoProducto.setBounds(53, 138, 106, 24);
		panel.add(lblCodigoProducto);

		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		textField.setBounds(190, 141, 201, 21);
		panel.add(textField);
		textField.setColumns(10);
	}

	public void generarCodigo() {
		try {
			JFileChooser file = new JFileChooser();
			FileNameExtensionFilter filtroImagen=new FileNameExtensionFilter("PNG","png");
			file.setFileFilter(filtroImagen);
			file.showSaveDialog(null);
			File guarda = file.getSelectedFile();

			JBarcodeBean barcode = new JBarcodeBean();
			barcode.setCodeType(new Interleaved25());
			barcode.setCode(textField.getText());
			BufferedImage bufferedImage = barcode.draw(new BufferedImage(300, 100, BufferedImage.TYPE_3BYTE_BGR));
			
			ImageIO.write(bufferedImage, "png", new File(guarda.getPath() + ".png"));
			JOptionPane.showMessageDialog(null, "Codigo de barras generado");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "No se pudo generar el codigo de barras");
		}
	}
}
