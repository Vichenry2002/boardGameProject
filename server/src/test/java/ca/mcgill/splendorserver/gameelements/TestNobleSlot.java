package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for NobleSlot class
 */
public class TestNobleSlot {

  private NobleSlot slot1 = new NobleSlot();
  private NobleSlot slot2 = new NobleSlot(new Noble(1,2,3,0,0,3,"a"));

  @Test
  public void hasNobleTest_noNoble() {
    assertFalse(slot1.hasNoble());
  }

  @Test
  public void hasNobleTest_withNoble() {
    assertTrue(slot2.hasNoble());
  }

  @Test
  public void removeNobleTest() {
    assertTrue(slot2.hasNoble());
    slot2.removeNoble();
    assertFalse(slot2.hasNoble());
  }


  @Test
  public void nobleInSlotTest_useOnce() {
    assertTrue(slot2.hasNoble());
    Noble noble = slot2.nobleInSlot();
    assertTrue(slot2.hasNoble());
    assertSame(noble,slot2.nobleInSlot());
  }

  @Test
  public void nobleInSlotTest_useTwice() {
    assertTrue(slot2.hasNoble());
    Noble noble1 = slot2.nobleInSlot();
    Noble noble2 = slot2.nobleInSlot();
    assertTrue(slot2.hasNoble());
    assertSame(noble1,slot2.nobleInSlot());
    assertSame(noble1,noble2);
  }

  @Test
  public void getNobleStringTest() {
    assertEquals(slot1.getNobleString(), "Empty");
    assertEquals(slot2.getNobleString(), "1,2,3,0,0,3,a");
  }

}

