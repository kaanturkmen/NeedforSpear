package tr.edu.ku.devnull.needforspear.View.PlayViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.FocusableJTextField;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.*;
import tr.edu.ku.devnull.needforspear.Viewmodel.AuthHandler.LoginHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.*;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gameview allows player to build, load or save a map and play it
 *
 * @author Can Usluel
 */
public class GameView {

    private static JButton switchRunningModeButton, createNewMapButton, saveMapButton, loadMapButton, pauseButton, resumeButton, chanceGivingSpellButton, expansionSpellButton, magicalHexButton, unstoppableSpellButton, muteButton, unmuteButton;
    private static JPanel overlayPanel, backgroundPanel;
    private static GamePanel gamePanel;
    private static JLabel score, lives;
    private static JComboBox<String> addObstacleChoice;

    private static boolean isGameViewOpened = false, areKeysLoaded = false;

    /**
     * This method constructs GameView.java
     */
    public static void createView() {
        createUIElements();
        determineUIElementsSizes();
        obtainVisibility();
        createActionListenersForGameView();
        adjustSpellButtons();
        createActionListenerForSpellButtons();
    }

    /**
     * This method builds the UI elements that are used in gameView
     */
    private static void createUIElements() {
        NeedforSpearGame.getMainFrame().getContentPane().setLayout(new BorderLayout());
        switchRunningModeButton = new JButton(Constants.UIConstants.SWITCH_TO_RUNNING_MODE_TEXT);
        createNewMapButton = new JButton(Constants.UIConstants.CREATE_A_NEW_MAP_TEXT);
        saveMapButton = new JButton(Constants.UIConstants.SAVE_A_MAP_TEXT);
        loadMapButton = new JButton(Constants.UIConstants.LOAD_GAME_TEXT);
        pauseButton = new JButton(Constants.UIConstants.PAUSE_GAME_TEXT);
        addObstacleChoice = new JComboBox<>(Constants.ArrayConstants.OBSTACLE_NAMES_ARR);
        addObstacleChoice.setSelectedItem(Constants.ObstacleNameConstants.SIMPLE);
        resumeButton = new JButton(Constants.UIConstants.RESUME_GAME_TEXT);
        overlayPanel = new JPanel();
        score = new JLabel(Constants.UIConstants.SCORE_TEXT + Constants.UIConstants.INIT_SCORE);
        lives = new JLabel(Constants.UIConstants.LIVES_TEXT + Constants.UIConstants.INIT_LIVES);
        muteButton = new JButton(Constants.UIConstants.MUTE_TEXT);
        unmuteButton = new JButton(Constants.UIConstants.UNMUTE_TEXT);
    }

    /**
     * This method sets the properties of UI elements, such as colors, to the given value
     */
    private static void determineUIElementsSizes() {
        score.setForeground(Color.white);
        lives.setForeground(Color.white);
        overlayPanel.setBackground(Constants.UIConstants.OVERLAY_BACKGROUND_COLOR);
    }

    /**
     * This method creates action listeners for the UI elements that are added to frame
     */
    private static void createActionListenersForGameView() {
        switchRunningModeButton.addActionListener(e -> {
            adjustOverlayPanelForRunningMode();
            SwitchModeHandler.getInstance().notifySubscribers();
            NeedforSpearGame.setIsPaused(false);
            NeedforSpearGame.switchToRunningMode();
        });

        loadMapButton.addActionListener(e -> {
            if (NeedforSpearGame.getIsPaused()) {
                if (NeedforSpearGame.getGameMap() == null && !GamePanel.getIsGameLoaded()) {

                    // If the user loads a map for the first time from building mode panel, and if the map exists in database.
                    SaveLoadHandler.getInstance().loadGame(NeedforSpearGame.getPlayer());
                } else if (NeedforSpearGame.getGameMap() != null && GamePanel.getIsGameLoaded()) {

                    // If used has loaded a map already
                    // deletes the current gamePanel and constructs a new one according to the data retrieved from database.
                    // If user loaded a map before, then delete it and replace it with the map in the database
                    SaveLoadHandler.getInstance().loadGame(NeedforSpearGame.getPlayer());
                } else {
                    JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), Constants.UIConstants.NON_EXISTING_MAP_ERROR_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        createNewMapButton.addActionListener(e -> {
            askForObstacles();
        });

        saveMapButton.addActionListener(e -> {
            if (NeedforSpearGame.getIsPaused()) {
                SaveLoadHandler.getInstance().saveGame(NeedforSpearGame.getPlayer(), NeedforSpearGame.getGameMap());
                LoginHandler.getInstance().updateP(NeedforSpearGame.getPlayer());
                SaveLoadHandler.getInstance().setPreviousLives(NeedforSpearGame.getPlayer().getLives());
                SaveLoadHandler.getInstance().setPreviousScore(NeedforSpearGame.getPlayer().getScore());
                SaveLoadHandler.getInstance().setPreviousSpells(NeedforSpearGame.getPlayer().getListofSpells());
            }
        });

        pauseButton.addActionListener(e -> {
            pauseButton.setVisible(false);
            NeedforSpearGame.setIsPaused(true);
            resumeButton.setVisible(true);
            GamePanel.pause();
        });

        resumeButton.addActionListener(e -> {
            pauseButton.setVisible(true);
            NeedforSpearGame.setIsPaused(false);
            resumeButton.setVisible(false);
            GamePanel.resume();
        });

        addObstacleChoice.addActionListener(e -> {
            BuildModeHandler.getInstance().setAddedObstacleType((String) addObstacleChoice.getSelectedItem());
            System.out.println(BuildModeHandler.getInstance().getAddedObstacleType());
        });

        muteButton.addActionListener(e -> {
            unmuteButton.setVisible(true);
            muteButton.setVisible(false);
            System.out.println("Game muted");
            NeedforSpearGame.setMuteModeActivated(true);
            NeedforSpearGame.stopBackgroundMusic();
        });

        unmuteButton.addActionListener(e -> {
            unmuteButton.setVisible(false);
            muteButton.setVisible(true);
            System.out.println("Game unmuted");
            NeedforSpearGame.setMuteModeActivated(false);
            NeedforSpearGame.playBackgroundMusic();
        });
    }
    private static void createActionListenerForSpellButtons(){

            chanceGivingSpellButton.addActionListener(e -> {
                if(!NeedforSpearGame.getIsPaused()){
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.CHANCE));
                }
            });
            expansionSpellButton.addActionListener(e -> {
                if(!NeedforSpearGame.getIsPaused()) {
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.EXPANSION));
                }
            });
            magicalHexButton.addActionListener(e -> {
                if(!NeedforSpearGame.getIsPaused()) {
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.HEX));
                }
            });
            unstoppableSpellButton.addActionListener(e -> {
                if(!NeedforSpearGame.getIsPaused()) {
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.UNSTOPPABLE));
                }
            });

    }
    /**
     * This method adjusts the overlay panel for running mode by changing visibilities of certain UI elements
     */
    private static void adjustOverlayPanelForRunningMode() {
        switchRunningModeButton.setVisible(false);
        addObstacleChoice.setVisible(false);
        score.setVisible(true);
        lives.setVisible(true);
        pauseButton.setVisible(true);
        createNewMapButton.setVisible(false);
        chanceGivingSpellButton.setVisible(true);
        magicalHexButton.setVisible(true);
        unstoppableSpellButton.setVisible(true);
        expansionSpellButton.setVisible(true);
        resumeButton.setVisible(false);
    }
    /**
     * This method adjusts the spell buttons for spells and draws them onto overlayPanel
     */
    private static void adjustSpellButtons(){
        chanceGivingSpellButton = new JButton(String.valueOf(0));
        expansionSpellButton = new JButton(String.valueOf(0));
        magicalHexButton = new JButton(String.valueOf(0));
        unstoppableSpellButton = new JButton(String.valueOf(0));
        overlayPanel.add(chanceGivingSpellButton, FlowLayout.LEFT);
        overlayPanel.add(magicalHexButton, FlowLayout.LEFT);
        overlayPanel.add(unstoppableSpellButton);
        overlayPanel.add(expansionSpellButton);
        chanceGivingSpellButton.setBackground(Color.GREEN.darker().darker());
        expansionSpellButton.setBackground(Color.PINK);
        magicalHexButton.setBackground(Color.CYAN.darker());
        unstoppableSpellButton.setBackground(Color.YELLOW.darker());

        chanceGivingSpellButton.setOpaque(true);
        expansionSpellButton.setOpaque(true);
        magicalHexButton.setOpaque(true);
        unstoppableSpellButton.setOpaque(true);

        chanceGivingSpellButton.setVisible(false);
        magicalHexButton.setVisible(false);
        unstoppableSpellButton.setVisible(false);
        expansionSpellButton.setVisible(false);
    }
    /**
     * This method adjusts the overlay panel for building mode by changing visibilities of certain UI elements
     */
    public static void adjustOverlayPanelForBuildingMode() {
        switchRunningModeButton.setVisible(true);
        addObstacleChoice.setVisible(true);
        createNewMapButton.setVisible(false);
        saveMapButton.setVisible(true);
        chanceGivingSpellButton.setVisible(false);
        magicalHexButton.setVisible(false);
        unstoppableSpellButton.setVisible(false);
        expansionSpellButton.setVisible(false);

    }
    /**
     * This method adds UI elements to the mainframe and sets background
     */
    private static void obtainVisibility() {
        overlayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, Constants.UIConstants.FLOWLAYOUT_HGAP, Constants.UIConstants.FLOWLAYOUT_VGAP));
        overlayPanel.add(switchRunningModeButton);
        overlayPanel.add(createNewMapButton);
        overlayPanel.add(saveMapButton);
        overlayPanel.add(loadMapButton);
        overlayPanel.add(score);
        overlayPanel.add(lives);
        overlayPanel.add(pauseButton);
        overlayPanel.add(resumeButton);
        overlayPanel.add(addObstacleChoice);
        overlayPanel.add(muteButton);
        overlayPanel.add(unmuteButton);

        lives.setVisible(false);
        score.setVisible(false);
        resumeButton.setVisible(false);
        pauseButton.setVisible(false);
        switchRunningModeButton.setVisible(false);
        addObstacleChoice.setVisible(false);
        saveMapButton.setVisible(false);
        unmuteButton.setVisible(false);

        backgroundPanel = new BackgroundHandler().getBackgroundedJPanel(Constants.UIConstants.GAME_BACKGROUND_IMAGE);

        NeedforSpearGame.getMainFrame().getContentPane().add(backgroundPanel, BorderLayout.CENTER);
        NeedforSpearGame.getMainFrame().getContentPane().add(overlayPanel, BorderLayout.NORTH);
        NeedforSpearGame.getMainFrame().revalidate();
    }

    /**
     * This method asks for obstacles and constructs a new map if user pushes create new map button
     */
    private static void askForObstacles() {
        JFrame obstacleNumberCheckFrame = new JFrame();

        FocusableJTextField dummyField = new FocusableJTextField(Constants.UIConstants.SIMPLE_OBSTACLE_NUM_TEXT);
        FocusableJTextField simpObstacleTxt = new FocusableJTextField(Constants.UIConstants.SIMPLE_OBSTACLE_NUM_TEXT);
        FocusableJTextField firmObstacleTxt = new FocusableJTextField(Constants.UIConstants.FIRM_OBSTACLE_NUM_TEXT);
        FocusableJTextField explosiveObstacleTxt = new FocusableJTextField(Constants.UIConstants.EXPLOSIVE_OBSTACLE_NUM_TEXT);
        FocusableJTextField giftObstacleTxt = new FocusableJTextField(Constants.UIConstants.GIFT_OBSTACLE_NUM_TEXT);

        JButton confirm = new JButton(Constants.UIConstants.OK_TEXT);

        simpObstacleTxt.setBounds(Constants.UIConstants.OBSTACLE_TXT_X, Constants.UIConstants.OBSTACLE_TXT_Y, Constants.UIConstants.OBSTACLE_TXT_WIDTH, Constants.UIConstants.OBSTACLE_TXT_HEIGHT);
        firmObstacleTxt.setBounds(Constants.UIConstants.OBSTACLE_TXT_X, Constants.UIConstants.OBSTACLE_TXT_Y + Constants.UIConstants.OBSTACLE_TXT_X_PADDING, Constants.UIConstants.OBSTACLE_TXT_WIDTH, Constants.UIConstants.OBSTACLE_TXT_HEIGHT);
        explosiveObstacleTxt.setBounds(Constants.UIConstants.OBSTACLE_TXT_X, Constants.UIConstants.OBSTACLE_TXT_Y + 2 * Constants.UIConstants.OBSTACLE_TXT_X_PADDING, Constants.UIConstants.OBSTACLE_TXT_WIDTH, Constants.UIConstants.OBSTACLE_TXT_HEIGHT);
        giftObstacleTxt.setBounds(Constants.UIConstants.OBSTACLE_TXT_X, Constants.UIConstants.OBSTACLE_TXT_Y + 3 * Constants.UIConstants.OBSTACLE_TXT_X_PADDING, Constants.UIConstants.OBSTACLE_TXT_WIDTH, Constants.UIConstants.OBSTACLE_TXT_HEIGHT);
        confirm.setBounds(Constants.UIConstants.OBSTACLE_TXT_X, Constants.UIConstants.OBSTACLE_TXT_Y + 4 * Constants.UIConstants.OBSTACLE_TXT_X_PADDING, Constants.UIConstants.OBSTACLE_TXT_WIDTH - 6 * Constants.UIConstants.OBSTACLE_TXT_X_PADDING, Constants.UIConstants.OBSTACLE_TXT_HEIGHT + Constants.UIConstants.OBSTACLE_TXT_Y_PADDING);

        confirm.addActionListener(e -> buildGameMap(obstacleNumberCheckFrame, simpObstacleTxt, firmObstacleTxt, explosiveObstacleTxt, giftObstacleTxt));
        obstacleNumberCheckFrame.add(dummyField);
        obstacleNumberCheckFrame.add(simpObstacleTxt);
        obstacleNumberCheckFrame.add(firmObstacleTxt);
        obstacleNumberCheckFrame.add(explosiveObstacleTxt);
        obstacleNumberCheckFrame.add(giftObstacleTxt);
        obstacleNumberCheckFrame.add(confirm);

        obstacleNumberCheckFrame.setSize(Constants.UIConstants.OBSTACLE_NUM_CHECK_FRAME_SIZE, Constants.UIConstants.OBSTACLE_NUM_CHECK_FRAME_SIZE);
        obstacleNumberCheckFrame.setLayout(null);
        obstacleNumberCheckFrame.setVisible(true);
    }

    /**
     * This method gets input from the user and constructs and calls
     * the method which builds the gamePanel that the game will be played on
     */
    private static void buildGameMap(JFrame obstacleNumberCheckFrame, JTextField simpleObstacleTxt, JTextField firmObstacleTxt, JTextField explosiveObstacleTxt, JTextField giftObstacleTxt) {
        int simpObstacleNum = 0, firmObstacleNum = 0, explosiveObstacleNum = 0, giftObstacleNum = 0;
        try {
            simpObstacleNum = Integer.parseInt(simpleObstacleTxt.getText());
            firmObstacleNum = Integer.parseInt(firmObstacleTxt.getText());
            explosiveObstacleNum = Integer.parseInt(explosiveObstacleTxt.getText());
            giftObstacleNum = Integer.parseInt(giftObstacleTxt.getText());
        } catch (NumberFormatException | NullPointerException exception) {
            JOptionPane.showMessageDialog(obstacleNumberCheckFrame, Constants.UIConstants.ENTER_VALID_NUMBER_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (BuildModeHandler.getInstance().checkObstacleNumbers(simpObstacleNum, firmObstacleNum, explosiveObstacleNum, giftObstacleNum)) {
            prepObstacles(BuildModeHandler.getInstance(), simpObstacleNum, firmObstacleNum, explosiveObstacleNum, giftObstacleNum);
            obstacleNumberCheckFrame.dispose();
            adjustOverlayPanelForBuildingMode();
            GamePanel.setIsGameLoaded(true);
            SpellHandler.getInstance().determineGiftObstaclesSpells();
            //setting player's values to their initials
            PlayerScoreHandler.setNewMapCreated(0);
            NeedforSpearGame.getPlayer().setListofSpells(new ArrayList<>());
            NeedforSpearGame.getPlayer().setLives(Constants.UIConstants.INIT_LIVES);
            NeedforSpearGame.getPlayer().setScore(Constants.UIConstants.INIT_SCORE);
        } else {
            JOptionPane.showMessageDialog(obstacleNumberCheckFrame, Constants.UIConstants.ENTER_BETWEEN_GIVEN_VALUES_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);

        }
    }

    /**
     * This method loads a map from database and switches into running mode
     */
    public static void loadAMap() {
        prepGamePanel(NeedforSpearGame.getGameMap().getListofObstacles());
        adjustOverlayPanelForRunningMode();
        SwitchModeHandler.getInstance().notifySubscribers();
        GamePanel.setIsGameLoaded(true);
        NeedforSpearGame.setIsPaused(false);
        NeedforSpearGame.switchToRunningMode();
        PlayerScoreHandler.setNewMapCreated(1);
        NeedforSpearGame.getPlayer().setScore(SaveLoadHandler.getInstance().getPreviousScore());
        NeedforSpearGame.getPlayer().setLives(SaveLoadHandler.getInstance().getPreviousLives());
        updatePlayerLives(SaveLoadHandler.getInstance().getPreviousLives());
        updatePlayerScore(SaveLoadHandler.getInstance().getPreviousScore());
        NeedforSpearGame.getPlayer().setListofSpells(SaveLoadHandler.getInstance().copyPreviousSpells());
        PlayerScoreHandler.setScore(Constants.UIConstants.INIT_SCORE);
        updateSpellNumbers();
    }

    /**
     * This method constructs a game panel using the given list in animators
     */
    private static void prepGamePanel(List<Obstacle> obstacleList) {
        gamePanel = new GamePanel(new SphereAnimator(obstacleList), new ObstacleAnimator(obstacleList), new NoblePhantasmAnimator(), new SpellAnimator(), new BulletAnimator(obstacleList));
        gamePanel.setSize(NeedforSpearGame.getMainFrame().getWidth(), NeedforSpearGame.getMainFrame().getHeight() - overlayPanel.getHeight());
        gamePanel.setLocation(0, overlayPanel.getHeight());
        SwitchModeHandler.getInstance().subscribe(gamePanel);
        MagicalHexHandler.getInstance().subscribe2(gamePanel);
        NeedforSpearGame.getMainFrame().getContentPane().remove(backgroundPanel);
        NeedforSpearGame.getMainFrame().getContentPane().add(gamePanel, BorderLayout.CENTER);

        gamePanel.setVisible(true);
        if (!areKeysLoaded()) {
            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.addKeyEventDispatcher(new KeyDispatcher());
            setAreKeysLoaded(true);
        }
    }

    /**
     * This method creates obstacle instances and sets their location
     */
    private static void prepObstacles(BuildModeHandler buildModeHandler, int simpObstacleNum, int firmObstacleNum, int explosiveObstacleNum, int giftObstacleNum) {
        buildModeHandler.prepGameMap();
        buildModeHandler.createObstacles(simpObstacleNum, firmObstacleNum, explosiveObstacleNum, giftObstacleNum);
        buildModeHandler.setObstacleLocation(Constants.UIConstants.OBSTACLE_VGAP / 2, overlayPanel.getWidth());
        List<Obstacle> obstacleList = NeedforSpearGame.getGameMap().getListofObstacles();
        prepGamePanel(obstacleList);
    }

    /**
     * @return Boolean indicating if the game view is open or not.
     */
    public static boolean getIsGameViewOpened() {
        return isGameViewOpened;
    }

    /**
     * This method sets isGameViewOpened to the given value.
     */
    public static void setIsGameViewOpened(boolean isGameViewOpened) {
        GameView.isGameViewOpened = isGameViewOpened;
    }

    /**
     * @return Boolean indicating if the key listener is loaded or not.
     */
    public static boolean areKeysLoaded() {
        return areKeysLoaded;
    }

    /**
     * This method sets areKeysLoaded to the given value.
     */
    public static void setAreKeysLoaded(boolean areKeysLoaded) {
        GameView.areKeysLoaded = areKeysLoaded;
    }

    /**
     * This method removes all the components from the gamePanel and removes gamePanel from mainFrame.
     */
    public static void removeGamePanel() {
        //gamePanel.resetKeyboardActions();
        gamePanel.removeAll();
        gamePanel.repaint();
        gamePanel.revalidate();
        GamePanel.setIsGameLoaded(false);
        GamePanel.setIsGameStarted(false);
        SwitchModeHandler.getInstance().unSubscribe(gamePanel);
        MagicalHexHandler.getInstance().unSubscribe(gamePanel);
        NeedforSpearGame.getMainFrame().getContentPane().remove(gamePanel);
        NeedforSpearGame.getMainFrame().repaint();
        NeedforSpearGame.getMainFrame().revalidate();
        gamePanel = null;
        BuildModeHandler.getInstance().resetPhantasmAndSphereLocation();
    }

    /**
     * This method updates and displays the remaining lives of player.
     */
    public static void updatePlayerLives(int life) {
        lives.setText(Constants.UIConstants.LIVES_TEXT + life);
    }

    /**
     * This method updates and displays the current score of player.
     */
    public static void updatePlayerScore(int currScore) {
        score.setText(Constants.UIConstants.SCORE_TEXT + currScore);
    }

    /**
     * This method updates and displays the current spells of player.
     */
    public static void updateSpellNumbers(){
        chanceGivingSpellButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.CHANCE)));
        magicalHexButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.HEX)));
        unstoppableSpellButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.UNSTOPPABLE)));
        expansionSpellButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.EXPANSION)));
    }
    /**
     * This method removes all the components from the overlayPanel and removes overlayPanel from mainFrame.
     */
    public static void removeOverlayPanel() {
        overlayPanel.removeAll();
        overlayPanel.repaint();
        NeedforSpearGame.getMainFrame().getContentPane().remove(overlayPanel);
        NeedforSpearGame.getMainFrame().repaint();
        overlayPanel = null;
    }
}




