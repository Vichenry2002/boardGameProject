package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for SplendorGameController class.
 */
public class TestSplendorGameController {

  @Test
  public void putGameIdTest_and_getGameStateLongTradingTest() {
    long gameid = 111;
    List<PlayerDetails> playerDetails = Arrays.asList(
        new PlayerDetails("p1", "1"),
        new PlayerDetails("p2", "2"),
        new PlayerDetails("p3", "3"),
        new PlayerDetails("p4", "4")
    );
    SessionDetails gameDetails = new SessionDetails("abc",
        playerDetails, "creator");

    SplendorGameController gameController = new SplendorGameController();
    gameController.putGameIdTrading(gameid, gameDetails);

    assertEquals(gameController.getGameStateLongOrient("133", "")
            .getResult().toString(),
        "<400 BAD_REQUEST Bad Request,There is no game with that id,[]>");
    assertNotEquals(gameController.getGameStateLongOrient("111", "")
            .getResult().toString(),
        "<400 BAD_REQUEST Bad Request,There is no game with that id,[]>");

  }

  @Test
  public void putGameIdTest_and_getGameStateLongCitiesTest() {
    long gameid = 111;
    List<PlayerDetails> playerDetails = Arrays.asList(
        new PlayerDetails("p1", "1"),
        new PlayerDetails("p2", "2"),
        new PlayerDetails("p3", "3"),
        new PlayerDetails("p4", "4")
    );
    SessionDetails gameDetails = new SessionDetails("abc",
        playerDetails, "creator");

    SplendorGameController gameController = new SplendorGameController();
    gameController.putGameIdTrading(gameid, gameDetails);

    assertEquals(gameController.getGameStateLongOrient("133", "")
            .getResult().toString(),
        "<400 BAD_REQUEST Bad Request,There is no game with that id,[]>");
    assertNotEquals(gameController.getGameStateLongOrient("111", "")
            .getResult().toString(),
        "<400 BAD_REQUEST Bad Request,There is no game with that id,[]>");

  }

  @Test
  public void getGameStateTest() {

  }

  @Test
  public void putGameIdTest_and_getGameStateLongOrientTest() {
    long gameid = 111;
    List<PlayerDetails> playerDetails = Arrays.asList(
        new PlayerDetails("p1", "1"),
        new PlayerDetails("p2", "2"),
        new PlayerDetails("p3", "3"),
        new PlayerDetails("p4", "4")
    );
    SessionDetails gameDetails = new SessionDetails("abc",
        playerDetails, "creator");

    SplendorGameController gameController = new SplendorGameController();
    gameController.putGameIdOrient(gameid, gameDetails);
    gameController.getGameState("12");

    assertEquals(gameController.getGameStateLongOrient("133", "")
        .getResult().toString(),
        "<400 BAD_REQUEST Bad Request,There is no game with that id,[]>");
    assertNotEquals(gameController.getGameStateLongOrient("111", "")
        .getResult().toString(),
        "<400 BAD_REQUEST Bad Request,There is no game with that id,[]>");

  }


  @Test
  public void registerGameServiceTest() throws UnirestException {
    try {
      SplendorGameController gameController = new SplendorGameController();

      gameController.registerGameServiceOrient();
    } catch (Exception e) {
      System.out.println("");
    }

  }






}
