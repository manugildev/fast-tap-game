package gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import configuration.Configuration;
import gameworld.GameWorld;

/**
 * Created by ManuGil on 10/03/15.
 */
public class Background extends GameObject {
    public Background(GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture) {
        super(world, x, y, width, height, texture,
                world.parseColor(Configuration.COLOR_BACKGROUND_COLOR, 1f));
    }
}
