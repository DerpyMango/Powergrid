package com.powergrid.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

/**
 * Created by David on 8/07/2017.
 */
public class Plant {
    private int id;
    private int cost;
    private int numCity;
    private int numResource;
    private Resource resource;
    private int coal = 0;
    private int oil = 0;
    private int trash = 0;
    private int nuclear = 0;

    public static final int numPlants = 43;
    public static Plant plantType[] = new Plant[numPlants];

    public static Plant step3 = new Plant(0).cost(0).numResource(0).resource(Resource.step3).numCity(0);
    public static Plant p03 = new Plant(1).cost(3).numResource(2).resource(Resource.oil).numCity(1);
    public static Plant p04 = new Plant(2).cost(4).numResource(2).resource(Resource.coal).numCity(1);
    public static Plant p05 = new Plant(3).cost(5).numResource(2).resource(Resource.coaloil).numCity(1);
    public static Plant p06 = new Plant(4).cost(6).numResource(1).resource(Resource.trash).numCity(1);
    public static Plant p07 = new Plant(5).cost(7).numResource(3).resource(Resource.oil).numCity(2);
    public static Plant p08 = new Plant(6).cost(8).numResource(3).resource(Resource.coal).numCity(2);
    public static Plant p09 = new Plant(7).cost(9).numResource(1).resource(Resource.oil).numCity(1);
    public static Plant p10 = new Plant(8).cost(10).numResource(2).resource(Resource.coal).numCity(2);
    public static Plant p13 = new Plant(9).cost(13).numResource(0).resource(Resource.wind).numCity(1);
    public static Plant p11 = new Plant(10).cost(11).numResource(1).resource(Resource.nuclear).numCity(2);
    public static Plant p12 = new Plant(11).cost(12).numResource(2).resource(Resource.coaloil).numCity(2);
    public static Plant p14 = new Plant(12).cost(14).numResource(2).resource(Resource.trash).numCity(2);
    public static Plant p15 = new Plant(13).cost(15).numResource(2).resource(Resource.coal).numCity(3);
    public static Plant p16 = new Plant(14).cost(16).numResource(2).resource(Resource.oil).numCity(3);
    public static Plant p17 = new Plant(15).cost(17).numResource(1).resource(Resource.nuclear).numCity(2);
    public static Plant p18 = new Plant(16).cost(18).numResource(0).resource(Resource.wind).numCity(2);
    public static Plant p19 = new Plant(17).cost(19).numResource(2).resource(Resource.trash).numCity(3);
    public static Plant p20 = new Plant(18).cost(20).numResource(3).resource(Resource.coal).numCity(5);
    public static Plant p21 = new Plant(19).cost(21).numResource(2).resource(Resource.coaloil).numCity(4);
    public static Plant p22 = new Plant(20).cost(22).numResource(0).resource(Resource.wind).numCity(2);
    public static Plant p23 = new Plant(21).cost(23).numResource(1).resource(Resource.nuclear).numCity(3);
    public static Plant p24 = new Plant(22).cost(24).numResource(2).resource(Resource.trash).numCity(4);
    public static Plant p25 = new Plant(23).cost(25).numResource(2).resource(Resource.coal).numCity(5);
    public static Plant p26 = new Plant(24).cost(26).numResource(2).resource(Resource.oil).numCity(5);
    public static Plant p27 = new Plant(25).cost(27).numResource(0).resource(Resource.wind).numCity(3);
    public static Plant p28 = new Plant(26).cost(28).numResource(1).resource(Resource.nuclear).numCity(4);
    public static Plant p29 = new Plant(27).cost(29).numResource(1).resource(Resource.coaloil).numCity(4);
    public static Plant p30 = new Plant(28).cost(30).numResource(3).resource(Resource.trash).numCity(6);
    public static Plant p31 = new Plant(29).cost(31).numResource(3).resource(Resource.coal).numCity(6);
    public static Plant p32 = new Plant(30).cost(32).numResource(3).resource(Resource.oil).numCity(6);
    public static Plant p33 = new Plant(31).cost(33).numResource(0).resource(Resource.wind).numCity(4);
    public static Plant p34 = new Plant(32).cost(34).numResource(1).resource(Resource.nuclear).numCity(5);
    public static Plant p35 = new Plant(33).cost(35).numResource(1).resource(Resource.oil).numCity(5);
    public static Plant p36 = new Plant(34).cost(36).numResource(3).resource(Resource.coal).numCity(7);
    public static Plant p37 = new Plant(35).cost(37).numResource(0).resource(Resource.wind).numCity(4);
    public static Plant p38 = new Plant(36).cost(38).numResource(3).resource(Resource.trash).numCity(7);
    public static Plant p39 = new Plant(37).cost(39).numResource(1).resource(Resource.nuclear).numCity(6);
    public static Plant p40 = new Plant(38).cost(40).numResource(2).resource(Resource.oil).numCity(6);
    public static Plant p42 = new Plant(39).cost(42).numResource(2).resource(Resource.coal).numCity(6);
    public static Plant p44 = new Plant(40).cost(44).numResource(0).resource(Resource.wind).numCity(5);
    public static Plant p46 = new Plant(41).cost(46).numResource(3).resource(Resource.coaloil).numCity(7);
    public static Plant p50 = new Plant(42).cost(50).numResource(0).resource(Resource.fusion).numCity(6);

    public Plant(int id) {
        if (plantType[id] != null) {
            System.out.println("Plant already registered");
            System.exit(1);
        }
        plantType[id] = this;
        this.id = id;
    }

    private Plant cost(int cost) {
        this.cost = cost;
        return this;
    }

    private Plant numCity(int cities) {
        this.numCity = cities;
        return this;
    }

    private Plant numResource(int numResource) {
        this.numResource = numResource;
        return this;
    }

    private Plant resource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public static Plant getPlant(int plant) {
        return plantType[plant];
    }

    public int getNumCity() {
        return numCity;
    }

    public int getNumResource() {
        return numResource;
    }

    public Resource getResource() {
        return resource;
    }

    public void displayPlant(SpriteBatch batch, BitmapFont font, int x, int y) {
        StringBuilder plantDesc = new StringBuilder(String.format("[%d] %s Fuel: %d Cities: %d",cost,resource.getName(),numResource,numCity));
        if (coal>0)  plantDesc.append(" coal: "+coal);
        if (oil>0)  plantDesc.append(" oil: "+oil);
        if (trash>0)  plantDesc.append(" trash: "+trash);
        if (nuclear>0)  plantDesc.append(" nuke: "+nuclear);
        font.setColor(resource.getColour());
        font.draw(batch,plantDesc,x,y);
    }

    public void displayPlantNum(int i, SpriteBatch batch, BitmapFont font, int x, int y) {
        font.setColor(resource.getColour());
        font.draw(batch,i+".",x,y);
        displayPlant(batch,font,x+16,y);
    }

    public void displayPlantBid(SpriteBatch batch, BitmapFont font, int x, int y, int bid) {
        StringBuilder plantDesc = new StringBuilder(String.format("[%d] %s Fuel: %d Cities: %d Bid: %d", cost, resource.getName(), numResource, numCity, bid));
        font.setColor(resource.getColour());
        font.draw(batch,plantDesc,x,y);
    }

    public int getCost() {
        return cost;
    }

    public int getCoal() {
        return coal;
    }

    public void setCoal(int coal) {
        this.coal = coal;
    }

    public int getOil() {
        return oil;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public int getTrash() {
        return trash;
    }

    public void setTrash(int trash) {
        this.trash = trash;
    }

    public int getNuclear() {
        return nuclear;
    }

    public void setNuclear(int nuclear) {
        this.nuclear = nuclear;
    }

    public void incCoal() {
        coal+=1;
    }

    public void incOil() {
        oil+=1;
    }

    public void incTrash() {
        trash+=1;
    }

    public void incNuclear() {
        nuclear+=1;
    }

    public boolean notMaxCoal() {
        if(resource!=Resource.coal && resource!=Resource.coaloil) return false;
        if(resource==Resource.coal && coal<numResource*2) return true;
        if(resource==Resource.coaloil && coal+oil<numResource) return true;
        return false;
    }

    public boolean notMaxOil() {
        if (resource != Resource.oil && resource != Resource.coaloil) return false;
        if (resource == Resource.oil && oil < numResource*2) return true;
        if (resource == Resource.coaloil && coal + oil < numResource*2) return true;
        return false;
    }

    public boolean notMaxTrash() {
        if(resource!=Resource.trash) return false;
        if(trash<numResource*2) return true;
        return false;
    }

    public boolean notMaxNuclear() {
        if(resource!=Resource.nuclear) return false;
        if(nuclear<numResource*2) return true;
        return false;
    }
}
