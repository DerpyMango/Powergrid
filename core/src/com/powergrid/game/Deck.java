package com.powergrid.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by David on 9/07/2017.
 */
public class Deck extends Cards {

    Random rand = new Random();

    public void initDeck(int numPlayers) {
        add(Plant.p03);
        add(Plant.p04);
        add(Plant.p05);
        add(Plant.p06);
        add(Plant.p07);
        add(Plant.p08);
        add(Plant.p09);
        add(Plant.p10);
        add(Plant.p13);
        addShuffled(numPlayers);
        add(Plant.step3);
    }

    private void addShuffled(int numPlayers) {
        int toChose = 42-10+1;
        if (numPlayers<5) toChose -= 4;
        if (numPlayers<4) toChose -= 4;

        for (int i=0; i<toChose; i++) {
            int plant;
            do {
                plant = rand.nextInt(42-10+1) + 10;
            } while (isIn(Plant.getPlant(plant)));
            add(Plant.getPlant(plant));
        }
    }

    public Plant getTop() {
        return cards.get(0);
    }
}
