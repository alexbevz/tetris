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

import java.util.ArrayList;

import javax.lang.model.type.ArrayType;

import Info.Info;

public class GameScreen implements Screen {

    private Main main;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Array<Block> blocks;
    private Array<Block> current_block;
    private boolean deleted = false;
    private int current_state;

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
        lineCheck();
    }

    private void lineCheck() {
        ArrayList<Float> currentYs = new ArrayList<>();
        for (Sprite sprite : blocks) {
            if (!currentYs.contains(sprite.getY())) {
                currentYs.add(sprite.getY());
            }
        }
        for (Float currentY : currentYs) {
            int count = 0;
            for (Sprite sprite : blocks) {
                if (sprite.getY() == currentY)
                    count++;
            }
            if (count == 10) {
                for (Block sprite : blocks) {
                    if (sprite.getY() == currentY) {
                        blocks.removeValue(sprite, false);
                    }
                }
                for (Block sprite : blocks) {
                    if (sprite.getY() == currentY) {
                        blocks.removeValue(sprite, false);
                    }
                }
                deleted = true;
                for (Block sprite : blocks) {
                    if (sprite.getY() > currentY) {
                        sprite.setPosition(sprite.getX(),sprite.getY() - Info.SQUARE_WIDTH);
                    }
                }
            } else deleted = false;
        }
    }

    private void inputeHandler() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            current_state = 0;
            if (!deleted)
                blocks.addAll(current_block);
            System.out.println(blocks.size);
            current_block.clear();
            current_block.add(new Block(208, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH * 2, assetManager),
                    new Block(240, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH, assetManager),
                    new Block(272, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH * 2, assetManager),
                    new Block(240, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH * 2, assetManager));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            switch (current_state) {
                case 0:
                    current_block.get(0).setPosition(current_block.get(1).getX(), current_block.get(1).getY());
                    current_block.get(1).setPosition(current_block.get(2).getX(), current_block.get(2).getY());
                    current_block.get(2).setPosition(current_block.get(0).getX(), current_block.get(0).getY() - Info.SQUARE_WIDTH * 2);
                    current_state = 1;
                    break;
                case 1:
                    current_block.get(0).setPosition(current_block.get(1).getX(), current_block.get(1).getY());
                    current_block.get(1).setPosition(current_block.get(2).getX(), current_block.get(2).getY());
                    current_block.get(2).setPosition(current_block.get(0).getX() - Info.SQUARE_WIDTH * 2, current_block.get(0).getY());
                    current_state = 2;
                    break;
                case 2:
                    current_block.get(0).setPosition(current_block.get(1).getX(), current_block.get(1).getY());
                    current_block.get(1).setPosition(current_block.get(2).getX(), current_block.get(2).getY());
                    current_block.get(2).setPosition(current_block.get(0).getX(), current_block.get(0).getY() + Info.SQUARE_WIDTH * 2);
                    current_state = 3;
                    break;
                case 3:
                    current_block.get(0).setPosition(current_block.get(1).getX(), current_block.get(1).getY());
                    current_block.get(1).setPosition(current_block.get(2).getX(), current_block.get(2).getY());
                    current_block.get(2).setPosition(current_block.get(0).getX() + Info.SQUARE_WIDTH * 2, current_block.get(0).getY());
                    current_state = 0;
                    break;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if (!deleted)
                blocks.addAll(current_block);
            System.out.println(blocks.size);
            current_block.clear();
            current_block.add(new Block(240, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH, assetManager),
                    new Block(240, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH * 2, assetManager),
                    new Block(208, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH * 2, assetManager),
                    new Block(272, Info.TOP_EDGE_Y - Info.SQUARE_WIDTH * 2, assetManager));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            boolean clear = true;
            boolean equalX;
            float lowYPos = current_block.first().getY();
            for (Sprite sprite: current_block) {
                if (sprite.getY() < lowYPos)
                    lowYPos = sprite.getY();
            }
            if (lowYPos > Info.BOTTOM_EDGE_Y) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : current_block) {
                        equalX = sprite.getX() == sprite1.getX();
                        if ((sprite.getY() + sprite.getHeight() > sprite1.getY() - Info.SQUARE_WIDTH + 1) && equalX)
                            clear = false;
                    }
                if (clear) for (Sprite sprite2 : current_block)
                    sprite2.setPosition(sprite2.getX(),
                            sprite2.getY() - Info.SQUARE_WIDTH);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            boolean clear = true;
            boolean equalY;
            float leftXPos = current_block.first().getX();
            for (Sprite sprite: current_block) {
                if (sprite.getX() < leftXPos)
                    leftXPos = sprite.getX();
            }
            if (leftXPos > Info.LEFT_EDGE_X) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : current_block) {
                        equalY = sprite.getY() == sprite1.getY();
                        if ((sprite.getX() == sprite1.getX() - Info.SQUARE_WIDTH) && equalY)
                            clear = false;
                    }
                if (clear) for (Sprite sprite : current_block)
                    sprite.setPosition(sprite.getX() - Info.SQUARE_WIDTH,
                            sprite.getY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            boolean clear = true;
            boolean equalY;
            float rightXPos = current_block.first().getX() + current_block.first().getWidth();
            for (Sprite sprite: current_block) {
                if (sprite.getX() + sprite.getWidth() > rightXPos)
                    rightXPos = sprite.getX() + sprite.getWidth();
            }
            if (rightXPos < Info.RIGHT_EDGE_X) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : current_block) {
                        equalY = sprite.getY() == sprite1.getY();
                        if ((sprite.getX() == sprite1.getX() + Info.SQUARE_WIDTH) && equalY)
                            clear = false;
                    }
                if (clear) for (Sprite sprite : current_block)
                    sprite.setPosition(sprite.getX() + Info.SQUARE_WIDTH,
                            sprite.getY());
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update((int)Info.WIDTH,(int)Info.HEIGHT);
    }

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
