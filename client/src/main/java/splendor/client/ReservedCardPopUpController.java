package splendor.client;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.w3c.dom.ls.LSResourceResolver;
import splendor.gameelements.BaseCard;
import splendor.gameelements.BonusColor;
import splendor.gameelements.Inventory;
import splendor.gameelements.OrientCard;
import splendor.gameelements.OrientCardType;
import splendor.gameelements.Player;
import splendor.gameelements.TradingPostType;

/**
 * JavaFX controller class for the card popup when a reserved card is clicked.
 */
public class ReservedCardPopUpController implements Initializable {

  private static ReservedCardPopUpController instance;
  int costs = 0;
  int counter = 1;
  int max1;
  int max2;
  int max3;
  int max4;
  BaseCard currentCard;
  String currentImageId;
  @FXML
  private Label tester;
  @FXML
  private Label g1;
  @FXML
  private Label g2;
  @FXML
  private Label g3;
  @FXML
  private Label g4;
  @FXML
  private Circle circle1;
  @FXML
  private Circle circle2;
  @FXML
  private Circle circle3;
  @FXML
  private Circle circle4;
  @FXML
  private Button plus1;
  @FXML
  private Button plus2;
  @FXML
  private Button plus3;
  @FXML
  private Button plus4;
  @FXML
  private Button minus1;
  @FXML
  private Button minus2;
  @FXML
  private Button minus3;
  @FXML
  private Button minus4;
  @FXML
  private AnchorPane cardAnchorPane;
  @FXML
  private ImageView cardImageView;
  @FXML
  private AnchorPane mainAnchorPane;

  // Gold cards
  @FXML
  private Label availableGold;
  @FXML
  private Label usingGold;
  @FXML
  private Label unassignedGold;
  @FXML
  private Button plusGoldCard;
  @FXML
  private Button minusGoldCard;
  @FXML
  private Label doubleGoldValue;
  private int freeUsed;

  private Map<Integer, String> positionToColorMap = new HashMap<>();
  private Map<String, Integer> colorToCostMap = new HashMap<>();
  private Map<String, Label> colorToLabelMap = new HashMap<>();
  private Map<Integer, Label> intToLabelMap = new HashMap<>();

  public static ReservedCardPopUpController getInstance() {
    return ReservedCardPopUpController.instance;
  }

  @FXML
  protected void onBuyButtonClick() {

    System.out.println(currentCard.getClass().toString());
    System.out.println(currentCard.getType().toString());

    if (currentCard.getType() == OrientCardType.PAIR_CARD
        && Game.getInstance().player1.getInventory().getAllCards().size() == 0) {
      // CANT PURCHASE CARD BECAUSE NO CARD IN INVENTORY TO PAIR TO
      return;
    }

    if (currentCard.getType() == OrientCardType.TAKE_L1_CARD
        && Game.getInstance().player1.getInventory().getAllCards().size() == 0) {
      // CANT PURCHASE CARD BECAUSE NO CARD IN INVENTORY TO PAIR TO
      return;
    }

    if (currentCard.getType() == OrientCardType.DISCARD_BONUSES) {
      if (getAvailableDiscards(Game.getInstance().player1, (OrientCard) currentCard) < 2) {
        // CANT PURCHASE BECAUSE NOT ENOUGH CARDS IN INVENTORY TO DISCARD
        return;
      }

      // Purchase discard card, different from the rest, so we ignore the rest of the method
      PlayerSelfController.getInstance().onClickDeselectAll();
      GameBoardController.getInstance().select(currentImageId);
      GameBoardController.getInstance().setSelectedAction(ActionType.BUY_CARD);

      // Set selection status and discard color
      GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CLICK_DISCARD);
      BonusColor discardColor = ((OrientCard) currentCard).getDiscardColor();
      GameBoardController.getInstance().setDiscardColor(discardColor);

      hide();
      return;
    }

    // Check if enough resources
    int blackGold = 0;
    int redGold = 0;
    int greenGold = 0;
    int blueGold = 0;
    int whiteGold = 0;

    try {
      blackGold = Integer.parseInt(colorToLabelMap.get("black").getText());
    } catch (Exception e) {
      blackGold = 0;
    }
    try {
      redGold = Integer.parseInt(colorToLabelMap.get("red").getText());
    } catch (Exception e) {
      redGold = 0;
    }
    try {
      greenGold = Integer.parseInt(colorToLabelMap.get("green").getText());
    } catch (Exception e) {
      greenGold = 0;
    }
    try {
      blueGold = Integer.parseInt(colorToLabelMap.get("blue").getText());
    } catch (Exception e) {
      blueGold = 0;
    }
    try {
      whiteGold = Integer.parseInt(colorToLabelMap.get("white").getText());
    } catch (Exception e) {
      whiteGold = 0;
    }

    Inventory inventory = SplendorUiApplication.aGame.player1.getInventory();
    int missing = 0;
    missing += Math.max(0,
        currentCard.getBlackCost() - inventory.getBlackBonus() - inventory.getBlackTokens()
            - blackGold);
    missing += Math.max(0,
        currentCard.getRedCost() - inventory.getRedBonus() - inventory.getRedTokens() - redGold);
    missing += Math.max(0,
        currentCard.getGreenCost() - inventory.getGreenBonus() - inventory.getGreenTokens()
            - greenGold);
    missing += Math.max(0,
        currentCard.getBlueCost() - inventory.getBlueBonus() - inventory.getBlueTokens()
            - blueGold);
    missing += Math.max(0,
        currentCard.getWhiteCost() - inventory.getWhiteBonus() - inventory.getWhiteTokens()
            - whiteGold);

    if (missing == 0) {
      boolean doubleValue = Game.getInstance().player1.hasTradingPost(TradingPostType.DOUBLE_GOLD);
      int maxExcess = doubleValue ? 3 : 1;
      int unassignedAmount = Integer.parseInt(unassignedGold.getText());
      if (unassignedAmount > maxExcess) {
        return;
      }

      PlayerSelfController.getInstance().onClickDeselectAll();

      PlayerSelfController.getInstance().usedTokens.add(currentCard.getWhiteCost() - whiteGold);
      PlayerSelfController.getInstance().usedTokens.add(currentCard.getBlueCost() - blueGold);
      PlayerSelfController.getInstance().usedTokens.add(currentCard.getGreenCost() - greenGold);
      PlayerSelfController.getInstance().usedTokens.add(currentCard.getRedCost() - redGold);
      PlayerSelfController.getInstance().usedTokens.add(currentCard.getBlackCost() - blackGold);
      System.out.println(
          blackGold + " " + redGold + " " + greenGold + " " + blueGold + " " + whiteGold);
      PlayerSelfController.getInstance().usedTokens.add(
          blackGold + redGold + greenGold + blueGold + whiteGold - freeUsed);

      PlayerSelfController.getInstance().setDoubleGold(Integer.parseInt(usingGold.getText()));

      //GameBoardController.getInstance().deselectAll();
      //PlayerSelfController.getInstance().resetExtra();
      //GameBoardActions.resetSelectedTokens();
      GameBoardController.getInstance().select(currentImageId);
      GameBoardController.getInstance().setSelectedAction(ActionType.BUY_RESERVED_CARD);

      // TODO : Check if valid action

      if (currentCard.getType() == OrientCardType.TAKE_L1_CARD) {
        GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CLICK_PAIR_L1);
      }

      else if (currentCard.getType() == OrientCardType.TAKE_L2_CARD) {
        GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CLICK_L2);
      }

      else if (currentCard.getType() == OrientCardType.PAIR_CARD) {
        GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CLICK_PAIR);
      }

      else if (currentCard.getType() == OrientCardType.RESERVE_NOBLE) {
        GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CLICK_NOBLE);
      }

      else {
        PlayerSelfController.getInstance().confirmActionSetActive();
      }
      SplendorUiApplication.aGame.updatePlayer1Tokens();
      //tester.setText("Card bought!");
      hide();
    }
  }

  @FXML
  protected void onExitCardButtonClick() {
    hide();
  }

  @FXML
  protected void onG1i() {
    int unassignedAmount = Integer.parseInt(unassignedGold.getText());
    int currentAmount = Integer.parseInt(g1.getText());
    if (currentAmount == max4) {
      return;
    } else if (unassignedAmount > 0) {
      freeUsed += 1;
      unassignedAmount -= 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
      g1.setText(String.valueOf(currentAmount + 1));
    } else {
      g1.setText(String.valueOf(Math.min(Math.min(max4, Integer.parseInt(g1.getText()) + 1),
          Integer.parseInt(g1.getText()) + getGoldTokensLeft())));
    }
  }

  @FXML
  protected void onG2i() {
    int unassignedAmount = Integer.parseInt(unassignedGold.getText());
    int currentAmount = Integer.parseInt(g2.getText());
    if (currentAmount == max3) {
      return;
    } else if (unassignedAmount > 0) {
      freeUsed += 1;
      unassignedAmount -= 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
      g2.setText(String.valueOf(currentAmount + 1));
    } else {
      g2.setText(String.valueOf(Math.min(Math.min(max3, Integer.parseInt(g2.getText()) + 1),
          Integer.parseInt(g2.getText()) + getGoldTokensLeft())));
    }
  }

  @FXML
  protected void onG3i() {
    int unassignedAmount = Integer.parseInt(unassignedGold.getText());
    int currentAmount = Integer.parseInt(g3.getText());
    if (currentAmount == max2) {
      return;
    } else if (unassignedAmount > 0) {
      freeUsed += 1;
      unassignedAmount -= 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
      g3.setText(String.valueOf(currentAmount + 1));
    } else {
      g3.setText(String.valueOf(Math.min(Math.min(max2, Integer.parseInt(g3.getText()) + 1),
          Integer.parseInt(g3.getText()) + getGoldTokensLeft())));
    }
  }

  @FXML
  protected void onG4i() {
    int unassignedAmount = Integer.parseInt(unassignedGold.getText());
    int currentAmount = Integer.parseInt(g4.getText());
    if (currentAmount == max1) {
      return;
    } else if (unassignedAmount > 0) {
      freeUsed += 1;
      unassignedAmount -= 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
      g4.setText(String.valueOf(currentAmount + 1));
    } else {
      g4.setText(String.valueOf(Math.min(Math.min(max1, Integer.parseInt(g4.getText()) + 1),
          Integer.parseInt(g4.getText()) + getGoldTokensLeft())));
    }
  }

  @FXML
  protected void onG1d() {
    int used = getGoldTokensUsed();
    int current = Integer.parseInt(g1.getText());
    if (used == 0 && current > 0) {
      freeUsed -= 1;
      int unassignedAmount = Integer.parseInt(unassignedGold.getText()) + 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
    }
    g1.setText(String.valueOf(Math.max(0, Integer.parseInt(g1.getText()) - 1)));
  }

  @FXML
  protected void onG2d() {
    int used = getGoldTokensUsed();
    int current = Integer.parseInt(g2.getText());
    if (used == 0 && current > 0) {
      freeUsed -= 1;
      int unassignedAmount = Integer.parseInt(unassignedGold.getText()) + 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
    }
    g2.setText(String.valueOf(Math.max(0, Integer.parseInt(g2.getText()) - 1)));
  }

  @FXML
  protected void onG3d() {
    int used = getGoldTokensUsed();
    int current = Integer.parseInt(g3.getText());
    if (used == 0 && current > 0) {
      freeUsed -= 1;
      int unassignedAmount = Integer.parseInt(unassignedGold.getText()) + 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
    }
    g3.setText(String.valueOf(Math.max(0, Integer.parseInt(g3.getText()) - 1)));
  }

  @FXML
  protected void onG4d() {
    int used = getGoldTokensUsed();
    int current = Integer.parseInt(g4.getText());
    if (used == 0 && current > 0) {
      freeUsed -= 1;
      int unassignedAmount = Integer.parseInt(unassignedGold.getText()) + 1;
      unassignedGold.setText(String.valueOf(unassignedAmount));
    }
    g4.setText(String.valueOf(Math.max(0, Integer.parseInt(g4.getText()) - 1)));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ReservedCardPopUpController.instance = this;
    intToLabelMap.put(4, g1);
    intToLabelMap.put(3, g2);
    intToLabelMap.put(2, g3);
    intToLabelMap.put(1, g4);
  }

  public int getGoldTokensLeft() {
    int goldTokensUsed = getGoldTokensUsed();
    return SplendorUiApplication.aGame.player1.getInventory().getGoldTokens() - goldTokensUsed;
  }

  public int getGoldTokensUsed() {
    return Integer.parseInt(g1.getText()) + Integer.parseInt(g2.getText()) + Integer.parseInt(
        g3.getText()) + Integer.parseInt(g4.getText()) - freeUsed;
  }

  public void setVisible(ImageView imageView) {

    // Update available gold cards
    int availableAmount = Game.getInstance().player1.getInventory().getGoldCardAmount();
    availableGold.setText(String.valueOf(availableAmount));
    if (Game.getInstance().player1.hasTradingPost(TradingPostType.DOUBLE_GOLD)) {
      doubleGoldValue.setVisible(true);
    }

    currentImageId = imageView.getId();
    int id = Integer.parseInt(currentImageId.substring(currentImageId.length()-1))-1;
    currentCard = Game.getInstance().player1.getInventory().getReservedCards().get(id);

    System.out.println(currentCard);
    System.out.println(currentCard.getGreenCost());
    System.out.println(currentCard.getRedCost());
    System.out.println(currentCard.getBlackCost());
    System.out.println(currentCard.getBlueCost());

    if (currentCard.getBlackCost() > 0) {
      positionToColorMap.put(counter, "black");
      colorToCostMap.put("black", currentCard.getBlackCost());
      colorToLabelMap.put("black", intToLabelMap.get(counter));
      counter = counter + 1;
      costs += 1;
    }
    if (currentCard.getRedCost() > 0) {
      positionToColorMap.put(counter, "red");
      colorToCostMap.put("red", currentCard.getRedCost());
      colorToLabelMap.put("red", intToLabelMap.get(counter));
      counter = counter + 1;
      costs += 1;
    }
    if (currentCard.getGreenCost() > 0) {
      positionToColorMap.put(counter, "green");
      colorToCostMap.put("green", currentCard.getGreenCost());
      colorToLabelMap.put("green", intToLabelMap.get(counter));
      counter = counter + 1;
      costs += 1;
    }
    if (currentCard.getBlueCost() > 0) {
      positionToColorMap.put(counter, "blue");
      colorToCostMap.put("blue", currentCard.getBlueCost());
      colorToLabelMap.put("blue", intToLabelMap.get(counter));
      counter = counter + 1;
      costs += 1;
    }
    if (currentCard.getWhiteCost() > 0) {
      positionToColorMap.put(counter, "white");
      colorToCostMap.put("white", currentCard.getWhiteCost());
      colorToLabelMap.put("white", intToLabelMap.get(counter));
      counter = counter + 1;
      costs += 1;
    }
    counter = 1;

    System.out.println(costs);

    if (costs < 4) {
      g1.setVisible(false);
      circle1.setVisible(false);
      plus1.setVisible(false);
      minus1.setVisible(false);
    } else {
      g1.setVisible(true);
      circle1.setVisible(true);
      plus1.setVisible(true);
      minus1.setVisible(true);
    }
    if (costs < 3) {
      g2.setVisible(false);
      circle2.setVisible(false);
      plus2.setVisible(false);
      minus2.setVisible(false);
    } else {
      g2.setVisible(true);
      circle2.setVisible(true);
      plus2.setVisible(true);
      minus2.setVisible(true);
    }
    if (costs < 2) {
      g3.setVisible(false);
      circle3.setVisible(false);
      plus3.setVisible(false);
      minus3.setVisible(false);
    } else {
      g3.setVisible(true);
      circle3.setVisible(true);
      plus3.setVisible(true);
      minus3.setVisible(true);
    }

    try {
      max1 = colorToCostMap.get(positionToColorMap.get(1));
    } catch (Exception e) {
      max1 = 0;
    }

    try {
      max2 = colorToCostMap.get(positionToColorMap.get(2));
    } catch (Exception e) {
      max2 = 0;
    }

    try {
      max3 = colorToCostMap.get(positionToColorMap.get(3));
    } catch (Exception e) {
      max3 = 0;
    }

    try {
      max4 = colorToCostMap.get(positionToColorMap.get(4));
    } catch (Exception e) {
      max4 = 0;
    }

    System.out.println(cardImageView);
    cardImageView.setImage(imageView.getImage());
    GameController.getInstance().setReservedCardVisible(true);

  }

  public void hide() {
    g1.setText("0");
    g2.setText("0");
    g3.setText("0");
    g4.setText("0");

    availableGold.setText("0");
    usingGold.setText("0");
    unassignedGold.setText("0");
    doubleGoldValue.setVisible(false);

    positionToColorMap = new HashMap<>();
    colorToLabelMap = new HashMap<>();
    colorToCostMap = new HashMap<>();
    costs = 0;

    cardImageView.setImage(null);
    GameController.getInstance().setReservedCardVisible(false);
  }

  /**
   * Gets the amount of cards that are available to discard in order to purchase a given card.
   *
   * @param player the player purchasing the card
   * @param card the card of type DISCARD_BONUSES to be purchases
   * @return the amount of bonuses of the corresponding color in the player's inventory that are
   * available to be discarded
   */
  public int getAvailableDiscards(Player player, OrientCard card) {
    assert card.getType() == OrientCardType.DISCARD_BONUSES;
    Inventory inventory = player.getInventory();

    return ( card.getWhiteCost() * inventory.getWhiteBonus()
        + card.getBlueCost() * inventory.getBlueBonus()
        + card.getGreenCost() * inventory.getGreenBonus()
        + card.getRedCost() * inventory.getRedBonus()
        + card.getBlackCost() * inventory.getBlackBonus() ) / 2;


  }

  private void onClickPlusGoldCard() {
    int availableAmount = Integer.parseInt(availableGold.getText());
    int usingAmount = Integer.parseInt(usingGold.getText());
    if (availableAmount > 0) {
      availableAmount -= 1;
      usingAmount += 1;
      availableGold.setText(String.valueOf(availableAmount));
      usingGold.setText(String.valueOf(usingAmount));
      int unassignedAmount = Integer.parseInt(unassignedGold.getText());
      unassignedAmount += 2;
      unassignedGold.setText(String.valueOf(unassignedAmount));
    }
  }

  private void onClickMinusGoldCard() {
    int availableAmount = Integer.parseInt(availableGold.getText());
    int usingAmount = Integer.parseInt(usingGold.getText());
    if (usingAmount > 0) {
      availableAmount += 1;
      usingAmount -= 1;
      availableGold.setText(String.valueOf(availableAmount));
      usingGold.setText(String.valueOf(usingAmount));
      int unassignedAmount = usingAmount * 2;
      resetGold();
      freeUsed = 0;
    }
  }

  private void resetGold() {
    g1.setText("0");
    g2.setText("0");
    g3.setText("0");
    g4.setText("0");
  }


}