package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.DeckColor;
import ca.mcgill.splendorserver.gameelements.Inventory;
import ca.mcgill.splendorserver.gameelements.Noble;
import ca.mcgill.splendorserver.gameelements.Player;
import ca.mcgill.splendorserver.gameelements.TradingPostType;
import org.junit.Test;

/**
 * JUnit tests for InventoryView class.
 */
public class TestInventoryView {
  private InventoryView invView = new InventoryView(new Inventory(
      new Player("p1", "1", 1)));

  @Test
  public void getAllCardsTest() {
    assertEquals(invView.getAllCards().size(), 0);
  }

  @Test
  public void getReservedCardsTest() {
    assertEquals(invView.getReservedCards().size(), 0);
  }

  @Test
  public void getWhiteTokensTest() {
    assertEquals(invView.getWhiteTokens(), 0);
  }

  @Test
  public void getBlueTokensTest() {
    assertEquals(invView.getBlueTokens(), 0);
  }

  @Test
  public void getGreenTokensTest() {
    assertEquals(invView.getGreenTokens(), 0);
  }

  @Test
  public void getRedTokensTest() {
    assertEquals(invView.getRedTokens(), 0);
  }

  @Test
  public void getBlackTokensTest() {
    assertEquals(invView.getBlackTokens(), 0);
  }

  @Test
  public void getGoldTokensTest() {
    assertEquals(invView.getGoldTokens(), 0);
  }

  @Test
  public void getWhiteBonusTest() {
    assertEquals(invView.getWhiteBonus(), 0);
  }

  @Test
  public void getBlueBonusTest() {
    assertEquals(invView.getBlueBonus(), 0);
  }

  @Test
  public void getGreenBonusTest() {
    assertEquals(invView.getGreenBonus(), 0);
  }

  @Test
  public void getRedBonusTest() {
    assertEquals(invView.getRedBonus(), 0);
  }

  @Test
  public void getBlackBonusTest() {
    assertEquals(invView.getBlackBonus(), 0);
  }

  @Test
  public void equalsTest() {
    assertTrue(invView.equals(invView));
    assertFalse(invView.equals("inventory"));
  }

  @Test
  public void inventoryViewTest_withReservedAndNobles() {
    Inventory inv = new Inventory(new Player("p1", "1", 1));
    inv.reserveCard(new BaseCard("GREEN,5,3,0,3,3,3,Green1.png", DeckColor.BLUE));
    inv.reserveNoble(new Noble("4,0,0,0,4,3,Noble05.png"));
    inv.addNoble(new Noble("4,0,0,0,4,3,Noble05.png"));
    inv.addTradePosts(TradingPostType.PRESTIGE_PER_POST);
    invView = new InventoryView(inv);

    assertEquals(invView.getReservedCards().size(), 1);
    assertEquals(invView.getReservedNobles().size(), 1);
    assertEquals(invView.getOwnedNobles().size(), 1);

  }

}
