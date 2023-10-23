package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * JUnit tests for BaseDeck class
 */
public class BoardTest {

  private Board board2p = new Board(2);
  private Board board3p = new Board(3);
  private Board board4p = new Board(4);

  @Test
  public void getGreenDeckTest() {
    assertNotEquals(board2p.getGreenDeck().getSize(), 0);
    assertNotEquals(board3p.getGreenDeck().getSize(), 0);
    assertNotEquals(board4p.getGreenDeck().getSize(), 0);
  }

  @Test
  public void getYellowDeckTest() {
    assertNotEquals(board2p.getYellowDeck().getSize(), 0);
    assertNotEquals(board3p.getYellowDeck().getSize(), 0);
    assertNotEquals(board4p.getYellowDeck().getSize(), 0);
  }

  @Test
  public void getBlueDeckTest() {
    assertNotEquals(board2p.getBlueDeck().getSize(), 0);
    assertNotEquals(board3p.getBlueDeck().getSize(), 0);
    assertNotEquals(board4p.getBlueDeck().getSize(), 0);
  }

  @Test
  public void getCardSlotsTest() {
    assertNotEquals(board2p.getCardSlots().size(), 0);
    assertNotEquals(board3p.getCardSlots().size(), 0);
    assertNotEquals(board4p.getCardSlots().size(), 0);
  }

  @Test
  public void getWhiteTokenAmountTest() {
    assertEquals(board2p.getWhiteTokenAmount(), 4);
    assertEquals(board3p.getWhiteTokenAmount(), 5);
    assertEquals(board4p.getWhiteTokenAmount(), 7);
  }

  @Test
  public void getBlueTokenAmountTest() {
    assertEquals(board2p.getBlueTokenAmount(), 4);
    assertEquals(board3p.getBlueTokenAmount(), 5);
    assertEquals(board4p.getBlueTokenAmount(), 7);
  }

  @Test
  public void getGreenTokenAmountTest() {
    assertEquals(board2p.getGreenTokenAmount(), 4);
    assertEquals(board3p.getGreenTokenAmount(), 5);
    assertEquals(board4p.getGreenTokenAmount(), 7);
  }

  @Test
  public void getRedTokenAmountTest() {
    assertEquals(board2p.getRedTokenAmount(), 4);
    assertEquals(board3p.getRedTokenAmount(), 5);
    assertEquals(board4p.getRedTokenAmount(), 7);
  }

  @Test
  public void getBlackTokenAmountTest() {
    assertEquals(board2p.getBlackTokenAmount(), 4);
    assertEquals(board3p.getBlackTokenAmount(), 5);
    assertEquals(board4p.getBlackTokenAmount(), 7);
  }

  @Test
  public void getGoldTokenAmountTest() {
    assertEquals(board2p.getGoldTokenAmount(), 5);
    assertEquals(board3p.getGoldTokenAmount(), 5);
    assertEquals(board4p.getGoldTokenAmount(), 5);
  }

  @Test
  public void addColorTokenTest() {

    assertEquals(board4p.getWhiteTokenAmount(), 7);
    assertEquals(board4p.getBlueTokenAmount(), 7);
    assertEquals(board4p.getGreenTokenAmount(), 7);
    assertEquals(board4p.getRedTokenAmount(), 7);
    assertEquals(board4p.getBlackTokenAmount(), 7);
    assertEquals(board4p.getGoldTokenAmount(), 5);

    for (int color = 0; color < 6; color++) {
      board4p.addColorToken(color, 1);
    }

    assertEquals(board4p.getWhiteTokenAmount(), 8);
    assertEquals(board4p.getBlueTokenAmount(), 8);
    assertEquals(board4p.getGreenTokenAmount(), 8);
    assertEquals(board4p.getRedTokenAmount(), 8);
    assertEquals(board4p.getBlackTokenAmount(), 8);
    assertEquals(board4p.getGoldTokenAmount(), 6);

  }

  @Test
  public void reduceColorTokenTest() {

    assertEquals(board4p.getWhiteTokenAmount(), 7);
    assertEquals(board4p.getBlueTokenAmount(), 7);
    assertEquals(board4p.getGreenTokenAmount(), 7);
    assertEquals(board4p.getRedTokenAmount(), 7);
    assertEquals(board4p.getBlackTokenAmount(), 7);
    assertEquals(board4p.getGoldTokenAmount(), 5);

    for (int color = 0; color < 6; color++) {
      board4p.reduceColorToken(color, 1);
    }

    assertEquals(board4p.getWhiteTokenAmount(), 6);
    assertEquals(board4p.getBlueTokenAmount(), 6);
    assertEquals(board4p.getGreenTokenAmount(), 6);
    assertEquals(board4p.getRedTokenAmount(), 6);
    assertEquals(board4p.getBlackTokenAmount(), 6);
    assertEquals(board4p.getGoldTokenAmount(), 4);
  }



}
