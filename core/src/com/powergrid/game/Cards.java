package com.powergrid.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 16/07/2017.
 */
public class Cards {
    protected List<Plant> cards = new ArrayList<>();

    public void displayCards(SpriteBatch batch, BitmapFont font, int x, int y) {
        int yy = y;
        for(Plant plant : cards) {
            plant.displayPlant(batch,font,x+8,yy);
            yy-=8;
        }
    }

    public void displayCardsNum(SpriteBatch batch, BitmapFont font, int x, int y) {
        int yy = y;
        int i = 1;
        for(Plant plant : cards) {
            plant.displayPlantNum(i,batch,font,x+8,yy);
            yy-=8;
            i++;
        }
    }

    public static void move(Plant plant, Cards from, Cards to) {
        from.remove(plant);
        to.add(plant);
    }

    public void remove(Plant plant) {
        cards.remove(plant);
    }

    public void add(Plant plant) {
        cards.add(plant);
    }

    public boolean isIn(Plant plant) {
        return cards.contains(plant);
    }

    public void moveTopTo(Cards other) {
        Plant top = cards.get(0);
        cards.remove(top);
        other.add(top);
    }

    public List<Plant> getCards() {
        return cards;
    }
}
