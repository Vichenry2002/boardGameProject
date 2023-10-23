package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;

import ca.mcgill.splendorserver.gameelements.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TestSaveRequestBody {

  @Test
  public void orientSaveRequestBodyTest() {

    List<String> players = new ArrayList<>();
    players.add("p1");
    players.add("p2");
    SaveRequestBody orient = new SaveRequestBody("splendor_orient",
        players, "orient_id");

    assertEquals("splendor_orient", orient.gamename);
    assertEquals(players, orient.players);
    assertEquals("orient_id", orient.savegameid);
  }

  @Test
  public void tradingSaveRequestBodyTest() {

    List<String> players = new ArrayList<>();
    players.add("p1");
    players.add("p2");
    SaveRequestBody trading = new SaveRequestBody("splendor_trading",
        players, "trading_id");

    assertEquals("splendor_trading", trading.gamename);
    assertEquals(players, trading.players);
    assertEquals("trading_id", trading.savegameid);
  }

  @Test
  public void citiesSaveRequestBodyTest() {

    List<String> players = new ArrayList<>();
    players.add("p1");
    players.add("p2");
    SaveRequestBody cities = new SaveRequestBody("splendor_cities",
        players, "cities_id");

    assertEquals("splendor_cities", cities.gamename);
    assertEquals(players, cities.players);
    assertEquals("cities_id", cities.savegameid);
  }
}
