package splendor.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store game state information that players should have access to,
 * whenever a get request for the game state is made, a new game state object
 * will be created and sent to the clients.
 */
public class GameState {

  public int greenDeck;
  public int blueDeck;
  public int yellowDeck;
  public int redL1Deck;
  public int redL2Deck;
  public int redL3Deck;
  public Map<String, InventoryView> playerInventories = new HashMap<>();
  public Map<String, String> playerNames = new HashMap<>();
  public Map<String, String> playerColors = new HashMap<>();
  public Map<String, Integer> playerPoints = new HashMap<>();
  public List<String> cardsOnBoard = new ArrayList<>();
  public List<String> noblesOnBoard = new ArrayList<>();
  public final List<Integer> citySlots = new ArrayList<>();
  public int currentPlayer;
  public int whiteTokenSelected;
  public int blueTokenSelected;
  public int greenTokenSelected;
  public int redTokenSelected;
  public int blackTokenSelected;
  public int goldTokenSelected;
  public int whiteTokenAmount;
  public int blueTokenAmount;
  public int greenTokenAmount;
  public int redTokenAmount;
  public int blackTokenAmount;
  public int goldTokenAmount;
  public int selectedCard; //0 if none, 1-12 from bottom left to top right,
  // 13-24 if reserve selection from bottom left to top right
  public int winningPlayer;
  public String status;

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
    return cardsOnBoard;
  }

  /**
   * Getter for the list containing the cards on the board, ordered from left to right.
   *
   * @return noble slots on the board
   */
  public List<String> getNoblesOnBoard() {
    return noblesOnBoard;
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

  public int getWinningPlayer() { return winningPlayer; };

  /**
   * Getter for the turn status.
   *
   * @return TurnStatus string
   */
  public String getStatus() {
    return status;
  }

  /**
   * Getter for the city slots.
   *
   * @return city slots
   */
  public List<Integer> getCitySlots() {
    return citySlots;
  }







}
