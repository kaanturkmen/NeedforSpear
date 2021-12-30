package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;

public class HelpViewState extends ViewState {
    public HelpViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the HelpView. (ActivationView)");
    }

    @Override
    public void switchToLoginView() {
        System.out.println("Invalid access from the HelpView. (LoginView)");
    }

    @Override
    public void switchToSendVerificationView() {
        System.out.println("Invalid access from the HelpView. (SendVerificationView)");
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Invalid access from the HelpView. (ValidateAndChangePasswordView)");
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
        System.out.println("Invalid access from the HelpView. (GameView)");
    }

    @Override
    public void switchToHelpView() {
        System.out.println("Already in HelpView.");
    }
}
