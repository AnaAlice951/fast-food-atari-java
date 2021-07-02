package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Player extends Rectangle {
	private Animation<Texture> mouthAnimation;
	private Texture[] playerFrames = new Texture[6];
	private boolean xFlipped, yFlipped;
	static float stateTime = 0f;
	
	public Player() {
		width = 64;
		height = 64;
		x = Gdx.graphics.getWidth() - (width * 2);
		y = (Gdx.graphics.getHeight() / 2) - (height / 2);
		xFlipped = false;
		yFlipped = false;
		
		for(int i = 0; i < 6; i++)
			playerFrames[i] = new Texture(Gdx.files.internal("player-sprites/sprite_" + i + ".png"));
		
		mouthAnimation = new Animation<Texture>(0.075f, playerFrames);
	}
	
	public boolean isXFlipped() {
		return xFlipped;
	}

	public void setXFlipped(boolean xFlipped) {
		this.xFlipped = xFlipped;
	}

	public boolean isYFlipped() {
		return yFlipped;
	}

	public void setYFlipped(boolean yFlipped) {
		this.yFlipped = yFlipped;
	}

	public void render(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		
		Texture currentFrame = mouthAnimation.getKeyFrame(stateTime, true);
		batch.begin();
		batch.draw(
			currentFrame, 
			x, 
			y, 
			width, height, 
			0, 0, 
			(int) width, (int) height, 
			xFlipped, yFlipped
		);
		batch.end();
	}
	
	public void verifyOverflow() {
		if(x < 0)
			x = 0;
		
		if(x > Gdx.graphics.getWidth() - width)
			x = Gdx.graphics.getWidth() - width;
		
		if(y > Gdx.graphics.getHeight() - 250)
			y = Gdx.graphics.getHeight() - 250;
		
		if(y < 0)
			y = 0;
	}
	
	public void move() {
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			x -= 500 * Gdx.graphics.getDeltaTime();
			setXFlipped(false);
		}
			
	    if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
	    	x += 500 * Gdx.graphics.getDeltaTime();
	    	setXFlipped(true);
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
	    	y += 500 * Gdx.graphics.getDeltaTime();
	    
	    
	    if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
	    	y -= 500 * Gdx.graphics.getDeltaTime();
	}
	
	public void disposeAll() {
		
		// Dispose the animation sprites
		for(Texture frame: playerFrames) {
			frame.dispose();
		}
	}
}