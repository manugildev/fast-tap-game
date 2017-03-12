package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import configuration.Configuration;
import gameworld.GameWorld;
import helpers.FlatColors;
import tweens.SpriteAccessor;
import tweens.VectorAccessor;

/**
 * Created by ManuGil on 10/03/15.
 */
public class GameObject {
    public GameWorld world;
    private float x, y;
    private float width, height;
    private Rectangle rectangle;
    private TextureRegion texture;

    private Vector2 position, velocity, acceleration;
    private Sprite sprite, flashSprite;
    private Color color;
    public boolean isPressed = false;

    private TweenManager manager;

    public GameObject(GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture, Color color) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.rectangle = new Rectangle(x, y, width, height);

        position = new Vector2(x, y);
        velocity = new Vector2();
        acceleration = new Vector2();

        sprite = new Sprite(texture);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(width, height);
        this.color = color;
        sprite.setColor(color);

        flashSprite = new Sprite(texture);
        flashSprite.setPosition(position.x, position.y);
        flashSprite.setSize(width, height);
        flashSprite.setAlpha(0);

        //TWEEN STUFF
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Vector2.class, new VectorAccessor());
        manager = new TweenManager();

    }

    public void update(float delta) {
        manager.update(delta);
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));
        rectangle.setPosition(position);

        sprite.setPosition(position.x, position.y);
        sprite.setOriginCenter();

        if (flashSprite.getColor().a != 0) {
            flashSprite.setPosition(position.x, position.y);
            flashSprite.setOriginCenter();
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        sprite.draw(batch);

        if (flashSprite.getColor().a != 0) {
            flashSprite.draw(batch);
        }

        if (Configuration.DEBUG) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(FlatColors.DARK_GREEN);
            shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            shapeRenderer.end();
            batch.begin();
        }
    }


    //CLICK
    public boolean isTouchDown(int screenX, int screenY) {
        if (rectangle.contains(screenX, screenY)) {
            //!Gdx.app.log("TouchedDown", screenX + " " + screenY);
            isPressed = true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (rectangle.contains(screenX, screenY) && isPressed) {
            //Gdx.app.log("TouchedUp", screenX + " " + screenY);
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }


    //EFFECTS
    public void fadeIn(float duration, float delay) {
        sprite.setAlpha(0);
        Tween.to(getSprite(), SpriteAccessor.ALPHA, duration).target(1).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

    public void fadeOut(float duration, float delay) {
        sprite.setAlpha(1);
        Tween.to(getSprite(), SpriteAccessor.ALPHA, duration).target(0).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

    public void scale(float from, float duration, float delay) {
        sprite.setScale(from);
        Tween.to(getSprite(), SpriteAccessor.SCALE, duration).target(1).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

    public void flash(float duration, float delay) {
        flashSprite.setAlpha(1);
        Tween.to(flashSprite, SpriteAccessor.ALPHA, duration).target(0).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

    public void effectY(float from, float to, float duration, float delay) {
        position.y = from;
        Tween.to(position, VectorAccessor.VERTICAL, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

    public void effectX(float from, float to, float duration, float delay) {
        position.x = from;
        Tween.to(position, VectorAccessor.HORIZONTAL, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }


    public Vector2 getPosition() {
        return position.cpy();
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Sprite getFlashSprite() {
        return flashSprite;
    }


    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setColor(Color color) {
        this.color = color;
        sprite.setColor(color);
    }

    public TweenManager getManager() {
        return manager;
    }

    public TextureRegion getTexture(){
        return texture;
    }


}
