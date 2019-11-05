package GameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.Random;

import Info.Info;

public class GameScreen implements Screen {

    private Main main;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Array<Block> blocks;
    private Array<Block> cb;  //current block
    private int cs, cf;  //current state, current figure

    public GameScreen(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        blocks = new Array<>();
        cb = new Array<>();
        camera = new OrthographicCamera(Info.WIDTH,Info.HEIGHT);
        viewport = new FitViewport(Info.WIDTH,Info.HEIGHT, camera);
        assetManager = new AssetManager();
        assetManager.load("GameScreen/GameBackground.png", Texture.class);
        assetManager.load("GameScreen/pOfI.png", Texture.class);
        assetManager.load("GameScreen/pOfJ.png", Texture.class);
        assetManager.load("GameScreen/pOfL.png", Texture.class);
        assetManager.load("GameScreen/pOfO.png", Texture.class);
        assetManager.load("GameScreen/pOfS.png", Texture.class);
        assetManager.load("GameScreen/pOfT.png", Texture.class);
        assetManager.load("GameScreen/pOfZ.png", Texture.class);
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
        main.getBatch().draw(new TextureRegion(assetManager.get("GameScreen/GameBackground.png",
                Texture.class)), 0, 0, Info.WIDTH, Info.HEIGHT);
        main.getBatch().enableBlending();
        for (Block block : blocks)
            main.getBatch().draw(block, block.getX(), block.getY());
        for (Block block : cb)
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
                for (int i = 0; i < 4; i++)
                    for (Block sprite : blocks) {
                        if (sprite.getY() == currentY) {
                            blocks.removeValue(sprite, false);
                        }
                    }
                for (Block sprite : blocks) {
                    if (sprite.getY() > currentY) {
                        sprite.setPosition(sprite.getX(),sprite.getY() - Info.SQ_W);
                    }
                }
            }
        }
    }

    private void inputeHandler() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Random rand = new Random();
            cf = rand.nextInt(7);
            cs = 0;
            blocks.addAll(cb);
            cb.clear();
            randomTetromino();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            turnRight();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            turnLeft();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            boolean clear = true;
            boolean equalX;
            float lowYPos = cb.first().getY();
            for (Sprite sprite: cb) {
                if (sprite.getY() < lowYPos)
                    lowYPos = sprite.getY();
            }
            if (lowYPos > Info.BOTTOM_EDGE_Y) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : cb) {
                        equalX = sprite.getX() == sprite1.getX();
                        if ((sprite.getY() + sprite.getHeight() > sprite1.getY() - Info.SQ_W + 1) && equalX)
                            clear = false;
                    }
                if (clear) for (Sprite sprite2 : cb)
                    sprite2.setPosition(sprite2.getX(),
                            sprite2.getY() - Info.SQ_W);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            boolean clear = true;
            boolean equalY;
            float leftXPos = cb.first().getX();
            for (Sprite sprite: cb) {
                if (sprite.getX() < leftXPos)
                    leftXPos = sprite.getX();
            }
            if (leftXPos > Info.LEFT_EDGE_X) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : cb) {
                        equalY = sprite.getY() == sprite1.getY();
                        if ((sprite.getX() == sprite1.getX() - Info.SQ_W) && equalY)
                            clear = false;
                    }
                if (clear) for (Sprite sprite : cb)
                    sprite.setPosition(sprite.getX() - Info.SQ_W,
                            sprite.getY());
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            boolean clear = true;
            boolean equalY;
            float rightXPos = cb.first().getX() + cb.first().getWidth();
            for (Sprite sprite: cb) {
                if (sprite.getX() + sprite.getWidth() > rightXPos)
                    rightXPos = sprite.getX() + sprite.getWidth();
            }
            if (rightXPos < Info.RIGHT_EDGE_X) {
                for (Sprite sprite : blocks)
                    for (Sprite sprite1 : cb) {
                        equalY = sprite.getY() == sprite1.getY();
                        if ((sprite.getX() == sprite1.getX() + Info.SQ_W) && equalY)
                            clear = false;
                    }
                if (clear) for (Sprite sprite : cb)
                    sprite.setPosition(sprite.getX() + Info.SQ_W,
                            sprite.getY());
            }
        }
    }

    private void randomTetromino() {
        switch (cf) {
            case 0:
                addT(cb);
                break;
            case 1:
                addO(cb);
                break;
            case 2:
                addS(cb);
                break;
            case 3:
                addZ(cb);
                break;
            case 4:
                addL(cb);
                break;
            case 5:
                addJ(cb);
                break;
            case 6:
                addI(cb);
                break;
        }
    }

    private void turnRight() {
        switch (cf) {
            case 0:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cs = 1;
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cs = 2;
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cs = 3;
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cs = 0;
                        break;
                }
                break;
            case 1:
                break;
            case 2:
                switch (cs) {
                    case 0:
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2,
                                cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        cs = 3;
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        cs = 2;
                        break;
                }
                break;
            case 3:
                switch (cs) {
                    case 0:
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W,cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() + Info.SQ_W * 2);
                        cs = 1;
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W,cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        cs = 2;
                        break;
                }
                break;
            case 4:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() + Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        cs = 1;
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        cs = 2;
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() - Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        cs = 3;
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        cs = 0;
                        break;
                }
                break;
            case 5:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W * 2, cb.get(3).getY());
                        cs = 1;
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() + Info.SQ_W * 2);
                        cs = 2;
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W * 2, cb.get(3).getY());
                        cs = 3;
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        cs = 0;
                        break;
                }
                break;
            case 6:
                switch (cs) {
                    case 0:
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2,cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W * 2);
                        cs = 1;
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2,cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W * 2);
                        cs = 2;
                        break;
                }
                break;
        }
    }

    private void turnLeft() {
        switch (cf) {
            case 0:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cs = 3;
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cs = 2;
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cs = 1;
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cs = 0;
                        break;
                }
                break;
            case 1:
                break;
            case 2:
                switch (cs) {
                    case 0:
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2,
                                cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        cs = 3;
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        cs = 2;
                        break;
                }
                break;
            case 3:
                switch (cs) {
                    case 0:
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W,cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() + Info.SQ_W * 2);
                        cs = 1;
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W,cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        cs = 2;
                        break;
                }
                break;
            case 4:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        cs = 3;
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() + Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        cs = 2;
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        cs = 1;
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() - Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        cs = 0;
                        break;
                }
                break;
            case 5:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() + Info.SQ_W * 2);
                        cs = 3;
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W * 2, cb.get(3).getY());
                        cs = 2;
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        cs = 1;
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W * 2, cb.get(3).getY());
                        cs = 0;
                        break;
                }
                break;
            case 6:
                switch (cs) {
                    case 0:
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2,cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W * 2);
                        cs = 1;
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2,cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W * 2);
                        cs = 2;
                        break;
                }
                break;
        }
    }

    private void addI(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfI.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
                new Block(Info.CENTER_X  - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                new Block(Info.CENTER_X + Info.SQ_W * 2, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addJ(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfJ.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
            new Block(Info.CENTER_X  - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addL(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfL.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
            new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X  - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y  - Info.SQ_W, textureRegion));
    }

    private void addZ(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfZ.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
            new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addS(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfS.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
            new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion));
    }

    private void addO(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfO.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
            new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addT(Array<Block> current_block) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get("GameScreen/pOfT.png",
                Texture.class), (int) Info.SQ_W, (int) Info.SQ_W);
        current_block.add(
            new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
            new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
            new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
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

}
