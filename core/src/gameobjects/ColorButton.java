package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Configuration;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;

/**
 * Created by ManuGil on 09/03/15.
 */
public class ColorButton extends GameObject {

    private int type;
    private Color color;
    private Sprite backSprite;
    private TweenCallback cbClickCorrect, cbFadeInNew;
    private ButtonState buttonState;

    private enum ButtonState {
        IDLE, CHANGING
    }

    public ColorButton(final GameWorld world, float x, float y, float width, float height,
                       TextureRegion texture, Color color) {

        super(world, x, y, width, height, texture, color);
        type = MathUtils.random(0, 4);
        setColorOfButtons();
        float delay = MathUtils.random(.2f, .7f);
        getRectangle().set(x - 15, y - 15, width + 30, height + 30);

        /*float delay;
        switch (row) {
            case 0:
                delay = 0.5f;
                break;
            case 1:
                delay = 0.4f;
                break;
            case 2:
                delay = 0.3f;
                break;
            case 3:
                delay = 0.2f;
                break;
            case 4:
                delay = 0.1f;
                break;
            default:
                delay = 0;

        }*/
        //scale(1.1f, 0.5f, delay);

        backSprite = new Sprite(AssetLoader.dot);
        backSprite.setColor(FlatColors.DARK_BLACK);
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        backSprite.setSize(getSprite().getWidth(), getSprite().getHeight() - 10);
        backSprite.setScale(1.1f);
        backSprite.setAlpha(1);
        backSprite.setOriginCenter();
        buttonState = ButtonState.IDLE;


        //TWEEN CALLBACKS
        cbClickCorrect = new TweenCallback() {
            @Override
            public void onEvent(int tipo, BaseTween<?> source) {
                //Not get the same as before again
                int tip;
                do {
                    tip = MathUtils.random(0, 4);
                } while (tip == type);
                type = tip;

                setColorOfButtons();
                fadeInNew(.3f, .1f);
                world.getBoard().checkNumberOfCurrentColors();

            }
        };

        cbFadeInNew = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                buttonState = ButtonState.IDLE;
            }
        };
    }

    public void fadeInNew(float duration, float delay) {
        getSprite().setAlpha(0);
        Tween.to(getSprite(), SpriteAccessor.ALPHA, duration).target(1).delay(delay)
                .setCallback(cbFadeInNew).setCallbackTriggers(TweenCallback.COMPLETE)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    @Override
    public void update(float delta) {
        //backSprite.setAlpha(world.getBoard().getSprite().getColor().a);
        backSprite.setY(getSprite().getY());
        super.update(delta);
        getRectangle().setPosition(getPosition().x - 15, getPosition().y - 15);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        backSprite.draw(batch);
        if (isPressed) {
            getSprite().setRegion(AssetLoader.colorButton_pressed);
            getFlashSprite().setRegion(AssetLoader.colorButton_pressed);
        } else {
            getSprite().setRegion(AssetLoader.colorButton);
            getFlashSprite().setRegion(AssetLoader.colorButton);
        }
        super.render(batch, shapeRenderer);
    }

    public void renderNoButton(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //backSprite.draw(batch);
        super.render(batch, shapeRenderer);
    }

    @Override
    public boolean isTouchDown(int screenX, int screenY) {
        if (buttonState == ButtonState.IDLE && world.isRunning()) {
            return super.isTouchDown(screenX, screenY);
        } else return false;
    }

    @Override
    public boolean isTouchUp(int screenX, int screenY) {
        if (world.isRunning()) {
            if (super.isTouchUp(screenX, screenY)) {
                //flash(0.1f, 0.0f);
                return true;
            }
        }
        return false;
    }

    public void clickCorrect() {
        world.getBoard().checkNumberOfCurrentColors();
        buttonState = ButtonState.CHANGING;
        getSprite().setAlpha(1);
        Tween.to(getSprite(), SpriteAccessor.ALPHA, .3f).target(0).delay(0.05f)
                .setCallback(cbClickCorrect).setCallbackTriggers(
                TweenCallback.COMPLETE)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    private void setColorOfButtons() {
        switch (type) {
            case 0:
                color = world.parseColor(Configuration.COLOR_BUTTON_3,1f);
                break;
            case 1:
                color = world.parseColor(Configuration.COLOR_BUTTON_1,1f);
                break;
            case 2:
                color = world.parseColor(Configuration.COLOR_BUTTON_5,1f);
                break;
            case 3:
                color = world.parseColor(Configuration.COLOR_BUTTON_2,1f);
                break;
            case 4:
                color = world.parseColor(Configuration.COLOR_BUTTON_4,1f);
                break;
        }
        setColor(color);
    }

    public void setType(int typ) {
        type = typ;
        setColorOfButtons();
    }

    public void effectsIn() {
        fadeIn(0.6f, 0.5f);
        effectY(getPosition().y - world.gameHeight, getPosition().y, 0.48f, 0.0f);
    }

    public void effectsOut() {
        fadeOut(0.4f, 0.0f);
        //effectY(getPosition().y, getPosition().y - world.gameHeight, 0.5f, 0.26f);
    }

    public int getType() {
        return type;
    }

    public void setRandomType() {
        int tip;
        do {
            tip = MathUtils.random(0, 4);
        } while (tip == type);

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        Integer tempStr;
        for (int i = 0; i < world.getBoard().getColorButtons().size(); i++) {
            tempStr = world.getBoard().getColorButtons().get(i).getType();
            if (map.containsKey(tempStr)) {
                map.put(tempStr, map.get(tempStr) + 1);
            } else {
                map.put(tempStr, 1);
            }
        }

        //System.out.print(map.toString());
        int bigNumber = 0;
        int hmvalue = 0;
        for (int i = 0; i < map.size() + 1; i++) {
            if (map.get(i) != null) {
                if (i == 0) {
                    bigNumber = map.get(i);
                } else {
                    if (map.get(i) >= bigNumber) {
                        bigNumber = map.get(i);
                        hmvalue = i;
                    }
                }
            }
        }
        type = hmvalue;
        setColorOfButtons();
        world.getBoard().checkNumberOfCurrentColors();

        //System.out.print(bigNumber);
    }

    public Sprite getBackSprite(){
        return backSprite;
    }

}
