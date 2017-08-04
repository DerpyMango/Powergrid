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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public class Powergrid extends ApplicationAdapter {
	private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Window window;
    private BitmapFont font;
    private Display display;

	private static final int maxPlayers = 6;
	private int numPlayers = 2;
	private Players players = new Players();
	private Color colours[] = {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.WHITE};
	private String playerNames[] = {"Dave","Alex","Josh","Fred","Jake","Alice"};
	private String phases[] = {"Init","Play Order","Auction","Resources","Cities","Payment","Bureaucracy"};

    public static final int areas[] = {0,0,3,3,4,5,5};
    public static final int maxPlants[] = {0,0,4,3,3,3,3};
	public static final int step2[] = {0,0,10,7,7,7,6};
	public static final int winCity[] = {0,0,21,17,17,15,14};

	public static final int payment[] = {10,22,33,44,54,64,73,82,90,98,105,112,118,124,129,134,138,142,145,148,150,0};

	public static final int H = 8;
	public static final int col2 = 40;
	public static final int col3 = 60;
	public static final int map = 85;

	private int step = 0; //step 0 = initialisation step before game starts properly
	private int phase = 1;
	private int coal = 24;
	private int oil = 18;
	private int trash = 9;
	private int nuclear = 2;
	private boolean won = false;

	private Market market;
    private Deck deck;
    private List<Player> turnOrder;
    private List<Player> reverseTurnOrder;
    private Player currentPlayer;
    private int currentPlayerNum = 0;

    private Plant currentPlant = null;
    private int currentBid = 0;
    private int minBid = 0;
    private String errorMessage = "";
    private long errorTime = 0;
    private String cityNumString = "";
    private int cityCost = 0;
    private String number = "";
    private int numPassed = 0;

	@Override
	public void create () {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont(Gdx.files.internal("skin/courier12.fnt"));
        font.setColor(Color.BLACK);
        display = new Display(batch,font,Gdx.app.getGraphics().getHeight());
        Gdx.input.setInputProcessor(stage);

        deck = new Deck();
        deck.initDeck(numPlayers);

        market = new Market();
        market.initMarket(deck);
        players.initPlayers(numPlayers,playerNames,colours);
        turnOrder = players.getTurnOrder();
        reverseTurnOrder = players.getReverseTurnOrder();

        //6 active zones for 6 players
        Zone.brown.setActive(true);
        Zone.cyan.setActive(true);
        Zone.red.setActive(true);
        Zone.blue.setActive(true);
        Zone.magenta.setActive(true);
        Zone.yellow.setActive(true);

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
            displayErrorMessage();
            doEachPhase();
            doInput();
        batch.end();
    }

    private void doInput() {
	    if(won) return;
	    if (phase==1) {
	        numPassed = 0;
        } else if(phase==2) {
	        if(currentPlayer.getPlants().getCards().size() >= maxPlants[numPlayers]) {
	            getPlantToDiscard();
            } else {
                getPlantToChoose();
            }
        } else if(phase==3) {
	        buyResources();
        } else if(phase==4) {
	        buyCity();
        } else if(phase==5) {
	        chooseHowManyCitiesToPower();
        } else if(phase==6) {
            resupplyResources();
            updatePlantMarket();
            phase=1;
        }
    }

    private void resupplyResources() {
        coal+=Resource.coalReplenish[numPlayers][step-1];
        if(coal>Resource.maxCoal) coal = Resource.maxCoal;

        oil+=Resource.oilReplenish[numPlayers][step-1];
        if(oil>Resource.maxOil) oil = Resource.maxOil;

        trash+=Resource.trashReplenish[numPlayers][step-1];
        if(trash>Resource.maxTrash) trash = Resource.maxTrash;

        nuclear+=Resource.nuclearReplenish[numPlayers][step-1];
        if(nuclear>Resource.maxNuclear) nuclear = Resource.maxNuclear;
    }

    private void updatePlantMarket() {
	    int maxCity = players.getMostCities();
        market.updatePlantMarket(deck,step,maxCity);
    }


    private void chooseHowManyCitiesToPower() {
	    int digit = getDigit();
	    if(digit>-1) {
	        number += digit;
        }
        displayNum();
	    if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && number.length()>0) {
            number = number.substring(0, number.length() - 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && number.length()>0) {
            int numCity = Integer.parseInt(number);
            if (numCity > currentPlayer.getNumCity())
                numCity = currentPlayer.getNumCity();
            number = "";
            payCurrentPlayer(numCity);
            if(won) return;
            setNextReversePlayer();
        }
    }

    private void payCurrentPlayer(int numCities) {
	    int citiesSupplied = 0;
	    for(Plant plant : currentPlayer.getPlants().getCards()) {
	        if(enoughResources(plant)) {
	            citiesSupplied += reduceResources(plant);
            }
            if(citiesSupplied>=numCities)
                break;
        }
        int actualCitiesPowered = Math.min(citiesSupplied,numCities);
        currentPlayer.spend(-payment[actualCitiesPowered]);
	    if(actualCitiesPowered>=winCity[numPlayers]) {
	        won = true;
        }
    }

    private boolean enoughResources(Plant plant) {
	    Resource resource = plant.getResource();
        if(resource==Resource.coal && plant.getCoal()>=plant.getNumResource()) return true;
        if(resource==Resource.coaloil && (plant.getCoal() + plant.getOil())>=plant.getNumResource()) return true;
        if(resource==Resource.oil && plant.getOil()>=plant.getNumResource()) return true;
        if(resource==Resource.trash && plant.getTrash()>=plant.getNumResource()) return true;
        if(resource==Resource.nuclear && plant.getNuclear()>=plant.getNumResource()) return true;
        if(resource==Resource.wind || resource==Resource.fusion) return true;

        return false;
    }

    private int reduceResources(Plant plant) {
        Resource resource = plant.getResource();
        if(resource==Resource.coal ) {
            plant.setCoal(plant.getCoal()-plant.getNumResource());
        } else if(resource==Resource.oil ) {
            plant.setOil(plant.getOil()-plant.getNumResource());
        } else if(resource==Resource.coaloil ) {
            int coal = plant.getCoal();
            int oil = plant.getOil();
            int numResources = plant.getNumResource();
            while(numResources>0) {
                if(coal>0 && numResources>0) {
                    coal--;
                    plant.setCoal(coal);
                    numResources--;
                }
                if(oil>0 && numResources>0) {
                    oil--;
                    plant.setOil(oil);
                    numResources--;
                }
            }
        } else if(resource==Resource.trash ) {
            plant.setTrash(plant.getTrash()-plant.getNumResource());
        } else if(resource==Resource.nuclear ) {
            plant.setNuclear(plant.getNuclear()-plant.getNumResource());
        }
        return plant.getNumCity();
    }

    private void displayNum() {
	    if(number.length()>0) {
            display.text(col2,49,number+" cities",Color.WHITE);
        }
    }

    private void buyCity() {
	    get2digitNumber();
	    displayCityFromNum();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && cityNumString.length()>0)
            cityNumString = cityNumString.substring(0,cityNumString.length()-1);
	    if(cityNumString.length()==2) {
            int c = Integer.parseInt(cityNumString);
            City city = City.getCity(c);
            if(currentPlayer.getNumCity()>0) {
                cityCost = city.getPrice(step,currentPlayer); //check
                cityCost += city.findCheapestRoute(currentPlayer);
            } else {
                cityCost = 10;
            }
        }
        if (cityNumString.length()==2 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            int c = Integer.parseInt(cityNumString);
            City city = City.getCity(c);
            if(cityCost>currentPlayer.getElectros()) {
                setErrorMessage("Not enough money");
            } else {
                if(city.getTen()==null) {
                    currentPlayer.spend(cityCost);
                    city.setTen(currentPlayer);
                    currentPlayer.incNumCity();
                } else if (step>1 && city.getTen()!=null && city.getTen() != currentPlayer && city.getFifteen()==null) {
                    currentPlayer.spend(cityCost);
                    city.setFifteen(currentPlayer);
                    currentPlayer.incNumCity();
                } else if (step>2 && city.getTen()!=null && city.getTen() != currentPlayer && city.getFifteen()!=null && city.getFifteen()!=currentPlayer
                        && city.getTwenty()==null) {
                    currentPlayer.spend(cityCost);
                    city.setTwenty(currentPlayer);
                    currentPlayer.incNumCity();
                } else {
                    setErrorMessage("Can't buy this city");
                }
                removeLowestPlantInMarket();
                if(step==1 && currentPlayer.getNumCity()>=step2[numPlayers]) {
                    step = 2;
                    removeLowestPlant();
                }
            }
            cityNumString = "";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            number = "";
            cityNumString = "";
	        setNextReversePlayer();
        }
    }

    private void removeLowestPlant() {
	    int maxCity = players.getMostCities();
        market.removeLowestPlant(deck,step,maxCity);
        updateMarket();
    }

    private void removeLowestPlantInMarket() {
        int lowest = players.getMostCities();
        if (market.removeLowestPlantInMarket(deck,step,lowest)) {
            updateMarket();
        }
    }

    private void get2digitNumber() {
	    int digit = getDigit();
	    if(digit>-1 && cityNumString.length()<2) {
            if (cityNumString.length() == 1) {
                String num = cityNumString + digit;
                int n = Integer.parseInt(num);
                if (n >= City.numCities)
                    return;
            }
            cityNumString += digit;
        } else if(digit>-1 && cityNumString.length()==2) {
            cityNumString = "" + digit;
        }
	}

    private void displayCityFromNum() {
	    StringBuilder cityDesc = new StringBuilder(String.format("%s.",cityNumString));
        Color colour = Color.WHITE;
	    if(cityNumString.length()==2) {
	        int city = Integer.parseInt(cityNumString);
	        cityDesc.append(String.format(" %s Cost: %d",City.getCity(city).getName(),cityCost));
            colour = City.getCity(city).getColor();
        }
	    display.text(col2,49,cityDesc.toString(),colour);
    }

    private void buyResources() {
        int numPlants = currentPlayer.getPlants().getCards().size();
        if (numPlants > 0 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
            buyResourcesFor(1);
        else if (numPlants > 1 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
            buyResourcesFor(2);
        else if (numPlants > 2 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
            buyResourcesFor(3);
        else if (numPlants > 3 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
            buyResourcesFor(4);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            setNextReversePlayer();
            currentPlant = null;
        }

        if(currentPlant != null) {
            displayResourcePlant();
            if(coal>0 && currentPlayer.getElectros()>Resource.coalPrice[coal-1] && currentPlant.notMaxCoal() && Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                currentPlant.incCoal();
                currentPlayer.spend(Resource.coalPrice[coal-1]);
                coal--;
            }
            if(oil>0 && currentPlayer.getElectros()>Resource.oilPrice[oil-1] && currentPlant.notMaxOil() && Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                currentPlant.incOil();
                currentPlayer.spend(Resource.oilPrice[oil-1]);
                oil--;
            }
            if(trash>0 && currentPlayer.getElectros()>Resource.trashPrice[trash-1] && currentPlant.notMaxTrash() && Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                currentPlant.incTrash();
                currentPlayer.spend(Resource.trashPrice[trash-1]);
                trash--;
            }
            if(nuclear>0 && currentPlayer.getElectros()>Resource.nuclearPrice[nuclear-1] && currentPlant.notMaxNuclear() && Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                currentPlant.incNuclear();
                currentPlayer.spend(Resource.nuclearPrice[nuclear-1]);
                nuclear--;
            }
        }
    }

    private void buyResourcesFor(int p) {
	    currentPlant = currentPlayer.getPlants().getCards().get(p-1);
    }

    private void getPlantToDiscard() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
            discardPlant(1);
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
            discardPlant(2);
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
            discardPlant(3);
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
            discardPlant(4);
        else if(step > 0 && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            numPassed++;
            passBid(currentPlayer);
        }
    }

    private void discardPlant(int p) {
	    Plant plant = currentPlayer.getPlants().getCards().get(p-1);
        currentPlayer.getPlants().remove(plant);
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
        else if(step > 0 && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            numPassed++;
            passBid(currentPlayer);
        }
        if(currentPlant !=null) {
            displayCurrentBid();
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
                currentBid+=1;
            else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && currentBid>minBid)
                currentBid-=1;
            else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
                movePlantToPlayer(currentPlayer, currentPlant);
            else if(Gdx.input.isKeyJustPressed(Input.Keys.A))
                movePlantToPlayer(turnOrder.get(0), currentPlant);
            else if(Gdx.input.isKeyJustPressed(Input.Keys.B))
                movePlantToPlayer(turnOrder.get(1), currentPlant);
            else if(numPlayers>2 && Gdx.input.isKeyJustPressed(Input.Keys.C))
                movePlantToPlayer(turnOrder.get(2), currentPlant);
            else if(numPlayers>3 && Gdx.input.isKeyJustPressed(Input.Keys.D))
                movePlantToPlayer(turnOrder.get(3), currentPlant);
            else if(numPlayers>4 && Gdx.input.isKeyJustPressed(Input.Keys.E))
                movePlantToPlayer(turnOrder.get(4), currentPlant);
            else if(numPlayers>5 && Gdx.input.isKeyJustPressed(Input.Keys.F))
                movePlantToPlayer(turnOrder.get(5), currentPlant);
        }
    }

    private void bidOnPlant(int plantNum) {
	    Plant plant = market.getMarket().getCards().get(plantNum-1);
	    currentBid = plant.getCost();
	    minBid = currentBid;
	    currentPlant = plant;
    }

    private void displayCurrentBid() {
        currentPlant.displayPlantBid(display,col2,49,currentBid);
    }

    private void displayResourcePlant() {
        currentPlant.displayPlant(display,col2,49);
    }

    private void movePlantToPlayer(Player player, Plant plant) {
	    if(currentBid > player.getElectros()) {
	        setErrorMessage("Not enough money");
	        return;
        }
	    Cards.move(plant,market.getMarket(),player.getPlants());
        player.spend(currentBid);

	    updateMarket();
	    passBid(player);
    }

    private void updateMarket() {
	    int maxCity = players.getMostCities();
	    if (market.updateMarket(deck,step,phase,maxCity)) {
	        //Do step 3 stuff
            step=3;
            removeLowestPlant();
        }
    }

    private void passBid(Player player) {
        player.setPassed(true);
	    currentPlant = null;
	    setNextPlayer();
    }

    private void setNextPlayer() {
	    //find first player not yet passed
        int p=0;
        for(Player player: turnOrder)
        {
            if(!player.hasPassed()) {
                currentPlayer = player;
                currentPlayerNum = p;
                return;
            }
            p++;
        }
        if(step==0) {
            step=1;
            determineTurnOrder();
        }
        phase+=1;
        for (Player player : turnOrder)
            player.setPassed(false);
        if(numPassed>=numPlayers)
            removeLowestPlant();
        initReverseTurnOrderPlayer();
    }

    private void setNextReversePlayer() {
	    currentPlayerNum+=1;
	    if(currentPlayerNum>=numPlayers) {
	        phase+=1;
            initReverseTurnOrderPlayer();
            return;
        }
        currentPlayer = reverseTurnOrder.get(currentPlayerNum);
    }

    private void doEachPhase() {
	    if(won) {
	        displayMessage(new StringBuilder(currentPlayer.getName()).append(" Has Won!"),currentPlayer.getColour());
	        return;
        }
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

	private void determineTurnOrder() {
        players.setTurnOrder();
        turnOrder = players.getTurnOrder();
        reverseTurnOrder = players.getReverseTurnOrder();
    }

    private void initTurnOrderPlayer() {
        currentPlayer = turnOrder.get(0);
        currentPlayerNum = 0;
    }


    private void initReverseTurnOrderPlayer() {
        currentPlayer = reverseTurnOrder.get(0);
        currentPlayerNum = 0;
    }

    private void phase1() {
        determineTurnOrder();
        initTurnOrderPlayer();
        phase=2;
    }

    private void phase2() {
	    bidForPlant();
    }

    private void phase3() {
        StringBuilder message = new StringBuilder(currentPlayer.getName()+" choose plant to add resources (Enter to finish turn)");
        displayMessage(message,currentPlayer.getColour());
	}

    private void phase4() {
        StringBuilder message = new StringBuilder(currentPlayer.getName()+" choose city to connect to (P to finish turn)");
        displayMessage(message,currentPlayer.getColour());
    }

    private void phase5() {
	    //Bureaucracy
        StringBuilder message = new StringBuilder(currentPlayer.getName()+" how many cities you want to power?");
        displayMessage(message,currentPlayer.getColour());
    }

    private void bidForPlant() {
        StringBuilder message = new StringBuilder(currentPlayer.getName());
	    if(currentPlayer.getPlants().getCards().size() < maxPlants[numPlayers]) {
            message.append(" choose plant to bid on");
        } else {
	        message.append(" choose plant to discard");
        }
        if(step>0)
            message.append(" or press P to pass");
        displayMessage(message,currentPlayer.getColour());
    }

    private void displayMessage(StringBuilder message, Color colour) {
	    currentPlayer.display(display, col2,51);
	    display.text(col2,48,message.toString(),colour);
    }

    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        errorTime = TimeUtils.millis()+1000;
    }

    private void displayErrorMessage() {
	    if(errorTime==0) return;
        font.setColor(Color.PURPLE);
        display.text(col2,50,errorMessage,Color.PURPLE);
        if (TimeUtils.millis()>errorTime) {
            errorTime = 0;
            errorMessage = "";
        }
    }

    private void displayHeader() {
	    font.setColor(Color.RED);
	    display.text(12,1,"Step: "+step+" Phase: "+phase+" "+phases[phase],Color.RED);
	    Resource.displayResources(display,coal,oil,trash,nuclear,0,2);
    }

    private void displayMarket(int step) {
	    market.displayMarket(step,display,0,7);
    }

    private void displayPlayers() {
	    int y = 18;
        for(Player player : turnOrder) {
            player.display(display,0,y);
            y+=6;
        }
    }

    private void displayCities() {
	    int y=1;
        for(City city : City.germany) {
            if (city.getZone().isActive()) {
                city.display(display,col3,map,y);
                y+=1;
            }
        }
    }

    private int getDigit() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) return 1;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) return 2;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) return 3;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) return 4;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) return 5;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) return 6;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) return 7;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) return 8;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) return 9;
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) return 0;
        return -1;
    }

	@Override
	public void dispose () {
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
}
