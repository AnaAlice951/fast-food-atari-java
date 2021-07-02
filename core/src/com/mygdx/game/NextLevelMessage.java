package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public class NextLevelMessage {
	private int currentY = 0;
	private int pointsUntilNextLevel;
	private GlyphLayout glyphLayout = new GlyphLayout();
	private BitmapFont font = new BitmapFont();
	private Rectangle textArea = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
	public NextLevelMessage(int pointsUntilNextLevel) {
		this.pointsUntilNextLevel = pointsUntilNextLevel;
	}
	
	public void render(SpriteBatch batch) {
		batch.begin();
		drawText(font, batch, "You're getting fattier!", textArea, currentY);
		currentY -= 400 * Gdx.graphics.getDeltaTime();
		
		if(currentY < 0) {
			pointsUntilNextLevel = 0;
		}
		
		batch.end();
	}
	
	private void drawText(BitmapFont font, SpriteBatch batch, String text, Rectangle bounds, int currentY) {
	        glyphLayout.setText(font, text, Color.BLACK, bounds.width, Align.center, true);
	        font.getData().setScale(4);
	        font.draw(
                batch,
                text,
                bounds.x,
                bounds.y + bounds.height / 2f + glyphLayout.height / 2f,
                bounds.width,
                Align.center,
                true
	        );
	    }
}
