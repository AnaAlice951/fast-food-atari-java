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

/**
 * Classe responsável pela renderização e lógica do jogo
 */
public class GameScreen implements Screen {

	private MyGame game;

	// Jogador
	private Player player;

	// Comidas
	private Food food;

	// Quantidade de pontos até o próximo nível
	private int pointsUntilNextLevel;

	// Define se a animação de próximo nível ou fim de jogo está sendo renderizada
	private boolean nextLevelAnimation;
	private boolean gameOverAnimation;

	// Quantidade de pontos do jogador
	private int points;

	// Posição das mensagens de próximo mível e fim de jogo no eixo Y
	private int messageY;

	// Velocidade atual do jogo com base no nível
	private int gameSpeed;

	// Quantidade de comidas ruins ingeridas
	private int badFoodIngested;
	
	// Som do jogador ingerindo uma comida
	private Sound eatSound;

	// Som de próximo nível
	private Sound levelUpSound;

	// Som de fim de jogo
	private Sound gameOverSound;

	// Som do jogador comendo um hambúrguer (comida ruim)
	private Sound vomitSound;
	
	// Visor com a quantidade de pontos
	private TextComponent pointsDisplay;

	// Texto com a mensagem de próximo nível
	private TextComponent nextLevelMessage;

	// Texto com a mensagem de fim de jogo
	private TextComponent endGameMessage;
	
	private OrthographicCamera camera;
	
	public GameScreen(MyGame game) {
		this.game = game;

		// Instancia o jogador
		player = new Player();

		// Instancia a classe responsável pela manipulação das comidas
		food = new Food();

		// Define a quantidade de pontos até o próximo nível como 15 (15 comidas por nível)
		pointsUntilNextLevel = 15;

		// Animações de próximo nível e fim de jogo estão desativadas na inicialização
		nextLevelAnimation = false;
		gameOverAnimation = false;

		// Mensagens de próximo nível e fim de jogo começam no topo da tela
		messageY = Gdx.graphics.getHeight();

		// Zera a velocidade adicional do jogo
		gameSpeed = 0;

		// Zera a quantidade de comidas ingeridas
		badFoodIngested = 0;

		// Importa os arquivos de áudio
		eatSound = Gdx.audio.newSound(Gdx.files.internal("sounds/eat.mp3"));
		levelUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelup.mp3"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
		vomitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/vomit.mp3"));

		// Cria componentes de texto vazios para as mensagens e o visor de pontos
		pointsDisplay = new TextComponent(50);
		nextLevelMessage = new TextComponent(50);
		endGameMessage = new TextComponent(50);

		// Gera a primeira comida na tela
		food.spawnFood((int) ((Math.random() * 18) + 1));	
	}
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Referência: 
	 * 
	 * LibGDX: A Simple Game
	 * @link https://libgdx.com/dev/simple-game/
	 */
	@Override
	public void render(float delta) {
		// Limpa a tela
		ScreenUtils.clear(0, 0, 0, 0);
		
		camera.update();
	
		game.getBatch().setProjectionMatrix(camera.combined);
		
		// Inicia a animação de fim de jogo e toca o som caso o número de comidas ruins ingeridas seja maior que 6
	    if(badFoodIngested >= 6) {
	    	gameOverSound.play();
	    	gameOverAnimation = true;
	    	badFoodIngested = 0;
	    }
	    
		// Inicia a animação de próximo nível e toca o som caso o número de pontos até o próximo nível seja zero
	    if(pointsUntilNextLevel <= 0) {
			levelUpSound.play();
			nextLevelAnimation = true;
			pointsUntilNextLevel = 15;
			gameSpeed += 100;
		}
	    
		//  Renderiza a animação de fim de jogo ("BURP! Game Over.")
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
				dispose();
	    	}

			// Renderiza a animação de próximo nível ("You're getting healthier!")
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

			// Renderiza a quantidade de hambúrgueres ingeridos no topo da tela, abaixo do visor de pontos
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
			
			// Renderiza o visor de pontos
			pointsDisplay.write(
				game.getBatch(), 
				(int) (Gdx.graphics.getWidth()/2 - pointsDisplay.getGlyphLayout().width/2), 
				(int) ((Gdx.graphics.getHeight() - 50) + (pointsDisplay.getGlyphLayout().height/2)),
				String.valueOf(points),
				Color.WHITE
			);
			
			// Renderiza as comidas
			food.render(game.getBatch());
		      
			// Gera uma nova comida em, no mínimo, 0.5s após a última gerada e, no máximo, 2s
			if(TimeUtils.millis() - food.lastFoodSpawnTime > MathUtils.random(500, 2000)) food.spawnFood((int) ((Math.random() * 18) + 1));
			
			Iterator<FoodSprite> iter = food.foods.iterator();
			while(iter.hasNext()) {
				FoodSprite food = iter.next();

				// Movimenta as comidas
				food.x += (food.getBaseSpeed() + gameSpeed) * Gdx.graphics.getDeltaTime();
				
				// Remove as comidas ao atingirem a borda direita da tela
				if(food.x + 64 < 0)
					iter.remove();
				
				// Verifica colisão entre o jogador e as comidas
				if(player.overlaps(food)) {
					points += food.getValue();
					pointsUntilNextLevel--;
		            iter.remove();
		            
					// Verifica se a comida ingerida é ruim
		            if(food.getBad()) {
						// Toca o som de comer hambúrguer
		            	vomitSound.play();

						// Adiciona a pontuação correspondente à comida ingerida
		            	points += food.getValue();
		            	badFoodIngested++;
		            } else {
						// Toca o som de ingerir comida
		            	eatSound.play();
		            }
		         }
			}
			
			// Move o jogador
			player.move();

			// Verifica se o jogador está tentando sair dos limites da tela
		    player.verifyOverflow();

			// Renderiza o jogador
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
