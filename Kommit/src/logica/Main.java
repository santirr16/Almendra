package logica;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Main {

	/**
	 * Metodo main para ejecutar el codigo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(
				JOptionPane.showInputDialog("Ingrese por favor la cantidad de filas y columnas que tendra la matriz"));

		int[][] matriz = new int[n][n];

		resolverMatriz(n);

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				matriz[i][j] = Integer.parseInt(
						JOptionPane.showInputDialog(null, "Ingrese por favor el valor de la casilla " + i + "," + j));
			}
		}
		JOptionPane.showMessageDialog(null, "El costo minimo de la matriz es: " + determinarCosto(matriz));
	}

	/**
	 * Metodo que me permite resolver la matriz mágica recibiendo la cantidad N de
	 * filas y columnas
	 * 
	 * @param n, numero de filas y columnas
	 */
	public static void resolverMatriz(int n) {
		int i = 0;
		int j = 0;
		int medio = n / 2;
		int[][] matriz = new int[n][n];
		int temporal = 1;

		for (int k = 0; k < matriz.length; k++) {
			for (int l = 0; l < matriz.length; l++) {
				matriz[k][l] = 0;
			}
		}
		int k = 0;
		int l = medio;
		while (temporal != (n * n) + 1) {
			if (matriz[k][l] == 0) {
				matriz[k][l] = temporal;
			} else {
				k = i + 1;
				l = j;
				matriz[k][l] = temporal;
			}

			i = k;
			j = l;
			temporal++;
			l++;
			k--;

			if (k < 0 && l == n) {
				k = n - 1;
				l = 0;
			} else if (k < 0) {
				k = k + n;

			} else if (l == n) {
				l = 0;
			}
		}
		String resultado = "";
		for (int k1 = 0; k1 < matriz.length; k1++) {
			for (int k2 = 0; k2 < matriz.length; k2++) {
				resultado += matriz[k1][k2] + " ";
			}
			resultado += '\n';
		}
		JOptionPane.showMessageDialog(null, "La matriz mágica es la siguiente:" + "\n" + resultado);
	}

	public static int determinarCosto(int[][] matriz) {
		int numeroMagico = (matriz.length * ((int) (Math.pow(matriz.length, 2)) + 1)) / 2;
		int resultado = 0;
		int suma = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				suma += matriz[i][j];
			}
			if (suma < numeroMagico) {
				resultado += numeroMagico - suma;
			} else if (suma > numeroMagico) {
				resultado += suma - numeroMagico;
			}
			suma = 0;
		}
		return resultado;
	}
}
