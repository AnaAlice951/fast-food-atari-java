package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Food{
	public ArrayList<FoodSprite> foods = new ArrayList<FoodSprite>();
	public Texture img;
	public long lastFoodTime;
	 
	public class FoodSprite{
	public Texture img;
	public Rectangle area = new Rectangle();
	public boolean bad = false;
		
		public FoodSprite(Texture img, int x, int y, int width, int height, boolean bad) {
			this.img = img;
			this.area.x = x;
			this.area.y = y;
			this.area.width = width;
			this.area.height = height;
			this.bad = bad;
		}
	}
	
	
	
	
	public void spawnFood(int i) {
		FoodSprite food;
		if (MathUtils.random(0, 3) == 1) {
			food = new FoodSprite(new Texture(Gdx.files.internal("bad-food/1.png")), 0 , MathUtils.random(0, Gdx.graphics.getHeight()-64), 64,64, true);
		}
		else {
			food = new FoodSprite(new Texture(Gdx.files.internal("good-food/"+i+".png")), 0 , MathUtils.random(0, Gdx.graphics.getHeight()-64), 64,64, false);
		}
		
		foods.add(food);
		lastFoodTime = TimeUtils.nanoTime();
	}
}
