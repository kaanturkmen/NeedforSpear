package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * SoundHandler is a Controller Design Pattern for the handling Sound and Music.
 *
 * @author Kaan Turkmen
 */
public class SoundHandler {
    private static SoundHandler onlyInstance;
    private Thread backgroundMusicThread;
    private Clip backgroundMusicClip;

    /**
     * Constructor of SoundHandler.
     */
    public SoundHandler() {
    }

    /**
     * Singleton Design Pattern's getInstance method.
     *
     * @return Single instance of the SoundHandler.
     */
    public static SoundHandler getInstance() {
        if (onlyInstance == null) onlyInstance = new SoundHandler();

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
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.UIConstants.SOUNDS_FOLDER_PATH + Constants.UIConstants.BACKGROUND_MUSIC));
                backgroundMusicClip.open(inputStream);
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
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
        backgroundMusicThread = null;
    }

    /**
     * Plays a given sound (.wav extension is required) once.
     *
     * @param path Path of the name of the file which is located in Resources directory.
     */
    public void playSound(String path) {
        if (NeedforSpearGame.getInstance().getGameInfo().isMuteModeActivated()) return;

        Thread soundEffectThread = new Thread(() -> {
            try {
                Clip soundEffectClip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.UIConstants.SOUNDS_FOLDER_PATH + path));
                soundEffectClip.open(inputStream);
                soundEffectClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundEffectThread.start();
    }
}
