package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;

import java.net.URL;
import java.net.URLConnection;

/**
 * InternetHandler is an Controller Design Pattern for the handling internet connection.
 *
 * @author Kaan Turkmen
 */
public class InternetHandler {
    private final String websiteURL;

    /**
     * Constructor of the InternetHandler.
     */
    public InternetHandler() {
        this.websiteURL = Constants.UIConstants.WEBSITE_TO_BE_PINGED;
    }

    /**
     * Checks if the player has an active internet connection.
     *
     * @return Boolean indicating if user has an internet connection.
     */
    public boolean checkInternetConnection() {
        try {
            URL url = new URL(websiteURL);
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            System.err.println(Constants.UIConstants.INTERNET_CONNECTION_ERROR_MESSAGE);
            return false;
        }
    }
}
