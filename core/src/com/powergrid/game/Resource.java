package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by David on 8/07/2017.
 */
public class Resource {
    private int id;
    private String name;
    private Color colour;

    public static final int numResources = 8;
    public static Resource resourceType[] = new Resource[numResources];

    public static final Resource step3 = new Resource(0).name("step3").colour(Color.GRAY);
    public static final Resource coal = new Resource(1).name("coal").colour(Color.BROWN);
    public static final Resource oil = new Resource(2).name("oil").colour(Color.BLACK);
    public static final Resource coaloil = new Resource(3).name("coaloil").colour(Color.ORANGE);
    public static final Resource wind = new Resource(4).name("wind").colour(Color.GREEN);
    public static final Resource trash = new Resource(5).name("trash").colour(Color.YELLOW);
    public static final Resource nuclear = new Resource(6).name("nuclear").colour(Color.RED);
    public static final Resource fusion = new Resource(7).name("fusion").colour(Color.CYAN);

    public Resource(int id) {
        if (resourceType[id] != null) {
            System.out.println("Resource already registered");
            System.exit(1);
        }
        resourceType[id] = this;
        this.id = id;
    }

    public Resource name(String name) {
        this.name = name;
        return this;
    }

    public Resource colour(Color colour) {
        this.colour = colour;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public static int getNumResources() {
        return numResources;
    }
}
