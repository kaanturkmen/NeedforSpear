package tr.edu.ku.devnull.needforspear.Viewmodel.State;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

public abstract class ViewState {
    protected NeedforSpearGame needforSpearGame;

    public ViewState(NeedforSpearGame needforSpearGame) {
        this.needforSpearGame = needforSpearGame;
    }

    public abstract void switchToActivationView();
    public abstract void switchToLoginView();
    public abstract void switchToSendVerificationView();
    public abstract void switchToValidateAndChangePasswordView();
    public abstract void switchToMainMenuView();
    public abstract void switchToGameView();
}
