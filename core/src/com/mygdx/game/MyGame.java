package com.mygdx.game;

import java.util.Iterator;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Food.FoodSprite;
import com.mygdx.game.screens.StartScreen;

public class MyGame extends Game {
	
	private SpriteBatch batch;
	Food Allfoods = new Food();

	@Override
	public void create () {
		batch = new SpriteBatch();
		Allfoods.spawnFood((int) ((Math.random() * 18) + 1));	
		this.setScreen(new StartScreen(this));
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public void render () {
super.render();
            batch.begin();
      for(int i=0;i<Allfoods.foods.size();i++)
         batch.draw(Allfoods.foods.get(i).img, Allfoods.foods.get(i).area.x,  Allfoods.foods.get(i).area.y);
      batch.end();
      
      if(TimeUtils.nanoTime() - Allfoods.lastFoodTime > 1000000000) Allfoods.spawnFood((int) ((Math.random() * 18) + 1));

      
      Iterator<FoodSprite> iter = Allfoods.foods.iterator();
      while(iter.hasNext()) {
         FoodSprite food = iter.next();
         food.area.x += 200 * Gdx.graphics.getDeltaTime();
         if(food.area.x + 64 < 0)
        	 iter.remove();
      }

   }

	@Override
	public void dispose () {
		for(int i=0;i<Allfoods.foods.size();i++)
			Allfoods.foods.get(i).img.dispose();
		batch.dispose();
	}
}