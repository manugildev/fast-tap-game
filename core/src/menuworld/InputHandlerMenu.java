package menuworld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import configuration.Configuration;
import helpers.AssetLoader;
import ui.MenuButton;

/**
 * Created by ManuGil on 14/03/15.
 */
public class InputHandlerMenu implements InputProcessor {
    private final MenuWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;

    public InputHandlerMenu(MenuWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenuButtons();
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        for (int i = 0; i < menuButtons.size(); i++) {
            if (menuButtons.get(i).isTouchDown(screenX, screenY)) {
                AssetLoader.click.play();
            }

        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (rectangle.contains(screenX, screenY)) {
        }

        if (menuButtons.get(0).isTouchUp(screenX, screenY)) {
            world.goToGameScreen();
        } else if (menuButtons.get(1).isTouchUp(screenX, screenY)) {
            world.actionResolver.showScores();
        } else if (menuButtons.get(2).isTouchUp(screenX, screenY)) {
            world.actionResolver.shareGame(Configuration.SHARE_MESSAGE);
        } else if (menuButtons.get(3).isTouchUp(screenX, screenY)) {
            world.actionResolver.showAchievement();
        } else if (menuButtons.get(4).isTouchUp(screenX, screenY)) {
            world.actionResolver.IAPClick();
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (world.gameHeight - screenY / scaleFactorY);
    }
}
