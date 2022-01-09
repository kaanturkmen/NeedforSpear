package tr.edu.ku.devnull.needforspear.View.PlayViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.HollowPurpleSubscriber;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.*;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BuildModeHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MapHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GamePanel for incorporating animations of game objects, and key and mouse events
 *
 * @author Can Usluel, Melis Oktayoğlu
 */
public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, SwitchModeSubscriber, MagicalHexSubscriber, HollowPurpleSubscriber {
    private final SphereAnimator sphereAnimator;
    private final ObstacleAnimator obstacleAnimator;
    private final NoblePhantasmAnimator npa;
    private final SpellAnimator spellAnimator;
    private final BulletAnimator bulletAnimator;
    private final Image background = new BackgroundHandler().getRespectiveImage(Constants.UIConstants.GAME_BACKGROUND_IMAGE);
    protected Timer timer;
    private long magicalHexStartTime;
    private boolean isGameStarted = false, isHexActivated = false;

    /**
     * Constructor of GamePanel.
     *
     * @param sphereAnimator   SphereAnimator to be set.
     * @param obstacleAnimator ObstacleAnimator to be set.
     * @param npa              NoblePhantasmAnimator to be set.
     * @param spellAnimator    SpellAnimator to be set.
     * @param bulletAnimator   BulletAnimator to be set.
     */
    public GamePanel(SphereAnimator sphereAnimator, ObstacleAnimator obstacleAnimator, NoblePhantasmAnimator npa, SpellAnimator spellAnimator, BulletAnimator bulletAnimator) {
        int delay;
        if (System.getProperty("os.name").startsWith("Windows")) delay = 3;
        else delay = 8;
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

    /**
     * Triggered when action event e is performed.
     *
     * @param e ActionEvent to be used.
     */
    public void actionPerformed(ActionEvent e) {
    // will run when the timer fires
        repaint();
    }

    /**
     * Paints the component.
     *
     * @param g Graphics to be drawn.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

        if (isGameStarted) {
            sphereAnimator.draw(g);
            npa.draw(g);
            if (isHexActivated) {
                if ((System.currentTimeMillis() - magicalHexStartTime) / 1000 >= 30) {
                    setIsHexActivated(false);
                }
                bulletAnimator.draw(g);
                if (BulletAnimator.getListOfBullets().isEmpty()) {
                    BulletAnimator.getListOfBullets().add(Bullet.createLeftBullet());
                    BulletAnimator.getListOfBullets().add(Bullet.createRightBullet());
                }
            }
        }

        spellAnimator.draw(g);
        obstacleAnimator.draw(g);

        if (NeedforSpearGame.getInstance().getGameInfo().getPlayer().getLives() <= 0) {
            timer.stop();
            finishGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), Constants.UIConstants.LOSE_GAME_TXT);
        } else if (NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().size() == 0) {
            timer.stop();
            finishGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), Constants.UIConstants.WIN_GAME_TXT + NeedforSpearGame.getInstance().getGameInfo().getPlayer().getScore());
        }

    }

    /**
     * Mouse dragged event.
     *
     * @param e Event e to be used.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {
            int x = e.getX();
            int y = e.getY();

            if (BuildModeHandler.getInstance().getSelectedObstacle() != null && !isGameStarted) {
                BuildModeHandler.getInstance().relocateObstacle(x, y, getGraphics(), obstacleAnimator, true);
                if (BuildModeHandler.getInstance().getObstacleByLocation(x, y).getObstacleType().equals(Constants.ObstacleNameConstants.GIFT_OBSTACLE)) {
                    BuildModeHandler.getInstance().getObstacleByLocation(x, y).getSpell().setLocation(new Location(x, y));
                }
            }
        }

        if (NeedforSpearGame.getInstance().getGameInfo().getPlayer().getLives() <= 0)
            finishGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), Constants.UIConstants.LOSE_GAME_TXT);
    }

    /**
     * Mouse moved event.
     *
     * @param e Event e to be used.
     */
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

    /**
     * Resumes the panel.
     */
    public void resume() {
        timer.start();
    }

    /**
     * Pauses the panel.
     */
    public void pause() {
        timer.stop();
    }

    /**
     * Prepares the mouse listener.
     */
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
                        }
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
                                if (!(obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE) &&
                                        BuildModeHandler.getInstance().checkSimpleObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE)) ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE) &&
                                                BuildModeHandler.getInstance().checkFirmObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.FIRM_OBSTACLE))) ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE) &&
                                                BuildModeHandler.getInstance().checkExplosiveObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE))) ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT_OBSTACLE) &&
                                                BuildModeHandler.getInstance().checkGiftObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.GIFT_OBSTACLE))))) {

                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "Maximum number for that obstacle type is reached", "Alert", JOptionPane.WARNING_MESSAGE);

                                } else if (BuildModeHandler.getInstance().doesObstacleCollide(obstacle)) {
                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                } else if (BuildModeHandler.getInstance().checkObstacleLocation(obstacle)) {
                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "Obstacle is too close to the phantasm!", "Alert", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                        //checks if the current number of obstacles allows deletion
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (BuildModeHandler.getInstance().getObstacleByLocation(x, y) != null) {
                                Obstacle obstacle = BuildModeHandler.getInstance().getObstacleByLocation(x, y);
                                if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE) &&
                                        BuildModeHandler.getInstance().checkSimpleObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE) - 1) ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE) &&
                                                BuildModeHandler.getInstance().checkFirmObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.FIRM_OBSTACLE) - 1)) ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE) &&
                                                BuildModeHandler.getInstance().checkExplosiveObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE) - 1)) ||
                                        (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT_OBSTACLE) &&
                                                BuildModeHandler.getInstance().checkGiftObstacleNum(new MapHandler().retrieveObstacleNumber(Constants.ObstacleNameConstants.GIFT_OBSTACLE) - 1))) {

                                    BuildModeHandler.getInstance().removeObstacle(x, y, getGraphics(), BuildModeHandler.getInstance().getObstacleByLocation(x, y));
                                } else {
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
                                BuildModeHandler.getInstance().checkObstacleLocation(BuildModeHandler.getInstance().getObstacleByLocation(x, y))) {
                            BuildModeHandler.getInstance().relocateObstacle(0, 0, getGraphics(), obstacleAnimator, false);
                        }
                    } else {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (!NeedforSpearGame.getInstance().getGameInfo().getSphere().isMoving())
                                NeedforSpearGame.getInstance().getGameInfo().getSphere().setMoving(true);
                        }
                    }
                }
            }
        });
        addMouseMotionListener(this);
    }

    /**
     * Gets if the game is started.
     *
     * @return Boolean value indicating if the game is started.
     */
    public boolean getIsGameStarted() {
        return isGameStarted;
    }

    /**
     * Sets the game started boolean.
     *
     * @param b Boolean to be set.
     */
    public void setIsGameStarted(boolean b) {
        isGameStarted = b;
    }

    /**
     * Updates the game started variable.
     */
    @Override
    public void update() {
        isGameStarted = true;
    }

    /**
     * Sets the hex activated variable.
     *
     * @param b Boolean b to be set.
     */
    public void setIsHexActivated(boolean b) {
        isHexActivated = b;
    }

    /**
     * Second activate method for hex spell.
     */
    @Override
    public void updateHex() {
        isHexActivated = true;
        magicalHexStartTime = System.currentTimeMillis();
    }

    /**
     * Finishes the game.
     *
     * @param player        Player to be used.
     * @param finishGameTxt Finish game text to be displayed.
     */
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

    /**
     * Updates the hollow spell.
     */
    @Override
    public void updateHollow() {
        BuildModeHandler.getInstance().createHollowObstacles(getGraphics(), obstacleAnimator);
    }
}

