package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;

public class GameViewState extends ViewState {
    public GameViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the GameView. (ActivationView)");
    }

    @Override
    public void switchToLoginView() {
        System.out.println("Invalid access from the GameView. (LoginView)");
    }

    @Override
    public void switchToSendVerificationView() {
        System.out.println("Invalid access from the GameView. (VerificationView)");
    }

    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Invalid access from the GameView. (ValidateAndChangePasswordView)");
    }

    @Override
    public void switchToMainMenuView() {
        needforSpearGame.setCurrentState(new MainMenuViewState(needforSpearGame));

        MainMenuView mainMenuView = new MainMenuView();
        NeedforSpearGame.getInstance().getViewData().setMainMenuView(mainMenuView);

//        GameView gameView = new GameView();
//        NeedforSpearGame.getInstance().setGameView(gameView);

        mainMenuView.createView();
    }

    @Override
    public void switchToGameView() {
        System.out.println("Already in GameView.");
    }
}
