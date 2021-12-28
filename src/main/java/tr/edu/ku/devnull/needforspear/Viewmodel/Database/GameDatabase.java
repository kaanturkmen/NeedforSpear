package tr.edu.ku.devnull.needforspear.Viewmodel.Database;

import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;

/**
 * GameDatabase is an interface to apply dependency injection.
 *
 * @author Kaan Turkmen
 */
public interface GameDatabase {
    void initialize();

    void login(Player player);

    void register(Player player);

    void saveGame(Player player, GameMap gameMap);

    void loadGame(Player player);

    void forgotPassword(String email);

    void confirmPlayer(String email, String verificationCode);

    void resetPassword(String email, String verificationCode, String newPassword);

    void updatePlayer(Player player);

    void subscribeToAuth(DatabaseAuthSubscriber databaseAuthSubscriber);

    void unsubscribeToAuth(DatabaseAuthSubscriber databaseAuthSubscriber);

    void notifyAuthSubscribers();

    void subscribeToLoadSave(DatabaseSaveLoadSubscriber databaseSaveLoadSubscriber);

    void unsubscribeToLoadSave(DatabaseSaveLoadSubscriber databaseSaveLoadSubscriber);

    void notifyLoadSaveSubscribers();
}
