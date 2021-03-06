package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;

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
    private boolean passed = false;

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

    public void display(Display display, int x, int y) {
        String desc = String.format("%c. %s, Electros: %d, Num Plants: %d, Num Cities: %d/%d/%d",turnOrder+'A'-1,name,electros,plants.getCards().size(),getMaxNumberOfCitiesCanPower(),getNumberOfCitiesCanPower(),numCity);
        display.text(x,y,desc,colour);
        plants.displayCardsNum(display,x,y+1);
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

    public int getNumberOfCitiesCanPower() {
        int numCities = 0;
        for(Plant plant : getPlants().getCards()) {
            if(enoughResources(plant)) {
                numCities+=plant.getNumCity();
            }
        }
        return numCities;
    }

    public int getMaxNumberOfCitiesCanPower() {
        int numCities = 0;
        for(Plant plant : getPlants().getCards()) {
            numCities+=plant.getNumCity();
        }
        return numCities;
    }

    public boolean enoughResources(Plant plant) {
        Resource resource = plant.getResource();
        if(resource==Resource.coal && plant.getCoal()>=plant.getNumResource()) return true;
        if(resource==Resource.coaloil && (plant.getCoal() + plant.getOil())>=plant.getNumResource()) return true;
        if(resource==Resource.oil && plant.getOil()>=plant.getNumResource()) return true;
        if(resource==Resource.trash && plant.getTrash()>=plant.getNumResource()) return true;
        if(resource==Resource.nuclear && plant.getNuclear()>=plant.getNumResource()) return true;
        if(resource==Resource.wind || resource==Resource.fusion) return true;

        return false;
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

    public boolean spend(int amount) {
        if(electros<amount) return true;
        electros -= amount;
        return false;
    }

    public boolean hasPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void incNumCity() {
        numCity+=1;
    }
}
