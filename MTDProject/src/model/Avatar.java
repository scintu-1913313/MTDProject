package model;

import java.util.Map;

/**
 * Rappresenta un avatar dell'utente, mappando un enum ad un percorso immagine.
 */
public class Avatar {

	/** Mappa statica che associa ogni AvatarEnum al percorso dell'immagine corrispondente. */
	public static final Map<AvatarEnum, String> mappaFile = Map.of(
			AvatarEnum.DEFAULT, "/img/avatars/avatarDefault.png",
			AvatarEnum.AVATAR1, "/img/avatars/avatar1.png",
			AvatarEnum.AVATAR2, "/img/avatars/avatar2.png",
			AvatarEnum.AVATAR3, "/img/avatars/avatar3.png",
			AvatarEnum.AVATAR4, "/img/avatars/avatar4.png"
	);

	/** Valore enum dell'avatar. */
	private final AvatarEnum valore;

	/** Percorso dell'immagine associata all'avatar. */
	private final String percorsoImmagine;

	/**
	 * Costruisce un avatar dato il suo enum, impostando il percorso immagine.
	 * @param valore enum dell'avatar
	 */
	public Avatar(AvatarEnum valore){
		this.valore = valore;
		this.percorsoImmagine = mappaFile.get(valore);
	}
	
	/** 
	 * Restituisce il valore enum dell'avatar. 
	 * @return valore enum dell'avatar
	 */
	public AvatarEnum getValore() {
		return valore;
	}
	
	/**
	 * Restituisce il percorso dell'immagine associata all'avatar.
	 * @return path immagine dell'avatar
	 */
	public String getPercorsoImmagine() {
		return percorsoImmagine;
	}

	/** 
	 * Rappresentazione testuale dell'avatar (path immagine). 
	 */
	@Override
	public String toString() {
		return percorsoImmagine;
	}
	
	/**
	 * Confronta due avatar basandosi sul loro valore enum.
	 */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
        if (!(obj instanceof Avatar)) return false;

	    Avatar other = (Avatar) obj;
	    return valore == other.valore;
	}

	/**
	 * Confronto basato su hashcode coerente con equals.
	 */
	@Override
	public int hashCode() {
	    return valore != null ? valore.hashCode() : 0;
	}
}