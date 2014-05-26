import java.io.*;
import javax.mail.*;

public class Main {

	public static void main(String[] args) {
				
		try{            
            FileInputStream fstream = new FileInputStream("D:\\acc\\CSDMC2010_SPAM\\ExtractContent.py");  	// Abrimos el archivo
            DataInputStream entrada = new DataInputStream(fstream); 	// Creamos el objeto de entrada            
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada)); 	// Creamos el Buffer de Lectura
            String strLinea;
            
            while ((strLinea = buffer.readLine()) != null) // Leer el archivo linea por linea                
                System.out.println (strLinea); // Imprimimos la línea por pantalla
                       
            entrada.close(); // Cerramos el archivo
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }			
		
	}

}
