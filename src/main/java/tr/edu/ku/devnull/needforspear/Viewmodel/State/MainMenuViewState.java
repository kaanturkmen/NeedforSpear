package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.View.PlayViews.HelpView;

/**
 * MainMenuViewState is a member of State Design Pattern.
 *
 * @author Kaan Turkmen, Can Usluel.
 */
public class MainMenuViewState extends ViewState {

    /**
     * Constructor of the MainMenuViewState.
     *
     * @param needforSpearGame The object which has states.
     */
    public MainMenuViewState(NeedforSpearGame needforSpearGame) {
        super(needforSpearGame);
    }

    /**
     * Event to be performed when switched to the activation view.
     */
    @Override
    public void switchToActivationView() {
        System.out.println("Invalid access from the MainMenuView. (ActivationView)");
    }

    /**
     * Event to be performed when switched to the login view.
     */
    @Override
    public void switchToLoginView() {
        System.out.println("Invalid access from the GameView. (LoginView)");
    }

    /**
     * Event to be performed when switched to send verification view.
     */
    @Override
    public void switchToSendVerificationView() {
        System.out.println("Invalid access from the MainMenuView. (SendVerificationView)");
    }

    /**
     * Event to be performed when switched to validate and change password view.
     */
    @Override
    public void switchToValidateAndChangePasswordView() {
        System.out.println("Invalid access from the MainMenuView. (ValidateAndChangePasswordView)");
    }

    /**
     * Event to be performed when switched to the switch to main menu view.
     */
    @Override
    public void switchToMainMenuView() {
        System.out.println("Already in MainMenuView.");
    }

    /**
     * Event to be performed when switched to the game view.
     */
    @Override
    public void switchToGameView() {
        needforSpearGame.setCurrentState(new GameViewState(needforSpearGame));

        GameView gameView = new GameView();
        NeedforSpearGame.getInstance().getViewData().setGameView(gameView);
        gameView.createView();
    }

    /**
     * Event to be performed when switched to the help view.
     */
    @Override
    public void switchToHelpView() {
        needforSpearGame.setCurrentState(new HelpViewState(needforSpearGame));

        HelpView helpview = new HelpView();
        NeedforSpearGame.getInstance().getViewData().setHelpView(helpview);
        helpview.createView();
    }
}
