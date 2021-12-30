package tr.edu.ku.devnull.needforspear.View.PlayViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.HollowPurpleSubscriber;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.*;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BuildModeHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MapHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GamePanel for incorporating animations of game objects, and key and mouse events
 *
 * @author Can Usluel, Melis Oktayoğlu
 */
public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, SwitchModeSubscriber, MagicalHexSubscriber, HollowPurpleSubscriber {

    SphereAnimator sphereAnimator;
    ObstacleAnimator obstacleAnimator;
    NoblePhantasmAnimator npa = new NoblePhantasmAnimator();
    SpellAnimator spellAnimator;
    BulletAnimator bulletAnimator;
    NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
    private MovementHandler movementHandler = new MovementHandler();
    boolean rotatingRight = false, rotatingLeft = false;
    private boolean isGameStarted = false, isHexActivated=false;
    private Location phantasmLocation;


    Image background = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.GAME_BACKGROUND_IMAGE);
    double x_pos_NoblePhantasm;
    double y_pos_NoblePhantasm;
    private int delay;
    protected Timer timer;
    long magicalHexStartTime;


    public GamePanel(SphereAnimator sphereAnimator, ObstacleAnimator obstacleAnimator, NoblePhantasmAnimator npa, SpellAnimator spellAnimator, BulletAnimator bulletAnimator) {
        if(System.getProperty("os.name").startsWith("Windows")) this.delay = 3;
        else this.delay = 8;
        phantasmLocation = NoblePhantasm.getInstance().getLocation();
        x_pos_NoblePhantasm = phantasmLocation.getXCoordinates();
        y_pos_NoblePhantasm = phantasmLocation.getYCoordinates();
        this.sphereAnimator = sphereAnimator;
        this.obstacleAnimator = obstacleAnimator;
        this.npa = npa;
        this.spellAnimator = spellAnimator;
        this.bulletAnimator = bulletAnimator;
        prepMouseListener();
        timer = new Timer(delay, this);
        timer.start();// start the timer
        setFocusTraversalKeysEnabled(false);

    }
    public void actionPerformed(ActionEvent e)
    // will run when the timer fires
    {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

        if (isGameStarted) {
            sphereAnimator.draw(g);
            npa.draw(g);
            if (isHexActivated) {
                if((System.currentTimeMillis()-magicalHexStartTime)/1000 >=30 ){
                    setIsHexActivated(false);
                }
                bulletAnimator.draw(g);
                if(bulletAnimator.listOfBullets.isEmpty()){
                    BulletAnimator.listOfBullets.add(Bullet.createLeftBullet());
                    BulletAnimator.listOfBullets.add(Bullet.createRightBullet());
                }
            }
        }

        spellAnimator.draw(g);
        obstacleAnimator.draw(g);




        if(NeedforSpearGame.getInstance().getGameInfo().getPlayer().getLives() <= 0 ){
            System.out.println("ups i know u died");
            timer.stop();
            finishGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), Constants.UIConstants.LOSE_GAME_TXT);
        }else if(NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().size() == 0){
            System.out.println("you have won");
            timer.stop();
            finishGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), Constants.UIConstants.WIN_GAME_TXT);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {
            int x = e.getX();
            int y = e.getY();

            if (BuildModeHandler.getInstance().getSelectedObstacle() != null && !isGameStarted) {
                BuildModeHandler.getInstance().relocateObstacle(x, y, getGraphics(), obstacleAnimator, true);
            }
        }

        if (NeedforSpearGame.getInstance().getGameInfo().getPlayer().getLives() <= 0) finishGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), Constants.UIConstants.LOSE_GAME_TXT);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {
            int x = e.getX();
            int y = e.getY();
            if (!isGameStarted) {
                if (BuildModeHandler.getInstance().getObstacleByLocation(x, y) != null) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }

    public void resume() {
        timer.start();
    }

    public void pause() {
        timer.stop();
    }

    public void prepMouseListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {
                    int x = e.getX();
                    int y = e.getY();

                    if (!isGameStarted) {
                        BuildModeHandler.getInstance().setSelectedObstacle(BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                        if (BuildModeHandler.getInstance().getSelectedObstacle() != null) {
                            BuildModeHandler.getInstance().setPreviousLocation(BuildModeHandler.getInstance().getSelectedObstacle().getLocation());
                            System.out.println(BuildModeHandler.getInstance().getSelectedObstacle().getLocation().getXCoordinates());
                        }
                        System.out.println(x);
                    }
                }
            }

            @Override
            //adding or removing obstacles via mouse click
            public void mouseClicked(MouseEvent e) {
                if (NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {
                    int x = e.getX();
                    int y = e.getY();
                    if (!isGameStarted) {
                        //placing obstacles to the given location if available
                        if (e.getClickCount() == 2) {
                            if (BuildModeHandler.getInstance().getObstacleByLocation(x, y) == null) {
                                //checking each obstacle number individually since one obstacle type can be at maximum
                                //but player may try to add an obstacle whose number limit is not max
                                BuildModeHandler.getInstance().createNewObstacle(x, y, BuildModeHandler.getInstance().getAddedObstacleType(), getGraphics(), obstacleAnimator);
                                Obstacle obstacle = BuildModeHandler.getInstance().getObstacleByLocation(x, y);
                                if (!(obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE) &&
                                        BuildModeHandler.getInstance().checkSimpleObstacleNum(new MapHandler().retrieveSimpleObstacleNumber())  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM) &&
                                        BuildModeHandler.getInstance().checkFirmObstacleNum(new MapHandler().retrieveFirmObstacleNumber()))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP) &&
                                        BuildModeHandler.getInstance().checkExplosiveObstacleNum(new MapHandler().retrieveExplosiveObstacleNumber()))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT) &&
                                        BuildModeHandler.getInstance().checkGiftObstacleNum(new MapHandler().retrieveGiftObstacleNumber())))) {

                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "Maximum number for that obstacle type is reached", "Alert", JOptionPane.WARNING_MESSAGE);

                                } else if(BuildModeHandler.getInstance().doesObstacleCollide(obstacle)){
                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                } else if(BuildModeHandler.getInstance().checkObstacleLocation(obstacle)){
                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "Obstacle is too close to the phantasm!", "Alert", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                        //checks if the current number of obstacles allows deletion
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (BuildModeHandler.getInstance().getObstacleByLocation(x, y) != null) {
                                Obstacle obstacle = BuildModeHandler.getInstance().getObstacleByLocation(x, y);
                                if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE) &&
                                        BuildModeHandler.getInstance().checkSimpleObstacleNum(new MapHandler().retrieveSimpleObstacleNumber() -1)  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM) &&
                                                BuildModeHandler.getInstance().checkFirmObstacleNum(new MapHandler().retrieveFirmObstacleNumber() -1))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP) &&
                                                BuildModeHandler.getInstance().checkExplosiveObstacleNum(new MapHandler().retrieveExplosiveObstacleNumber() -1))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT) &&
                                                BuildModeHandler.getInstance().checkGiftObstacleNum(new MapHandler().retrieveGiftObstacleNumber() -1))) {

                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                }else{
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "Minimum number for that obstacle type is reached", "Alert", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {
                    int x = e.getX();
                    int y = e.getY();
                    if (!isGameStarted && BuildModeHandler.getInstance().getObstacleByLocation(x, y) != null) {
                        if (BuildModeHandler.getInstance().doesObstacleCollide(BuildModeHandler.getInstance().getObstacleByLocation(x, y)) ||
                                BuildModeHandler.getInstance().checkObstacleLocation(BuildModeHandler.getInstance().getObstacleByLocation(x, y))){
                            BuildModeHandler.getInstance().relocateObstacle(0, 0, getGraphics(), obstacleAnimator, false);
                        }
                    } else {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            NeedforSpearGame.getInstance().getGameInfo().getSphere().setMoving(true);
                        }
                    }
                }
            }
        });
        addMouseMotionListener(this);
    }

    public boolean getIsGameStarted() {
        return isGameStarted;
    }

    public void setIsGameStarted(boolean b) {
        isGameStarted = b;
    }


    @Override
    public void update() {
        isGameStarted = true;
    }


    public void setIsHexActivated(boolean b) {
        isHexActivated = b;
    }

    @Override
    public void update2() {
        isHexActivated = true;
        magicalHexStartTime = System.currentTimeMillis();
    }

    public void finishGame(Player player, String finishGameTxt) {
        NeedforSpearGame.getInstance().stopYmirAction();
        JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), finishGameTxt, "Alert", JOptionPane.WARNING_MESSAGE);
        player.setLives(3);
        NeedforSpearGame.getInstance().getGameInfo().setGameLoaded(false);
        NeedforSpearGame.getInstance().getViewData().getGameView().getGamePanel().setIsGameStarted(false);
        NeedforSpearGame.getInstance().getGameInfo().setGameMode(GameMode.BUILDING_MODE);
        NeedforSpearGame.getInstance().getGameInfo().setPaused(false);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().removeAll();
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().repaint();
        NeedforSpearGame.getInstance().startMainMenu();
    }

    @Override
    public void updateHollow() {
        BuildModeHandler.getInstance().createHollowObstacles(getGraphics(), obstacleAnimator);
    }
}

