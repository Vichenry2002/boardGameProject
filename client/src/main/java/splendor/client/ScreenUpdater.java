package splendor.client;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import splendor.gameelements.BaseCard;
import splendor.gameelements.DeckColor;
import splendor.gameelements.Inventory;
import splendor.gameelements.Noble;
import splendor.gameelements.NobleSlot;
import splendor.gameelements.OrientCard;
import splendor.gameelements.Player;
import splendor.gameelements.TradingPostType;

public class ScreenUpdater {

  private static Map<Integer, Player> playerMap = new HashMap<>();
  private static Map<String, Integer> playerNumberMap = new HashMap<>();
  private static GameState oldGameState;
  private static int numberOfPlayers;
  private static TradingPostsBox tradingPostsBox = new TradingPostsBox();

  /**
   * Used when exiting game so the next game loads properly.
   */
  public static void clearState() {
    oldGameState = null;
  }

  /**
   * Method to update the screen when receiving a new game state.
   *
   * @param newGameState new game state
   */
  public static void updateScreen(GameState newGameState) {

    if (oldGameState == null) {
      initializeState(newGameState);
      oldGameState = newGameState;
    }
    //check if winning player exists
    System.out.println("WINNING PLAYER " + newGameState.getWinningPlayer());
    if(newGameState.getWinningPlayer() != -1) {
      String winning_player = "Player"+newGameState.getWinningPlayer();
      EndGameController.getInstance().setLabel(newGameState.playerNames.get(winning_player));
      GameController.getInstance().setEndGameVisible(true);
    }

    //updating cities
    if (oldGameState.getCitySlots() != newGameState.getCitySlots()) {
      if (newGameState.getCitySlots().get(0) == -1) {
        GameBoardController.getInstance().removeCity1();

      }
      if (newGameState.getCitySlots().get(1) == -1) {
        GameBoardController.getInstance().removeCity2();

      }
      if (newGameState.getCitySlots().get(2) == -1) {
        GameBoardController.getInstance().removeCity3();
      }
    }

    //updating trading posts
    boolean changed = false;

    for(String playerStr : newGameState.getPlayerInventories().keySet()) {

      Player player = playerMap.get(playerNumberMap.get(playerStr));
      InventoryView oldInventory = oldGameState.getPlayerInventories().get(playerStr);
      InventoryView newInventory = newGameState.getPlayerInventories().get(playerStr);

      if (!oldInventory.getOwnedTradePosts().equals(newInventory.getOwnedTradePosts())) {
        changed = true;
        player.clearTradingPosts();

        for (String postStr : newGameState.getPlayerInventories().get(playerStr)
            .getOwnedTradePosts()) {

          TradingPostType post = TradingPostType.valueOf(postStr);
          player.addTradingPost(post);
        }
      }
    }

    if (changed) {
      tradingPostsBox.clearTradingPosts();

      for (Player player : Game.getInstance().players) {
        for (TradingPostType tp : TradingPostType.values()) {
          if (player.hasTradingPost(tp)) {
            tradingPostsBox.addTradingPost(tp, "#".concat(player.getColor()));
          }
        }
      }

    }

    //update decks
    if (oldGameState.getGreenDeck() != newGameState.getGreenDeck()) {
      // Related JavaFX object : GameBoardController -> greenDeckLabel
      GameBoardController.getInstance().setGreenDeckLabel(
          String.valueOf(newGameState.getGreenDeck()));
      Game.getInstance().setDeckSize(DeckColor.GREEN, newGameState.getGreenDeck());
    }
    if (oldGameState.getYellowDeck() != newGameState.getYellowDeck()) {
      // Related JavaFX object : GameBoardController -> orangeDeckLabel
      GameBoardController.getInstance().setOrangeDeckLabel(
          String.valueOf(newGameState.getYellowDeck()));
      Game.getInstance().setDeckSize(DeckColor.YELLOW, newGameState.getYellowDeck());
    }
    if (oldGameState.getBlueDeck() != newGameState.getBlueDeck()) {
      // Related JavaFX object : GameBoardController -> blueDeckLabel
      GameBoardController.getInstance().setBlueDeckLabel(
          String.valueOf(newGameState.getBlueDeck()));
      Game.getInstance().setDeckSize(DeckColor.BLUE, newGameState.getBlueDeck());
    }
    if (oldGameState.getRedL1Deck() != newGameState.getRedL1Deck()) {
      // Related JavaFX object : GameBoardController -> greenDeckLabel
      GameBoardController.getInstance().setRedL1DeckLabel(
          String.valueOf(newGameState.getRedL1Deck()));
      Game.getInstance().setDeckSize(DeckColor.REDL1, newGameState.getRedL1Deck());
    }
    if (oldGameState.getRedL2Deck() != newGameState.getRedL2Deck()) {
      // Related JavaFX object : GameBoardController -> orangeDeckLabel
      GameBoardController.getInstance().setRedL2DeckLabel(
          String.valueOf(newGameState.getRedL2Deck()));
      Game.getInstance().setDeckSize(DeckColor.REDL2, newGameState.getRedL2Deck());
    }
    if (oldGameState.getRedL3Deck() != newGameState.getRedL3Deck()) {
      // Related JavaFX object : GameBoardController -> blueDeckLabel
      GameBoardController.getInstance().setRedL3DeckLabel(
          String.valueOf(newGameState.getRedL3Deck()));
      Game.getInstance().setDeckSize(DeckColor.REDL3, newGameState.getRedL3Deck());
    }

    //update inventories
    if (!oldGameState.getPlayerInventories().get("Player1")
        .equals(newGameState.getPlayerInventories().get("Player1"))) {
      int localNumber = playerNumberMap.get("Player1");
      updateInventory(localNumber, oldGameState.getPlayerInventories().get("Player1"),
          newGameState.getPlayerInventories().get("Player1"));
    }
    if (!oldGameState.getPlayerInventories().get("Player2")
        .equals(newGameState.getPlayerInventories().get("Player2"))) {
      int localNumber = playerNumberMap.get("Player2");
      updateInventory(localNumber, oldGameState.getPlayerInventories().get("Player2"),
          newGameState.getPlayerInventories().get("Player2"));
    }
    if (numberOfPlayers >= 3) {
      if (!oldGameState.getPlayerInventories().get("Player3")
          .equals(newGameState.getPlayerInventories().get("Player3"))) {
        int localNumber = playerNumberMap.get("Player3");
        updateInventory(localNumber, oldGameState.getPlayerInventories().get("Player3"),
            newGameState.getPlayerInventories().get("Player3"));
      }
    }
    if (numberOfPlayers >= 4) {
      if (!oldGameState.getPlayerInventories().get("Player4")
          .equals(newGameState.getPlayerInventories().get("Player4"))) {
        int localNumber = playerNumberMap.get("Player4");
        updateInventory(localNumber, oldGameState.getPlayerInventories().get("Player4"),
            newGameState.getPlayerInventories().get("Player4"));
      }
    }

    //update points
    if (!oldGameState.getPlayerPoints().get("Player1")
        .equals(newGameState.getPlayerPoints().get("Player1"))) {
      int localNumber = playerNumberMap.get("Player1");
      updatePlayerPoints(localNumber, newGameState.getPlayerPoints().get("Player1"));
    }
    if (!oldGameState.getPlayerPoints().get("Player2")
        .equals(newGameState.getPlayerPoints().get("Player2"))) {
      int localNumber = playerNumberMap.get("Player2");
      updatePlayerPoints(localNumber, newGameState.getPlayerPoints().get("Player2"));
    }
    if (numberOfPlayers >= 3) {
      if (!oldGameState.getPlayerPoints().get("Player3")
          .equals(newGameState.getPlayerPoints().get("Player3"))) {
        int localNumber = playerNumberMap.get("Player3");
        updatePlayerPoints(localNumber, newGameState.getPlayerPoints().get("Player3"));
      }
    }
    if (numberOfPlayers >= 4) {
      if (!oldGameState.getPlayerPoints().get("Player4")
          .equals(newGameState.getPlayerPoints().get("Player4"))) {
        int localNumber = playerNumberMap.get("Player4");
        updatePlayerPoints(localNumber, newGameState.getPlayerPoints().get("Player4"));
      }
    }



    //update cards
    for (int i = 0; i < 12; i++) {
      if (!oldGameState.getCardsOnBoard().get(i).equals(newGameState.getCardsOnBoard().get(i))) {
        String path = "/Assets/emptyImage.png";
        if (newGameState.getCardsOnBoard().get(i).equals("Empty")) {
          Game.getInstance().removeCardFromSlot(i);
        } else {
          BaseCard newCard =
              new BaseCard(newGameState.getCardsOnBoard().get(i), deckColorFromInt(i));
          path = newCard.getPath();
          Game.getInstance().addCardToSlot(i, newCard);
        }

        // Related JavaFX objects : GameBoardController -> imageG1 , ... , imageB4 (12 total)
        setCardImage(i, path);

      }
    }
    for (int i = 12; i < 18; i++) {
      if (!oldGameState.getCardsOnBoard().get(i).equals(newGameState.getCardsOnBoard().get(i))) {
        String path = "/Assets/emptyImage.png";
        if (newGameState.getCardsOnBoard().get(i).equals("Empty")) {
          Game.getInstance().removeCardFromSlot(i);
        } else {
          OrientCard newCard =
              OrientCard.getOrientCard(newGameState.getCardsOnBoard().get(i), deckColorFromInt(i));
          path = newCard.getPath();
          Game.getInstance().addCardToSlot(i, newCard);
        }

        // Related JavaFX objects : GameBoardController -> imageG1 , ... , imageB4 (12 total)
        setCardImage(i, path);

      }
    }
    GameBoardController.getInstance().updateImageMap();

    //update nobles
    for (int i = 0; i < newGameState.getNoblesOnBoard().size(); i++) {
      if (!oldGameState.getNoblesOnBoard().get(i).equals(newGameState.getNoblesOnBoard().get(i))) {
        String path = "/Assets/emptyImage.png";
        if (newGameState.getNoblesOnBoard().get(i).equals("Empty")) {
          Game.getInstance().removeNobleFromSlot(i);
        } else {
          Noble newNoble = new Noble(newGameState.getNoblesOnBoard().get(i));
          path = newNoble.getPath();
          Game.getInstance().addNobleToSlot(i, newNoble);
        }

        // Related JavaFX objects : GameBoardController -> Noble1 , ... , Noble5 (5 total)
        setNobleImage(i, path);

      }
    }


    //update current player
    if (oldGameState.getCurrentPlayer() != newGameState.getCurrentPlayer()) {
      Game.getInstance().setCurrentPlayer(newGameState.getCurrentPlayer());
    }

    //update token selections
    if (oldGameState.getWhiteTokenSelected() != newGameState.getWhiteTokenSelected()) {
      // Related JavaFX objects : GameBoardController -> viewToken0
      // Related JavaFX methods : GameBoardController -> select(), deselect, setSelectedTokens()
      int number = newGameState.getWhiteTokenSelected();
      if (number == 0) {
        GameBoardController.getInstance().deselect("viewToken0");
      }
      else {
        GameBoardController.getInstance().select("viewToken0");
      }
      GameBoardController.getInstance().setSelectedTokens(0, number);
    }

    if (oldGameState.getBlueTokenSelected() != newGameState.getBlueTokenSelected()) {
      // Related JavaFX objects : GameBoardController -> viewToken1
      // Related JavaFX methods : GameBoardController -> select(), deselect, setSelectedTokens()
      int number = newGameState.getBlueTokenSelected();
      if (number == 0) {
        GameBoardController.getInstance().deselect("viewToken1");
      }
      else {
        GameBoardController.getInstance().select("viewToken1");
      }
      GameBoardController.getInstance().setSelectedTokens(1, number);
    }

    if (oldGameState.getGreenTokenSelected() != newGameState.getGreenTokenSelected()) {
      // Related JavaFX objects : GameBoardController -> viewToken2
      // Related JavaFX methods : GameBoardController -> select(), deselect, setSelectedTokens()
      int number = newGameState.getGreenTokenSelected();
      if (number == 0) {
        GameBoardController.getInstance().deselect("viewToken2");
      }
      else {
        GameBoardController.getInstance().select("viewToken2");
      }
      GameBoardController.getInstance().setSelectedTokens(2, number);
    }

    if (oldGameState.getRedTokenSelected() != newGameState.getRedTokenSelected()) {
      // Related JavaFX objects : GameBoardController -> viewToken3
      // Related JavaFX methods : GameBoardController -> select(), deselect, setSelectedTokens()
      int number = newGameState.getRedTokenSelected();
      if (number == 0) {
        GameBoardController.getInstance().deselect("viewToken3");
      }
      else {
        GameBoardController.getInstance().select("viewToken3");
      }
      GameBoardController.getInstance().setSelectedTokens(3, number);
    }

    if (oldGameState.getBlackTokenSelected() != newGameState.getBlackTokenSelected()) {
      // Related JavaFX objects : GameBoardController -> viewToken4
      // Related JavaFX methods : GameBoardController -> select(), deselect, setSelectedTokens()
      int number = newGameState.getBlackTokenSelected();
      if (number == 0) {
        GameBoardController.getInstance().deselect("viewToken4");
      }
      else {
        GameBoardController.getInstance().select("viewToken4");
      }
      GameBoardController.getInstance().setSelectedTokens(4, number);
    }

    if (oldGameState.getGoldTokenSelected() != newGameState.getGoldTokenSelected()) {
      // Related JavaFX objects : GameBoardController -> viewTokenGold
      // Related JavaFX methods : GameBoardController -> select(), deselect
      int number = newGameState.getGoldTokenSelected();
      if (number == 0) {
        GameBoardController.getInstance().deselect("viewTokenGold");
      }
      else {
        GameBoardController.getInstance().select("viewTokenGold");
      }
    }

    //update token amounts
    if (oldGameState.getWhiteTokenAmount() != newGameState.getWhiteTokenAmount()) {
      // Related JavaFX object : GameBoardController -> whiteTokenLabel
      GameBoardController.getInstance().setWhiteTokenLabel(
          String.valueOf(newGameState.getWhiteTokenAmount()));
      Game.getInstance().setColorTokenAmount(0, newGameState.getWhiteTokenAmount());
    }
    if (oldGameState.getBlueTokenAmount() != newGameState.getBlueTokenAmount()) {
      // Related JavaFX object : GameBoardController -> blueTokenLabel
      GameBoardController.getInstance().setBlueTokenLabel(
          String.valueOf(newGameState.getBlueTokenAmount()));
      Game.getInstance().setColorTokenAmount(1, newGameState.getBlueTokenAmount());
    }
    if (oldGameState.getGreenTokenAmount() != newGameState.getGreenTokenAmount()) {
      // Related JavaFX object : GameBoardController -> greenTokenLabel
      GameBoardController.getInstance().setGreenTokenLabel(
          String.valueOf(newGameState.getGreenTokenAmount()));
      Game.getInstance().setColorTokenAmount(2, newGameState.getGreenTokenAmount());
    }
    if (oldGameState.getRedTokenAmount() != newGameState.getRedTokenAmount()) {
      // Related JavaFX object : GameBoardController -> redTokenLabel
      GameBoardController.getInstance().setRedTokenLabel(
          String.valueOf(newGameState.getRedTokenAmount()));
      Game.getInstance().setColorTokenAmount(3, newGameState.getRedTokenAmount());
    }
    if (oldGameState.getBlackTokenAmount() != newGameState.getBlackTokenAmount()) {
      // Related JavaFX object : GameBoardController -> blackTokenLabel
      GameBoardController.getInstance().setBlackTokenLabel(
          String.valueOf(newGameState.getBlackTokenAmount()));
      Game.getInstance().setColorTokenAmount(4, newGameState.getBlackTokenAmount());
    }
    if (oldGameState.getGoldTokenAmount() != newGameState.getGoldTokenAmount()) {
      // Related JavaFX object : GameBoardController -> goldTokenLabel
      GameBoardController.getInstance().setGoldTokenLabel(
          String.valueOf(newGameState.getGoldTokenAmount()));
      Game.getInstance().setGoldTokenAmount(newGameState.getGoldTokenAmount());
    }

    String currentPlayerNumberString = "Player".concat(String.valueOf(newGameState.getCurrentPlayer()));
    int localNumber = playerNumberMap.get(currentPlayerNumberString);
    highlightCurrentPlayer(localNumber);

    GameInterfaceHints.changeTurnStatus(TurnStatus.NORMAL);
    if (newGameState.getStatus().equals("CHOOSE_NOBLE")) {
      GameInterfaceHints.changeTurnStatus(TurnStatus.NOBLE);
      for (int i = 0; i < 5; i++) {
        NobleSlot slot = Game.getInstance().nobleSlots.get(i);
        if (!slot.hasNoble()) {
          continue;
        }
        Noble noble = slot.nobleInSlot();
        if (canTakeNoble(Game.getInstance().player1, noble)) {
          String viewName = "viewNoble".concat(String.valueOf(i + 1));
          GameBoardController.getInstance().select(viewName);
        }
      }
    }



    oldGameState = newGameState;


  }

  private static void updatePlayerPoints(int localNumber, int points) {
    switch (localNumber) {
      case 1 -> PlayerSelfController.getInstance().setPoints(points);
      case 2 -> PlayerOthersController.getInstance().setPoints(2, points);
      case 3 -> PlayerOthersController.getInstance().setPoints(3, points);
      case 4 -> PlayerOthersController.getInstance().setPoints(4, points);
      default -> throw new IllegalStateException();
    }
  }

  private static void updatePlayerColor(int localNumber, String color) {
    String colorStr = "#".concat(color);
    switch (localNumber) {
      case 1 -> PlayerSelfController.getInstance().setColor(colorStr);
      case 2 -> PlayerOthersController.getInstance().setColor(2, colorStr);
      case 3 -> PlayerOthersController.getInstance().setColor(3, colorStr);
      case 4 -> PlayerOthersController.getInstance().setColor(4, colorStr);
      default -> throw new IllegalStateException();
    }
  }

  private static void updatePlayerName(int localNumber, String name) {
    System.out.println("Updating Name for player (local number): " + localNumber
        + " with name: " + name);
    switch (localNumber) {
      case 1 -> PlayerSelfController.getInstance().setName(name);
      case 2 -> PlayerOthersController.getInstance().setName(2, name);
      case 3 -> PlayerOthersController.getInstance().setName(3, name);
      case 4 -> PlayerOthersController.getInstance().setName(4, name);
      default -> throw new IllegalStateException();
    }
  }


  /**
   * Helper method to update player inventories.
   *
   * @param player the player whose inventory we want to update
   * @param oldInventory the old inventory
   * @param newInventory the new inventory
   */
  private static void updateInventory(int player,
                                     InventoryView oldInventory, InventoryView newInventory) {

    if (player == 1) {
      if (!oldInventory.getAllCards().equals(newInventory.getAllCards())) {
        Inventory currentInventory = playerMap.get(player).getInventory();
        currentInventory.emptyCards();
        for (String cardView : newInventory.getAllCards()) {

          int index = newInventory.getAllCards().indexOf(cardView);
          String deckColorString = newInventory.cardDeckColors.get(index);
          DeckColor deckColor = DeckColor.valueOf(deckColorString);

          BaseCard newCard;

          if (deckColor == DeckColor.GREEN
              || deckColor == DeckColor.YELLOW
              || deckColor == DeckColor.BLUE) {

            newCard = new BaseCard(cardView, deckColor);

          } else {

            newCard = OrientCard.getOrientCard(cardView, deckColor);

          }

          //BaseCard newCard = new BaseCard(cardView.cardData, cardView.getDeckColor());
          //TODO : Get actual deck color, right now it assumes that its green which is wrong but
          //TODO : doesn't break the code since color is not important in inventory
          playerMap.get(player).getInventory().addCard(newCard);
        }
        currentInventory.updateInventoryCards();
      }
      if (!oldInventory.getReservedCards().equals(newInventory.getReservedCards())) {
        Inventory currentInventory = playerMap.get(player).getInventory();
        currentInventory.emptyReserve();
        for (String cardView : newInventory.getReservedCards()) {
          System.out.println("Reserved: " + cardView);

          int index = newInventory.getReservedCards().indexOf(cardView);
          String deckColorString = newInventory.reservedDeckColors.get(index);
          DeckColor deckColor = DeckColor.valueOf(deckColorString);

          BaseCard newCard;

          if (deckColor == DeckColor.GREEN
              || deckColor == DeckColor.YELLOW
              || deckColor == DeckColor.BLUE) {

            newCard = new BaseCard(cardView, deckColor);

          } else {

            newCard = OrientCard.getOrientCard(cardView, deckColor);

          }

          //BaseCard newCard = new BaseCard(cardView.cardData, cardView.getDeckColor());
          //TODO : Get actual deck color, right now it assumes that its green which is wrong but
          //TODO : doesn't break the code since color is not important in inventory
          playerMap.get(player).getInventory().reserveCard(newCard);
        }
        currentInventory.updateReserveCards();
      }
      playerMap.get(player).getInventory().setColorTokens(0, newInventory.getWhiteTokens());
      playerMap.get(player).getInventory().setColorTokens(1, newInventory.getBlueTokens());
      playerMap.get(player).getInventory().setColorTokens(2, newInventory.getGreenTokens());
      playerMap.get(player).getInventory().setColorTokens(3, newInventory.getRedTokens());
      playerMap.get(player).getInventory().setColorTokens(4, newInventory.getBlackTokens());
      playerMap.get(player).getInventory().setGoldTokens(newInventory.getGoldTokens());
      InventorySelfController.getInstance().setPlayer1WhiteTokenAmount(
          String.valueOf(newInventory.getWhiteTokens()));
      InventorySelfController.getInstance().setPlayer1BlueTokenAmount(
          String.valueOf(newInventory.getBlueTokens()));
      InventorySelfController.getInstance().setPlayer1GreenTokenAmount(
          String.valueOf(newInventory.getGreenTokens()));
      InventorySelfController.getInstance().setPlayer1RedTokenAmount(
          String.valueOf(newInventory.getRedTokens()));
      InventorySelfController.getInstance().setPlayer1BlackTokenAmount(
          String.valueOf(newInventory.getBlackTokens()));
      InventorySelfController.getInstance().setPlayer1GoldTokenAmount(
          String.valueOf(newInventory.getGoldTokens()));


      // update owned nobles
      if (!oldInventory.getOwnedNobles().equals(newInventory.getOwnedNobles())) {
        Inventory currentInventory = playerMap.get(player).getInventory();
        currentInventory.emptyNobles();

        for (String nobleString : newInventory.getOwnedNobles()) {

          int index = newInventory.getOwnedNobles().indexOf(nobleString);

          Noble noble = new Noble(nobleString);

          currentInventory.addNoble(noble);
        }

        currentInventory.updateNobles();

      }

      // update reserved nobles
      if (!oldInventory.getReservedNobles().equals(newInventory.getReservedNobles())) {
        Inventory currentInventory = playerMap.get(player).getInventory();
        currentInventory.emptyReservedNobles();

        for (String nobleString : newInventory.getReservedNobles()) {

          int index = newInventory.getReservedNobles().indexOf(nobleString);

          Noble noble = new Noble(nobleString);

          currentInventory.reserveNoble(noble);
        }

        currentInventory.updateReservedNobles();

      }

    }

    else {
      if (!oldInventory.getAllCards().equals(newInventory.getAllCards())) {
        //update cards
        Inventory currentInventory = playerMap.get(player).getInventory();
        currentInventory.emptyCards();
        for (String cardView : newInventory.getAllCards()) {

          int index = newInventory.getAllCards().indexOf(cardView);
          String deckColorString = newInventory.cardDeckColors.get(index);
          DeckColor deckColor = DeckColor.valueOf(deckColorString);

          BaseCard newCard;

          if (deckColor == DeckColor.GREEN
              || deckColor == DeckColor.YELLOW
              || deckColor == DeckColor.BLUE) {

            newCard = new BaseCard(cardView, deckColor);

          } else {

            newCard = OrientCard.getOrientCard(cardView, deckColor);

          }

          //BaseCard newCard = new BaseCard(cardView.cardData, cardView.getDeckColor());
          //TODO : Get actual deck color, right now it assumes that its green which is wrong but
          //TODO : doesn't break the code since color is not important in inventory
          playerMap.get(player).getInventory().addCard(newCard);
        }
        //update bonuses
        PlayerOthersController.getInstance().setBonus(0, player, newInventory.getWhiteBonus());
        PlayerOthersController.getInstance().setBonus(1, player, newInventory.getBlueBonus());
        PlayerOthersController.getInstance().setBonus(2, player, newInventory.getGreenBonus());
        PlayerOthersController.getInstance().setBonus(3, player, newInventory.getRedBonus());
        PlayerOthersController.getInstance().setBonus(4, player, newInventory.getBlackBonus());
      }
      if (!oldInventory.getReservedCards().equals(newInventory.getReservedCards())) {
        //update reserved cards
        Inventory currentInventory = playerMap.get(player).getInventory();
        currentInventory.emptyReserve();
        for (String cardView : newInventory.getReservedCards()) {

          int index = newInventory.getReservedCards().indexOf(cardView);
          String deckColorString = newInventory.reservedDeckColors.get(index);
          DeckColor deckColor = DeckColor.valueOf(deckColorString);

          BaseCard newCard;

          if (deckColor == DeckColor.GREEN
              || deckColor == DeckColor.YELLOW
              || deckColor == DeckColor.BLUE) {

            newCard = new BaseCard(cardView, deckColor);

          } else {

            newCard = OrientCard.getOrientCard(cardView, deckColor);

          }

          //BaseCard newCard = new BaseCard(cardView.cardData, cardView.getDeckColor());
          //TODO : Get actual deck color, right now it assumes that its green which is wrong but
          //TODO : doesn't break the code since color is not important in inventory
          playerMap.get(player).getInventory().reserveCard(newCard);
        }
        currentInventory.updateReserveCardsOther(player);
      }
      //update tokens
      playerMap.get(player).getInventory().setColorTokens(0, newInventory.getWhiteTokens());
      playerMap.get(player).getInventory().setColorTokens(1, newInventory.getBlueTokens());
      playerMap.get(player).getInventory().setColorTokens(2, newInventory.getGreenTokens());
      playerMap.get(player).getInventory().setColorTokens(3, newInventory.getRedTokens());
      playerMap.get(player).getInventory().setColorTokens(4, newInventory.getBlackTokens());
      playerMap.get(player).getInventory().setGoldTokens(newInventory.getGoldTokens());
      PlayerOthersController.getInstance().setTokens(0, player, newInventory.getWhiteTokens());
      PlayerOthersController.getInstance().setTokens(1, player, newInventory.getBlueTokens());
      PlayerOthersController.getInstance().setTokens(2, player, newInventory.getGreenTokens());
      PlayerOthersController.getInstance().setTokens(3, player, newInventory.getRedTokens());
      PlayerOthersController.getInstance().setTokens(4, player, newInventory.getBlackTokens());
      PlayerOthersController.getInstance().setTokens(5, player, newInventory.getGoldTokens());
    }

  }

  private static void initializeState(GameState state) {


    playerMap = new HashMap<>();
    playerNumberMap = new HashMap<>();

    int myPlayerNumber = 0;

    numberOfPlayers = state.getPlayerNames().size();
    int nextPlayer = 2;
    System.out.println(state.getPlayerNames());
    for (int i = 0; i < numberOfPlayers; i++) {
      String playerNumberString = "Player" + String.valueOf(i + 1);
      if (state.getPlayerNames().get(playerNumberString).equals(GlobalUserInfo.getUsername())) {
        myPlayerNumber = i + 1;
        ScreenUpdater.playerMap.put(1,
            new Player(state.getPlayerNames().get(playerNumberString),
                state.getPlayerColors().get(playerNumberString),  i + 1)
        );
        ScreenUpdater.playerNumberMap.put(playerNumberString, 1);
      }

      else {
        ScreenUpdater.playerMap.put(nextPlayer,
            new Player(state.getPlayerNames().get(playerNumberString),
                state.getPlayerColors().get(playerNumberString), i + 1)
        );
        ScreenUpdater.playerNumberMap.put(playerNumberString, nextPlayer);
        nextPlayer += 1;
      }
    }
    //Update trade posts if already exists from saved game
    for(String playerStr : state.getPlayerInventories().keySet()) {
      for (String postStr : state.getPlayerInventories().get(playerStr).getOwnedTradePosts()) {
        TradingPostType post = TradingPostType.valueOf(postStr);
        Player player = playerMap.get(playerNumberMap.get(playerStr));
        //System.out.println("Player Number :" + playerNumberMap.get(player) +"\n");
        //System.out.println("Post Number :" + postNum +"\n");
        //addPosts(playerNumberMap.get(player),postNum);

        player.addTradingPost(post);

      }
    }

    //deck labels
    // Related JavaFX object : GameBoardController -> greenDeckLabel
    GameBoardController.getInstance().setGreenDeckLabel(
        String.valueOf(state.getGreenDeck()));
    // Related JavaFX object : GameBoardController -> orangeDeckLabel
    GameBoardController.getInstance().setOrangeDeckLabel(
        String.valueOf(state.getYellowDeck()));
    // Related JavaFX object : GameBoardController -> blueDeckLabel
    GameBoardController.getInstance().setBlueDeckLabel(
        String.valueOf(state.getBlueDeck()));

    // Related JavaFX object : GameBoardController -> redL1DeckLabel
    GameBoardController.getInstance().setRedL1DeckLabel(
        String.valueOf(state.getRedL1Deck()));
    // Related JavaFX object : GameBoardController -> redL2DeckLabel
    GameBoardController.getInstance().setRedL2DeckLabel(
        String.valueOf(state.getRedL2Deck()));
    // Related JavaFX object : GameBoardController -> redL3DeckLabel
    GameBoardController.getInstance().setRedL3DeckLabel(
        String.valueOf(state.getRedL3Deck()));

    GameBoardController.getInstance().deselectAll();


    //initialize cards
    for (int i = 0; i < 12; i++) {



      String path = "/Assets/emptyImage.png";
      if (!state.getCardsOnBoard().get(i).equals("Empty")) {
        BaseCard newCard =
            new BaseCard(state.getCardsOnBoard().get(i), deckColorFromInt(i));
        path = newCard.getPath();
      }

      // Related JavaFX objects : GameBoardController -> imageG1 , ... , imageB4 (12 total)
      System.out.println(path);
      setCardImage(i, path);

    }

    for (int i = 12; i < 18; i++) {



      String path = "/Assets/emptyImage.png";
      if (!state.getCardsOnBoard().get(i).equals("Empty")) {
        OrientCard newCard =
            OrientCard.getOrientCard(state.getCardsOnBoard().get(i), deckColorFromInt(i));
        path = newCard.getPath();
      }

      // Related JavaFX objects : GameBoardController -> imageG1 , ... , imageB4 (12 total)
      System.out.println(path);
      setCardImage(i, path);

    }


    //initialize nobles
    for (int i = 0; i < state.getNoblesOnBoard().size(); i++) {

      String path = "/Assets/emptyImage.png";

      if (!state.getNoblesOnBoard().get(i).equals("Empty")) {
        Noble newNoble =
            new Noble(state.getNoblesOnBoard().get(i));
        path = newNoble.getPath();
      }

      // Related JavaFX objects : GameBoardController -> Noble1 , ... , Noble5 (5 total)
      setNobleImage(i, path);


    }

    //initialize cities
    if (GlobalUserInfo.getGameService().equals("splendor_cities")) {
      String path = "/Assets/CityCards/city" + state.getCitySlots().get(0) + ".png";
      String path1 = "/Assets/CityCards/city" + state.getCitySlots().get(1) + ".png";
      String path2 = "/Assets/CityCards/city" + state.getCitySlots().get(2) + ".png";

      System.out.println(path);
      System.out.println(path1);
      System.out.println(path2);

      GameBoardController.getInstance().setCity1(path);
      GameBoardController.getInstance().setCity2(path1);
      GameBoardController.getInstance().setCity3(path2);
    }


    //initialize token amounts
    // Related JavaFX object : GameBoardController -> whiteTokenLabel
    GameBoardController.getInstance().setWhiteTokenLabel(
        String.valueOf(state.getWhiteTokenAmount()));
    // Related JavaFX object : GameBoardController -> blueTokenLabel
    GameBoardController.getInstance().setBlueTokenLabel(
        String.valueOf(state.getBlueTokenAmount()));
    // Related JavaFX object : GameBoardController -> greenTokenLabel
    GameBoardController.getInstance().setGreenTokenLabel(
        String.valueOf(state.getGreenTokenAmount()));
    // Related JavaFX object : GameBoardController -> redTokenLabel
    GameBoardController.getInstance().setRedTokenLabel(
        String.valueOf(state.getRedTokenAmount()));
    // Related JavaFX object : GameBoardController -> blackTokenLabel
    GameBoardController.getInstance().setBlackTokenLabel(
        String.valueOf(state.getBlackTokenAmount()));
    // Related JavaFX object : GameBoardController -> goldTokenLabel
    GameBoardController.getInstance().setGoldTokenLabel(
        String.valueOf(state.getGoldTokenAmount()));


    //initialize player 1
    int localNumber = playerNumberMap.get("Player1");
    updatePlayerPoints(localNumber, state.getPlayerPoints().get("Player1"));
    updatePlayerName(localNumber, state.getPlayerNames().get("Player1"));
    updatePlayerColor(localNumber, state.getPlayerColors().get("Player1"));

    //initialize player 2
    localNumber = playerNumberMap.get("Player2");
    updatePlayerPoints(localNumber, state.getPlayerPoints().get("Player2"));
    updatePlayerName(localNumber, state.getPlayerNames().get("Player2"));
    updatePlayerColor(localNumber, state.getPlayerColors().get("Player2"));

    if (numberOfPlayers >= 3) {
      //initialize player 3
      localNumber = playerNumberMap.get("Player3");
      updatePlayerPoints(localNumber, state.getPlayerPoints().get("Player3"));
      updatePlayerName(localNumber, state.getPlayerNames().get("Player3"));
      updatePlayerColor(localNumber, state.getPlayerColors().get("Player3"));
    }

    if (numberOfPlayers >= 4) {
      //initialize player 4
      localNumber = playerNumberMap.get("Player4");
      updatePlayerPoints(localNumber, state.getPlayerPoints().get("Player4"));
      updatePlayerName(localNumber, state.getPlayerNames().get("Player4"));
      updatePlayerColor(localNumber, state.getPlayerColors().get("Player4"));
    }

    List<Player> players = new ArrayList<>();
    players.add(playerMap.get(1));
    players.add(playerMap.get(2));
    if (numberOfPlayers >= 3) {
      players.add(playerMap.get(3));
    }
    if (numberOfPlayers >= 4) {
      players.add(playerMap.get(4));
    }

    Game newGame = new Game(state, players);
    assert myPlayerNumber != 0;
    newGame.myPlayerNumber = myPlayerNumber;
    SplendorUiApplication.aGame = newGame;

    InventoryView emptyView = new InventoryView(
        new Inventory(
            new Player("", "", 1)
        )
    );

    localNumber = playerNumberMap.get("Player1");
    updateInventory(localNumber, emptyView, state.getPlayerInventories().get("Player1"));
    localNumber = playerNumberMap.get("Player2");
    updateInventory(localNumber, emptyView, state.getPlayerInventories().get("Player2"));
    if (playerNumberMap.containsKey("Player3")) {
      localNumber = playerNumberMap.get("Player3");
      updateInventory(localNumber, emptyView, state.getPlayerInventories().get("Player3"));
    }
    if (playerNumberMap.containsKey("Player4")) {
      localNumber = playerNumberMap.get("Player4");
      updateInventory(localNumber, emptyView, state.getPlayerInventories().get("Player4"));
    }




    //update cards
    for (int i = 0; i < 12; i++) {
      if (state.getCardsOnBoard().get(i).equals("Empty")) {
        continue;
      }
      BaseCard newCard = new BaseCard(state.getCardsOnBoard().get(i), deckColorFromInt(i));
      Game.getInstance().addCardToSlot(i, newCard);
    }

    //update cards
    for (int i = 12; i < 18; i++) {
      if (state.getCardsOnBoard().get(i).equals("Empty")) {
        continue;
      }
      OrientCard newCard = OrientCard.getOrientCard(state.getCardsOnBoard().get(i),
          deckColorFromInt(i));
      Game.getInstance().addCardToSlot(i, newCard);
    }

    //update nobles
    for (int i = 0; i < 5; i++) {
      if (state.getNoblesOnBoard().get(i).equals("Empty")) {
        continue;
      }
      Noble noble = new Noble(state.getNoblesOnBoard().get(i));
      Game.getInstance().addNobleToSlot(i, noble);
    }

    tradingPostsBox.clearTradingPosts();

    for (Player player : Game.getInstance().players) {
      for (TradingPostType tp : TradingPostType.values()) {
        if (player.hasTradingPost(tp)) {
          tradingPostsBox.addTradingPost(tp, "#".concat(player.getColor()));
        }
      }
    }

    //update tokens
    Game.getInstance().setColorTokenAmount(0, state.getWhiteTokenAmount());
    Game.getInstance().setColorTokenAmount(1, state.getBlueTokenAmount());
    Game.getInstance().setColorTokenAmount(2, state.getGreenTokenAmount());
    Game.getInstance().setColorTokenAmount(3, state.getRedTokenAmount());
    Game.getInstance().setColorTokenAmount(4, state.getBlackTokenAmount());
    Game.getInstance().setGoldTokenAmount(state.getGoldTokenAmount());

    Game.getInstance().setCurrentPlayer(state.getCurrentPlayer());

    Game.getInstance().setDeckSize(DeckColor.BLUE, state.getBlueDeck());
    Game.getInstance().setDeckSize(DeckColor.GREEN, state.getGreenDeck());
    Game.getInstance().setDeckSize(DeckColor.REDL3, state.getRedL3Deck());
    Game.getInstance().setDeckSize(DeckColor.REDL1, state.getRedL1Deck());
    Game.getInstance().setDeckSize(DeckColor.REDL2, state.getRedL2Deck());
    Game.getInstance().setDeckSize(DeckColor.YELLOW, state.getYellowDeck());

    System.out.format("Current player : %d \n", state.getCurrentPlayer());

    String currentPlayerNumberString = "Player".concat(String.valueOf(state.getCurrentPlayer()));
    localNumber = playerNumberMap.get(currentPlayerNumberString);
    highlightCurrentPlayer(localNumber);

    GameBoardController.getInstance().updateImageMap();

    // TODO : update player inventory, for now assume empty at the start
  }

  public static void highlightCurrentPlayer(int localPlayerNumber) {
    PlayerOthersController.getInstance().highlight(localPlayerNumber);
    if (localPlayerNumber == 1) {
      PlayerSelfController.getInstance().highlightPlayer1();
    }
    else {
      PlayerSelfController.getInstance().removeHighlightPlayer1();
    }
  }

  private static void setCardImage(int i, String path) {
    switch (i+1) {
      case 1 -> GameBoardController.getInstance().setImageG1(path);
      case 2 -> GameBoardController.getInstance().setImageG2(path);
      case 3 -> GameBoardController.getInstance().setImageG3(path);
      case 4 -> GameBoardController.getInstance().setImageG4(path);
      case 5 -> GameBoardController.getInstance().setImageO1(path);
      case 6 -> GameBoardController.getInstance().setImageO2(path);
      case 7 -> GameBoardController.getInstance().setImageO3(path);
      case 8 -> GameBoardController.getInstance().setImageO4(path);
      case 9 -> GameBoardController.getInstance().setImageB1(path);
      case 10 -> GameBoardController.getInstance().setImageB2(path);
      case 11 -> GameBoardController.getInstance().setImageB3(path);
      case 12 -> GameBoardController.getInstance().setImageB4(path);

      case 13 -> GameBoardController.getInstance().setImageR1(path);
      case 14 -> GameBoardController.getInstance().setImageR2(path);
      case 15 -> GameBoardController.getInstance().setImageR3(path);
      case 16 -> GameBoardController.getInstance().setImageR4(path);
      case 17 -> GameBoardController.getInstance().setImageR5(path);
      case 18 -> GameBoardController.getInstance().setImageR6(path);

      default -> throw new IllegalStateException();
    }
  }

  private static void setNobleImage(int i, String path) {
    switch (i+1) {
      case 1 -> GameBoardController.getInstance().setImageNoble1(path);
      case 2 -> GameBoardController.getInstance().setImageNoble2(path);
      case 3 -> GameBoardController.getInstance().setImageNoble3(path);
      case 4 -> GameBoardController.getInstance().setImageNoble4(path);
      case 5 -> GameBoardController.getInstance().setImageNoble5(path);
      default -> throw new IllegalStateException();
    }
  }



  /**
   * Use if client game data is wrong (shouldn't happen).
   */
  public static void updateGame() {
    //TODO implement later, for now assume the client is correct
  }

  private static DeckColor deckColorFromInt(int i) {
    if (i < 4) {
      return DeckColor.GREEN;
    }
    else if (i < 8) {
      return DeckColor.YELLOW;
    }
    else if (i < 12){
      return DeckColor.BLUE;
    }
    else if (i < 14) {
      return DeckColor.REDL1;
    }
    else if (i < 16) {
      return DeckColor.REDL2;
    }
    else {
      return DeckColor.REDL3;
    }

  }

  private static boolean canTakeNoble(Player player, Noble noble) {
    return player.getInventory().getRedBonus() >= noble.getRedCost()
        && player.getInventory().getBlackBonus() >= noble.getBlackCost()
        && player.getInventory().getBlueBonus() >= noble.getBlueCost()
        && player.getInventory().getWhiteBonus() >= noble.getWhiteCost()
        && player.getInventory().getGreenBonus() >= noble.getGreenCost();
  }

//  private static void removePosts(int playerNum, int postNum) {
//    if (playerNum == 1) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().removeTp1Blue();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().removeTp2Blue();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().removeTp3Blue();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().removeTp4Blue();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().removeTp5Blue();
//      }
//      return;
//    }
//    if (playerNum == 2) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().removeTp1Yellow();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().removeTp2Yellow();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().removeTp3Yellow();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().removeTp4Yellow();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().removeTp5Yellow();
//      }
//      return;
//    }
//    if (playerNum == 3) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().removeTp1Black();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().removeTp2Black();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().removeTp3Black();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().removeTp4Black();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().removeTp5Black();
//      }
//      return;
//    }
//    if (playerNum == 4) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().removeTp1Red();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().removeTp2Red();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().removeTp3Red();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().removeTp4Red();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().removeTp5Red();
//      }
//    }
//  }
//
//  private static void addPosts(int playerNum, int postNum){
//    if (playerNum == 1) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().setTp1Blue();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().setTp2Blue();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().setTp3Blue();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().setTp4Blue();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().setTp5Blue();
//      }
//      return;
//    }
//    if (playerNum == 2) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().setTp1Yellow();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().setTp2Yellow();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().setTp3Yellow();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().setTp4Yellow();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().setTp5Yellow();
//      }
//      return;
//    }
//    if (playerNum == 3) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().setTp1Black();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().setTp2Black();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().setTp3Black();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().setTp4Black();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().setTp5Black();
//      }
//      return;
//    }
//    if (playerNum == 4) {
//      if (postNum == 1) {
//        GameBoardController.getInstance().setTp1Red();
//      }
//      else if (postNum == 2){
//        GameBoardController.getInstance().setTp2Red();
//      }
//      else if (postNum == 3) {
//        GameBoardController.getInstance().setTp3Red();
//      }
//      else if (postNum == 4) {
//        GameBoardController.getInstance().setTp4Red();
//      }
//      else if (postNum == 5) {
//        GameBoardController.getInstance().setTp5Red();
//      }
//    }
//
//  }


}




