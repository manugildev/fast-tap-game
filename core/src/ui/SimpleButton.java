package ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.Value;
import tweens.ValueAccessor;

public class SimpleButton {

    private float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;
    private Sprite sprite;

    private GameWorld world;
    public boolean isPressed = false;
    private TweenManager manager;
    private String text;

    public SimpleButton(final GameWorld world, final float x, float y, float width, float height,
                        TextureRegion buttonUp, TextureRegion buttonDown, String color,
                        String text) {

        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.text = text;
        bounds = new Rectangle(x, y, width, height);
        sprite = new Sprite(buttonUp);
        sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        sprite.setColor(world.parseColor(color, 1f));
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();


    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher, ShaderProgram fontShader) {
        if (isPressed) {
            sprite.setAlpha(.5f);
            sprite.draw(batcher);
        } else {
            sprite.setAlpha(1f);
            sprite.draw(batcher);
        }
        batcher.setShader(fontShader);
        AssetLoader.fontS.drawWrapped(batcher, text, x, y, width, BitmapFont.HAlignment.CENTER);

        batcher.setShader(null);
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }

    public void update(float delta) {
        manager.update(delta);
    }
}