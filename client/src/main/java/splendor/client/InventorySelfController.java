package splendor.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import splendor.gameelements.BaseCard;
import splendor.gameelements.BonusColor;
import splendor.gameelements.Inventory;
import splendor.gameelements.OrientCardType;
import splendor.gameelements.TradingPostType;

/**
 * JavaFX controller for the inventory of the player.
 */
public class InventorySelfController implements Initializable {

  private static InventorySelfController instance;

  /**
   * Owned cards.
   */
  private final ObjectProperty<Image> imageOwned1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned4 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned5 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned6 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned7 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned8 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned9 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned10 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned11 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwned12 = new SimpleObjectProperty<>();

  /**
   * Reserved cards.
   */
  private final ObjectProperty<Image> imageReserved1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageReserved2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageReserved3 = new SimpleObjectProperty<>();

  /**
   * Owned nobles
   */
  private final ObjectProperty<Image> imageOwnedNoble1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwnedNoble2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageOwnedNoble3 = new SimpleObjectProperty<>();

  /**
   * Reserved nobles
   */
  private final ObjectProperty<Image> imageReservedNoble1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageReservedNoble2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageReservedNoble3 = new SimpleObjectProperty<>();

  /**
   * Token amounts.
   */
  @FXML
  private Label player1WhiteTokenAmount;
  @FXML
  private Label player1BlueTokenAmount;
  @FXML
  private Label player1GreenTokenAmount;
  @FXML
  private Label player1RedTokenAmount;
  @FXML
  private Label player1BlackTokenAmount;
  @FXML
  private Label player1GoldTokenAmount;

  /**
   * Tokens to be used on the selected action.
   */
  @FXML
  private Label player1WhiteTokenUsed;
  @FXML
  private Label player1BlueTokenUsed;
  @FXML
  private Label player1GreenTokenUsed;
  @FXML
  private Label player1RedTokenUsed;
  @FXML
  private Label player1BlackTokenUsed;
  @FXML
  private Label player1GoldTokenUsed;

  /**
   * Image views of owned cards.
   */
  @FXML
  private ImageView ownedCard1;
  @FXML
  private ImageView ownedCard2;
  @FXML
  private ImageView ownedCard3;
  @FXML
  private ImageView ownedCard4;
  @FXML
  private ImageView ownedCard5;
  @FXML
  private ImageView ownedCard6;
  @FXML
  private ImageView ownedCard7;
  @FXML
  private ImageView ownedCard8;
  @FXML
  private ImageView ownedCard9;
  @FXML
  private ImageView ownedCard10;
  @FXML
  private ImageView ownedCard11;
  @FXML
  private ImageView ownedCard12;
  @FXML
  private ImageView ownedCard13;
  @FXML
  private ImageView ownedCard14;
  @FXML
  private ImageView ownedCard15;
  @FXML
  private ImageView ownedCard16;
  @FXML
  private ImageView ownedCard17;
  @FXML
  private ImageView ownedCard18;
  @FXML
  private ImageView ownedCard19;
  @FXML
  private ImageView ownedCard20;
  @FXML
  private ImageView reservedCard1;
  @FXML
  private ImageView reservedCard2;
  @FXML
  private ImageView reservedCard3;
  @FXML
  private ImageView player1OwnedNoble1;
  @FXML
  private ImageView player1OwnedNoble2;
  @FXML
  private ImageView player1OwnedNoble3;
  @FXML
  private Button buttonReservedCard1;
  @FXML
  private Button buttonReservedCard2;
  @FXML
  private Button buttonReservedCard3;
  @FXML
  private ImageView player1ReservedNoble1;
  @FXML
  private ImageView player1ReservedNoble2;
  @FXML
  private ImageView player1ReservedNoble3;
  private Map<Integer, Label> colorToUsedLabelMap = new HashMap<>();
  private List<ImageView> ownedCards = new ArrayList<>();
  private boolean sentImageViews = false;
  private boolean sentReservedCardImageViews = false;
  private List<ImageView> reservedCardsImageViews = new ArrayList<ImageView>();
  private List<Button> reservedCardsButtons = new ArrayList<>();

  public InventorySelfController() {
  }

  public static InventorySelfController getInstance() {
    return InventorySelfController.instance;
  }

  EventHandler<MouseEvent> eventHandlerClickingReservedCard = new EventHandler<>() {
    @Override
    public void handle(MouseEvent e) {
      if (CardPopupController.getInstance().isVisible()) {
        return;
      }

      if (Game.getInstance().getCurrentPlayer() != Game.getInstance().myPlayerNumber) {
        return;
      }

      ImageView currentCardView = (ImageView) ((Button) e.getTarget()).getGraphic();
      String cardId = currentCardView.getId();

      if (!sentReservedCardImageViews) {
        GameBoardController.getInstance().receiveImageViews(reservedCardsImageViews);
        sentReservedCardImageViews = true;
      }

      System.out.println("ReservedCardPopUp for "+cardId);
      System.out.println(ReservedCardPopUpController.getInstance());

      int id = Integer.parseInt(cardId.substring(cardId.length()-1));
      //Make sure the reserve card exists
      if(Game.getInstance().player1.getInventory().getReservedCards().size() >= id){
        ReservedCardPopUpController.getInstance().setVisible(currentCardView);
      }
    }
  };

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    System.out.println("dwauid");
    InventorySelfController.instance = this;
    colorToUsedLabelMap.put(0, player1WhiteTokenUsed);
    colorToUsedLabelMap.put(1, player1BlueTokenUsed);
    colorToUsedLabelMap.put(2, player1GreenTokenUsed);
    colorToUsedLabelMap.put(3, player1RedTokenUsed);
    colorToUsedLabelMap.put(4, player1BlackTokenUsed);
    colorToUsedLabelMap.put(5, player1GoldTokenUsed);
    ownedCards = Arrays.asList(ownedCard1, ownedCard2, ownedCard3, ownedCard4, ownedCard5,
        ownedCard6, ownedCard7, ownedCard8, ownedCard9, ownedCard10,
        ownedCard11, ownedCard12, ownedCard13, ownedCard14, ownedCard15, ownedCard16, ownedCard17,
        ownedCard18, ownedCard19, ownedCard20);
    reservedCardsImageViews = Arrays.asList(reservedCard1,reservedCard2,reservedCard3);
    reservedCardsButtons = Arrays.asList(buttonReservedCard1,buttonReservedCard2,buttonReservedCard3);
    for (ImageView imageView : ownedCards) {
      imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerClickingOwnedCard);
    }

    for (Button button : reservedCardsButtons) {
      button.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerClickingReservedCard);
    }

  }

  public int getOwnedCardImageViewNumber(ImageView view) {
    assert ownedCards.contains(view);
    return ownedCards.indexOf(view);
  }

  EventHandler<MouseEvent> eventHandlerClickingOwnedCard = new EventHandler<>() {
    @Override
    public void handle(MouseEvent e) {
      if (CardPopupController.getInstance().isVisible()) {
        return;
      }

      if (Game.getInstance().getCurrentPlayer() != Game.getInstance().myPlayerNumber) {
        return;
      }

      if (!sentImageViews) {
        GameBoardController.getInstance().receiveImageViews(ownedCards);
        sentImageViews = true;
      }

      ImageView currentCardView = (ImageView) e.getTarget();

      if(currentCardView.getImage() == null) {
        System.out.println("Card image is NULL");
        return;
      }
      if (PlayerSelfController.getInstance().getNumberFromImageId(currentCardView.getId())
          > Game.getInstance().player1.getInventory().getAllCards().size() - 1 ) {
        System.out.println("Number too large");
        return;
      }

      if (GameBoardController.getInstance().getSelectionStatus() == SelectionStatus.CLICK_PAIR_L1) {
        GameBoardController.getInstance().select(currentCardView.getId());

        GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CLICK_L1);
      }

      else if (GameBoardController.getInstance().getSelectionStatus() == SelectionStatus.CLICK_PAIR) {
        System.out.println("Current card view id : " + currentCardView.getId());
        GameBoardController.getInstance().select(currentCardView.getId());

        PlayerSelfController.getInstance().confirmActionSetActive();
      } else if (GameBoardController.getInstance().getSelectionStatus()
          == SelectionStatus.CLICK_DISCARD) {

        BonusColor discardColor = GameBoardController.getInstance().getDiscardColor();
        int cardNumber = getOwnedCardImageViewNumber(currentCardView);
        Inventory inventory = Game.getInstance().player1.getInventory();
        List<BaseCard> cards = inventory.getAllCards();
        BaseCard currentCard = inventory.getAllCards().get(cardNumber);

        // Card must be the correct bonus color
        if (currentCard.getBonusColor() != discardColor) {
          return;
        }
        // Card must not be already selected
        if (isSelected(cardNumber + 1)) {
          return;
        }

        // Check if there is an unselected pair card of the corresponding color if the current card
        // is not a pair card
        if (currentCard.getType() != OrientCardType.PAIR_CARD && currentCard.getType() != OrientCardType.TAKE_L1_CARD) {
          boolean found = false;
          for (int i = 0; i < cards.size(); i++) {
            BaseCard card = cards.get(i);
            if (card.getBonusColor() != discardColor) {
              continue;
            }
            if (card.getType() != OrientCardType.PAIR_CARD
                && card.getType() != OrientCardType.TAKE_L1_CARD) {
              continue;
            }
            if (!isSelected(i + 1)) {
              found = true;
              break;
            }
          }
          if (found) {
            return;
          }
        }

        GameBoardController.getInstance().select(currentCardView.getId());
        if (currentCard.getType() == OrientCardType.DOUBLE_BONUS
            && getNumberOfSelectedOwnedCards() == 1) {
          PlayerSelfController.getInstance().setExtra3(1);
          if (hasTokenChoice()) {
            GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CHOOSE_FREE_TOKEN);
          } else {
            GameBoardController.getInstance().setSelectionStatus(SelectionStatus.NORMAL);
            PlayerSelfController.getInstance().confirmActionSetActive();
          }
        } else if (getNumberOfSelectedOwnedCards() == 2) {
          PlayerSelfController.getInstance().setExtra3(2);
          if (hasTokenChoice()) {
            GameBoardController.getInstance().setSelectionStatus(SelectionStatus.CHOOSE_FREE_TOKEN);
          } else {
            GameBoardController.getInstance().setSelectionStatus(SelectionStatus.NORMAL);
            PlayerSelfController.getInstance().confirmActionSetActive();
          }
        }
      }

    }
  };

  private boolean hasTokenChoice() {
    return Game.getInstance().player1.hasTradingPost(TradingPostType.FREE_TOKEN_WITH_PURCHASE);
  }

  private boolean isSelected(int cardNumber) {
    String numberStr = String.valueOf(cardNumber);
    for (int i = 0; i < GameBoardController.getInstance().getSelectedItemsSize(); i++) {
      String selectedItem = GameBoardController.getInstance().getSelectedItem(i);
      if (selectedItem.equals("ownedCard".concat(numberStr))) {
        return true;
      }
    }
    return false;
  }

  private int getNumberOfSelectedOwnedCards() {
    int n = 0;
    for (int i = 0; i < GameBoardController.getInstance().getSelectedItemsSize(); i++) {
      String selectedItem = GameBoardController.getInstance().getSelectedItem(i);
      if (selectedItem.startsWith("ownedC")) {
        n += 1;
      }
    }
    return n;
  }

  public void setPlayer1WhiteTokenAmount(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1WhiteTokenAmount.setText(paramTokens);

      }
    });

  }

  public void setPlayer1BlueTokenAmount(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1BlueTokenAmount.setText(paramTokens);

      }
    });

  }

  public void setPlayer1GreenTokenAmount(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1GreenTokenAmount.setText(paramTokens);

      }
    });

  }

  public void setPlayer1RedTokenAmount(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1RedTokenAmount.setText(paramTokens);

      }
    });

  }

  public void setPlayer1BlackTokenAmount(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1BlackTokenAmount.setText(paramTokens);

      }
    });

  }

  public void setPlayer1GoldTokenAmount(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        player1GoldTokenAmount.setText(paramTokens);

      }
    });

  }

  public void setPlayer1WhiteTokenUsed(String paramTokens) {
    player1WhiteTokenUsed.setVisible(!(paramTokens.equals("0")));
    player1WhiteTokenUsed.setText(paramTokens);
  }

  public void setPlayer1BlueTokenUsed(String paramTokens) {
    player1BlueTokenUsed.setVisible(!(paramTokens.equals("0")));
    player1BlueTokenUsed.setText(paramTokens);
  }

  public void setPlayer1GreenTokenUsed(String paramTokens) {
    player1GreenTokenUsed.setVisible(!(paramTokens.equals("0")));
    player1GreenTokenUsed.setText(paramTokens);
  }

  public void setPlayer1RedTokenUsed(String paramTokens) {
    player1RedTokenUsed.setVisible(!(paramTokens.equals("0")));
    player1RedTokenUsed.setText(paramTokens);
  }

  public void setPlayer1BlackTokenUsed(String paramTokens) {
    player1BlackTokenUsed.setVisible(!(paramTokens.equals("0")));
    player1BlackTokenUsed.setText(paramTokens);
  }

  public void setPlayer1GoldTokenUsed(String paramTokens) {
    player1GoldTokenUsed.setVisible(!(paramTokens.equals("0")));
    player1GoldTokenUsed.setText(paramTokens);
  }

  public final Image getImageOwned1() {
    return imageOwned1Property().get();
  }

  public final void setImageOwned1(String paramImagePath) {

    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final Image getImageOwned2() {
    return imageOwned2Property().get();
  }

  public final void setImageOwned2(String paramImagePath) {

    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final Image getImageOwned3() {
    return imageOwned3Property().get();
  }

  public final void setImageOwned3(String paramImagePath) {


    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final Image getImageOwned4() {
    return imageOwned4Property().get();
  }

  public final Image getImageOwned5() {
    return imageOwned5Property().get();
  }

  public final Image getImageOwned6() {
    return imageOwned6Property().get();
  }

  public final Image getImageOwned7() {
    return imageOwned7Property().get();
  }

  public final Image getImageOwned8() {
    return imageOwned8Property().get();
  }

  public final Image getImageOwned9() {
    return imageOwned9Property().get();
  }

  public final Image getImageOwned10() {
    return imageOwned10Property().get();
  }

  public final Image getImageOwned11() {
    return imageOwned11Property().get();
  }

  public final Image getImageOwned12() {
    return imageOwned12Property().get();
  }

  public final void setImageOwned4(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned4Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned5(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned5Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned6(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned6Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned7(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned7Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned8(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned8Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned9(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned9Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned10(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned10Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned11(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned11Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final void setImageOwned12(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwned12Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public ObjectProperty<Image> imageOwned1Property() {
    return imageOwned1;
  }

  public ObjectProperty<Image> imageOwned2Property() {
    return imageOwned2;
  }

  public ObjectProperty<Image> imageOwned3Property() {
    return imageOwned3;
  }

  public ObjectProperty<Image> imageOwned4Property() {
    return imageOwned4;
  }

  public ObjectProperty<Image> imageOwned5Property() {
    return imageOwned5;
  }

  public ObjectProperty<Image> imageOwned6Property() {
    return imageOwned6;
  }

  public ObjectProperty<Image> imageOwned7Property() {
    return imageOwned7;
  }

  public ObjectProperty<Image> imageOwned8Property() {
    return imageOwned8;
  }

  public ObjectProperty<Image> imageOwned9Property() {
    return imageOwned9;
  }

  public ObjectProperty<Image> imageOwned10Property() {
    return imageOwned10;
  }

  public ObjectProperty<Image> imageOwned11Property() {
    return imageOwned11;
  }

  public ObjectProperty<Image> imageOwned12Property() {
    return imageOwned12;
  }

  public ObjectProperty<Image> imageReserved1Property() {
    return imageReserved1;
  }

  public ObjectProperty<Image> imageReserved2Property() {
    return imageReserved2;
  }

  public ObjectProperty<Image> imageReserved3Property() {
    return imageReserved3;
  }

  public final Image getImageReserved1() {
    return imageReserved1Property().get();
  }


  public final void setImageReserved1(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageReserved1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final Image getImageReserved2() {
    return imageReserved2Property().get();
  }


  public final void setImageReserved2(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageReserved2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public final Image getImageReserved3() {
    return imageReserved3Property().get();
  }

  public final void setImageReserved3(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageReserved3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }


  public ObjectProperty<Image> imageOwnedNoble1Property() {
    return imageOwnedNoble1;
  }

  public final Image getImageOwnedNoble1() {
    return imageOwnedNoble1Property().get();
  }

  public final void setImageOwnedNoble1(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwnedNoble1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public ObjectProperty<Image> imageOwnedNoble2Property() {
    return imageOwnedNoble2;
  }

  public final Image getImageOwnedNoble2() {
    return imageOwnedNoble2Property().get();
  }

  public final void setImageOwnedNoble2(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwnedNoble2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public ObjectProperty<Image> imageOwnedNoble3Property() {
    return imageOwnedNoble3;
  }

  public final Image getImageOwnedNoble3() {
    return imageOwnedNoble3Property().get();
  }

  public final void setImageOwnedNoble3(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageOwnedNoble3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }


  public ObjectProperty<Image> imageReservedNoble1Property() {
    return imageReservedNoble1;
  }

  public final Image getImageReservedNoble1() {
    return imageReservedNoble1Property().get();
  }

  public final void setImageReservedNoble1(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageReservedNoble1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public ObjectProperty<Image> imageReservedNoble2Property() {
    return imageReservedNoble2;
  }

  public final Image getImageReservedNoble2() {
    return imageReservedNoble2Property().get();
  }

  public final void setImageReservedNoble2(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageReservedNoble2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }

  public ObjectProperty<Image> imageReservedNoble3Property() {
    return imageReservedNoble3;
  }

  public final Image getImageReservedNoble3() {
    return imageReservedNoble3Property().get();
  }

  public final void setImageReservedNoble3(String paramImagePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        imageReservedNoble3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

      }
    });

  }


}
