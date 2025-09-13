package carte;

public enum TipoMazzo {

	NAPOLETANTE("napoletane"),
	PIACENTINE("piacentine");
	
	private final String nomeTipoMazzo;
	
	TipoMazzo(String nomeTipoMazzo){
		this.nomeTipoMazzo = nomeTipoMazzo;
	}
	
	public static String getTipoMazzo(TipoMazzo nomeTipoMazzo) {
		return nomeTipoMazzo.toString();
	}
	
	@Override
	public String toString() {
		return nomeTipoMazzo;
	}
}
