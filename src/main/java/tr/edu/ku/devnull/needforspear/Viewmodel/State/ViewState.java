package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

/**
 * ViewState is an interface to specify which switch modes are available, and it is part of State design pattern.
 *
 * @author Kaan Turkmen, Can Usluel.
 */
public abstract class ViewState {
    protected NeedforSpearGame needforSpearGame;

    /**
     * Constructor of the ViewState.
     *
     * @param needforSpearGame The object which has states.
     */
    public ViewState(NeedforSpearGame needforSpearGame) {
        this.needforSpearGame = needforSpearGame;
    }

    public abstract void switchToActivationView();

    public abstract void switchToLoginView();

    public abstract void switchToSendVerificationView();

    public abstract void switchToValidateAndChangePasswordView();

    public abstract void switchToMainMenuView();

    public abstract void switchToGameView();

    public abstract void switchToHelpView();
}
