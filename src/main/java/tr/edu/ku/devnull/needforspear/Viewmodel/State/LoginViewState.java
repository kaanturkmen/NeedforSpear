package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.ActivationView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.AuthViews.SendVerificationView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;

public class LoginViewState extends ViewState {

    public LoginViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        needforSpearGame.setCurrentState(new ActivationViewState(needforSpearGame));

        ActivationView activationView = new ActivationView();
        NeedforSpearGame.getInstance().getViewData().setActivationView(activationView);
        activationView.createView();
    }

    @Override
    public void switchToLoginView() {
        System.out.println("Already in LoginView.");
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
        System.out.println("Invalid access from the LoginView. (ValidateAndChangePasswordView)");
    }

    @Override
    public void switchToMainMenuView() {
        needforSpearGame.setCurrentState(new MainMenuViewState(needforSpearGame));

        MainMenuView mainMenuView = new MainMenuView();
        NeedforSpearGame.getInstance().getViewData().setMainMenuView(mainMenuView);

        mainMenuView.createView();
    }

    @Override
    public void switchToGameView() {
        System.out.println("Invalid access from the LoginView. (GameView)");
    }

    @Override
    public void switchToHelpView() {
        System.out.println("Invalid access from the LoginView. (HelpView)");
    }
}
