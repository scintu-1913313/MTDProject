package model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe che gestisce la riproduzione degli effetti sonori e della musica di sottofondo.
 * Implementa un semplice singleton per centralizzare il controllo dell'audio.
 */
public class GestoreAudio {
	
    /** Istanza singleton di GestoreAudio. */
    private static GestoreAudio instance;

    /** Indica se la musica di sottofondo è abilitata. */
    private boolean musicaAbilitata;

    /** Timer per la gestione della musica di sottofondo. */
    private Timer musicTimer;

    /** Indica se la musica di sottofondo è attualmente in riproduzione. */
    private boolean musicaInRiproduzione;

    /** Percorso della musica di sottofondo nelle risorse. */
    private static final String PATH_MUSICA_GIOCO = "/sounds/musicaDelGioco.wav";

    /** Percorsi dell'effetto sonoro del bottone nelle risorse. */
    private static final String PATH_AUDIO_BOTTONE = "/sounds/button.wav";

    /** Percorsi dell'effetto sonoro della carta nelle risorse. */
    private static final String PATH_AUDIO_CARTA = "/sounds/suonoCarta.wav";

    /** La clip audio usata per riprodurre il suono del bottone. */
    private Clip clipBottone;
    
    /** La clip audio usata per riprodurre il suono della carta. */
    private Clip clipCarta;

    /**
     * Costruttore privato (pattern Singleton)
     * Inizzializza le clip audio usati per i bottoni e le carte
     */
    private GestoreAudio() {
    	musicaAbilitata = false;
    	musicaInRiproduzione= false;
    	
    	try {
            URL soundURL = GestoreAudio.class.getResource(PATH_AUDIO_BOTTONE);

            if (soundURL!=null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                clipBottone = AudioSystem.getClip();
                clipBottone.open(audioStream);
            } else {
                System.err.println("File audio non trovato: " + PATH_AUDIO_BOTTONE);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore nella riproduzione dell'audio: " + PATH_AUDIO_BOTTONE);
            e.printStackTrace();
        }
        catch (Exception e) {
        	System.err.println("Errore nella riproduzione dell'audio: " + PATH_AUDIO_BOTTONE);
            e.printStackTrace();
		}
    	
    	try {
            URL soundURL = GestoreAudio.class.getResource(PATH_AUDIO_CARTA);

            if (soundURL!=null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                clipCarta = AudioSystem.getClip();
                clipCarta.open(audioStream);
            } else {
                System.err.println("File audio non trovato: " + PATH_AUDIO_CARTA);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore nella riproduzione dell'audio: " + PATH_AUDIO_CARTA);
            e.printStackTrace();
        }
        catch (Exception e) {
        	System.err.println("Errore nella riproduzione dell'audio: " + PATH_AUDIO_CARTA);
            e.printStackTrace();
		}
    }
    
    /**
     * Restituisce l'istanza unica di GestoreAudio (pattern Singleton)
     * 
     * @return L'istanza di GestoreAudio
     */
    public static GestoreAudio getInstance() {
        if (instance == null) {
            instance = new GestoreAudio();
        }
        return instance;
    }
    
    /**
     * Riproduce l'effetto sonoro del bottone.
     */
    public void playBottone() {        
    	if(clipBottone==null) {
    		return;
    	}
    	if (clipBottone.isRunning()) {
    		clipBottone.stop(); // ferma se già in riproduzione
        }

    	clipBottone.setFramePosition(0); // riavvia dall'inizio
    	clipBottone.start();
    }
    
    /**
     * Riproduce l'effetto sonoro della carta.
     */
    public void playCarta() { 
    	if(clipCarta==null) {
    		return;
    	}
    	if (clipCarta.isRunning()) {
            clipCarta.stop(); // ferma se già in riproduzione
        }

        clipCarta.setFramePosition(0); // riavvia dall'inizio
        clipCarta.start();
    }
    
    /**
     * Avvia la riproduzione della musica di sottofondo in loop se abilitata.
     */
    public void playBackgroundMusic() {
        System.out.println("Tentativo di avviare la musica di sottofondo");
        
        if (!musicaAbilitata) {
            System.out.println("Musica disabilitata");
            return;
        }
        
        if (musicaInRiproduzione) {
            System.out.println("Musica già in riproduzione");
            return;
        }
        
        musicaInRiproduzione = true;
        
        // Riproduciamo il file musicaDelGioco.wav
        try {
        	URL soundURL = GestoreAudio.class.getResource(PATH_MUSICA_GIOCO);

            if (soundURL==null) {
                System.out.println("File musicaDelGioco.wav non trovato: " + PATH_MUSICA_GIOCO);
                return;
            }
                        
            // Creiamo un thread separato per la riproduzione del suono
            Thread soundThread = new Thread(() -> {
                try {
                    // Utilizziamo AudioInputStream e Clip per riprodurre il file WAV
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    
                    // Impostiamo il volume a un livello moderato
                    if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-10.0f); // Riduciamo il volume di 10dB
                    }
                    
                    // Riproduciamo il file in loop
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    
                    System.out.println("Riproduzione main_music.wav avviata");
                    
                    // Attendiamo che la riproduzione venga fermata
                    while (musicaInRiproduzione && clip.isActive()) {
                        try {
                            Thread.sleep(500); // Controlliamo ogni mezzo secondo
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    
                    // Fermiamo la riproduzione quando necessario
                    clip.stop();
                    clip.close();
                    audioStream.close();
                    
                } catch (Exception e) {
                    System.out.println("Errore nella riproduzione di main_music.wav: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            soundThread.start();
            
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione dell'audio: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Avvio riproduzione musica di sottofondo completato");
    }
    
    /**
     * Interrompe la riproduzione della musica di sottofondo.
     */
    public void stopBackgroundMusic() {
        if (musicTimer != null) {
            musicTimer.cancel();
            musicTimer = null;
        }
        musicaInRiproduzione = false;
        System.out.println("Musica di sottofondo fermata");
    }
    
    /**
     * Abilita o disabilita la musica e avvia/ferma la riproduzione di conseguenza.
     * @param enabled true per abilitare, false per disabilitare
     */
    private void setMusicEnabled(boolean enabled) {
        this.musicaAbilitata = enabled;
        if (!enabled) {
            stopBackgroundMusic();
        } else if (enabled) {
            playBackgroundMusic();
        }
    }
    
    /**
     * Indica se la musica di sottofondo è abilitata.
     * @return true se abilitata, false altrimenti
     */
    public boolean isMusicaAbilitata() {
        return musicaAbilitata;
    }
    
    /**
     * Inverte lo stato della musica (on/off) e ritorna il nuovo stato.
     * @return nuovo stato della musica dopo il cambio
     */
    public boolean cambiaStatoMusica() {
        if(musicaAbilitata) {
        	setMusicEnabled(false);
        	return musicaAbilitata;
        }
        else
        {
        	setMusicEnabled(true);
        	return musicaAbilitata;
        }
    }
}
