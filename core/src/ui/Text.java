package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import gameobjects.GameObject;
import gameworld.GameWorld;
import helpers.AssetLoader;

/**
 * Created by ManuGil on 14/03/15.
 */
public class Text extends GameObject {
    private final BitmapFont font;
    private final Color fontColor;
    private final float distance;
    private String text;
    private BitmapFont.HAlignment center;

    public Text(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture, Color color, String text, BitmapFont font, Color fontColor,
                float distance, BitmapFont.HAlignment center) {
        super(world, x, y, width, height, texture, color);
        this.font = font;
        this.text = text;
        this.fontColor = fontColor;
        this.distance = distance;
        this.center = center;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontshader) {
        if (getTexture() != AssetLoader.transparent) {
            super.render(batch, shapeRenderer);
        }
        batch.setShader(fontshader);
        font.setColor(fontColor);
        font.drawWrapped(batch, text, getRectangle().x + 40,
                getRectangle().y + getRectangle().height - distance, getRectangle().width - 80,
                center);
        font.setColor(Color.WHITE);
        batch.setShader(null);
    }

    public void setText(String text) {
        this.text = text;
    }



}
