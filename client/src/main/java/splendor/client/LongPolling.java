package splendor.client;


import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * TODO.
 */
public class LongPolling {

  private static SavedGamesList currentSavedGames = new SavedGamesList();

  /**
   * TODO.
   *
   * @param location ip address of server
   * @throws UnirestException TODO
   */
  public static void getNewGameState(String location) throws UnirestException,
      InterruptedException {

    String previousState = DigestUtils.md5Hex("");
    int status = 200;

    while (status != 500) {

      HttpResponse<String> response;
      response = Unirest.get(location).queryString("hash", previousState).asString();
      status = response.getStatus();

      //Thread.sleep(10);

      //System.out.println(location);

      if (status == 200) {
        String responseString = response.getBody();
        previousState = DigestUtils.md5Hex(responseString);
        GameState newGameState = new Gson().fromJson(responseString, GameState.class);

        System.out.println("Got new game state. Updating.");

        ScreenUpdater.updateScreen(newGameState);
      }

    }

  }

  public static void getActiveSessions(String location) throws UnirestException, IOException {

    String previousSessions = DigestUtils.md5Hex("");
    int status = 200;

    while (status != 500 && !Thread.currentThread().isInterrupted()) {

      HttpResponse<String> response;
      response = Unirest.get(location).queryString("hash", previousSessions).asString();
      status = response.getStatus();

      if (status == 200) {

        String responseString = response.getBody();
        previousSessions = DigestUtils.md5Hex(responseString);

        System.out.println(responseString);
        SessionList newSessionList = new Gson().fromJson(responseString, SessionList.class);

        LobbyUpdater.updateLobbySessions(newSessionList);
      }

    }


  }


//  public static void getSavedGames(String location) throws UnirestException, IOException {
//
//    String previousSaves = DigestUtils.md5Hex("");
//    int status = 200;
//    System.out.println("saves: a");
//    while (status != 500) {
//      System.out.println("saves: b");
//      HttpResponse<String> response;
//      response = Unirest.get(location).queryString("hash", previousSaves).asString();
//      status = response.getStatus();
//      System.out.println("saves: c");
//      System.out.println(status);
//      if (status == 200) {
//        System.out.println("saves: d");
//        String responseString = response.getBody();
//        previousSaves = DigestUtils.md5Hex(responseString);
//        System.out.println("saves: e");
//        System.out.println(responseString);
//        SavedGamesList newSavedGames = new Gson().fromJson(responseString, SavedGamesList.class);
//        System.out.println("saves: f");
//
//        currentSavedGames.updateList(newSavedGames);
//
//        LobbyUpdater.updateLobbySavedGames(currentSavedGames);
//      }
//
//    }
//
//
//  }

  public static void getSavedGames(String location)
      throws UnirestException, IOException {

    //String previousSaves = DigestUtils.md5Hex("");
    int status = 200;

    while (status != 500 && !Thread.currentThread().isInterrupted()) {
      HttpResponse<String> response;

      String refreshToken = GlobalUserInfo.getRefreshToken();
      String accessToken = null;
      try {
        accessToken = Login.getUserAccessToken(
            GlobalUserInfo.getUsername(),
                GlobalUserInfo.getPassword());
            //.replace("+", "%2B");
      } catch (UnirestException ex) {
        throw new RuntimeException(ex);
      }

      response = Unirest.get(location).queryString("access_token", accessToken).asString();
      status = response.getStatus();

      if (status == 200) {
        String responseString = response.getBody();
        //previousSaves = DigestUtils.md5Hex(responseString);
        System.out.println(responseString);
        SaveGame[] newSavedGames = new Gson().fromJson(responseString, SaveGame[].class);
        //SavedGamesList newSavedGames = new Gson().fromJson(responseString, SavedGamesList.class);

        currentSavedGames.updateList(newSavedGames);

        LobbyUpdater.updateLobbySavedGames(currentSavedGames);
      }


      try {
        Thread.sleep(20000);
      } catch (InterruptedException e) {
        System.out.println("Stopping save games thread");
        Thread.currentThread().interrupt();
      }
    }
  }

}
