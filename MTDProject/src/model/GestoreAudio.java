package model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe che gestisce la riproduzione di effetti sonori nel gioco
 */
public class GestoreAudio {
	
    private static GestoreAudio instance;
    private boolean musicaAbilitata;
    private Timer musicTimer;
    private boolean musicaInRiproduzione;
    private static final String PATH_MUSICA_GIOCO = "/sounds/musicaDelGioco.wav";
    private static final String PATH_AUDIO_BOTTONE = "/sounds/button.wav";

    /**
     * Costruttore privato (pattern Singleton)
     */
    private GestoreAudio() {
    	musicaAbilitata = false;
    	musicaInRiproduzione= false;
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
     * Riproduce un file audio
     * 
     * @param filename Il percorso del file audio da riprodurre
     */
    
    private void play(String filename) {        
        try {
            URL soundURL = GestoreAudio.class.getResource(filename);

            if (soundURL!=null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } else {
                System.err.println("File audio non trovato: " + filename);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore nella riproduzione dell'audio: " + filename);
            e.printStackTrace();
        }
    }
    
    /**
     * Riproduce un effetto sonoro
     * 
     * @param name Il nome dell'effetto sonoro da riprodurre
     */
    public void playBottone() {        
        play(PATH_AUDIO_BOTTONE);
    }
        
    /**
     * Avvia la riproduzione della musica di sottofondo
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
     * Interrompe la riproduzione della musica di sottofondo
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
     * Attiva o disattiva la musica di sottofondo
     * 
     * @param enabled true per attivare, false per disattivare
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
     * Verifica se la musica di sottofondo è attiva
     * 
     * @return true se la musica è attiva, false altrimenti
     */
    public boolean isMusicaAbilitata() {
        return musicaAbilitata;
    }
    
    /**
     * Verifica se la musica di sottofondo è attiva
     * 
     * @return true se la musica è attiva, false altrimenti
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
