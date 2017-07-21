package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by David on 9/07/2017.
 */
public class Market {
    private int step = 1;
    private Cards future;
    private Cards market;

    public void initMarket(Deck deck) {
        market = new Cards();
        future = new Cards();

        deck.moveTopTo(market);
        deck.moveTopTo(market);
        deck.moveTopTo(market);
        deck.moveTopTo(market);

        deck.moveTopTo(future);
        deck.moveTopTo(future);
        deck.moveTopTo(future);
        deck.moveTopTo(future);
    }

    public void displayMarket(int step, SpriteBatch batch, BitmapFont font, int x, int y) {
        font.setColor(Color.GREEN);
        font.draw(batch,"Future",x,y);
        future.displayCards(batch,font,x,y-8);
        font.setColor(Color.GREEN);
        font.draw(batch,"Market",x,y-40);
        market.displayCardsNum(batch,font,x,y-40-8);
    }
}
