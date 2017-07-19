package com.powergrid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class Powergrid extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;

	static int maxPlayers = 6;
	private int numPlayers = 2;
	private Player[] players = new Player[maxPlayers];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		Deck deck = new Deck();
		deck.initDeck(numPlayers);

		Market market = new Market();
		market.initMarket(deck);
		System.out.println("Deck");
		deck.displayCards();
		market.displayMarket();

		for (int p=0;p<numPlayers;p++) {
			players[p] = new Player().name("Player "+p).id(p).electros(50);
			players[p].display();
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
