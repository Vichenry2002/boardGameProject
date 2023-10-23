package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.Board;
import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.CitySlot;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.NobleSlot;
import ca.mcgill.splendorserver.gameelements.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store the data of a saved game.
 */
public class Savegame {

  // Decks contain an ordered list of the cards in them
  private final List<String> greenDeck = new ArrayList<>();
  private final List<String> yellowDeck = new ArrayList<>();
  private final List<String> blueDeck = new ArrayList<>();
  private final List<String> redL1Deck = new ArrayList<>();
  private final List<String> redL2Deck = new ArrayList<>();
  private final List<String> redL3Deck = new ArrayList<>();

  // Map of player inventories, uses the same InventoryView class that was used for storing the
  // game state
  private final Map<String, InventoryView> playerInventories = new HashMap<>();

  // Player information
  private final Map<String, String> playerNames = new HashMap<>();
  private final Map<String, String> playerColors = new HashMap<>();
  private final Map<String, Integer> playerPoints = new HashMap<>();

  // Elements on the board stored in an ordered list
  private final List<String> cardSlots = new ArrayList<>();
  private final List<String> nobleSlots = new ArrayList<>();
  private final List<String> citySlots = new ArrayList<>();

  // Player who will perform the next move
  private final int currentPlayer;

  // Token amounts on the board
  private final int whiteTokenAmount;
  private final int blueTokenAmount;
  private final int greenTokenAmount;
  private final int redTokenAmount;
  private final int blackTokenAmount;
  private final int goldTokenAmount;
  private final Extensions extensions;
  private final TurnStatus status;


  /**
   * Constructor to create a save game object which will be used to save a game to a file by
   * converting an instance of this class to a Json string.
   *
   * @param game the game from which to generate a save.
   */
  public Savegame(Game game) {
    extensions = game.getExtensions();
    status = game.getTurnStatus();

    Board board = game.getGameBoard();

    // Add cards to decks
    List<String> greenD = game.getGameBoard().getGreenDeck().asData();
    greenDeck.addAll(greenD);

    List<String> yellowD = game.getGameBoard().getYellowDeck().asData();
    yellowDeck.addAll(yellowD);

    List<String> blueD = game.getGameBoard().getBlueDeck().asData();
    blueDeck.addAll(blueD);

    List<String> redL1D = game.getGameBoard().getRedL1Deck().asData();
    redL1Deck.addAll(redL1D);

    List<String> redL2D = game.getGameBoard().getRedL2Deck().asData();
    redL2Deck.addAll(redL2D);

    List<String> redL3D = game.getGameBoard().getRedL3Deck().asData();
    redL3Deck.addAll(redL3D);

    // Add player information and inventories
    for (Player player : game.getPlayers()) {
      String playerNumber = String.valueOf(player.getNumber());
      String playerKey = "Player".concat(playerNumber);

      // Inventories
      playerInventories.put(playerKey, new InventoryView(player.getInventory()));

      //player information
      playerNames.put(playerKey, player.getName());
      playerColors.put(playerKey, player.getPlayerColor());
      playerPoints.put(playerKey, player.getPrestigePoints());

    }

    // Add cards on the board ("Empty" if no card on a slot)
    List<CardSlot> slots = game.getCardsOnBoard();
    for (CardSlot slot : slots) {
      if (slot.hasCard()) {
        cardSlots.add(slot.getCardString());
      } else {
        cardSlots.add("Empty");
      }
    }

    // Nobles on the board ("Empty" if no noble on a slot)
    List<NobleSlot> slotsNobles = game.getNoblesOnBoard();
    for (NobleSlot slot : slotsNobles) {
      if (slot.hasNoble()) {
        nobleSlots.add(slot.getNobleString());
      } else {
        nobleSlots.add("Empty");
      }
    }

    //Cities on the board
    List<CitySlot> slotsCities = game.getCitiesOnBoard();
    for (CitySlot slot : slotsCities) {
      if (slot.hasCard()) {
        citySlots.add(String.valueOf(slot.getCityNumber()));
      } else {
        citySlots.add(String.valueOf(-1));
      }
    }

    // Set the current player
    currentPlayer = game.getCurrentPlayer();

    // Set the amount of tokens on the board
    whiteTokenAmount = game.getWhiteTokenAmount();
    blueTokenAmount = game.getBlueTokenAmount();
    greenTokenAmount = game.getGreenTokenAmount();
    redTokenAmount = game.getRedTokenAmount();
    blackTokenAmount = game.getBlackTokenAmount();
    goldTokenAmount = game.getGoldTokenAmount();


  }

  /**
   * Getter for the cards in the green deck.
   *
   * @return list of card strings containing the data of each card
   */
  public List<String> getGreenDeck() {
    return greenDeck;
  }

  /**
   * Getter for the cards in the yellow deck.
   *
   * @return list of card strings containing the data of each card
   */
  public List<String> getYellowDeck() {
    return yellowDeck;
  }

  /**
   * Getter for the cards in the blue deck.
   *
   * @return list of card strings containing the data of each card
   */
  public List<String> getBlueDeck() {
    return blueDeck;
  }

  /**
   * Getter for the cards in the red level 1 deck.
   *
   * @return list of card strings containing the data of each card
   */
  public List<String> getRedL1Deck() {
    return redL1Deck;
  }

  /**
   * Getter for the cards in the red level 2 deck.
   *
   * @return list of card strings containing the data of each card
   */
  public List<String> getRedL2Deck() {
    return redL2Deck;
  }

  /**
   * Getter for the cards in the red level 3 deck.
   *
   * @return list of card strings containing the data of each card
   */
  public List<String> getRedL3Deck() {
    return redL3Deck;
  }

  /**
   * Getter for the map containing the inventories of all players.
   *
   * @return inventory views of all player inventories
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
   * Getter for the list containing the cities on the board, ordered from left to right.
   *
   * @return city slots on the board
   */
  public List<String> getCitiesOnBoard() {
    return citySlots;
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
   * Getter for the extensions of the game (Orient, Orient + Cities, etc..).
   *
   * @return extensions enum
   */
  public Extensions getExtensions() {
    return extensions;
  }

  /**
   * Getter for the turn status.
   *
   * @return TurnStatus enum
   */
  public TurnStatus getTurnStatus() {
    return status;
  }
}
