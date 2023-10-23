package splendor.client;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class EndGameController implements Initializable {
 private static EndGameController instance;
 @FXML
 private Label label;

 public void setLabel(String s){
  Platform.runLater(new Runnable() {
   @Override
   public void run(){
    label.setText(s);
   }
  });
 }


 public static EndGameController getInstance() {
  return instance;
 }

 @FXML
 protected void onQuitClick(ActionEvent event) {
   try {
    PlayerSelfController.getInstance().switchToLobbyMenu();
   } catch (IOException e) {
    throw new RuntimeException("Failed to switch to Lobby");
   }
 }

 @Override
 public void initialize(URL url, ResourceBundle resourceBundle) {
  EndGameController.instance = this;
 }
}
