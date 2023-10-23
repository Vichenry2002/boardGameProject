package splendor.client;

import javafx.scene.Node;

public class NameHighlighter {

  public static void addHighlight(Node node) {
    node.setStyle("-fx-effect: dropshadow(gaussian, green, 10, 0.8, 0 ,0);"
        + "-fx-background-color: white;" + "-fx-border-width: 2;" + "-fx-border-color: black;" );
  }

  public static void removeHighlight(Node node) {
    node.setStyle("-fx-background-color: white;" + "-fx-border-width: 2;"
        + "-fx-border-color: black;" );
  }

}
