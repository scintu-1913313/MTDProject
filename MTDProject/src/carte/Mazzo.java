package carte;

import java.util.List;
import java.util.ArrayList;

public class Mazzo {
	private final List<Carta> carte;
	
	private Mazzo(MazzoBuilder builder) {
		this.carte = builder.carte;
	}
	
	public List<Carta> getCarte() {
		return this.carte;
	}
	
	public static class MazzoBuilder {
		private final List<Carta> carte = new ArrayList<>();
		

		public MazzoBuilder generaCarte(TipoMazzo nomeTipoMazzo) {
			for(Seme seme: Seme.values()) {
				for(Valore valore: Valore.values()){
					carte.add(new Carta(nomeTipoMazzo,seme,valore,getPath(seme,valore,nomeTipoMazzo)));
				}
			}
			return this;
		}
		public MazzoBuilder generaAssoDeiMazzi() {
			for(TipoMazzo nomeTipoMazzo: TipoMazzo.values()) {
					carte.add(new Carta(nomeTipoMazzo, Seme.COPPE,Valore.ASSO,getPath(Seme.COPPE,Valore.ASSO,nomeTipoMazzo)));
			}
			
			return this;

		}	
		public String getPath(Seme seme, Valore valore, TipoMazzo nomeTipoMazzo ) {
			
			return "/img/" + nomeTipoMazzo +"/" + valore + "_" + seme + ".jpg";
		}
		
		public MazzoBuilder mescola() {
			java.util.Collections.shuffle(carte);
			return this;
		}
		
		public Mazzo build() {
			return new Mazzo(this);
		}
	}
}
