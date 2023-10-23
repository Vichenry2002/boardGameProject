package splendor.client;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

/**
 * This class contains 2 static method. One of them is used to update the active sessions shown in
 * the lobby screen and the other is used to update the list of saved games.
 */
public class LobbyUpdater {
  private static SessionList oldSessionList = new SessionList();
  public static long sessionID;

  private static VBox sessionsVBox = new VBox();
  private static VBox savesVBox = new VBox();

  /**
   * Method to update the active sessions shown in the lobby screen.
   *
   * @param newSessionList the list of sessions.
   */
  public static void updateLobbySessions(SessionList newSessionList) {

    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        sessionsVBox.getChildren().clear();
        for (Long key : newSessionList.sessions.keySet()) {
          String id = String.valueOf(key);
          Session session = newSessionList.sessions.get(key);
          SessionPane newSessionPane = new SessionPane(id, session);
          sessionsVBox.getChildren().add(newSessionPane);

          newSessionPane.prefWidthProperty()
              .bind(((VBox) newSessionPane.getParent()).widthProperty());
        }

        LobbyController.getInstance().sessions.setContent(sessionsVBox);
        sessionsVBox.prefWidthProperty()
            .bind(LobbyController.getInstance().sessions.widthProperty().subtract(2));

      }
    });

  }

  /**
   * Method to update the saved games shown in the lobby screen.
   *
   * @param newSaves the list of saved games.
   */
  public static void updateLobbySavedGames(SavedGamesList newSaves) {

    Platform.runLater(new Runnable() {
      @Override
      public void run(){
        savesVBox.getChildren().clear();
        for (SaveGame save : newSaves.savegames.values()) {
          SaveGamePane newSavePane = new SaveGamePane(save);
          savesVBox.getChildren().add(newSavePane);

          newSavePane.prefWidthProperty()
              .bind(((VBox) newSavePane.getParent()).widthProperty());
        }

        LobbyController.getInstance().savedgames.setContent(savesVBox);
        savesVBox.prefWidthProperty()
            .bind(LobbyController.getInstance().savedgames.widthProperty().subtract(2));

      }
    });

  }

}
