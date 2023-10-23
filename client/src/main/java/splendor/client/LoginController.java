package splendor.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX controller for the log in screen.
 */
public class LoginController {

  private Stage stage;
  private Scene scene;
  private FXMLLoader fxmlLoader;

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label errorLabel;

  /**
   * Method triggered when the login button is clicked from the login menu. Switches the screen to
   * the lobby screen that shows the sessions and saved games.
   *
   * @throws IOException if something goes wrong when reading the fxml file
   */
  public void switchToLobbyMenu() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("lobby.fxml"));
    stage = (Stage) usernameField.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);
  }

  /**
   * Method triggered when the login button is clicked from the login menu. Sends a request to the
   * Lobby Service with the currently entered username and password. If the login information is
   * wrong, displays an error. If the login information matches a currently registered user, sets
   * the user's fields in the GlobalUserInfo class and then switches to the Lobby screen.
   */
  public void clickLogIn() {

    String username = usernameField.getText();
    String password = passwordField.getText();

    try {
      String token = Login.getUserAccessToken(username, password);

      if (token.equals("error")) {
        errorLabel.setVisible(true);

      } else {
        String role = Login.getRoleFromToken(token);
        GlobalUserInfo.setRole(role);
        GlobalUserInfo.setToken(token);
        GlobalUserInfo.setUsername(username);
        GlobalUserInfo.setPassword(password);
        switchToLobbyMenu();
      }

    } catch (UnirestException e) {

      errorLabel.setVisible(true);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    usernameField.setText("");
    passwordField.setText("");

  }

}
