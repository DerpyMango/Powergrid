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

    private void initStep3Market(Deck deck, int phase) {
        future.moveTopTo(market);
        future.moveTopTo(market);
        future.moveTopTo(market);
        future.moveTopTo(market);
        Plant lowest = market.getLowest();
        market.remove(lowest);
        deck.shuffle();
    }

    public void displayMarket(int step, Display display, int x, int y) {
        if (step<3) {
            display.text(x, y, "Future", Color.GREEN);
            future.displayCards(display, x, y + 1);
            display.text(x, y + 5, "Actual", Color.GREEN);
            market.displayCardsNum(display, x, y + 6);
        } else {
            display.text(x, y, "Actual", Color.GREEN);
            market.displayCardsNum(display, x, y + 1);
        }
    }

    public Cards getMarket() {
        return market;
    }

    public boolean updateMarket(Deck deck, int step, int phase) {
        boolean step3 = false;
        if(deck.getTop()==Plant.step3) {
            deck.remove(Plant.step3);
            step3 = true;
            initStep3Market(deck,phase);
        }
        if (step<3 && !step3) {
            deck.moveTopTo(future);
            Plant lowest = future.getLowest();
            Cards.move(lowest, future, market);
        } else {
            deck.moveTopTo(market);
        }
        return step3;
    }

    public void updatePlantMarket(Deck deck, int step) {
        if(step<3) {
            Plant highest = future.getHighest();
            Cards.moveBottom(highest, future, deck);
            deck.moveTopTo(future);
        } else {
            Plant lowest = market.getLowest();
            market.remove(lowest);
        }
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

    public void removeLowestPlant(Deck deck, int step) {
        int lowest = 99;
        Plant plantToRemove = null;
        for(Plant plant : market.getCards()) {
            if(plant.getCost()<=lowest) {
                lowest = plant.getCost();
                plantToRemove = plant;
            }
        }
        market.remove(plantToRemove);
        updatePlantMarket(deck, step);
    }
}
