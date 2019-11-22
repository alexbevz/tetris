package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import GameClass.GameScreen;
import Info.Info;

public class Main extends Game {

	private SpriteBatch batch;

	private AssetManager assetManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
		textureParameter.genMipMaps = true;

		assetManager.load("GameScreen/Background/EmptyGameBackground.png", Texture.class, textureParameter);

		assetManager.load("GameScreen/Buttons/addLevel.png", Texture.class);
		assetManager.load("GameScreen/Buttons/subtractLevel.png", Texture.class);
		assetManager.load("GameScreen/Buttons/downArrow.png", Texture.class);
		assetManager.load("GameScreen/Buttons/hardDrop.png", Texture.class);
		assetManager.load("GameScreen/Buttons/leftArrow.png", Texture.class);
		assetManager.load("GameScreen/Buttons/leftRotate.png", Texture.class);
		assetManager.load("GameScreen/Buttons/reset.png", Texture.class);
		assetManager.load("GameScreen/Buttons/pause.png", Texture.class);

		assetManager.load("GameScreen/Pieces/pOfI.png", Texture.class, textureParameter);
		assetManager.load("GameScreen/Pieces/pOfJ.png", Texture.class, textureParameter);
		assetManager.load("GameScreen/Pieces/pOfL.png", Texture.class, textureParameter);
		assetManager.load("GameScreen/Pieces/pOfO.png", Texture.class, textureParameter);
		assetManager.load("GameScreen/Pieces/pOfS.png", Texture.class, textureParameter);
		assetManager.load("GameScreen/Pieces/pOfT.png", Texture.class, textureParameter);
		assetManager.load("GameScreen/Pieces/pOfZ.png", Texture.class, textureParameter);

        assetManager.load("GameScreen/Next Figures/0.png", Texture.class, textureParameter);
        assetManager.load("GameScreen/Next Figures/1.png", Texture.class, textureParameter);
        assetManager.load("GameScreen/Next Figures/2.png", Texture.class, textureParameter);
        assetManager.load("GameScreen/Next Figures/3.png", Texture.class, textureParameter);
        assetManager.load("GameScreen/Next Figures/4.png", Texture.class, textureParameter);
        assetManager.load("GameScreen/Next Figures/5.png", Texture.class, textureParameter);
        assetManager.load("GameScreen/Next Figures/6.png", Texture.class, textureParameter);

		assetManager.finishLoading();

		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {

	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
