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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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

    private Block b; //block

    private Timer timer, timer2;

    private boolean falling;
    private boolean blockRotate = false;
    private boolean pauseState;

    private Stage stage;

    private Sprite nextFigure;

    private Texture bg;

    private Label currentScoreLabel;
    private Label currentSpeedLabel;

    private CurrentTouch currentTouch;

    public GameScreen(Main main) {
        this.main = main;
        assetManager = main.getAssetManager();
    }

    @Override
    public void show() {

        (bg = assetManager.get("GameScreen/Background/EmptyGameBackground.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        timer = new Timer();
        timer2 = new Timer();
        b = new Block();
        blocks = new Array<>();
        cb = new Array<>();
        camera = new OrthographicCamera(Info.WIDTH, Info.HEIGHT);
        camera.position.set(Info.WIDTH / 2,Info.HEIGHT / 2, 0);
        viewport = new FitViewport(Info.WIDTH, Info.HEIGHT, camera);
        stage = new Stage(viewport, main.getBatch());
        labelsGenerate();
        buttonsActivate();
        Gdx.input.setInputProcessor(stage);
        timer.clear();
        currentTouch = CurrentTouch.EMPTY;
        createRandomTetromino();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int score = Integer.parseInt(("" + myStats.getCurrentScore()).split("\\.")[0]);
        int speed = Integer.parseInt(("" + myStats.getCurrentSpeed()).split("\\.")[0]);
        currentScoreLabel.setText(score);
        currentSpeedLabel.setText(61 - speed);
        main.getBatch().begin();
        main.getBatch().disableBlending();
        main.getBatch().draw(new TextureRegion(bg), 0, 0, Info.WIDTH, Info.HEIGHT);
        main.getBatch().enableBlending();
        for (Block block : blocks)
            main.getBatch().draw(block, block.getX(), block.getY());
        for (Block block : cb)
            main.getBatch().draw(block, block.getX(), block.getY());
        if (nextFigure != null) {
            nextFigure.draw(main.getBatch());
        }
        main.getBatch().end();
        stage.draw();
        main.getBatch().setProjectionMatrix(camera.combined);
        camera.update();
        if (!pauseState) {
            for (Actor actor: stage.getActors()) {
                if (actor.getName() == null)
                    actor.setTouchable(Touchable.enabled);

            }
            timer.start();
            timer2.start();
            inputeHandler();
            stage.act();
        } else {
            for (Actor actor: stage.getActors()) {
                if (actor.getName() == null)
                    actor.setTouchable(Touchable.disabled);

            }
            timer.stop();
            timer2.stop();
        }

    }

    private Label labelGenerate(String text, int size) {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MyriadPro-Semibold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.spaceX = 2;
        parameter.borderWidth = 0.5f;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        if (text.contains("" + myStats.getCurrentScore()) || text.contains("" + (60 - myStats.getCurrentSpeed())))
            return new Label(text, new Label.LabelStyle(bitmapFont, Color.WHITE));
        else
            return new Label(text, new Label.LabelStyle(bitmapFont, new Color(0xBFDAACFF)));
    }

    private void labelsGenerate() {
        currentScoreLabel = labelGenerate("" + myStats.getCurrentScore(), 79);
        currentScoreLabel.setAlignment(Align.center);

        currentSpeedLabel = labelGenerate("" + (60 - myStats.getCurrentSpeed()), 79);
        currentSpeedLabel.setAlignment(Align.center);

        Label scoreLabel = labelGenerate("SCORE", 69);
        scoreLabel.setAlignment(Align.center);

        Label speedLabel = labelGenerate("SPEED", 69);
        speedLabel.setAlignment(Align.center);

        Label nextLabel = labelGenerate("NEXT", 69);
        nextLabel.setAlignment(Align.center);

        Label resetLabel = labelGenerate("RESET", 69);
        resetLabel.setAlignment(Align.center);

        Table table = new Table();
        table.top().right();
        table.padTop(200).padRight(68);
        table.add(scoreLabel).width(190).height(50).fillY().align(Align.center);
        table.row();
        table.add(currentScoreLabel).padTop(92).width(190).height(50).fillX();
        table.row();
        table.add(nextLabel).padTop(92).width(190).height(50).fillX();
        table.row();
        table.add(speedLabel).padTop(302).width(190).height(50).fillX();
        table.row();
        table.add(currentSpeedLabel).padTop(162).width(190).height(50).fillX();
        table.row();
        table.add(resetLabel).padTop(250).width(190).height(50).fillX();
        table.row();
        table.setFillParent(true);

        stage.addActor(table);
    }

    private void buttonsActivate() {

        Sprite sprite = new Sprite(assetManager.get("GameScreen/Buttons/leftArrow.png", Texture.class));

        Button leftButton = new ImageButton(new SpriteDrawable(sprite));

        sprite = new Sprite(assetManager.get("GameScreen/Buttons/leftArrow.png", Texture.class));

        sprite.flip(true, false);

        Button rightButton = new ImageButton(new SpriteDrawable(sprite));
        Button downButton = new ImageButton(new SpriteDrawable(new Sprite(assetManager.get("GameScreen/Buttons/downArrow.png", Texture.class))));
        Button increaseButton = new ImageButton(new SpriteDrawable(new Sprite(assetManager.get("GameScreen/Buttons/addLevel.png", Texture.class))));
        Button decreaseButton = new ImageButton(new SpriteDrawable(new Sprite(assetManager.get("GameScreen/Buttons/subtractLevel.png", Texture.class))));

        sprite = new Sprite(assetManager.get("GameScreen/Buttons/leftRotate.png", Texture.class));

        Button rotateLButton = new ImageButton(new SpriteDrawable(sprite));

        sprite = new Sprite(assetManager.get("GameScreen/Buttons/leftRotate.png", Texture.class));

        sprite.flip(true, false);

        Button rotateRButton = new ImageButton(new SpriteDrawable(sprite));
        Button resetButton = new ImageButton(new SpriteDrawable(new Sprite(assetManager.get("GameScreen/Buttons/reset.png", Texture.class))));
        Button hardDrop = new ImageButton(new SpriteDrawable(new Sprite(assetManager.get("GameScreen/Buttons/hardDrop.png", Texture.class))));
        Button pauseButton = new ImageButton(new SpriteDrawable(new Sprite(assetManager.get("GameScreen/Buttons/pause.png", Texture.class))));

        leftButton.setPosition(26, 186);
        leftButton.setHeight(222);
        leftButton.setWidth(215);

        rightButton.setPosition(268, 186);
        rightButton.setHeight(222);
        rightButton.setWidth(215);

        downButton.setPosition(140, 20);
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
        hardDrop.setHeight(156);
        hardDrop.setWidth(156);

        pauseButton.setPosition(934, 1774);
        pauseButton.setHeight(116);
        pauseButton.setWidth(116);
        pauseButton.setName("pauseButton");

        leftButton.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                timer2.clear();
                currentTouch = CurrentTouch.LEFT;
                movingFigure();

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                currentTouch = CurrentTouch.EMPTY;
                timer2.clear();
            }

        });

        rightButton.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                timer2.clear();
                currentTouch = CurrentTouch.RIGHT;
                movingFigure();

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                currentTouch = CurrentTouch.EMPTY;
                timer2.clear();
            }

        });

        downButton.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                timer2.clear();
                currentTouch = CurrentTouch.DOWN;
                movingFigure();

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                currentTouch = CurrentTouch.EMPTY;
                timer2.clear();
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
                if (myStats.getCurrentSpeed() > 1)
                    myStats.setCurrentSpeed(myStats.getCurrentSpeed() - 1);
            }
        });

        decreaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (myStats.getCurrentSpeed() < 60)
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
                createRandomTetromino();
            }
        });

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseState = !pauseState;
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
        stage.addActor(pauseButton);

    }

    private void useHardDrop() {
        while (falling)
            checkBottomClear();
    }

    private void fallingFigures() {

        if (falling && !(currentTouch == CurrentTouch.DOWN) && !(Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
            timer.clear();
            checkBottomClear();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    fallingFigures();
                }
            }, myStats.getCurrentSpeed() / Info.FRAMES_PER_SECOND);
        } else if (falling) {
            timer.clear();
            checkBottomClear();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
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
                   createRandomTetromino();
                   blockRotate = false;
               }
           }, 14 / Info.FRAMES_PER_SECOND);
        }

    }

    private void movingFaster() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || currentTouch == CurrentTouch.LEFT) {
            checkLeftClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 6 / Info.FRAMES_PER_SECOND);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || currentTouch == CurrentTouch.RIGHT) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || currentTouch == CurrentTouch.LEFT) {
            checkLeftClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || currentTouch == CurrentTouch.RIGHT) {
            checkRightClear();
            timer2.clear();
            timer2.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    movingFaster();
                }
            }, 16 / Info.FRAMES_PER_SECOND);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || currentTouch == CurrentTouch.DOWN) {
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            useHardDrop();

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            movingFigure();

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

        Texture texture = assetManager.get("GameScreen/Next Figures/" + cf + ".png", Texture.class);
        texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
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
