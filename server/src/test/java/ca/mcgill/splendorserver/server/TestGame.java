package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Inventory;
import ca.mcgill.splendorserver.gameelements.Player;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUNIT test for game class
 */
public class TestGame {
  private Player p1 = new Player("p1", "1", 1);
  private Player p2 = new Player("p2", "1", 2);
  private Player p3 = new Player("p3", "1", 3);
  private Player p4 = new Player("p4", "1", 4);
  private List<Player> players4 = Arrays.asList(p1, p2, p3, p4);
  private List<Player> players3 = Arrays.asList(p1, p2, p3);
  private List<Player> players2 = Arrays.asList(p1, p2);
  private Game testGame1 = new Game(players4, Extensions.ORIENT);
  private Game testGame2 = new Game(players3, Extensions.ORIENT_TRADING);
  private Game testGame3 = new Game(players2, Extensions.ORIENT_CITIES);
  //private Game gameFromSave = new Game(players4, "");


  @Test
  public void getCardsOnBoardTest() {
    assertNotEquals(testGame1.getCardsOnBoard().size(), 0);
  }

  @Test
  public void getGreenDeckSizeTest() {
    assertNotEquals(testGame1.getGreenDeckSize(), 0);
  }

  @Test
  public void getYellowDeckSizeTest() {
    assertNotEquals(testGame1.getYellowDeckSize(), 0);
  }

  @Test
  public void getBlueDeckSizeTest() {
    assertNotEquals(testGame1.getBlueDeckSize(), 0);
  }

  @Test
  public void getPlayersTest() {
    assertEquals(testGame1.getPlayers().size(), 4);
    assertEquals(testGame1.getPlayers().get(0), p1);
    assertEquals(testGame1.getPlayers().get(1), p2);
    assertEquals(testGame1.getPlayers().get(2), p3);
    assertEquals(testGame1.getPlayers().get(3), p4);

  }

  @Test
  public void getCurrentPlayerTest() {
    assertEquals(testGame1.getCurrentPlayer(), 1);
  }

  @Test
  public void getWhiteTokenSelectedTest() {
    assertEquals(testGame1.getWhiteTokenSelected(), 0);
  }

  @Test
  public void getBlueTokenSelectedTest() {
    assertEquals(testGame1.getBlueTokenSelected(), 0);
  }

  @Test
  public void getGreenTokenSelectedTest() {
    assertEquals(testGame1.getGreenTokenSelected(), 0);
  }

  @Test
  public void getRedTokenSelectedTest() {
    assertEquals(testGame1.getRedTokenSelected(), 0);
  }

  @Test
  public void getBlackTokenSelectedTest() {
    assertEquals(testGame1.getBlackTokenSelected(), 0);
  }

  @Test
  public void getGoldTokenSelectedTest() {
    assertEquals(testGame1.getGoldTokenSelected(), 0);
  }




  @Test
  public void getWhiteTokenAmountTest() {
    assertEquals(testGame1.getWhiteTokenAmount(), 7);
    assertEquals(testGame2.getWhiteTokenAmount(), 5);
    assertEquals(testGame3.getWhiteTokenAmount(), 4);
  }

  @Test
  public void getBlueTokenAmountTest() {
    assertEquals(testGame1.getBlueTokenAmount(), 7);
    assertEquals(testGame2.getBlueTokenAmount(), 5);
    assertEquals(testGame3.getBlueTokenAmount(), 4);
  }

  @Test
  public void getGreenTokenAmountTest() {
    assertEquals(testGame1.getGreenTokenAmount(), 7);
    assertEquals(testGame2.getGreenTokenAmount(), 5);
    assertEquals(testGame3.getGreenTokenAmount(), 4);

  }

  @Test
  public void getRedTokenAmountTest() {
    assertEquals(testGame1.getRedTokenAmount(), 7);
    assertEquals(testGame2.getRedTokenAmount(), 5);
    assertEquals(testGame3.getRedTokenAmount(), 4);
  }

  @Test
  public void getBlackTokenAmountTest() {
    assertEquals(testGame1.getBlackTokenAmount(), 7);
    assertEquals(testGame2.getBlackTokenAmount(), 5);
    assertEquals(testGame3.getBlackTokenAmount(), 4);

  }

  @Test
  public void getGoldTokenAmountTest() {
    assertEquals(testGame1.getGoldTokenAmount(), 5);
    assertEquals(testGame2.getGoldTokenAmount(), 5);
    assertEquals(testGame3.getGoldTokenAmount(), 5);
  }

  @Test
  public void getSelectedCard() {
    assertEquals(testGame1.getSelectedCard(), 0);
  }

  @Test
  public void saveGameConstructorTest() {

    Savegame save = new Savegame(testGame1);

    Game gameFromSave = new Game(players4, save);

    assertEquals(testGame1.getGoldTokenAmount(), gameFromSave.getGoldTokenAmount());
    assertEquals(testGame1.getRedTokenAmount(), gameFromSave.getRedTokenAmount());
    assertEquals(testGame1.getBlackTokenAmount(), gameFromSave.getBlackTokenAmount());
    assertEquals(testGame1.getBlueTokenAmount(), gameFromSave.getBlueTokenAmount());
    assertEquals(testGame1.getGreenTokenAmount(), gameFromSave.getGreenTokenAmount());
    assertEquals(testGame1.getWhiteTokenAmount(), gameFromSave.getWhiteTokenAmount());

    assertEquals(testGame1.getPlayers().get(0).getName(),
        gameFromSave.getPlayers().get(0).getName());
    assertEquals(testGame1.getPlayers().get(0).getPlayerColor(),
        gameFromSave.getPlayers().get(0).getPlayerColor());
    assertEquals(testGame1.getPlayers().get(0).getNumber(),
        gameFromSave.getPlayers().get(0).getNumber());
    assertEquals(testGame1.getPlayers().get(0).getPrestigePoints(),
        gameFromSave.getPlayers().get(0).getPrestigePoints());

    assertEquals(testGame1.getPlayers().get(1).getName(),
        gameFromSave.getPlayers().get(1).getName());
    assertEquals(testGame1.getPlayers().get(1).getPlayerColor(),
        gameFromSave.getPlayers().get(1).getPlayerColor());
    assertEquals(testGame1.getPlayers().get(1).getNumber(),
        gameFromSave.getPlayers().get(1).getNumber());
    assertEquals(testGame1.getPlayers().get(1).getPrestigePoints(),
        gameFromSave.getPlayers().get(1).getPrestigePoints());

    assertEquals(testGame1.getPlayers().get(2).getName(),
        gameFromSave.getPlayers().get(2).getName());
    assertEquals(testGame1.getPlayers().get(2).getPlayerColor(),
        gameFromSave.getPlayers().get(2).getPlayerColor());
    assertEquals(testGame1.getPlayers().get(2).getNumber(),
        gameFromSave.getPlayers().get(2).getNumber());
    assertEquals(testGame1.getPlayers().get(2).getPrestigePoints(),
        gameFromSave.getPlayers().get(2).getPrestigePoints());

    assertEquals(testGame1.getPlayers().get(3).getName(),
        gameFromSave.getPlayers().get(3).getName());
    assertEquals(testGame1.getPlayers().get(3).getPlayerColor(),
        gameFromSave.getPlayers().get(3).getPlayerColor());
    assertEquals(testGame1.getPlayers().get(3).getNumber(),
        gameFromSave.getPlayers().get(3).getNumber());
    assertEquals(testGame1.getPlayers().get(3).getPrestigePoints(),
        gameFromSave.getPlayers().get(3).getPrestigePoints());

    InventoryView p1Source = new InventoryView(testGame1.getPlayers().get(0).getInventory());
    InventoryView p1Save = new InventoryView(testGame1.getPlayers().get(0).getInventory());
    InventoryView p2Source = new InventoryView(testGame1.getPlayers().get(1).getInventory());
    InventoryView p2Save = new InventoryView(testGame1.getPlayers().get(1).getInventory());
    InventoryView p3Source = new InventoryView(testGame1.getPlayers().get(2).getInventory());
    InventoryView p3Save = new InventoryView(testGame1.getPlayers().get(2).getInventory());
    InventoryView p4Source = new InventoryView(testGame1.getPlayers().get(3).getInventory());
    InventoryView p4Save = new InventoryView(testGame1.getPlayers().get(3).getInventory());

    assertEquals(p1Source, p1Save);
    assertEquals(p2Source, p2Save);
    assertEquals(p3Source, p3Save);
    assertEquals(p4Source, p4Save);


  }




}
