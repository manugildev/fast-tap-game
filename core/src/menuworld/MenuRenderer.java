package menuworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import configuration.Configuration;
import gameworld.GameCam;
import helpers.AssetLoader;


public class MenuRenderer {

    private final ShapeRenderer shapeRenderer;
    BitmapFont font = new BitmapFont();
    private MenuWorld world;
    private ShaderProgram fontShader;
    //GAME OBJECTS
    //private Hero hero;
    private Sprite backSprite;
    private GameCam camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float angle;

    public MenuRenderer(MenuWorld world, float gameWidth, float gameHeight) {
        this.world = world;
        sprite = new Sprite(AssetLoader.square);
        sprite.setPosition(0, 0);
        sprite.setSize(world.worldWidth, world.worldHeight);
        camera = world.getCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        initObjects();
        initFont();
    }

    private void initObjects() {
    }

    private void initFont() {
        fontShader = new ShaderProgram(Gdx.files.internal("font.vert"),
                Gdx.files.internal("font.frag"));
        if (!fontShader.isCompiled()) {
            Gdx.app.error("fontShader",
                    "compilation failed:\n" + fontShader.getLog());
        }
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        batch.begin();

        camera.render(batch, shapeRenderer);
        world.render(batch, shapeRenderer, fontShader);
        batch.end();

        //REMOVE THIS OUTSIDE DEBUGGING
        if (Configuration.DEBUG) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.end();
        }

    }

    private boolean cameraInsideWorld() {
        Gdx.app.log("CameraPos", camera.getCamera().position.toString());
        return false;
    }

}
