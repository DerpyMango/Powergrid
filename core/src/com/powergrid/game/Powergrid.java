package com.powergrid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

public class Powergrid extends ApplicationAdapter {
	private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Window window;
    private BitmapFont font;

	private static final int maxPlayers = 6;
	private int numPlayers = 2;
	private Player players[] = new Player[maxPlayers];
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
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont(Gdx.files.internal("skin/courier12.fnt"));
        font.setColor(Color.BLACK);
        Gdx.input.setInputProcessor(stage);

        Deck deck = new Deck();
        deck.initDeck(numPlayers);

        market = new Market();
        market.initMarket(deck);

        for (int p = 0; p < numPlayers; p++) {
            players[p] = new Player().name(playerNames[p]).id(p).electros(50).colour(colours[p]);
            players[p].display();
        }

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
		batch.end();
	}

	private void displayHeader() {
	    font.setColor(Color.RED);
	    font.draw(batch,"Step: "+step+" Phase: "+phase, 200, 472);
	    Resource.displayResources(batch,font,coal,oil,trash,nuclear,0,460);
    }

    private void displayMarket(int step) {
	    market.displayMarket(step,batch,font,0,400);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
}
