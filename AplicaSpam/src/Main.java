import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

public class Main {
	
	static Hashtable<String, Integer> palabras= new Hashtable<String, Integer>();	
	static String rutaTraining="/home/jonatan/workspace/extraidoTRAINING/";
	static String rutaStopWords = rutaTraining + "../stopwords.txt";
	static String rutaTrainLabels = rutaTraining + "../SPAMTrain.label";
	static ArrayList<String> stopWords = new ArrayList<String>();
	static ArrayList<String> clavesList= new ArrayList<String>();
	static ArrayList<String> etiquetasList = new ArrayList<String>();
	static int numeroCaracteristicas = 30;
	static int numeroCorreosTraining= 4327; //4327 - 2885
	static int numeroCorreosHamTraining = 2949;
	static int numeroCorreosSpamTraining = 1378;
	static double [][] matrizCaracteristicasSpam = new double[numeroCorreosSpamTraining][numeroCaracteristicas];
	static double [][] matrizCaracteristicasHam = new double[numeroCorreosHamTraining][numeroCaracteristicas];
	static double [][] vectorMediaSpam = new double[numeroCaracteristicas][1];
	static double [][] vectorMediaHam = new double[numeroCaracteristicas][1];
	static double [][] matrizCovarianzaSpam;
	static double [][] matrizCovarianzaHam;
	static int cuentaSpam=0;
	static int cuentaHam=0;
	
	public static void main(String[] args) throws Exception{		
		getListaClaves();
		getListaEtiquetas();
		getVectoresCaracteristicas();		
		getVectoresMedia();
		getMatricesCovarianza();			
		
//		for(int i=0; i<numeroCorreosSpamTraining; i++) {
//			for (int j=0; j<numeroCaracteristicas; j++) {
//				System.out.print(matrizCaracteristicasSpam[i][j] + " ");
//			}
//			System.out.println("");
//		}
		
		double [][] x= new double[numeroCaracteristicas][1];
		for(int i=0; i<numeroCaracteristicas; i++) {
			x[i][0]=matrizCaracteristicasSpam[2][i];
		}
		
		System.out.println("Probabilidad Spam = "+funcionClasificadoraSpam(x));
		System.out.println("Probabilidad Ham = "+funcionClasificadoraHam(x));				
	}	
	
	public static double funcionClasificadoraSpam(double [][] x) {		
		RealMatrix vectorMedia = MatrixUtils.createRealMatrix(vectorMediaSpam);
    	RealMatrix matrizCovarianza = MatrixUtils.createRealMatrix(matrizCovarianzaSpam);    	   	    	
    	RealMatrix matrizCovInv = new LUDecomposition(matrizCovarianza).getSolver().getInverse();
    	RealMatrix vector = MatrixUtils.createRealMatrix(x);
    	RealMatrix subsMatriz = vector.subtract(vectorMedia);    	
    	RealMatrix transp = subsMatriz.transpose();
    	double determ = new LUDecomposition(matrizCovarianza).getDeterminant();
    	int d = numeroCaracteristicas;
    	RealMatrix mult1 = matrizCovInv.preMultiply(transp);
    	RealMatrix mult2 = subsMatriz.preMultiply(mult1);
    	double[][]data = mult2.getData();
    	double valor = 2*3.141591;
    	double logarit = new Log().value(valor);
    	double logarit2 = new Log().value(determ);
    	    	
		return -1*0.5*data[0][0] - (d/2)*logarit - 0.5*logarit2+100;
	}
	
public static double funcionClasificadoraHam(double [][] x) {		
		RealMatrix vectorMedia = MatrixUtils.createRealMatrix(vectorMediaHam);
    	RealMatrix matrizCovarianza = MatrixUtils.createRealMatrix(matrizCovarianzaHam);    	   	    	
    	RealMatrix matrizCovInv = new LUDecomposition(matrizCovarianza).getSolver().getInverse();
    	RealMatrix vector = MatrixUtils.createRealMatrix(x);
    	RealMatrix subsMatriz = vector.subtract(vectorMedia);    	
    	RealMatrix transp = subsMatriz.transpose();
    	double determ = new LUDecomposition(matrizCovarianza).getDeterminant();
    	int d = numeroCaracteristicas;
    	RealMatrix mult1 = matrizCovInv.preMultiply(transp);
    	RealMatrix mult2 = subsMatriz.preMultiply(mult1);
    	double[][]data = mult2.getData();
    	double valor = 2*3.141591;
    	double logarit = new Log().value(valor);
    	double logarit2 = new Log().value(determ);
    	    	
		return -1*0.5*data[0][0] - (d/2)*logarit - 0.5*logarit2+100;
	}
	
	public static void getMatricesCovarianza() {
		System.out.println("Encontrando matrices covarianza ...");
		Covariance covarianzaSpam = new Covariance(matrizCaracteristicasSpam, false);
		RealMatrix realCovarianzaSpam = covarianzaSpam.getCovarianceMatrix();
    	matrizCovarianzaSpam = realCovarianzaSpam.getData();
		
    	Covariance covarianzaHam = new Covariance(matrizCaracteristicasHam, false);
		RealMatrix realCovarianzaHam = covarianzaHam.getCovarianceMatrix();
    	matrizCovarianzaHam = realCovarianzaHam.getData();		
	}
	
	public static void getVectoresMedia() {
		System.out.println("Encontrando vectores media ...");
		for(int i=0; i<numeroCaracteristicas; i++)
			vectorMediaHam[i][0]=0;
		
		for(int i=0; i<numeroCaracteristicas; i++)
			vectorMediaSpam[i][0]=0;
					
		for(int i=0; i<numeroCorreosHamTraining; i++) {
			for(int j=0; j<numeroCaracteristicas; j++) {
				vectorMediaHam[j][0]=vectorMediaHam[j][0]+matrizCaracteristicasHam[i][j];
			}
		}
				
		for(int i=0; i<numeroCorreosSpamTraining; i++) {
			for(int j=0; j<numeroCaracteristicas; j++) {
				vectorMediaSpam[j][0]=vectorMediaSpam[j][0]+matrizCaracteristicasSpam[i][j];
			}
		}		

		for(int i=0; i<numeroCaracteristicas; i++)
			vectorMediaHam[i][0]=vectorMediaHam[i][0]/numeroCorreosHamTraining;
		
		for(int i=0; i<numeroCaracteristicas; i++)
			vectorMediaSpam[i][0]=vectorMediaSpam[i][0]/numeroCorreosSpamTraining;
	}
	
	public static void getListaEtiquetas() throws Exception {
		System.out.println("Encontrando lista de etiquetas de training ...");
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
		Random r = new Random();
		if (etiquetasList.get(indice).equals("0")) {
			for(int i=0; i<numeroCaracteristicas; i++)
				matrizCaracteristicasSpam[cuentaSpam][i] = r.nextFloat()/1000.0 +StringUtils.countMatches(contenido, clavesList.get(i));
			cuentaSpam++;
		}
	    else {
	    	for(int i=0; i<numeroCaracteristicas; i++)
				matrizCaracteristicasHam[cuentaHam][i] = r.nextFloat()/1000.0+StringUtils.countMatches(contenido, clavesList.get(i));
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
		   System.out.println("  Palabra Clave: "+clave + " " + "\t\tFrecuencia: "+frecuencia);		   		   
		}  		
		stopWords.clear();		
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
