import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Main {

	static ArrayList<String> palabras= new ArrayList<String>();
	static Hashtable<String, Integer> pals= new Hashtable<String, Integer>();
	
	public static void main(String[] args) throws Exception{
		String rutaTest="D:\\acc\\workspace\\extraidoTRAINING\\";
		ArrayList<String> clavesList = getListaClaves("/home/jonatan/workspace/claves.txt");
		//getCaracteristicas(0,"/home/jonatan/workspace/prueba.txt", clavesList);
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
		//Map<String, Integer> palsOrdenado= new TreeMap<String, Integer>(pals);
		LinkedHashMap<String, Integer> palsOrdenado= sortByValues(pals);
//		ListIterator list = palsOrdenado.keySet().iterator().
		Iterator iterator = palsOrdenado.keySet().iterator();
		   
		while (iterator.hasNext()) {  
		   String key = iterator.next().toString();  
		   String value = palsOrdenado.get(key).toString();  		   
		   System.out.println(key + " " + value);  
		}  
		
		System.out.println("Fin del Main!!!");
//		Enumeration key = pals.keys();
//		Enumeration elements = pals.elements();
//		while (key.hasMoreElements()) {
//			System.out.println(key.nextElement() + " "+ elements.nextElement());
//		}
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
//        for(Map.Entry<K,V> entry: entries){
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }      
        return sortedMap;
    }
	
}
