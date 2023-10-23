package splendor.client;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class TradingPostShape extends SVGPath {

  public TradingPostShape(int x, int y, String color) {
    this.setLayoutY(y + 3);
    this.setLayoutX(x + 3);
    //getStyleClass().add("trading-post-shape");
    this.setContent("M 0 0 L 40 0 L 40 20 C 40 31 29 46 20 48 C 11 46 0 31 0 20 L 0 0");
    this.setFill(Color.web(color));

    //this.setStyle("-fx-background-color:" + color + "  ;");

  }

}
