package com.powergrid.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 8/07/2017.
 */
public class Connection {
    private int id;
    private City from;
    private City to;
    private int cost;

    public static final int numConnections = 83;
    public static Connection connections[] = new Connection[numConnections];

    public static Connection flenKiel = new Connection(0).from(City.flensburg).to(City.kiel).cost(4);
    public static Connection kielLube = new Connection(1).from(City.kiel).to(City.lubeck).cost(4);
    public static Connection kielHamb = new Connection(2).from(City.kiel).to(City.hamburg).cost(8);
    public static Connection lubeSchw = new Connection(3).from(City.lubeck).to(City.schwerin).cost(6);
    public static Connection lubeHamb = new Connection(4).from(City.lubeck).to(City.hamburg).cost(6);
    public static Connection hambSchw = new Connection(5).from(City.hamburg).to(City.schwerin).cost(8);
    public static Connection hambCuxh = new Connection(6).from(City.hamburg).to(City.cuxhaven).cost(11);
    public static Connection hambBrem = new Connection(7).from(City.hamburg).to(City.bremen).cost(11);
    public static Connection hambHann = new Connection(8).from(City.hamburg).to(City.hannover).cost(17);
    public static Connection cuxhBrem = new Connection(9).from(City.cuxhaven).to(City.bremen).cost(8);

    public static Connection schwRost = new Connection(10).from(City.schwerin).to(City.rostock).cost(6);
    public static Connection wilhBrem = new Connection(11).from(City.wilhelmshaven).to(City.bremen).cost(11);
    public static Connection rostTorg = new Connection(12).from(City.rostock).to(City.torgelow).cost(19);
    public static Connection schwTorg = new Connection(13).from(City.schwerin).to(City.torgelow).cost(19);
    public static Connection schwHano = new Connection(14).from(City.schwerin).to(City.hannover).cost(19);
    public static Connection schwMagd = new Connection(15).from(City.schwerin).to(City.magdeburg).cost(16);
    public static Connection schwBerl = new Connection(16).from(City.schwerin).to(City.berlin).cost(18);
    public static Connection torgBerl = new Connection(17).from(City.torgelow).to(City.berlin).cost(15);
    public static Connection wilhOsna = new Connection(18).from(City.wilhelmshaven).to(City.osnabruck).cost(14);
    public static Connection bremOsna = new Connection(19).from(City.bremen).to(City.osnabruck).cost(11);

    public static Connection bremHann = new Connection(20).from(City.bremen).to(City.hannover).cost(10);
    public static Connection osnaHanna = new Connection(21).from(City.osnabruck).to(City.hannover).cost(16);
    public static Connection hannMagd = new Connection(22).from(City.hannover).to(City.magdeburg).cost(15);
    public static Connection magdBerl = new Connection(23).from(City.magdeburg).to(City.berlin).cost(10);
    public static Connection berlFraO = new Connection(24).from(City.berlin).to(City.frankfurto).cost(6);
    public static Connection osnaMuns = new Connection(25).from(City.osnabruck).to(City.munster).cost(7);
    public static Connection osnaKass = new Connection(26).from(City.osnabruck).to(City.kassel).cost(20);
    public static Connection hannKass = new Connection(27).from(City.hannover).to(City.kassel).cost(15);
    public static Connection magdHall = new Connection(28).from(City.magdeburg).to(City.halle).cost(11);
    public static Connection berlHall = new Connection(29).from(City.berlin).to(City.halle).cost(17);

    public static Connection franLeip = new Connection(30).from(City.frankfurto).to(City.leipzig).cost(21);
    public static Connection hallLeip = new Connection(31).from(City.halle).to(City.leipzig).cost(0);
    public static Connection munsEsse = new Connection(32).from(City.munster).to(City.essen).cost(6);
    public static Connection duisEsse = new Connection(33).from(City.duisburg).to(City.essen).cost(0);
    public static Connection munsDort = new Connection(34).from(City.munster).to(City.dortmund).cost(2);
    public static Connection esseDuss = new Connection(35).from(City.essen).to(City.dusseldorf).cost(2);
    public static Connection esseDort = new Connection(36).from(City.essen).to(City.dortmund).cost(2);
    public static Connection dortKass = new Connection(37).from(City.dortmund).to(City.kassel).cost(18);
    public static Connection hannErfu = new Connection(38).from(City.hannover).to(City.erfurt).cost(19);
    public static Connection kassErfu = new Connection(39).from(City.kassel).to(City.erfurt).cost(15);

    public static Connection hallErfu = new Connection(40).from(City.halle).to(City.erfurt).cost(6);
    public static Connection franDres = new Connection(41).from(City.frankfurto).to(City.dresden).cost(16);
    public static Connection leipDres = new Connection(42).from(City.leipzig).to(City.dresden).cost(13);
    public static Connection erfuDres = new Connection(43).from(City.erfurt).to(City.dresden).cost(19);
    public static Connection dussAach = new Connection(44).from(City.dusseldorf).to(City.aachen).cost(9);
    public static Connection dussKoln = new Connection(45).from(City.dusseldorf).to(City.koln).cost(4);
    public static Connection dortKoln = new Connection(46).from(City.dortmund).to(City.koln).cost(10);
    public static Connection dortFraM = new Connection(47).from(City.dortmund).to(City.frankfurtm).cost(20);
    public static Connection kassFraM = new Connection(48).from(City.kassel).to(City.frankfurtm).cost(13);
    public static Connection kassFuld = new Connection(49).from(City.kassel).to(City.fulda).cost(8);

    public static Connection aachKoln = new Connection(50).from(City.aachen).to(City.koln).cost(7);
    public static Connection kolnWies = new Connection(51).from(City.koln).to(City.wiesbaden).cost(21);
    public static Connection freiKons = new Connection(52).from(City.freiburg).to(City.konstanz).cost(14);
    public static Connection erfuFuld = new Connection(53).from(City.erfurt).to(City.fulda).cost(13);
    public static Connection aachTrie = new Connection(54).from(City.aachen).to(City.trier).cost(19);
    public static Connection kolnTrie = new Connection(55).from(City.koln).to(City.trier).cost(20);
    public static Connection trieWies = new Connection(56).from(City.trier).to(City.wiesbaden).cost(18);
    public static Connection wiesFraM = new Connection(57).from(City.wiesbaden).to(City.frankfurtm).cost(0);
    public static Connection trieSaar = new Connection(58).from(City.trier).to(City.saarbrucken).cost(11);
    public static Connection wiesSaar = new Connection(59).from(City.wiesbaden).to(City.saarbrucken).cost(10);

    public static Connection wiesMann = new Connection(60).from(City.wiesbaden).to(City.mannheim).cost(11);
    public static Connection fuldFraM = new Connection(61).from(City.fulda).to(City.frankfurtm).cost(8);
    public static Connection fuldWurz = new Connection(62).from(City.fulda).to(City.wurzburg).cost(11);
    public static Connection fraMWurz = new Connection(63).from(City.frankfurtm).to(City.wurzburg).cost(13);
    public static Connection erfuNurn = new Connection(64).from(City.erfurt).to(City.nurnberg).cost(21);
    public static Connection saarMann = new Connection(65).from(City.saarbrucken).to(City.mannheim).cost(11);
    public static Connection mannWurz = new Connection(66).from(City.mannheim).to(City.wurzburg).cost(10);
    public static Connection wurzNurn = new Connection(67).from(City.wurzburg).to(City.nurnberg).cost(8);
    public static Connection nurnRege = new Connection(68).from(City.nurnberg).to(City.regensburg).cost(12);
    public static Connection regePass = new Connection(69).from(City.regensburg).to(City.passau).cost(12);

    public static Connection saarStut = new Connection(70).from(City.saarbrucken).to(City.stuttgart).cost(17);
    public static Connection mannStut = new Connection(71).from(City.mannheim).to(City.stuttgart).cost(6);
    public static Connection wurzStut = new Connection(72).from(City.wurzburg).to(City.stuttgart).cost(12);
    public static Connection wurzAugs = new Connection(73).from(City.wurzburg).to(City.augsburg).cost(19);
    public static Connection nurnAugs = new Connection(74).from(City.nurnberg).to(City.augsburg).cost(18);
    public static Connection regeAugs = new Connection(75).from(City.regensburg).to(City.augsburg).cost(13);
    public static Connection regeMunc = new Connection(76).from(City.regensburg).to(City.munchen).cost(10);
    public static Connection stutAugs = new Connection(77).from(City.stuttgart).to(City.augsburg).cost(15);
    public static Connection augsMunc = new Connection(78).from(City.augsburg).to(City.munchen).cost(6);
    public static Connection muncPass = new Connection(79).from(City.munchen).to(City.passau).cost(14);

    public static Connection stutFrei = new Connection(80).from(City.stuttgart).to(City.freiburg).cost(16);
    public static Connection stutKons = new Connection(81).from(City.stuttgart).to(City.konstanz).cost(16);
    public static Connection augsKons = new Connection(82).from(City.augsburg).to(City.konstanz).cost(17);

    public Connection(int id) {
        if (connections[id] != null) {
            System.out.println("Connection already registered");
            System.exit(1);
        }
        connections[id] = this;
        this.id = id;
    }

    public Connection from(City from) {
        this.from = from;
        return this;
    }

    public Connection to(City to) {
        this.to = to;
        return this;
    }

    public Connection cost(int cost) {
        this.cost = cost;
        return this;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public int getCost() {
        return cost;
    }

    public static List<City> getConnectedCities(City city) {
        List<City> connectedCities = new ArrayList<>();
        for(int i=0; i<numConnections; i++) {
            if(connections[i].from==city)
                connectedCities.add(connections[i].to);
            else if(connections[i].to==city)
                connectedCities.add(connections[i].from);
        }
        return connectedCities;
    }

    public static List<Connection> getConnections(City city) {
        List<Connection> connectionsTo = new ArrayList<>();
        for(int i=0; i<numConnections; i++) {
            if(connections[i].from==city || connections[i].to==city)
                connectionsTo.add(connections[i]);
        }
        return connectionsTo;
    }

    public City otherCity(City city) {
        if(from==city)
            return to;
        else
            return from;
    }

    public boolean hasCity(City city) {
        return (to==city || from==city);
    }
}
