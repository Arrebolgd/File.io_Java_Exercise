package solucion;

import java.io.File;

import java.util.Scanner;

public class UI {

	GestorDeArchivos gda;
	Scanner sc;

	public UI(File archivo) {
		sc = new Scanner(System.in);
		gda = new GestorDeArchivos(archivo);
	}

	public void start() {
		System.out.println("Bienvenido al sistema de administración de archivos 😊!");

		LOOP: while (true) {
			escribirRuta();

			System.out.println("""
					¿Qué acción quiere realizar?
						(1) Listar directorios y archivos
						(2) Cambiar de directorio
						(3) Crear directorio
						(4) Crear directorios de manera recursiva
						(5) Borrar directorio
						(6) Borrar directorio de manera recursiva
						(7) Crear archivo
						(8) Borrar archivo
						(9) Salir
					""");

			int entradaUsuario = inputChecker(sc.nextLine());

			switch (entradaUsuario) {
			// Case 0 es un campo de pruebas
			case 0:
				System.out.println(gda.getAbsoluteLocation());
				System.out.println(gda.getLocation());
				break;
			case 1:
				gda.listarTodosLosArchivos(gda.getFileList());
				break;
			case 2:
				cambiarDirectorioMenu();
				break;
			case 3:
				System.out.println("Indique el nombre que le quiera dar al directorio: ");
				System.out.print("▶ ");
				gda.crearDirectorio(sc.nextLine());
				break;
			case 4:
				System.out.println("Indique la ruta completa que quiere crear (se crea desde el directorio en el que se encuentra):");
				System.out.println("▶ ");
				gda.crearDirectorioRec(sc.nextLine());
				break;
			case 5:
				System.out.println("Indique el nombre del directorio que quiere borrar: ");
				gda.listarChildDirectory();
				System.out.print("▶ ");
				gda.borrarDirectorio(sc.nextLine());
				break;
			case 6:
				System.out.println("Funcion DESimplementada, no funciona y es peligrosa 💀");
//				System.out.println("""
//						Indica el nombre del directorio que desea borrar recursivamente:
//						
//						1. 💀 Se borraran todos los archivos contenidos tanto en el directorio como en los subdirectorios.
//						2. 💀 Se borraran tanto el directorio como los subdirectorios.
//						
//						(⌐■_■) < Se pedirá triple confirmación.""");
//				System.out.print("▶ ");
//				String directorio = sc.nextLine();
//				for(int i = 0; i < 3; i++) {
//					System.out.println("Peticion " + (i+1) + ": ¿Desea borrar el directorio: " + directorio + "?");
//					System.out.print("💀 (s/n) ");
//					String uInput = sc.nextLine();
//					if(!uInput.equalsIgnoreCase("s")) break;
//					if(uInput.equalsIgnoreCase("s") && i == 2) gda.borrarDirectorioRec(directorio);
//					
//				}
				break;
			case 7:
				System.out.println("Indique el nombre que le quiere dar al archivo:");
				System.out.print("▷ ");
				gda.crearFichero(sc.nextLine());
				break;
			case 8:
				System.out.println("Indique el nombre del archivo que quiere borrar:");
				gda.listarChildFiles();
				System.out.print("▷ ");
				gda.borrarFichero(sc.nextLine());
				break;
			case 9:
				System.out.println("Saliendo del sistema.");
				break LOOP;
			default:
				System.out.println("(⌐■_■) < Quizas te gustaria revisar mejor los datos introducidos...");
			}
		}
	}

	// Menu de seleccion de directorio
	public void cambiarDirectorioMenu() {

		System.out.println("""

				¿A que escritorio quiere desplazarse?
					(1) Al directorio padre desde su ruta actual.
					(2) Al directorio hijo desde su ruta actual.
					(3) Quiero introducir la dirección completa por teclado.
					(4) Volver atras.
				\n
				""");

		int entradaUsuario = inputChecker(sc.nextLine());

		switch (entradaUsuario) {
		case 1:
			gda.moverAPadre();
			break;
		case 2:
			System.out.println("Estos son los directorios a los que puede desplazarse.");
			System.out.println("¿A que directorio desea ir?");
			gda.listarChildDirectory();
			System.out.print("▶ ");
			if (!gda.moverAHijo(sc.nextLine())) { // Error con String vacio: no escribe el mensaje
				System.out.println("(⌐■_■) < El directorio especificado no existe...");
			}
			break;

		case 3:
			System.out.println("Introduzca la dirección a la que quiere desplazarse.");
			System.out.print("▶ ");
			if (!cambiarDirectorioAccion(sc.nextLine())) {
				System.out.println("(⌐■_■) < El directorio especificado no existe...");
			}
			break;

		case 4:
			break;

		default:
			System.out.println("(⌐■_■) < Caso no contemplado");
		}
	}

	// Verificar entrada de usuario
	public int inputChecker(String uInput) {
		int entradaUsuario;
		try {
			entradaUsuario = Integer.parseInt(uInput);
			
		} catch(NumberFormatException nfe) {
//			System.out.println("error nfe");
//			nfe.printStackTrace();
			System.out.println("A introducido un valor diferente a un número");
			return -1;
		}
		
		return entradaUsuario;
	}
	
	// Escribir ruta
	public void escribirRuta() {
		System.out.printf("""

				Su ruta absoluta es: 
				▶ %s
				Su ruta relativa actual es: 
				▶ %s

				""", gda.getAbsoluteLocation(), gda.getLocation());
	}

	// Introduccion manual de ruta (case 3 de cambiarDirectorioMenu())
	public boolean cambiarDirectorioAccion(String ruta) {
		if (gda.moverDirectorio(ruta)) {
			escribirRuta();
			return true;

		}
		return false;
	}
}
