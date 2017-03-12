package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import buttons.ActionResolver;
import buttons.ButtonsGame;
import menuworld.InputHandlerMenu;
import menuworld.MenuRenderer;
import menuworld.MenuWorld;

/**
 * Created by ManuGil on 09/03/15.
 */
public class MenuScreen implements Screen {

    private MenuWorld world;
    private MenuRenderer renderer;
    private float runTime;
    public float sW = Gdx.graphics.getWidth();
    public float sH = Gdx.graphics.getHeight();
    public float gameWidth = 1080;
    public float gameHeight = sH / (sW / gameWidth);
    public float w = 1080 / 100;
    public float worldWidth = gameWidth * 1;
    public float worldHeight = gameHeight * 1;

    public MenuScreen(ButtonsGame game, ActionResolver actionResolver, int points, float timePlayed) {
        Gdx.app.log("GameScreen", "Attached");
        Gdx.app.log("GameWidth " + gameWidth, "GameHeight " + gameHeight);
        world = new MenuWorld(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, points, timePlayed);
        Gdx.input.setInputProcessor(new InputHandlerMenu(world, sW / gameWidth, sH
                / gameHeight));
        renderer = new MenuRenderer(world, gameWidth, gameHeight);

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resize");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {

    }
}
