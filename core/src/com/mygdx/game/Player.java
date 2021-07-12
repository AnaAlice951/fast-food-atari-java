package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Classe responsável por manipular o jogador
 * 
 * Referências:
 * 
 * LibGDX: Official Wiki - 2D Animation
 * @link https://github.com/libgdx/libgdx/wiki/2D-Animation
 * 
 * LibGDX: Official Documentation - Interface Batch
 * @link https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/Batch.html
 */
@SuppressWarnings("serial")
public class Player extends Rectangle {

	// Animação da boca
	private Animation<Texture> mouthAnimation;

	// Frames da animação
	private Texture[] playerFrames = new Texture[6];

	// Largura e altura da imagem do jogador
	private int spriteWidth, spriteHeight;

	// Define o lado para o qual o jogador está olhando
	private boolean xFlipped, yFlipped;

	// "Delta time" da renderizaçao
	private float stateTime = 0f;
	
	public Player() {
		// Posição X e Y do jogador (propriedades extendidas da classe Rectangle)
		x = Gdx.graphics.getWidth() - (width * 2);
		y = (Gdx.graphics.getHeight() / 2) - (height / 2);

		// Largura e altura do hitbox do jogador (propriedades extendidas da classe Rectangle)
		width = 64;
		height = 64;

		spriteWidth = 64;
		spriteHeight = 64;
		xFlipped = false;
		yFlipped = false;
		
		// Importa os sprites para cada frame da animação
		for(int i = 0; i < 6; i++)
			playerFrames[i] = new Texture(Gdx.files.internal("player-sprites/sprite_" + i + ".png"));
		
		// Define os frames da animação e um tempo de 0.075s entre cada quadro
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

	/**
	 * Renderiza o jogador tela
	 * 
	 * @param SpriteBatch sprite batch
	 */
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
			spriteWidth, spriteHeight, 
			xFlipped, yFlipped
		);
		batch.end();
	}
	
	/**
	 * Verifica a posição e impede o jogador de sair dos limites da tela
	 */
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
	
	/**
	 * Atualiza as posições X e Y do jogador conforme a entrada do usuário
	 */
	public void move() {
		// Confere se o jogador se moveu para a esquerda
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			x -= 500 * Gdx.graphics.getDeltaTime();

			// Mantém a perspectiva padrão do jogador: olhando para a esquerda
			setXFlipped(false);
		}

		// Confere se o jogador se moveu para a direita
	    if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
	    	x += 500 * Gdx.graphics.getDeltaTime();

			// Muda a perspectiva do jogador: olhando para a direita
	    	setXFlipped(true);
	    }
	    
		// Confere se o jogador se moveu para cima
	    if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
	    	y += 500 * Gdx.graphics.getDeltaTime();
	    
		// Confere se o jogador se moveu para baixo
	    if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
	    	y -= 500 * Gdx.graphics.getDeltaTime();
	}
	
	public void disposeAll() {
		// Remove objetos inutilizados da memória
		for(Texture frame: playerFrames) {
			frame.dispose();
		}
	}
}