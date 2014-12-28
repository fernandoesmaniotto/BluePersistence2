package br.com.bluepersistence.util;

import android.annotation.SuppressLint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Utilitarios {
	@SuppressWarnings("unused")
	private static int byteSoma = 0;
    private static int byteLido = 0;
	
	public static Date formataData(String data){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.ou.println("teste jenkins")
		Date dataFormatada =null;
		try {
			dataFormatada = new java.sql.Date(formatter.parse(data.toString()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataFormatada;
	}
	
	@SuppressWarnings("resource")
	public static byte[] imagemToByte(String caminho){
		File arquivo = new File(caminho); 
        FileInputStream fis = null;
        byte[] imagem = null;
		try {
			fis = new FileInputStream(arquivo);
			imagem = new byte[(int) arquivo.length()];  
		    fis.read(imagem);  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}       
        return imagem;
	}
	
	public boolean backupBase(String backupPara, String arquivoBackup) throws IOException {        
		try {	        
	        File oldfile = new File(backupPara);
	        if (oldfile.exists()) {
	            InputStream inStream = new FileInputStream(backupPara);
	            FileOutputStream fs = new FileOutputStream(arquivoBackup);
	            byte[] buffer = new byte[1444];
	            while ((byteLido = inStream.read(buffer)) != -1) {
	                byteSoma += byteLido;
	                fs.write(buffer, 0, byteLido);
	            }
	            inStream.close();
	            fs.close();
	        }
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}


}
