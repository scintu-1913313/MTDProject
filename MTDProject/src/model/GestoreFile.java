package model;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestoreFile {
	
	private static GestoreFile obj; 
	
	private GestoreFile() {
		
	}
	
	//singleton
	public static GestoreFile getGestoreFile() {
		
		if (obj == null) {
			
			obj = new GestoreFile();
		}
		return obj;
	}
	
	//SCRITTURA
    public void scriviFileJSON(String percorso, boolean append, JSONObject info) throws IOException {
    	
    	FileWriter file = new FileWriter(percorso,append);
    	file.write(info.toString(4)); // 4 = indentazione leggibile
    	file.close();
   }
    
	//LETTURA
    public JSONObject leggiFileJSON(String percorso) throws IOException {
        // Legge il contenuto del file come stringa
        String contenuto = new String(Files.readAllBytes(Paths.get(percorso)));
        // Converte la stringa in oggetto JSON
        JSONObject json = new JSONObject(contenuto);

        return json;
    }
}


