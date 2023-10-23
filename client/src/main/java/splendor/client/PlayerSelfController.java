package splendor.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import splendor.gameelements.BaseCard;

/**
 * JavaFX controller for the segment of the game screen that shows the player's own information.
 */
public class PlayerSelfController implements Initializable {

  private static PlayerSelfController instance;
  public List<Integer> usedTokens = new ArrayList<>();
  @FXML
  private Label player1NameLabel;
  @FXML
  private Label player1PointsLabel;
  @FXML
  private Button confirmActionButton;
  @FXML
  private Pane player1NameBox;
  private boolean confirmActionActive = false;

  private Map<String, Integer> tokenNameToIntMap = new HashMap<>();
  private Map<String, Integer> cardNameToIntMap = new HashMap<>();
  private Map<String, Integer> DeckToIntMap = new HashMap<>();

  private int extra1 = 0;
  private int extra2 = 0;
  private int extra3 = 0;
  private int extra4 = 0;
  private int doubleGold = 0;
  private int freeToken = 0;

  /**
   * Sets the value of the first extra variable.
   *
   * @param value the value to set to (0-99)
   */
  public void setExtra1(int value) {
    extra1 = value;
  }

  /**
   * Sets the value of the second extra variable.
   *
   * @param value the value to set to (0-99)
   */
  public void setExtra2(int value) {
    extra2 = value;
  }

  /**
   * Sets the value of the third extra variable.
   *
   * @param value the value to set to (0-99)
   */
  public void setExtra3(int value) {
    extra3 = value;
  }

  /**
   * Sets the value of the fouth extra variable.
   *
   * @param value the value to set to (0-99)
   */
  public void setExtra4(int value) {
    extra4 = value;
  }

  /**
   * Sets the value of the 5th extra variable (double gold cards used).
   *
   * @param value the value to set to (0-5)
   */
  public void setDoubleGold(int value) {
    doubleGold = value;
  }

  /**
   * Sets the value of the 6th extra variable (free token number).
   *
   * @param value the value to set to (0-4)
   */
  public void setFreeToken(int value) {
    freeToken = value;
  }


  public static PlayerSelfController getInstance() {
    return PlayerSelfController.instance;
  }

  public void highlightPlayer1() {
    NameHighlighter.addHighlight(player1NameBox);
  }

  public void removeHighlightPlayer1() {
    NameHighlighter.removeHighlight(player1NameBox);
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    tokenNameToIntMap.put("viewToken0", 0);
    tokenNameToIntMap.put("viewToken1", 1);
    tokenNameToIntMap.put("viewToken2", 2);
    tokenNameToIntMap.put("viewToken3", 3);
    tokenNameToIntMap.put("viewToken4", 4);

    cardNameToIntMap.put("viewCardG1", 0);
    cardNameToIntMap.put("viewCardG2", 1);
    cardNameToIntMap.put("viewCardG3", 2);
    cardNameToIntMap.put("viewCardG4", 3);
    cardNameToIntMap.put("viewCardO1", 4);
    cardNameToIntMap.put("viewCardO2", 5);
    cardNameToIntMap.put("viewCardO3", 6);
    cardNameToIntMap.put("viewCardO4", 7);
    cardNameToIntMap.put("viewCardB1", 8);
    cardNameToIntMap.put("viewCardB2", 9);
    cardNameToIntMap.put("viewCardB3", 10);
    cardNameToIntMap.put("viewCardB4", 11);

    cardNameToIntMap.put("viewCardR1", 12);
    cardNameToIntMap.put("viewCardR2", 13);
    cardNameToIntMap.put("viewCardR3", 14);
    cardNameToIntMap.put("viewCardR4", 15);
    cardNameToIntMap.put("viewCardR5", 16);
    cardNameToIntMap.put("viewCardR6", 17);

    cardNameToIntMap.put("ownedCard1", 0);
    cardNameToIntMap.put("ownedCard2", 1);
    cardNameToIntMap.put("ownedCard3", 2);
    cardNameToIntMap.put("ownedCard4", 3);
    cardNameToIntMap.put("ownedCard5", 4);
    cardNameToIntMap.put("ownedCard6", 5);
    cardNameToIntMap.put("ownedCard7", 6);
    cardNameToIntMap.put("ownedCard8", 7);
    cardNameToIntMap.put("ownedCard9", 8);
    cardNameToIntMap.put("ownedCard10", 9);
    cardNameToIntMap.put("ownedCard11", 10);
    cardNameToIntMap.put("ownedCard12", 11);
    cardNameToIntMap.put("ownedCard13", 12);
    cardNameToIntMap.put("ownedCard14", 13);
    cardNameToIntMap.put("ownedCard15", 14);
    cardNameToIntMap.put("ownedCard16", 15);
    cardNameToIntMap.put("ownedCard17", 16);
    cardNameToIntMap.put("ownedCard18", 17);
    cardNameToIntMap.put("ownedCard19", 18);
    cardNameToIntMap.put("ownedCard20", 19);

    cardNameToIntMap.put("reservedCard1", 0);
    cardNameToIntMap.put("reservedCard2", 1);
    cardNameToIntMap.put("reservedCard3", 2);

    cardNameToIntMap.put("viewNoble1", 0);
    cardNameToIntMap.put("viewNoble2", 1);
    cardNameToIntMap.put("viewNoble3", 2);
    cardNameToIntMap.put("viewNoble4", 3);
    cardNameToIntMap.put("viewNoble5", 4);

    DeckToIntMap.put("DeckL1",1);
    DeckToIntMap.put("DeckL2",2);
    DeckToIntMap.put("DeckL3",3);
    DeckToIntMap.put("DeckRL1",4);
    DeckToIntMap.put("DeckRL2",5);
    DeckToIntMap.put("DeckRL3",6);

    PlayerSelfController.instance = this;
  }

  public int getNumberFromImageId(String imageId) {
    return cardNameToIntMap.get(imageId);
  }

  public void setPoints(int points) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1PointsLabel.setText(String.valueOf(points));

      }
    });

  }

  public void setName(String name) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1NameLabel.setText(name);

      }
    });

  }

  public void setColor(String color) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1NameLabel.setTextFill(Color.web(color));
      }
    });

  }


  /**
   * Enables the confirm action button.
   */
  public void confirmActionSetActive() {
    confirmActionButton.setStyle("-fx-background-color: lightgreen");
    confirmActionButton.setMouseTransparent(false);
    confirmActionActive = true;
  }

  /**
   * Disables the confirm action button.
   */
  public void confirmActionSetNotActive() {
    confirmActionButton.setStyle("-fx-background-color: lightgray");
    confirmActionButton.setMouseTransparent(true);
    confirmActionActive = false;
  }

  public void onClickConfirmAction() throws UnirestException {

    ActionType actionType = GameBoardController.getInstance().getSelectedAction();

    // Take 3 Tokens

    if (actionType == ActionType.TAKE_THREE_TOKENS) {

      String colorToken1Name = GameBoardController.getInstance().getSelectedItem(0);
      int colorToken1 = tokenNameToIntMap.get(colorToken1Name);
      String colorToken2Name = GameBoardController.getInstance().getSelectedItem(1);
      int colorToken2 = tokenNameToIntMap.get(colorToken2Name);
      String colorToken3Name = GameBoardController.getInstance().getSelectedItem(2);
      int colorToken3 = tokenNameToIntMap.get(colorToken3Name);

      List<Integer> orderTokens = Arrays.asList(colorToken1, colorToken2, colorToken3);
      Collections.sort(orderTokens);

      long actionId = 200000000_00_00_00_00L
          + orderTokens.get(0) * 10000000_00_00_00_00L
          + orderTokens.get(1) * 1000000_00_00_00_00L
          + orderTokens.get(2) * 100000_00_00_00_00L;

      System.out.format("Sending action (take 3 tokens) : %d \n", actionId);

      ClientRequest.makeActionRequest(actionId);

    }

    // Take 2 Tokens

    else if (actionType == ActionType.TAKE_TWO_TOKENS) {

      String tokenName = GameBoardController.getInstance().getSelectedItem(0);
      int tokenNumber = tokenNameToIntMap.get(tokenName);

      long actionId = 100000000_00_00_00_00L
          + tokenNumber * 10000000_00_00_00_00L;

      System.out.format("Sending action (take 2 tokens) : %d \n", actionId);

      ClientRequest.makeActionRequest(actionId);

    }

    // Buy reserved card
    else if (actionType == ActionType.BUY_RESERVED_CARD){
      String id = GameBoardController.getInstance().getSelectedItem(0);
      String sub = id.substring(0,12);
      // Buy Card
      if (sub.equals("reservedCard")) {
        int cardId = cardNameToIntMap.get(id);

        int whiteTokenAmount = 0;
        int blueTokenAmount = 0;
        int greenTokenAmount = 0;
        int redTokenAmount = 0;
        int blackTokenAmount = 0;
        int goldTokenAmount = 0;

        if (usedTokens.size() > 0) {
          whiteTokenAmount = usedTokens.get(0);
          blueTokenAmount = usedTokens.get(1);
          greenTokenAmount = usedTokens.get(2);
          redTokenAmount = usedTokens.get(3);
          blackTokenAmount = usedTokens.get(4);
          goldTokenAmount = usedTokens.get(5);
        }

        usedTokens = new ArrayList<>();

        BaseCard card = Game.getInstance().player1.getInventory().getReservedCards().get(cardId);

        int amountSelected = GameBoardController.getInstance().getSelectedItemsSize();

        if (amountSelected > 1) {
          String extra1Id = GameBoardController.getInstance().getSelectedItem(1);
          extra1 = cardNameToIntMap.get(extra1Id);
        }

        if (amountSelected > 2) {
          String extra2Id = GameBoardController.getInstance().getSelectedItem(2);
          extra2 = cardNameToIntMap.get(extra2Id);
        }

        if (amountSelected > 3) {
          String extra3Id = GameBoardController.getInstance().getSelectedItem(3);
          extra3 = cardNameToIntMap.get(extra3Id);
        }

        if (amountSelected > 4) {
          String extra4Id = GameBoardController.getInstance().getSelectedItem(4);
          extra4 = cardNameToIntMap.get(extra4Id);
        }

        long actionId = 600000000_00_00_00_00_0_0L
                + whiteTokenAmount * 10000000_00_00_00_00_0_0L
                + blueTokenAmount * 1000000_00_00_00_00_0_0L
                + greenTokenAmount * 100000_00_00_00_00_0_0L
                + redTokenAmount * 10000_00_00_00_00_0_0L
                + blackTokenAmount * 1000_00_00_00_00_0_0L
                + goldTokenAmount * 100_00_00_00_00_0_0L
                + cardId * 1_00_00_00_00_0_0L
                + extra1 * 1_00_00_00_0_0L
                + extra2 * 1_00_00_0_0L
                + extra3 * 1_00_0_0L
                + extra4 * 1_0_0L
                + doubleGold * 1_0L
                + freeToken;

        ClientRequest.makeActionRequest(actionId);
      }
    }

    // Buy Card

    else if (actionType == ActionType.BUY_CARD) {

      String id = GameBoardController.getInstance().getSelectedItem(0);
      System.out.println("Bought card : " + id);
      String sub = id.substring(0, 5);

      // Buy Card
      if (sub.equals("viewC")) {

        int cardId = cardNameToIntMap.get(id);

        int whiteTokenAmount = 0;
        int blueTokenAmount = 0;
        int greenTokenAmount = 0;
        int redTokenAmount = 0;
        int blackTokenAmount = 0;
        int goldTokenAmount = 0;

        if (usedTokens.size() > 0) {
          whiteTokenAmount = usedTokens.get(0);
          blueTokenAmount = usedTokens.get(1);
          greenTokenAmount = usedTokens.get(2);
          redTokenAmount = usedTokens.get(3);
          blackTokenAmount = usedTokens.get(4);
          goldTokenAmount = usedTokens.get(5);
        }

        usedTokens = new ArrayList<>();

        BaseCard card = Game.getInstance().cardSlots.get(cardId).getCard();

        int amountSelected = GameBoardController.getInstance().getSelectedItemsSize();

        if (amountSelected > 1) {
          String extra1Id = GameBoardController.getInstance().getSelectedItem(1);
          if (!extra1Id.startsWith("viewToken")) {
            extra1 = cardNameToIntMap.get(extra1Id);
          }
        }

        if (amountSelected > 2) {
          String extra2Id = GameBoardController.getInstance().getSelectedItem(2);
          if (!extra2Id.startsWith("viewToken")) {
            extra2 = cardNameToIntMap.get(extra2Id);
          }
        }

        if (amountSelected > 3) {
          String extra3Id = GameBoardController.getInstance().getSelectedItem(3);
          if (!extra3Id.startsWith("viewToken")) {
            extra3 = cardNameToIntMap.get(extra3Id);
          }
        }

        if (amountSelected > 4) {
          String extra4Id = GameBoardController.getInstance().getSelectedItem(4);
          if (!extra4Id.startsWith("viewToken")) {
            extra4 = cardNameToIntMap.get(extra4Id);
          }
        }


        long actionId = 300000000_00_00_00_00_0_0L
            + whiteTokenAmount * 10000000_00_00_00_00_0_0L
            + blueTokenAmount * 1000000_00_00_00_00_0_0L
            + greenTokenAmount * 100000_00_00_00_00_0_0L
            + redTokenAmount * 10000_00_00_00_00_0_0L
            + blackTokenAmount * 1000_00_00_00_00_0_0L
            + goldTokenAmount * 100_00_00_00_00_0_0L
            + cardId * 1_00_00_00_00_0_0L
            + extra1 * 1_00_00_00_0_0L
            + extra2 * 1_00_00_0_0L
            + extra3 * 1_00_0_0L
            + extra4 * 1_0_0L
            + doubleGold * 1_0L
            + freeToken;

        ClientRequest.makeActionRequest(actionId);

      }

    }

    // Reserve Card

    else if (actionType == ActionType.RESERVE_CARD) {

      boolean takingGoldToken = ( GameBoardController.getInstance().getSelectedItemsSize() == 2);

      String selectedFirst = GameBoardController.getInstance().getSelectedItem(0);
      int cardNumber = cardNameToIntMap.get(selectedFirst);

      int i = 0;
      if (takingGoldToken) {
        i = 1;
      }

      long actionId = 400000000_00_00_00_00L
          + cardNumber * 1000000_00_00_00_00L
          + i * 100000_00_00_00_00L ;

      ClientRequest.makeActionRequest(actionId);

    }
    //Reserve Card from Deck
    else if (actionType == ActionType.RESERVE_FROM_DECK) {
      System.out.println("got to action type");
      boolean takingGoldToken = ( GameBoardController.getInstance().getSelectedItemsSize() == 2);

      String selectedFirst = GameBoardController.getInstance().getSelectedItem(0);
      int DeckColor = DeckToIntMap.get(selectedFirst);

      int i = 0;
      if (takingGoldToken) {
        i = 1;
      }

      long actionId = 400000000_00_00_00_00L
          + DeckColor *   10000_00_00_00_00L
          + i *          100000_00_00_00_00L ;

      ClientRequest.makeActionRequest(actionId);

    }
    System.out.println("request sent nth returned?");


    GameBoardController.getInstance().setSelectionStatus(SelectionStatus.NORMAL);

    SplendorUiApplication.aGame.updatePlayer1Tokens();
    GameBoardController.getInstance().deselectAll();
    confirmActionSetNotActive();

  }


  /**
   * Resets the tokens that are marked as being used for the current action. Deselects all items
   * from the board and from the player's inventory that are currently selected. Resets the value
   * of all extra action variables. Changes the selection status to normal and disables the
   * confirm action button.
   */
  public void onClickDeselectAll() {
    usedTokens = new ArrayList<>();
    SplendorUiApplication.aGame.updatePlayer1Tokens();
    GameBoardController.getInstance().deselectAll();
    resetExtra();
    GameBoardController.getInstance().setSelectionStatus(SelectionStatus.NORMAL);
    confirmActionSetNotActive();
  }

  public void resetExtra() {
    extra1 = 0;
    extra2 = 0;
    extra3 = 0;
    extra4 = 0;
    doubleGold = 0;
    freeToken = 0;
  }

  public void onClickMenuButton() {
    try {
      switchToLobbyMenu();
    } catch (IOException e) {
      throw new RuntimeException("Failed to switch to Lobby");
    }
  }

  public void onClickSaveButton() {
    try {
      ClientRequest.makeSaveRequest();
    } catch (UnirestException e) {
      throw new RuntimeException("Failed to save game");
    }
  }


  /**
   * Switches to the lobby menu when the menu button is clicked.
   *
   * @throws IOException if the fxml file cannot be found
   */
  public void switchToLobbyMenu() throws IOException {

    GameStart.statePollingThread.interrupt();
    ScreenUpdater.clearState();
    Game.clearGame();
    PlayerOthersController.clearInstance();

    Parent root = FXMLLoader.load(getClass().getResource("lobby.fxml"));
    Stage stage = (Stage) player1NameLabel.getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);
  }


}