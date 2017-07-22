package com.powergrid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public class Powergrid extends ApplicationAdapter {
	private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Window window;
    private BitmapFont font;

	private static final int maxPlayers = 6;
	private int numPlayers = 6;
	private Players players = new Players();
	private Color colours[] = {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.WHITE};
	private String playerNames[] = {"Dave","Alex","Josh","Fred","Jake","Alice"};

    public static final int areas[] = {0,0,3,3,4,5,5};
    public static final int maxPlants[] = {0,0,4,3,3,3,3};
	public static final int step2[] = {0,0,10,7,7,7,6};
	public static final int winCity[] = {0,0,21,17,17,15,14};

	public static final int payment[] = {10,22,33,44,54,64,73,82,90,98,105,112,118,124,129,134,138,142,145,148,150,0};

	public static final int H = 8;

	private int step = 0; //step 0 = initialisation step before game starts properly
	private int phase = 1;
	private int coal = 24;
	private int oil = 18;
	private int trash = 9;
	private int nuclear = 2;

	private Market market;
    private Deck deck;
    private List<Player> turnOrder;
    private List<Player> reverseTurnOrder;
    private Player currentPlayer;
    private int currentPlayerNum = 0;

	@Override
	public void create () {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont(Gdx.files.internal("skin/courier12.fnt"));
        font.setColor(Color.BLACK);
        Gdx.input.setInputProcessor(stage);

        deck = new Deck();
        deck.initDeck(numPlayers);

        market = new Market();
        market.initMarket(deck);
        players.initPlayers(numPlayers,playerNames,colours);

        //6 active zones for 6 players
        Zone.brown.setActive(true);
        Zone.yellow.setActive(true);
        Zone.cyan.setActive(true);
        Zone.red.setActive(true);
        Zone.blue.setActive(true);
        Zone.magenta.setActive(true);

        TextField numPlayers = new TextField("", skin);
        numPlayers.setMessageText("No. players");
        numPlayers.setAlignment(Align.left);

        TextField name = new TextField("", skin);
        name.setMessageText("Enter name");
        name.setAlignment(Align.left);

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        TextButton button = new TextButton("Go", skin);
        window = new Window("Start", skin);
        window.getTitleTable().add(new TextButton("X", skin)).height(window.getPadTop());
        window.align(Align.center | Align.top);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight());
        window.defaults().spaceBottom(10);
        window.row().fill().expandX();
        window.add(numPlayers);
        window.row();
        window.add(name);
        window.row();
        window.add(button);
        window.pack();
        window.setMovable(false);
        window.setResizable(false);
        window.setVisible(false);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(numPlayers.getText());
                System.out.println(name.getText());
                window.remove();
            }
        });

        stage.addActor(table);
        stage.addActor(window);
    }

	@Override
	public void render () {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        batch.begin();
            displayHeader();
            displayMarket(step);
            displayPlayers();
            displayCities();
            doEachPhase();
        batch.end();

        doInput();
    }

    private void doInput() {
	    if(phase==2) {
	        getPlantToChoose();
        }
    }

    private void getPlantToChoose() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
            bidOnPlant(1);
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
            bidOnPlant(2);
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
            bidOnPlant(3);
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
            bidOnPlant(4);
        else if(step==3 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))
            bidOnPlant(5);
        else if(step==3 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_6))
            bidOnPlant(6);
        else if(step > 0 && Gdx.input.isKeyJustPressed(Input.Keys.P))
            passBid();
    }

    private void bidOnPlant(int plantNum) {
	    int p = plantNum-1;
	    Cards.move(market.getMarket().getCards().get(p),market.getMarket(),currentPlayer.getPlants());
	    updateMarket();
	    passBid();
    }

    private void updateMarket() {
	    market.updateMarket(deck,step,phase);
    }

    private void passBid() {
	    setNextPlayer();
    }

    private void setNextPlayer() {
        if(currentPlayerNum<numPlayers-1) {
            currentPlayerNum++;
            currentPlayer = turnOrder.get(currentPlayerNum);

        } else {
            phase+=1;
        }
    }

    private void doEachPhase() {
		switch (phase) {
            case 1:
                phase1();
                break;
            case 2:
                phase2();
                break;
            case 3:
                phase3();
                break;
            case 4:
                phase4();
                break;
            case 5:
                phase5();
                break;
        }
	}

    private void phase1() {
        players.setTurnOrder();
        turnOrder = players.getTurnOrder();
        reverseTurnOrder = players.getTurnOrder();
        currentPlayer = turnOrder.get(0);
        currentPlayerNum = 0;
        phase=2;
    }

    private void phase2() {
	    bidForPlant();
    }

    private void phase3() {

    }

    private void phase4() {

    }

    private void phase5() {

    }

    private void bidForPlant() {
        StringBuilder message = new StringBuilder(currentPlayer.getName()+" choose plant to bid on");
        if(step>0)
            message.append(" or press P to pass");
        displayMessage(message,currentPlayer.getColour());
    }

    private void displayMessage(StringBuilder message, Color colour) {
	    font.setColor(colour);
	    font.draw(batch,message,600,100);
    }

    private void displayHeader() {
	    font.setColor(Color.RED);
	    font.draw(batch,"Step: "+step+" Phase: "+phase, 200, 472);
	    Resource.displayResources(batch,font,coal,oil,trash,nuclear,0,460);
    }

    private void displayMarket(int step) {
	    market.displayMarket(step,batch,font,0,420);
    }

    private void displayPlayers() {
	    List<Player> turnOrder = players.getTurnOrder();
	    int y = 0;
        for(Player player : turnOrder) {
            player.display(batch,font,0,320-y);
            y+=48;
        }
    }

    private void displayCities() {
	    int y=0;
        for(City city : City.germany) {
            if (city.getZone().isActive()) {
                city.display(batch,font,600,460-y);
                y+=8;
            }
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
}
