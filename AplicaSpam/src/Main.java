import java.io.*;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

public class Main {

	public static void main(String[] args) throws Exception{

		try {
			FileInputStream fstream = new FileInputStream("D:\\acc\\CSDMC2010_SPAM\\ExtractContent.py"); // Abrimos el archivo
			DataInputStream entrada = new DataInputStream(fstream); // Creamos el objeto de entrada
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada)); // Creamos el Buffer de Lectura
			String strLinea;

			while ((strLinea = buffer.readLine()) != null) { // Leer el archivo linea por linea				
				// System.out.println (strLinea); // Imprimimos la línea por pantalla
			}
			entrada.close(); // Cerramos el archivo
		} catch (Exception e) { // Catch de excepciones
			System.err.println("Ocurrio un error: " + e.getMessage());
		}
		
		display(new File("D:\\acc\\CSDMC2010_SPAM\\TESTING\\TEST_00000.eml"));		
	}

	public static void display(File emlFile) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.host", "smtp.dummydomain.com");
		props.put("mail.transport.protocol", "smtp");

		Session mailSession = Session.getDefaultInstance(props, null);
		InputStream source = new FileInputStream(emlFile);
		MimeMessage message = new MimeMessage(mailSession, source);

		System.out.println("Subject : " + message.getSubject());			
		System.out.println("Body : " + message.getContent());
	}

}
