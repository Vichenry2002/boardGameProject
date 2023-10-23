package splendor.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Custom JavaFX class to create a Pane that contains information about a given session.
 * Contains 3 labels which display information about the given session and up to 2 of 4
 * buttons that send specific HTTP requests to the Lobby Service.
 */
public class SessionPane extends Pane {

  @FXML
  Label game;
  @FXML
  Label creator;
  @FXML
  Label players;

  @FXML
  Button launch;
  @FXML
  Button delete;
  @FXML
  Button join;
  @FXML
  Button leave;
  @FXML
  Button play;

  Session session;
  String id;

  private HttpURLConnection connection;

  /**
   * Constructor for SessionPane class.
   *
   * @param session session obtained from the Lobby Service from which to create the SessionPane.
   */
  public SessionPane(String key, Session session) {

    this.session = session;
    this.id = key;

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
    String gameName = session.gameParameters.name;
    game = new Label(gameName);
    game.setFont(fontLabels);
    game.relocate(20,10);

    // Add creator label
    String creatorName = session.creator;
    creatorName = creatorName.substring(0, 1).toUpperCase() + creatorName.substring(1);
    creator = new Label(creatorName);
    creator.setFont(fontLabels);
    creator.relocate(190,10);

    // Add players label
    String max = String.valueOf(session.gameParameters.maxSessionPlayers);
    String min = String.valueOf(session.gameParameters.minSessionPlayers);
    String current = String.valueOf(session.players.size());

    StringBuilder playerString;
    if (!min.equals(max)) {
      playerString = new StringBuilder("[" + current + "/" + min + "-" + max + "]: ");
    } else {
      playerString = new StringBuilder("[" + current + "/" + max + "]: ");
    }
    boolean first = true;
    for (String player : session.players) {
      player = player.substring(0, 1).toUpperCase() + player.substring(1);
      if (first) {
        playerString.append(player);
        first = false;
      } else {
        playerString.append(", ").append(player);
      }
    }

    players = new Label(playerString.toString());
    players.setFont(fontLabels);
    players.relocate(320,10);

    getChildren().addAll(game, creator, players);

    if (inSession() && launched()) {

      // Add Play button
      play = new Button("Play");
      play.setFont(fontButtons);
      play.layoutXProperty().bind(this.widthProperty().subtract(90));
      play.setLayoutY(7);

      play.setOnAction(playEvent);

      getChildren().addAll(play);

    } else if (isCreator()) {

      // Add Launch button
      launch = new Button("Launch");
      launch.setFont(fontButtons);
      launch.layoutXProperty().bind(this.widthProperty().subtract(90));
      launch.setLayoutY(7);

      // Add Delete button
      delete = new Button("Delete");
      delete.setFont(fontButtons);
      delete.layoutXProperty().bind(this.widthProperty().subtract(170));
      delete.setLayoutY(7);

      delete.setOnAction(deleteEvent);

      if (sessionReady()) {
        launch.setOnAction(launchEvent);
      }

      getChildren().addAll(launch, delete);

    } else if (inSession()) {

      // Add Leave button
      leave = new Button("Leave");
      leave.setFont(fontButtons);
      leave.layoutXProperty().bind(this.widthProperty().subtract(90));
      leave.setLayoutY(7);

      leave.setOnAction(leaveEvent);

      getChildren().addAll(leave);

    } else {

      // Add Join button
      join = new Button("Join");
      join.setFont(fontButtons);
      join.layoutXProperty().bind(this.widthProperty().subtract(90));
      join.setLayoutY(7);

      join.setOnAction(joinEvent);

      getChildren().addAll(join);

    }

  }

  /**
   * Checks if the player associated with this client is in the session associated with this
   * SessionPane instance.
   *
   * @return true if the player is in the session, false if not.
   */
  private boolean inSession() {
    for (String player : session.players) {
      if (player.equals(GlobalUserInfo.getUsername())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the player associated with this client is the creator of the session associated
   * with this SessionPane instance.
   *
   * @return true if the player is the creator of the session, false if not.
   */
  private boolean isCreator() {
    return session.creator.equals(GlobalUserInfo.getUsername());
  }


  /**
   * Checks if the session associated with this SessionPane instance has sufficient players to
   * start the game.
   *
   * @return true if session is ready to start, false if not.
   */
  private boolean sessionReady() {
    int min = session.gameParameters.minSessionPlayers;
    int current = session.players.size();
    return current >= min;
  }

  /**
   * Checks if the session associated with this SessionPane instance is full.
   *
   * @return true if session is full, false if not.
   */
  private boolean sessionFull() {
    int max = session.gameParameters.maxSessionPlayers;
    int current = session.players.size();
    return current == max;
  }

  /**
   * Checks if the session associated with this SessionPane instance is launched.
   *
   * @return true if session is launched, false if not.
   */
  private boolean launched() {
    return session.launched;
  }



  // Event handler for the Launch button
  EventHandler<ActionEvent> launchEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      String refreshToken = GlobalUserInfo.getRefreshToken();
      String accessToken = null;
      try {
        accessToken = Login.getUserAccessToken(
                GlobalUserInfo.getUsername(),
                GlobalUserInfo.getPassword())
            .replace("+", "%2B");
//        accessToken = Login.refreshUserAccessToken(refreshToken)
//            .replace("+", "%2B");
      } catch (UnirestException ex) {
        throw new RuntimeException(ex);
      }

      String urlStr = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/sessions/" + id
          + "?access_token=" + accessToken;

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(GlobalUserInfo.getLobbyServiceAddress()
              + "/api/sessions/" + id
              + "?access_token="+ accessToken))
          .POST(HttpRequest.BodyPublishers.ofString(""))
          .build();

      System.out.println(request.uri());

      HttpResponse<String> response = null;
      try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
      System.out.println(response.body().toString());

    }
  };

  // Event handler for the Delete button
  EventHandler<ActionEvent> deleteEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      String refreshToken = GlobalUserInfo.getRefreshToken();
      String accessToken = null;
      try {
        accessToken = Login.getUserAccessToken(
                GlobalUserInfo.getUsername(),
                GlobalUserInfo.getPassword())
            .replace("+", "%2B");
//        accessToken = Login.refreshUserAccessToken(refreshToken)
//            .replace("+", "%2B");
      } catch (UnirestException ex) {
        throw new RuntimeException(ex);
      }

      String urlStr = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/sessions/" + id
          + "?access_token=" + accessToken;

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(GlobalUserInfo.getLobbyServiceAddress()
              + "/api/sessions/" + id
              + "?access_token="+ accessToken))
          .DELETE()
          .build();

      System.out.println(request.uri());

      HttpResponse<String> response = null;
      try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
      System.out.println(response.body().toString());

    }
  };

  // Event handler for the Join button
  EventHandler<ActionEvent> joinEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      String refreshToken = GlobalUserInfo.getRefreshToken();
      String accessToken = null;
      try {
        accessToken = Login.getUserAccessToken(
            GlobalUserInfo.getUsername(),
            GlobalUserInfo.getPassword())
            .replace("+", "%2B");
//        accessToken = Login.refreshUserAccessToken(refreshToken)
//            .replace("+", "%2B");
      } catch (UnirestException ex) {
        throw new RuntimeException(ex);
      }

      String urlStr = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/sessions/" + id
          + "/players/" + GlobalUserInfo.getUsername()
          + "?access_token=" + accessToken;

      try {
        URL url = new URL(urlStr);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.getOutputStream();
        int status = connection.getResponseCode();

        System.out.println(status);
        connection.connect();
      } catch (MalformedURLException | ProtocolException exception) {
        exception.printStackTrace();
      } catch (IOException exception) {
        throw new RuntimeException(exception);
      }

    }
  };

  // Event handler for the Leave button
  EventHandler<ActionEvent> leaveEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      String refreshToken = GlobalUserInfo.getRefreshToken();
      String accessToken = null;
      try {
        accessToken = Login.getUserAccessToken(
                GlobalUserInfo.getUsername(),
                GlobalUserInfo.getPassword())
            .replace("+", "%2B");
//        accessToken = Login.refreshUserAccessToken(refreshToken)
//            .replace("+", "%2B");
      } catch (UnirestException ex) {
        throw new RuntimeException(ex);
      }

      String urlStr = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/sessions/" + id
          + "/players/" + GlobalUserInfo.getUsername()
          + "?access_token=" + accessToken;

      try {
        URL url = new URL(urlStr);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
        connection.getOutputStream();
        int status = connection.getResponseCode();

        System.out.println(status);
        connection.connect();
      } catch (MalformedURLException | ProtocolException exception) {
        exception.printStackTrace();
      } catch (IOException exception) {
        throw new RuntimeException(exception);
      }

    }
  };

  // Event handler for the Play button
  EventHandler<ActionEvent> playEvent = new EventHandler<>() {
    @Override
    public void handle(ActionEvent e) {

      System.out.println("SESSION LAUNCHED");
      try {
        GlobalUserInfo.setGameService(session.gameParameters.name);
        GlobalUserInfo.setSessionID(id);
        LobbyController.getInstance().switchToGameBoard();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }

    }
  };





}
