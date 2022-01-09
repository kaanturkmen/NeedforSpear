package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * InfiniteVoidSpell is a spell type which implements YmirPower interface.
 */
public class InfiniteVoidSpell implements YmirPower {

    /**
     * Triggers spell effect.
     */
    @Override
    public void triggerYmirEffect() {
        System.out.println(Constants.MessageConstants.INFINITE_VOID_TRIGGERED);

        List<Obstacle> obstacleList = selectRandomObstacles();
        System.out.println(obstacleList.size());
        for (Obstacle obstacle : obstacleList) {
            obstacle.freeze();
            obstacle.activateInfiniteVoid(true);
        }

    }

    /**
     * Selects random obstacles and puts in a list.
     *
     * @return List of obstacles which has random obstacles.
     */
    public List<Obstacle> selectRandomObstacles() {
        List<Integer> randomIndices = new ArrayList<>();
        List<Obstacle> obstacleList = NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles();
        List<Obstacle> randomObstacleList = new ArrayList<>();

        int randNum = new Random().nextInt(obstacleList.size());
        if (obstacleList.size() > Constants.ObstacleNumberConstants.INFINITE_VOID_NUM) {
            while (randomIndices.size() < Constants.ObstacleNumberConstants.INFINITE_VOID_NUM) {
                if (!randomIndices.contains(randNum)) {
                    randomIndices.add(randNum);
                }
                randomIndices.add(randNum);
            }

            for (Integer randomIndex : randomIndices) {
                randomObstacleList.add(obstacleList.get(randomIndex));
            }
        } else {
            randomObstacleList = obstacleList;
        }

        return randomObstacleList;
    }

}
