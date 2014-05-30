import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class Main {

	static ArrayList<String> palabras= new ArrayList<String>();
	static Hashtable<String, Integer> pals= new Hashtable<String, Integer>();
	
	public static void main(String[] args) throws Exception{
		String rutaTest="D:\\acc\\workspace\\extraidoTRAINING\\";
		ArrayList<String> clavesList = getListaClaves("/home/jonatan/workspace/claves.txt");		
		for (int i=0; i<10; i++) {						
			if (i>=0 && i<=9) {			
				getCaracteristicas(i,rutaTest+"TRAIN_0000"+i+".eml", clavesList);				
			}			
			if (i>=10 && i<=99) {
				getCaracteristicas(i,rutaTest+"TRAIN_000"+i+".eml", clavesList);
			}
			if (i>=100 && i<=999) {
				getCaracteristicas(i,rutaTest+"TRAIN_00"+i+".eml", clavesList);
			}
			if (i>=1000 && i<=9999) {
				getCaracteristicas(i,rutaTest+"TRAIN_0"+i+".eml", clavesList);
			}
		}
		
		LinkedHashMap<String, Integer> palsOrdenado= sortByValues(pals);
		Iterator iterator = palsOrdenado.keySet().iterator();
		int i=0;
		while (iterator.hasNext() && i!=10) {  
		   String key = iterator.next().toString();  
		   String value = palsOrdenado.get(key).toString();  		   
		   System.out.println(key + " " + value);  
		   i++;
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
	
	public static void getCaracteristicas(int i, String strCorreoArch, ArrayList<String> clavesList) throws Exception {
		File correoArch = new File(strCorreoArch);		
		Scanner correoScanArch = new Scanner(new FileReader(correoArch));						
				 		
		String palabra, contenido="";
		int cantClaves=0;
		while (correoScanArch.hasNext()){		
		    palabra = correoScanArch.next();		   
		    if(pals.containsKey(palabra)) {
		    	pals.put(palabra, pals.get(palabra)+1);
		    }else
		    {
		    	pals.put(palabra, 1);
		    }		    		    
		    contenido=contenido.toLowerCase()+" "+palabra;				    				    		    				   		    			    
		}				
		correoScanArch.close();
		
//		for(int j=0; j<clavesList.size(); j++) {
//	    	if (contenido.contains(clavesList.get(j)))
//	    		cantClaves++;
//	    }
		System.out.println("Correo:"+i+"\t\tFrases clave:"+cantClaves);	
	}	

	public static <K extends Comparable,V extends Comparable> LinkedHashMap<K,V> sortByValues(Map<K,V> map){
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
