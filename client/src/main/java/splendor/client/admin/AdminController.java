package splendor.client.admin;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import splendor.client.Game;
import splendor.client.GameStart;
import splendor.client.GlobalUserInfo;
import splendor.client.PlayerOthersController;
import splendor.client.ScreenUpdater;
import splendor.client.SplendorUiApplication;

/**
 * JavaFX controller for the admin menu.
 */
public class AdminController implements Initializable {

  @FXML
  Label myUsername;

  @FXML
  TextField newUserName;
  @FXML
  PasswordField newUserPassword;
  @FXML
  ChoiceBox newUserRole;
  @FXML
  ColorPicker newUserColor;
  @FXML
  Button addUserButton;

  @FXML
  ScrollPane servers;

  @FXML
  ScrollPane users;

  private VBox serversVBox = new VBox();
  private VBox usersVBox = new VBox();

  private List<GameService> gameServiceList;
  private User[] userList;

  private static AdminController instance;


  /**
   * Update the list of registered game servers shown on the admin menu.
   *
   * @param gameServices the list of registered game services obtained from the Lobby Service.
   */
  public void updateServers(List<GameService> gameServices) {
    serversVBox.getChildren().clear();
    for (GameService server : gameServices) {
      ServerPane serverPane = new ServerPane(server);
      serversVBox.getChildren().add(serverPane);

      serverPane.prefWidthProperty()
          .bind(((VBox) serverPane.getParent()).widthProperty());
    }

    servers.setContent(serversVBox);
    serversVBox.prefWidthProperty().bind(servers.widthProperty());
  }

  /**
   * Update the list of registered users shown on the admin menu.
   *
   * @param userList the list of registered users obtained from the Lobby Service.
   */
  public void updateUsers(User[] userList) {
    usersVBox.getChildren().clear();
    for (User user : userList) {
      UserPane userPane = new UserPane(user);
      usersVBox.getChildren().add(userPane);

      userPane.prefWidthProperty()
          .bind(((VBox) userPane.getParent()).widthProperty());
    }

    users.setContent(usersVBox);
    usersVBox.prefWidthProperty().bind(users.widthProperty());
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    instance = this;
    myUsername.setText(GlobalUserInfo.getUsername());
    servers.setStyle("-fx-background-color:transparent;");
    users.setStyle("-fx-background-color:transparent;");
    newUserRole.getItems().add("Player");
    newUserRole.getItems().add("Admin");
    newUserRole.getItems().add("Service");
    newUserRole.setStyle("-fx-font-size: 18px");
    newUserRole.setValue("Player");
    updateAdminMenu();
  }

  /**
   * Update game servers and users.
   */
  public void updateAdminMenu() {
    try {
      gameServiceList = AdminRequest.getServices();
      updateServers(gameServiceList);

    } catch (UnirestException e) {
      System.out.println("Could not display game service list");
    }

    try {
      userList = AdminRequest.getUsers();
      updateUsers(userList);

    } catch (UnirestException e) {
      System.out.println("Could not display user list");
    }
  }

  /**
   * Get a reference to the unique AdminController instance.
   *
   * @return AdminController instance
   */
  public static AdminController getInstance() {
    return instance;
  }

  /**
   * Creates a new user with the parameters specified by the client by sending the corresponding
   * request to the Lobby Service. Updates the screen with the new changes.
   */
  public void addUser() {
    String name = newUserName.getText();
    String password = newUserPassword.getText();
    String roleStr = (String) newUserRole.getValue();
    String role = "ROLE_".concat(roleStr.toUpperCase());
    String colour = newUserColor.getValue().toString().substring(2,8).toUpperCase();

    AdminRequest.createUser(name, password, colour, role);
    updateAdminMenu();
  }

  /**
   * Method triggered when the lobby button is clicked form the admin menu. Switches the screen to
   * the lobby screen that shows the sessions and saved games.
   *
   * @throws IOException if something goes wrong when reading the fxml file
   */
  public void switchToLobbyMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplendorUiApplication.class.getResource("lobby.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);
  }

  /**
   * Method triggered when the logout button is clicked form the admin menu. Switches the screen to
   * the log in screen that is presented to the used when starting the client.
   *
   * @throws IOException if something goes wrong when reading the fxml file
   */
  public void switchToLogInScreen(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplendorUiApplication.class.getResource("log-in-screen.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);
    GlobalUserInfo.clearInfo();
  }

}
