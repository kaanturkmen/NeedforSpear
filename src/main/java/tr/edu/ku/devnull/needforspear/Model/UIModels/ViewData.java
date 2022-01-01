package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.View.AuthViews.ActivationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ValidateAndChangePasswordView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.HelpView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;

/**
 * ViewData is class to store the views of the current game.
 */
public class ViewData {
    private GameView gameView;
    private MainMenuView mainMenuView;
    private ValidateAndChangePasswordView validateAndChangePasswordView;
    private SendVerificationView sendVerificationView;
    private LoginView loginView;
    private ActivationView activationView;
    private HelpView helpView;

    /**
     * Constructor for Viewdata.
     */
    public ViewData() {
    }

    /**
     * Gets the game view.
     *
     * @return GameView to be played.
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * Sets the game view.
     *
     * @param gameView GameView to be set.
     */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Gets the main menu view.
     *
     * @return MainMenu to be played.
     */
    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }

    /**
     * Sets the main menu view.
     *
     * @param mainMenuView MainMenuView to be set.
     */
    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    /**
     * Gets the validate and change password view.
     *
     * @return ValidateAndChangePasswordView to be played.
     */
    public ValidateAndChangePasswordView getValidateAndChangePasswordView() {
        return validateAndChangePasswordView;
    }

    /**
     * Sets the validate and change password view.
     *
     * @param validateAndChangePasswordView ValidateAndChangePasswordView to be set.
     */
    public void setValidateAndChangePasswordView(ValidateAndChangePasswordView validateAndChangePasswordView) {
        this.validateAndChangePasswordView = validateAndChangePasswordView;
    }

    /**
     * Gets the send verification view.
     *
     * @return SendVerificationView to be played.
     */
    public SendVerificationView getSendVerificationView() {
        return sendVerificationView;
    }

    /**
     * Sets the verification view.
     *
     * @param sendVerificationView VerificaitonView to be set.
     */
    public void setSendVerificationView(SendVerificationView sendVerificationView) {
        this.sendVerificationView = sendVerificationView;
    }

    /**
     * Gets the login view.
     *
     * @return LoginView to be played.
     */
    public LoginView getLoginView() {
        return loginView;
    }

    /**
     * Sets the login view.
     *
     * @param loginView LoginView to be set.
     */
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * Gets the activation view.
     *
     * @return ActivationView to be played.
     */
    public ActivationView getActivationView() {
        return activationView;
    }

    /**
     * Sets the activation view.
     *
     * @param activationView ActivationView to be set.
     */
    public void setActivationView(ActivationView activationView) {
        this.activationView = activationView;
    }

    /**
     * Gets the help view.
     *
     * @return HelpView to be played.
     */
    public HelpView getHelpView() {
        return helpView;
    }

    /**
     * Sets the help view.
     *
     * @param helpView HelpView to be set.
     */
    public void setHelpView(HelpView helpView) {
        this.helpView = helpView;
    }
}
