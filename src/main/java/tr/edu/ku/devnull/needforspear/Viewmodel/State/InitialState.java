package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;

public class InitialState extends ViewState {

    public InitialState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Initial State Error.");
    }

    @Override
    public void switchToLoginView() {
        needforSpearGame.setCurrentState(new LoginViewState(needforSpearGame));

        LoginView loginView = new LoginView();
        NeedforSpearGame.getInstance().getViewData().setLoginView(loginView);
        loginView.createView();
    }

    @Override
    public void switchToSendVerificationView() {
        System.out.println("Initial State Error.");
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Initial State Error.");
    }

    @Override
    public void switchToMainMenuView() {
        System.out.println("Initial State Error.");
    }

    @Override
    public void switchToGameView() {
        System.out.println("Initial State Error.");
    }
}
