package GameClass;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import Info.Info;

class Block extends Sprite {

    Block() {

    }

    private Block(float x, float y, TextureRegion texture) {
        super(texture);
        setPosition(x, y);
    }



    private void addI(Array<Block> current_block, AssetManager assetManager) {
        Texture texture;
        (texture = assetManager.get("GameScreen/Pieces/pOfI.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W * 2, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addJ(Array<Block> current_block, AssetManager assetManager) {
        Texture texture;
        (texture = assetManager.get("GameScreen/Pieces/pOfJ.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addL(Array<Block> current_block, AssetManager assetManager) {
        Texture texture;
        (texture = assetManager.get("GameScreen/Pieces/pOfL.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion));
    }

    private void addZ(Array<Block> current_block, AssetManager assetManager) {
        Texture texture;
        (texture = assetManager.get("GameScreen/Pieces/pOfZ.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addS(Array<Block> current_block, AssetManager assetManager) {
        Texture texture;
        (texture = assetManager.get("GameScreen/Pieces/pOfS.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion));
    }

    private void addO(Array<Block> current_block, AssetManager assetManager) {
            Texture texture;
            (texture = assetManager.get("GameScreen/Pieces/pOfO.png",
                    Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
            TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    private void addT(Array<Block> current_block, AssetManager assetManager) {
        Texture texture;
        (texture = assetManager.get("GameScreen/Pieces/pOfT.png",
                Texture.class)).setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, (int) Info.SQ_W, (int) Info.SQ_W);
            current_block.add(
                    new Block(Info.CENTER_X - Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W, textureRegion),
                    new Block(Info.CENTER_X + Info.SQ_W, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion),
                    new Block(Info.CENTER_X, Info.TOP_EDGE_Y - Info.SQ_W * 2, textureRegion));
    }

    void newTetromino(GameScreen gameScreen, int cf, int cs, Array<Block> cb) {
        AssetManager assetManager = gameScreen.getAssetManager();
        switch (cs) {
            case 0:
                switch (cf) {
                    case 0:
                        addT(cb, assetManager);
                        break;
                    case 1:
                        addO(cb, assetManager);
                        break;
                    case 2:
                        addS(cb, assetManager);
                        break;
                    case 3:
                        addZ(cb, assetManager);
                        break;
                    case 4:
                        addL(cb, assetManager);
                        break;
                    case 5:
                        addJ(cb, assetManager);
                        break;
                    case 6:
                        addI(cb, assetManager);
                        break;
                }
                break;
            case 1:
                switch (cf) {
                    case 0:
                        addT(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 1:
                        addO(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 2:
                        addS(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 3:
                        addZ(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 4:
                        addL(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 5:
                        addJ(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 6:
                        addI(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        break;
                }
                break;
            case 2:
                switch (cf) {
                    case 0:
                        addT(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 1:
                        addO(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 2:
                        addS(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 3:
                        addZ(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 4:
                        addL(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 5:
                        addJ(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                    case 6:
                        addI(cb, assetManager);
                        turnRight(gameScreen, cb, cf);
                        turnRight(gameScreen, cb, cf);
                        break;
                }
                break;
            case 3:
                switch (cf) {
                    case 0:
                        addT(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                    case 1:
                        addO(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                    case 2:
                        addS(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                    case 3:
                        addZ(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                    case 4:
                        addL(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                    case 5:
                        addJ(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                    case 6:
                        addI(cb, assetManager);
                        turnLeft(gameScreen, cb, cf);
                        break;
                }
                break;
        }
    }

    void turnRight(GameScreen gameScreen, Array<Block> cb, int cf) {
        int cs = gameScreen.getCs();
        switch (cf) {
            case 0:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        gameScreen.setCs(1);
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        gameScreen.setCs(2);
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        gameScreen.setCs(3);
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        gameScreen.setCs(0);
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
                        gameScreen.setCs(1);
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        gameScreen.setCs(2);
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
                        gameScreen.setCs(1);
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W,cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        gameScreen.setCs(2);
                        break;
                }
                break;
            case 4:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() + Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        gameScreen.setCs(1);
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        gameScreen.setCs(2);
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() - Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        gameScreen.setCs(3);
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        gameScreen.setCs(0);
                        break;
                }
                break;
            case 5:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W * 2, cb.get(3).getY());
                        gameScreen.setCs(1);
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() + Info.SQ_W * 2);
                        gameScreen.setCs(2);
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W * 2, cb.get(3).getY());
                        gameScreen.setCs(3);
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        gameScreen.setCs(0);
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
                        gameScreen.setCs(1);
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2,cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W * 2);
                        gameScreen.setCs(2);
                        break;
                }
                break;
        }
    }

    void turnLeft(GameScreen gameScreen, Array<Block> cb, int cf) {
        int cs = gameScreen.getCs();
        switch (cf) {
            case 0:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        gameScreen.setCs(3);
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        gameScreen.setCs(2);
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        gameScreen.setCs(1);
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        gameScreen.setCs(0);
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
                        gameScreen.setCs(1);
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        gameScreen.setCs(2);
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
                        gameScreen.setCs(1);
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W,cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        gameScreen.setCs(2);
                        break;
                }
                break;
            case 4:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        gameScreen.setCs(3);
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() + Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() + Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        gameScreen.setCs(2);
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2, cb.get(0).getY());
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W);
                        gameScreen.setCs(1);
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX(), cb.get(0).getY() - Info.SQ_W * 2);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() + Info.SQ_W);
                        gameScreen.setCs(0);
                        break;
                }
                break;
            case 5:
                switch (cs) {
                    case 0:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() + Info.SQ_W * 2);
                        gameScreen.setCs(3);
                        break;
                    case 3:
                        cb.get(0).setPosition(cb.get(0).getX() + Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() - Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() - Info.SQ_W * 2, cb.get(3).getY());
                        gameScreen.setCs(2);
                        break;
                    case 2:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() + Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX(), cb.get(3).getY() - Info.SQ_W * 2);
                        gameScreen.setCs(1);
                        break;
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W, cb.get(0).getY() - Info.SQ_W);
                        cb.get(2).setPosition(cb.get(2).getX() + Info.SQ_W, cb.get(2).getY() + Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W * 2, cb.get(3).getY());
                        gameScreen.setCs(0);
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
                        gameScreen.setCs(1);
                        break;
                    case 3:
                    case 1:
                        cb.get(0).setPosition(cb.get(0).getX() - Info.SQ_W * 2,cb.get(0).getY() + Info.SQ_W);
                        cb.get(1).setPosition(cb.get(1).getX() - Info.SQ_W, cb.get(1).getY() - Info.SQ_W);
                        cb.get(3).setPosition(cb.get(3).getX() + Info.SQ_W, cb.get(3).getY() - Info.SQ_W * 2);
                        gameScreen.setCs(2);
                        break;
                }
                break;
        }
    }
}

