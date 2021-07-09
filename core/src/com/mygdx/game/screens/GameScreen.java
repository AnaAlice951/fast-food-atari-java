package com.mygdx.game.screens;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Food;
import com.mygdx.game.MyGame;
import com.mygdx.game.Player;
import com.mygdx.game.Food.FoodSprite;
import com.mygdx.game.components.TextComponent;

public class GameScreen implements Screen {

	private MyGame game;
	private Player player;
	private Food food;
	private int pointsUntilNextLevel;
	private boolean nextLevelAnimation;
	private boolean gameOverAnimation;
	private int points;
	private int messageY;
	private int gameSpeed;
	private int badFoodIngested;
	
	private Sound eatSound;
	private Sound levelUpSound;
	private Sound gameOverSound;
	private Sound vomitSound;
	
	private TextComponent pointsDisplay;
	private TextComponent nextLevelMessage;
	private TextComponent endGameMessage;
	
	private OrthographicCamera camera;
	
	public GameScreen(MyGame game) {
		this.game = game;
		player = new Player();
		food = new Food();
		pointsUntilNextLevel = 15;
		nextLevelAnimation = false;
		gameOverAnimation = false;
		messageY = Gdx.graphics.getHeight();
		gameSpeed = 0;
		badFoodIngested = 0;
		eatSound = Gdx.audio.newSound(Gdx.files.internal("sounds/eat.mp3"));
		levelUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelup.mp3"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
		vomitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/vomit.mp3"));
		pointsDisplay = new TextComponent(50);
		nextLevelMessage = new TextComponent(50);
		endGameMessage = new TextComponent(50);
		food.spawnFood((int) ((Math.random() * 18) + 1));	
	}
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 0);
		
		camera.update();
	
		game.getBatch().setProjectionMatrix(camera.combined);
		
	    if(badFoodIngested >= 6) {
	    	gameOverSound.play();
	    	gameOverAnimation = true;
	    	badFoodIngested = 0;
	    }
	    
	    if(pointsUntilNextLevel <= 0) {
			levelUpSound.play();
			nextLevelAnimation = true;
			pointsUntilNextLevel = 15;
			gameSpeed += 100;
		}
	    
	    if(gameOverAnimation) {
	    	endGameMessage.write(
	    			game.getBatch(),
	    			(int) (Gdx.graphics.getWidth()/2 - endGameMessage.getGlyphLayout().width/2), 
					messageY,
					"BURP! Game Over.",
					Color.RED
	    	);
	    	messageY -= 300 * Gdx.graphics.getDeltaTime();
	    
	    	if(messageY <= 0) {
	    		gameOverAnimation = false;
	    		game.setScreen(new StartScreen(game));
	    	}
	    } else if(nextLevelAnimation) {
			nextLevelMessage.write(
				game.getBatch(), 
				(int) (Gdx.graphics.getWidth()/2 - nextLevelMessage.getGlyphLayout().width/2), 
				messageY,
				"You're getting healthier!",
				Color.WHITE
			);
			messageY -= 300 * Gdx.graphics.getDeltaTime();
			
			if(messageY <= 0)
				nextLevelAnimation = false;
		} else {
			messageY = Gdx.graphics.getHeight();
			
			game.getBatch().begin();
			for(int i = 0; i < badFoodIngested; i++)
				game.getBatch().draw(
						new Texture(Gdx.files.internal("bad-food/hamburger.png")), 
						(int) ((Gdx.graphics.getWidth()/6) * i), 
						(int) (Gdx.graphics.getHeight() - 250 + 70), 
						64, 64, 
						0, 0, 
						32, 32, 
						false, false
					);
			game.getBatch().end();
			
			pointsDisplay.write(
				game.getBatch(), 
				(int) (Gdx.graphics.getWidth()/2 - pointsDisplay.getGlyphLayout().width/2), 
				(int) ((Gdx.graphics.getHeight() - 50) + (pointsDisplay.getGlyphLayout().height/2)),
				String.valueOf(points),
				Color.WHITE
			);
			
			food.render(game.getBatch());
		      
			if(TimeUtils.millis() - food.lastFoodSpawnTime > MathUtils.random(500, 2000)) food.spawnFood((int) ((Math.random() * 18) + 1));
			
			Iterator<FoodSprite> iter = food.foods.iterator();
			while(iter.hasNext()) {
				FoodSprite food = iter.next();
				food.x += (food.getBaseSpeed() + gameSpeed) * Gdx.graphics.getDeltaTime();
				
				if(food.x + 64 < 0)
					iter.remove();
				
				if(player.overlaps(food)) {
					points += food.getValue();
					pointsUntilNextLevel--;
		            iter.remove();
		            
		            if(food.getBad()) {
		            	vomitSound.play();
		            	points += food.getValue();
		            	badFoodIngested++;
		            } else {
		            	eatSound.play();
		            }
		         }
			}
			
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
		for(int i = 0; i < food.foods.size(); i++)
			food.foods.get(i).getImg().dispose();
		
		pointsDisplay.dispose();
		nextLevelMessage.dispose();
		player.disposeAll();
	}

}
