package tr.edu.ku.devnull.needforspear.View.PlayViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.*;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BuildModeHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.PlayerLivesHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GamePanel for incorporating animations of game objects, and key and mouse events
 *
 * @author Can Usluel, Melis Oktayoğlu
 */
public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, SwitchModeSubscriber, MagicalHexSubscriber {

    SphereAnimator sphereAnimator;
    ObstacleAnimator obstacleAnimator;
    NoblePhantasmAnimator npa;
    SpellAnimator spellAnimator;
    BulletAnimator bulletAnimator;

    boolean rotatingRight = false, rotatingLeft = false;
    private static boolean isGameStarted = false, isGameLoaded = false, isHexActivated=false;
    private Location phantasmLocation;


    Image background = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.GAME_BACKGROUND_IMAGE);
    double x_pos_NoblePhantasm;
    double y_pos_NoblePhantasm;
    private int delay;
    protected static Timer timer;
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




        if(NeedforSpearGame.getPlayer().getLives() <= 0 ){
            System.out.println("ups i know u died");
            timer.stop();
            endGame(NeedforSpearGame.getPlayer());
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isGameLoaded) {
            int x = e.getX();
            int y = e.getY();

            if (BuildModeHandler.getInstance().getSelectedObstacle() != null && !isGameStarted) {
                BuildModeHandler.getInstance().relocateObstacle(x, y, getGraphics(), obstacleAnimator, true);
            }
        }

        if (NeedforSpearGame.getPlayer().getLives() <= 0) endGame(NeedforSpearGame.getPlayer());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isGameLoaded) {
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

    public static void resume() {
        timer.start();
    }

    public static void pause() {
        timer.stop();
    }

    public void prepMouseListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (isGameLoaded) {
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
                if (isGameLoaded) {
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
                                        BuildModeHandler.getInstance().checkSimpleObstacleNum(NeedforSpearGame.getGameMap().retrieveSimpleObstacleNumber())  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM) &&
                                        BuildModeHandler.getInstance().checkFirmObstacleNum(NeedforSpearGame.getGameMap().retrieveFirmObstacleNumber()))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP) &&
                                        BuildModeHandler.getInstance().checkExplosiveObstacleNum(NeedforSpearGame.getGameMap().retrieveExplosiveObstacleNumber()))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT) &&
                                        BuildModeHandler.getInstance().checkGiftObstacleNum(NeedforSpearGame.getGameMap().retrieveGiftObstacleNumber())))) {

                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), "Maximum number for that obstacle type is reached", "Alert", JOptionPane.WARNING_MESSAGE);

                                } else if(BuildModeHandler.getInstance().doesObstacleCollide(obstacle)){
                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                } else if(!BuildModeHandler.getInstance().checkObstacleLocation(obstacle)){
                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), "Obstacle is too close to the phantasm!", "Alert", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                        //checks if the current number of obstacles allows deletion
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (BuildModeHandler.getInstance().getObstacleByLocation(x, y) != null) {
                                Obstacle obstacle = BuildModeHandler.getInstance().getObstacleByLocation(x, y);
                                if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE) &&
                                        BuildModeHandler.getInstance().checkSimpleObstacleNum(NeedforSpearGame.getGameMap().retrieveSimpleObstacleNumber() -1)  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM) &&
                                                BuildModeHandler.getInstance().checkFirmObstacleNum(NeedforSpearGame.getGameMap().retrieveFirmObstacleNumber() -1))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP) &&
                                                BuildModeHandler.getInstance().checkExplosiveObstacleNum(NeedforSpearGame.getGameMap().retrieveExplosiveObstacleNumber() -1))  ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT) &&
                                                BuildModeHandler.getInstance().checkGiftObstacleNum(NeedforSpearGame.getGameMap().retrieveGiftObstacleNumber() -1))) {

                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                }else{
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), "Minimum number for that obstacle type is reached", "Alert", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (isGameLoaded) {
                    int x = e.getX();
                    int y = e.getY();
                    if (!isGameStarted && BuildModeHandler.getInstance().getObstacleByLocation(x, y) != null) {
                        if (BuildModeHandler.getInstance().doesObstacleCollide(BuildModeHandler.getInstance().getObstacleByLocation(x, y)) ||
                                !BuildModeHandler.getInstance().checkObstacleLocation(BuildModeHandler.getInstance().getObstacleByLocation(x, y))){
                            BuildModeHandler.getInstance().relocateObstacle(0, 0, getGraphics(), obstacleAnimator, false);
                        }
                    } else {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            Sphere.getInstance().setMoving(true);
                        }
                    }
                }
            }
        });
        addMouseMotionListener(this);
    }

    public static void setIsGameLoaded(boolean b) {
        isGameLoaded = b;
    }

    public static boolean getIsGameLoaded() {
        return isGameLoaded;
    }

    public static boolean getIsGameStarted() {
        return isGameStarted;
    }

    public static void setIsGameStarted(boolean b) {
        isGameStarted = b;
    }


    @Override
    public void update() {
        isGameStarted = true;
    }


    public static void setIsHexActivated(boolean b) {
        isHexActivated = b;
    }

    @Override
    public void update2() {
        isHexActivated = true;
        magicalHexStartTime = System.currentTimeMillis();
    }

    public void endGame(Player player) {
        JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), "You have lost", "Alert", JOptionPane.WARNING_MESSAGE);
        player.setLives(3);
        GamePanel.setIsGameLoaded(false);
        GamePanel.setIsGameStarted(false);
        NeedforSpearGame.setGameMode(GameMode.BUILDING_MODE);
        NeedforSpearGame.setIsPaused(false);
        NeedforSpearGame.getMainFrame().getContentPane().removeAll();
        NeedforSpearGame.getMainFrame().repaint();
        NeedforSpearGame.startMainMenu();
    }
}

