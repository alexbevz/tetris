package GameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
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

    private Array<Block> blocks, cb;  // all field blocks, current block,

    private int cs, cf;  //current state, current figure
    private int csNext = -1, cfNext = -1;
    private int clearedLines = 0;
    private int currentPointer;

    private Block b; //block

    private Timer timer, timer2;

    private boolean falling;
    private boolean downPressed;
    private boolean justCreated;
    private boolean leftReleased = true, rightReleased = true, downReleased = true;
    private boolean blockRotate = false;

    private Stage stage;

    private Sprite nextFigure;

    private Texture bg;

    private Label scoreLabel;

    public GameScreen(Main main) {
        this.main = main;
        assetManager = main.getAssetManager();
    }

    @Override
    public void show() {

        scoreLabelGenerate();
        (bg = assetManager.get("GameScreen/GameBackground.png",
                Texture.class)).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        downPressed = false;
        timer = new Timer();
        timer2 = new Timer();
        b = new Block();
        blocks = new Array<>();
        cb = new Array<>();
        camera = new OrthographicCamera(Info.WIDTH, Info.HEIGHT);
        camera.position.set(Info.WIDTH / 2,Info.HEIGHT / 2, 0);
        viewport = new FitViewport(Info.WIDTH, Info.HEIGHT, camera);
        stage = new Stage(viewport, main.getBatch());
        buttonsActivate();
        Gdx.input.setInputProcessor(stage);
        timer.clear();
        fallingFigures();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int score = Integer.parseInt(("" + myStats.getCurrentScore()).split("\\.")[0]);
        scoreLabel.setText(score);
        main.getBatch().begin();
        main.getBatch().disableBlending();
        main.getBatch().draw(new TextureRegion(bg), 0, 0, Info.WIDTH, Info.HEIGHT);
        main.getBatch().enableBlending();
        for (Block block : blocks)
            main.getBatch().draw(block, block.getX(), block.getY());
        for (Block block : cb)
            main.getBatch().draw(block, block.getX(), block.getY());
        if (nextFigure != null)
            nextFigure.draw(main.getBatch());
        scoreLabel.draw(main.getBatch(), 1);
        main.getBatch().end();
        main.getBatch().setProjectionMatrix(camera.combined);
        camera.update();
        inputeHandler();
        touchHandler();

    }

    private void touchHandler() {

        if (Gdx.input.isTouched(currentPointer)) {
            if (Gdx.input.getY() < ((Info.REAL_HEIGHT - Info.BB_HEIGHT * 2) * 0.9046875f) + Info.BB_HEIGHT &&
                    Gdx.input.getY() > (Info.REAL_HEIGHT - Info.BB_HEIGHT * 2) * 0.786979167f + Info.BB_HEIGHT) {
                if (Gdx.input.getX() > Info.REAL_WIDTH * 0.0324f && Gdx.input.getX() < Info.REAL_WIDTH * 0.4388f) {
                    if (Gdx.input.getX() > Info.REAL_WIDTH * 0.236f) {
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
            else if (Gdx.input.getY() > (Info.REAL_HEIGHT - Info.BB_HEIGHT * 2) * 0.9046875f + Info.BB_HEIGHT &&
                    Gdx.input.getX() > Info.REAL_WIDTH * 0.0324f && Gdx.input.getX() < Info.REAL_WIDTH * 0.4388f) {
                leftReleased = true;
                rightReleased = true;
                downReleased = false;
            }
        }

    }

    private void scoreLabelGenerate() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Myriad Pro Light.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.borderWidth = 0f;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        scoreLabel = new Label("" + myStats.getCurrentScore(), new Label.LabelStyle(bitmapFont, new Color(229, 230, 255, 1)));
        scoreLabel.setPosition(800, 1670);
        scoreLabel.setSize(230,91);
        scoreLabel.setAlignment(Align.center);

    }

    private void buttonsActivate() {

        Button leftButton = new Button();
        Button rightButton = new Button();
        Button downButton = new Button();
        Button increaseButton = new Button();
        Button decreaseButton = new Button();
        Button rotateLButton = new Button();
        Button rotateRButton = new Button();
        Button resetButton = new Button();
        Button hardDrop = new Button();

        leftButton.setPosition(36, 186);
        leftButton.setHeight(222);
        leftButton.setWidth(215);

        rightButton.setPosition(258, 186);
        rightButton.setHeight(222);
        rightButton.setWidth(215);

        downButton.setPosition(98, 78);
        downButton.setHeight(161);
        downButton.setWidth(228);

        increaseButton.setPosition(928, 882);
        increaseButton.setHeight(133);
        increaseButton.setWidth(133);

        decreaseButton.setPosition(777, 882);
        decreaseButton.setHeight(133);
        decreaseButton.setWidth(133);

        rotateLButton.setPosition(652, 25);
        rotateLButton.setHeight(203);
        rotateLButton.setWidth(203);

        rotateRButton.setPosition(862, 25);
        rotateRButton.setHeight(203);
        rotateRButton.setWidth(203);

        resetButton.setPosition(804, 434);
        resetButton.setHeight(225);
        resetButton.setWidth(225);

        hardDrop.setPosition(450, 70);
        hardDrop.setHeight(132);
        hardDrop.setWidth(132);

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
                if (rotationAvailable(blockRotate)) {
                    b.turnLeft(GameScreen.this, cb, cf);
                    falling = true;
                }
            }
        });

        rotateRButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (rotationAvailable(blockRotate)) {
                    b.turnRight(GameScreen.this, cb, cf);
                    falling = true;
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

        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                blocks.clear();
                cb.clear();
                timer.clear();
                myStats.setCurrentScore(0);
                justCreated = true;
                createRandomTetromino();
            }
        });

        hardDrop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                useHardDrop();
            }
        });

        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(downButton);
        stage.addActor(increaseButton);
        stage.addActor(decreaseButton);
        stage.addActor(rotateLButton);
        stage.addActor(rotateRButton);
        stage.addActor(resetButton);
        stage.addActor(hardDrop);

    }

    private void useHardDrop() {
        while (falling)
            checkBottomClear();
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
            blockRotate = true;
            timer.clear();
            timer.scheduleTask(new Timer.Task() {
               @Override
               public void run() {
                   blocks.addAll(cb);
                   cb.clear();
                   clearedLines = 0;
                   for (int i = 0; i < 10; i++)
                       lineCheck();
                   updateScore();
                   justCreated = true;
                   createRandomTetromino();
                   blockRotate = false;
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
            timer.clear();
            myStats.setCurrentScore(0);
            justCreated = true;
            createRandomTetromino();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            myStats.setCurrentSpeed(myStats.getCurrentSpeed() - 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            myStats.setCurrentSpeed(myStats.getCurrentSpeed() + 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if (rotationAvailable(blockRotate)) {
                b.turnLeft(this, cb, cf);
                falling = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (rotationAvailable(blockRotate)) {
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
        downReleased = true;
    }

    private void showNextTetromino(int cf, int cs) {

        Texture texture = assetManager.get("GameScreen/Pieces/" + cf + ".png", Texture.class);
        nextFigure = new Sprite(texture, texture.getWidth(), texture.getHeight());
        nextFigure.setOrigin(nextFigure.getWidth() / 2,nextFigure.getHeight() / 2);
        nextFigure.setRotation(360 - 90 * cs);
        nextFigure.setPosition(Info.NEXTFIGURE_BL_X,Info.NEXTFIGURE_BL_Y);

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
                clearedLines++;
                for (int i = 0; i < 10; i++)
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

    private void updateScore() {

        switch (clearedLines) {
            case 1:
                myStats.setCurrentScore((int)(myStats.getCurrentScore() + 400));
                break;
            case 2:
                myStats.setCurrentScore((int)(myStats.getCurrentScore() + 1000));
                break;
            case 3:
                myStats.setCurrentScore((int)(myStats.getCurrentScore() + 3000));
                break;
            case 4:
                myStats.setCurrentScore((int)(myStats.getCurrentScore() + 12000));
                break;
        }

    }

    private boolean rotationAvailable(boolean blockRotate) {

        boolean left = true, right = true, other_blocks = true;
        Array<Block> copy = new Array<>();

        copy.clear();
        copy.addAll(cb);
        b.turnLeft(this, copy, cf);

        for (int i = 0; i < 5; i++) {
            for (Block block : copy) {
                if (block.getX() < Info.LEFT_EDGE_X || block.getX() >= Info.RIGHT_EDGE_X ||
                        block.getY() < Info.BOTTOM_EDGE_Y) {
                    left = false;
                }
                for (Block blocks : blocks) {
                    if (Math.abs(blocks.getY() - block.getY()) <= 5 && Math.abs(blocks.getX() - block.getX()) <= 5)
                        other_blocks = false;
                }
            }
        }

        copy.clear();
        copy.addAll(cb);
        b.turnRight(this, copy, cf);

        for (int i = 0; i < 5; i++) {
            for (Block block : copy) {
                if (block.getX() < Info.LEFT_EDGE_X || block.getX() >= Info.RIGHT_EDGE_X ||
                        block.getY() < Info.BOTTOM_EDGE_Y) {
                    right = false;
                }
                for (Block blocks : blocks) {
                    if (Math.abs(blocks.getY() - block.getY()) <= 5 && Math.abs(blocks.getX() - block.getX()) <= 5)
                        other_blocks = false;
                }
            }
        }

        return left && right && other_blocks && !blockRotate;
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
        viewport.update(width, height);
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

    int getCs() {
        return cs;
    }

    void setCs(int cs) {
        this.cs = cs;
    }

    AssetManager getAssetManager() {
        return assetManager;
    }
}
