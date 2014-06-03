import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class Main {
	
	static Hashtable<String, Integer> palabras= new Hashtable<String, Integer>();	
	static String rutaTraining="D:\\acc\\workspace\\extraidoTRAINING\\";
	static String rutaStopWords = rutaTraining + "..\\stopwords.txt";
	static String rutaTrainLabels = rutaTraining + "..\\SPAMTrain.label";
	static ArrayList<String> stopWords = new ArrayList<String>();
	static ArrayList<String> clavesList= new ArrayList<String>();
	static ArrayList<String> etiquetasList = new ArrayList<String>();
	static int numeroCaracteristicas = 40;
	static int numeroCorreosTraining= 4327; //4327 - 2885
	static int numeroCorreosHamTraining = 2949;
	static int numeroCorreosSpamTraining = 1378;
	static double [][] matrizCaracteristicasSpam = new double[numeroCorreosSpamTraining][numeroCaracteristicas];
	static double [][] matrizCaracteristicasHam = new double[numeroCorreosHamTraining][numeroCaracteristicas];
	static int cuentaSpam=0;
	static int cuentaHam=0;
	
	public static void main(String[] args) throws Exception{		
		getListaClaves();
		getListaEtiquetas();
		getVectoresCaracteristicas();		
		
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
	
	public static void getListaEtiquetas() throws Exception {
		File trainLabelsArch = new File(rutaTrainLabels);		
		Scanner trainLabelsScanArch = new Scanner(new FileReader(trainLabelsArch));
		String palabra;
		while (trainLabelsScanArch.hasNext()){		
		    palabra = trainLabelsScanArch.next();
		    if (palabra.equals("0") || palabra.equals("1")) {
		    	etiquetasList.add(palabra);				    	
		    }	    		    			    				    		    				   		    			   
		}
		trainLabelsScanArch.close();					
	}
	
	public static void getVectoresCaracteristicas() throws Exception {
		System.out.println("Encontrando vectores caracteristicas de todos los correos ...");		
		
		for (int i=0; i<numeroCorreosTraining; i++) { //4327 training
			if (i>=0 && i<=9) {		
				getCaracteristicas(i, rutaTraining+"TRAIN_0000"+i+".eml");							
			}			
			if (i>=10 && i<=99) {
				getCaracteristicas(i, rutaTraining+"TRAIN_000"+i+".eml");
			}
			if (i>=100 && i<=999) {
				getCaracteristicas(i, rutaTraining+"TRAIN_00"+i+".eml");
			}
			if (i>=1000 && i<=9999) {
				getCaracteristicas(i, rutaTraining+"TRAIN_0"+i+".eml");
			}
		}				
	}
	
	public static void getCaracteristicas(int indice, String strCorreoArch) throws Exception {
		File correoArch = new File(strCorreoArch);		
		Scanner correoScanArch = new Scanner(new FileReader(correoArch));
		String palabra, contenido="";
		
		while (correoScanArch.hasNext()){		
		    palabra = correoScanArch.next();
		    palabra = palabra.toLowerCase();
		    contenido = contenido + palabra;		    		    	    
		}				
		
		if (etiquetasList.get(indice).equals("0")) {			
			for(int i=0; i<numeroCaracteristicas; i++)
				matrizCaracteristicasSpam[cuentaSpam][i] = StringUtils.countMatches(contenido, clavesList.get(i));
			cuentaSpam++;			
	    } 
	    else {	    	
	    	for(int i=0; i<numeroCaracteristicas; i++)
				matrizCaracteristicasHam[cuentaHam][i] = StringUtils.countMatches(contenido, clavesList.get(i));	    	
	    	cuentaHam++;
	    }		
		correoScanArch.close();	
	}
	
	public static void getStopWords() throws Exception{
		System.out.println("Encontrando lista de stop words ...");
		File stopWordsArch = new File(rutaStopWords);		
		Scanner stopWordsScanArch = new Scanner(new FileReader(stopWordsArch));
		String palabra;
		while (stopWordsScanArch.hasNext()){		
		    palabra = stopWordsScanArch.next();
		    stopWords.add(palabra);			    	    		    		    			    				    		    				   		    			   
		}
		stopWordsScanArch.close();		
	}
	
	public static void getListaClaves() throws Exception{
		getStopWords();
		System.out.println("Encontrando caracteristicas (palabras mas frecuentes) ...");		
		
		for (int i=0; i<numeroCorreosTraining; i++) { //4327 training
			if (i>=0 && i<=9) {		
				getPalabrasFrecuencia(rutaTraining+"TRAIN_0000"+i+".eml");							
			}			
			if (i>=10 && i<=99) {
				getPalabrasFrecuencia(rutaTraining+"TRAIN_000"+i+".eml");
			}
			if (i>=100 && i<=999) {
				getPalabrasFrecuencia(rutaTraining+"TRAIN_00"+i+".eml");
			}
			if (i>=1000 && i<=9999) {
				getPalabrasFrecuencia(rutaTraining+"TRAIN_0"+i+".eml");
			}
		}
		
		LinkedHashMap<String, Integer> palabrasOrdenado= ordenarPorValores(palabras);
		Iterator<String> iterator = palabrasOrdenado.keySet().iterator();
	
		while (iterator.hasNext()) {  
		   String clave = iterator.next().toString();  		   
		   String frecuencia = palabrasOrdenado.get(clave).toString();		   
		   clavesList.add(clave);
		   System.out.println(" Palabra Clave: "+clave + " " + "\t\tFrecuencia: "+frecuencia);		   		   
		}  		
		stopWords.clear();
		System.out.println("");		
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
	
	public static void getPalabrasFrecuencia(String strCorreoArch) throws Exception{
		File correoArch = new File(strCorreoArch);		
		Scanner correoScanArch = new Scanner(new FileReader(correoArch));
		String palabra;
		
		while (correoScanArch.hasNext()){		
		    palabra = correoScanArch.next();
		    palabra = palabra.toLowerCase();
		    palabra = palabra.replace(".", "");
		    palabra = palabra.replace(",", "");
		    
		    if (!stopWords.contains(palabra)) {
			    if(palabras.containsKey(palabra))
			    	palabras.put(palabra, palabras.get(palabra)+1);		    
			    else 
			    	palabras.put(palabra, 1);		
		    }
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
        for(int i=entries.size()-1, j=0; i>=0 && j!=numeroCaracteristicas; --i, j++) {
        	sortedMap.put(entries.get(i).getKey(), entries.get(i).getValue());
        }    
        return sortedMap;
    }
	
}
