package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ActivationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;

/**
 * LoginViewState is a member of State Design Pattern.
 *
 * @author Kaan Turkmen, Can Usluel.
 */
public class LoginViewState extends ViewState {

    /**
     * Constructor of the LoginViewState.
     *
     * @param needforSpearGame The object which has states.
     */
    public LoginViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    /**
     * Event to be performed when switched to the activation view.
     */
    @Override
    public void switchToActivationView() {
        needforSpearGame.setCurrentState(new ActivationViewState(needforSpearGame));

        ActivationView activationView = new ActivationView();
        NeedforSpearGame.getInstance().getViewData().setActivationView(activationView);
        activationView.createView();
    }

    /**
     * Event to be performed when switched to the login view.
     */
    @Override
    public void switchToLoginView() {
        System.out.println("Already in LoginView.");
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
        System.out.println("Invalid access from the LoginView. (ValidateAndChangePasswordView)");
    }

    /**
     * Event to be performed when switched to the main menu view.
     */
    @Override
    public void switchToMainMenuView() {
        needforSpearGame.setCurrentState(new MainMenuViewState(needforSpearGame));

        MainMenuView mainMenuView = new MainMenuView();
        NeedforSpearGame.getInstance().getViewData().setMainMenuView(mainMenuView);

        mainMenuView.createView();
    }

    /**
     * Event to be performed when switched to the game view.
     */
    @Override
    public void switchToGameView() {
        System.out.println("Invalid access from the LoginView. (GameView)");
    }

    /**
     * Event to be performed when switched to the help view.
     */
    @Override
    public void switchToHelpView() {
        System.out.println("Invalid access from the LoginView. (HelpView)");
    }
}
