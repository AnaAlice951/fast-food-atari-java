package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Classe responsável por manupular as comidas no jogo
 */
public class Food {
	// Unidades de comida
	public ArrayList<FoodSprite> foods = new ArrayList<FoodSprite>();

	// Textura da comida (imagem)
	private Texture img;

	// Tempo da última geração de comida
	public long lastFoodSpawnTime;
	 
	/**
	 * Classe responsável por gerenciar as propriedades visuais e lógicas de cada comida
	 */
	@SuppressWarnings("serial")
	public class FoodSprite extends Rectangle {
		// Textura da comida (imagem)
		private Texture img;

		// Largura e altura da imagem
		private int spriteWidth, spriteHeight;

		// Define se a comida é ruim (hambúrguer)
		private boolean bad = false;

		// Define a velocidade base de todas as comidas
		private int baseSpeed;

		// Define o valor (pontuação) de cada comida
		private int value;
		
		public FoodSprite(Texture img, boolean bad, int baseSpeed, int value) {
			this.img = img;
			this.baseSpeed = baseSpeed;

			// Posição X e Y da comida (propriedades extendidas da classe Rectangle)
			x = 0;
			y = MathUtils.random(0, Gdx.graphics.getHeight() - 250);

			// Largura e altura do hitbox da comida (propriedades extendidas da classe Rectangle)
			width = 64;
			height = 64;
			
			spriteWidth = 32;
			spriteHeight = 32;

			this.bad = bad;
			this.value = value;
		}
		
		public boolean getBad() {
			return this.bad;
		}
		
		public int getBaseSpeed() {
			return this.baseSpeed;
		}
		
		public Texture getImg() {
			return this.img;
		}
		
		public int getValue() {
			return this.value;
		}
	}
	
	/**
	 * Gera uma nova comida na tela
	 * 
	 * @param int índice que define e correlaciona pontuação e tipo da comida
	 */
	public void spawnFood(int i) {
		FoodSprite food;

		// As comidas recebem uma posição X = 0 e Y aleatório dentro dos limites da tela
		// Comidas ruins tem probabilidade de 25% de aparecerem
		if (MathUtils.random(0, 3) == 1) {
			food = new FoodSprite(new Texture(Gdx.files.internal("bad-food/hamburger.png")), true, MathUtils.random(200, 400), 0);
		} else {
			food = new FoodSprite(new Texture(Gdx.files.internal("good-food/" + i + ".png")), false, MathUtils.random(200, 400), i);
		}
		
		foods.add(food);

		// Atualiza a variável com o tempo da última geração de comida
		lastFoodSpawnTime = TimeUtils.millis();
	}
	
	/**
	 * Renderiza todas as comidas na tela
	 * 
	 * @param SpriteBatch sprite batch
	 */
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
