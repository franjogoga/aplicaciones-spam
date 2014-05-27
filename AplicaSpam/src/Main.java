import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception{
		ArrayList<String> clavesList = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream("/home/jonatan/workspace/claves.txt");
			DataInputStream entrada = new DataInputStream(fstream);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));			
			String strLinea;

			while ((strLinea = buffer.readLine()) != null) 
				clavesList.add(strLinea);				
			
			entrada.close();
			
		} catch (Exception e) {
			System.err.println("Ocurrio un error: " + e.getMessage());
		}			
		
		for (int i=0; i<4292; i++) {
			
			if (i>=0 && i<=5) {			
				File file = new File("/home/jonatan/workspace/extraidoTEST/TEST_0000"+i+".eml");		
				Scanner scanFile = new Scanner(new FileReader(file));						
						 		
				String theWord, contenido="";
				int cantPalabras=0, cantClaves=0;
				while (scanFile.hasNext()){		
				    theWord = scanFile.next();
				    contenido=contenido.toLowerCase()+" "+theWord;				    				    		    				   
				    cantPalabras++;				    
				}				
				scanFile.close();
				
				for(int j=0; j<clavesList.size(); j++) {
			    	if (contenido.contains(clavesList.get(j))) {cantClaves++; System.out.println(j + " "+clavesList.get(j));}
			    }
				
				System.out.println("Correo:"+i+"\tNúmero de palabras:"+cantPalabras+"\t\tNúmero de frases clave:"+cantClaves);
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
