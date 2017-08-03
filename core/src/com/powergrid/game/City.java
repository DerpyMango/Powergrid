package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

/**
 * Created by David on 8/07/2017.
 */
public class City {
    private String name;
    private int id;
//    private List<Connection> connections;
    private Zone zone;
    private Player ten;
    private Player fifteen;
    private Player twenty;
    private int totalCost = 9999;
    private int x;
    private int y;

    public static final int numCities = 42;
    public static City germany[] = new City[numCities];

    public static final float SC = 3f;
    public static final int X = 600;
    public static final int Y = 20;

    public static City flensburg = new City(0).name("Flensberg").zone(Zone.cyan).coords(63,7);
    public static City kiel = new City(1).name("Kiel").zone(Zone.cyan).coords(70,19);
    public static City cuxhaven= new City(2).name("Cuxhaven").zone(Zone.cyan).coords(52,30);
    public static City hamburg = new City(3).name("Hamburg").zone(Zone.cyan).coords(70,38);
    public static City wilhelmshaven = new City(4).name("Wilhelmshaven").zone(Zone.cyan).coords(42,38);
    public static City bremen = new City(5).name("Bremen").zone(Zone.cyan).coords(55,50);
    public static City hannover = new City(6).name("Hannover").zone(Zone.cyan).coords(70,69);

    public static City lubeck = new City(7).name("Lubeck").zone(Zone.brown).coords(82,28);
    public static City rostock = new City(8).name("Rostock").zone(Zone.brown).coords(101,23);
    public static City schwerin = new City(9).name("Schwerin").zone(Zone.brown).coords(92,40);
    public static City torgelow = new City(10).name("Torgelow").zone(Zone.brown).coords(130,38);
    public static City magdeburg = new City(11).name("Magdeburg").zone(Zone.brown).coords(98,71);
    public static City berlin = new City(12).name("Berlin").zone(Zone.brown).coords(120,65);
    public static City frankfurto = new City(13).name("Franfurt-O").zone(Zone.brown).coords(136,70);

//    public static City  = new City().name("").zone(Zone.red);
    public static City osnabruck = new City(14).name("Osnabruck").zone(Zone.red).coords(43,66);
    public static City duisburg = new City(15).name("Duisburg").zone(Zone.red).coords(14,83);
    public static City munster = new City(16).name("Munster").zone(Zone.red).coords(35,78);
    public static City essen = new City(17).name("Essen").zone(Zone.red).coords(24,87);
    public static City dusseldorf = new City(18).name("Dusseldorf").zone(Zone.red).coords(17,98);
    public static City dortmund = new City(19).name("Dortmund").zone(Zone.red).coords(37,92);
    public static City kassel = new City(20).name("Kassel").zone(Zone.red).coords(62,95);

    public static City halle = new City(21).name("Halle").zone(Zone.yellow).coords(100,90);
    public static City leipzig = new City(22).name("Leipzig").zone(Zone.yellow).coords(108,96);
    public static City fulda = new City(23).name("Fulda").zone(Zone.yellow).coords(93,114);
    public static City erfurt = new City(24).name("Erfurt").zone(Zone.yellow).coords(91,103);
    public static City dresden = new City(25).name("Dresden").zone(Zone.yellow).coords(103,104);
    public static City wurzburg = new City(26).name("Wurzburg").zone(Zone.yellow).coords(72,134);
    public static City nurnberg = new City(27).name("Nurnberg").zone(Zone.yellow).coords(92,143);

    public static City aachen = new City(28).name("Aachen").zone(Zone.blue).coords(12,112);
    public static City koln = new City(29).name("Koln").zone(Zone.blue).coords(27,107);
    public static City trier = new City(30).name("Trier").zone(Zone.blue).coords(18,134);
    public static City wiesbaden = new City(31).name("Wiesbaden").zone(Zone.blue).coords(44,127);
    public static City frankfurtm = new City(32).name("Frankfurt-M").zone(Zone.blue).coords(53,122);
    public static City saarbrucken = new City(33).name("Saarbrucken").zone(Zone.blue).coords(32,149);
    public static City mannheim = new City(34).name("Mannheim").zone(Zone.blue).coords(51,145);

    public static City stuttgart = new City(35).name("Stuttgard").zone(Zone.magenta).coords(56,161);
    public static City regensburg = new City(36).name("Regensburg").zone(Zone.magenta).coords(101,154);
    public static City passau = new City(37).name("Passau").zone(Zone.magenta).coords(124,164);
    public static City augsburg = new City(38).name("Augsburg").zone(Zone.magenta).coords(81,166);
    public static City munchen = new City(39).name("Munchen").zone(Zone.magenta).coords(96,176);
    public static City freiburg = new City(40).name("Freiburg").zone(Zone.magenta).coords(38,176);
    public static City konstanz = new City(41).name("Konstanz").zone(Zone.magenta).coords(56,184);

    public City(int id) {
        if (germany[id] != null) {
            System.out.println("City already registered");
            System.exit(1);
        }
        germany[id] = this;
        this.id = id;
    }

    public City zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public City name(String name) {
        this.name = name;
        return this;
    }

    public City coords(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public Zone getZone() {
        return zone;
    }

    public Player getTen() {
        return ten;
    }

    public Player getFifteen() {
        return fifteen;
    }

    public Player getTwenty() {
        return twenty;
    }

    public void setTen(Player ten) {
        this.ten = ten;
    }

    public void setFifteen(Player fifteen) {
        this.fifteen = fifteen;
    }

    public void setTwenty(Player twenty) {
        this.twenty = twenty;
    }

    public Color getColor() {
        return zone.getColour();
    }

    public boolean isActive() {
        return zone.isActive();
    }

    public void setTotalCost(int cost) {
        totalCost = cost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void display(SpriteBatch batch, BitmapFont font, int x, int y) {
        String desc = String.format("%02d. %s",id,name);
        Color colour = zone.getColour();
        font.setColor(colour);
        font.draw(batch,desc,x,y);
        if (ten!=null) {
            font.setColor(ten.getColour());
            font.draw(batch,ten.getName(),x+120,y);
        }
        if (fifteen!=null) {
            font.setColor(fifteen.getColour());
            font.draw(batch,fifteen.getName(),x+160,y);
        }
        if (twenty!=null) {
            font.setColor(twenty.getColour());
            font.draw(batch,twenty.getName(),x+200,y);
        }
        //font.draw(batch,name,getX()*SC+X,480-getY()*SC-Y);
    }

    public int findCheapestRoute(Player player) {
        City.resetVisits();
        markConnectionCosts(0);
        int cheapest = 9999;
        for(City city : germany) {
            int cost = city.getTotalCost();
            if(city.hasPlayer(player) && cost < cheapest) {
                cheapest = cost;
            }
        }
        return cheapest;
    }

    private void markConnectionCosts(int prevCost) {
        List<Connection> connections = Connection.getConnections(this);
        for(Connection connection : connections) {
            City city = connection.otherCity(this);
            if(city.isActive()) {
                int cost = connection.getCost() + prevCost;
                //System.out.println("City: "+city.getName()+" Cost: "+(cost));
                if (cost < city.getTotalCost()) {
                    city.setTotalCost(cost);
                    //System.out.println("--go in--");
                    city.markConnectionCosts(cost);
                }
            }
            //System.out.println("--return--");
        }
    }

    public boolean hasPlayer(Player player) {
        return (ten==player || fifteen==player || twenty==player);
    }

    public static City getCity(int cityNum) {
        return germany[cityNum];
    }

    public static void resetVisits() {
        for(City city : germany) {
            city.totalCost = 9999;
        }
    }

    public int getPrice(int step, Player player) {
        if(ten==null) return 10;
        if(fifteen==null) return 15;
        if(twenty==null) return 20;
        return 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
