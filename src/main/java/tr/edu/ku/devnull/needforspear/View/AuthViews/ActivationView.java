package tr.edu.ku.devnull.needforspear.View.AuthViews;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.UIModels.FocusableJTextField;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.AuthHandler.LoginHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;

import javax.swing.*;

/**
 * ActivationView lets user to activate their account
 * to play the game.
 *
 * @author Kaan Turkmen
 */
public class ActivationView {
    private static FocusableJTextField emailField, codeField;
    private static JButton activateButton, backButton;

    /**
     * A method for starting creation of the ActivationView.java
     */
    public static void createView() {
        createUIElements();
        determineUIElementsSizes();
        createActionListenerForLoginButton();
        obtainVisibility();
        NeedforSpearGame.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * A method for assigning components to the class variables.
     */
    private static void createUIElements() {
        NeedforSpearGame.getMainFrame().setTitle(Constants.UIConstants.GAME_NAME);
        emailField = new FocusableJTextField(Constants.UIConstants.EMAIL_TEXT_FIELD_PLACEHOLDER);
        codeField = new FocusableJTextField(Constants.UIConstants.VERIFICATION_CODE_PLACEHOLDER);
        activateButton = new JButton(Constants.UIConstants.ACTIVATE_MY_ACCOUNT_BUTTON_PLACEHOLDER);
        backButton = new JButton(Constants.UIConstants.BACK_BUTTON_PLACEHOLDER);
    }

    /**
     * A method for determining the sizes of the elements.
     */
    private static void determineUIElementsSizes() {
        int x_coordinates_loc = (int) Constants.UIConstants.AUTH_VIEW_EXCEPT_LOGIN_LOCATION.getXCoordinates().doubleValue();
        int y_coordinates_loc = (int) Constants.UIConstants.AUTH_VIEW_EXCEPT_LOGIN_LOCATION.getYCoordinates().doubleValue();
        emailField.setBounds(x_coordinates_loc, y_coordinates_loc + 2 * Constants.UIConstants.PADDING_BETWEEN_TEXT_FIELDS, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
        codeField.setBounds(x_coordinates_loc, y_coordinates_loc + 3 * Constants.UIConstants.PADDING_BETWEEN_TEXT_FIELDS, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
        activateButton.setBounds(x_coordinates_loc, y_coordinates_loc + 4 * Constants.UIConstants.PADDING_BETWEEN_TEXT_FIELDS, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
        backButton.setBounds(Constants.UIConstants.BACK_BUTTON_X_COORDINATE, Constants.UIConstants.BACK_BUTTON_Y_COORDINATE, Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getWidth(), Constants.UIConstants.MENU_AND_AUTH_VIEW_COMPONENT_SIZE.getLength());
    }

    /**
     * A method for creating listeners for the buttons.
     */
    private static void createActionListenerForLoginButton() {
        activateButton.addActionListener(e -> {
            LoginHandler.getInstance().confirmUser(emailField.getText(), codeField.getText());
            NeedforSpearGame.getMainFrame().getContentPane().removeAll();
            NeedforSpearGame.getMainFrame().repaint();
            NeedforSpearGame.startLoginView();
        });

        backButton.addActionListener(e -> {
            NeedforSpearGame.getMainFrame().getContentPane().removeAll();
            NeedforSpearGame.getMainFrame().repaint();
            NeedforSpearGame.startLoginView();
        });
    }

    /**
     * A method for creating visibility to the components.
     */
    private static void obtainVisibility() {
        NeedforSpearGame.getMainFrame().setContentPane(new BackgroundHandler().getBackgroundedJPanel(Constants.UIConstants.ACTIVATION_VIEW_BACKGROUND_IMAGE));
        NeedforSpearGame.getMainFrame().add(emailField);
        NeedforSpearGame.getMainFrame().add(codeField);
        NeedforSpearGame.getMainFrame().add(activateButton);
        NeedforSpearGame.getMainFrame().add(backButton);
        NeedforSpearGame.getMainFrame().setLayout(null);
        NeedforSpearGame.getMainFrame().setVisible(true);
    }
}
