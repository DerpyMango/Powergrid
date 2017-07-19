package com.powergrid.game;

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

    public void displayMarket() {
        System.out.println("Market");
        market.displayCards();
        System.out.println("Future");
        future.displayCards();
    }
}
