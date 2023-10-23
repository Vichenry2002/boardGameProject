package splendor.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * TODO add description.
 */
public class GameController implements Initializable {

  private static GameController instance;
  Scale scale = new Scale();
  @FXML
  private Pane mainBox;
  @FXML
  private Pane playerSelfPane;
  @FXML
  private Pane boardPane;
  @FXML
  private Pane playerOthersPane;
  @FXML
  private AnchorPane cardAnchorPane;
  @FXML
  private AnchorPane endAnchorPane;
  @FXML
  private AnchorPane reservedCardAnchorPane;

  public static GameController getInstance() {
    return GameController.instance;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    GameController.instance = this;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    scale.setX(width / 1920);
    scale.setY(height / 1080);

    mainBox.getTransforms().add(scale);
    //playerSelfPane.getTransforms().add(scale1);
    //boardPane.getTransforms().add(scale1);
    //playerOthersPane.getTransforms().add(scale1);

  }

  public void setCardVisible(boolean visible) {
    cardAnchorPane.setVisible(visible);
  }
  public void setReservedCardVisible(boolean visible){reservedCardAnchorPane.setVisible(visible);}
  public void setEndGameVisible(boolean visible) {
    endAnchorPane.setVisible(visible);
  }


}
