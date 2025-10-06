package model;

import java.util.Map;

import model.AvatarEnum;

public class Avatar {
	public static final Map<AvatarEnum, String> mappaFile = Map.of(
			AvatarEnum.DEFAULT, "/img/avatars/avatarDefault.png",
			AvatarEnum.AVATAR1, "/img/avatars/avatar1.png",
			AvatarEnum.AVATAR2, "/img/avatars/avatar2.png",
			AvatarEnum.AVATAR3, "/img/avatars/avatar3.png",
			AvatarEnum.AVATAR4, "/img/avatars/avatar3.png"
	);
	private final AvatarEnum valore;
	private final String percorsoImmagine;

	public Avatar(AvatarEnum valore){
		this.valore = valore;
		this.percorsoImmagine = mappaFile.get(valore);
	}
	
	public AvatarEnum getValore() {
		return valore;
	}
	
	public String getPercorsoImmagine() {
		return percorsoImmagine;
	}

	@Override
	public String toString() {
		return percorsoImmagine;
	}
}