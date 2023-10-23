package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for CardSlot class
 */
public class TestCardSlot {

  @Test
  public void hasCardTest_noCard(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    assertFalse(slot.hasCard());
  }

  @Test
  public void hasCardTest_withCard(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    slot.refill();
    assertTrue(slot.hasCard());
  }

  @Test
  public void removeTest_removeOne(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    slot.refill();
    assertTrue(slot.hasCard());
    BaseCard card = slot.remove();
    assertFalse(slot.hasCard());
    assertSame(card.getDeckColor(),DeckColor.GREEN);
  }

  @Test
  public void removeTest_removeTwo(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    slot.refill();
    BaseCard card1 = slot.remove();
    slot.refill();
    BaseCard card2 = slot.remove();
    assertSame(card1.getDeckColor(),DeckColor.GREEN);
    assertSame(card2.getDeckColor(),DeckColor.GREEN);
    assertNotEquals(card1,card2);
  }

  @Test
  public void getCardTest_useOnce(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    slot.refill();
    assertTrue(slot.hasCard());
    BaseCard card = slot.cardInSlot();
    assertTrue(slot.hasCard());
    assertSame(card.getDeckColor(),DeckColor.GREEN);
    assertSame(card,slot.cardInSlot());
  }

  @Test
  public void getCardTest_useTwice(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    slot.refill();
    assertTrue(slot.hasCard());
    BaseCard card1 = slot.cardInSlot();
    BaseCard card2 = slot.cardInSlot();
    assertTrue(slot.hasCard());
    assertSame(card1.getDeckColor(),DeckColor.GREEN);
    assertSame(card1,slot.cardInSlot());
    assertSame(card1,card2);
  }

  @Test
  public void refillTest(){
    BaseDeck deck = new BaseDeck(DeckColor.GREEN,2);
    CardSlot slot = new CardSlot(deck);
    assertFalse(slot.hasCard());
    slot.refill();
    assertTrue(slot.hasCard());
  }




}
