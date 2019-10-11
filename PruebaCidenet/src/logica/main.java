package logica;

import javax.swing.JOptionPane;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, sumaCadena());
		cantidadDulces();
	}

	/**
	 * Metodo que suma una cadena de numeros separa por espacios ingresada por el
	 * usuario
	 * 
	 * @return, retorna la suma de la cadena ingresada
	 */
	public static String sumaCadena() {
		String cadena = JOptionPane.showInputDialog(null, "Ingrese por favor la cadena a sumar separada por espacios");
		String[] arregloCadena = cadena.split(" ");// arreglo con la cadena separada por espacios
		double suma = 0;
		for (int i = 0; i < arregloCadena.length; i++) {
			if (!arregloCadena[i].equals("")) {// validación para no sumar campos vacios
				try {
					suma += Double.parseDouble(arregloCadena[i]);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "La información ingresada no corresponde a datos númericos");
					sumaCadena();
				}
			}
		}
		return "El resultado de la suma es: " + suma; // devuelve como resultado 0 si no ingresan información o si la
														// suma da como resultado 0
	}

	/**
	 * Metodo para calcular la cantidad dulces para entregar a Valeria
	 */
	public static void cantidadDulces() {
		try {
			int n = Integer.parseInt(JOptionPane.showInputDialog(null, "Lucia ingrese por favor el valor de N"));
			if (n % 2 == 0) {
				suma(n / 2, n / 2, n, 0, n / 2, n / 2);
			} else {
				suma(n / 2, (n / 2) + 1, n, 0, n / 2, (n / 2) + 1);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "La información ingresada no corresponde a datos númericos");
			cantidadDulces();
		}
	}

	/**
	 * metodo recursivo para comparar todas las opciones
	 * @param mayor
	 * @param menor
	 * @param n
	 * @param resultado
	 * @param mayorActual
	 * @param menorActual
	 */
	public static void suma(int mayor, int menor, int n, int resultado, int mayorActual, int menorActual) {
		String binarioMayor = convertir(mayor);
		String binarioMenor = convertir(menor);
		int suma = 0;
		for (int i = 0; i < binarioMayor.length(); i++) {
			if (binarioMayor.charAt(i) == '1') {
				suma++;
			}
		}
		for (int i = 0; i < binarioMenor.length(); i++) {
			if (binarioMenor.charAt(i) == '1') {
				suma++;
			}
		}
		System.out.println(suma);
		if (suma >= resultado) {
			if (menor > 0 && mayor < n) {
				suma(mayor + 1, menor - 1, n, suma, mayor, menor);
			} else {
				JOptionPane.showMessageDialog(null, "Los números que mayor cantidad de dulces pueden obtener son: "
						+ mayorActual + "+" + menorActual);
			}
		} else {
			if (menor > 0 && mayor < n) {
				suma(mayor + 1, menor - 1, n, resultado, mayorActual, menorActual);
			} else {
				JOptionPane.showMessageDialog(null,
						"Los números que mayor cantidad de dulces pueden obtener son: " + mayorActual + "+"
								+ menorActual);
			}
		}
	}

	/**
	 * metodo para convertir un numero de decimal a binario
	 * @param numero
	 * @return
	 */
	public static String convertir(int numero) {
		String resultado = "";
		if (numero > 0) {// conversion para numero Mayor
			while (numero > 0) {
				if (numero % 2 == 0) {
					resultado = "0" + resultado;
				} else {
					resultado = "1" + resultado;
				}
				numero = (int) numero / 2;
			}
		} else if (numero == 0) {
			resultado = "0";
		} else {
			resultado = "No se pudo convertir el numero. Ingrese solo números positivos";
		}
		return resultado;
	}
}
