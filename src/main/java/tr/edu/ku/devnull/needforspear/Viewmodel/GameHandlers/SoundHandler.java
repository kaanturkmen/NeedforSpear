package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundHandler {

    private Thread backgroundMusicThread, soundEffectThread;
    private Clip backgroundMusicClip;
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
                backgroundMusicClip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/java/tr/edu/ku/devnull/needforspear/Resources/backgroundMusic.wav"));
                backgroundMusicClip.open(inputStream);
                backgroundMusicClip.loop(0);
                backgroundMusicClip.start();
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
        backgroundMusicClip.stop();
        backgroundMusicThread.stop();
        backgroundMusicThread = null;
    }

    /**
     * Plays a given sound (.wav extension is required) once.
     *
     * @param path Path of the name of the file which is located in Resources directory.
     */
    public void playSound(String path) {
        if (NeedforSpearGame.getInstance().getGameInfo().isMuteModeActivated()) return;

        soundEffectThread = new Thread(() -> {
            try {
                Clip soundEffectClip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/java/tr/edu/ku/devnull/needforspear/Resources/" + path));
                soundEffectClip.open(inputStream);
                soundEffectClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundEffectThread.start();
    }
}
