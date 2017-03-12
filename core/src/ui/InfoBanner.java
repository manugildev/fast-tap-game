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
 * Created by ManuGil on 11/03/15.
 */
public class InfoBanner extends GameObject {

    private String bannerTitle = "Points";
    private String text = "0";

    public InfoBanner(GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture,
                      Color color, String bannerTitle) {
        super(world, x, y, width, height, texture, color);
        this.bannerTitle = bannerTitle;
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        super.render(batch, shapeRenderer);
        batch.setShader(fontShader);
        AssetLoader.fontXS
                .drawWrapped(batch, bannerTitle, getRectangle().x,
                        getRectangle().y + getRectangle().height-4, getRectangle().width,
                        BitmapFont.HAlignment.CENTER);
        AssetLoader.fontB
                .drawWrapped(batch, text, getRectangle().x,
                        getRectangle().y + getRectangle().height - 55, getRectangle().width,
                        BitmapFont.HAlignment.CENTER);
        batch.setShader(null);
    }

    public void setText(String text){
        this.text = text;
    }
}
