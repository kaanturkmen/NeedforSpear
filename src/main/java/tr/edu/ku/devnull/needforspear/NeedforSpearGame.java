package tr.edu.ku.devnull.needforspear;


import tr.edu.ku.devnull.needforspear.Model.Database.FirebaseDatabase;
import tr.edu.ku.devnull.needforspear.Model.Database.GameDatabase;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Model.Ymir.Ymir;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ActivationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ValidateAndChangePasswordView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.InitialState;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.ViewState;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * NeedforSpearGame is a class that have a main method
 * to run Need for Spear game.
 *
 * @author Kaan Turkmen
 */
public class NeedforSpearGame {
    private JFrame mainFrame = new JFrame();
    private GameMode gameMode = GameMode.BUILDING_MODE;
    private boolean isPaused = true;
    private Player player;
    private GameMap gameMap;
    private GameDatabase gameDatabase;
    private Thread backgroundMusicThread, soundEffectThread;
    private boolean muteModeActivated = false;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Clip clip;
    private static NeedforSpearGame onlyInstance;
    private GameView gameView;
    private MainMenuView mainMenuView;
    private ValidateAndChangePasswordView validateAndChangePasswordView;
    private SendVerificationView sendVerificationView;
    private LoginView loginView;
    private ActivationView activationView;
    private ViewState currentState;
    private boolean isGameLoaded = false;

    private long startMillis;

    /**
     * Main method of the game.
     *
     * @param args Args given when program is started.
     */
    public static void main(String[] args) {
        getInstance().setCurrentState(new InitialState(getInstance()));
        getInstance().playBackgroundMusic();
        getInstance().init();
        getInstance().startLoginView();
    }

    private NeedforSpearGame() {}

    public static NeedforSpearGame getInstance() {
        if (onlyInstance == null) onlyInstance = new NeedforSpearGame();

        return onlyInstance;
    }

    /**
     * Init method to do assignments and checks.
     */
    private void init() {
        gameDatabase = new FirebaseDatabase();
        mainFrame.setResizable(false);
        mainFrame.setSize(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (!checkInternetConnection()) System.exit(0);
    }

    /**
     * A method to find resource folder of the player.
     *
     * @param combinedPathString Path string of the required image to be combined with the resources folder.
     * @return Absolute path of the image.
     */
    public String findResourceFolder(String combinedPathString) {
        String gameLocation = System.getProperty(Constants.UIConstants.USER_DIRECTORY_KEYWORD);
        return gameLocation + Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + combinedPathString;
    }

    /**
     * A method to start the LoginView.
     */
    public void startLoginView() {
        currentState.switchToLoginView();
    }

    /**
     * A method to start the VerificationView.
     */
    public void startVerificationView() {
        currentState.switchToSendVerificationView();
    }

    /**
     * A method to start the ResetPasswordView.
     */
    public void startResetPasswordView() {
        currentState.switchToValidateAndChangePasswordView();
    }

    /**
     * A method to start the ActivationView.
     */
    public void startActivationView() {
        currentState.switchToActivationView();
    }

    /**
     * A method to start the MainMenuView.
     */
    public void startMainMenu() {
        currentState.switchToMainMenuView();
    }

    /**
     * A method to start the GameView.
     */
    public void startGameView() {
        currentState.switchToGameView();
    }

    /**
     * Checks if the player has an active internet connection.
     *
     * @return Boolean indicating if user has an internet connection.
     */
    private boolean checkInternetConnection() {
        try {
            URL url = new URL(Constants.UIConstants.WEBSITE_TO_BE_PINGED);
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            System.err.println("You do not have an internet connection! This game requires an internet connection to play!");
            return false;
        }
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
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(findResourceFolder("backgroundMusic.wav")));
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
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(findResourceFolder(path)));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundEffectThread.start();
    }

    /**
     * To get the current gamemode of the game to switch between BuildModes.
     *
     * @return Current gamemode of the game.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Starts measuring time the moment game switches to running mode
     *
     * /**
     * Switches building mode to the running mode.
     */
    public void switchToRunningMode() {
        startMillis = System.currentTimeMillis();
        gameMode = GameMode.RUNNING_MODE;
    }

    /**
     * Starts executing Ymir powers by executing threads in given time.
     */
    public void startExecutorService() {
        Ymir y = new Ymir();
        //TODO to try infinite void we should wait until game starts so 0 delay makes it not work
       executorService.scheduleAtFixedRate(y, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * Stops Ymir powers by executing threads in given time.
     */
    public void stopExecutorService() {
        executorService.shutdownNow();
    }

    /**
     * To get a logged in player to check their maps, score and other properties.
     *
     * @return Logged in player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * To set a logged in player to change their maps, score and other properties.
     * This method is used by converting null into new object. It is not being used to
     * set player multiple times.
     *
     */
    public void setPlayer(Player player) {
       this.player = player;
    }

    /**
     * To get a main frame to update views on the main frame.
     *
     * @return Main frame of the game.
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * To get a game database to access it by using dependency injection.
     *
     * @return Gamedatabase to access its database call methods.
     */
    public GameDatabase getGameDatabase() {
        return gameDatabase;
    }

    /**
     * To get a game map to construct on the views.
     *
     * @return Gamemap to be played on.
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * To set a game map to be played on.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Check if the game is paused.
     *
     * @return Boolean indicating if the game is paused.
     */
    public boolean getIsPaused() {
        return isPaused;
    }

    /**
     * To set if the game is paused.
     *
     * @param isPaused Boolean indicating if the game is paused.
     */
    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * To set a game mode to be played on.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * To get the start time of the game.
     *
     * @return Long value of the time the running mode starts in milliseconds.
     */
    public long getStartMillis() {
        return startMillis;
    }

    /**
     * To get the current time of the game.
     *
     * @return Long value of the current time in milliseconds.
     */
    public long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public ViewState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ViewState currentState) {
        this.currentState = currentState;
    }

    public boolean isGameLoaded() {
        return isGameLoaded;
    }

    public void setGameLoaded(boolean gameLoaded) {
        isGameLoaded = gameLoaded;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setValidateAndChangePasswordView(ValidateAndChangePasswordView validateAndChangePasswordView) {
        this.validateAndChangePasswordView = validateAndChangePasswordView;
    }

    public void setSendVerificationView(SendVerificationView sendVerificationView) {
        this.sendVerificationView = sendVerificationView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setActivationView(ActivationView activationView) {
        this.activationView = activationView;
    }

     /**
     * Check if game is in mute mode where no sound effect or background music is being played.
     *
     * @return Boolean value of the mute mode.
     */
    public boolean isMuteModeActivated() {
        return muteModeActivated;
    }

    /**
     * Sets the game's mute mode.
     */
    public void setMuteModeActivated(boolean muteModeActivated) {
        this.muteModeActivated = muteModeActivated;
    }
}
