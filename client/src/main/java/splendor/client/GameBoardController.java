package splendor.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import splendor.gameelements.BaseCard;
import splendor.gameelements.BonusColor;
import splendor.gameelements.CardSlot;
import splendor.gameelements.NobleSlot;
import splendor.gameelements.OrientCardType;
import splendor.gameelements.TradingPostType;

/**
 * JavaFX controller for the game board.
 */
public class GameBoardController implements Initializable {

  private static GameBoardController instance;
  /**
   * Methods to set each card image.
   */
  private final ObjectProperty<Image> city1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> city2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> city3 = new SimpleObjectProperty<>();
  
  private final ObjectProperty<Image> imageG1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageG2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageG3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageG4 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageO1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageO2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageO3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageO4 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageB1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageB2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageB3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageB4 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageR1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageR2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageR3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageR4 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageR5 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageR6 = new SimpleObjectProperty<>();

  /**
   * Methods to set each noble image
   */
  private final ObjectProperty<Image> imageNoble1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageNoble2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageNoble3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageNoble4 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageNoble5 = new SimpleObjectProperty<>();

  private final ObjectProperty<Image> imagePlaymat = new SimpleObjectProperty<>();
  @FXML
  private ImageView viewPlaymat;

  @FXML
  private Pane tradingPosts;

  @FXML
  Label hint;

  public void addTradingPost(TradingPostShape tp) {

    Platform.runLater(() -> {

      //System.out.println("BBBBBBBBBBBB");
      tradingPosts.getChildren().add(tp);
      //Pane newPane = new Pane();
      //Rectangle rect = new Rectangle(50, 50, Color.RED);
      //newPane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
      //tradingPosts.getChildren().add(newPane);
      //tradingPosts.getChildren().add(rect);

      //tradingPosts.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));

    });

  }

  public void removeTradingPost(TradingPostShape tp) {
    tradingPosts.getChildren().remove(tp);
  }

  @FXML
  private ImageView tradingIcon;
  @FXML
  private Label tradingLabel;

  public void setVisibleTrading() {
    tradingIcon.setVisible(true);
    tradingLabel.setVisible(true);
  }

  @FXML
  private ImageView cityCard1;
  @FXML
  private ImageView cityCard2;
  @FXML
  private ImageView cityCard3;

  @FXML
  private ImageView viewCardG1;
  @FXML
  private ImageView viewCardG2;
  @FXML
  private ImageView viewCardG3;
  @FXML
  private ImageView viewCardG4;
  @FXML
  private ImageView viewCardO1;
  @FXML
  private ImageView viewCardO2;
  @FXML
  private ImageView viewCardO3;
  @FXML
  private ImageView viewCardO4;
  @FXML
  private ImageView viewCardB1;
  @FXML
  private ImageView viewCardB2;
  @FXML
  private ImageView viewCardB3;
  @FXML
  private ImageView viewCardB4;
  @FXML
  private ImageView viewCardR1;
  @FXML
  private ImageView viewCardR2;
  @FXML
  private ImageView viewCardR3;
  @FXML
  private ImageView viewCardR4;
  @FXML
  private ImageView viewCardR5;
  @FXML
  private ImageView viewCardR6;
  @FXML
  private ImageView viewNoble1;
  @FXML
  private ImageView viewNoble2;
  @FXML
  private ImageView viewNoble3;
  @FXML
  private ImageView viewNoble4;
  @FXML
  private ImageView viewNoble5;
  @FXML
  private ImageView viewToken0;
  @FXML
  private ImageView viewToken1;
  @FXML
  private ImageView viewToken2;
  @FXML
  private ImageView viewToken3;
  @FXML
  private ImageView viewToken4;
  @FXML
  private ImageView viewTokenGold;
  @FXML
  private ImageView DeckRL1;
  @FXML
  private ImageView DeckL1;
  @FXML
  private ImageView DeckRL2;
  @FXML
  private ImageView DeckL2;
  @FXML
  private ImageView DeckRL3;
  @FXML
  private ImageView DeckL3;
  @FXML
  private Button buttonCardG1;
  @FXML
  private Button buttonCardG2;
  @FXML
  private Button buttonCardG3;
  @FXML
  private Button buttonCardG4;
  @FXML
  private Button buttonCardO1;
  @FXML
  private Button buttonCardO2;
  @FXML
  private Button buttonCardO3;
  @FXML
  private Button buttonCardO4;
  @FXML
  private Button buttonCardB1;
  @FXML
  private Button buttonCardB2;
  @FXML
  private Button buttonCardB3;
  @FXML
  private Button buttonCardB4;
  @FXML
  private Button buttonCardR1;
  @FXML
  private Button buttonCardR2;
  @FXML
  private Button buttonCardR3;
  @FXML
  private Button buttonCardR4;
  @FXML
  private Button buttonCardR5;
  @FXML
  private Button buttonCardR6;
  @FXML
  private Button buttonNoble1;
  @FXML
  private Button buttonNoble2;
  @FXML
  private Button buttonNoble3;
  @FXML
  private Button buttonNoble4;
  @FXML
  private Button buttonNoble5;
  @FXML
  private Button tokenWhite;
  @FXML
  private Button tokenBlue;
  @FXML
  private Button tokenGreen;
  @FXML
  private Button tokenRed;
  @FXML
  private Button tokenBlack;
  @FXML
  private Label whiteTokenLabel;
  @FXML
  private Label greenTokenLabel;
  @FXML
  private Label blueTokenLabel;
  @FXML
  private Label redTokenLabel;
  @FXML
  private Label blackTokenLabel;
  @FXML
  private Label goldTokenLabel;
  @FXML
  private Label selectedTokensWhite;
  @FXML
  private Label selectedTokensBlue;
  @FXML
  private Label selectedTokensGreen;
  @FXML
  private Label selectedTokensRed;
  @FXML
  private Label selectedTokensBlack;
  @FXML
  private Label greenDeckLabel;
  @FXML
  private Label orangeDeckLabel;
  @FXML
  private Label blueDeckLabel;
  @FXML
  private Label redL1DeckLabel;
  @FXML
  private Label redL2DeckLabel;
  @FXML
  private Label redL3DeckLabel;
  private List<Label> selectedTokens = new ArrayList<>();
  private List<ImageView> cardImageViews = new ArrayList<>();
  private List<ImageView> nobleImageViews = new ArrayList<>();
  private List<Button> cardButtons = new ArrayList<>();
  private List<Button> nobleButtons = new ArrayList<>();
  private List<ImageView> tokenImageViews = new ArrayList<>();
  private List<ImageView> DeckReserveviews = new ArrayList<>();
  private Map<String, ImageView> imageViewMap = new HashMap<>();
  private List<String> selectedItems = new ArrayList<>();
  private ActionType selectedAction;
  private SelectionStatus selectionStatus = SelectionStatus.NORMAL;
  private BonusColor discardColor;
  private Map<ImageView, CardSlot> imageToCardMap = new HashMap<>();
  private Map<ImageView, NobleSlot> imageToNobleMap = new HashMap<>();
  String currentImageId;

  EventHandler<MouseEvent> eventHandlerClickingCard = new EventHandler<>() {
    @Override
    public void handle(MouseEvent e) {

      if (Game.getInstance().getCurrentPlayer() != Game.getInstance().myPlayerNumber) {
        return;
      }

      if (CardPopupController.getInstance().isVisible()) {
        return;
      }

      ImageView currentCardView = (ImageView) ((Button) e.getTarget()).getGraphic();
      // REGULAR CLICK
      if (selectionStatus == SelectionStatus.NORMAL
          || selectionStatus == SelectionStatus.CLICK_NOBLE
          || selectionStatus == SelectionStatus.CLICK_PAIR
          || selectionStatus == SelectionStatus.CLICK_PAIR_L1
          || selectionStatus == SelectionStatus.CLICK_DISCARD) {
        CardPopupController.getInstance().setVisible(currentCardView);
        System.out.println(currentCardView.getId());
      }

      // CLICK LEVEL 1
      else if (selectionStatus == SelectionStatus.CLICK_L1) {
        if (!isLevel1(currentCardView)) {
          CardPopupController.getInstance().setVisible(currentCardView);
          return;
        }

        BaseCard currentCard = getCardSlot(currentCardView).getCard();

        GameBoardController.getInstance().select(currentCardView.getId());

        if (currentCard.getType() == OrientCardType.PAIR_CARD) {
          setSelectionStatus(SelectionStatus.CLICK_PAIR);
        }

        else {
          if (hasTokenChoice()) {
            setSelectionStatus(SelectionStatus.CHOOSE_FREE_TOKEN);
          } else {
            PlayerSelfController.getInstance().confirmActionSetActive();
            setSelectionStatus(SelectionStatus.NORMAL);
          }
        }
      }

      // CLICK LEVEL 2
      else if (selectionStatus == SelectionStatus.CLICK_L2) {
        if (!isLevel2(currentCardView)) {
          CardPopupController.getInstance().setVisible(currentCardView);
          return;
        }

        GameBoardController.getInstance().select(currentCardView.getId());

        BaseCard currentCard = getCardSlot(currentCardView).getCard();

        if (currentCard.getType() == OrientCardType.TAKE_L1_CARD) {
          setSelectionStatus(SelectionStatus.CLICK_PAIR_L1);
        }

        else if (currentCard.getType() == OrientCardType.RESERVE_NOBLE) {
          setSelectionStatus(SelectionStatus.CLICK_NOBLE);
        }

        else {
          if (hasTokenChoice()) {
            setSelectionStatus(SelectionStatus.CHOOSE_FREE_TOKEN);
          } else {
            PlayerSelfController.getInstance().confirmActionSetActive();
            setSelectionStatus(SelectionStatus.NORMAL);
          }
        }
      }

    }
  };

  private boolean isLevel1(ImageView cardView) {
    String id = cardView.getId();
    int viewNumber = PlayerSelfController.getInstance().getNumberFromImageId(id);
    return (viewNumber <= 3) || (viewNumber <= 13 && viewNumber >= 12);
  }

  private boolean isLevel2(ImageView cardView) {
    String id = cardView.getId();
    int viewNumber = PlayerSelfController.getInstance().getNumberFromImageId(id);
    return (viewNumber <= 7 && viewNumber >= 4) || (viewNumber <= 15 && viewNumber >= 14);
  }

  private boolean hasTokenChoice() {
    return Game.getInstance().player1.hasTradingPost(TradingPostType.FREE_TOKEN_WITH_PURCHASE);
  }

  EventHandler<MouseEvent> eventHandlerClickingNoble = new EventHandler<>() {
    @Override
    public void handle(MouseEvent e) {
      if (CardPopupController.getInstance().isVisible()) {
        return;
      }

      if (Game.getInstance().getCurrentPlayer() != Game.getInstance().myPlayerNumber) {
        return;
      }

      ImageView currentNobleView = (ImageView) ((Button) e.getTarget()).getGraphic();

      if (selectionStatus == SelectionStatus.CLICK_NOBLE) {
        select(currentNobleView.getId());
        if (hasTokenChoice()) {
          setSelectionStatus(SelectionStatus.CHOOSE_FREE_TOKEN);
        } else {
          setSelectionStatus(SelectionStatus.NORMAL);
          PlayerSelfController.getInstance().confirmActionSetActive();
        }
      }
      else if (currentNobleView.getStyle()
          .equals("-fx-effect: dropshadow(three-pass-box, green, 10, 0.9, 0 ,0)")) {
        deselectAll();
        int nobleNumber = PlayerSelfController.getInstance()
            .getNumberFromImageId(currentNobleView.getId());

        try {
          ClientRequest.makeActionRequest(50L + nobleNumber);
        } catch (UnirestException ex) {
          System.out.println("Unirest error when sending claim noble action");
        }
      }
    }
  };

  EventHandler<MouseEvent> eventHandlerClickingToken = new EventHandler<>() {
    @Override
    public void handle(MouseEvent e) {
      if (CardPopupController.getInstance().isVisible()) {
        return;
      }

      if (Game.getInstance().getCurrentPlayer() != Game.getInstance().myPlayerNumber) {
        return;
      }

      ImageView currentToken = (ImageView) (e.getTarget());
      String id = currentToken.getId();
      int color = Integer.parseInt(id.substring(id.length() - 1));

      if (selectionStatus == SelectionStatus.CHOOSE_FREE_TOKEN) {
        PlayerSelfController.getInstance().setFreeToken(color);
        GameBoardController.getInstance().select(id);
        GameBoardController.getInstance().setSelectedTokens(color, 1);
        GameBoardController.getInstance().setSelectionStatus(SelectionStatus.NORMAL);
        PlayerSelfController.getInstance().confirmActionSetActive();
      }
    }
  };


  public static GameBoardController getInstance() {
    return GameBoardController.instance;
  }

  public final void setCity1(String paramImagePath) {
    Platform.runLater(() -> {
      city1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
    });
  }
  public final Image getCity1() { return city1.get(); }
  public final ObjectProperty<Image> city1Property() { return city1; }
  public void removeCity1() {
    Platform.runLater(() -> {
      city1Property().set(null);
    });
  }

  public final void setCity2(String paramImagePath) {
    Platform.runLater(() -> {
      city2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }
  public final Image getCity2() { return city2.get(); }
  public final ObjectProperty<Image> city2Property() { return city2; }
  public void removeCity2() {
    Platform.runLater(() -> {
      city2Property().set(null);
    });
  }

  public final void setCity3(String paramImagePath) {
    Platform.runLater(() -> {
      city3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public final Image getCity3() { return city3.get(); }

  public final ObjectProperty<Image> city3Property() { return city3; }
  public void removeCity3() {
    Platform.runLater(() -> {
      city3Property().set(null);
    });
  }

  public ObjectProperty<Image> imageG1Property() {
    return imageG1;
  }

  public ObjectProperty<Image> imagePlaymatProperty() {
    return imagePlaymat;
  }

  public final Image getImageG1() {
    return imageG1Property().get();
  }

  public final Image getImagePlaymat() {
    return imagePlaymatProperty().get();
  }

  public final void setImageG1(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardG1.setVisible(false);
        return;
      }
      imageG1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }


  public final void setImagePlaymat(String paramImagePath) {

    Platform.runLater(() -> {

      imagePlaymatProperty().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageG2Property() {
    return imageG2;
  }

  public final Image getImageG2() {
    return imageG2Property().get();
  }


  public final void setImageG2(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardG2.setVisible(false);
        return;
      }
      imageG2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageG3Property() {
    return imageG3;
  }

  public final Image getImageG3() {
    return imageG3Property().get();
  }


  public final void setImageG3(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardG3.setVisible(false);
        return;
      }
      imageG3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageG4Property() {
    return imageG4;
  }

  public final Image getImageG4() {
    return imageG4Property().get();
  }


  public final void setImageG4(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardG4.setVisible(false);
        return;
      }
      imageG4Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageO1Property() {
    return imageO1;
  }

  public final Image getImageO1() {
    return imageO1Property().get();
  }



  public final void setImageO1(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardO1.setVisible(false);
        return;
      }
      imageO1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageO2Property() {
    return imageO2;
  }

  public final Image getImageO2() {
    return imageO2Property().get();
  }


  public final void setImageO2(String paramImagePath) {

    Platform.runLater(() -> {


      if (paramImagePath == null) {
        buttonCardO2.setVisible(false);
        return;
      }
      imageO2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageO3Property() {
    return imageO3;
  }

  public final Image getImageO3() {
    return imageO3Property().get();
  }


  public final void setImageO3(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardO3.setVisible(false);
        return;
      }
      imageO3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageO4Property() {
    return imageO4;
  }

  public final Image getImageO4() {
    return imageO4Property().get();
  }


  public final void setImageO4(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardO4.setVisible(false);
        return;
      }
      imageO4Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageB1Property() {
    return imageB1;
  }


  public final Image getImageB1() {
    return imageB1Property().get();
  }


  public final void setImageB1(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardB1.setVisible(false);
        return;
      }
      imageB1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageB2Property() {
    return imageB2;
  }


  public final Image getImageB2() {
    return imageB2Property().get();
  }


  public final void setImageB2(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardB2.setVisible(false);
        return;
      }
      imageB2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageB3Property() {
    return imageB3;
  }

  public final Image getImageB3() {
    return imageB3Property().get();
  }


  public final void setImageB3(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardB3.setVisible(false);
        return;
      }
      imageB3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageB4Property() {
    return imageB4;
  }


  public final Image getImageB4() {
    return imageB4Property().get();
  }


  public final void setImageB4(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardB4.setVisible(false);
        return;
      }
      imageB4Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  /////////

  public ObjectProperty<Image> imageR1Property() {
    return imageR1;
  }


  public final Image getImageR1() {
    return imageR1Property().get();
  }


  public final void setImageR1(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardR1.setVisible(false);
        return;
      }
      imageR1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageR2Property() {
    return imageR2;
  }


  public final Image getImageR2() {
    return imageR2Property().get();
  }


  public final void setImageR2(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardR2.setVisible(false);
        return;
      }
      imageR2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageR3Property() {
    return imageR3;
  }

  public final Image getImageR3() {
    return imageR3Property().get();
  }


  public final void setImageR3(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardR3.setVisible(false);
        return;
      }
      imageR3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageR4Property() {
    return imageR4;
  }


  public final Image getImageR4() {
    return imageR4Property().get();
  }


  public final void setImageR4(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardR4.setVisible(false);
        return;
      }
      imageR4Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }



  public ObjectProperty<Image> imageR5Property() {
    return imageR5;
  }


  public final Image getImageR5() {
    return imageR5Property().get();
  }


  public final void setImageR5(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardR5.setVisible(false);
        return;
      }
      imageR5Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageR6Property() {
    return imageR6;
  }


  public final Image getImageR6() {
    return imageR6Property().get();
  }


  public final void setImageR6(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonCardR6.setVisible(false);
        return;
      }
      imageR6Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }





  public ObjectProperty<Image> imageNoble1Property() {
    return imageNoble1;
  }


  public final Image getImageNoble1() {
    return imageNoble1Property().get();
  }


  public final void setImageNoble1(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonNoble1.setVisible(false);
        return;
      }
      imageNoble1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageNoble2Property() {
    return imageNoble2;
  }


  public final Image getImageNoble2() {
    return imageNoble2Property().get();
  }


  public final void setImageNoble2(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonNoble2.setVisible(false);
        return;
      }
      imageNoble2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageNoble3Property() {
    return imageNoble3;
  }


  public final Image getImageNoble3() {
    return imageNoble3Property().get();
  }


  public final void setImageNoble3(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonNoble3.setVisible(false);
        return;
      }
      imageNoble3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageNoble4Property() {
    return imageNoble4;
  }


  public final Image getImageNoble4() {
    return imageNoble4Property().get();
  }


  public final void setImageNoble4(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonNoble4.setVisible(false);
        return;
      }
      imageNoble4Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }

  public ObjectProperty<Image> imageNoble5Property() {
    return imageNoble5;
  }


  public final Image getImageNoble5() {
    return imageNoble5Property().get();
  }


  public final void setImageNoble5(String paramImagePath) {

    Platform.runLater(() -> {

      if (paramImagePath == null) {
        buttonNoble5.setVisible(false);
        return;
      }
      imageNoble5Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));

    });
  }



  /**
   * Methods to set each token label.
   */
  public void setWhiteTokenLabel(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        whiteTokenLabel.setText(paramTokens);

      }
    });

  }

  public void setBlueTokenLabel(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        blueTokenLabel.setText(paramTokens);

      }
    });

  }

  public void setGreenTokenLabel(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        greenTokenLabel.setText(paramTokens);

      }
    });

  }

  public void setRedTokenLabel(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        redTokenLabel.setText(paramTokens);

      }
    });

  }

  public void setBlackTokenLabel(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        blackTokenLabel.setText(paramTokens);

      }
    });

  }


  public void setGoldTokenLabel(String paramTokens) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        goldTokenLabel.setText(paramTokens);

      }
    });

  }

  public void setGreenDeckLabel(String paramCardsLeft) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        greenDeckLabel.setText(paramCardsLeft);

      }
    });
  }

  public void setOrangeDeckLabel(String paramCardsLeft) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        orangeDeckLabel.setText(paramCardsLeft);

      }
    });

  }

  public void setBlueDeckLabel(String paramCardsLeft) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        blueDeckLabel.setText(paramCardsLeft);

      }
    });

  }

  public void setRedL1DeckLabel(String paramCardsLeft) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        redL1DeckLabel.setText(paramCardsLeft);

      }
    });
  }

  public void setRedL2DeckLabel(String paramCardsLeft) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        redL2DeckLabel.setText(paramCardsLeft);

      }
    });

  }

  public void setRedL3DeckLabel(String paramCardsLeft) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        redL3DeckLabel.setText(paramCardsLeft);

      }
    });

  }

  public int getSelectedTokens(int color) {
    return Integer.parseInt(selectedTokens.get(color).getText());
  }

  public void setSelectedTokens(int color, int newLabel) {
    selectedTokens.get(color).setText(String.valueOf(newLabel));
  }

  public int getSelectedItemsSize() {
    return selectedItems.size();
  }

  public String getSelectedItem(int index) {
    return selectedItems.get(index);
  }

  public CardSlot getCardSlot(ImageView imgView) {
    return imageToCardMap.get(imgView);
  }

  public NobleSlot getNobleSlot(ImageView imgView) {
    return imageToNobleMap.get(imgView);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    System.out.println("Initialized Game Board Controller");
    GameBoardController.instance = this;
    selectedTokens = Arrays.asList(selectedTokensWhite, selectedTokensBlue, selectedTokensGreen,
        selectedTokensRed, selectedTokensBlack);
    cardImageViews = Arrays.asList(viewCardG1, viewCardG2, viewCardG3, viewCardG4,
        viewCardO1, viewCardO2, viewCardO3, viewCardO4,
        viewCardB1, viewCardB2, viewCardB3, viewCardB4,
        viewCardR1, viewCardR2, viewCardR3, viewCardR4, viewCardR5, viewCardR6);
    nobleImageViews = Arrays.asList(viewNoble1, viewNoble2, viewNoble3, viewNoble4, viewNoble5);
    cardButtons = Arrays.asList(buttonCardG1, buttonCardG2, buttonCardG3, buttonCardG4,
        buttonCardO1, buttonCardO2, buttonCardO3, buttonCardO4,
        buttonCardB1, buttonCardB2, buttonCardB3, buttonCardB4,
        buttonCardR1, buttonCardR2, buttonCardR3, buttonCardR4, buttonCardR5, buttonCardR6);
    nobleButtons = Arrays.asList(buttonNoble1, buttonNoble2, buttonNoble3, buttonNoble4,
        buttonNoble5);
    tokenImageViews = Arrays.asList(viewToken0, viewToken1, viewToken2, viewToken3, viewToken4,
        viewTokenGold);
    DeckReserveviews = Arrays.asList(DeckL3, DeckRL3,  DeckL2, DeckRL2, DeckL1, DeckRL1 );

    for (ImageView imgView : cardImageViews) {
      imageViewMap.put(imgView.getId(), imgView);
    }
    for (ImageView imgView : nobleImageViews) {
      imageViewMap.put(imgView.getId(), imgView);
    }
    for (ImageView imgView : tokenImageViews) {
      imageViewMap.put(imgView.getId(), imgView);
    }
    for (ImageView imgView : DeckReserveviews) {
      imageViewMap.put(imgView.getId(), imgView);
    }


    for (Button button : cardButtons) {
      button.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerClickingCard);
      System.out.println(button);
    }

    for (Button button : nobleButtons) {
      button.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerClickingNoble);
    }

    for (ImageView tokenView : tokenImageViews) {
      tokenView.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerClickingToken);
    }

    try {
      GameStart.startGame(GlobalUserInfo.getSessionID());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (GlobalUserInfo.getGameService().equals("splendor_orient")) {
      setImagePlaymat("/Assets/high-res-playmat.png");
    }

    if (GlobalUserInfo.getGameService().equals("splendor_trading")) {
      setImagePlaymat("/Assets/trading.png");
    }

    if (GlobalUserInfo.getGameService().equals("splendor_cities")) {
      setImagePlaymat("/Assets/high-res-playmat.png");
    }
  }

  public void receiveImageViews(List<ImageView> views) {
    for (ImageView view : views) {
      if (!imageViewMap.containsKey(view.getId())) {
        imageViewMap.put(view.getId(), view);
      }
    }
  }


  public void updateImageMap() {
    for (ImageView imgView : cardImageViews) {
      imageToCardMap.put(imgView,
          SplendorUiApplication.aGame.cardSlots.get(cardImageViews.indexOf(imgView)));
    }
    for (ImageView imgView : nobleImageViews) {
      imageToNobleMap.put(imgView,
          SplendorUiApplication.aGame.nobleSlots.get(nobleImageViews.indexOf(imgView))
          );
    }

  }


  public void select(String nodeName) {
    ImageView imgView = imageViewMap.get(nodeName);
    imgView.setStyle("-fx-effect: dropshadow(three-pass-box, green, 10, 0.9, 0 ,0)");
    selectedItems.add(nodeName);
  }


  public void deselect(String nodeName) {
    ImageView imgView = imageViewMap.get(nodeName);
    imgView.setStyle("-fx-effect: null");
    selectedItems.remove(nodeName);
  }

  public void reserveSelection(String nodeName) {
    ImageView imgView = imageViewMap.get(nodeName);
    imgView.setStyle("-fx-effect: dropshadow(three-pass-box, yellow, 10, 0.9, 0 ,0)");
    selectedItems.add(nodeName);
  }

  public void deselectAll() {
    while (!selectedItems.isEmpty()) {
      String item = selectedItems.get(0);
      deselect(item);
      selectedItems.remove(item);
      GameBoardActions.resetSelectedTokens();
    }
  }

  public void deselectCards() {
    for (ImageView view : cardImageViews) {
      deselect(view.getId());
    }
    SplendorUiApplication.aGame.updatePlayer1Tokens();
  }


  /**
   * Button methods, let GameBoardActions class do the work to reduce code duplication.
   */
  public void onClickWhiteTokenPlus() {
    GameBoardActions.changeTokens(0, 0);
  }

  public void onClickBlueTokenPlus() {
    GameBoardActions.changeTokens(1, 0);
  }

  public void onClickGreenTokenPlus() {
    GameBoardActions.changeTokens(2, 0);
  }

  public void onClickRedTokenPlus() {
    GameBoardActions.changeTokens(3, 0);
  }

  public void onClickBlackTokenPlus() {
    GameBoardActions.changeTokens(4, 0);
  }

  public void onClickWhiteTokenMinus() {
    GameBoardActions.changeTokens(0, 1);
  }

  public void onClickBlueTokenMinus() {
    GameBoardActions.changeTokens(1, 1);
  }

  public void onClickGreenTokenMinus() {
    GameBoardActions.changeTokens(2, 1);
  }

  public void onClickRedTokenMinus() {
    GameBoardActions.changeTokens(3, 1);
  }

  public void onClickBlackTokenMinus() {
    GameBoardActions.changeTokens(4, 1);
  }

  public ActionType getSelectedAction() {
    return selectedAction;
  }

  public void setSelectedAction(ActionType type) {
    selectedAction = type;
  }

  public SelectionStatus getSelectionStatus() {
    return selectionStatus;
  }

  public void setSelectionStatus(SelectionStatus status) {
    selectionStatus = status;
    GameInterfaceHints.signalChange();
  }

  public void onDeck(int deckColor) {

    if (Game.getInstance().getCurrentPlayer() != Game.getInstance().myPlayerNumber) {
      return;
    }

    if (Game.getInstance().player1.getInventory().getReservedCards().size() == 3) {
      return;
    }

    GameBoardController.getInstance().deselectAll();
    PlayerSelfController.getInstance().resetExtra();
    GameBoardActions.resetSelectedTokens();
    GameBoardController.getInstance().reserveSelection(DeckReserveviews.get(deckColor).getId());

    if (SplendorUiApplication.aGame.goldTokens > 0) {

      GameBoardController.getInstance().select("viewTokenGold");

    }

    GameBoardController.getInstance().setSelectedAction(ActionType.RESERVE_FROM_DECK);
    PlayerSelfController.getInstance().confirmActionSetActive();


  }

  public void onDeckL3Click(javafx.event.ActionEvent event) {
    if (Game.getInstance().blueDeckSize > 0) {
      onDeck(0);
    }
  }

  public void onDeckRL3Click(javafx.event.ActionEvent event) {
    if (Game.getInstance().redL3DeckSize > 0) {
      onDeck(1);
    }
  }

  public void onDeckL2Click(javafx.event.ActionEvent event) {
    if (Game.getInstance().yellowDeckSize > 0) {
      onDeck(2);
    }
  }

  public void onDeckRL2Click(javafx.event.ActionEvent event) {
    if (Game.getInstance().redL2DeckSize > 0) {
      onDeck(3);
    }
  }

  public void onDeckL1Click(javafx.event.ActionEvent event) {
    if (Game.getInstance().greenDeckSize > 0) {
      onDeck(4);
    }
  }

  public void onDeckRL1Click(javafx.event.ActionEvent event) {
    if (Game.getInstance().redL1DeckSize > 0) {
      onDeck(5);
    }
  }

  /**
   * Sets the color of the cards that must be chosen when discarding cards to purchase a card of
   * type DISCARD_BONUSES.
   *
   * @param color the color of the bonuses that must be discarded
   */
  public void setDiscardColor(BonusColor color) {
    discardColor = color;
  }

  /**
   * Getter for the color of the cards that must be chosen when discarding cards to purchase a card
   * of type DISCARD_BONUSES.
   *
   * @return the color of the bonuses that must be discarded
   */
  public BonusColor getDiscardColor() {
    return  discardColor;
  }

}
