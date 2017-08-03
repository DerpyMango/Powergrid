package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    public static final Resource oil = new Resource(2).name("oil").colour(Color.DARK_GRAY);
    public static final Resource coaloil = new Resource(3).name("coaloil").colour(Color.ORANGE);
    public static final Resource wind = new Resource(4).name("wind").colour(Color.GREEN);
    public static final Resource trash = new Resource(5).name("trash").colour(Color.YELLOW);
    public static final Resource nuclear = new Resource(6).name("nuclear").colour(Color.RED);
    public static final Resource fusion = new Resource(7).name("fusion").colour(Color.CYAN);

    public static final int maxCoal = 24;
    public static final int maxOil = 24;
    public static final int maxTrash = 24;
    public static final int maxNuclear = 24;

    public static final int coalPrice[] = {8,8,8,7,7,7,6,6,6,5,5,5,4,4,4,3,3,3,2,2,2,1,1,1};
    public static final int oilPrice[] = {8,8,8,7,7,7,6,6,6,5,5,5,4,4,4,3,3,3,2,2,2,1,1,1};
    public static final int trashPrice[] = {8,8,8,7,7,7,6,6,6,5,5,5,4,4,4,3,3,3,2,2,2,1,1,1};
    public static final int nuclearPrice[] = {16,14,12,10,8,7,6,5,4,3,2,1};

    public static final int coalReplenish[][] = {{},{},{3,4,3},{4,5,3},{5,6,4},{5,7,5},{7,9,6}};
    public static final int oilReplenish[][] = {{},{},{2,2,4},{2,3,4},{3,4,5},{4,5,6},{5,6,7}};
    public static final int trashReplenish[][] = {{},{},{1,2,3},{1,2,3},{2,3,4},{3,3,5},{3,5,6}};
    public static final int nuclearReplenish[][] = {{},{},{1,1,1},{1,1,1},{1,2,2},{2,3,2},{2,3,3}};

    public static void displayResources(Display display, int c, int o, int t, int n, int x, int y) {
        displayResourceTrack(display,"Coal",c,coalPrice,coal.getColour(), x, y);
        displayResourceTrack(display,"Oil",o,oilPrice,oil.getColour(),x,y+1);
        displayResourceTrack(display,"Trash",t,trashPrice,trash.getColour(),x,y+2);
        displayResourceTrack(display,"Nuke",n,nuclearPrice,nuclear.getColour(),x,y+3);
    }

    public static void displayResourceTrack(Display display, String name, int amount, int price[], Color colour, int x, int y) {
        display.text(x,y,name+": ",colour);
        for(int i=0;i<amount;i++) {
            display.text(x+2*(amount-i)+4,y,""+price[i],colour);
        }
    }

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

    public String getPaddedName() {
        return String.format("%5s",name);
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
