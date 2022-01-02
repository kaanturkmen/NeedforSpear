package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;

/**
 * ValidateAndChangePasswordViewState is a member of State Design Pattern.
 *
 * @author Kaan Turkmen, Can Usluel.
 */
public class ValidateAndChangePasswordViewState extends ViewState {

    /**
     * Constructor of the ValidateAndChangePasswordViewState.
     *
     * @param needforSpearGame The object which has states.
     */
    public ValidateAndChangePasswordViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    /**
     * Event to be performed when switched to the activation view.
     */
    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (ActivationView)");
    }

    /**
     * Event to be performed when switched to the login view.
     */
    @Override
    public void switchToLoginView() {
        needforSpearGame.setCurrentState(new LoginViewState(needforSpearGame));

        LoginView loginView = new LoginView();
        NeedforSpearGame.getInstance().getViewData().setLoginView(loginView);
        loginView.createView();
    }

    /**
     * Event to be performed when switched to send verification view.
     */
    @Override
    public void switchToSendVerificationView() {
        needforSpearGame.setCurrentState(new SendVerificationViewState(needforSpearGame));

        SendVerificationView sendVerificationView = new SendVerificationView();
        NeedforSpearGame.getInstance().getViewData().setSendVerificationView(sendVerificationView);
        sendVerificationView.createView();
    }

    /**
     * Event to be performed when switched to validate and change password view.
     */
    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Already in ValidateAndChangePasswordView.");
    }

    /**
     * Event to be performed when switched to the main menu view.
     */
    @Override
    public void switchToMainMenuView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (MainMenuView)");
    }

    /**
     * Event to be performed when switched to the game view.
     */
    @Override
    public void switchToGameView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (GameView)");
    }

    /**
     * Event to be performed when switched to the help view.
     */
    @Override
    public void switchToHelpView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (HelpView)");
    }
}
