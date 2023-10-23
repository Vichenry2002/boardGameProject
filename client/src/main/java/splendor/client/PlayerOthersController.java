package splendor.client;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class PlayerOthersController implements Initializable {

  @FXML
  private Label player2NameLabel;
  @FXML
  private Label player2PointsLabel;

  @FXML
  private Label player3NameLabel;
  @FXML
  private Label player3PointsLabel;

  @FXML
  private Label player4NameLabel;
  @FXML
  private Label player4PointsLabel;

  @FXML
  private Label player2WhiteTokenAmount;
  @FXML
  private Label player2BlueTokenAmount;
  @FXML
  private Label player2GreenTokenAmount;
  @FXML
  private Label player2RedTokenAmount;
  @FXML
  private Label player2BlackTokenAmount;
  @FXML
  private Label player2GoldTokenAmount;

  @FXML
  private Label player3WhiteTokenAmount;
  @FXML
  private Label player3BlueTokenAmount;
  @FXML
  private Label player3GreenTokenAmount;
  @FXML
  private Label player3RedTokenAmount;
  @FXML
  private Label player3BlackTokenAmount;
  @FXML
  private Label player3GoldTokenAmount;

  @FXML
  private Label player4WhiteTokenAmount;
  @FXML
  private Label player4BlueTokenAmount;
  @FXML
  private Label player4GreenTokenAmount;
  @FXML
  private Label player4RedTokenAmount;
  @FXML
  private Label player4BlackTokenAmount;
  @FXML
  private Label player4GoldTokenAmount;

  @FXML
  private Label player2WhiteBonusAmount;
  @FXML
  private Label player2BlueBonusAmount;
  @FXML
  private Label player2GreenBonusAmount;
  @FXML
  private Label player2RedBonusAmount;
  @FXML
  private Label player2BlackBonusAmount;

  @FXML
  private Label player3WhiteBonusAmount;
  @FXML
  private Label player3BlueBonusAmount;
  @FXML
  private Label player3GreenBonusAmount;
  @FXML
  private Label player3RedBonusAmount;
  @FXML
  private Label player3BlackBonusAmount;

  @FXML
  private Label player4WhiteBonusAmount;
  @FXML
  private Label player4BlueBonusAmount;
  @FXML
  private Label player4GreenBonusAmount;
  @FXML
  private Label player4RedBonusAmount;
  @FXML
  private Label player4BlackBonusAmount;


  @FXML
  private ImageView p2Reserved1;
  @FXML
  private ImageView p2Reserved2;
  @FXML
  private ImageView p2Reserved3;
  @FXML
  private ImageView p3Reserved1;
  @FXML
  private ImageView p3Reserved2;
  @FXML
  private ImageView p3Reserved3;
  @FXML
  private ImageView p4Reserved1;
  @FXML
  private ImageView p4Reserved2;
  @FXML
  private ImageView p4Reserved3;

  @FXML
  private ImageView p2OwnedNoble1;
  @FXML
  private ImageView p2OwnedNoble2;
  @FXML
  private ImageView p2OwnedNoble3;
  @FXML
  private ImageView p3OwnedNoble1;
  @FXML
  private ImageView p3OwnedNoble2;
  @FXML
  private ImageView p3OwnedNoble3;
  @FXML
  private ImageView p4OwnedNoble1;
  @FXML
  private ImageView p4OwnedNoble2;
  @FXML
  private ImageView p4OwnedNoble3;


  private final ObjectProperty<Image> imageP2Reserved1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP2Reserved2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP2Reserved3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP3Reserved1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP3Reserved2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP3Reserved3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP4Reserved1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP4Reserved2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP4Reserved3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP2OwnedNoble1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP2OwnedNoble2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP2OwnedNoble3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP3OwnedNoble1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP3OwnedNoble2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP3OwnedNoble3 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP4OwnedNoble1 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP4OwnedNoble2 = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> imageP4OwnedNoble3 = new SimpleObjectProperty<>();


  private static Map<Integer, Label> tokenLabelsMap = new HashMap<>();

  private static Map<Integer, Label> bonusLabelsMap = new HashMap<>();

  private static PlayerOthersController instance;

  public static PlayerOthersController getInstance() {
    return PlayerOthersController.instance;
  }

  public static void clearInstance() {
    instance = null;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    //put token labels in map
    tokenLabelsMap.put(20, player2WhiteTokenAmount);
    tokenLabelsMap.put(21, player2BlueTokenAmount);
    tokenLabelsMap.put(22, player2GreenTokenAmount);
    tokenLabelsMap.put(23, player2RedTokenAmount);
    tokenLabelsMap.put(24, player2BlackTokenAmount);
    tokenLabelsMap.put(25, player2GoldTokenAmount);
    tokenLabelsMap.put(30, player3WhiteTokenAmount);
    tokenLabelsMap.put(31, player3BlueTokenAmount);
    tokenLabelsMap.put(32, player3GreenTokenAmount);
    tokenLabelsMap.put(33, player3RedTokenAmount);
    tokenLabelsMap.put(34, player3BlackTokenAmount);
    tokenLabelsMap.put(35, player3GoldTokenAmount);
    tokenLabelsMap.put(40, player4WhiteTokenAmount);
    tokenLabelsMap.put(41, player4BlueTokenAmount);
    tokenLabelsMap.put(42, player4GreenTokenAmount);
    tokenLabelsMap.put(43, player4RedTokenAmount);
    tokenLabelsMap.put(44, player4BlackTokenAmount);
    tokenLabelsMap.put(45, player4GoldTokenAmount);

    //put bonus labels in map
    bonusLabelsMap.put(20, player2WhiteBonusAmount);
    bonusLabelsMap.put(21, player2BlueBonusAmount);
    bonusLabelsMap.put(22, player2GreenBonusAmount);
    bonusLabelsMap.put(23, player2RedBonusAmount);
    bonusLabelsMap.put(24, player2BlackBonusAmount);
    bonusLabelsMap.put(30, player3WhiteBonusAmount);
    bonusLabelsMap.put(31, player3BlueBonusAmount);
    bonusLabelsMap.put(32, player3GreenBonusAmount);
    bonusLabelsMap.put(33, player3RedBonusAmount);
    bonusLabelsMap.put(34, player3BlackBonusAmount);
    bonusLabelsMap.put(40, player4WhiteBonusAmount);
    bonusLabelsMap.put(41, player4BlueBonusAmount);
    bonusLabelsMap.put(42, player4GreenBonusAmount);
    bonusLabelsMap.put(43, player4RedBonusAmount);
    bonusLabelsMap.put(44, player4BlackBonusAmount);

    PlayerOthersController.instance = this;

  }

  public void setPoints(int player, int points) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        if (player == 2) {
          player2PointsLabel.setText(String.valueOf(points));
        }
        if (player == 3) {
          player3PointsLabel.setText(String.valueOf(points));
        }
        if (player == 4) {
          player4PointsLabel.setText(String.valueOf(points));
        }

      }
    });

  }

  public void setTokens(int color, int player, int amount) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        tokenLabelsMap.get(player * 10 + color).setText(String.valueOf(amount));

      }
    });

  }

  public void setBonus(int color, int player, int amount) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        bonusLabelsMap.get(player * 10 + color).setText(String.valueOf(amount));

      }
    });

  }

  public void setColor(int localNumber, String color) {
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        if (localNumber == 2) {
          player2NameLabel.setTextFill(Color.web(color));
        }
        if (localNumber == 3) {
          player3NameLabel.setTextFill(Color.web(color));
        }
        if (localNumber == 4) {
          player4NameLabel.setTextFill(Color.web(color));
        }
      }
    });

  }

  public void setName(int localNumber, String name) {
    System.out.println("SetName called with localnumber = " + localNumber + " and name = " + name);
    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        if (localNumber == 2) {
          System.out.println("setting p2 name: " + name);
          player2NameLabel.setText(name);
        }
        if (localNumber == 3) {
          player3NameLabel.setText(name);
        }
        if (localNumber == 4) {
          player4NameLabel.setText(name);
        }

      }
    });

  }













  public final Image getImageP2Reserved1() {
    return imageP2Reserved1Property().get();
  }

  public final void setImageP2Reserved1(String paramImagePath) {
    imageP2Reserved1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP2Reserved1Property() {
    return imageP2Reserved1;
  }



  public final Image getImageP2Reserved2() {
    return imageP2Reserved2Property().get();
  }

  public final void setImageP2Reserved2(String paramImagePath) {
    imageP2Reserved2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP2Reserved2Property() {
    return imageP2Reserved2;
  }




  public final Image getImageP2Reserved3() {
    return imageP2Reserved3Property().get();
  }

  public final void setImageP2Reserved3(String paramImagePath) {
    imageP2Reserved3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP2Reserved3Property() {
    return imageP2Reserved3;
  }




  public final Image getImageP3Reserved1() {
    return imageP3Reserved1Property().get();
  }

  public final void setImageP3Reserved1(String paramImagePath) {
    imageP3Reserved1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP3Reserved1Property() {
    return imageP3Reserved1;
  }




  public final Image getImageP3Reserved2() {
    return imageP3Reserved2Property().get();
  }

  public final void setImageP3Reserved2(String paramImagePath) {
    imageP3Reserved2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP3Reserved2Property() {
    return imageP3Reserved2;
  }




  public final Image getImageP3Reserved3() {
    return imageP3Reserved3Property().get();
  }

  public final void setImageP3Reserved3(String paramImagePath) {
    imageP3Reserved3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP3Reserved3Property() {
    return imageP3Reserved3;
  }




  public final Image getImageP4Reserved1() {
    return imageP4Reserved1Property().get();
  }

  public final void setImageP4Reserved1(String paramImagePath) {
    imageP4Reserved1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP4Reserved1Property() {
    return imageP4Reserved1;
  }




  public final Image getImageP4Reserved2() {
    return imageP4Reserved2Property().get();
  }

  public final void setImageP4Reserved2(String paramImagePath) {
    imageP4Reserved2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP4Reserved2Property() {
    return imageP4Reserved2;
  }




  public final Image getImageP4Reserved3() {
    return imageP4Reserved3Property().get();
  }

  public final void setImageP4Reserved3(String paramImagePath) {
    imageP4Reserved3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP4Reserved3Property() {
    return imageP4Reserved3;
  }


  public void highlight(int player) {
    if (player == 1) {
      NameHighlighter.removeHighlight(player2NameLabel);
      NameHighlighter.removeHighlight(player3NameLabel);
      NameHighlighter.removeHighlight(player4NameLabel);
    }
    else if (player == 2) {
      NameHighlighter.addHighlight(player2NameLabel);
      NameHighlighter.removeHighlight(player3NameLabel);
      NameHighlighter.removeHighlight(player4NameLabel);
    }
    else if (player == 3) {
      NameHighlighter.removeHighlight(player2NameLabel);
      NameHighlighter.addHighlight(player3NameLabel);
      NameHighlighter.removeHighlight(player4NameLabel);
    }
    else {
      NameHighlighter.removeHighlight(player2NameLabel);
      NameHighlighter.removeHighlight(player3NameLabel);
      NameHighlighter.addHighlight(player4NameLabel);
    }
  }


  public final Image getImageP2OwnedNoble1() {
    return imageP2OwnedNoble1Property().get();
  }

  public final void setImageP2OwnedNoble1(String paramImagePath) {
    imageP2OwnedNoble1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP2OwnedNoble1Property() {
    return imageP2OwnedNoble1;
  }



  public final Image getImageP2OwnedNoble2() {
    return imageP2OwnedNoble2Property().get();
  }

  public final void setImageP2OwnedNoble2(String paramImagePath) {
    imageP2OwnedNoble2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP2OwnedNoble2Property() {
    return imageP2OwnedNoble2;
  }




  public final Image getImageP2OwnedNoble3() {
    return imageP2OwnedNoble3Property().get();
  }

  public final void setImageP2OwnedNoble3(String paramImagePath) {
    imageP2OwnedNoble3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP2OwnedNoble3Property() {
    return imageP2OwnedNoble3;
  }




  public final Image getImageP3OwnedNoble1() {
    return imageP3OwnedNoble1Property().get();
  }

  public final void setImageP3OwnedNoble1(String paramImagePath) {
    imageP3OwnedNoble1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP3OwnedNoble1Property() {
    return imageP3OwnedNoble1;
  }




  public final Image getImageP3OwnedNoble2() {
    return imageP3OwnedNoble2Property().get();
  }

  public final void setImageP3OwnedNoble2(String paramImagePath) {
    imageP3OwnedNoble2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP3OwnedNoble2Property() {
    return imageP3OwnedNoble2;
  }




  public final Image getImageP3OwnedNoble3() {
    return imageP3OwnedNoble3Property().get();
  }

  public final void setImageP3OwnedNoble3(String paramImagePath) {
    imageP3OwnedNoble3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP3OwnedNoble3Property() {
    return imageP3OwnedNoble3;
  }




  public final Image getImageP4OwnedNoble1() {
    return imageP4OwnedNoble1Property().get();
  }

  public final void setImageP4OwnedNoble1(String paramImagePath) {
    imageP4OwnedNoble1Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP4OwnedNoble1Property() {
    return imageP4OwnedNoble1;
  }




  public final Image getImageP4OwnedNoble2() {
    return imageP4OwnedNoble2Property().get();
  }

  public final void setImageP4OwnedNoble2(String paramImagePath) {
    imageP4OwnedNoble2Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP4OwnedNoble2Property() {
    return imageP4OwnedNoble2;
  }




  public final Image getImageP4OwnedNoble3() {
    return imageP4OwnedNoble3Property().get();
  }

  public final void setImageP4OwnedNoble3(String paramImagePath) {
    imageP4OwnedNoble3Property().set(new Image(this.getClass().getResourceAsStream(paramImagePath)));
  }

  public ObjectProperty<Image> imageP4OwnedNoble3Property() {
    return imageP4OwnedNoble3;
  }








}
