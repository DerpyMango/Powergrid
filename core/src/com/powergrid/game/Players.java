package com.powergrid.game;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Players {
    private List<Player> players = new ArrayList<>();

    public void initPlayers(int numPlayers, String playerNames[], Color colours[]) {
        for (int p = 0; p < numPlayers; p++) {
            players.add(new Player().name(playerNames[p]).id(p).electros(50).colour(colours[p]));
        }
    }

    public List<Player> getTurnOrder() {
        List<Player> sorted = new ArrayList<>(players);
        Collections.sort(sorted);
        return sorted;
    }


    public List<Player> getReverseTurnOrder() {
        List<Player> sorted = new ArrayList<>(players);
        Collections.sort(sorted);
        Collections.reverse(sorted);
        return sorted;
    }

    public void setTurnOrder() {
        List<Player> sorted = getTurnOrder();
        int o=1;
        for(Player player : sorted) {
            player.setTurnOrder(o++);
        }
    }

    public Player getPlayer(int p) {
        return players.get(p);
    }
}
