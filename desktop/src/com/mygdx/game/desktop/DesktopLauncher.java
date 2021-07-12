package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGame;

/**
 * Classe responsável por inicializar a execução do backend da LibGDX
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Modificação das dimensões da janela
		config.width = (LwjglApplicationConfiguration.getDesktopDisplayMode().width / 2) + 240;
		config.height = (LwjglApplicationConfiguration.getDesktopDisplayMode().height / 2) + 240;

		// Desativação da funcionalidade de redimensionar a janela
		config.resizable = false;

		// Início da execução do jogo a partir da classe MyGame
		new LwjglApplication(new MyGame(), config);
	}
}