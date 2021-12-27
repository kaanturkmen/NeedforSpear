package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ValidateAndChangePasswordView;

public class SendVerificationViewState extends ViewState {
    public SendVerificationViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the SendVerificationView. (ActivationView)");
    }

    @Override
    public void switchToLoginView() {
        needforSpearGame.setCurrentState(new LoginViewState(needforSpearGame));

        LoginView loginView = new LoginView();
        NeedforSpearGame.getInstance().setLoginView(loginView);
        loginView.createView();
    }

    @Override
    public void switchToSendVerificationView() {
        System.out.println("Already in VerificationView");
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        needforSpearGame.setCurrentState(new ValidateAndChangePasswordViewState(needforSpearGame));

        ValidateAndChangePasswordView validateAndChangePasswordView = new ValidateAndChangePasswordView();
        NeedforSpearGame.getInstance().setValidateAndChangePasswordView(validateAndChangePasswordView);
        validateAndChangePasswordView.createView();
    }

    @Override
    public void switchToMainMenuView() {
        System.out.println("Invalid access from the SendVerificationView. (MainMenuView)");
    }

    @Override
    public void switchToGameView() {
        System.out.println("Invalid access from the SendVerificationView. (GameView)");
    }
}
