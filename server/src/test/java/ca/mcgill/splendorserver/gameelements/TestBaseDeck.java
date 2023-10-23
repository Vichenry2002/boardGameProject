package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for BaseDeck class
 */
public class TestBaseDeck {

  @Test
  public void isEmptyTest_empty(){
    for(DeckColor color : DeckColor.values()){
      BaseDeck deck = new BaseDeck(color,1);
      BaseCard card = deck.draw();
      assertTrue(deck.isEmpty());
    }
  }

  @Test
  public void isEmptyTest_withCards(){
    for(DeckColor color : DeckColor.values()){
      BaseDeck deck = new BaseDeck(color,2);
      assertFalse(deck.isEmpty());
    }
  }

  @Test
  public void drawTest(){
    for(DeckColor color : DeckColor.values()){
      BaseDeck deck = new BaseDeck(color,2);
      assertEquals(deck.getSize(),2);
      BaseCard card1 = deck.draw();
      assertEquals(deck.getSize(),1);
      BaseCard card2 = deck.draw();
      assertEquals(deck.getSize(),0);
      assertNotEquals(card1,card2);
    }
  }

  @Test
  public void getDeckColorTest(){
    for(DeckColor color : DeckColor.values()){
      BaseDeck deck = new BaseDeck(color,2);
      assertEquals(color,deck.getDeckColor());
    }
  }

  @Test
  public void getSizeTest(){
    for(DeckColor color : DeckColor.values()){
      BaseDeck deck1 = new BaseDeck(color,1);
      assertEquals(deck1.getSize(),1);
      BaseDeck deck2 = new BaseDeck(color,2);
      assertEquals(deck2.getSize(),2);
      BaseDeck deck3 = new BaseDeck(color,3);
      assertEquals(deck3.getSize(),3);
    }
  }


}
