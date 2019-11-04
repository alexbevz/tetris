package GameClass;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Block extends Sprite {

    public Block(float x, float y, AssetManager assetManager) {
        super(assetManager.get("GameScreen/pOfT.png", Texture.class));
        setPosition(x, y);
    }

}
