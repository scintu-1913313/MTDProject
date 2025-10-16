package carte;

import java.util.List;
import java.util.ArrayList;

public class Mazzo {
	private final List<Carta> carte;
	
	private Mazzo(MazzoBuilder builder) {
		this.carte = builder.carte;
	}
	
	public Mazzo(Mazzo altro) {
	    this.carte = new ArrayList<>();
	    for (Carta c : altro.getCarte()) {
	        this.carte.add(new Carta(
	            c.getTipoMazzo(),
	            c.getSeme(),
	            c.getValore(),
	            c.getPercorsoImmagine(),
	            c.getPercorsoImmagineRetro()
	        ));
	    }
	}

	
	public void mischiaCarte() {
		java.util.Collections.shuffle(this.carte);
	}
	
	public List<Carta> getCarte() {
		return this.carte;
	}
	
	public static class MazzoBuilder {
		private final List<Carta> carte = new ArrayList<>();
		

		public MazzoBuilder generaCarte(TipoMazzo nomeTipoMazzo) {
			for(Seme seme: Seme.values()) {
				for(Valore valore: Valore.values()){
					carte.add(new Carta(nomeTipoMazzo,seme,valore,getPath(seme,valore,nomeTipoMazzo),getPathRetro(nomeTipoMazzo)));
				}
			}
			return this;
		}
		public MazzoBuilder generaAssoDeiMazzi() {
			for(TipoMazzo nomeTipoMazzo: TipoMazzo.values()) {
					carte.add(new Carta(nomeTipoMazzo, Seme.COPPE,Valore.ASSO,getPath(Seme.COPPE,Valore.ASSO,nomeTipoMazzo),getPathRetro(nomeTipoMazzo)));
			}
			
			return this;

		}	
		public String getPath(Seme seme, Valore valore, TipoMazzo nomeTipoMazzo ) {
			
			return "/img/" + nomeTipoMazzo +"/" + valore + "_" + seme + ".jpg";
		}
		
		public String getPathRetro(TipoMazzo nomeTipoMazzo ) {
			
			return "/img/" + nomeTipoMazzo +"/retro.jpg";
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
