package tr.edu.ku.devnull.needforspear.View.PlayViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MainMenuHandler;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import javax.swing.*;

/**
 * MainMenuView lets user to select from the set of options
 * to play the game.
 *
 * @author Kaan Turkmen
 */
public class MainMenuView {
    private static JButton newGameButton, loadGameButton, helpScreenButton, exitGameButton;
    private static boolean gameViewOpened = false;

    /**
     * A method for starting creation of the MainMenuView.java
     */
    public static void createView() {
        createUIElements();
        determineUIElementsSizes();
        createActionListeners();
        obtainVisibility();
    }

    /**
     * A method for assigning components to the class variables.
     */
    private static void createUIElements() {
        newGameButton = new JButton(Constants.UIConstants.NEW_GAME_TEXT);
        loadGameButton = new JButton(Constants.UIConstants.LOAD_GAME_TEXT);
        helpScreenButton = new JButton(Constants.UIConstants.HELP_SCREEN_TEXT);
        exitGameButton = new JButton(Constants.UIConstants.EXIT_GAME_TEXT);
    }

    /**
     * A method for determining the sizes of the elements.
     */
    private static void determineUIElementsSizes() {
        int x_coordinates_loc = (int) Constants.UIConstants.MAIN_MENU_VIEW_LOCATION.getXCoordinates().doubleValue();
        int y_coordinates_loc = (int) Constants.UIConstants.MAIN_MENU_VIEW_LOCATION.getYCoordinates().doubleValue();
        newGameButton.setBounds(x_coordinates_loc, y_coordinates_loc + Constants.UIConstants.PADDING_MAIN_MENU, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
        loadGameButton.setBounds(x_coordinates_loc, y_coordinates_loc + 2 * Constants.UIConstants.PADDING_MAIN_MENU, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
        helpScreenButton.setBounds(x_coordinates_loc, y_coordinates_loc + 3 * Constants.UIConstants.PADDING_MAIN_MENU, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
        exitGameButton.setBounds(x_coordinates_loc, y_coordinates_loc + 4 * Constants.UIConstants.PADDING_MAIN_MENU, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
    }

    /**
     * A method for creating listeners for the buttons.
     */
    private static void createActionListeners() {
        newGameButton.addActionListener(e -> {
            NoblePhantasm npa = NoblePhantasm.getInstance();
            npa.resetLocation();
            NeedforSpearGame.getMainFrame().getContentPane().removeAll();
            NeedforSpearGame.getMainFrame().repaint();
            NeedforSpearGame.startGameView();
        });

        loadGameButton.addActionListener(e -> {
            GamePanel.setIsGameLoaded(false);
            MainMenuHandler.getInstance().loadGame(NeedforSpearGame.getPlayer());
        });

        helpScreenButton.addActionListener(e -> System.out.println("Opened up help screen!"));

        exitGameButton.addActionListener(e -> {
            String[] options = new String[] {"Yes", "No"};
            int response = JOptionPane.showOptionDialog(NeedforSpearGame.getMainFrame(), "Are you sure?", "Exit",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            if(response == 0){
                System.exit(0);
            }
        });
    }

    /**
     * A method for creating visibility to the components.
     */
    private static void obtainVisibility() {
        NeedforSpearGame.getMainFrame().setContentPane(new BackgroundHandler().getBackgroundedJPanel(Constants.UIConstants.MAIN_MENU_BACKGROUND_IMAGE));
        NeedforSpearGame.getMainFrame().getContentPane().add(newGameButton);
        NeedforSpearGame.getMainFrame().getContentPane().add(loadGameButton);
        NeedforSpearGame.getMainFrame().getContentPane().add(helpScreenButton);
        NeedforSpearGame.getMainFrame().getContentPane().add(exitGameButton);
        NeedforSpearGame.getMainFrame().setLayout(null);
        NeedforSpearGame.getMainFrame().setVisible(true);
    }

    public static boolean isGameViewOpened() {
        return gameViewOpened;
    }

    public static void setGameViewOpened(boolean gameViewOpened) {
        MainMenuView.gameViewOpened = gameViewOpened;
    }
}

