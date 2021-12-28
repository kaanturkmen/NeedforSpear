package tr.edu.ku.devnull.needforspear.Viewmodel.Database;

import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;

/**
 * DatabaseSaveLoadSubscriber is an interface to apply Observer Design Pattern.
 *
 * @author Kaan Turkmen
 */
public interface DatabaseSaveLoadSubscriber {
    void gameMapResponseArrived(Integer databaseResponse, GameMap gameMap);
}
