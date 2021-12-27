package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import javax.swing.*;
import java.awt.*;

/**
 * BackgroundHandler is Controller Design Pattern
 * to achieve MVVM Design on displaying background image.
 *
 * @author Kaan Turkmen
 */
public class BackgroundHandler {

    /**
     * Constructor for the BackgroundHandler.
     */
    public BackgroundHandler() {
    }

    /**
     * Creates a JPanel and drawing background image on it.
     *
     * @param path Path of the background image.
     * @return JPanel which has background image on it.
     */
    public JPanel getBackgroundedJPanel(String path) {
        Image background = Toolkit.getDefaultToolkit().getImage(NeedforSpearGame.findResourceFolder(path))
                .getScaledInstance(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT, Image.SCALE_SMOOTH);

        return new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }

    /**
     * Creates a background image and scaling it.
     *
     * @param path Path of the background image.
     * @return Image of the background.
     */
    public Image getBackgroundImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(NeedforSpearGame.findResourceFolder(path))
                .getScaledInstance(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT, Image.SCALE_SMOOTH);
    }
}
