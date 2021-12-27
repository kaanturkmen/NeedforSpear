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
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MainMenuHandler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
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
    private static JFrame mainFrame = new JFrame();
    private static GameMode gameMode = GameMode.BUILDING_MODE;
    private static boolean isPaused = true;
    private static Player player;
    private static GameMap gameMap;
    private static GameDatabase gameDatabase;
    private static Thread backgroundMusicThread, soundEffectThread;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private static long startMillis;

    /**
     * Main method of the game.
     *
     * @param args Args given when program is started.
     */
    public static void main(String[] args) {
        init();
        startLoginView();
//        startExecutorService();
    }

    /**
     * Init method to do assignments and checks.
     */
    private static void init() {
        gameDatabase = new FirebaseDatabase();
        mainFrame.setResizable(false);
        mainFrame.setSize(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (!checkInternetConnection()) System.exit(0);
    }

    public static void setMainFrame(JFrame jFrame) {
        mainFrame = jFrame;
    }

    /**
     * A method to find resource folder of the player.
     *
     * @param combinedPathString Path string of the required image to be combined with the resources folder.
     * @return Absolute path of the image.
     */
    public static String findResourceFolder(String combinedPathString) {
        String gameLocation = System.getProperty(Constants.UIConstants.USER_DIRECTORY_KEYWORD);
        return gameLocation + Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + combinedPathString;
    }

    /**
     * A method to start the LoginView.
     */
    public static void startLoginView() {
        LoginView.createView();
    }

    /**
     * A method to start the VerificationView.
     */
    public static void startVerificationView() {
        SendVerificationView.createView();
    }

    /**
     * A method to start the ResetPasswordView.
     */
    public static void startResetPasswordView() {
        ValidateAndChangePasswordView.createView();
    }

    /**
     * A method to start the ActivationView.
     */
    public static void startActivationView() {
        ActivationView.createView();
    }

    /**
     * A method to start the MainMenuView.
     */
    public static void startMainMenu() {
        MainMenuHandler.getInstance().setGameDatabase(gameDatabase);
        MainMenuView.setGameViewOpened(true);
        GameView.setIsGameViewOpened(false);
        MainMenuView.createView();
    }

    /**
     * A method to start the GameView.
     */
    public static void startGameView() {
        MainMenuView.setGameViewOpened(false);
        GameView.setIsGameViewOpened(true);
        GameView.createView();
    }

    /**
     * Gets the color equivalent of the given string.
     *
     * @param color String name of the color.
     * @return Color class equivalent of the given string.
     */
    public static Color getColorEquivalent(String color) {

        Color colorSelection = Color.BLACK;

        switch (color) {
            case Constants.UIConstants.RED_COLOR_STRING:
                colorSelection = Color.RED;
                break;
            case Constants.UIConstants.BLUE_COLOR_STRING:
                colorSelection = Color.BLUE;
                break;
            case Constants.UIConstants.ORANGE_COLOR_STRING:
                colorSelection = Color.ORANGE;
                break;
            case Constants.UIConstants.GREEN_COLOR_STRING:
                colorSelection = Color.GREEN;
                break;
            default:
                break;
        }

        return colorSelection;
    }

    /**
     * Checks if the player has an active internet connection.
     *
     * @return Boolean indicating if user has an internet connection.
     */
    private static boolean checkInternetConnection() {
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
     *
     * @param path Path of the name of the file which is located in Resources directory.
     */
    public static void playBackgroundMusic(String path) {
        if (backgroundMusicThread != null) {
            backgroundMusicThread.stop();
        }

        backgroundMusicThread = new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(findResourceFolder(path)));
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
     * Plays a given sound (.wav extension is required) once.
     *
     * @param path Path of the name of the file which is located in Resources directory.
     */
    public static void playSound(String path) {
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
    public static GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Starts measuring time the moment game switches to running mode
     * <p>
     * /**
     * Switches building mode to the running mode.
     */
    public static void switchToRunningMode() {
        startMillis = System.currentTimeMillis();
        gameMode = GameMode.RUNNING_MODE;
    }

    /**
     * Starts executing Ymir powers by executing threads in given time.
     */
    public static void startExecutorService() {
        Ymir y = new Ymir();
        //TODO to try infinite void we should wait until game starts so 0 delay makes it not work
        NeedforSpearGame.executorService.scheduleAtFixedRate(y, 0, 30, TimeUnit.SECONDS);

    }

    /**
     * Stops Ymir powers by executing threads in given time.
     */
    public static void stopExecutorService() {
        NeedforSpearGame.executorService.shutdownNow();
    }

    /**
     * To get a logged in player to check their maps, score and other properties.
     *
     * @return Logged in player.
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * To set a logged in player to change their maps, score and other properties.
     * This method is used by converting null into new object. It is not being used to
     * set player multiple times.
     *
     * @return Logged in player.
     */
    public static void setPlayer(Player player) {
        NeedforSpearGame.player = player;
    }

    /**
     * To get a main frame to update views on the main frame.
     *
     * @return Main frame of the game.
     */
    public static JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * To get a game database to access it by using dependency injection.
     *
     * @return Gamedatabase to access its database call methods.
     */
    public static GameDatabase getGameDatabase() {
        return gameDatabase;
    }

    /**
     * To get a game map to construct on the views.
     *
     * @return Gamemap to be played on.
     */
    public static GameMap getGameMap() {
        return gameMap;
    }

    /**
     * To set a game map to be played on.
     */
    public static void setGameMap(GameMap gameMap) {
        NeedforSpearGame.gameMap = gameMap;
    }

    /**
     * Check if the game is paused.
     *
     * @return Boolean indicating if the game is paused.
     */
    public static boolean getIsPaused() {
        return isPaused;
    }

    /**
     * To set if the game is paused.
     *
     * @param isPaused Boolean indicating if the game is paused.
     */
    public static void setIsPaused(boolean isPaused) {
        NeedforSpearGame.isPaused = isPaused;
    }

    /**
     * To set a game mode to be played on.
     */
    public static void setGameMode(GameMode gameMode) {
        NeedforSpearGame.gameMode = gameMode;
    }

    /**
     * To get the start time of the game.
     *
     * @return Long value of the time the running mode starts in milliseconds.
     */
    public static long getStartMillis() {
        return startMillis;
    }

    /**
     * To get the current time of the game.
     *
     * @return Long value of the current time in milliseconds.
     */
    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }
}
