package splendor.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import splendor.gameelements.BaseCard;
import splendor.gameelements.BaseDeck;
import splendor.gameelements.CardSlot;
import splendor.gameelements.DeckColor;
import splendor.gameelements.Noble;
import splendor.gameelements.NobleSlot;
import splendor.gameelements.Player;
import splendor.gameelements.TradingPostType;

public class Game {

  private static Game instance;

  //leaving public for easier testing of other features, will fix later
  public Player player1;
  public Player player2;
  public Player player3;
  public Player player4;
  public List<Player> players = new ArrayList<>();
  public BaseDeck greenDeck;
  public BaseDeck yellowDeck;
  public BaseDeck blueDeck;
  public BaseDeck redL1Deck;
  public BaseDeck redL2Deck;
  public BaseDeck redL3Deck;
  public int greenDeckSize;
  public int yellowDeckSize;
  public int blueDeckSize;
  public int redL1DeckSize;
  public int redL2DeckSize;
  public int redL3DeckSize;
  public List<CardSlot> cardSlots = new ArrayList<>();
  public List<NobleSlot> nobleSlots = new ArrayList<>();

  public int turn; //will rotate through values 1-4 if max players

  public List<Integer> colorTokens = new ArrayList<>();

  public int goldTokens;
  public int currentPlayer;
  public int myPlayerNumber;

  public static void clearGame() {
    instance = null;
  }

  public Game() {
    //Constructor just gives a reference to the current game, while there isn't a game everything
    // is null
  }

  public Game(GameState state, List<Player> players) {
    assert instance == null;
    instance = this;

    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));

    cardSlots.add(new CardSlot(redL1Deck));
    cardSlots.add(new CardSlot(redL1Deck));
    cardSlots.add(new CardSlot(redL2Deck));
    cardSlots.add(new CardSlot(redL2Deck));
    cardSlots.add(new CardSlot(redL3Deck));
    cardSlots.add(new CardSlot(redL3Deck));

    for (int i = 0; i < 5; i++) {
      nobleSlots.add(new NobleSlot());
    }

    currentPlayer = state.getCurrentPlayer();

    this.players = players;
    player1 = players.get(0);
    player2 = players.get(1);
    if (players.size() >= 3) {
      player3 = players.get(2);
    }
    if (players.size() >= 4) {
      player4 = players.get(3);
    }

    colorTokens.add(0);
    colorTokens.add(0);
    colorTokens.add(0);
    colorTokens.add(0);
    colorTokens.add(0);
  }


  public void startGame(String paramUsername) {
    player1 = new Player(paramUsername, "blue", 1);

    //Create decks to be used in the game
    greenDeck = new BaseDeck(DeckColor.GREEN, 7);
    yellowDeck = new BaseDeck(DeckColor.YELLOW, 7);
    blueDeck = new BaseDeck(DeckColor.BLUE, 7);

    //Create card slots (to hold face-up cards)
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));

    for (CardSlot slot : cardSlots) {
      slot.refill();
    }

    turn = 1; //Player 1 turn

    colorTokens = Arrays.asList(7, 7, 7, 7, 7);

    goldTokens = 7;


  }

  public void displayInitialData() {
    updateTokens();
    updateDecks();
    System.out.println(cardSlots.get(9).getCard().getDeckColor());
    updateGreenCards();
    updateYellowCards();
    updateBlueCards();
    GameBoardController.getInstance().setImageG1(cardSlots.get(0).getCard().getPath());

    InventorySelfController.getInstance().setImageOwned1("/Assets/emptyImage.png");
    InventorySelfController.getInstance().setImageOwned2("/Assets/emptyImage.png");
    InventorySelfController.getInstance().setImageOwned3("/Assets/emptyImage.png");
    InventorySelfController.getInstance().setImageOwned4("/Assets/emptyImage.png");

    //        InventorySelfController.getInstance().setImageOwned5("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned6("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned7("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned8("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned9("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned10("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned11("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned12("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned13("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned14("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned15("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned16("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned17("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned18("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned19("/Assets/emptyImage.png");
    //        InventorySelfController.getInstance().setImageOwned20("/Assets/emptyImage.png");

    InventorySelfController.getInstance().setImageReserved1("/Assets/emptyImage.png");
    InventorySelfController.getInstance().setImageReserved2("/Assets/emptyImage.png");
    InventorySelfController.getInstance().setImageReserved3("/Assets/emptyImage.png");


  }


  public void takeTokens(Player player, int color, int amount) {
    player.addColorTokens(color, amount);
    colorTokens.set(color, colorTokens.get(color) - amount);
    updateTokens();
  }


  public void returnTokens(Player player, int color, int amount) {
    int change = 0;
    switch (color) {
      case 0 -> change = amount - player.getInventory().getWhiteBonus();
      case 1 -> change = amount - player.getInventory().getBlueBonus();
      case 2 -> change = amount - player.getInventory().getGreenBonus();
      case 3 -> change = amount - player.getInventory().getRedBonus();
      case 4 -> change = amount - player.getInventory().getBlackBonus();
      default -> throw new IllegalStateException();
    }
    System.out.println("Bonus: " + player.getInventory().getGreenBonus());
    System.out.println("Tokens: " + player.getInventory().getGreenTokens());
    change = Math.max(change, 0);
    colorTokens.set(color, colorTokens.get(color) + change);
    player.getInventory().removeColorTokens(color, change);
    updateTokens();

  }


  public void returnGoldToken(Player player, int amount) {
    System.out.print("returned: ");
    System.out.println(amount);
    goldTokens = goldTokens + amount;
    player.getInventory().removeGoldToken(amount);
    updateTokens();
  }


  public void reserveCard(Player player, CardSlot slot) {
    player.getInventory().reserveCard(slot.remove());
    player.getInventory().addGoldToken(1);
    goldTokens = goldTokens - 1;
    slot.refill();
    updateDecks();
    updateBlueCards();
    updateYellowCards();
    updateGreenCards();
    updateTokens();
  }


  public void buyCard(Player player, CardSlot slot) {
    BaseCard card = slot.getCard();
    player.getInventory().addCard(slot.remove());
    slot.refill();
    updateDecks();
    updateBlueCards();
    updateYellowCards();
    updateGreenCards();
    updateTokens();
  }


  public void updateTokens() {
    GameBoardController.getInstance().setWhiteTokenLabel(colorTokens.get(0).toString());
    GameBoardController.getInstance().setBlueTokenLabel(colorTokens.get(1).toString());
    GameBoardController.getInstance().setGreenTokenLabel(colorTokens.get(2).toString());
    GameBoardController.getInstance().setRedTokenLabel(colorTokens.get(3).toString());
    GameBoardController.getInstance().setBlackTokenLabel(colorTokens.get(4).toString());
    GameBoardController.getInstance().setGoldTokenLabel(String.valueOf(goldTokens));
  }


  public void updateDecks() {
    GameBoardController.getInstance().setBlueDeckLabel(String.valueOf(blueDeck.getSize()));
    GameBoardController.getInstance().setOrangeDeckLabel(String.valueOf(yellowDeck.getSize()));
    GameBoardController.getInstance().setGreenDeckLabel(String.valueOf(greenDeck.getSize()));
  }


  public void updateGreenCards() {
    try {
      GameBoardController.getInstance().setImageG1(cardSlots.get(0).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageG1(null);
    }
    try {
      GameBoardController.getInstance().setImageG2(cardSlots.get(1).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageG2(null);
    }
    try {
      GameBoardController.getInstance().setImageG3(cardSlots.get(2).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageG3(null);
    }
    try {
      GameBoardController.getInstance().setImageG4(cardSlots.get(3).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageG4(null);
    }
  }


  public void updateYellowCards() {
    try {
      GameBoardController.getInstance().setImageO1(cardSlots.get(4).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageO1(null);
    }
    try {
      GameBoardController.getInstance().setImageO2(cardSlots.get(5).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageO2(null);
    }
    try {
      GameBoardController.getInstance().setImageO3(cardSlots.get(6).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageO3(null);
    }
    try {
      GameBoardController.getInstance().setImageO4(cardSlots.get(7).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageO4(null);
    }
  }

  public void updateBlueCards() {
    try {
      GameBoardController.getInstance().setImageB1(cardSlots.get(8).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageB1(null);
    }
    try {
      GameBoardController.getInstance().setImageB2(cardSlots.get(9).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageB2(null);
    }
    try {
      GameBoardController.getInstance().setImageB3(cardSlots.get(10).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageB3(null);
    }
    try {
      GameBoardController.getInstance().setImageB4(cardSlots.get(11).getCard().getPath());
    } catch (Exception e) {
      GameBoardController.getInstance().setImageB4(null);
    }
  }

  public void updatePlayer1Tokens() {
    InventorySelfController.getInstance()
        .setPlayer1WhiteTokenAmount(String.valueOf(player1.getInventory().getWhiteTokens()));
    InventorySelfController.getInstance()
        .setPlayer1BlueTokenAmount(String.valueOf(player1.getInventory().getBlueTokens()));
    InventorySelfController.getInstance()
        .setPlayer1GreenTokenAmount(String.valueOf(player1.getInventory().getGreenTokens()));
    InventorySelfController.getInstance()
        .setPlayer1RedTokenAmount(String.valueOf(player1.getInventory().getRedTokens()));
    InventorySelfController.getInstance()
        .setPlayer1BlackTokenAmount(String.valueOf(player1.getInventory().getBlackTokens()));
    InventorySelfController.getInstance()
        .setPlayer1GoldTokenAmount(String.valueOf(player1.getInventory().getGoldTokens()));

    try {

      int whiteAmount = PlayerSelfController.getInstance().usedTokens.get(0);
      int whiteBonus = player1.getInventory().getWhiteBonus();
      int whiteTokenUsedLabel = Math.max(0, whiteAmount - whiteBonus);

      InventorySelfController.getInstance().setPlayer1WhiteTokenUsed(
          String.valueOf(whiteTokenUsedLabel * -1)
      );

//      InventorySelfController.getInstance().setPlayer1WhiteTokenUsed(
//          String.valueOf((PlayerSelfController.getInstance().usedTokens.get(0) * -1)));
    } catch (Exception e) {
      InventorySelfController.getInstance().setPlayer1WhiteTokenUsed("0");
    }
    try {

      int blueAmount = PlayerSelfController.getInstance().usedTokens.get(1);
      int blueBonus = player1.getInventory().getBlueBonus();
      int blueTokenUsedLabel = Math.max(0, blueAmount - blueBonus);

      InventorySelfController.getInstance().setPlayer1BlueTokenUsed(
          String.valueOf(blueTokenUsedLabel * -1)
      );

//      InventorySelfController.getInstance().setPlayer1BlueTokenUsed(
//          String.valueOf((PlayerSelfController.getInstance().usedTokens.get(1) * -1)));
    } catch (Exception e) {
      InventorySelfController.getInstance().setPlayer1BlueTokenUsed("0");
    }
    try {

      int greenAmount = PlayerSelfController.getInstance().usedTokens.get(2);
      int greenBonus = player1.getInventory().getGreenBonus();
      int greenTokenUsedLabel = Math.max(0, greenAmount - greenBonus);

      InventorySelfController.getInstance().setPlayer1GreenTokenUsed(
          String.valueOf(greenTokenUsedLabel * -1)
      );

//      InventorySelfController.getInstance().setPlayer1GreenTokenUsed(
//          String.valueOf((PlayerSelfController.getInstance().usedTokens.get(2) * -1)));
    } catch (Exception e) {
      InventorySelfController.getInstance().setPlayer1GreenTokenUsed("0");
    }
    try {

      int redAmount = PlayerSelfController.getInstance().usedTokens.get(3);
      int redBonus = player1.getInventory().getRedBonus();
      int redTokenUsedLabel = Math.max(0, redAmount - redBonus);

      InventorySelfController.getInstance().setPlayer1RedTokenUsed(
          String.valueOf(redTokenUsedLabel * -1)
      );

//      InventorySelfController.getInstance().setPlayer1RedTokenUsed(
//          String.valueOf((PlayerSelfController.getInstance().usedTokens.get(3) * -1)));
    } catch (Exception e) {
      InventorySelfController.getInstance().setPlayer1RedTokenUsed("0");
    }
    try {

      int blackAmount = PlayerSelfController.getInstance().usedTokens.get(4);
      int blackBonus = player1.getInventory().getBlackBonus();
      int blackTokenUsedLabel = Math.max(0, blackAmount - blackBonus);

      InventorySelfController.getInstance().setPlayer1BlackTokenUsed(
          String.valueOf(blackTokenUsedLabel * -1)
      );

//      InventorySelfController.getInstance().setPlayer1BlackTokenUsed(
//          String.valueOf((PlayerSelfController.getInstance().usedTokens.get(4) * -1)));
    } catch (Exception e) {
      InventorySelfController.getInstance().setPlayer1BlackTokenUsed("0");
    }
    try {
      InventorySelfController.getInstance().setPlayer1GoldTokenUsed(
          String.valueOf((PlayerSelfController.getInstance().usedTokens.get(5) * -1)));
    } catch (Exception e) {
      InventorySelfController.getInstance().setPlayer1GoldTokenUsed("0");
    }
  }

  public static Game getInstance() {
    return Game.instance;
  }

  public static void closeGame() {
    instance = null;
  }

  public void setDeckSize(DeckColor color, int size) {
    switch (color) {
      case GREEN -> greenDeckSize = size;
      case YELLOW -> yellowDeckSize = size;
      case BLUE -> blueDeckSize = size;
      case REDL1 -> redL1DeckSize = size;
      case REDL2 -> redL2DeckSize = size;
      case REDL3 -> redL3DeckSize = size;
      default -> throw new IllegalStateException();
    }
  }

  public void addCardToSlot(int slot, BaseCard card) {
    cardSlots.get(slot).setCard(card);
  }

  public void addNobleToSlot(int slot, Noble noble) {
    nobleSlots.get(slot).addNoble(noble);
  }

  public void removeCardFromSlot(int slot) {
    cardSlots.get(slot).remove();
  }

  public void removeNobleFromSlot(int slot) {
    nobleSlots.get(slot).removeNoble();
  }

  public int getCurrentPlayer() {
    return currentPlayer;
  }
  public void setCurrentPlayer(int playerNumber) {
    currentPlayer = playerNumber;
  }

  public void setColorTokenAmount(int color, int amount) {
    colorTokens.set(color, amount);
  }

  public void setGoldTokenAmount(int amount) {
    goldTokens = amount;
  }

  public Player getMyPlayer() {
    return player1;
  }









}
