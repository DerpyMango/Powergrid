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

    public Cards getMarket() {
        return market;
    }

    public void updateMarket(Deck deck, int step, int phase) {
        if(deck.getTop()==Plant.step3) {
            step = 3;
            deck.remove(Plant.step3);
        }
        deck.moveTopTo(future);
        Plant lowest = future.getLowest();
        Cards.move(lowest,future,market);
    }

    public void updatePlantMarket(Deck deck, int step) {
        Plant highest = future.getHighest();
        Cards.moveBottom(highest,future,deck);
        deck.moveTopTo(future);
    }

    public boolean removeLowestPlantInMarket(Deck deck, int step, int lowest) {
        boolean repeat = false;
        boolean hasRemoved = false;
        Plant plantToRemove = null;
        do {
            for(Plant plant : market.getCards()) {
                if(plant.getCost()<=lowest) {
                    plantToRemove = plant;
                    hasRemoved = true;
                    break;
                }
            }
            if(plantToRemove!=null) {
                market.remove(plantToRemove);
                updatePlantMarket(deck, step);
                plantToRemove = null;
            }
        } while (repeat);
        return hasRemoved;
    }
}
