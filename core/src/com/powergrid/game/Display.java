package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Display {
    private SpriteBatch batch;
    private BitmapFont font;
    private int height;

    public static final int SCALE = 8;

    public Display(SpriteBatch batch, BitmapFont font, int windowHeight) {
        this.batch = batch;
        this.font = font;
        this.height = windowHeight;
    }

    public void text(int x, int y, String text, Color colour) {
        int xx = convertX(x);
        int yy = convertY(y);
        font.setColor(colour);
        font.draw(batch,text,xx,yy);
    }

    private int convertX(int x) {
        return x* SCALE;
    }

    private int convertY(int y) {
        return height -y* SCALE;
    }
}
