package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import configuration.Configuration;

/**
 * Created by ManuGil on 09/03/15.
 */
public class AssetLoader {

    public static Texture logoTexture, dotT, colorButtonT, boardT, colorButton_pressedT,
            currentColorBannerT, currentColorIndicatorT, timeBannerT, buttonsT, highscoreBannerT,
            titleBannerT;
    public static TextureRegion logo, square, dot, colorButton, board, colorButton_pressed, highscoreBanner,
            currentColorBanner, currentColorIndicator, timeBanner, transparent, titleBanner;

    //BUTTONS
    public static TextureRegion playButtonUp, rankButtonUp, shareButtonUp, achieveButtonUp,
            rateButtonUp, pauseButton, adsUp;

    public static BitmapFont font, fontS, fontXS, fontB;
    private static Preferences prefs;

    public static Sound click, success, end;

    public static void load() {
        //LOGO TEXTURE "logo.png"
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, logoTexture.getWidth(),
                logoTexture.getHeight());

        square = new TextureRegion(new Texture(Gdx.files.internal("square.png")), 0, 0, 10, 10);

        dotT = new Texture(Gdx.files.internal("dot.png"));
        dotT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        dot = new TextureRegion(dotT, 0, 0, dotT.getWidth(), dotT.getHeight());

        transparent = new TextureRegion(new Texture(Gdx.files.internal("transparent.png")), 0, 0,
                10, 10);

        boardT = new Texture(Gdx.files.internal("board.png"));
        boardT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        board = new TextureRegion(boardT, 0, 0, boardT.getWidth(),
                boardT.getHeight());

        colorButtonT = new Texture(Gdx.files.internal("colorButton.png"));
        colorButtonT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        colorButton = new TextureRegion(colorButtonT, 0, 0, colorButtonT.getWidth(),
                colorButtonT.getHeight());

        colorButton_pressedT = new Texture(Gdx.files.internal("colorButton_pressed.png"));
        colorButton_pressedT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        colorButton_pressed = new TextureRegion(colorButton_pressedT, 0, 0,
                colorButton_pressedT.getWidth(),
                colorButton_pressedT.getHeight());

        currentColorBannerT = new Texture(Gdx.files.internal("currentColorBanner.png"));
        currentColorBannerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        currentColorBanner = new TextureRegion(currentColorBannerT, 0, 0,
                currentColorBannerT.getWidth(),
                currentColorBannerT.getHeight());

        currentColorIndicatorT = new Texture(Gdx.files.internal("currentColorIndicator.png"));
        currentColorIndicatorT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        currentColorIndicator = new TextureRegion(currentColorIndicatorT, 0, 0,
                currentColorIndicatorT.getWidth(),
                currentColorIndicatorT.getHeight());

        timeBannerT = new Texture(Gdx.files.internal("timeBanner.png"));
        timeBannerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        timeBanner = new TextureRegion(timeBannerT, 0, 0,
                timeBannerT.getWidth(),
                timeBannerT.getHeight());

        highscoreBannerT = new Texture(Gdx.files.internal("highscoreBanner.png"));
        highscoreBannerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        highscoreBanner = new TextureRegion(highscoreBannerT, 0, 0,
                highscoreBannerT.getWidth(),
                highscoreBannerT.getHeight());

        titleBannerT = new Texture(Gdx.files.internal("titleBanner.png"));
        titleBannerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        titleBanner = new TextureRegion(titleBannerT, 0, 0,
                titleBannerT.getWidth(),
                titleBannerT.getHeight());


        buttonsT = new Texture(Gdx.files.internal("buttons.png"));
        buttonsT.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //CROP BUTTONS
        playButtonUp = new TextureRegion(buttonsT, 0, 0, 240, 240);
        rankButtonUp = new TextureRegion(buttonsT, 240, 0, 240, 240);
        shareButtonUp = new TextureRegion(buttonsT, 720, 0, 240, 240);
        achieveButtonUp = new TextureRegion(buttonsT, 960, 0, 240, 240);
        adsUp = new TextureRegion(buttonsT, 1200, 0, 240, 240);
        rateButtonUp = new TextureRegion(buttonsT, 480, 0, 240, 240);

        //LOADING FONT
        Texture tfont = new Texture(Gdx.files.internal("sans.png"), true);
        tfont.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);

        font = new BitmapFont(Gdx.files.internal("sans.fnt"),
                new TextureRegion(tfont), true);
        font.setScale(1.9f, -1.9f);
        font.setColor(FlatColors.WHITE);

        fontB = new BitmapFont(Gdx.files.internal("sans.fnt"),
                new TextureRegion(tfont), true);
        fontB.setScale(1.4f, -1.4f);
        fontB.setColor(FlatColors.WHITE);

        fontS = new BitmapFont(Gdx.files.internal("sans.fnt"),
                new TextureRegion(tfont), true);
        fontS.setScale(1.2f, -1.2f);
        fontS.setColor(FlatColors.WHITE);

        fontXS = new BitmapFont(Gdx.files.internal("sans.fnt"),
                new TextureRegion(tfont), true);
        fontXS.setScale(0.9f, -0.9f);
        fontXS.setColor(FlatColors.WHITE);

        //PREFERENCES - SAVE DATA IN FILE
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

        if (!prefs.contains("games")) {
            prefs.putInteger("games", 0);
        }

        click = Gdx.audio.newSound(Gdx.files.internal("blip_click.wav"));
        success = Gdx.audio.newSound(Gdx.files.internal("blip_success.wav"));
        end = Gdx.audio.newSound(Gdx.files.internal("blip_end.wav"));


    }

    public static void dispose() {
        font.dispose();
        fontS.dispose();
        fontXS.dispose();
        dotT.dispose();
        logoTexture.dispose();
        click.dispose();
        end.dispose();
        success.dispose();

    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static void setButtonsClicked(int val) {
        prefs.putInteger("buttonsClicked", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }


    public static int getButtonsClicked() {
        return prefs.getInteger("buttonsClicked");
    }

    public static void addGamesPlayed() {
        prefs.putInteger("games", prefs.getInteger("games") + 1);
        prefs.flush();
    }

    public static void addButtonsClicked(int val) {
        prefs.putInteger("buttonsClicked", prefs.getInteger("buttonsClicked") + val);
        prefs.flush();
    }

    public static int getGamesPlayed() {
        return prefs.getInteger("games");
    }

    public static void setAds(boolean removeAdsVersion) {
        prefs.putBoolean("ads", removeAdsVersion);
        prefs.flush();
    }

    public static boolean getAds() {
        return prefs.getBoolean("ads", false);
    }
}
