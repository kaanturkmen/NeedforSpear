package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfiniteVoidSpell implements YmirPower {
    private Random r = new Random();

    @Override
    public void triggerYmirEffect() {
        System.out.println("Infinite Void Spell is activated.");

        List<Obstacle> obstacleList = selectRandomObstacles();
        System.out.println(obstacleList.size());
        for (int i = 0; i <obstacleList.size(); i++){
            Obstacle obstacle = obstacleList.get(i);
            obstacle.freeze();
            obstacle.activateInfiniteVoid(true);
        }

    }

    public List<Obstacle> selectRandomObstacles(){
        List<Integer> randomIndices = new ArrayList<>();
        List<Obstacle> obstacleList =  NeedforSpearGame.getInstance().getGameMap().getListofObstacles();
        List<Obstacle> randomObstacleList = new ArrayList<>();

        int randNum = r.nextInt(obstacleList.size());
        if (obstacleList.size() > 8) {
            while (randomIndices.size() < 8) {
                if (!randomIndices.contains(randNum)) {
                    randomIndices.add(randNum);
                }
                randomIndices.add(randNum);
            }

            for (int i = 0; i < randomIndices.size(); i++) {
                randomObstacleList.add(obstacleList.get(randomIndices.get(i)));
            }
        }else{
            randomObstacleList = obstacleList;
        }

        return randomObstacleList;
    }

}
