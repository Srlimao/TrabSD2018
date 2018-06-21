package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Base64;
import java.util.HashMap;

public class Util {

	public static void main(String args[]){
		
	}
	public static String fileToString(String path) throws Exception {
		
		File originalFile = new File(path);
		String retorno = null;		
		FileInputStream reader = new FileInputStream(originalFile);
        byte[] bytes = new byte[(int)originalFile.length()];
        reader.read(bytes);
        retorno = new String(bytes);
        reader.close();
		
		return retorno;
	}
	public static String fileToBase64(String path) throws Exception {
		File originalFile = new File(path);
		String base64 = null;

		FileInputStream reader = new FileInputStream(originalFile);
        byte[] bytes = new byte[(int)originalFile.length()];
        reader.read(bytes);
        base64 = new String(Base64.getEncoder().encodeToString(bytes));
        reader.close();
		return base64; 
	}

	public static void base64ToFile(String base64, String path)  throws Exception{

		byte[] bytes = Base64.getDecoder().decode(base64);

		FileOutputStream writer = new FileOutputStream(path);
		writer.write(bytes);
		writer.close();
	}
	
	public static void teste(String base64, String path) throws Exception{
		byte[] bytes = Base64.getDecoder().decode(base64);
		
		for(byte b : bytes){
			
			System.out.print(((char) b) + "     " + b + "     " + Integer.toBinaryString(b));
			System.out.println();
		}
		
		FileOutputStream writer = new FileOutputStream(path);
		writer.write(bytes);
		writer.close();
	}
	
	public static HashMap<String,String> carregaConfig(String caminhoArquivo)throws Exception{

		HashMap<String,String> retorno = new HashMap<String,String>();

		File arquivo = new File(caminhoArquivo);
		BufferedReader br = new BufferedReader(new FileReader(arquivo));
				for(String line; (line = br.readLine()) != null; ) {
			    	String[] aux = line.split("=");
			    	retorno.put(aux[0],aux[1]);
			    }
				br.close();
		return retorno;
	}
	
	public static boolean fileExists(String path) {
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}else return false;
	}
	
	public static void saveLog(String log, String path) throws ServerException{
		try {
			base64ToFile(log,path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServerException(0,e.getMessage());
		}
	}
}
