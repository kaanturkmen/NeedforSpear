package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.AuthViews.LoginView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.HelpView;

public class MainMenuViewState extends ViewState {
    public MainMenuViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the MainMenuView. (ActivationView)");
    }

    @Override
    public void switchToLoginView() {
        System.out.println("Invalid access from the GameView. (LoginView)");
    }

    @Override
    public void switchToSendVerificationView() {
        System.out.println("Invalid access from the MainMenuView. (SendVerificationView)");
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Invalid access from the MainMenuView. (ValidateAndChangePasswordView)");
    }

    @Override
    public void switchToMainMenuView() {
        System.out.println("Already in MainMenuView.");
    }

    @Override
    public void switchToGameView() {
        needforSpearGame.setCurrentState(new GameViewState(needforSpearGame));

        GameView gameView = new GameView();
        NeedforSpearGame.getInstance().getViewData().setGameView(gameView);
        gameView.createView();
    }

    @Override
    public void switchToHelpView() {
        needforSpearGame.setCurrentState(new HelpViewState(needforSpearGame));

        HelpView helpview = new HelpView();
        NeedforSpearGame.getInstance().getViewData().setHelpView(helpview);
        helpview.createView();
    }
}
