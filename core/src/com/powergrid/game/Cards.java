package com.powergrid.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 16/07/2017.
 */
public class Cards {
    protected List<Plant> cards = new ArrayList<>();

    public void displayCards() {
        for(Plant plant : cards) {
            plant.printPlant();
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
}
