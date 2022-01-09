package tr.edu.ku.devnull.needforspear.Viewmodel.Database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.SavedMapEntry;
import tr.edu.ku.devnull.needforspear.Model.Player.Account;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * FirebaseDatabase is a database to store user activity.
 * It implements the methods from the GameDatabase to obtain dependency injection.
 *
 * @author Kaan Turkmen
 */
public class FirebaseDatabase implements GameDatabase {
    protected List<DatabaseAuthSubscriber> databaseAuthSubscribers = new ArrayList<>();
    protected List<DatabaseSaveLoadSubscriber> databaseMapSubscribers = new ArrayList<>();
    private Integer databaseResponse = 0;
    private Player confirmedPlayer = null;
    private GameMap gameMap = null;

    /**
     * Initializes the database for the further use.
     */
    @Override
    public void initialize() {
        FileInputStream refreshToken;
        try {

            refreshToken = new FileInputStream(System.getProperty(Constants.UIConstants.USER_DIRECTORY_KEYWORD) + File.separator + DatabaseCredentials.AUTH_FILE_PATH);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl(DatabaseCredentials.DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a player instance and sends it to the database.
     *
     * @param player Player to be registered.
     */
    @Override
    public synchronized void register(Player player) {
        if (player == null) return;

        getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkIfUserExistsAndRegister(dataSnapshot, player);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Logs player in by checking the database entries.
     *
     * @param player Player to be logged in.
     */
    @Override
    public synchronized void login(Player player) {
        getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                findRegisteredUser(dataSnapshot, player);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Saves game map into the database.
     *
     * @param player  Player to be combined GameMap with them.
     * @param gameMap GameMap to be combined Player with them.
     */
    @Override
    public synchronized void saveGame(Player player, GameMap gameMap) {
        if (player == null || gameMap == null) return;

        SavedMapEntry savedMapEntry = new SavedMapEntry(player, gameMap);

        getReferenceOfChild(DatabaseCredentials.DATABASE_MAPS_PATH, DatabaseCredentials.DATABASE_MAPS_CHILD_PATH)
                .child(player.getAccount().getUid())
                .setValueAsync(savedMapEntry);
    }

    /**
     * Gets an map associated with the player from database.
     *
     * @param player Player who will be searched to obtain GameMap from.
     */
    @Override
    public synchronized void loadGame(Player player) {
        if (player == null) return;

        getReferenceOfChild(DatabaseCredentials.DATABASE_MAPS_PATH, DatabaseCredentials.DATABASE_MAPS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadMapFromDatabase(dataSnapshot, player);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Sends verification mail to the given email.
     *
     * @param email Email to be send an verification code to.
     */
    @Override
    public synchronized void forgotPassword(String email) {
        getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sendNewVerification(dataSnapshot, email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Confirms the player to play the game with the given verification code.
     *
     * @param email            Email to be confirmed.
     * @param verificationCode Verification code to be checked to verify.
     */
    @Override
    public synchronized void confirmPlayer(String email, String verificationCode) {
        getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confirmUser(dataSnapshot, email, verificationCode);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Resets user password to the new password that is specified.
     *
     * @param email            New password to be set for the given email associated account.
     * @param verificationCode Verification code to be checked to verify.
     * @param newPassword      New password to be updated the old one.
     */
    @Override
    public synchronized void resetPassword(String email, String verificationCode, String newPassword) {
        getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resetPlayerPassword(dataSnapshot, email, verificationCode, newPassword);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Updates player information stored on the database.
     *
     * @param player Player who will be updated.
     */
    @Override
    public synchronized void updatePlayer(Player player) {
        getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updatePlayerOnDatabase(dataSnapshot, player);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Helper method of the updatePlayer method. Updates the score, lives and the listofSpells of the given player.
     *
     * @param dataSnapshot Current database snapshot to be searched in.
     * @param player       Player who will be updated.
     */
    private void updatePlayerOnDatabase(DataSnapshot dataSnapshot, Player player) {
        Player currentPlayer = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {

            currentPlayer = unit.getValue(Player.class);
            if (currentPlayer.getAccount().getEmail().equals(player.getAccount().getEmail())) {
                HashMap<String, Object> user = new HashMap<>();
                user.put(currentPlayer.getAccount().getUid() + "/lives", player.getLives());
                user.put(currentPlayer.getAccount().getUid() + "/score", player.getScore());
                user.put(currentPlayer.getAccount().getUid() + "/listofSpells", player.getListofSpells());
                getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).updateChildrenAsync(user);
                System.out.println("Player updated.");
            }
        }

        updatePlayerOnGameMapEntries(player);
    }

    /**
     * Updates the game map entries from the database.
     *
     * @param player Player to be updated.
     */
    private void updatePlayerOnGameMapEntries(Player player) {
        getReferenceOfChild(DatabaseCredentials.DATABASE_MAPS_PATH, DatabaseCredentials.DATABASE_MAPS_CHILD_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updatePlayerOnLoadedMaps(dataSnapshot, player);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Data cancelled.");
            }
        });
    }

    /**
     * Helper method of the updatePlayerOnGameMapEntries. Searches the player and updates it.
     *
     * @param dataSnapshot DataSnapshat to be searched in.
     * @param player       Player to be updated.
     */
    private void updatePlayerOnLoadedMaps(DataSnapshot dataSnapshot, Player player) {
        SavedMapEntry savedMapEntry = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {

            savedMapEntry = unit.getValue(SavedMapEntry.class);

            if (player.getAccount().getEmail().equals(savedMapEntry.getPlayer().getAccount().getEmail())) {
                HashMap<String, Object> user = new HashMap<>();
                user.put(savedMapEntry.getPlayer().getAccount().getUid() + "/player" + "/lives", player.getLives());
                user.put(savedMapEntry.getPlayer().getAccount().getUid() + "/player" + "/score", player.getScore());
                user.put(savedMapEntry.getPlayer().getAccount().getUid() + "/player" + "/listofSpells", player.getListofSpells());
                getReferenceOfChild(DatabaseCredentials.DATABASE_MAPS_PATH, DatabaseCredentials.DATABASE_MAPS_CHILD_PATH).updateChildrenAsync(user);
                System.out.println("Player updated on GameMapEntry.");
                break;
            }
        }

        if (savedMapEntry != null) {
            databaseResponse = DatabaseCredentials.DATABASE_SUCCESS;
        } else {
            databaseResponse = DatabaseCredentials.DATABASE_FAIL;
        }
    }

    /**
     * Helper method of the confirmPlayer method. Updates database if operation is successful
     * and it is making verified property of the user true in the database.
     *
     * @param dataSnapshot     Current database snapshot to be searched in.
     * @param email            Email to be confirmed.
     * @param verificationCode Verification code to be checked to verify.
     */
    private void confirmUser(DataSnapshot dataSnapshot, String email, String verificationCode) {
        Player currentPlayer = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {

            currentPlayer = unit.getValue(Player.class);
            if (currentPlayer.getAccount().getEmail().equals(email) && currentPlayer.getAccount().getVerificationCode().equals(Integer.parseInt(verificationCode))) {
                HashMap<String, Object> user = new HashMap<>();
                user.put(currentPlayer.getAccount().getUid() + "/account/verified", true);
                getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).updateChildrenAsync(user);
                System.out.println("User is now confirmed.");
            }
        }
    }

    /**
     * Helper method of the resetPassword method. Updates databases
     * password and verified properties if the operation is successful.
     *
     * @param dataSnapshot     Current database snapshot to be searched in.
     * @param email            New password to be set for the given email associated account.
     * @param verificationCode Verification code to be checked to verify.
     * @param newPassword      New password to be updated the old one.
     */
    private void resetPlayerPassword(DataSnapshot dataSnapshot, String email, String verificationCode, String newPassword) {
        Player currentPlayer = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {
            currentPlayer = unit.getValue(Player.class);
            if (currentPlayer.getAccount().getEmail().equals(email) && currentPlayer.getAccount().getVerificationCode().equals(Integer.parseInt(verificationCode))) {
                HashMap<String, Object> user = new HashMap<>();
                user.put(currentPlayer.getAccount().getUid() + "/account/password", newPassword);
                user.put(currentPlayer.getAccount().getUid() + "/account/verified", true);
                getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).updateChildrenAsync(user);
                System.out.println("Password reset is completed.");
            }
        }
    }

    /**
     * Helper method of forgotPassword method. Generates a new verificationCode and updates the old one.
     *
     * @param dataSnapshot Current database snapshot to be searched in.
     * @param email        verificationCode to be updated for the given email associated account.
     */
    private void sendNewVerification(DataSnapshot dataSnapshot, String email) {
        Player currentPlayer = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {
            currentPlayer = unit.getValue(Player.class);
            if (currentPlayer.getAccount().getEmail().equals(email)) {
                HashMap<String, Object> user = new HashMap<>();
                Account a = new Account();
                a.sendVerification(email);
                user.put(currentPlayer.getAccount().getUid() + "/account/verificationCode", a.getVerificationCode());
                getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).updateChildrenAsync(user);

                JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), Constants.UIConstants.NEW_VERIFICATION_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);

                System.out.println("New verification is sent.");
            }
        }
    }

    /**
     * Checks if given two players are identical.
     *
     * @param p1 Player 1 to be compared with.
     * @param p2 Player 2 to be compared with.
     * @return Integer response from the database.
     */
    private Integer checkIfValidCredentials(Player p1, Player p2) {
        if (p1 == null || p2 == null) return DatabaseCredentials.DATABASE_FAIL;

        if (!p2.getAccount().isVerified()) {
            JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), Constants.UIConstants.VERIFY_BEFORE_LOGIN_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
            System.out.println("USER IS NOT VERIFIED!");
            return DatabaseCredentials.DATABASE_FAIL;
        }

        if (p1.getAccount().getPassword().equals(p2.getAccount().getPassword())) {
            return DatabaseCredentials.DATABASE_SUCCESS;
        } else {
            JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), Constants.UIConstants.INCORRECT_PASSWORD_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
            return DatabaseCredentials.DATABASE_FAIL;
        }
    }

    /**
     * Gets reference of the child of the associated entry.
     *
     * @param parentRef Parent reference of the database.
     * @param childRef  Child reference of the database.
     * @return Database Reference to be written / read from.
     */
    private DatabaseReference getReferenceOfChild(String parentRef, String childRef) {
        final com.google.firebase.database.FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(parentRef);

        return ref.child(childRef);
    }

    /**
     * Helper method of loadGame method. Finds a associated map with the user.
     * Notifies LoadSaveSubscribers afterwards.
     *
     * @param dataSnapshot Current database snapshot to be searched in.
     * @param player       Player who will be searched to obtain GameMap from.
     */
    private void loadMapFromDatabase(DataSnapshot dataSnapshot, Player player) {
        SavedMapEntry savedMapEntry = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {

            savedMapEntry = unit.getValue(SavedMapEntry.class);

            if (player.getAccount().getEmail().equals(savedMapEntry.getPlayer().getAccount().getEmail())) {
                gameMap = savedMapEntry.getGameMap();
                break;
            }
        }

        if (savedMapEntry != null) {
            databaseResponse = DatabaseCredentials.DATABASE_SUCCESS;
        } else {
            databaseResponse = DatabaseCredentials.DATABASE_FAIL;
        }

        notifyLoadSaveSubscribers();
    }

    /**
     * Helper method of the login method. Finds a registered user's account from the database.
     * Notifies AuthSubscribers afterwards.
     *
     * @param dataSnapshot Current database snapshot to be searched in.
     * @param player       Player who will be searched in the database.
     */
    private void findRegisteredUser(DataSnapshot dataSnapshot, Player player) {
        Player currentPlayer = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {
            currentPlayer = unit.getValue(Player.class);
            if (currentPlayer.getAccount().getEmail().equals(player.getAccount().getEmail()) && currentPlayer.getAccount().getUsername().equals(player.getAccount().getUsername()))
                break;
        }

        databaseResponse = checkIfValidCredentials(player, currentPlayer);

        if (databaseResponse.equals(DatabaseCredentials.DATABASE_SUCCESS)) confirmedPlayer = currentPlayer;

        notifyAuthSubscribers();
    }

    /**
     * Helper method of the register method. Checks if player account of given credentials already exists
     * on the database. If not, registers the user.
     *
     * @param dataSnapshot Current database snapshot to be searched in.
     * @param player       Player who will be searched and registered in the database.
     */
    private void checkIfUserExistsAndRegister(DataSnapshot dataSnapshot, Player player) {
        Player currentPlayer = null;

        for (DataSnapshot unit : dataSnapshot.getChildren()) {
            currentPlayer = unit.getValue(Player.class);
            if (currentPlayer.getAccount().getEmail().equals(player.getAccount().getEmail()) || currentPlayer.getAccount().getUsername().equals(player.getAccount().getUsername())) {
                JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), Constants.UIConstants.USER_EXISTS_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
                System.out.println("Player already exists. Returning.");
                return;
            }
        }

        player.getAccount().sendVerification();
        DatabaseReference newPostRef = getReferenceOfChild(DatabaseCredentials.DATABASE_USERS_PATH, DatabaseCredentials.DATABASE_USERS_CHILD_PATH).child(player.getAccount().getUid());
        newPostRef.setValueAsync(player);
        JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), Constants.UIConstants.VERIFICATION_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Observer Design Pattern's subscription method for the authentication responses.
     *
     * @param databaseAuthSubscriber The class who inherits DatabaseAuthSubscriber interface.
     */
    @Override
    public void subscribeToAuth(DatabaseAuthSubscriber databaseAuthSubscriber) {
        databaseAuthSubscribers.add(databaseAuthSubscriber);
    }

    /**
     * Observer Design Pattern's unsubscription method for the authentication responses.
     *
     * @param databaseAuthSubscriber The class who inherits DatabaseAuthSubscriber interface.
     */
    @Override
    public void unsubscribeToAuth(DatabaseAuthSubscriber databaseAuthSubscriber) {
        databaseAuthSubscribers.remove(databaseAuthSubscriber);
    }

    /**
     * Observer Design Pattern's notification method for the authentication responses.
     */
    @Override
    public void notifyAuthSubscribers() {
        for (DatabaseAuthSubscriber databaseAuthSubscriber : databaseAuthSubscribers) {
            databaseAuthSubscriber.loginResponseArrived(confirmedPlayer, databaseResponse);
        }
    }

    /**
     * Observer Design Pattern's subscription method for the map save and retrieval responses.
     *
     * @param databaseSaveLoadSubscriber The class who inherits DatabaseSaveLoadSubscriber interface.
     */
    @Override
    public void subscribeToLoadSave(DatabaseSaveLoadSubscriber databaseSaveLoadSubscriber) {
        databaseMapSubscribers.add(databaseSaveLoadSubscriber);
    }

    /**
     * Observer Design Pattern's unsubscription method for the map save and retrieval responses.
     *
     * @param databaseSaveLoadSubscriber The class who inherits DatabaseSaveLoadSubscriber interface.
     */
    @Override
    public void unsubscribeToLoadSave(DatabaseSaveLoadSubscriber databaseSaveLoadSubscriber) {
        databaseMapSubscribers.remove(databaseSaveLoadSubscriber);
    }

    /**
     * Observer Design Pattern's notification method for the map save and retrieval responses.
     */
    @Override
    public void notifyLoadSaveSubscribers() {
        System.out.println(Arrays.toString(databaseMapSubscribers.toArray()));
        for (DatabaseSaveLoadSubscriber databaseSaveLoadSubscriber : databaseMapSubscribers) {
            databaseSaveLoadSubscriber.gameMapResponseArrived(databaseResponse, gameMap);
        }
    }
}
