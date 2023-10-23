package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Player;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for GameState class.
 */
public class TestGameState {
  private Player p1 = new Player("p1", "1", 1);
  private Player p2 = new Player("p2", "1", 2);
  private Player p3 = new Player("p3", "1", 3);
  private Player p4 = new Player("p4", "1", 4);
  private List<Player> players4 = Arrays.asList(p1, p2, p3, p4);
  private List<Player> players3 = Arrays.asList(p1, p2, p3);
  private List<Player> players2 = Arrays.asList(p1, p2);
  private Game testGame1 = new Game(players4, Extensions.ORIENT);
  private Game testGame2 = new Game(players3, Extensions.ORIENT);
  private Game testGame3 = new Game(players2, Extensions.ORIENT);
  private GameState gameState1 = new GameState(testGame1);
  private GameState gameState2 = new GameState(testGame2);
  private GameState gameState3 = new GameState(testGame3);



  @Test
  public void getCardsOnBoardTest() {
    assertNotEquals(gameState1.getCardsOnBoard().size(), 0);
  }

  @Test
  public void getGreenDeckTest() {
    assertNotEquals(gameState1.getGreenDeck(), 0);
    assertNotEquals(gameState2.getGreenDeck(), 0);
    assertNotEquals(gameState3.getGreenDeck(), 0);

  }

  @Test
  public void getYellowDeckTest() {
    assertNotEquals(gameState1.getYellowDeck(), 0);
    assertNotEquals(gameState2.getYellowDeck(), 0);
    assertNotEquals(gameState3.getYellowDeck(), 0);
  }

  @Test
  public void getBlueDeckTest() {
    assertNotEquals(gameState1.getBlueDeck(), 0);
    assertNotEquals(gameState2.getBlueDeck(), 0);
    assertNotEquals(gameState3.getBlueDeck(), 0);
  }

  @Test
  public void getPlayerInventoriesTest() {
    assertEquals(gameState1.getPlayerInventories().size(), 4);
    assertEquals(gameState2.getPlayerInventories().size(), 3);
    assertEquals(gameState3.getPlayerInventories().size(), 2);
  }

  @Test
  public void getPlayerNamesTest() {
    assertEquals(gameState1.getPlayerNames().size(), 4);
    assertEquals(gameState2.getPlayerNames().size(), 3);
    assertEquals(gameState3.getPlayerNames().size(), 2);

    assertEquals(gameState1.getPlayerNames().get("Player1"), p1.getName());
    assertEquals(gameState1.getPlayerNames().get("Player2"), p2.getName());
    assertEquals(gameState1.getPlayerNames().get("Player3"), p3.getName());
  }

  @Test
  public void getPlayerColorsTest() {
    assertEquals(gameState1.getPlayerColors().size(), 4);
    assertEquals(gameState2.getPlayerColors().size(), 3);
    assertEquals(gameState3.getPlayerColors().size(), 2);

    assertEquals(gameState1.getPlayerColors().get("Player1"), p1.getPlayerColor());
    assertEquals(gameState1.getPlayerColors().get("Player2"), p2.getPlayerColor());
    assertEquals(gameState1.getPlayerColors().get("Player3"), p3.getPlayerColor());
  }

  @Test
  public void getPlayerPointsTest() {
    assertEquals(gameState1.getPlayerPoints().size(), 4);
    assertEquals(gameState2.getPlayerPoints().size(), 3);
    assertEquals(gameState3.getPlayerPoints().size(), 2);

    assertEquals(gameState1.getPlayerPoints().get("Player1").intValue(), p1.getPrestigePoints());
    assertEquals(gameState1.getPlayerPoints().get("Player2").intValue(), p2.getPrestigePoints());
    assertEquals(gameState1.getPlayerPoints().get("Player3").intValue(), p3.getPrestigePoints());
  }

  @Test
  public void getCurrentPlayerTest() {
    assertEquals(gameState1.getCurrentPlayer(), 1);
  }

  @Test
  public void getWhiteTokenSelectedTest() {
    assertEquals(gameState1.getWhiteTokenSelected(), 0);
  }

  @Test
  public void getBlueTokenSelectedTest() {
    assertEquals(gameState1.getBlueTokenSelected(), 0);
  }

  @Test
  public void getGreenTokenSelectedTest() {
    assertEquals(gameState1.getGreenTokenSelected(), 0);
  }

  @Test
  public void getRedTokenSelectedTest() {
    assertEquals(gameState1.getRedTokenSelected(), 0);
  }

  @Test
  public void getBlackTokenSelectedTest() {
    assertEquals(gameState1.getBlackTokenSelected(), 0);
  }

  @Test
  public void getGoldTokenSelectedTest() {
    assertEquals(gameState1.getGoldTokenSelected(), 0);
  }




  @Test
  public void getWhiteTokenAmountTest() {
    assertEquals(gameState1.getWhiteTokenAmount(), 7);
    assertEquals(gameState2.getWhiteTokenAmount(), 5);
    assertEquals(gameState3.getWhiteTokenAmount(), 4);
  }

  @Test
  public void getBlueTokenAmountTest() {
    assertEquals(gameState1.getBlueTokenAmount(), 7);
    assertEquals(gameState2.getBlueTokenAmount(), 5);
    assertEquals(gameState3.getBlueTokenAmount(), 4);
  }

  @Test
  public void getGreenTokenAmountTest() {
    assertEquals(gameState1.getGreenTokenAmount(), 7);
    assertEquals(gameState2.getGreenTokenAmount(), 5);
    assertEquals(gameState3.getGreenTokenAmount(), 4);

  }

  @Test
  public void getRedTokenAmountTest() {
    assertEquals(gameState1.getRedTokenAmount(), 7);
    assertEquals(gameState2.getRedTokenAmount(), 5);
    assertEquals(gameState3.getRedTokenAmount(), 4);
  }

  @Test
  public void getBlackTokenAmountTest() {
    assertEquals(gameState1.getBlackTokenAmount(), 7);
    assertEquals(gameState2.getBlackTokenAmount(), 5);
    assertEquals(gameState3.getBlackTokenAmount(), 4);

  }

  @Test
  public void getGoldTokenAmountTest() {
    assertEquals(gameState1.getGoldTokenAmount(), 5);
    assertEquals(gameState2.getGoldTokenAmount(), 5);
    assertEquals(gameState3.getGoldTokenAmount(), 5);
  }

  @Test
  public void getSelectedCard() {
    assertEquals(gameState1.getSelectedCard(), 0);
  }

  @Test
  public void isEmptyTest() {
    assertFalse(gameState1.isEmpty());
  }
}
