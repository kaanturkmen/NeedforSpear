package tr.edu.ku.devnull.needforspear.Model.GameData;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.awt.*;
import java.io.File;
import java.io.InputStream;


/**
 * Constants interface is used to store all the necessary constants for the game.
 */
public interface Constants {

    interface ProportionConstants {
        Integer HEIGHT_OF_NOBLE_PHANTASM = 20;
        Integer WIDTH_OF_NOBLE_PHANTASM = Constants.UIConstants.INITIAL_SCREEN_WIDTH / 10;
        Integer X_CENTER_OF_NOBLE_PHANTASM = Constants.ProportionConstants.WIDTH_OF_NOBLE_PHANTASM / 2;
        Integer Y_CENTER_OF_NOBLE_PHANTASM = Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM / 2;
        Integer HEIGHT_OF_THE_OBSTACLE = 20;
        Integer RADIUS_OF_THE_SPHERE = 16;
        Double RATIO_OF_NOBLE_PHANTASM = 0.1;
        Double EXPLOSIVE_ORBIT_RADIUS = 1.5 * WIDTH_OF_NOBLE_PHANTASM;

        Integer RADIUS_OF_THE_BULLET = 6;
        Integer SPEED_OF_THE_BULLET = 2;

        Integer RADIUS_OF_EXPLOSIVE_OBSTACLE = 15;

        Integer SPELL_SIZE = 15;

    }

    interface UIConstants {

        String TITLE_SCREEN_BACKGROUND_IMAGE = "TitleScreen.png";
        String ACTIVATION_VIEW_BACKGROUND_IMAGE = "ActivateAccountBackground.png";
        String SEND_VERIFICATION_VIEW_BACKGROUND_IMAGE = "ForgotPasswordBackground.png";
        String VALIDATE_AND_CHANGE_PASSWORD_VIEW_BACKGROUND_IMAGE = "ValidateAndResetBackground.png";
        String MAIN_MENU_BACKGROUND_IMAGE = "MainMenuBackground.png";
        String GAME_BACKGROUND_IMAGE = "GameBackground.png";

        String USER_DIRECTORY_KEYWORD = "user.dir";
        String USER_DIRECTORY_TO_RESOURCE_FOLDER = File.separator + "src" + File.separator + "main" + File.separator
                + "java" + File.separator + "tr" + File.separator + "edu" + File.separator + "ku" + File.separator +
                "devnull" + File.separator + "needforspear" + File.separator + "Resources" + File.separator;

        String GAME_NAME = "Need for Spear!";
        String USERNAME_TEXT_FIELD_PLACEHOLDER = "Username";
        String EMAIL_TEXT_FIELD_PLACEHOLDER = "Email address";
        String PASSWORD_PASSWORD_FIELD_PLACEHOLDER = "Password";
        String LOGIN_BUTTON_TEXT = "Login";

        String REGISTER_BUTTON_PLACEHOLDER = "Register";
        String FORGOT_MY_PASSWORD_BUTTON_PLACEHOLDER = "Forgot My Password";
        String ACTIVATE_MY_ACCOUNT_BUTTON_PLACEHOLDER = "Activate My Account";
        String VERIFICATION_CODE_PLACEHOLDER = "Verification Code";
        String BACK_BUTTON_PLACEHOLDER = "Back";

        String NEW_GAME_TEXT = "New Game";
        String LOAD_GAME_TEXT = "Load Game";
        String HELP_SCREEN_TEXT = "Help!";
        String EXIT_GAME_TEXT = "Quit";

        String RESET_PASSWORD_PLACEHOLDER = "Reset Password";

        String NEW_PASSWORD_PLACEHOLDER = "New Password";

        String SWITCH_TO_RUNNING_MODE_TEXT = "Switch to running mode";
        String CREATE_A_NEW_MAP_TEXT = "Create a new map";
        String SAVE_A_MAP_TEXT = "Save game";
        String PAUSE_GAME_TEXT = "Pause game";
        String RESUME_GAME_TEXT = "Resume game";
        String SCORE_TEXT = "Score: ";
        String LIVES_TEXT = "Remaining lives: ";
        String NON_EXISTING_MAP_ERROR_TEXT = "There isn't a previously saved map";
        String ALERT_TEXT = "Alert";
        String MUTE_TEXT = "Mute";
        String UNMUTE_TEXT = "Unmute";
        String SIMPLE_OBSTACLE_NUM_TEXT = "Enter the number of simple obstacles (min 75, max 120):";
        String FIRM_OBSTACLE_NUM_TEXT = "Enter the number of firm obstacles (min 10, max 20): ";
        String EXPLOSIVE_OBSTACLE_NUM_TEXT = "Enter the number of explosive obstacles (min 5, max 10): ";
        String GIFT_OBSTACLE_NUM_TEXT = "Enter the number of gift obstacles (min 10, max 20): ";
        String OK_TEXT = "OK";
        String ENTER_VALID_NUMBER_TEXT = "Please enter a valid number";
        String ENTER_BETWEEN_GIVEN_VALUES_TEXT = "Please enter number between given values";

        String USER_EXISTS_TEXT = "This user already exists on the game, please try another credentials!";
        String INCORRECT_PASSWORD_TEXT = "Incorrect password.";
        String NEW_VERIFICATION_TEXT = "New verification code is sent to your email!";
        String VERIFY_BEFORE_LOGIN_TEXT = "Please verify your account before logging into your account!";

        String RED_COLOR_STRING = "RED";
        String BLUE_COLOR_STRING = "BLUE";
        String ORANGE_COLOR_STRING = "ORANGE";
        String GREEN_COLOR_STRING = "GREEN";

        String DARK_CYAN_COLOR_STRING = "DARKRED";
        String DARK_YELLOW_COLOR_STRING = "DARKBLUE";
        String PINK_COLOR_STRING = "DARKMAGENTA";
        String DARK_GREEN_COLOR_STRING = "DARKGREEN";

        String WEBSITE_TO_BE_PINGED = "http://www.google.com";

        Integer PADDING_BETWEEN_TEXT_FIELDS = 50;
        Integer PADDING_MAIN_MENU = 50;

        Integer INITIAL_SCREEN_WIDTH = 1280;
        Integer INITIAL_SCREEN_HEIGHT = 720;

        Integer BACK_BUTTON_X_COORDINATE = 70;
        Integer BACK_BUTTON_Y_COORDINATE = 600;

        Integer OBSTACLE_TXT_X = 50;
        Integer OBSTACLE_TXT_Y = 50;
        Integer OBSTACLE_TXT_X_PADDING = 50;
        Integer OBSTACLE_TXT_Y_PADDING = 30;
        Integer OBSTACLE_TXT_WIDTH = 400;
        Integer OBSTACLE_TXT_HEIGHT = 20;
        Integer OBSTACLE_NUM_CHECK_FRAME_SIZE = 500;
        Integer FLOWLAYOUT_HGAP = 30;
        Integer FLOWLAYOUT_VGAP = 10;
        Integer OBSTACLE_VGAP = 20;
        Integer OBSTACLE_HGAP = 30;
        Integer INIT_LIVES = 3;
        Integer OVERLAY_PANEL_HEIGHT = 100;
        Integer OBSTACLE_DISTANCE_FROM_PHANTASM = 150;

        Integer INIT_SCORE = 0;

        Location LOGIN_VIEW_COMPONENT_LOCATION = new Location(943.0, 186.0);
        Size MENU_AND_AUTH_VIEW_COMPONENT_SIZE = new Size(192, 50);

        Location AUTH_VIEW_EXCEPT_LOGIN_LOCATION = new Location(550.0, 80.0);

        Location MAIN_MENU_VIEW_LOCATION = new Location(543.0, 361.0);

        Color OVERLAY_BACKGROUND_COLOR = new Color(0.21f, 0.22f, 0.28f);
    }

    interface ObstacleNameConstants {
        String SIMPLE = "SimpleObstacle";
        String FIRM = "FirmObstacle";
        String EXP = "ExplosiveObstacle";
        String GIFT = "GiftObstacle";
    }

    interface ObstacleNumberConstants {
        Integer MIN_SIMPLE_OBSTACLE_NUM = 75;
        Integer MIN_FIRM_OBSTACLE_NUM = 10;
        Integer MIN_EXPLOSIVE_OBSTACLE_NUM = 5;
        Integer MIN_GIFT_OBSTACLE_NUM = 10;

        Integer MAX_SIMPLE_OBSTACLE_NUM = 120;
        Integer MAX_FIRM_OBSTACLE_NUM = 20;
        Integer MAX_EXPLOSIVE_OBSTACLE_NUM = 10;
        Integer MAX_GIFT_OBSTACLE_NUM = 20;
    }

    interface ArrayConstants {
        String[] OBSTACLE_NAMES_ARR = {Constants.ObstacleNameConstants.SIMPLE, Constants.ObstacleNameConstants.FIRM,
                Constants.ObstacleNameConstants.EXP, Constants.ObstacleNameConstants.GIFT};
    }
    interface SpellNameConstants {
        String CHANCE = "ChanceGivingSpell";
        String EXPANSION = "ExpansionSpell";
        String HEX = "MagicalHexSpell";
        String UNSTOPPABLE = "UnstoppableSpell";
    }
}