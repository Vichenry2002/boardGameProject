package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for Inventory class
 */
public class TestInventory {

  private Inventory inventory = new Inventory(new Player("Empty", "", 1));

  @Test
  public void getWhiteBonusTest() {
    assertEquals(inventory.getWhiteBonus(), 0);
    BonusColor color = BonusColor.GREEN;
    BaseCard card = null;
    while (color != BonusColor.WHITE) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getWhiteBonus(), 1);
  }

  @Test
  public void getBlueBonusTest() {
    assertEquals(inventory.getBlueBonus(), 0);
    BonusColor color = BonusColor.GREEN;
    BaseCard card = null;
    while (color != BonusColor.BLUE) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getBlueBonus(), 1);
  }

  @Test
  public void getGreenBonusTest() {
    assertEquals(inventory.getGreenBonus(), 0);
    BonusColor color = BonusColor.BLUE;
    BaseCard card = null;
    while (color != BonusColor.GREEN) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getGreenBonus(), 1);
  }

  @Test
  public void getRedBonusTest() {
    assertEquals(inventory.getRedBonus(), 0);
    BonusColor color = BonusColor.BLUE;
    BaseCard card = null;
    while (color != BonusColor.RED) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getRedBonus(), 1);
  }

  @Test
  public void getBlackBonusTest() {
    assertEquals(inventory.getBlackBonus(), 0);
    BonusColor color = BonusColor.BLUE;
    BaseCard card = null;
    while (color != BonusColor.BLACK) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getBlackBonus(), 1);
  }

  @Test
  public void getWhiteTokensTest() {
    assertEquals(inventory.getWhiteTokens(), 0);
    inventory.addColorTokens(0,1);
    assertEquals(inventory.getWhiteTokens(), 1);
  }

  @Test
  public void getBlueTokensTest() {
    assertEquals(inventory.getBlueTokens(), 0);
    inventory.addColorTokens(1,1);
    assertEquals(inventory.getBlueTokens(), 1);
  }

  @Test
  public void getGreenTokensTest() {
    assertEquals(inventory.getGreenTokens(), 0);
    inventory.addColorTokens(2,1);
    assertEquals(inventory.getGreenTokens(), 1);
  }

  @Test
  public void getRedTokensTest() {
    assertEquals(inventory.getRedTokens(), 0);
    inventory.addColorTokens(3,1);
    assertEquals(inventory.getRedTokens(), 1);
  }

  @Test
  public void getBlackTokensTest() {
    assertEquals(inventory.getBlackTokens(), 0);
    inventory.addColorTokens(4,1);
    assertEquals(inventory.getBlackTokens(), 1);
  }

  @Test
  public void getGoldTokensTest() {
    assertEquals(inventory.getGoldTokens(), 0);
    inventory.addGoldToken(1);
    assertEquals(inventory.getGoldTokens(), 1);
  }

  @Test
  public void addCardTest_WhiteCard() {
    assertEquals(inventory.getAllCards().size(), 0);
    assertEquals(inventory.getWhiteBonus(), 0);
    BonusColor color = BonusColor.GREEN;
    BaseCard card = null;
    while (color != BonusColor.WHITE) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getAllCards().size(), 1);
    assertEquals(inventory.getWhiteBonus(), 1);
  }

  @Test
  public void addCardTest_BlueCard() {
    assertEquals(inventory.getAllCards().size(), 0);
    assertEquals(inventory.getBlueBonus(), 0);
    BonusColor color = BonusColor.GREEN;
    BaseCard card = null;
    while (color != BonusColor.BLUE) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getAllCards().size(), 1);
    assertEquals(inventory.getBlueBonus(), 1);
  }

  @Test
  public void addCardTest_TwoGreen_OneRed() {
    assertEquals(inventory.getAllCards().size(), 0);
    assertEquals(inventory.getGreenBonus(), 0);
    assertEquals(inventory.getRedBonus(), 0);

    BonusColor color = BonusColor.WHITE;
    BaseCard card = null;
    while (color != BonusColor.GREEN) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);

    while (color != BonusColor.RED) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);

    while (color != BonusColor.GREEN) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);

    assertEquals(inventory.getAllCards().size(), 3);
    assertEquals(inventory.getGreenBonus(), 2);
    assertEquals(inventory.getRedBonus(), 1);
  }

  @Test
  public void addCardTest_Black_Points() {
    assertEquals(inventory.getAllCards().size(), 0);
    assertEquals(inventory.getBlackBonus(), 0);
    BonusColor color = BonusColor.WHITE;
    BaseCard card = null;
    while (color != BonusColor.BLACK) {
      card = new BaseDeck(DeckColor.YELLOW, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getAllCards().size(), 1);
    assertEquals(inventory.getBlackBonus(), 1);
  }

  @Test
  public void getReservedCardsTest() {
    assertEquals(inventory.getReservedCards().size(), 0);
    BaseDeck deck = new BaseDeck(DeckColor.GREEN, 3);
    BaseCard card1 = deck.draw();
    BaseCard card2 = deck.draw();
    BaseCard card3 = deck.draw();
    inventory.reserveCard(card1);
    assertEquals(inventory.getReservedCards().size(), 1);
    inventory.reserveCard(card2);
    assertEquals(inventory.getReservedCards().size(), 2);
    inventory.reserveCard(card3);
    assertEquals(inventory.getReservedCards().size(), 3);
    assertTrue(inventory.getReservedCards().contains(card1));
    assertTrue(inventory.getReservedCards().contains(card2));
    assertTrue(inventory.getReservedCards().contains(card3));
  }

  @Test
  public void removeGoldTokensTest() {
    assertEquals(inventory.getGoldTokens(), 0);
    inventory.addGoldToken(1);
    assertEquals(inventory.getGoldTokens(), 1);
    inventory.removeGoldToken(1);
    assertEquals(inventory.getGoldTokens(), 0);
  }

  @Test
  public void removeColorTokensTest() {
    assertEquals(inventory.getWhiteTokens(), 0);
    for (int i = 0; i < 5; i++) {
      inventory.addColorTokens(i, i+1);
    }
    assertEquals(inventory.getWhiteTokens(), 1);
    assertEquals(inventory.getBlueTokens(), 2);
    assertEquals(inventory.getGreenTokens(), 3);
    assertEquals(inventory.getRedTokens(), 4);
    assertEquals(inventory.getBlackTokens(), 5);

    for (int i = 0; i < 5; i++) {
      inventory.removeColorTokens(i, 1);
    }

    assertEquals(inventory.getWhiteTokens(), 0);
    assertEquals(inventory.getBlueTokens(), 1);
    assertEquals(inventory.getGreenTokens(), 2);
    assertEquals(inventory.getRedTokens(), 3);
    assertEquals(inventory.getBlackTokens(), 4);

    for (int i = 2; i < 5; i++) {
      inventory.removeColorTokens(i, 2);
    }

    assertEquals(inventory.getWhiteTokens(), 0);
    assertEquals(inventory.getBlueTokens(), 1);
    assertEquals(inventory.getGreenTokens(), 0);
    assertEquals(inventory.getRedTokens(), 1);
    assertEquals(inventory.getBlackTokens(), 2);

  }

  @Test
  public void addNobleTest() {
    assertEquals(inventory.getOwnedNobles().size(), 0);
    Noble noble = new Noble(3,3,3,0,0,3,"a");
    inventory.addNoble(noble);
    assertEquals(inventory.getOwnedNobles().size(), 1);
  }

  @Test
  public void reserveNobleTest() {
    assertEquals(inventory.getReservedNobles().size(), 0);
    Noble noble = new Noble(3,3,3,0,0,3,"a");
    inventory.reserveNoble(noble);
    assertEquals(inventory.getReservedNobles().size(), 1);
  }

  @Test
  public void discardTest_White() {
    assertEquals(inventory.getWhiteBonus(), 0);
    BonusColor color = BonusColor.GREEN;
    BaseCard card = null;
    while (color != BonusColor.WHITE) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getWhiteBonus(), 1);
    inventory.discard(card);
    assertEquals(inventory.getWhiteBonus(), 0);
  }

  @Test
  public void discardTest_Blue() {
    assertEquals(inventory.getBlueBonus(), 0);
    BonusColor color = BonusColor.GREEN;
    BaseCard card = null;
    while (color != BonusColor.BLUE) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getBlueBonus(), 1);
    inventory.discard(card);
    assertEquals(inventory.getBlueBonus(), 0);
  }

  @Test
  public void discardTest_Green() {
    assertEquals(inventory.getGreenBonus(), 0);
    BonusColor color = BonusColor.BLUE;
    BaseCard card = null;
    while (color != BonusColor.GREEN) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getGreenBonus(), 1);
    inventory.discard(card);
    assertEquals(inventory.getGreenBonus(), 0);
  }

  @Test
  public void discardTest_Red() {
    assertEquals(inventory.getRedBonus(), 0);
    BonusColor color = BonusColor.BLUE;
    BaseCard card = null;
    while (color != BonusColor.RED) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getRedBonus(), 1);
    inventory.discard(card);
    assertEquals(inventory.getRedBonus(), 0);
  }

  @Test
  public void discardTest_Black() {
    assertEquals(inventory.getBlackBonus(), 0);
    BonusColor color = BonusColor.BLUE;
    BaseCard card = null;
    while (color != BonusColor.BLACK) {
      card = new BaseDeck(DeckColor.GREEN, 1).draw();
      color = card.getBonusColor();
    }
    inventory.addCard(card);
    assertEquals(inventory.getBlackBonus(), 1);
    inventory.discard(card);
    assertEquals(inventory.getBlackBonus(), 0);
  }












}
