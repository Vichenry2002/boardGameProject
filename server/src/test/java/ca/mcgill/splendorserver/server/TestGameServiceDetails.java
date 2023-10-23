package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.server.gameservicedetails.CitiesDetails;
import ca.mcgill.splendorserver.server.gameservicedetails.OrientDetails;
import ca.mcgill.splendorserver.server.gameservicedetails.TradingDetails;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit tests for GameServiceDetails class.
 */
public class TestGameServiceDetails {

  // Orient game service
  @Test
  public void getMaxSessionPlayersTest_Orient() {
    assertEquals(new OrientDetails().getMaxSessionPlayers(), 4);
  }

  @Test
  public void getMinSessionPlayersTest_Orient() {
    assertEquals(new OrientDetails().getMinSessionPlayers(), 2);
  }

  @Test
  public void getNameTest_Orient() {
    assertEquals(new OrientDetails().getName(), "splendor_orient");
  }

  @Test
  public void getDisplayNameTest_Orient() {
    assertEquals(new OrientDetails().getDisplayName(), "splendor_orient");
  }

  @Test
  public void getLocationTest_Orient() {
    assertNotEquals(new OrientDetails().getLocation(),
        "");
    assertNotNull(new OrientDetails().getLocation());
    assertTrue(new OrientDetails().getLocation().endsWith("/splendor_orient"));
  }

  @Test
  public void isWebSupportTest_Orient() {
    assertFalse(new OrientDetails().isWebSupport());
  }

  // Cities game service
  @Test
  public void getMaxSessionPlayersTest_Cities() {
    assertEquals(new CitiesDetails().getMaxSessionPlayers(), 4);
  }

  @Test
  public void getMinSessionPlayersTest_Cities() {
    assertEquals(new CitiesDetails().getMinSessionPlayers(), 2);
  }

  @Test
  public void getNameTest_Cities() {
    assertEquals(new CitiesDetails().getName(), "splendor_cities");
  }

  @Test
  public void getDisplayNameTest_Cities() {
    assertEquals(new CitiesDetails().getDisplayName(), "splendor_cities");
  }

  @Test
  public void getLocationTest_Cities() {
    assertNotEquals(new CitiesDetails().getLocation(),
        "");
    assertNotNull(new CitiesDetails().getLocation());
    assertTrue(new CitiesDetails().getLocation().endsWith("/splendor_cities"));
  }

  @Test
  public void isWebSupportTest_Cities() {
    assertFalse(new CitiesDetails().isWebSupport());
  }

  // Trading Posts game service
  @Test
  public void getMaxSessionPlayersTest_Trading() {
    assertEquals(new TradingDetails().getMaxSessionPlayers(), 4);
  }

  @Test
  public void getMinSessionPlayersTest_Trading() {
    assertEquals(new TradingDetails().getMinSessionPlayers(), 2);
  }

  @Test
  public void getNameTest_Trading() {
    assertEquals(new TradingDetails().getName(), "splendor_trading");
  }

  @Test
  public void getDisplayNameTest_Trading() {
    assertEquals(new TradingDetails().getDisplayName(), "splendor_trading");
  }

  @Test
  public void getLocationTest_Trading() {
    assertNotEquals(new TradingDetails().getLocation(),
        "");
    assertNotNull(new TradingDetails().getLocation());
    assertTrue(new TradingDetails().getLocation().endsWith("/splendor_trading"));
  }

  @Test
  public void isWebSupportTest_Trading() {
    assertFalse(new TradingDetails().isWebSupport());
  }


}
