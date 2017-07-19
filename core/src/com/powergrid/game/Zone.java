package com.powergrid.game;

/**
 * Created by David on 8/07/2017.
 */
public class Zone {
    private int id;
    private String name;
    private boolean active;

    public static final int numZones = 7;
    public static Zone zoneType[] = new Zone[numZones];

    public static Zone green = new Zone(0).name("green");
    public static Zone cyan = new Zone(1).name("cyan");
    public static Zone brown = new Zone(2).name("brown");
    public static Zone red = new Zone(3).name("red");
    public static Zone yellow = new Zone(4).name("yellow");
    public static Zone blue = new Zone(5).name("blue");
    public static Zone magenta = new Zone(6).name("magenta");

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
}
