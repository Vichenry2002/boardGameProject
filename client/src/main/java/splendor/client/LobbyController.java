package splendor.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaFX controller for the lobby screen.
 */
public class LobbyController implements Initializable {

  @FXML
  HBox game;
  private Stage stage;
  private Scene scene;
  private FXMLLoader fxmlLoader;

  @FXML
  Label myUsername;

  @FXML
  Label sessionName;
  @FXML
  Label sessionHost;
  @FXML
  Label sessionPlayers;
  @FXML
  Button launchButton;
  @FXML
  Button joinButton;

  @FXML
  ScrollPane sessions;
  @FXML
  ScrollPane savedgames;

  @FXML
  ChoiceBox gameServiceChoice;

  @FXML
  Button adminButton;

  private static HttpURLConnection connection;

  private static LobbyController instance;

  public static LobbyController getInstance() {
    return LobbyController.instance;
  }

  public void joinSession(ActionEvent event) throws IOException {

    String player = GlobalUserInfo.getUsername();
    String token = GlobalUserInfo.getToken();
    token = token.replace("+", "%2B");
    String gameId = String.valueOf(GlobalUserInfo.getSessionID());
    joinSessionRequest(gameId,player,token);
  }
  public void launchSession(ActionEvent event)
      throws IOException, InterruptedException, UnirestException {

    String gameId = String.valueOf(GlobalUserInfo.getSessionID());
    String token = Login.getUserAccessToken(GlobalUserInfo.getUsername(), "abc123_ABC123").replace("+", "%2B");

    launchSessionRequest(gameId,token);
  }

  /**
   * Switches to the log-in screen when the user clicks the "Log Out" button.
   *
   * @param event the event that triggered the method call
   * @throws IOException if something goes wrong when reading the fxml file
   */
  public void switchToLogInScreen(ActionEvent event) throws IOException {
    GameStart.sessionsPollingThread.interrupt();
    for (Thread t : GameStart.savesPollingThreads.values()) {
      t.interrupt();
    }

    fxmlLoader = new FXMLLoader(SplendorUiApplication.class.getResource("log-in-screen.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);
    GlobalUserInfo.clearInfo();
  }

  /**
   * Switches to the admin menu screen when the user clicks the "Admin" button.
   *
   * @param event the event that triggered the method call
   * @throws IOException if something goes wrong when reading the fxml file
   */
  public void switchToAdminScreen(ActionEvent event) throws IOException {
    GameStart.sessionsPollingThread.interrupt();
    for (Thread t : GameStart.savesPollingThreads.values()) {
      t.interrupt();
    }

    fxmlLoader = new FXMLLoader(SplendorUiApplication.class.getResource("admin.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);
  }

  /**
   * Switches to the game board screen when the user clicks the "Play" button.
   *
   * @throws IOException if something goes wrong when reading the fxml file
   */
  public void switchToGameBoard() throws IOException {

    GameStart.sessionsPollingThread.interrupt();
    for (Thread t : GameStart.savesPollingThreads.values()) {
      t.interrupt();
    }

    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        Parent root;
        try {
          root = FXMLLoader.load(getClass().getResource("game.fxml"));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        stage = (Stage) myUsername.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
      }
    });

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    instance = this;
    GameStart.refreshSessions();
    GameStart.refreshSavedGamesAll();

    myUsername.setText(GlobalUserInfo.getUsername());
    savedgames.setStyle("-fx-background-color:transparent;");
    sessions.setStyle("-fx-background-color:transparent;");

    gameServiceChoice.getItems().addAll(
        "splendor_orient",
        "splendor_trading",
        "splendor_cities"
    );
    gameServiceChoice.setStyle("-fx-font-size: 18px");

    try {
      gameServiceChoice.setValue("splendor_orient");
    } catch (Exception e) {
      System.out.println("Could not set choice");
    }

    // Enable the admin button only if the current user is an admin.
    String role = GlobalUserInfo.getRole();
    if (role.equals("ROLE_ADMIN")) {
      adminButton.setVisible(true);
    } else {
      adminButton.setVisible(false);
    }
  }

  /**
   * Creates a new game with hardcoded parameters
   *
   * @param event
   * @throws IOException
   * @throws InterruptedException
   */
  public void newGame(ActionEvent event) throws IOException, InterruptedException,
      UnirestException {
    //OAuth2Token oauth = createNewAccessToken("maex","abc123_ABC123");
    //String accessToken = oauth.access_token;

    String accessToken = Login.getUserAccessToken(
        GlobalUserInfo.getUsername(),
        GlobalUserInfo.getPassword());

    accessToken = accessToken.replace("+", "%2B");

    String gameService = (String) gameServiceChoice.getValue();
    System.out.println(gameService);

    NewSession newSession = new NewSession(
        GlobalUserInfo.getUsername(),
        gameService,
        "");

    String request = postSession(accessToken,newSession);

    //System.out.println(instance.sessionName);
    //System.out.println(instance.currentSessionId);

  }
  /**
   * TODO.
   *
   * @param sessionId TODO
   * @param player TODO
   * @param accessToken TODO
   */
  private static void joinSessionRequest(String sessionId, String player, String accessToken) {
    String urlStr = GlobalUserInfo.getLobbyServiceAddress() + "/api/sessions/" + sessionId + "/players/" + player + "?access_token=" + accessToken;

    try {
      URL url = new URL(urlStr);
      connection = (HttpURLConnection) url.openConnection();
      //Not sure if necessary to set request property
      connection.setRequestMethod("PUT");
      connection.setDoOutput(true);
      connection.getOutputStream();
      int status = connection.getResponseCode();

      System.out.println(status);
      connection.connect();
    } catch (MalformedURLException | ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * TODO Description.
   *
   * @param sessionId TODO
   * @param accessToken TODO
   * @throws IOException TODO
   * @throws InterruptedException TODO
   */
  private static void launchSessionRequest(String sessionId, String accessToken)
      throws IOException, InterruptedException {

    accessToken = accessToken.replace("+", "%2B");
    String urlStr = GlobalUserInfo.getLobbyServiceAddress() + "/api/sessions/" + sessionId + "?access_token=" + accessToken;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(GlobalUserInfo.getLobbyServiceAddress() + "/api/sessions/" + sessionId + "?access_token="+accessToken))
        .POST(HttpRequest.BodyPublishers.ofString(""))
        .build();

    System.out.println(request.uri());

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body().toString());
  }


  /**
   * Used to create an access token, refresh token, etc...
   *
   * NOTE : + SIGN IS OFTEN REPLACED BY SPACE CHARACTER AT SOME POINT, NEED TO FIND WHERE ARE FIX IT
   * token = token.replace("+","%2B"); will do the trick
   *
   * @param username
   * @param password
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  private static OAuth2Token createNewAccessToken(String username, String password) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GlobalUserInfo.getLobbyServiceAddress() + "/oauth/token?grant_type=password&username="
                    +username+"&password="+password))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .header("authorization","Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=")
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();

    OAuth2Token accessToken = gson.fromJson(response.body(),OAuth2Token.class);

    return accessToken;
  }

  /**
   * This method sends a GET Request to the LobbyService to receive a list of ongoing sessions
   *
   * @param accessToken
   * @return SessionMap
   * @throws IOException
   * @throws InterruptedException
   */
  private static SessionList getSessions(String accessToken) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GlobalUserInfo.getLobbyServiceAddress() + "/api/sessions?access_token=" + accessToken))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();

    SessionList sessions = gson.fromJson(response.body(),SessionList.class);

    return sessions;
  }

  /**
   * This method sends a POST Request to the LobbyService to create a new session
   *
   * @param accessToken
   * @param newSession
   * @return String containing the ID of the session
   * @throws IOException
   * @throws InterruptedException
   */
  protected static String postSession(String accessToken, NewSession newSession) throws IOException, InterruptedException {
    Gson gson = new Gson();
    String body = gson.toJson(newSession);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GlobalUserInfo.getLobbyServiceAddress() + "/api/sessions?access_token="+accessToken))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type","application/json")
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body().toString());

    return response.body();
  }
}