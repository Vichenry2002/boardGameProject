package splendor.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameStart {

  public static Thread statePollingThread;
  public static Thread sessionsPollingThread;
  //public static Thread savesPollingThread;
  public static Map<String, Thread> savesPollingThreads = new HashMap<>();

  public static boolean stopPollingLobby = false;
  public static boolean stopPollingGame = false;


  public static void startGame(String gameid) throws IOException {


      statePollingThread = new Thread() {
        public void run() {
          try {

            while(PlayerOthersController.getInstance() == null
                || PlayerSelfController.getInstance() == null) {

              // TODO : use synchronized(lock) instead of sleep if it becomes an issue
              // Right now taking 0.1-1 second but it doesnt crash
              sleep(100);
              System.out.println("waiting for javafx thread");
            }

            System.out.println("Started game state thread");

            LongPolling.getNewGameState(GlobalUserInfo.getServerAddress()
                .concat(GlobalUserInfo.getGameService())
                .concat("/api/games/")
                .concat(gameid)
                .concat("/gamestate"));

            System.out.println("Ended game state thread");

          } catch (UnirestException | InterruptedException e) {

            System.out.println("Long polling thread error");
            throw new RuntimeException(e);

          }
        }
      };

      statePollingThread.start();

  }

    public static void refreshSessions() {
        sessionsPollingThread = new Thread() {
            public void run() {
                try {
                    System.out.println("Trying to poll the sessions");
                    LongPolling.getActiveSessions(GlobalUserInfo.getLobbyServiceAddress().concat("/api/sessions"));

                } catch (UnirestException | IOException e) {

                    System.out.println("Long polling thread error : LOBBY SESSIONS");

                }
            }
        };

        sessionsPollingThread.start();

    }


  public static void refreshSavedGames(String gameservice) {
    Thread savesPollingThread;
    savesPollingThread = new Thread() {
      public void run() {
        try {
          System.out.println("Trying to poll the saved games");
          LongPolling.getSavedGames(GlobalUserInfo.getLobbyServiceAddress()
              .concat("/api/gameservices/")
              .concat(gameservice)
              .concat("/savegames"));

        } catch (UnirestException | IOException e) {

          System.out.println(
              "Long polling thread error : LOBBY SAVED GAMES (UnirestException / IOException)");

        }
      }
    };
    savesPollingThreads.put(gameservice, savesPollingThread);

    savesPollingThread.start();

  }

  public static void refreshSavedGamesAll() {

    // TODO : get gameservices from lobby service later
    List<String> gameServices = Arrays.asList("splendor_orient",
        "splendor_trading", "splendor_cities");

    for (String gameService : gameServices) {
      refreshSavedGames(gameService);
    }

  }










}
