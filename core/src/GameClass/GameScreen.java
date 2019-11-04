package GameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

import Info.Info;

public class GameScreen implements Screen {

    private Main main;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Array<Block> blocks;
    private Array<Block> current_block;
    private float lowYPos, leftXPos, rightXPos;

    public GameScreen(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        blocks = new Array<>();
        current_block = new Array<>();
        camera = new OrthographicCamera(Info.WIDTH,Info.HEIGHT);
        viewport = new FitViewport(Info.WIDTH,Info.HEIGHT, camera);
        assetManager = new AssetManager();
        assetManager.load("GameScreen/GameBackground.png", Texture.class);
        assetManager.load("GameScreen/T.png", Texture.class);
        assetManager.load("GameScreen/pOfT.png", Texture.class);
        assetManager.finishLoading();
        World world = new World(new Vector2(0,0),true);
        world.step(1/60f,6,4);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.getBatch().begin();
        main.getBatch().disableBlending();
        main.getBatch().draw(assetManager.get("GameScreen/GameBackground.png", Texture.class),
                0,0);
        main.getBatch().enableBlending();
        for (Block block : blocks)
            main.getBatch().draw(block, block.getX(), block.getY());
        for (Block block : current_block)
            main.getBatch().draw(block, block.getX(), block.getY());
        main.getBatch().end();
        camera.update();
        inputeHandler();
    }

    private void inputeHandler() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            blocks.addAll(current_block);
            current_block.clear();
            current_block.add(new Block(240, Info.HEIGHT - 59, assetManager),
                    new Block(240, Info.HEIGHT - 91, assetManager),
                    new Block(208, Info.HEIGHT - 91, assetManager),
                    new Block(272, Info.HEIGHT - 91, assetManager));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            boolean clear = true;
            boolean equalX;
            lowYPos = current_block.first().getY();
            for (Sprite sprite: current_block) {
                if (sprite.getY() < lowYPos)
                    lowYPos = sprite.getY();
            }
            if (lowYPos > 134) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : current_block) {
                        equalX = sprite.getX() == sprite1.getX();
                        if ((sprite.getY() + sprite.getHeight() > sprite1.getY() - 31) && equalX)
                            clear = false;
                    }
                if (clear) for (Sprite sprite2 : current_block)
                    sprite2.setPosition(sprite2.getX(),
                            sprite2.getY() - 32);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            boolean clear = true;
            boolean equalY;
            leftXPos = current_block.first().getX();
            for (Sprite sprite: current_block) {
                if (sprite.getX() < leftXPos)
                    leftXPos = sprite.getX();
            }
            if (leftXPos > 80) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : current_block) {
                        equalY = sprite.getY() == sprite1.getY();
                        if ((sprite.getX() == sprite1.getX() - 32) && equalY)
                            clear = false;
                    }
                if (clear) for (Sprite sprite : current_block)
                    sprite.setPosition(sprite.getX() - 32,
                            sprite.getY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            boolean clear = true;
            boolean equalY;
            rightXPos = current_block.first().getX() + current_block.first().getWidth();
            for (Sprite sprite: current_block) {
                if (sprite.getX() + sprite.getWidth() > rightXPos)
                    rightXPos = sprite.getX() + sprite.getWidth();
            }
            if (rightXPos < 400) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : current_block) {
                        equalY = sprite.getY() == sprite1.getY();
                        if ((sprite.getX() == sprite1.getX() + 32) && equalY)
                            clear = false;
                    }
                if (clear) for (Sprite sprite : current_block)
                    sprite.setPosition(sprite.getX() + 32,
                            sprite.getY());
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update((int)Info.WIDTH,(int)Info.HEIGHT);
    }

    //test commit

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
