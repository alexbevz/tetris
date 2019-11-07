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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.Random;

import Info.Info;
import Info.myStats;

public class GameScreen implements Screen {

    private Main main;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Array<Block> blocks, nextBlock;

    private Array<Block> cb;  //current block

    private int cs, cf;  //current state, current figure
    private int csNext = -1, cfNext = -1;

    private Block b; //block
    private Timer timer, timer2;

    private boolean falling;
    private boolean downPressed;
    private boolean justCreated;

    public GameScreen(Main main) {
        this.main = main;
        assetManager = main.getAssetManager();
    }

    @Override
    public void show() {
        downPressed = false;
        timer = new Timer();
        timer2 = new Timer();
        b = new Block();
        blocks = new Array<>();
        nextBlock = new Array<>();
        cb = new Array<>();
        camera = new OrthographicCamera(Info.WIDTH,Info.HEIGHT);
        viewport = new FitViewport(Info.WIDTH,Info.HEIGHT, camera);
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
        for (Block block : nextBlock)
            main.getBatch().draw(block, block.getX(), block.getY());
        main.getBatch().end();
        camera.update();
        inputeHandler();
        lineCheck();
    }

    private void fallingFigures() {
        if (!justCreated)
            downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        else downPressed = false;
        if (falling && !downPressed) {
            if (!justCreated)
                checkBottomClear();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    justCreated = false;
                    fallingFigures();
                }
            }, myStats.getCurrentSpeed() / Info.FRAMES_PER_SECOND);
        }
        else if (falling) {
            timer.clear();
            checkBottomClear();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    justCreated = false;
                    fallingFigures();
                }
            }, 2 / Info.FRAMES_PER_SECOND);
        }
        else {
            timer.clear();
            timer.scheduleTask(new Timer.Task() {
               @Override
               public void run() {
                   justCreated = true;
                   createRandomTetromino();
               }
           }, 14 / Info.FRAMES_PER_SECOND);
        }
    }

    private void movingFaster() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            checkLeftClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 6 / Info.FRAMES_PER_SECOND);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            checkRightClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 6 / Info.FRAMES_PER_SECOND);
        }
    }

    private void movingFigure() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            checkLeftClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            checkRightClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }
    }

    private void inputeHandler() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            blocks.clear();
            cb.clear();
            justCreated = true;
            createRandomTetromino();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            myStats.setCurrentSpeed(myStats.getCurrentSpeed() - 1);
            System.out.println(myStats.getCurrentSpeed());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            myStats.setCurrentSpeed(myStats.getCurrentSpeed() + 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if (rotationAvailable()) {
                b.turnLeft(this, cb, cf);
                falling = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (rotationAvailable()) {
                b.turnRight(this, cb, cf);
                falling = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            timer.clear();
            fallingFigures();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            movingFigure();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            movingFigure();
        }
    }

    private void createRandomTetromino() {
        Random rand = new Random();
        if (csNext == -1) {
            cf = rand.nextInt(7);
            int currentS = rand.nextInt(4);
            cs = 0;
            blocks.addAll(cb);
            cb.clear();
            b.newTetromino(this, cf, currentS, cb);
            falling = true;
            fallingFigures();
        }
        else {
            cf = cfNext;
            int currentS = csNext;
            cs = 0;
            blocks.addAll(cb);
            cb.clear();
            b.newTetromino(this, cf, currentS, cb);
            falling = true;
            fallingFigures();
        }
        cfNext = rand.nextInt(7);
        csNext = rand.nextInt(4);
        showNextTetromino(cfNext, csNext);
    }

    private void showNextTetromino(int cf, int cs) {
        nextBlock.clear();
        int start = this.cs;
        this.cs = 0;
        b.newTetromino(this, cf, cs, nextBlock);
        this.cs = start;
        for (Block block : nextBlock)
            block.setPosition(block.getX() + 200, block.getY() - 40);
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

    private boolean rotationAvailable() {
        boolean left = true, right = true, other_blocks = true;
        Array<Block> copy = new Array<>();
        copy.clear();
        copy.addAll(cb);
        b.turnLeft(this, copy, cf);
        for (Block block : copy) {
            if (block.getX() < Info.LEFT_EDGE_X || block.getX() >= Info.RIGHT_EDGE_X ||
            block.getY() < Info.BOTTOM_EDGE_Y) {
                left = false;
            }
            for (Block blocks : blocks) {
                if (blocks.getY() == block.getY() && blocks.getX() == block.getX())
                    other_blocks = false;
            }
        }
        copy.clear();
        copy.addAll(cb);
        b.turnRight(this, copy, cf);
        for (Block block : copy) {
            if (block.getX() < Info.LEFT_EDGE_X || block.getX() >= Info.RIGHT_EDGE_X ||
                    block.getY() < Info.BOTTOM_EDGE_Y) {
                right = false;
            }
            for (Block blocks : blocks) {
                if (blocks.getY() == block.getY() && blocks.getX() == block.getX())
                    other_blocks = false;
            }
        }
        return left && right && other_blocks;
    }

    private void checkLeftClear() {
        if (falling) {
            boolean clear = true, equalY;
            float leftXPos = cb.first().getX();
            for (Sprite sprite : cb) {
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
    }

    private void checkRightClear() {
        if (falling) {
            boolean clear = true, equalY;
            float rightXPos = cb.first().getX() + cb.first().getWidth();
            for (Sprite sprite : cb) {
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

    private void checkBottomClear() {
        boolean clear = true, equalX;
        float lowYPos = cb.first().getY();
        for (Sprite sprite2: cb) {
            if (sprite2.getY() < lowYPos)
                lowYPos = sprite2.getY();
        }
        if (lowYPos >= Info.BOTTOM_EDGE_Y) {
            for (Sprite sprite : blocks)
                for (Sprite sprite1 : cb) {
                    equalX = sprite.getX() == sprite1.getX();
                    if (((sprite.getY() + sprite.getHeight() > sprite1.getY() - Info.SQ_W) &&
                            (sprite.getY() < sprite1.getY()) && equalX))
                        clear = false;
                }
            if (lowYPos == Info.BOTTOM_EDGE_Y)
                clear = false;
            if (clear) {
                for (Sprite sprite2 : cb)
                    sprite2.setPosition(sprite2.getX(),
                            sprite2.getY() - Info.SQ_W);
                falling = true;
            } else falling = false;
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

    Array<Block> getCb() {
        return cb;
    }

    int getCs() {
        return cs;
    }

    void setCs(int cs) {
        this.cs = cs;
    }

    int getCf() {
        return cf;
    }

    void setFalling(boolean falling) {
        this.falling = falling;
    }

    AssetManager getAssetManager() {
        return assetManager;
    }
}
