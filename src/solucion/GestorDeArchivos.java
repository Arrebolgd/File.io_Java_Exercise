package solucion;

import java.io.File;
import java.io.IOException;

public class GestorDeArchivos {

	private File localizacion;
	private static int profundidad = 0;
	
	public GestorDeArchivos(File localizacion) {
		this.localizacion = localizacion;
	}
	
	public String getLocation() {
		try {
			return localizacion.getCanonicalFile().getName() + File.separator;
			
		} catch (IOException e) {
			System.out.println("ioe error - getLocalizacion");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getAbsoluteLocation() {
		return localizacion.getAbsolutePath();
	}
	
	public File[] getFileList() {
		return localizacion.listFiles();
	}
	
	// Crear directorio 
	public void crearDirectorio(String name) {
		if(name != null && !name.isEmpty()) {
			File directorio = new File(name);
			
			boolean creado = directorio.mkdir();
			
			if(creado) {
				System.out.println("Directorio creado");
				listarChildDirectory();
			}else if(directorio.exists()){
				System.out.println("(⌐■_■) < El directorio ya existe.");
			} else {
				System.out.println("(⌐■_■) < No se pudo crear el directorio.");
			}
		}
	}
	
	// Crear directorio de manera recursiva
	public void crearDirectorioRec(String name) {
		if(name != null && !name.isEmpty()) {
			File directorio = new File(name);
			
			boolean creado = directorio.mkdirs();
			
			if(creado) {
				System.out.println("Directorio creado");
				listarChildDirectory();
			}else if(directorio.exists()){
				System.out.println("(⌐■_■) < El directorio ya existe.");
			} else {
				System.out.println("(⌐■_■) < No se pudo crear el directorio.");
			}
		}
	}
	
	// Borrar directorio
	public void borrarDirectorio(String name) {
		if(name != null && !name.isEmpty()) {
			
			for(File files : localizacion.listFiles()) {
				if(files.isDirectory() && files.getName().contains(name)) {
					files.delete();
					System.out.println("El directorio ha sido borrado");
					listarChildDirectory();
					return;
				}
			}
			System.out.println("(⌐■_■) < Directorio no encontrado");
			return;
		}
		System.out.println("(⌐■_■) < Error en el nombre del directorio");
	}
	
	// Borrar directorio de manera recursiva, NO USAR BAJO NINGUN CONCEPTO
	public void borrarDirectorioRec(String name) {
		
		File directory = new File(name);
		
		if(name != null && !name.isEmpty()) {
			for(File files : directory.listFiles()) {
				
				if(files.isFile() && files.exists()) files.delete();
				
				if(files.isDirectory() && files.exists()) {
					borrarDirectorioRec(directory.getName());
					files.delete();
				}
			}
		}
	}
	
	// Creacion de fichero, self contained
	public void crearFichero(String name) {
		File newFile = new File(name);
		try {
			boolean creado = newFile.createNewFile();
			if(creado) {
				System.out.println("Archivo creado!");
				listarChildFiles();
			} else {
				System.out.println("(⌐■_■) < El archivo ya existe...");
			}
		} catch (IOException ioe) {
			System.out.println("(⌐■_■) < Ha habido un error durante la creación del archivo: ioe");
		}
	}
	
	// Borrar fichero
	public void borrarFichero(String name) {
		if(name != null && !name.isEmpty()) {
			for(File files: localizacion.listFiles()) {
				if(files.isFile() && files.getName().contains(name)) {
					files.delete();
					System.out.println("El archivo ha sido borrado.");
					listarChildFiles();
					return;
				}
			}
			System.out.println("(⌐■_■) < Archivo no encontrado.");
			return;
		}
		System.out.println("(⌐■_■) < Error en el nombre del archivo.");
	}
	
	// Listar archivos contenidos en el directorio actual
	public void listarChildFiles() {
		for(File files : localizacion.listFiles()) {
			if(files.isFile()) {
				System.out.println("f:▷ " + files.getName());
			}
		}
	}
	
	// Listar directorios hijo
	public void listarChildDirectory() {
		for(File file : localizacion.listFiles()) {
			if(file.isDirectory()) {
				System.out.println("D:▶ " + file.getName());
			}
		}
	}
	
	// Listar directorios y archivos
	public void listarTodosLosArchivos(File[] fileList) {

		profundidad++;
		for (File archdir : fileList) {
			
			if(archdir.isDirectory()) {
				System.out.println("----");
				for (int i = 0; i < profundidad; i++) {
					System.out.print("▶");
				}
				System.out.println(archdir.getName());
				listarTodosLosArchivos(archdir.listFiles());
			}
			
			if(archdir.isFile()) {
				for (int i = 0; i < profundidad; i++) {
					System.out.print("▷");
				}
				System.out.println(archdir.getName());
			}
		}
		profundidad--;
	}
	
	// Moverse al directorio padre = atras
	public boolean moverAPadre() {
		// Debug localizacion.getAbsolutePath() C:\.
		if(this.getLocation().equals(File.separator)) {
			return false;
		}
		return moverDirectorio(localizacion.getAbsolutePath().replace(this.getLocation(), "\\."));
		
	}
	// Moverse al siguiente sub directorio especificado
	public boolean moverAHijo(String ruta) {
		return moverDirectorio(localizacion.getAbsolutePath().replace(".", (ruta + "\\.")));
	}
	
	// Moverse a un directorio especificado por ruta
	public boolean moverDirectorio(String ruta) {
		if(ruta != null && !ruta.isEmpty()) {
			File nuevaLocalizacion = new File(ruta);
			
			if(nuevaLocalizacion.exists()) {
				localizacion = nuevaLocalizacion;
				return true;
			}
		}
		return false;
	}
}
