package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import gameobjects.ColorButton;
import gameworld.GameWorld;
import helpers.FlatColors;
import screens.GameScreen;
import tweens.Value;

/**
 * Created by ManuGil on 14/03/15.
 */
public class MenuButton extends ColorButton {

    private Sprite icon;
    private Color color;
    private Value time = new Value();
    private TweenCallback cbGameScreen;

    public MenuButton(final GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture,
                      Color color, TextureRegion buttonIcon, int type) {
        super(world, x, y, width, height, texture, color);

        icon = new Sprite(buttonIcon);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();

        getBackSprite().setColor(FlatColors.DARK_WHITE);
        this.color = color;
        type = type;

        cbGameScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new GameScreen(world.getGame(), world.actionResolver));
            }
        };
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        setColor(this.color);
        super.render(batch, shapeRenderer);
        icon.draw(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (isPressed) {
            icon.setPosition(getPosition().x, getPosition().y - 3);
        } else {
            icon.setPosition(getPosition().x, getPosition().y + 3);
        }
    }

    @Override
    public boolean isTouchUp(int screenX, int screenY) {

        return super.isTouchUp(screenX, screenY);
    }

    public void toGameScreen(float duration, float delay) {
        time.setValue(0);
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbGameScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }


}
