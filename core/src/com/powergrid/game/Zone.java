package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by David on 8/07/2017.
 */
public class Zone {
    private int id;
    private String name;
    private boolean active = false;
    private Color colour;

    public static final int numZones = 7;
    public static Zone zoneType[] = new Zone[numZones];

    public static Zone green = new Zone(0).name("green").colour(Color.GREEN);
    public static Zone cyan = new Zone(1).name("cyan").colour(Color.CYAN);
    public static Zone brown = new Zone(2).name("brown").colour(Color.BROWN);
    public static Zone red = new Zone(3).name("red").colour(Color.RED);
    public static Zone yellow = new Zone(4).name("yellow").colour(Color.YELLOW);
    public static Zone blue = new Zone(5).name("blue").colour(Color.BLUE);
    public static Zone magenta = new Zone(6).name("magenta").colour(Color.MAGENTA);

    public Zone(int id) {
        if (zoneType[id] != null) {
            System.out.println("Zone already registered");
            System.exit(1);
        }
        zoneType[id] = this;
        this.id = id;
    }

    public Zone name(String name) {
        this.name = name;
        return this;
    }

    public Zone active(Boolean active) {
        this.active = active;
        return this;
    }

    public Zone colour(Color colour) {
        this.colour = colour;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Color getColour() {
        return colour;
    }

    public int getId() {
        return id;
    }


}
