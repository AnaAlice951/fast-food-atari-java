package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGame;
import com.mygdx.game.Player;
import com.mygdx.game.components.TextComponent;

public class GameScreen implements Screen {

	private Player player;
	private MyGame game;
	private int pointsUntilNextLevel, points;
	private int nextLevelY;
	private TextComponent pointsDisplay, nextLevelMessage;
	
	OrthographicCamera camera;
	
	public GameScreen(MyGame game) {
		this.game = game;
		this.player = new Player();
		this.pointsUntilNextLevel = 0;
		nextLevelY = Gdx.graphics.getHeight();
		pointsDisplay = new TextComponent(50, String.valueOf(points));
		nextLevelMessage = new TextComponent(50, "Você está engordando!");
	}
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
	  
		camera.update();
	
		game.getBatch().setProjectionMatrix(camera.combined);
	    
		if(pointsUntilNextLevel <= 0) {
			nextLevelMessage.write(
				game.getBatch(), 
				(int) (Gdx.graphics.getWidth()/2 - nextLevelMessage.getGlyphLayout().width/2), 
				nextLevelY
			);
			nextLevelY -= 300 * Gdx.graphics.getDeltaTime();
			
			if(nextLevelY <= 0) {
				pointsUntilNextLevel = 100;
			}
		} else {
			pointsDisplay.write(
				game.getBatch(), 
				(int) (Gdx.graphics.getWidth()/2 - pointsDisplay.getGlyphLayout().width/2), 
				(int) ((Gdx.graphics.getHeight() - 50) + (pointsDisplay.getGlyphLayout().height/2))
			);
			
			player.move();
		    player.verifyOverflow();
			player.render(game.getBatch());
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
		player.disposeAll();
	}

}
