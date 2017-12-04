package serpis.ad;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Scanner;



public class ArticuloMain {
	//Pruebas de clase	
	public enum Option {Salir, Nuevo, Editar, Eliminar, Consultar, Listar};
	private static Scanner scanner = new Scanner(System.in);
	public static class Articulo {
		
		private long id;
		private String nombre;
		private BigDecimal precio;
		private long categoria;
	}
	
	
	public static void main(String[] args) {
		scan(Option.class);
//		pruebaLong("introduce un numero: ");
		
		Runnable runnable = () -> nuevo();
		runnable.run();
		
		showFields(Articulo.class);
		
	}
//		public static long pruebaLong(String prueba) {
//			while (true) 
//				try {		
//					System.out.println(prueba);
//					String line = scanner.nextLine();
//					return Long.parseLong(line);
//				} catch (NumberFormatException ex) {
//					System.out.println("Por favor, sólo números. Vuelve a introducir");
//					
//				}
//		}
		
		public static <T extends Enum<T>> T scan(Class<T> enumType) {
			T[] constants = enumType.getEnumConstants();
			for(int index = 0; index < constants.length; index++)
				System.out.printf("%s - %s\n", index, constants[index]);
			String options = String.format("^[0-%s]$", constants.length -1);
			while (true) {
				System.out.println("Elige una opcion: ");
				String line = scanner.nextLine();
				if (line.matches(options))
					return constants[Integer.parseInt(line)];
				System.out.println("Opcion invalida. Vuelve a introducir.");	
				}
		}
		public static void nuevo(){
			//TODO implementar
		}
		
		
		private static void showFields(Class<?> type) {
			for (Field field : type.getDeclaredFields() )
				System.out.printf("%s - %s", field.getName(), field.getType().getName());
		}
		
}


