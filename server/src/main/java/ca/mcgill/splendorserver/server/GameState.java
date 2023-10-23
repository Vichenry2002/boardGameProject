package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.CitySlot;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.NobleSlot;
import ca.mcgill.splendorserver.gameelements.Player;
import eu.kartoffelquadrat.asyncrestlib.BroadcastContent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store game state information that players should have access to,
 * whenever a get request for the game state is made, a new game state object
 * will be created and sent to the clients.
 */
public class GameState implements BroadcastContent {

  private final int greenDeck;
  private final int blueDeck;
  private final int yellowDeck;
  private final int redL1Deck;
  private final int redL2Deck;
  private final int redL3Deck;
  private final Map<String, InventoryView> playerInventories = new HashMap<>();
  private final Map<String, String> playerNames = new HashMap<>();
  private final Map<String, String> playerColors = new HashMap<>();
  private final Map<String, Integer> playerPoints = new HashMap<>();
  private final List<String> cardSlots = new ArrayList<>();
  private final List<String> nobleSlots = new ArrayList<>();
  private final List<Integer> citySlots = new ArrayList<>();
  private final int currentPlayer;
  private final int whiteTokenSelected;
  private final int blueTokenSelected;
  private final int greenTokenSelected;
  private final int redTokenSelected;
  private final int blackTokenSelected;
  private final int goldTokenSelected;
  private final int whiteTokenAmount;
  private final int blueTokenAmount;
  private final int greenTokenAmount;
  private final int redTokenAmount;
  private final int blackTokenAmount;
  private final int goldTokenAmount;
  private final int selectedCard; //0 if none, 1-12 from bottom left to top right,
  // 13-24 if reserve selection from bottom left to top right
  private final Extensions extensions;
  private final String status;

  private final int winningPlayer;


  /**
   * Constructor for GameState, initialize fields.
   *
   * @param game the game which we want to get the state of
   */
  public GameState(Game game) {
    extensions = game.getExtensions();
    winningPlayer = game.getWinningPlayer();

    if (game.getTurnStatus() == null) {
      status = String.valueOf(TurnStatus.CHOOSE_ACTION);
      game.setTurnStatus(TurnStatus.CHOOSE_ACTION);
    } else {
      status = game.getTurnStatus().toString();
    }
    
    greenDeck = game.getGreenDeckSize();
    blueDeck = game.getBlueDeckSize();
    yellowDeck = game.getYellowDeckSize();
    redL1Deck = game.getRedL1DeckSize();
    redL2Deck = game.getRedL2DeckSize();
    redL3Deck = game.getRedL3DeckSize();


    for (Player player : game.getPlayers()) {
      playerInventories.put("Player".concat(String.valueOf(player.getNumber())),
          new InventoryView(player.getInventory()));
      playerNames.put("Player".concat(String.valueOf(player.getNumber())),
          player.getName());
      playerColors.put("Player".concat(String.valueOf(player.getNumber())),
          player.getPlayerColor());
      playerPoints.put("Player".concat(String.valueOf(player.getNumber())),
          player.getPrestigePoints());
    }


    List<CardSlot> slots = game.getCardsOnBoard();
    for (CardSlot slot : slots) {
      cardSlots.add(slot.getCardString());
    }

    List<NobleSlot> slotsNobles = game.getNoblesOnBoard();
    for (NobleSlot slot : slotsNobles) {
      nobleSlots.add(slot.getNobleString());
    }

    for (CitySlot citySlot : game.getCitiesOnBoard()) {
      citySlots.add(citySlot.getCityNumber());
    }

    currentPlayer = game.getCurrentPlayer();

    whiteTokenAmount = game.getWhiteTokenAmount();
    blueTokenAmount = game.getBlueTokenAmount();
    greenTokenAmount = game.getGreenTokenAmount();
    redTokenAmount = game.getRedTokenAmount();
    blackTokenAmount = game.getBlackTokenAmount();
    goldTokenAmount = game.getGoldTokenAmount();

    whiteTokenSelected = game.getWhiteTokenSelected();
    blueTokenSelected = game.getBlueTokenSelected();
    greenTokenSelected = game.getGreenTokenSelected();
    redTokenSelected = game.getRedTokenSelected();
    blackTokenSelected = game.getBlackTokenSelected();
    goldTokenSelected = game.getGoldTokenSelected();

    selectedCard = game.getSelectedCard();


  }

  /**
   * Getter for the extensions of the game (Orient, Orient + Cities, etc..).
   *
   * @return extensions enum
   */
  public Extensions getExtensions() {
    return extensions;
  }

  /**
   * Getter for the number of cards left in the green deck.
   *
   * @return the number of cards left in the green deck
   */
  public int getGreenDeck() {
    return greenDeck;
  }

  /**
   * Getter for the number of cards left in the yellow deck.
   *
   * @return the number of cards left in the yellow deck
   */
  public int getYellowDeck() {
    return yellowDeck;
  }

  /**
   * Getter for the number of cards left in the blue deck.
   *
   * @return the number of cards left in the blue deck
   */
  public int getBlueDeck() {
    return blueDeck;
  }

  /**
   * Getter for the number of cards left in the red level 1 deck.
   *
   * @return the number of cards left in the red level 1 deck
   */
  public int getRedL1Deck() {
    return redL1Deck;
  }

  /**
   * Getter for the number of cards left in the red level 2 deck.
   *
   * @return the number of cards left in the red level 2 deck
   */
  public int getRedL2Deck() {
    return redL2Deck;
  }

  /**
   * Getter for the number of cards left in the red level 3 deck.
   *
   * @return the number of cards left in the red level 3 deck
   */
  public int getRedL3Deck() {
    return redL3Deck;
  }

  /**
   * Getter for the map containing the inventories of all players.
   *
   * @return player inventories
   */
  public Map<String, InventoryView> getPlayerInventories() {
    return playerInventories;
  }

  /**
   * Getter for the map containing the names of all players.
   *
   * @return player names
   */
  public Map<String, String> getPlayerNames() {
    return playerNames;
  }

  /**
   * Getter for the map containing the colors of all players.
   *
   * @return player colors
   */
  public Map<String, String> getPlayerColors() {
    return playerColors;
  }

  /**
   * Getter for the map containing the prestige points of all players.
   *
   * @return player prestige points
   */
  public Map<String, Integer> getPlayerPoints() {
    return playerPoints;
  }

  /**
   * Getter for the list containing the cards on the board, ordered from bottom left to top right.
   *
   * @return card slots on the board
   */
  public List<String> getCardsOnBoard() {
    return cardSlots;
  }

  /**
   * Getter for the list containing the nobles on the board, ordered from left to right.
   *
   * @return noble slots on the board
   */
  public List<String> getNoblesOnBoard() {
    return nobleSlots;
  }

  /**
   * Getter for the number of the player whose turn it is currently.
   *
   * @return current player
   */
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Getter for the number of white tokens selected by the current player.
   *
   * @return number of white tokens selected
   */
  public int getWhiteTokenSelected() {
    return whiteTokenSelected;
  }

  /**
   * Getter for the number of blue tokens selected by the current player.
   *
   * @return number of blue tokens selected
   */
  public int getBlueTokenSelected() {
    return blueTokenSelected;
  }

  /**
   * Getter for the number of green tokens selected by the current player.
   *
   * @return number of green tokens selected
   */
  public int getGreenTokenSelected() {
    return greenTokenSelected;
  }

  /**
   * Getter for the number of red tokens selected by the current player.
   *
   * @return number of red tokens selected
   */
  public int getRedTokenSelected() {
    return redTokenSelected;
  }

  /**
   * Getter for the number of black tokens selected by the current player.
   *
   * @return number of black tokens selected
   */
  public int getBlackTokenSelected() {
    return blackTokenSelected;
  }

  /**
   * Getter for the number of gold tokens selected by the current player.
   *
   * @return number of gold tokens selected
   */
  public int getGoldTokenSelected() {
    return goldTokenSelected;
  }

  /**
   * Getter for the number of white tokens left on the board.
   *
   * @return number of white tokens left on the board
   */
  public int getWhiteTokenAmount() {
    return whiteTokenAmount;
  }

  /**
   * Getter for the number of blue tokens left on the board.
   *
   * @return number of blue tokens left on the board
   */
  public int getBlueTokenAmount() {
    return blueTokenAmount;
  }

  /**
   * Getter for the number of green tokens left on the board.
   *
   * @return number of green tokens left on the board
   */
  public int getGreenTokenAmount() {
    return greenTokenAmount;
  }

  /**
   * Getter for the number of red tokens left on the board.
   *
   * @return number of red tokens left on the board
   */
  public int getRedTokenAmount() {
    return redTokenAmount;
  }

  /**
   * Getter for the number of black tokens left on the board.
   *
   * @return number of black tokens left on the board
   */
  public int getBlackTokenAmount() {
    return blackTokenAmount;
  }

  /**
   * Getter for the number of gold tokens left on the board.
   *
   * @return number of gold tokens left on the board
   */
  public int getGoldTokenAmount() {
    return goldTokenAmount;
  }

  /**
   * Getter for the card which is selected by the current player, 0 if no card is selected,
   * 1-12 for selected cards from bottom left to top right, 13-24 for reserve selections from
   * bottom left to top right.
   *
   * @return number of the card selected
   */
  public int getSelectedCard() {
    return selectedCard;
  }

  /**
   * Getter for winning player.
   *
   * @return winning player number.
   */
  public int getWinningPlayer() {
    return winningPlayer;
  }

  /**
   * Getter for city slot numbers.
   *
   * @return city number list.
   */
  public List<Integer> getCitySlots() {
    return citySlots;
  }


  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Getter for the turn status.
   *
   * @return TurnStatus enum
   */
  public String getStatus() {
    return status;
  }
}
