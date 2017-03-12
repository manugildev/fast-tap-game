package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import gameobjects.ColorButton;
import gameobjects.GameObject;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;

/**
 * Created by ManuGil on 10/03/15.
 */
public class CurrentColorBanner extends GameObject {

    private ColorButton cCIndicator;

    public CurrentColorBanner(GameWorld world, float x, float y, float width, float height,
                              TextureRegion texture,
                              Color color) {
        super(world, x, y, width, height, texture, color);
        cCIndicator = new ColorButton(world, x + (width / 2) - 50, y + 25, 100, 115,
                AssetLoader.currentColorIndicator,
                FlatColors.GREEN);
        effectY(world.gameHeight, y, 0.6f, 0.2f);
        cCIndicator.effectY(world.gameHeight + 25, y + 25, 0.6f, 0.2f);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
        cCIndicator.renderNoButton(batch, shapeRenderer);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        cCIndicator.update(delta);
    }

    public void setType(int type) {
        flash(.1f, .1f);
        cCIndicator.setType(type);
    }

    public ColorButton getCCIndicator() {
        return cCIndicator;
    }
}
