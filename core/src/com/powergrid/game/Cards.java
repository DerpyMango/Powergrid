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

    public void displayCards(Display display, int x, int y) {
        int yy = y;
        for(Plant plant : cards) {
            plant.displayPlant(display,x+1,yy);
            yy++;
        }
    }

    public void displayCardsNum(Display display, int x, int y) {
        int yy = y;
        int i = 1;
        for(Plant plant : cards) {
            plant.displayPlantNum(i,display,x+1,yy);
            yy++;
            i++;
        }
    }

    public static void move(Plant plant, Cards from, Cards to) {
        from.remove(plant);
        to.add(plant);
    }

    public static void moveBottom(Plant plant, Cards from, Cards to) {
        from.remove(plant);
        to.addBottom(plant);
    }

    public void remove(Plant plant) {
        cards.remove(plant);
    }

    public void add(Plant plant) {
        cards.add(plant);
    }

    public void addBottom(Plant plant) {cards.add(cards.size()-1,plant);}

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

    public Plant getLowest() {
        int cost = 99;
        Plant lowest = null;
        for(Plant plant : cards) {
            if(plant.getCost()<cost) {
                lowest = plant;
                cost = plant.getCost();
            }
        }
        return lowest;
    }

    public Plant getHighest() {
        int cost = 0;
        Plant highest = null;
        for(Plant plant : cards) {
            if(plant.getCost()>cost) {
                highest = plant;
                cost = plant.getCost();
            }
        }
        return highest;
    }
}
