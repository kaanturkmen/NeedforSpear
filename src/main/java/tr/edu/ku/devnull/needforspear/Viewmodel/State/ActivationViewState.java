package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;

public class ActivationViewState extends ViewState {
    public ActivationViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Already in ActivationView.");
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
        System.out.println("Invalid access from the ActivationView. (SendVerificationView)");
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Invalid access from the ActivationView. (ValidateAndChangePasswordView)");
    }

    @Override
    public void switchToMainMenuView() {
        System.out.println("Invalid access from the ActivationView. (MainMenuView)");
    }

    @Override
    public void switchToGameView() {
        System.out.println("Invalid access from the ActivationView. (GameView)");
    }
}
