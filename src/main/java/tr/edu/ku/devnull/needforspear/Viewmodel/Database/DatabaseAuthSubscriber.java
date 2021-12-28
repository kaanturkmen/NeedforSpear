package tr.edu.ku.devnull.needforspear.Viewmodel.Database;

import tr.edu.ku.devnull.needforspear.Model.Player.Player;

/**
 * DatabaseAuthSubscriber is an interface to apply Observer Design Pattern.
 *
 * @author Kaan Turkmen
 */
public interface DatabaseAuthSubscriber {
    void loginResponseArrived(Player player, Integer databaseResponse);
}
