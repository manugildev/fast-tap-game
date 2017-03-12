package helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import configuration.Configuration;
import gameworld.GameWorld;

/**
 * Created by ManuGil on 09/03/15.
 */
public class InputHandler implements InputProcessor {

    private GameWorld world;
    private float scaleFactorX;
    private float scaleFactorY;
    private Rectangle rectangle;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.R) {
            world.startGame();
        } else if (keycode == Input.Keys.F) {
            world.getBoard().flash();
        }
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
        world.getBoard().checkIfButtonsTouchDown(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        world.getBoard().checkIfButtonsTouchUp(screenX, screenY);
        if (rectangle.contains(screenX, screenY)) {
            if (Configuration.DEBUG) world.startGame();
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
