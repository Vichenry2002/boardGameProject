package splendor.client;

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

/**
 * Custom JavaFX class to create a Pane that contains information about a given saved game.
 * Contains 3 labels which display information about the given session and 1 button that sends
 * specific HTTP requests to the Lobby Service.
 */
public class SaveGamePane extends Pane {

  @FXML
  Label game;
  @FXML
  Label savegameid;
  @FXML
  Label players;

  @FXML
  Button host;

  SaveGame save;

  /**
   * Constructor for SaveGamePane class.
   *
   * @param save saved game obtained from the Lobby Service from which to create the SaveGamePane.
   */
  public SaveGamePane(SaveGame save) {

    this.save = save;

    this.setPrefHeight(50);

    Border border = new Border(new BorderStroke(
        javafx.scene.paint.Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT
    ));

    this.setBorder(border);

    Font fontLabels = new Font(18);
    Font fontButtons = new Font(18);

    // Add game label
    String gameName = save.getGamename();
    game = new Label(gameName);
    game.setFont(fontLabels);
    game.relocate(20,10);

    // Add savegameid label
    String id = save.getSavegameid();
    savegameid = new Label(id);
    savegameid.setFont(fontLabels);
    savegameid.relocate(220,10);

    // Add players label
    List<String> playerList = save.getPlayers();
    String playerNames = "";
    for (String p : playerList) {
      if (playerNames.equals("")) {
        playerNames = p;
      } else {
        playerNames = playerNames.concat(", " + p);
      }
    }
    players = new Label(playerNames);
    players.setFont(fontLabels);
    players.relocate(420,10);

    // Add Host button
    host = new Button("Host");
    host.setFont(fontButtons);
    host.layoutXProperty().bind(this.widthProperty().subtract(90));
    host.setLayoutY(7);

    host.setOnAction(hostEvent);

    getChildren().addAll(game, savegameid, players, host);

  }

  // Event handler for the Host button
  EventHandler<ActionEvent> hostEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      String accessToken = null;
      try {
        accessToken = Login.getUserAccessToken(
            GlobalUserInfo.getUsername(),
            GlobalUserInfo.getPassword());
      } catch (UnirestException ex) {
        throw new RuntimeException(ex);
      }

      accessToken = accessToken.replace("+", "%2B");

      String gameService = save.gamename;
      String saveGame = save.savegameid;

      NewSession newSession = new NewSession(
          GlobalUserInfo.getUsername(),
          gameService,
          saveGame);

      try {
        String request = LobbyController.getInstance().postSession(accessToken,newSession);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }

    }
  };







}
