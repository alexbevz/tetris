package GameClass;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import Info.Info;

class Block extends Sprite {

    Block(float x, float y, TextureRegion texture) {
        super(texture);
        setPosition(x, y);
    }

}

