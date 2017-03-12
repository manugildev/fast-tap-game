package menuworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import buttons.ActionResolver;
import buttons.ButtonsGame;
import configuration.Configuration;
import gameobjects.Background;
import gameobjects.GameObject;
import gameworld.GameCam;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.Value;
import tweens.ValueAccessor;
import ui.FancyNumbers;
import ui.MenuButton;
import ui.Text;

/**
 * Created by ManuGil on 14/03/15.
 */
public class MenuWorld extends GameWorld {

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public MenuWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    private int score;
    private float timePlayed;

    //MENU OBJECTS
    private Background background, topWLayer;
    //Buttons
    private MenuButton playButton, leaderboardButton, achievementsButton, shareButton, adsButton;
    private ArrayList<MenuButton> menubuttons = new ArrayList<MenuButton>();
    private Text title, highscore, gamesplayed, buttonsclicked, highscoreNumber, gamesplayedNumber,
            buttonsclickedNumber, scoreT, scoreNumber;
    private FancyNumbers fancyHighScore, fancyGamesPlayed, fancyButtonsClicked, fancyScore;

    private Value second = new Value();
    TweenManager manager;
    TweenCallback cb;
    private GameObject backgroundBanner;
    private Sprite circleSprite;

    public MenuWorld(ButtonsGame game, final ActionResolver actionResolver, float gameWidth,
                     float gameHeight,
                     float worldWidth, float worldHeight, int points, float timePlayed) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight);
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;
        this.score = points;
        this.timePlayed = timePlayed;

        //REMOVE ADS IF BUYER
        if (AssetLoader.getAds()) {
            actionResolver.viewAd(false);
        }

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square);
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square);
        topWLayer.fadeOut(.6f, 0f);

        playButton = new MenuButton(this, gameWidth / 2 - 75 - (Configuration.BUTTON_SIZE * 2),
                gameHeight / 2 - 250 - Configuration.BUTTON_SIZE - 20,
                Configuration.BUTTON_SIZE,
                Configuration.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Configuration.COLOR_PLAY_BUTTON,1f), AssetLoader.playButtonUp, 0);
        leaderboardButton = new MenuButton(this, gameWidth / 2 - Configuration.BUTTON_SIZE - 25,
                gameHeight / 2 - 250 - Configuration.BUTTON_SIZE - 20, Configuration.BUTTON_SIZE,
                Configuration.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Configuration.COLOR_LEADERBOARD_BUTTON,1f), AssetLoader.rankButtonUp, 1);

        shareButton = new MenuButton(this, gameWidth / 2 + 25,
                gameHeight / 2 - 250 - Configuration.BUTTON_SIZE - 20, Configuration.BUTTON_SIZE,
                Configuration.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Configuration.COLOR_SHARE_BUTTON,1f), AssetLoader.shareButtonUp, 1);

        achievementsButton = new MenuButton(this, gameWidth / 2 + 75 + Configuration.BUTTON_SIZE,
                gameHeight / 2 - 250 - Configuration.BUTTON_SIZE - 20,
                Configuration.BUTTON_SIZE,
                Configuration.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Configuration.COLOR_ACHIEVEMENTS_BUTTON,1f), AssetLoader.achieveButtonUp, 1);

        adsButton = new MenuButton(this, gameWidth / 2 - (Configuration.BUTTON_SIZE / 2),
                gameHeight / 2 - 250 - Configuration.BUTTON_SIZE - 70 - Configuration.BUTTON_SIZE,
                Configuration.BUTTON_SIZE,
                Configuration.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Configuration.COLOR_ADS_BUTTON,1f), AssetLoader.adsUp, 1);

        menubuttons.add(playButton);
        menubuttons.add(leaderboardButton);
        menubuttons.add(shareButton);
        menubuttons.add(achievementsButton);
        menubuttons.add(adsButton);

        title = new Text(this, 40, gameHeight / 2 + 250, gameWidth - 80, 250,
                AssetLoader.titleBanner, FlatColors.DARK_BLACK, Configuration.GAME_NAME,
                AssetLoader.font, FlatColors.WHITE, 60, BitmapFont.HAlignment.CENTER);

        if (points != 0) {
            setUpTexts(-40);
        } else {
            setUpTexts(0);
        }


        fancyHighScore = new FancyNumbers();
        fancyHighScore.start(0, AssetLoader.getHighScore(), MathUtils.random(0.5f, 1f), 0.1f);

        fancyGamesPlayed = new FancyNumbers();
        fancyGamesPlayed.start(0, AssetLoader.getGamesPlayed(), MathUtils.random(0.5f, 1f), 0.1f);

        fancyButtonsClicked = new FancyNumbers();
        fancyButtonsClicked
                .start(0, AssetLoader.getButtonsClicked(), MathUtils.random(0.5f, 1f), 0.1f);

        fancyScore = new FancyNumbers();
        fancyScore.start(0, score, MathUtils.random(0.5f, 1f), 0.1f);

        Gdx.app.log("HighScoreBanner",
                title.getRectangle().width + " " + title.getRectangle().height);

        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if (Math.random() < (Configuration.AD_FREQUENCY)) {
                    actionResolver.showOrLoadInterstital();
                }
            }
        };

        if (!AssetLoader.getAds()) {
            actionResolver.viewAd(true);
            second.setValue(0);
            Tween.to(second, -1, .55f).target(1).setCallback(cb)
                    .setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
        }

        backgroundBanner = new GameObject(world, -100, gameHeight / 2 - (360 / 2) - 10,
                gameWidth + 200,
                360, AssetLoader.highscoreBanner, FlatColors.BLACK);

        circleSprite = new Sprite(AssetLoader.dot);
        circleSprite.setSize(gameWidth + 400, gameWidth + 400);
        circleSprite.setPosition(-200, gameHeight / 2 - ((gameWidth + 400) / 2));
        circleSprite.setColor(FlatColors.DARK_WHITE);
        circleSprite.setAlpha(0.2f);
    }

    private void setUpTexts(int offset) {

        scoreT = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.PURPLE, Configuration.SCORE_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 0, BitmapFont.HAlignment.LEFT);

        scoreNumber = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.WHITE, Configuration.HIGHSCORE_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 0, BitmapFont.HAlignment.RIGHT);

        highscore = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.PURPLE, Configuration.HIGHSCORE_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 40 - offset, BitmapFont.HAlignment.LEFT);

        highscoreNumber = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.WHITE, Configuration.HIGHSCORE_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 40 - offset, BitmapFont.HAlignment.RIGHT);

        gamesplayed = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.WHITE, Configuration.GAMESPLAYED_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 120 - offset, BitmapFont.HAlignment.LEFT);

        gamesplayedNumber = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.WHITE, Configuration.HIGHSCORE_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 120 - offset,
                BitmapFont.HAlignment.RIGHT);

        buttonsclicked = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.WHITE, Configuration.BUTTONSCLICKED_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 200 - offset, BitmapFont.HAlignment.LEFT);

        buttonsclickedNumber = new Text(this, 100, gameHeight / 2 - (320 / 2), gameWidth - 200, 320,
                AssetLoader.transparent, FlatColors.WHITE, Configuration.HIGHSCORE_TEXT,
                AssetLoader.fontS, FlatColors.WHITE, 200 - offset,
                BitmapFont.HAlignment.RIGHT);
    }

    public void update(float delta) {
        manager.update(delta);
        background.update(delta);
        for (int i = 0; i < getMenuButtons().size(); i++) {
            menubuttons.get(i).update(delta);
        }

        //FANCYNUMBERS
        fancyHighScore.update(delta);
        fancyGamesPlayed.update(delta);
        fancyButtonsClicked.update(delta);
        fancyScore.update(delta);

        //TEXTS
        title.update(delta);
        highscore.update(delta);
        highscoreNumber.update(delta);
        highscoreNumber.setText(fancyHighScore.getText());

        //GAMESPLAYED
        gamesplayed.update(delta);
        gamesplayedNumber.update(delta);
        gamesplayedNumber.setText(fancyGamesPlayed.getText());

        //ButtonsClicked
        buttonsclicked.update(delta);
        buttonsclickedNumber.update(delta);
        buttonsclickedNumber.setText(fancyButtonsClicked.getText());

        //ButtonsClicked
        scoreT.update(delta);
        scoreNumber.update(delta);
        scoreNumber.setText(fancyScore.getText());

        topWLayer.update(delta);
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        camera.render(batch, shapeRenderer);
        background.render(batch, shapeRenderer);

        for (int i = 0; i < getMenuButtons().size(); i++) {
            menubuttons.get(i).render(batch, shapeRenderer);
        }
        title.render(batch, shapeRenderer, fontShader);
        backgroundBanner.render(batch, shapeRenderer);

        highscore.render(batch, shapeRenderer, fontShader);
        highscoreNumber.render(batch, shapeRenderer, fontShader);

        gamesplayed.render(batch, shapeRenderer, fontShader);
        gamesplayedNumber.render(batch, shapeRenderer, fontShader);

        buttonsclicked.render(batch, shapeRenderer, fontShader);
        buttonsclickedNumber.render(batch, shapeRenderer, fontShader);

        if (score != 0) {
            scoreT.render(batch, shapeRenderer, fontShader);
            scoreNumber.render(batch, shapeRenderer, fontShader);
        }
        topWLayer.render(batch, shapeRenderer);
    }

    public GameCam getCamera() {
        return camera;
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menubuttons;
    }

    public void goToGameScreen() {
        playButton.toGameScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);

    }
}
