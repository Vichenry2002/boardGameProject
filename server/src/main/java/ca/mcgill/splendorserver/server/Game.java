package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.Board;
import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.CitySlot;
import ca.mcgill.splendorserver.gameelements.DeckColor;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Noble;
import ca.mcgill.splendorserver.gameelements.NobleSlot;
import ca.mcgill.splendorserver.gameelements.OrientCard;
import ca.mcgill.splendorserver.gameelements.Player;
import ca.mcgill.splendorserver.gameelements.TradingPostType;
import java.util.ArrayList;
import java.util.List;

/**
 * Game class.
 */
public class Game {
  private Player player1;
  private Player player2;
  private Player player3;
  private Player player4;
  private int numberOfPlayers;
  private Board gameBoard;
  private int currentPlayer = 1;
  private int winningPlayer = -1;
  private List<Player> players = new ArrayList<>();
  private int whiteTokenSelected = 0;
  private int blueTokenSelected = 0;
  private int greenTokenSelected = 0;
  private int redTokenSelected = 0;
  private int blackTokenSelected = 0;
  private int goldTokenSelected = 0;
  private int selectedCard = 0; //0 if none, 1-12 from bottom left to top right,
  // 13-24 if reserve selection from bottom left to top right
  private TurnStatus status = TurnStatus.CHOOSE_ACTION;
  private Extensions extensions;

  /**
   * Constructor for creating a new game.
   *
   * @param playerList players in the game
   * @param extensions the extensions used in the game
   */
  public Game(List<Player> playerList, Extensions extensions) {
    assert playerList.size() > 1 && playerList.size() <= 4;
    player1 = playerList.get(0);
    player2 = playerList.get(1);
    if (playerList.size() > 2) {
      player3 = playerList.get(2);
    }
    if (playerList.size() > 3) {
      player3 = playerList.get(3);
    }
    numberOfPlayers = playerList.size();
    gameBoard = new Board(numberOfPlayers);
    players = playerList;

    for (Player p : players) {
      for (int color = 0; color < 5; color++) {

        // UNCOMMENT THIS WHEN TESTING BUYING CARDS

        //p.getInventory().addColorTokens(color, 50);
      }
    }

    this.extensions = extensions;

  }

  /**
   * Constructor for creating a game from a saved game.
   *
   * @param playerList players in the game
   * @param save       state of the game when it was saved
   */
  public Game(List<Player> playerList, Savegame save) {
    assert playerList.size() > 1 && playerList.size() <= 4;

    player1 = playerList.get(0);
    player2 = playerList.get(1);
    if (playerList.size() > 2) {
      player3 = playerList.get(2);
    }
    if (playerList.size() > 3) {
      player4 = playerList.get(3);
    }
    numberOfPlayers = playerList.size();
    players = playerList;

    gameBoard = new Board(numberOfPlayers, save);

    player1.setPlayerNumber(1);
    player2.setPlayerNumber(2);
    if (playerList.size() > 2) {
      player3.setPlayerNumber(3);
    }
    if (playerList.size() > 3) {
      player4.setPlayerNumber(4);
    }

    currentPlayer = save.getCurrentPlayer();

    extensions = save.getExtensions();
    status = save.getTurnStatus();

    // Load each player's inventory
    for (int i = 0; i < playerList.size(); i++) {
      String playerStr = "Player".concat(String.valueOf(i + 1));

      // Add owned cards
      for (int j = 0; j < save.getPlayerInventories().get(playerStr).getAllCards().size(); j++) {
        String cardStr = save.getPlayerInventories().get(playerStr).getAllCards().get(j);
        DeckColor color = DeckColor.valueOf(
            save.getPlayerInventories().get(playerStr)
                .getCardDeckColors().get(j));
        BaseCard card;
        if (color == DeckColor.GREEN || color == DeckColor.YELLOW || color == DeckColor.BLUE) {
          card = new BaseCard(cardStr, color);
        } else {
          card = OrientCard.getOrientCard(cardStr, color);
        }
        players.get(i).getInventory().addCard(card);
      }

      // Add reserved cards
      for (int j = 0; j < save.getPlayerInventories().get(playerStr).getReservedCards().size();
           j++) {
        String cardStr = save.getPlayerInventories().get(playerStr).getReservedCards().get(j);
        DeckColor color = DeckColor.valueOf(
            save.getPlayerInventories().get(playerStr)
                .getReservedDeckColors().get(j));
        BaseCard card;
        if (color == DeckColor.GREEN || color == DeckColor.YELLOW || color == DeckColor.BLUE) {
          card = new BaseCard(cardStr, color);
        } else {
          card = OrientCard.getOrientCard(cardStr, color);
        }
        players.get(i).getInventory().reserveCard(card);
      }

      // Add owned nobles
      for (int j = 0; j < save.getPlayerInventories().get(playerStr).getOwnedNobles().size(); j++) {
        String nobleStr = save.getPlayerInventories().get(playerStr).getOwnedNobles().get(j);
        Noble noble = new Noble(nobleStr);
        players.get(i).getInventory().addNoble(noble);
      }

      // Add owned trading posts
      for (String tradingPost : save.getPlayerInventories().get(playerStr).getOwnedTradePosts()) {
        TradingPostType type = TradingPostType.valueOf(tradingPost);
        players.get(i).getInventory().addTradePosts(type);
      }

      // Add reserved nobles
      for (int j = 0; j < save.getPlayerInventories().get(playerStr)
          .getReservedNobles().size(); j++) {
        String nobleStr = save.getPlayerInventories().get(playerStr).getReservedNobles().get(j);
        Noble noble = new Noble(nobleStr);
        players.get(i).getInventory().reserveNoble(noble);
      }

      // Add owned tokens
      Player curP = players.get(i);
      curP.addColorTokens(0,
          save.getPlayerInventories().get(playerStr).getWhiteTokens());
      curP.addColorTokens(1,
          save.getPlayerInventories().get(playerStr).getBlueTokens());
      curP.addColorTokens(2,
          save.getPlayerInventories().get(playerStr).getGreenTokens());
      curP.addColorTokens(3,
          save.getPlayerInventories().get(playerStr).getRedTokens());
      curP.addColorTokens(4,
          save.getPlayerInventories().get(playerStr).getBlackTokens());
      curP.getInventory().addGoldToken(
          save.getPlayerInventories().get(playerStr).getGoldTokens());
    }
  }

  /**
   * Method to check the extensions used in this game.
   *
   * @return extensions enum
   */
  public Extensions getExtensions() {
    return extensions;
  }

  /**
   * Getter for the list of all the card slots on the board.
   *
   * @return list of card slots on board
   */
  public List<CardSlot> getCardsOnBoard() {
    return gameBoard.getCardSlots();
  }

  /**
   * TODO.
   *
   * @return TODO
   */
  public List<NobleSlot> getNoblesOnBoard() {
    return gameBoard.getNobleSlots();
  }


  /**
   * Getter for the number of cards left in the green deck.
   *
   * @return cards left in green deck
   */
  public int getGreenDeckSize() {
    return gameBoard.getGreenDeck().getSize();
  }

  /**
   * Getter for the number of cards left in the yellow deck.
   *
   * @return cards left in yellow deck
   */
  public int getYellowDeckSize() {
    return gameBoard.getYellowDeck().getSize();
  }

  /**
   * Getter for the number of cards left in the blue deck.
   *
   * @return cards left in blue deck
   */
  public int getBlueDeckSize() {
    return gameBoard.getBlueDeck().getSize();
  }

  /**
   * Getter for the number of cards left in the red level 1 deck.
   *
   * @return cards left in red level 1 deck
   */
  public int getRedL1DeckSize() {
    return gameBoard.getRedL1Deck().getSize();
  }

  /**
   * Getter for the number of cards left in the red level 2 deck.
   *
   * @return cards left in red level 2 deck
   */
  public int getRedL2DeckSize() {
    return gameBoard.getRedL2Deck().getSize();
  }

  /**
   * Getter for the number of cards left in the red level 3 deck.
   *
   * @return cards left in red level 3 deck
   */
  public int getRedL3DeckSize() {
    return gameBoard.getRedL3Deck().getSize();
  }

  /**
   * Getter for the list of players that are part of the game.
   *
   * @return list of players
   */
  public List<Player> getPlayers() {
    return players;
  }


  /**
   * Getter for the current player.
   *
   * @return current player
   */
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Getter for the amount of white tokens selected by the current player.
   *
   * @return amount of white tokens selected
   */
  public int getWhiteTokenSelected() {
    return whiteTokenSelected;
  }

  /**
   * Getter for the amount of blue tokens selected by the current player.
   *
   * @return amount of blue tokens selected
   */
  public int getBlueTokenSelected() {
    return blueTokenSelected;
  }

  /**
   * Getter for the amount of green tokens selected by the current player.
   *
   * @return amount of green tokens selected
   */
  public int getGreenTokenSelected() {
    return greenTokenSelected;
  }

  /**
   * Getter for the amount of red tokens selected by the current player.
   *
   * @return amount of red tokens selected
   */
  public int getRedTokenSelected() {
    return redTokenSelected;
  }

  /**
   * Getter for the amount of black tokens selected by the current player.
   *
   * @return amount of black tokens selected
   */
  public int getBlackTokenSelected() {
    return blackTokenSelected;
  }

  /**
   * Getter for the amount of gold tokens selected by the current player.
   *
   * @return amount of gold tokens selected
   */
  public int getGoldTokenSelected() {
    return goldTokenSelected;
  }

  /**
   * Getter for the amount of white tokens remaining on the board.
   *
   * @return amount of white tokens on board
   */
  public int getWhiteTokenAmount() {
    return gameBoard.getWhiteTokenAmount();
  }

  /**
   * Getter for the amount of blue tokens remaining on the board.
   *
   * @return amount of blue tokens on board
   */
  public int getBlueTokenAmount() {
    return gameBoard.getBlueTokenAmount();
  }

  /**
   * Getter for the amount of green tokens remaining on the board.
   *
   * @return amount of green tokens on board
   */
  public int getGreenTokenAmount() {
    return gameBoard.getGreenTokenAmount();
  }

  /**
   * Getter for the amount of red tokens remaining on the board.
   *
   * @return amount of red tokens on board
   */
  public int getRedTokenAmount() {
    return gameBoard.getRedTokenAmount();
  }

  /**
   * Getter for the amount of black tokens remaining on the board.
   *
   * @return amount of black tokens on board
   */
  public int getBlackTokenAmount() {
    return gameBoard.getBlackTokenAmount();
  }

  /**
   * Getter for the amount of gold tokens remaining on the board.
   *
   * @return amount of gold tokens on board
   */
  public int getGoldTokenAmount() {
    return gameBoard.getGoldTokenAmount();
  }

  /**
   * Getter for the number corresponding to the card selected by the current player,
   * 0 if no card selected, 1-12 for cards from bottom left to top right,
   * 13-24 for reserve selections from bottom left to top right.
   *
   * @return selected card
   */
  public int getSelectedCard() {
    return selectedCard;
  }

  /**
   * Returns a reference to the board of the game.
   *
   * @return board
   */
  public Board getGameBoard() {
    return gameBoard;
  }

  /**
   * Changes the current player / turn to be the next player, if the current player is the last
   * player, it switches to the first player.
   */
  public void nextPlayer() {
    if (currentPlayer == numberOfPlayers) {
      currentPlayer = 1;
    } else {
      currentPlayer += 1;
    }
  }

  /**
   * Getter for the game's turn status.
   *
   * @return status of the game
   */
  public TurnStatus getTurnStatus() {
    return status;
  }

  /**
   * Changes the game's turn status.
   *
   * @param newStatus the status to change to
   */
  public void setTurnStatus(TurnStatus newStatus) {
    status = newStatus;
  }

  /**
   * Setting winning player.
   *
   * @param num is the number of the winning player.
   */
  public void setWinningPlayer(int num) {
    winningPlayer = num;
  }

  /**
   * Get winning player.
   *
   * @return winning player number.
   */
  public int getWinningPlayer() {
    return winningPlayer;
  }

  /**
   * Getter for the cities on the board.
   *
   * @return city slots
   */
  public List<CitySlot> getCitiesOnBoard() {
    return gameBoard.getCitySlots();
  }


}
