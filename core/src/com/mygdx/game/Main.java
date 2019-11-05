package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import GameClass.GameScreen;

public class Main extends Game {

	private SpriteBatch batch;

	private AssetManager assetManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
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
