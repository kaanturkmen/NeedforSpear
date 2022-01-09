package tr.edu.ku.devnull.needforspear.View.PlayViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.FocusableJTextField;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.*;
import tr.edu.ku.devnull.needforspear.Viewmodel.AuthHandler.LoginHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.SoundHandler;
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

    private JButton switchRunningModeButton, createNewMapButton, saveMapButton, loadMapButton, pauseButton,
            resumeButton, expansionSpellButton, magicalHexButton, unstoppableSpellButton,
            muteButton, unmuteButton, changeDifficultyButton, backToMenuButton;
    private JTextField difficultyField;
    private JPanel overlayPanel, backgroundPanel;
    private GamePanel gamePanel;
    private JLabel score, lives;
    private JComboBox<String> addObstacleChoice;
    private JFrame obstacleNumberCheckFrame;

    private boolean areKeysLoaded = false;

    /**
     * This method constructs GameView.java
     */
    public void createView() {
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
    private void createUIElements() {
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().setLayout(new BorderLayout());
        switchRunningModeButton = new JButton(Constants.UIConstants.SWITCH_TO_RUNNING_MODE_TEXT);
        createNewMapButton = new JButton(Constants.UIConstants.CREATE_A_NEW_MAP_TEXT);
        saveMapButton = new JButton(Constants.UIConstants.SAVE_A_MAP_TEXT);
        loadMapButton = new JButton(Constants.UIConstants.LOAD_GAME_TEXT);
        pauseButton = new JButton(Constants.UIConstants.PAUSE_GAME_TEXT);
        addObstacleChoice = new JComboBox<>(Constants.ArrayConstants.OBSTACLE_NAMES_ARR);
        addObstacleChoice.setSelectedItem(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE);
        resumeButton = new JButton(Constants.UIConstants.RESUME_GAME_TEXT);
        backToMenuButton = new JButton(Constants.UIConstants.BACK_TO_MENU_TEXT);
        overlayPanel = new JPanel();
        score = new JLabel(Constants.UIConstants.SCORE_TEXT + Constants.UIConstants.INIT_SCORE);
        lives = new JLabel(Constants.UIConstants.LIVES_TEXT + Constants.UIConstants.INIT_LIVES);
        muteButton = new JButton(Constants.UIConstants.MUTE_TEXT);
        unmuteButton = new JButton(Constants.UIConstants.UNMUTE_TEXT);
        changeDifficultyButton = new JButton(Constants.UIConstants.CHANGE_DIFFICULTY_TEXT);
        difficultyField = new JTextField(6);
    }

    /**
     * This method sets the properties of UI elements, such as colors, to the given value
     */
    private void determineUIElementsSizes() {
        score.setForeground(Color.white);
        lives.setForeground(Color.white);
        overlayPanel.setBackground(Constants.UIConstants.OVERLAY_BACKGROUND_COLOR);
    }

    /**
     * This method creates action listeners for the UI elements that are added to frame
     */
    private void createActionListenersForGameView() {
        switchRunningModeButton.addActionListener(e -> {
            adjustOverlayPanelForRunningMode();
            SwitchModeHandler.getInstance().notifySubscribers();
            NeedforSpearGame.getInstance().getGameInfo().setPaused(false);
            NeedforSpearGame.getInstance().switchToRunningMode();
            NeedforSpearGame.getInstance().startYmirAction();
        });

        loadMapButton.addActionListener(e -> {
            if (NeedforSpearGame.getInstance().getGameInfo().isPaused()) {
                if (NeedforSpearGame.getInstance().getGameInfo().getGameMap() == null && !NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {

                    // If the user loads a map for the first time from building mode panel, and if the map exists in database.
                    SaveLoadHandler.getInstance().loadGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer());
                } else if (NeedforSpearGame.getInstance().getGameInfo().getGameMap() != null && NeedforSpearGame.getInstance().getGameInfo().isGameLoaded()) {

                    // If used has loaded a map already
                    // deletes the current gamePanel and constructs a new one according to the data retrieved from database.
                    // If user loaded a map before, then delete it and replace it with the map in the database
                    SaveLoadHandler.getInstance().loadGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer());
                } else {
                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), Constants.UIConstants.NON_EXISTING_MAP_ERROR_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        createNewMapButton.addActionListener(e -> {
            askForObstacles();
        });

        saveMapButton.addActionListener(e -> {
            if (NeedforSpearGame.getInstance().getGameInfo().isPaused()) {
                SaveLoadHandler.getInstance().saveGame(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), NeedforSpearGame.getInstance().getGameInfo().getGameMap());
                LoginHandler.getInstance().updateP(NeedforSpearGame.getInstance().getGameInfo().getPlayer());
                SaveLoadHandler.getInstance().setPreviousLives(NeedforSpearGame.getInstance().getGameInfo().getPlayer().getLives());
                SaveLoadHandler.getInstance().setPreviousScore(NeedforSpearGame.getInstance().getGameInfo().getPlayer().getScore());
                SaveLoadHandler.getInstance().setPreviousSpells(NeedforSpearGame.getInstance().getGameInfo().getPlayer().getListofSpells());
            }
        });

        pauseButton.addActionListener(e -> {
            pauseButton.setVisible(false);
            NeedforSpearGame.getInstance().getGameInfo().setPaused(true);
            resumeButton.setVisible(true);
            NeedforSpearGame.getInstance().getViewData().getGameView().getGamePanel().pause();
            NeedforSpearGame.getInstance().stopYmirAction();
        });

        resumeButton.addActionListener(e -> {
            pauseButton.setVisible(true);
            NeedforSpearGame.getInstance().getGameInfo().setPaused(false);
            resumeButton.setVisible(false);
            NeedforSpearGame.getInstance().getViewData().getGameView().getGamePanel().resume();
            NeedforSpearGame.getInstance().startYmirAction();
        });

        addObstacleChoice.addActionListener(e -> {
            BuildModeHandler.getInstance().setAddedObstacleType((String) addObstacleChoice.getSelectedItem());
        });

        muteButton.addActionListener(e -> {
            unmuteButton.setVisible(true);
            muteButton.setVisible(false);
            NeedforSpearGame.getInstance().getGameInfo().setMuteModeActivated(true);
            SoundHandler.getInstance().stopBackgroundMusic();
        });

        unmuteButton.addActionListener(e -> {
            unmuteButton.setVisible(false);
            muteButton.setVisible(true);
            NeedforSpearGame.getInstance().getGameInfo().setMuteModeActivated(false);
            SoundHandler.getInstance().playBackgroundMusic();
        });

        changeDifficultyButton.addActionListener(e -> {
            NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().changeDifficulty();
            difficultyField.setText(NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty().toString());
        });

        backToMenuButton.addActionListener(e -> {
            NeedforSpearGame.getInstance().startMainMenu();
        });
    }

    /**
     * This method creates action listeners for the spell buttons.
     */
    private void createActionListenerForSpellButtons() {

        expansionSpellButton.addActionListener(e -> {
            if (!NeedforSpearGame.getInstance().getGameInfo().isPaused()) {
                SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.EXPANSION));
            }
        });

        magicalHexButton.addActionListener(e -> {
            if (!NeedforSpearGame.getInstance().getGameInfo().isPaused()) {
                SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.HEX));
            }
        });
        unstoppableSpellButton.addActionListener(e -> {
            if (!NeedforSpearGame.getInstance().getGameInfo().isPaused()) {
                SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.UNSTOPPABLE));
            }
        });

    }

    /**
     * This method adjusts the overlay panel for running mode by changing visibilities of certain UI elements
     */
    private void adjustOverlayPanelForRunningMode() {
        switchRunningModeButton.setVisible(false);
        addObstacleChoice.setVisible(false);
        score.setVisible(true);
        lives.setVisible(true);
        pauseButton.setVisible(true);
        createNewMapButton.setVisible(false);
        magicalHexButton.setVisible(true);
        unstoppableSpellButton.setVisible(true);
        expansionSpellButton.setVisible(true);
        resumeButton.setVisible(false);
        changeDifficultyButton.setVisible(false);
        difficultyField.setVisible(false);
    }

    /**
     * This method adjusts the spell buttons for spells and draws them onto overlayPanel
     */
    private void adjustSpellButtons() {
        expansionSpellButton = new JButton(String.valueOf(0));
        magicalHexButton = new JButton(String.valueOf(0));
        unstoppableSpellButton = new JButton(String.valueOf(0));
        overlayPanel.add(magicalHexButton, FlowLayout.LEFT);
        overlayPanel.add(unstoppableSpellButton, FlowLayout.LEFT);
        overlayPanel.add(expansionSpellButton);
        expansionSpellButton.setBackground(Color.PINK);
        magicalHexButton.setBackground(Color.CYAN.darker());
        unstoppableSpellButton.setBackground(Color.YELLOW.darker());

        expansionSpellButton.setOpaque(true);
        magicalHexButton.setOpaque(true);
        unstoppableSpellButton.setOpaque(true);

        magicalHexButton.setVisible(false);
        unstoppableSpellButton.setVisible(false);
        expansionSpellButton.setVisible(false);
    }

    /**
     * This method adjusts the overlay panel for building mode by changing visibilities of certain UI elements
     */
    public void adjustOverlayPanelForBuildingMode() {
        switchRunningModeButton.setVisible(true);
        addObstacleChoice.setVisible(true);
        changeDifficultyButton.setVisible(true);
        difficultyField.setVisible(true);
        createNewMapButton.setVisible(false);
        saveMapButton.setVisible(true);
        magicalHexButton.setVisible(false);
        unstoppableSpellButton.setVisible(false);
        expansionSpellButton.setVisible(false);

    }

    /**
     * This method adds UI elements to the mainframe and sets background
     */
    private void obtainVisibility() {
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
        overlayPanel.add(changeDifficultyButton);
        overlayPanel.add(difficultyField);
        overlayPanel.add(backToMenuButton);

        lives.setVisible(false);
        score.setVisible(false);
        resumeButton.setVisible(false);
        pauseButton.setVisible(false);
        switchRunningModeButton.setVisible(false);
        addObstacleChoice.setVisible(false);
        saveMapButton.setVisible(false);
        if (NeedforSpearGame.getInstance().getGameInfo().isMuteModeActivated()) {
            unmuteButton.setVisible(true);
            muteButton.setVisible(false);
        } else {
            unmuteButton.setVisible(false);
        }
        changeDifficultyButton.setVisible(false);
        difficultyField.setVisible(false);
        difficultyField.setEditable(false);
        difficultyField.setHorizontalAlignment(JTextField.CENTER);
        difficultyField.setText(NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty().toString());


        backgroundPanel = new BackgroundHandler().getBackgroundedJPanel(Constants.UIConstants.GAME_BACKGROUND_IMAGE);

        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().add(backgroundPanel, BorderLayout.CENTER);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().add(overlayPanel, BorderLayout.NORTH);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().revalidate();
    }

    /**
     * This method asks for obstacles and constructs a new map if user pushes create new map button
     */
    private void askForObstacles() {
        if (obstacleNumberCheckFrame != null) {
            obstacleNumberCheckFrame.dispose();
            obstacleNumberCheckFrame = null;
        }
        obstacleNumberCheckFrame = new JFrame();

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

        confirm.addActionListener(e -> buildGameMap(simpObstacleTxt, firmObstacleTxt, explosiveObstacleTxt, giftObstacleTxt));
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
    private void buildGameMap(JTextField simpleObstacleTxt, JTextField firmObstacleTxt, JTextField explosiveObstacleTxt, JTextField giftObstacleTxt) {
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
            obstacleNumberCheckFrame = null;
            adjustOverlayPanelForBuildingMode();
            NeedforSpearGame.getInstance().getGameInfo().setGameLoaded(true);
            SpellHandler.getInstance().determineGiftObstaclesSpells();
            //setting player's values to their initials
            PlayerScoreHandler.getInstance().setNewMapCreated(0);
            NeedforSpearGame.getInstance().getGameInfo().getPlayer().setListofSpells(new ArrayList<>());
            NeedforSpearGame.getInstance().getGameInfo().getPlayer().setLives(Constants.UIConstants.INIT_LIVES);
            NeedforSpearGame.getInstance().getGameInfo().getPlayer().setScore(Constants.UIConstants.INIT_SCORE);
        } else {
            JOptionPane.showMessageDialog(obstacleNumberCheckFrame, Constants.UIConstants.ENTER_BETWEEN_GIVEN_VALUES_TEXT, Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * This method loads a map from database and switches into running mode
     */
    public void loadAMap() {
        prepGamePanel(NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles());
        adjustOverlayPanelForRunningMode();
        SwitchModeHandler.getInstance().notifySubscribers();
        NeedforSpearGame.getInstance().getGameInfo().setGameLoaded(true);
        NeedforSpearGame.getInstance().getGameInfo().setPaused(false);
        NeedforSpearGame.getInstance().switchToRunningMode();
        PlayerScoreHandler.getInstance().setNewMapCreated(1);
        NeedforSpearGame.getInstance().getGameInfo().getPlayer().setScore(SaveLoadHandler.getInstance().getPreviousScore());
        NeedforSpearGame.getInstance().getGameInfo().getPlayer().setLives(SaveLoadHandler.getInstance().getPreviousLives());
        updatePlayerLives(SaveLoadHandler.getInstance().getPreviousLives());
        updatePlayerScore(SaveLoadHandler.getInstance().getPreviousScore());
        NeedforSpearGame.getInstance().getGameInfo().getPlayer().setListofSpells(SaveLoadHandler.getInstance().copyPreviousSpells());
        PlayerScoreHandler.getInstance().setScore(Constants.UIConstants.INIT_SCORE);
        updateSpellNumbers();
        NeedforSpearGame.getInstance().startYmirAction();
    }

    /**
     * This method constructs a game panel using the given list in animators
     */
    private void prepGamePanel(List<Obstacle> obstacleList) {
        gamePanel = new GamePanel(new SphereAnimator(obstacleList), new ObstacleAnimator(obstacleList), new NoblePhantasmAnimator(), new SpellAnimator(), new BulletAnimator(obstacleList));
        gamePanel.setSize(NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getWidth(), NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getHeight() - overlayPanel.getHeight());
        gamePanel.setLocation(0, overlayPanel.getHeight());
        SwitchModeHandler.getInstance().subscribe(gamePanel);
        MagicalHexHandler.getInstance().subscribe2(gamePanel);

        HollowPurpleHandler.getInstance().subscribe(gamePanel);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().remove(backgroundPanel);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().add(gamePanel, BorderLayout.CENTER);

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
    private void prepObstacles(BuildModeHandler buildModeHandler, int simpObstacleNum, int firmObstacleNum, int explosiveObstacleNum, int giftObstacleNum) {
        buildModeHandler.prepGameMap();
        buildModeHandler.createObstacles(simpObstacleNum, firmObstacleNum, explosiveObstacleNum, giftObstacleNum);
        buildModeHandler.setObstacleLocation(Constants.UIConstants.OBSTACLE_VGAP / 2, overlayPanel.getWidth());
        List<Obstacle> obstacleList = NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles();
        prepGamePanel(obstacleList);
    }

    /**
     * @return Boolean indicating if the key listener is loaded or not.
     */
    public boolean areKeysLoaded() {
        return areKeysLoaded;
    }

    /**
     * This method sets areKeysLoaded to the given value.
     */
    public void setAreKeysLoaded(boolean areKeysLoaded) {
        this.areKeysLoaded = areKeysLoaded;
    }

    /**
     * This method removes all the components from the gamePanel and removes gamePanel from mainFrame.
     */
    public void removeGamePanel() {
        gamePanel.removeAll();
        gamePanel.repaint();
        gamePanel.revalidate();
        NeedforSpearGame.getInstance().getGameInfo().setGameLoaded(false);
        NeedforSpearGame.getInstance().getViewData().getGameView().getGamePanel().setIsGameStarted(false);
        SwitchModeHandler.getInstance().unSubscribe(gamePanel);
        MagicalHexHandler.getInstance().unSubscribe(gamePanel);

        HollowPurpleHandler.getInstance().unSubscribe(gamePanel);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().remove(gamePanel);
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().repaint();
        NeedforSpearGame.getInstance().getGameInfo().getMainFrame().revalidate();

        gamePanel = null;
        NeedforSpearGame.getInstance().getGameInfo().getSphere().setMoving(false);
        BuildModeHandler.getInstance().resetPhantasmAndSphereLocation();
    }

    /**
     * This method updates and displays the remaining lives of player.
     */
    public void updatePlayerLives(int life) {
        lives.setText(Constants.UIConstants.LIVES_TEXT + life);
    }

    /**
     * This method updates and displays the current score of player.
     */
    public void updatePlayerScore(int currScore) {
        score.setText(Constants.UIConstants.SCORE_TEXT + currScore);
    }

    /**
     * This method updates and displays the current spells of player.
     */
    public void updateSpellNumbers() {
        magicalHexButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.HEX)));
        unstoppableSpellButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.UNSTOPPABLE)));
        expansionSpellButton.setText(String.valueOf(SpellHandler.getInstance().getSpellNumber(Constants.SpellNameConstants.EXPANSION)));
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}




