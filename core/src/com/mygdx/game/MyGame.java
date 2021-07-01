package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends Game {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 480);
      batch = new SpriteBatch();
	}

	@Override
	public void render () {
      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      camera.update();

      batch.setProjectionMatrix(camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
