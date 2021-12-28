package tr.edu.ku.devnull.needforspear.Viewmodel.AuthHandler;

import tr.edu.ku.devnull.needforspear.Model.Database.DatabaseAuthSubscriber;
import tr.edu.ku.devnull.needforspear.Model.Database.DatabaseCredentials;
import tr.edu.ku.devnull.needforspear.Model.Player.Account;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.SaveLoadHandler;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * LoginHandler is Controller Design Pattern
 * to achieve MVVM Design on authentication actions.
 *
 * @author Kaan Turkmen
 */
public class LoginHandler implements DatabaseAuthSubscriber {
    private static LoginHandler onlyInstance = null;
    private Player player;

    /**
     * Private Constructor for the LoginHandler.
     */
    private LoginHandler() {
        NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().subscribeToAuth(this);
    }

    /**
     * Singleton Design Pattern's getInstance Method for LoginHandler.
     */
    public static synchronized LoginHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new LoginHandler();
            NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().initialize();
        }

        return onlyInstance;
    }

    /**
     * A call to the database method to register the player.
     *
     * @param username Username of the player.
     * @param email    Email address of the player.
     * @param password Password of the player.
     */
    public void register(String username, String email, String password) {
        Thread t = new Thread(() -> NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().register(new Player(new Account(username, email, password))));
        t.start();
    }

    public void updateP(Player p) {
        Thread t = new Thread(() -> NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().updatePlayer(p));
        t.start();
    }

    /**
     * A call to the database method to login the player.
     *
     * @param username Username of the player.
     * @param email    Email address of the player.
     * @param password Password of the player.
     */
    public void login(String username, String email, String password) {
        Thread t = new Thread(() -> NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().login(new Player(new Account(username, email, password))));
        t.start();
    }

    /**
     * A call to the database method to request password reset of the player.
     *
     * @param email Email address of the player.
     */
    public void forgotPassword(String email) {
        Thread t = new Thread(() -> NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().forgotPassword(email));
        t.start();
    }

    /**
     * A call to the database method to confirm the player.
     *
     * @param email            Email address of the player.
     * @param verificationCode Verification code of the player.
     */
    public void confirmUser(String email, String verificationCode) {
        Thread t = new Thread(() -> NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().confirmPlayer(email, verificationCode));
        t.start();
    }

    /**
     * A call to the database method to create new password for the player.
     *
     * @param email            Email address of the player.
     * @param verificationCode Verification code of the player.
     * @param newPassword      New password of the user.
     */
    public void resetPassword(String email, String verificationCode, String newPassword) {
        Thread t = new Thread(() -> NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().resetPassword(email, verificationCode, newPassword));
        t.start();
    }

    /**
     * Checks if the given string is an email address.
     *
     * @param email The string to be checked if it is an email address.
     * @return Boolean value if it is an email address.
     */
    public boolean isMail(String email) {
        if (email == null) return false;

        String rgx = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(rgx);
        return pat.matcher(email).matches();
    }

    /**
     * A method for Observer Design Pattern to be called by the database when results are arrived.
     *
     * @param player           Player instance from the database.
     * @param databaseResponse Database response indicating the operation's success or failure.
     */
    @Override
    public void loginResponseArrived(Player player, Integer databaseResponse) {
        // TODO Pop up the messages.
        this.player = player;
        System.out.println(player.getLives());

        if (databaseResponse.equals(DatabaseCredentials.DATABASE_SUCCESS)) {
            NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().removeAll();
            NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().repaint();
            NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().revalidate();
            NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().repaint();
            if(player.getListofSpells() == null){
                player.setListofSpells(new ArrayList<>());
            }
            NeedforSpearGame.getInstance().getGameInfo().setPlayer(player);
            SaveLoadHandler.getInstance().setPreviousLives(player.getLives());
            SaveLoadHandler.getInstance().setPreviousScore(player.getScore());
            SaveLoadHandler.getInstance().initializePreviousSpells(player.getListofSpells());
            NeedforSpearGame.getInstance().startMainMenu();
        } else if (databaseResponse.equals(DatabaseCredentials.DATABASE_FAIL)) {
            System.out.println("Failed");
        }
    }
}
