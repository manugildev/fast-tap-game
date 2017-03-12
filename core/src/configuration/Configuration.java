package configuration;

/**
 * Created by ManuGil on 09/03/15.
 */

public class Configuration {

    public static final String GAME_NAME = "Fast Tap!";
    public static final boolean DEBUG = false;
    public static final boolean SPLASHSCREEN = true;

    //ADMOB IDS
    public static final String AD_UNIT_ID_BANNER = "ca-app-pub-6147578034437241/4745179018";
    public static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6147578034437241/6221912212";
        public static float AD_FREQUENCY = .9f;

    //In App Purchases
    public static final String ENCODED_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Yqrjl7YEidktXOoxyYAivS1sKOlQeB2sTDuGZnDz0D8jKh7o9iFs/EsrLhS++4C1+Y6VWVZDqmMUuocRVVl1D78X2kBHNHZR8Au8wVWe5Zm50ZOeEsY+YTQpjMsJKrNCghScWaJPHShQiktlA6uC91W8z/54TG/atkIgai+fY5pvesqP7lLC0fONtlfLOMyjQnJvUkIQPaSBj2wImJHPAccPMW56olapIYspFe9JGPhiI1qYTXhr4Vz06VyyPcO34ZYbuyIkES5kAWmVA/PjAS+2oEEbB1I6AtmeJosWQHyfRZ4PJvC7bvs4RuPGwNHZ+mgQhUj084DAe2NPoXVJwIDAQAB";
    public static final String PRODUCT_ID = "removeads";

    //LEADERBOARDS
    public static final String LEADERBOARD_HIGHSCORE = "CgkIzf-DzsgaEAIQAA";
    public static final String LEADERBOARD_GAMESPLAYED = "CgkIzf-DzsgaEAIQAg";
    public static final String LEADERBOARD_BUTTONS_CLICKED = "CgkIzf-DzsgaEAIQAQ";

    //ACHIEVEMENTS IDS Points
    public static final String ACHIEVEMENT_50_P = "CgkIzf-DzsgaEAIQBA";
    public static final String ACHIEVEMENT_100_P = "CgkIzf-DzsgaEAIQBQ";
    public static final String ACHIEVEMENT_200_P = "CgkIzf-DzsgaEAIQBg";
    public static final String ACHIEVEMENT_300_P = "CgkIzf-DzsgaEAIQBw";
    public static final String ACHIEVEMENT_500_P = "CgkIzf-DzsgaEAIQCA";
    public static final String ACHIEVEMENT_1000_P = "CgkIzf-DzsgaEAIQCQ";
    //GAMES PLAYED
    public static final String ACHIEVEMENT_10_GP = "CgkIzf-DzsgaEAIQCg";
    public static final String ACHIEVEMENT_25_GP = "CgkIzf-DzsgaEAIQCw";
    public static final String ACHIEVEMENT_50_GP = "CgkIzf-DzsgaEAIQDA";
    public static final String ACHIEVEMENT_100_GP = "CgkIzf-DzsgaEAIQDQ";
    public static final String ACHIEVEMENT_200_GP = "CgkIzf-DzsgaEAIQDg";
    //TOTAL TAPS
    public static final String ACHIEVEMENT_50_T = "CgkIzf-DzsgaEAIQDw";
    public static final String ACHIEVEMENT_200_T = "CgkIzf-DzsgaEAIQEA";
    public static final String ACHIEVEMENT_500_T = "CgkIzf-DzsgaEAIQEQ";
    public static final String ACHIEVEMENT_1000_T = "CgkIzf-DzsgaEAIQEg";

    //COLORS
    //COLORS OF BUTTONS
    public static final String COLOR_BUTTON_1 = "#e74c3c";
    public static final String COLOR_BUTTON_2 = "#3498db";
    public static final String COLOR_BUTTON_3 = "#2ecc71";
    public static final String COLOR_BUTTON_4 = "#f1c40f";
    public static final String COLOR_BUTTON_5 = "#e67e22";

    //BOARD
    public static final String COLOR_BOARD = "#2c3e50";

    //MENU
    public static final String COLOR_PLAY_BUTTON = "#2ecc71";
    public static final String COLOR_LEADERBOARD_BUTTON = "#e74c3c";
    public static final String COLOR_SHARE_BUTTON = "#3498db";
    public static final String COLOR_ACHIEVEMENTS_BUTTON = "#f39c12";
    public static final String COLOR_ADS_BUTTON = "#9b59b6";

    public static final String COLOR_BACKGROUND_COLOR = "#ecf0f1";

    //GAMEPLAY VARIABLES
    public static final float HIGHEST_ADD_TIME = 2.2f;
    public static final float LOWEST_ADD_TIME = 1.6f;
    public static final float START_TIMER = 8;
    public static final int BUTTON_SIZE = 155;

    //TEXTs
    public static final String HIGHSCORE_TEXT = "High Score";
    public static final String GAMESPLAYED_TEXT = "Games Played";
    public static final String BUTTONSCLICKED_TEXT = "Total Taps";
    public static final String SCORE_TEXT = "Score";

    //Share Message
    public static final String SHARE_MESSAGE = "Have you tried " + GAME_NAME +
            "? Best Game EVER! Download here! ";
}
