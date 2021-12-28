package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;

import java.net.URL;
import java.net.URLConnection;

public class InternetHandler {
    private final String websiteURL;

    public InternetHandler(String websiteURL) {
        this.websiteURL = websiteURL;
    }

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
            System.err.println("You do not have an internet connection! This game requires an internet connection to play!");
            return false;
        }
    }
}
