package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
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

		if (Info.REAL_HEIGHT >= 1919) {
			assetManager.load("GameScreen/GameBackground1920.png", Texture.class);
		} else if (Info.REAL_HEIGHT >= 1279) {
			assetManager.load("GameScreen/GameBackground1280.png", Texture.class);
		} else
			assetManager.load("GameScreen/GameBackground800.png", Texture.class);

		assetManager.load("GameScreen/pOfI.png", Texture.class);
		assetManager.load("GameScreen/pOfJ.png", Texture.class);
		assetManager.load("GameScreen/pOfL.png", Texture.class);
		assetManager.load("GameScreen/pOfO.png", Texture.class);
		assetManager.load("GameScreen/pOfS.png", Texture.class);
		assetManager.load("GameScreen/pOfT.png", Texture.class);
		assetManager.load("GameScreen/pOfZ.png", Texture.class);

        assetManager.load("GameScreen/Pieces/0.png", Texture.class);
        assetManager.load("GameScreen/Pieces/1.png", Texture.class);
        assetManager.load("GameScreen/Pieces/2.png", Texture.class);
        assetManager.load("GameScreen/Pieces/3.png", Texture.class);
        assetManager.load("GameScreen/Pieces/4.png", Texture.class);
        assetManager.load("GameScreen/Pieces/5.png", Texture.class);
        assetManager.load("GameScreen/Pieces/6.png", Texture.class);

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
