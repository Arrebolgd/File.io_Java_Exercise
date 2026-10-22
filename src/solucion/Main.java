package solucion;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		UI userInterface = new UI(new File("."));
		
		userInterface.start();
		
		/*
		 * Entradas de prueba:
		 * 	C:\Users\Arrebol\Desktop
		 */
	}
}
