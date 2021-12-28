package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.View.AuthViews.ActivationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ValidateAndChangePasswordView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;

public class ViewData {
    private GameView gameView;
    private MainMenuView mainMenuView;
    private ValidateAndChangePasswordView validateAndChangePasswordView;
    private SendVerificationView sendVerificationView;
    private LoginView loginView;
    private ActivationView activationView;

    public ViewData(GameView gameView, MainMenuView mainMenuView, ValidateAndChangePasswordView validateAndChangePasswordView, SendVerificationView sendVerificationView, LoginView loginView, ActivationView activationView) {
        this.gameView = gameView;
        this.mainMenuView = mainMenuView;
        this.validateAndChangePasswordView = validateAndChangePasswordView;
        this.sendVerificationView = sendVerificationView;
        this.loginView = loginView;
        this.activationView = activationView;
    }

    public ViewData() {}

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public ValidateAndChangePasswordView getValidateAndChangePasswordView() {
        return validateAndChangePasswordView;
    }

    public void setValidateAndChangePasswordView(ValidateAndChangePasswordView validateAndChangePasswordView) {
        this.validateAndChangePasswordView = validateAndChangePasswordView;
    }

    public SendVerificationView getSendVerificationView() {
        return sendVerificationView;
    }

    public void setSendVerificationView(SendVerificationView sendVerificationView) {
        this.sendVerificationView = sendVerificationView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public ActivationView getActivationView() {
        return activationView;
    }

    public void setActivationView(ActivationView activationView) {
        this.activationView = activationView;
    }
}
