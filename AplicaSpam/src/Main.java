import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class Main {
	
	static Hashtable<String, Integer> palabras= new Hashtable<String, Integer>();	
	static String rutaTraining="/home/jonatan/workspace/extraidoTRAINING/";
	
	public static void main(String[] args) throws Exception{
		ArrayList<String> stopWords = getStopWords();
		ArrayList<String> clavesList = getListaClaves();
		
//		for (int i=0; i<10; i++) {						
//			if (i>=0 && i<=9) {			
//				getCaracteristicas(i,rutaTraining+"TRAIN_0000"+i+".eml", clavesList);				
//			}			
//			if (i>=10 && i<=99) {
//				getCaracteristicas(i,rutaTraining+"TRAIN_000"+i+".eml", clavesList);
//			}
//			if (i>=100 && i<=999) {
//				getCaracteristicas(i,rutaTraining+"TRAIN_00"+i+".eml", clavesList);
//			}
//			if (i>=1000 && i<=9999) {
//				getCaracteristicas(i,rutaTraining+"TRAIN_0"+i+".eml", clavesList);
//			}
//		}
					
//		String str = "helloslkhellodjladfjhello";
//		String findStr = "hello";
//		System.out.println(StringUtils.countMatches(str, findStr));
	}	
	
	public static ArrayList<String> getStopWords () {
		ArrayList<String> stopWords = new ArrayList<String>();
		
		
		return stopWords;
	}
	
	public static ArrayList<String> getListaClaves() throws Exception{
		System.out.println("Encontrando cacterísticas (palabras más frecuentes)...");
		ArrayList<String> clavesList = new ArrayList<String>();
		
		for (int i=0; i<100; i++) {						
			if (i>=0 && i<=9) {		
				getPalabrasFrecuencia(i, rutaTraining+"TRAIN_0000"+i+".eml");							
			}			
			if (i>=10 && i<=99) {
				getPalabrasFrecuencia(i, rutaTraining+"TRAIN_000"+i+".eml");
			}
			if (i>=100 && i<=999) {
				getPalabrasFrecuencia(i, rutaTraining+"TRAIN_00"+i+".eml");
			}
			if (i>=1000 && i<=9999) {
				getPalabrasFrecuencia(i, rutaTraining+"TRAIN_0"+i+".eml");
			}
		}
		
		LinkedHashMap<String, Integer> palabrasOrdenado= ordenarPorValores(palabras);
		Iterator<String> iterator = palabrasOrdenado.keySet().iterator();
		int i=0;
		while (iterator.hasNext() && i!=10) {  
		   String clave = iterator.next().toString();  		   
		   String frecuencia = palabrasOrdenado.get(clave).toString();		   
		   clavesList.add(clave);
		   System.out.println(" Palabra Clave: "+clave + " " + "\tFrecuencia: "+frecuencia);		   
		   i++;
		}  		
		System.out.println("");
		return clavesList;
	}
	
	
//	public static ArrayList<String> getListaClaves(String clavesArch) {
//		ArrayList<String> clavesList = new ArrayList<String>();
//		try {
//			FileInputStream fstream = new FileInputStream(clavesArch);
//			DataInputStream entrada = new DataInputStream(fstream);
//			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));			
//			String strLinea;
//
//			while ((strLinea = buffer.readLine()) != null) 
//				clavesList.add(strLinea);							
//			entrada.close();			
//		} catch (Exception e) {
//			System.err.println("Ocurrio un error: " + e.getMessage());
//		}		
//		return clavesList;
//	}
	
	public static void getPalabrasFrecuencia(int i, String strCorreoArch) throws Exception{
		File correoArch = new File(strCorreoArch);		
		Scanner correoScanArch = new Scanner(new FileReader(correoArch));
		String palabra;
		
		while (correoScanArch.hasNext()){		
		    palabra = correoScanArch.next();		   
		    if(palabras.containsKey(palabra))
		    	palabras.put(palabra, palabras.get(palabra)+1);		    
		    else 
		    	palabras.put(palabra, 1);		    	    		    		    			    				    		    				   		    			   
		}				
		correoScanArch.close();		
	}

	public static void getCaracteristicas(int i, String strCorreoArch, ArrayList<String> clavesList) throws Exception {
		File correoArch = new File(strCorreoArch);		
		Scanner correoScanArch = new Scanner(new FileReader(correoArch));						
				 		
		String palabra, contenido="";
		int cantClaves=0;
		while (correoScanArch.hasNext()){		
		    palabra = correoScanArch.next();		   
		    if(palabras.containsKey(palabra)) {
		    	palabras.put(palabra, palabras.get(palabra)+1);
		    } 
		    else {
		    	palabras.put(palabra, 1);
		    }		    		    
		    contenido=contenido.toLowerCase()+" "+palabra;				    				    		    				   		    			    
		}				
		correoScanArch.close();
		
//		for(int j=0; j<clavesList.size(); j++) {
//	    	if (contenido.contains(clavesList.get(j)))
//	    		cantClaves++;
//	    }
		System.out.println("Correo:"+i+"\tFrases clave:"+cantClaves);	
	}	

	public static <K extends Comparable,V extends Comparable> LinkedHashMap<K,V> ordenarPorValores(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {
            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });      
        
        LinkedHashMap<K,V> sortedMap = new LinkedHashMap<K,V>();        
        for(int i=entries.size()-1; i>=0; --i) {
        	sortedMap.put(entries.get(i).getKey(), entries.get(i).getValue());
        }    
        return sortedMap;
    }
	
}
