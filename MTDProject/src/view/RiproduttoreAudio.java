package view;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class RiproduttoreAudio {
    public static void play(String fileName) {
        try {
            URL soundURL = RiproduttoreAudio.class.getResource(fileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
