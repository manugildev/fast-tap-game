package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import configuration.Configuration;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import screens.MenuScreen;
import tweens.Value;

/**
 * Created by ManuGil on 09/03/15.
 */
public class Board extends GameObject {

    private int rows, columns;
    private ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();

    private float buttonSize = Configuration.BUTTON_SIZE;
    private Value time = new Value();
    private TweenCallback cbToMenuScreen;

    public Board(final GameWorld world, float x, float y, float width, float height,
                 TextureRegion texture, int rows, int columns) {
        super(world, x, y, width, height, texture, world.parseColor(Configuration.COLOR_BOARD,1f));
        this.rows = rows;
        this.columns = columns;
        setupBoard();
        //scale(0.95f, .5f, 0f);
        //fadeIn(0.7f, 0.0f);
        effectY(getPosition().y - world.gameHeight, y, 0.5f, 0f);
        //Gdx.app.log("Width and Height", height + " " + width);
        cbToMenuScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame()
                        .setScreen(new MenuScreen(world.getGame(), world.actionResolver,
                                world.getScore(), 0));
            }
        };

    }

    private void setupBoard() {
        float spaceBetweenCandys;
        spaceBetweenCandys = (getRectangle().width - (columns * buttonSize)) / (columns + 1);
        //buttonSize = (getRectangle().width-(spaceBetweenCandys*columns)-spaceBetweenCandys)/(columns);
        colorButtons.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                colorButtons.add(new ColorButton(world,
                        (j * buttonSize) + (spaceBetweenCandys * j) + getRectangle().x + (spaceBetweenCandys),
                        getRectangle().y + (i * buttonSize) + (spaceBetweenCandys * i) + (spaceBetweenCandys),
                        buttonSize, buttonSize + 10, AssetLoader.colorButton, Color.BLACK));

            }
        }

        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).effectsIn();
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).update(delta);
        }

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).render(batch, shapeRenderer);
        }
    }

    public void reset() {
        setupBoard();
    }

    //Just for Test Flash - Makes the board and fasttap flash!
    public void flash() {
        //flash(0.3f, 0f);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).flash(0.3f, 0f);
        }
        //world.getCurrentColorBanner().getCCIndicator().flash(0.3f,0f);
    }

    public void checkIfButtonsTouchDown(int screenX, int screenY) {
        if (world.isRunning()) {
            for (int i = 0; i < colorButtons.size(); i++) {
                if (colorButtons.get(i).isTouchDown(screenX, screenY)) {
                    touchUpAction(i);
                }
            }
        }
    }

    public void checkIfButtonsTouchUp(int screenX, int screenY) {
        for (int i = 0; i < colorButtons.size(); i++) {
            if (colorButtons.get(i).isTouchUp(screenX, screenY)) {
                //touchUpAction(i);
            }
        }
    }

    private void touchUpAction(int i) {
        if (world.isRunning()) {
            if (colorButtons.get(i).getType() == world.getCurrentColorBanner().getCCIndicator()
                    .getType()) {
                colorButtons.get(i).clickCorrect();
                world.addScore(1);
                AssetLoader.click.play();
            }
        }
    }

    public void checkNumberOfCurrentColors() {
        int currentColorNumber = 0;
        for (int i = 0; i < colorButtons.size(); i++) {
            if (colorButtons.get(i).getType() == world.getCurrentColorBanner().getCCIndicator()
                    .getType()) {
                currentColorNumber++;
            }
        }
        if (currentColorNumber == 0) {
            changeCurrentColor();
        }
        world.getTimeBanner().setText(currentColorNumber + "");
    }

    private void changeCurrentColor() {
        world.getCurrentColorBanner().getCCIndicator().flash(0.1f, 0f);
        world.getCurrentColorBanner().getCCIndicator().setRandomType();
        world.getTimer().addTime(
                MathUtils.random(Configuration.LOWEST_ADD_TIME, Configuration.HIGHEST_ADD_TIME));
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).flash(0.1f, 0f);
        }
        AssetLoader.success.play();
    }

    public ArrayList<ColorButton> getColorButtons() {
        return colorButtons;
    }

    public void finishGame() {
        time.setValue(0);
        Tween.to(time, -1, 0.6f).target(1).delay(.4f).setCallback(cbToMenuScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());

    }
}
