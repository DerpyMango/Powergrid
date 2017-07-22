package com.powergrid.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by David on 8/07/2017.
 */
public class Player implements Comparable<Player>{
    private String name;
    private int id;
    private int electros = 50;
    private Cards plants = new Cards();
    private int numCity = 0;
    private int turnOrder = 0;
    private Color colour;

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public Player id(int id) {
        this.id = id;
        return this;
    }

    public Player electros(int electros) {
        this.electros = electros;
        return this;
    }

    public Player colour(Color colour) {
        this.colour = colour;
        return this;
    }

    public void display(SpriteBatch batch, BitmapFont font, int x, int y) {
        String desc = String.format("%d. %s, Electros: %d, Num Plants: %d, Num Cities: %d\n",turnOrder,name,electros,plants.getCards().size(),numCity);
        font.setColor(colour);
        font.draw(batch,desc,x,y);
        plants.displayCardsNum(batch,font,x,y-8);
    }

    public Cards getPlants() {
        return plants;
    }

    public int getNumCity() {
        return numCity;
    }

    public int getHighestPlantNum() {
        if(plants.getCards().size()==0)
            return 0;
        else
            return plants.getCards().stream().map(Plant::getCost).max(Integer::compare).get();
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    @Override
    public int compareTo(Player other) {
        int byCity = other.getNumCity() - numCity;
        if (byCity==0)
            return other.getHighestPlantNum() - getHighestPlantNum();
        else
            return byCity;
    }

    public void setNumCity(int numCity) {
        this.numCity = numCity;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getElectros() {
        return electros;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public Color getColour() {
        return colour;
    }
}
