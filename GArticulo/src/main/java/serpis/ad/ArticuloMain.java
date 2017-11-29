package serpis.ad;

import java.util.Scanner;

public class ArticuloMain {
	//Pruebas de clase	
	public enum Option {Salir, Nuevo, Editar, Eliminar, Consultar, Listar};
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		
		pruebaLong("introduce un numero: ");
	}
		public static long pruebaLong(String prueba) {
			while (true) 
				try {		
					System.out.println(prueba);
					String line = scanner.nextLine();
					return Long.parseLong(line);
				} catch (NumberFormatException ex) {
					System.out.println("Por favor, sólo números. Vuelve a introducir");
					
				}
		}
	}


