package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;

/**
 * InitialState is a member of State Design Pattern.
 *
 * @author Kaan Turkmen, Can Usluel.
 */
public class InitialState extends ViewState {

    /**
     * Constructor of the InitialState.
     *
     * @param needforSpearGame The object which has states.
     */
    public InitialState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    /**
     * Event to be performed when switched to the activation view.
     */
    @Override
    public void switchToActivationView() {
        System.out.println("Initial State Error.");
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
     * Event to be performed when switched to the verification view.
     */
    @Override
    public void switchToSendVerificationView() {
        System.out.println("Initial State Error.");
    }

    /**
     * Event to be performed when switched to the validate and change password view.
     */
    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Initial State Error.");
    }

    /**
     * Event to be performed when switched to the main menu view.
     */
    @Override
    public void switchToMainMenuView() {
        System.out.println("Initial State Error.");
    }

    /**
     * Event to be performed when switched to the game view.
     */
    @Override
    public void switchToGameView() {
        System.out.println("Initial State Error.");
    }

    /**
     * Event to be performed when switched to the help view.
     */
    @Override
    public void switchToHelpView() {
        System.out.println("Initial State Error.");
    }
}
