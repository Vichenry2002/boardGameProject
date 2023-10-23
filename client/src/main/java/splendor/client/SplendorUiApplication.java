package splendor.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application class.
 */
public class SplendorUiApplication extends Application {

  public static Game aGame;
  public static String aUsername;

  /**
   * Main method of the program. Launches the JavaFX application. If an ip address is provided as
   * an argument, it will be used as the server address.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // if an ip address is provided as an argument, use it
    if (args.length == 1) {
      String ipAddress = args[0];
        if (isValidIPAddress(ipAddress)) {
            GlobalUserInfo.updateIpAddress(ipAddress);
        }
    }

    // launch the application
    launch();
  }

  private static boolean isValidIPAddress(String ipAddress) {
    try {
      InetAddress inetAddress = InetAddress.getByName(ipAddress);
      return inetAddress.getHostAddress().equals(ipAddress);
    } catch (UnknownHostException e) {
      return false;
    }
  }

  /**
   * Starts the JavaFX application.
   *
   * @param stage the stage to start the application on
   * @throws IOException if the fxml file cannot be found
   */
  @Override
  public void start(Stage stage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("log-in-screen.fxml"));
    Scene scene = new Scene(root);
    stage.setTitle("Splendor");
    stage.setScene(scene);
    stage.show();
    stage.setFullScreen(true);

  }
}