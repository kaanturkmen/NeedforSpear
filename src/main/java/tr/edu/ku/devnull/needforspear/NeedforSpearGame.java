package tr.edu.ku.devnull.needforspear;


import tr.edu.ku.devnull.needforspear.Viewmodel.Database.FirebaseDatabase;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameInfo;
import tr.edu.ku.devnull.needforspear.Model.UIModels.ViewData;
import tr.edu.ku.devnull.needforspear.Model.Ymir.Ymir;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.InternetHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.SoundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.InitialState;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.ViewState;

import javax.swing.*;
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
    private final ScheduledExecutorService executorOfYmir = Executors.newScheduledThreadPool(1);
    private static NeedforSpearGame onlyInstance;
    private ViewData viewData;
    private ViewState currentState;
    private GameInfo gameInfo;

    /**
     * Main method of the game.
     *
     * @param args Args given when program is started.
     */
    public static void main(String[] args) {
        getInstance().setCurrentState(new InitialState(getInstance()));
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
        viewData = new ViewData();
        gameInfo = new GameInfo();
        gameInfo.setGameDatabase(new FirebaseDatabase());
        initializeMainFrame();
        if (!new InternetHandler().checkInternetConnection()) System.exit(0);
        SoundHandler.getInstance().playBackgroundMusic();
    }

    private void initializeMainFrame() {
        gameInfo.getMainFrame().setResizable(false);
        gameInfo.getMainFrame().setSize(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT);
        gameInfo.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
     * Starts measuring time the moment game switches to running mode
     *
     * /**
     * Switches building mode to the running mode.
     */
    public void switchToRunningMode() {
        gameInfo.setStartMillis(System.currentTimeMillis());
        gameInfo.setGameMode(GameMode.RUNNING_MODE);
    }

    /**
     * Starts executing Ymir powers by executing threads in given time.
     */
    public void startYmirAction() {
        Ymir y = new Ymir();
        executorOfYmir.scheduleAtFixedRate(y, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * Stops Ymir powers by executing threads in given time.
     */
    public void stopYmirAction() {
        executorOfYmir.shutdownNow();
    }

    public ViewState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ViewState currentState) {
        this.currentState = currentState;
    }

    public ViewData getViewData() {
        return viewData;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public ScheduledExecutorService getExecutorOfYmir() {
        return executorOfYmir;
    }
}
