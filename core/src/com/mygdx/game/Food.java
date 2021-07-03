package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Food{
	public ArrayList<FoodSprite> foods = new ArrayList<FoodSprite>();
	public Texture img;
	public long lastFoodSpawnTime;
	 
	@SuppressWarnings("serial")
	public class FoodSprite extends Rectangle {
		public Texture img;
		private int spriteWidth, spriteHeight;
		public boolean bad = false;
		public int baseSpeed;
		
		public FoodSprite(Texture img, boolean bad, int baseSpeed) {
			this.img = img;
			this.baseSpeed = baseSpeed;
			x = 0;
			y = MathUtils.random(0, Gdx.graphics.getHeight() - 250);
			width = 64;
			height = 64;
			spriteWidth = 32;
			spriteHeight = 32;
			this.bad = bad;
		}
	}
	
	public void spawnFood(int i) {
		FoodSprite food;
		if (MathUtils.random(0, 3) == 1) {
			food = new FoodSprite(new Texture(Gdx.files.internal("bad-food/hamburger.png")), true, MathUtils.random(200, 400));
		} else {
			food = new FoodSprite(new Texture(Gdx.files.internal("good-food/" + i + ".png")), false, MathUtils.random(200, 400));
		}
		
		foods.add(food);
		lastFoodSpawnTime = TimeUtils.millis();
	}
	
	public void render(SpriteBatch batch) {
		batch.begin();
		for(int i = 0; i < foods.size(); i++) {
			batch.draw(
				foods.get(i).img, 
				foods.get(i).x, 
				foods.get(i).y, 
				foods.get(i).width, foods.get(i).height, 
				0, 0, 
				foods.get(i).spriteWidth, foods.get(i).spriteHeight, 
				false, false
			);
		}
		batch.end();
	}
}
