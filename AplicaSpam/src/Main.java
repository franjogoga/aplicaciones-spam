import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception{
		String rutaTest="/home/jonatan/workspace/extraidoTEST/";
		ArrayList<String> clavesList = getListaClaves("/home/jonatan/workspace/claves.txt");
		
		for (int i=0; i<4292; i++) {						
			if (i>=0 && i<=9) {			
				getCaracteristicas(i,rutaTest+"TEST_0000"+i+".eml", clavesList);				
			}			
			if (i>=10 && i<=99) {
				getCaracteristicas(i,rutaTest+"TEST_000"+i+".eml", clavesList);
			}
			if (i>=100 && i<=999) {
				getCaracteristicas(i,rutaTest+"TEST_00"+i+".eml", clavesList);
			}
			if (i>=1000 && i<=9999) {
				getCaracteristicas(i,rutaTest+"TEST_0"+i+".eml", clavesList);
			}							
		}
	}	
	
	public static ArrayList<String> getListaClaves(String clavesArch) {
		ArrayList<String> clavesList = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(clavesArch);
			DataInputStream entrada = new DataInputStream(fstream);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));			
			String strLinea;

			while ((strLinea = buffer.readLine()) != null) 
				clavesList.add(strLinea);				
			
			entrada.close();
			
		} catch (Exception e) {
			System.err.println("Ocurrio un error: " + e.getMessage());
		}		
		return clavesList;
	}
	
	public static void getCaracteristicas(int i, String correoArch, ArrayList<String> clavesList) throws Exception {
		File file = new File(correoArch);		
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
	    	if (contenido.contains(clavesList.get(j)))
	    		cantClaves++;
	    }
		
		System.out.println("Correo:"+i+"\tPalabras:"+cantPalabras+"\t\tFrases clave:"+cantClaves);
	}
	

}
