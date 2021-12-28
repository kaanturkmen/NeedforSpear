package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;

public class ValidateAndChangePasswordViewState extends ViewState {
    public ValidateAndChangePasswordViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (ActivationView)");
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
        needforSpearGame.setCurrentState(new SendVerificationViewState(needforSpearGame));

        SendVerificationView sendVerificationView = new SendVerificationView();
        NeedforSpearGame.getInstance().getViewData().setSendVerificationView(sendVerificationView);
        sendVerificationView.createView();
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Already in ValidateAndChangePasswordView.");
    }

    @Override
    public void switchToMainMenuView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (MainMenuView)");
    }

    @Override
    public void switchToGameView() {
        System.out.println("Invalid access from the ValidateAndChangePassword. (GameView)");
    }
}
