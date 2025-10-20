package model;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe utility che fornisce operazioni di lettura e scrittura di file JSON.
 * Implementa il pattern Singleton per centralizzare l'accesso al filesystem.
 */
public class GestoreFile {
	/** Istanza singleton al gestore file. */
	private static GestoreFile obj; 
	
	/**
	 * Costruttore privato (Singleton Pattern).
	 */
	private GestoreFile() {
		
	}
	
	/**
	 * Restituisce l'istanza singleton di GestoreFile.
	 * @return GestoreFile unico
	 */
	public static GestoreFile getGestoreFile() {
		
		if (obj == null) {
			obj = new GestoreFile();
		}
		return obj;
	}
	
	/**
	 * Scrive un JSONObject su file con indentazione leggibile.
	 * @param percorso path del file
	 * @param append se true aggiunge al file, altrimenti sovrascrive
	 * @param info JSONObject da scrivere
	 * @throws IOException se si verifica un errore di I/O
	 */
	public void scriviFileJSON(String percorso, boolean append, JSONObject info) throws IOException {
        
		FileWriter file = new FileWriter(percorso,append);
		file.write(info.toString(4)); // 4 = indentazione leggibile
		file.close();
   }
    
	/**
	 * Legge e parsa un file JSON in un JSONObject.
	 * @param percorso path del file JSON
	 * @return JSONObject letto
	 * @throws IOException se si verifica un errore di I/O
	 */
	public JSONObject leggiFileJSON(String percorso) throws IOException {
		// Legge il contenuto del file come stringa
		String contenuto = new String(Files.readAllBytes(Paths.get(percorso)));
		// Converte la stringa in oggetto JSON
		JSONObject json = new JSONObject(contenuto);

		return json;
	}
}


