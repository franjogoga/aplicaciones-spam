import java.io.*;
import java.util.*;

public class Main {

	static ArrayList<String> palabras= new ArrayList<String>();
	static Hashtable<String, Integer> pals= new Hashtable<String, Integer>();
	
	public static void main(String[] args) throws Exception{
		String rutaTest="/home/jonatan/workspace/extraidoTEST/";
		ArrayList<String> clavesList = getListaClaves("/home/jonatan/workspace/claves.txt");
		//getCaracteristicas(0,"/home/jonatan/workspace/prueba.txt", clavesList);
		for (int i=0; i<10; i++) {						
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
		
		System.out.println();
		Enumeration key = pals.keys();
		Enumeration elements = pals.elements();
		while (key.hasMoreElements()) {
			System.out.println(key.nextElement() + " "+ elements.nextElement());
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
		    if(pals.containsKey(theWord)) {
		    	pals.put(theWord, pals.get(theWord)+1);
		    }else
		    {
		    	pals.put(theWord, 1);
		    }		    		    
		    contenido=contenido.toLowerCase()+" "+theWord;				    				    		    				   
		    cantPalabras++;				    
		}				
		scanFile.close();
		
//		for(int j=0; j<clavesList.size(); j++) {
//	    	if (contenido.contains(clavesList.get(j)))
//	    		cantClaves++;
//	    }
		
		System.out.println("Correo:"+i+"\tPalabras:"+cantPalabras+"\t\tFrases clave:"+cantClaves);
		
	}
	

}
