package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGame;
import com.mygdx.game.components.TextComponent;

public class StartScreen implements Screen {
	private MyGame game;
	private OrthographicCamera camera;
	private TextComponent text;
	
	public StartScreen(MyGame game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		text = new TextComponent(50);
		
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 0);
		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);
		
		text.write(
			game.getBatch(), 
			(int) ((Gdx.graphics.getWidth()/2) - text.getGlyphLayout().width/2),
			(int) ((Gdx.graphics.getHeight()/2) + (text.getGlyphLayout().height/2)),
			"Click to play!",
			Color.GOLD
		);
		
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}
