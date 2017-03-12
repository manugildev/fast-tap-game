package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import buttons.ActionResolver;
import buttons.ButtonsGame;
import configuration.Configuration;
import gameobjects.Background;
import gameobjects.Board;
import helpers.AssetLoader;
import helpers.FlatColors;
import ui.CurrentColorBanner;
import ui.InfoBanner;
import ui.Timer;

/**
 * Created by ManuGil on 09/03/15.
 */

public class GameWorld {

    public final float w;
    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public GameWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    //VARIABLES
    private GameState gameState;
    private int score;

    //GAMEOBJECTS
    private Board board;
    private Background background, topWLayer;
    private CurrentColorBanner currentColorBanner;
    private InfoBanner pointsBanner, timeBanner;
    private Timer timer;

    public GameWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth,
                     float gameHeight, float worldWidth, float worldHeight) {

        this.gameWidth = gameWidth;
        this.w = gameHeight / 100;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;

        if(AssetLoader.getAds()){
            actionResolver.viewAd(false);
        }

        gameState = GameState.RUNNING;
        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square);
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square);
        topWLayer.fadeOut(.6f, 0f);
        setUpBoard();
        setUpCurrentColorBanner();
        setUpInfoBanner();
        Gdx.app.log("CurrentColorBannerWidth: ",
                currentColorBanner.getRectangle().getHeight() + "");
        timer = new Timer(this);
        startGame();
    }

    private void setUpBoard() {
        board = new Board(this, 40, gameHeight / 2 - (gameWidth / 2) + 40 - 10 - 50, gameWidth - 80,
                gameWidth - 70,
                AssetLoader.board, 5, 5);
    }

    public void update(float delta) {
        currentColorBanner.update(delta);
        board.update(delta);
        pointsBanner.update(delta);
        timeBanner.update(delta);
        topWLayer.update(delta);
        timer.update(delta);
        timeBanner.setText(timer.getTimeFormatted());
    }

    public void render(SpriteBatch batcher, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        background.render(batcher, shapeRenderer);
        board.render(batcher, shapeRenderer);
        currentColorBanner.render(batcher, shapeRenderer);
        pointsBanner.render(batcher, shapeRenderer, fontShader);
        timeBanner.render(batcher, shapeRenderer, fontShader);
        topWLayer.render(batcher, shapeRenderer);

        if (Configuration.DEBUG) {
            batcher.setShader(fontShader);
            AssetLoader.fontB
                    .drawWrapped(batcher, "MANUEL", 0, gameWidth / 2, gameWidth,
                            BitmapFont.HAlignment.CENTER);
            batcher.setShader(null);
        }
    }

    public void finishGame() {
        saveScoreLogic();
        topWLayer.fadeIn(0.6f, 0.4f);
        board.finishGame();
        AssetLoader.end.play();
        gameState = GameState.GAMEOVER;
        checkAchievements();

    }

    private void saveScoreLogic() {
        AssetLoader.addGamesPlayed();
        AssetLoader.addButtonsClicked(score);
        int gamesPlayed = AssetLoader.getGamesPlayed();

        // GAMES PLAYED ACHIEVEMENTS!
        actionResolver.submitScore(score);
        actionResolver.submitGamesPlayed(gamesPlayed);
        actionResolver.submitButtonsClicked(AssetLoader.getButtonsClicked());

        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
        }
        checkAchievements();
    }

    private void checkAchievements() {
        if (actionResolver.isSignedIn()) {
            if (score >= 50) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_P);
            if (score >= 100) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_P);
            if (score >= 200) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_P);
            if (score >= 300) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_300_P);
            if (score >= 500) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_500_P);
            if (score >= 1000)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_1000_P);

            int gamesPlayed = AssetLoader.getGamesPlayed();
            // GAMES PLAYED
            if (gamesPlayed >= 10)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_GP);
            if (gamesPlayed >= 25)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_GP);
            if (gamesPlayed >= 50)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_GP);
            if (gamesPlayed >= 100)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_GP);
            if (gamesPlayed >= 200)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_GP);

            int taps = AssetLoader.getButtonsClicked();
            // TAPS
            if (taps >= 50)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_T);
            if (taps >= 200)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_T);
            if (taps >= 500)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_500_T);
            if (taps >= 1000)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_1000_T);
        }
    }

    public void startGame() {
        score = 0;
        setUpBoard();
        setUpCurrentColorBanner();
        setUpInfoBanner();
        board.reset();
        topWLayer.fadeOut(0.6f, 0.1f);
        timer.start(Configuration.START_TIMER, 0, Configuration.START_TIMER, 1.5f);
        gameState = GameState.RUNNING;
    }

    private void setUpCurrentColorBanner() {
        currentColorBanner = new CurrentColorBanner(world, gameWidth / 2 - 75,
                world.gameHeight - (world.gameHeight - (board.getRectangle().getY() + board
                        .getRectangle().height)) + 60,
                150, 160, AssetLoader.currentColorBanner, FlatColors.BLACK);
    }

    private void setUpInfoBanner() {
        pointsBanner = new InfoBanner(world, 40,
                world.gameHeight - (world.gameHeight - (board.getRectangle().getY() + board
                        .getRectangle().height)) + 60, gameWidth / 2 - 75 - 80, 160,
                AssetLoader.timeBanner,
                FlatColors.BLACK, "Points");

        pointsBanner.effectX(-560, pointsBanner.getPosition().x, 0.6f, 0.2f);
        timeBanner = new InfoBanner(world, gameWidth / 2 + 75 + 40,
                world.gameHeight - (world.gameHeight - (board.getRectangle().getY() + board
                        .getRectangle().height)) + 60, gameWidth / 2 - 75 - 80, 160,
                AssetLoader.timeBanner,
                FlatColors.BLACK, "Time");
        timeBanner.effectX(world.gameWidth, timeBanner.getPosition().x, 0.6f, 0.2f);
    }


    public GameCam getCamera() {
        return camera;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int i) {
        score += i;
        pointsBanner.setText(score + "");
    }

    public Board getBoard() {
        return board;
    }

    public static Color parseColor(String hex, float alpha) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = alpha;
        return color;
    }

    public CurrentColorBanner getCurrentColorBanner() {
        return currentColorBanner;
    }


    public InfoBanner getTimeBanner() {
        return timeBanner;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public ButtonsGame getGame() {
        return game;
    }
}
