package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit tests for Noble class
 */
public class NobleTest {

  private Noble testNoble1 = new Noble(1,2,3,0,0,3,"a");
  private Noble testNoble2 = new Noble(0,0,4,2,3,4,"b");
  private Noble testNoble3 = new Noble("4,4,0,0,0,3,Noble01.png");

  @Test
  public void getWhiteCostTest() {
    assertEquals(testNoble1.getWhiteCost(),1);
    assertEquals(testNoble2.getWhiteCost(),0);
    assertEquals(testNoble3.getWhiteCost(),4);
  }

  @Test
  public void getBlueCostTest() {
    assertEquals(testNoble1.getBlueCost(),2);
    assertEquals(testNoble2.getBlueCost(),0);
    assertEquals(testNoble3.getBlueCost(),4);
  }

  @Test
  public void getGreenCostTest() {
    assertEquals(testNoble1.getGreenCost(),3);
    assertEquals(testNoble2.getGreenCost(),4);
    assertEquals(testNoble3.getGreenCost(),0);
  }

  @Test
  public void getRedCostTest() {
    assertEquals(testNoble1.getRedCost(),0);
    assertEquals(testNoble2.getRedCost(),2);
    assertEquals(testNoble3.getRedCost(),0);
  }

  @Test
  public void getBlackCostTest() {
    assertEquals(testNoble1.getBlackCost(),0);
    assertEquals(testNoble2.getBlackCost(),3);
    assertEquals(testNoble3.getBlackCost(),0);
  }

  @Test
  public void getPrestigePointsTest() {
    assertEquals(testNoble1.getPrestigePoints(),3);
    assertEquals(testNoble2.getPrestigePoints(),4);
    assertEquals(testNoble3.getPrestigePoints(),3);
  }

  @Test
  public void getPathTest() {
    assertEquals(testNoble1.getPath(),"a");
    assertEquals(testNoble2.getPath(),"b");
    assertEquals(testNoble3.getPath(),"Noble01.png");
  }

  @Test
  public void toStringTest() {
    assertEquals(testNoble1.toString(),"1,2,3,0,0,3,a");
    assertEquals(testNoble2.toString(),"0,0,4,2,3,4,b");
    assertEquals(testNoble3.toString(),"4,4,0,0,0,3,Noble01.png");
  }

  @Test
  public void getNoblesTest() {
    for (int i = 0; i < 6; i++) {
      assertEquals(Noble.getNobles(i).size(),i);
    }
  }



}
