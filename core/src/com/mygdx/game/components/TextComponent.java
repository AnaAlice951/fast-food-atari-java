package com.mygdx.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class TextComponent {
	private BitmapFont font;
	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;
	private GlyphLayout glyphLayout;
	
	public TextComponent(int size) {
		font = new BitmapFont();
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Tourney-Black.ttf"));
		freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		freeTypeFontParameter.size = size;
		fontGenerator.generateData(freeTypeFontParameter);
		font = fontGenerator.generateFont(freeTypeFontParameter);
		
		glyphLayout  = new GlyphLayout();

	}
	
	public GlyphLayout getGlyphLayout() {
		return glyphLayout;
	}

	public void setGlyphLayout(GlyphLayout glyphLayout) {
		this.glyphLayout = glyphLayout;
	}

	public void write(SpriteBatch batch, int x, int y, String s, Color color) {
		glyphLayout.setText(font, s);
		batch.begin();
		font.setColor(color);
		font.draw(batch, glyphLayout, x, y);
		batch.end();
	}
	
	public void dispose() {
		fontGenerator.dispose();
		font.dispose();
	}
}
