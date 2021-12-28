package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundHandler {

    private Thread backgroundMusicThread, soundEffectThread;
    private Clip clip;
    private static SoundHandler onlyInstance;

    public SoundHandler(){}

    public static SoundHandler getInstance() {
        if(onlyInstance == null) onlyInstance = new SoundHandler();

        return onlyInstance;
    }

    /**
     * Plays a music (.wav extension is required) on background.
     */
    public void playBackgroundMusic() {
        if (backgroundMusicThread != null) {
            backgroundMusicThread.interrupt();
        }

        backgroundMusicThread = new Thread(() -> {
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/java/tr/edu/ku/devnull/needforspear/Resources/backgroundMusic.wav"));
                clip.open(inputStream);
                clip.loop(0);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        backgroundMusicThread.start();
    }

    /**
     * Stops the background music.
     */
    public void stopBackgroundMusic() {
        clip.stop();
        backgroundMusicThread.stop();
        backgroundMusicThread = null;
    }

    /**
     * Plays a given sound (.wav extension is required) once.
     *
     * @param path Path of the name of the file which is located in Resources directory.
     */
    public void playSound(String path) {
        if (soundEffectThread != null) {
            soundEffectThread.stop();
        }

        soundEffectThread = new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/java/tr/edu/ku/devnull/needforspear/Resources/" + path));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundEffectThread.start();
    }
}
