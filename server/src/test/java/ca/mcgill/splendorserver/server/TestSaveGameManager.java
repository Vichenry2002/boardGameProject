package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Player;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for SaveGameManager class.
 */
public class TestSaveGameManager {
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
  private Savegame saveGame1 = new Savegame(testGame1);
  private Savegame saveGame2 = new Savegame(testGame2);
  private Savegame saveGame3 = new Savegame(testGame3);

  private SaveGameManager manager;

  {
    try {
      manager = SaveGameManager.getInstance();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void getInstanceTest() throws IOException {
    SplendorGameController.ignoreRequests = true;

    SaveGameManager managerOther = SaveGameManager.getInstance();
    assertSame(managerOther, manager);

  }

  @Test
  public void existingIdTest() throws IOException {
    SplendorGameController.ignoreRequests = true;

    String fakeId1 = "aa";
    String fakeId2 = "829377";

    String id1 = manager.saveGame(saveGame1);
    String id2 = manager.saveGame(saveGame2);
    String id3 = manager.saveGame(saveGame3);

    assertTrue(manager.existingId(id1));
    assertTrue(manager.existingId(id2));
    assertTrue(manager.existingId(id3));
    assertFalse(manager.existingId(fakeId1));
    assertFalse(manager.existingId(fakeId2));

    manager.deleteSave(id1);
    manager.deleteSave(id2);
    manager.deleteSave(id3);

  }

  @Test
  public void saveGameTest() throws IOException {
    SplendorGameController.ignoreRequests = true;

    String id1 = manager.saveGame(saveGame1);
    String id2 = manager.saveGame(saveGame2);
    String id3 = manager.saveGame(saveGame3);

    assertTrue(manager.existingId(id1));
    assertTrue(manager.existingId(id2));
    assertTrue(manager.existingId(id3));

    manager.deleteSave(id1);
    manager.deleteSave(id2);
    manager.deleteSave(id3);

  }

  @Test
  public void saveGameTest_withId() throws IOException {
    SplendorGameController.ignoreRequests = true;

    manager.saveGame(saveGame1, "id1");
    manager.saveGame(saveGame2, "id2");
    manager.saveGame(saveGame3, "id3");

    assertTrue(manager.existingId("id1"));
    assertTrue(manager.existingId("id2"));
    assertTrue(manager.existingId("id3"));

    assertFalse(manager.saveGame(saveGame1, "id1"));
    assertFalse(manager.saveGame(saveGame1, "id"));
    assertFalse(manager.saveGame(saveGame1, "i d 1"));

    manager.deleteSave("id1");
    manager.deleteSave("id2");
    manager.deleteSave("id3");



  }

  @Test
  public void deleteSaveTest() throws IOException {
    SplendorGameController.ignoreRequests = true;

    String id1 = manager.saveGame(saveGame1);
    String id2 = manager.saveGame(saveGame2);
    String id3 = manager.saveGame(saveGame3);

    assertTrue(manager.existingId(id1));
    assertTrue(manager.existingId(id2));
    assertTrue(manager.existingId(id3));

    manager.deleteSave(id1);
    manager.deleteSave(id2);
    manager.deleteSave("0023999");

    assertFalse(manager.existingId(id1));
    assertFalse(manager.existingId(id2));
    assertTrue(manager.existingId(id3));

    manager.deleteSave(id3);

  }

  @Test
  public void getSaveFromIdTest() throws IOException {
    SplendorGameController.ignoreRequests = true;

    String id1 = manager.saveGame(saveGame1);
    String id2 = manager.saveGame(saveGame2);
    String id3 = manager.saveGame(saveGame3);

    assertTrue(manager.existingId(id1));
    assertTrue(manager.existingId(id2));
    assertTrue(manager.existingId(id3));

    Savegame save1 = manager.getSaveFromId(id1);
    Savegame save2 = manager.getSaveFromId(id2);
    Savegame save3 = manager.getSaveFromId(id3);

    // Check that the decks are equal
    assertEquals(save1.getGreenDeck(), saveGame1.getGreenDeck());
    assertEquals(save1.getBlueDeck(), saveGame1.getBlueDeck());
    assertEquals(save1.getYellowDeck(), saveGame1.getYellowDeck());
    assertEquals(save1.getRedL1Deck(), saveGame1.getRedL1Deck());
    assertEquals(save1.getRedL2Deck(), saveGame1.getRedL2Deck());
    assertEquals(save1.getRedL3Deck(), saveGame1.getRedL3Deck());

    assertEquals(save2.getGreenDeck(), saveGame2.getGreenDeck());
    assertEquals(save2.getBlueDeck(), saveGame2.getBlueDeck());
    assertEquals(save2.getYellowDeck(), saveGame2.getYellowDeck());
    assertEquals(save2.getRedL1Deck(), saveGame2.getRedL1Deck());
    assertEquals(save2.getRedL2Deck(), saveGame2.getRedL2Deck());
    assertEquals(save2.getRedL3Deck(), saveGame2.getRedL3Deck());

    assertEquals(save3.getGreenDeck(), saveGame3.getGreenDeck());
    assertEquals(save3.getBlueDeck(), saveGame3.getBlueDeck());
    assertEquals(save3.getYellowDeck(), saveGame3.getYellowDeck());
    assertEquals(save3.getRedL1Deck(), saveGame3.getRedL1Deck());
    assertEquals(save3.getRedL2Deck(), saveGame3.getRedL2Deck());
    assertEquals(save3.getRedL3Deck(), saveGame3.getRedL3Deck());

    // Check that the player information is equal
    assertEquals(save1.getPlayerInventories(), saveGame1.getPlayerInventories());
    assertEquals(save1.getPlayerColors(), saveGame1.getPlayerColors());
    assertEquals(save1.getPlayerNames(), saveGame1.getPlayerNames());
    assertEquals(save1.getPlayerPoints(), saveGame1.getPlayerPoints());

    assertEquals(save2.getPlayerInventories(), saveGame2.getPlayerInventories());
    assertEquals(save2.getPlayerColors(), saveGame2.getPlayerColors());
    assertEquals(save2.getPlayerNames(), saveGame2.getPlayerNames());
    assertEquals(save2.getPlayerPoints(), saveGame2.getPlayerPoints());

    assertEquals(save3.getPlayerInventories(), saveGame3.getPlayerInventories());
    assertEquals(save3.getPlayerColors(), saveGame3.getPlayerColors());
    assertEquals(save3.getPlayerNames(), saveGame3.getPlayerNames());
    assertEquals(save3.getPlayerPoints(), saveGame3.getPlayerPoints());

    // Check that the board elements are equals
    assertEquals(save1.getCardsOnBoard(), saveGame1.getCardsOnBoard());
    assertEquals(save1.getNoblesOnBoard(), saveGame1.getNoblesOnBoard());

    assertEquals(save2.getCardsOnBoard(), saveGame2.getCardsOnBoard());
    assertEquals(save2.getNoblesOnBoard(), saveGame2.getNoblesOnBoard());

    assertEquals(save3.getCardsOnBoard(), saveGame3.getCardsOnBoard());
    assertEquals(save3.getNoblesOnBoard(), saveGame3.getNoblesOnBoard());

    // Check that token amounts are the same
    assertSame(save1.getWhiteTokenAmount(), saveGame1.getWhiteTokenAmount());
    assertSame(save1.getBlueTokenAmount(), saveGame1.getBlueTokenAmount());
    assertSame(save1.getGreenTokenAmount(), saveGame1.getGreenTokenAmount());
    assertSame(save1.getRedTokenAmount(), saveGame1.getRedTokenAmount());
    assertSame(save1.getBlackTokenAmount(), saveGame1.getBlackTokenAmount());
    assertSame(save1.getGoldTokenAmount(), saveGame1.getGoldTokenAmount());

    assertSame(save2.getWhiteTokenAmount(), saveGame2.getWhiteTokenAmount());
    assertSame(save2.getBlueTokenAmount(), saveGame2.getBlueTokenAmount());
    assertSame(save2.getGreenTokenAmount(), saveGame2.getGreenTokenAmount());
    assertSame(save2.getRedTokenAmount(), saveGame2.getRedTokenAmount());
    assertSame(save2.getBlackTokenAmount(), saveGame2.getBlackTokenAmount());
    assertSame(save2.getGoldTokenAmount(), saveGame2.getGoldTokenAmount());

    assertSame(save3.getWhiteTokenAmount(), saveGame3.getWhiteTokenAmount());
    assertSame(save3.getBlueTokenAmount(), saveGame3.getBlueTokenAmount());
    assertSame(save3.getGreenTokenAmount(), saveGame3.getGreenTokenAmount());
    assertSame(save3.getRedTokenAmount(), saveGame3.getRedTokenAmount());
    assertSame(save3.getBlackTokenAmount(), saveGame3.getBlackTokenAmount());
    assertSame(save3.getGoldTokenAmount(), saveGame3.getGoldTokenAmount());

    // Current player
    assertSame(save1.getCurrentPlayer(), saveGame1.getCurrentPlayer());
    assertSame(save2.getCurrentPlayer(), saveGame2.getCurrentPlayer());
    assertSame(save3.getCurrentPlayer(), saveGame3.getCurrentPlayer());

    manager.deleteSave(id1);
    manager.deleteSave(id2);
    manager.deleteSave(id3);

  }

}
