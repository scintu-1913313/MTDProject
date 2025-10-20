package model;

/**
 * Enum che rappresenta le possibili avatar disponibili per l'utente.
 * Possibili avatar: DEFAULT (0), AVATAR1 (1), AVATAR2 (2), AVATAR3 (3), AVATAR4 (4).
 */
public enum AvatarEnum {

	/** Avatar predefinito di default. */
	DEFAULT(0),

	/** Primo avatar selezionabile.*/
    AVATAR1(1),

	/** Secondo avatar selezionabile.*/
    AVATAR2(2),

	/** Terzo avatar selezionabile.*/
    AVATAR3(3),

	/** Quarto avatar selezionabile.*/
    AVATAR4(4);
	
	/** Id numerico associato all'avatar. */
    private final int avatarId;
    
	/**
	 * Costruisce un' avatar con il relativo id numerico.
	 * @param avatarId. L'id dell avatar
	 */
    AvatarEnum(int avatarId){
		this.avatarId = avatarId;
	}
	
	/**
	 * Converte un id numerico in AvatarEnum (default DEFAULT se non valido).
	 * @param avatarId L'id numerico dell' avatar
	 * @return L' avatar corrispondente all'id (default DEFAULT se non valido)
	 */
    public static AvatarEnum fromId(int avatarId) {
        for (AvatarEnum a : values()) {
            if (a.avatarId == avatarId) {
            	return a;
            }
        }
        return DEFAULT;
    }
    
	/**
	 * Converte una stringa in AvatarEnum (default DEFAULT se non valido).
	 * @param avatarString La stringa dell' avatar
	 * @return L' avatar corrispondente alla stringa (default DEFAULT se non valido)
	 */
    public static AvatarEnum fromString(String avatarString) {
        for (AvatarEnum a : values()) {
            if (a.name().equals(avatarString)){
            	return a;
            }
        }
        return DEFAULT;
    }
    
    
	/**
	 * Restituisce la stringa rappresentativa dell' avatar.
	 * @param avatar. L'avatar da convertire in stringa
	 * @return La stringa associata all'avatar tramite toString()
	 */
	public static String getNomeAvatar(AvatarEnum avatar) {
		return avatar.toString();
	}
	
	/**
	 * Restituisce la rappresentazione testuale dell' avatar.
	 * @return L'id dell' avatar in stringa.
	 */
	@Override
	public String toString() {
		return String.valueOf(avatarId);
	}
}
