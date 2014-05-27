import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception{

//		try {
//			FileInputStream fstream = new FileInputStream("D:\\acc\\CSDMC2010_SPAM\\ExtractContent.py"); // Abrimos el archivo
//			DataInputStream entrada = new DataInputStream(fstream); // Creamos el objeto de entrada
//			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada)); // Creamos el Buffer de Lectura
//			String strLinea;
//
//			while ((strLinea = buffer.readLine()) != null) { // Leer el archivo linea por linea				
//				// System.out.println (strLinea); // Imprimimos la línea por pantalla
//			}
//			entrada.close(); // Cerramos el archivo
//		} catch (Exception e) { // Catch de excepciones
//			System.err.println("Ocurrio un error: " + e.getMessage());
//		}			
		
			
		for (int i=0; i<4292; i++) {
			
			if (i>=0 && i<=9) {
				File file = new File("/home/jonatan/workspace/extraidoTEST/TEST_0000"+i+".eml");		
				Scanner scanFile = new Scanner(new FileReader(file));		
				ArrayList<String> words = new ArrayList<String>();
						 		
				String theWord;
				int cantPalabras=0;
				while (scanFile.hasNext()){		
				    theWord = scanFile.next();		
				    //words.add(theWord);
				    cantPalabras++;
				    //System.out.println(theWord+ " "+j);
				}				
				scanFile.close();
				System.out.println("Correo:"+i+"\tNúmero de palabras: "+cantPalabras);
			}
			if (i>=10 && i<=99) {
				
			}
			if (i>=100 && i<=999) {
				
			}
			if (i>=1000 && i<=9999) {
				
			}							
		}
	}	

}
