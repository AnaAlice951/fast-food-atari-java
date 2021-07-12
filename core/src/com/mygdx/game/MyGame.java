package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.StartScreen;

/**
 * Classe inicializadora
 */
public class MyGame extends Game {
	
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		// Navega para a tela inicial do jogo
		this.setScreen(new StartScreen(this));
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void render () {
		super.render();
   }

	@Override
	public void dispose () {
		batch.dispose();
	}
}