package splendor.client.admin;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import splendor.client.GlobalUserInfo;
import splendor.client.LobbyController;
import splendor.client.Login;
import splendor.client.NewSession;
import splendor.client.SaveGame;

/**
 * Custom JavaFX class to create a Pane that contains information about a given registered game
 * server.
 * Contains 4 labels which display information about the given game server and 1 button that sends
 * specific HTTP requests to the Lobby Service to unregister the game server.
 */
public class ServerPane extends Pane {

  @FXML
  Label name;
  @FXML
  Label displayName;
  @FXML
  Label location;
  @FXML
  Label players;

  @FXML
  Button unregister;

  GameService server;

  /**
   * Constructor for ServerPane class.
   *
   * @param server registered game server obtained from the Lobby Service from which to create the
   *               ServerPane
   */
  public ServerPane(GameService server) {

    this.server = server;

    this.setPrefHeight(40);

    Border border = new Border(new BorderStroke(
        javafx.scene.paint.Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT
    ));

    //this.setBorder(border);

    Font fontLabels = new Font(14);
    Font fontButtons = new Font(14);

    // Add name label
    String gameName = server.getName();
    name = new Label(gameName);
    name.setFont(fontLabels);
    name.relocate(0,5);

    // Add display name label
    String display = server.getDisplayName();
    displayName = new Label(display);
    displayName.setFont(fontLabels);
    displayName.relocate(150,5);

    // Add location label
    String locationStr = server.getLocation();
    location = new Label(locationStr);
    location.setFont(fontLabels);
    location.relocate(300,5);

    // Add players label
    String minPlayers = String.valueOf(server.getMinSessionPlayers());
    String maxPlayers = String.valueOf(server.getMaxSessionPlayers());
    String playersStr = "[" + minPlayers + " - " + maxPlayers + "]";
    players = new Label(playersStr);
    players.setFont(fontLabels);
    players.relocate(630,5);

    // Add Unregister button
    unregister = new Button("Force unregister");
    unregister.setFont(fontButtons);
    unregister.relocate(700,0);

    unregister.setOnAction(unregisterEvent);

    getChildren().addAll(name, displayName, location, players, unregister);

  }

  // Event handler for the Host button
  EventHandler<ActionEvent> unregisterEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      // Send request to delete the specified game service
      AdminRequest.deleteService(name.getText());

      // Update the ui to reflect the new changes
      AdminController.getInstance().updateAdminMenu();
    }
  };
}
