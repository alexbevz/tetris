package GameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.TouchableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.GrayFilter;

import Info.Info;
import Info.myStats;

public class GameScreen implements Screen {

    private Main main;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Array<Block> blocks;

    private Array<Block> cb;  //current block

    private int cs, cf;  //current state, current figure
    private int csNext = -1, cfNext = -1;

    private Block b; //block
    private Timer timer, timer2;

    private boolean falling;
    private boolean downPressed;
    private boolean justCreated;
    private boolean leftReleased = true, rightReleased = true, downReleased = true;

    private int currentPointer;

    private Stage stage;

    private Texture nextFigure;

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
        cb = new Array<>();
        camera = new OrthographicCamera(Info.WIDTH, Info.HEIGHT);
        camera.position.set(Info.WIDTH / 2,Info.HEIGHT / 2, 0);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * 1.7777f);
        stage = new Stage(viewport, main.getBatch());
        buttonsActivate();
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        if (nextFigure != null) {
            main.getBatch().draw(nextFigure, Info.NEXTFIGURE_BL_X, Info.NEXTFIGURE_BL_Y);
        }
        main.getBatch().end();
        main.getBatch().setProjectionMatrix(camera.combined);
        camera.update();
        inputeHandler();
        lineCheck();
        if (Gdx.input.isTouched(currentPointer)) {
            if (Gdx.input.getY() < 730 && Gdx.input.getY() > 620) {
                if (Gdx.input.getX() > 18 && Gdx.input.getX() < 260) {
                    if (Gdx.input.getX() > 135) {
                        leftReleased = true;
                        rightReleased = false;
                        downReleased = true;
                    } else {
                        leftReleased = false;
                        rightReleased = true;
                        downReleased = true;
                    }
                }
            }
            else if (Gdx.input.getY() > 730 && Gdx.input.getX() > 18 && Gdx.input.getX() < 260) {
                leftReleased = true;
                rightReleased = true;
                downReleased = false;
            }
        }
    }

    private void buttonsActivate() {
        Button leftButton = new Button();
        Button rightButton = new Button();
        Button downButton = new Button();
        Button increaseButton = new Button();
        Button decreaseButton = new Button();
        Button rotateLButton = new Button();
        Button rotateRButton = new Button();
        leftButton.setPosition(18, 74);
        leftButton.setHeight(100);
        leftButton.setWidth(110);
        rightButton.setPosition(148, 74);
        rightButton.setHeight(100);
        rightButton.setWidth(110);
        downButton.setPosition(72, 8);
        downButton.setHeight(58);
        downButton.setWidth(135);
        increaseButton.setPosition(404, 454);
        increaseButton.setHeight(56);
        increaseButton.setWidth(56);
        decreaseButton.setPosition(341, 454);
        decreaseButton.setHeight(56);
        decreaseButton.setWidth(56);
        rotateLButton.setPosition(283, 6);
        rotateLButton.setHeight(98);
        rotateLButton.setWidth(98);
        rotateRButton.setPosition(383, 6);
        rotateRButton.setHeight(98);
        rotateRButton.setWidth(98);
        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentPointer = pointer;
                leftReleased = false;
                rightReleased = true;
                downReleased = true;
                movingFigure();
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                leftReleased = true;
                rightReleased = true;
                downReleased = true;
            }
        });
        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentPointer = pointer;
                rightReleased = false;
                leftReleased = true;
                downReleased = true;
                movingFigure();
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                leftReleased = true;
                rightReleased = true;
                downReleased = true;
            }
        });
        downButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                currentPointer = pointer;
                timer.clear();
                downReleased = false;
                movingFigure();
                leftReleased = true;
                rightReleased = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftReleased = true;
                rightReleased = true;
                downReleased = true;
            }
        });
        rotateLButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (rotationAvailable()) {
                    b.turnLeft(GameScreen.this, cb, cf);
                }
            }
        });
        rotateRButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (rotationAvailable()) {
                    b.turnRight(GameScreen.this, cb, cf);
                }
            }
        });
        increaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myStats.setCurrentSpeed(myStats.getCurrentSpeed() - 1);
            }
        });
        decreaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myStats.setCurrentSpeed(myStats.getCurrentSpeed() + 1);
            }
        });
        timer.clear();
        fallingFigures();
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(downButton);
        stage.addActor(increaseButton);
        stage.addActor(decreaseButton);
        stage.addActor(rotateLButton);
        stage.addActor(rotateRButton);
    }

    private void fallingFigures() {
        if (!leftReleased || !rightReleased) {
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }
        boolean downReleasedCheck;
        if (!justCreated) {
            downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
            downReleasedCheck = downReleased;
        }
        else {
            downPressed = false;
            downReleasedCheck = true;
        }
        if (falling && !downPressed && downReleasedCheck) {
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
            if (cb.notEmpty())
                checkBottomClear();
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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || !leftReleased) {
            checkLeftClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 6 / Info.FRAMES_PER_SECOND);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || !rightReleased) {
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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || !leftReleased) {
            checkLeftClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || !rightReleased) {
            checkRightClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || !downReleased) {
            timer.clear();
            fallingFigures();
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
        nextFigure = assetManager.get("GameScreen/Pieces/" + cf + "_" + cs + ".png", Texture.class);

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
        //stage.getViewport().update((int)Info.WIDTH,(int)Info.HEIGHT);
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
