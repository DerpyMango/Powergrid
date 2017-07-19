package com.powergrid.game;


import java.awt.*;

/**
 * Created by David on 8/07/2017.
 */
public class Player {
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

    public void display() {
        System.out.format("Name: %s Id: %d Electros: %d\n",name,id,electros);
    }
}
