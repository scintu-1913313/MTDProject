package model;

public enum AvatarEnum {
	DEFAULT(0),
    AVATAR1(1),
    AVATAR2(2),
    AVATAR3(3),
    AVATAR4(4);
	
    private final int avatarId;
    
	/**
	 * Costruttore 
	 * @param avatarId. L'id dell avatar
	 */
    AvatarEnum(int avatarId){
		this.avatarId = avatarId;
	}
	
    public static AvatarEnum fromId(int avatarId) {
        for (AvatarEnum a : values()) {
            if (a.avatarId == avatarId) {
            	return a;
            }
        }
        return DEFAULT;
    }
    
    public static AvatarEnum fromString(String avatarString) {
        for (AvatarEnum a : values()) {
            if (a.name().equals(avatarString)){
            	return a;
            }
        }
        return DEFAULT;
    }
    
    
	/**
	 * 
	 * @param avatar. L'avatar da convertire in stringa
	 * @return La stringa associata all'avatar tramite toString()
	 */
	public static String getNomeAvatar(AvatarEnum avatar) {
		return avatar.toString();
	}
	
	/**
	 * @return Il nome in stringa dell' avatar
	 */
	@Override
	public String toString() {
		return String.valueOf(avatarId);
	}
}
