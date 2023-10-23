package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for OrientCard class.
 */
public class TestOrientCard {

  private OrientCard pairCard = new OrientCard(
      DeckColor.REDL1,
      BonusColor.NONE,
      Arrays.asList(0,3,2,0,0),
      0,
      OrientCardType.PAIR_CARD,
      "Pair1.png",
      "NONE,0,3,2,0,0,0,PAIR_CARD,Pair1.png"
      );

  private OrientCard l1cascade = new OrientCard(
      DeckColor.REDL2,
      BonusColor.NONE,
      Arrays.asList(4,3,0,0,1),
      0,
      OrientCardType.TAKE_L1_CARD,
      "L1Cascade1.png",
      "NONE,4,3,0,0,1,0,TAKE_L1_CARD,L1Cascade1.png"
  );

  private OrientCard doubleBonus = new OrientCard(
      DeckColor.REDL2,
      BonusColor.BLACK,
      Arrays.asList(0,3,0,4,0),
      0,
      OrientCardType.DOUBLE_BONUS,
      "DoubleBonus1.png",
      "BLACK,0,3,0,4,0,0,DOUBLE_BONUS,DoubleBonus1.png"
  );

  private OrientCard l2cascade = new OrientCard(
      DeckColor.REDL3,
      BonusColor.BLACK,
      Arrays.asList(6,3,0,0,1),
      0,
      OrientCardType.TAKE_L2_CARD,
      "L2Cascade1.png",
      "BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png"
  );

  private OrientCard discardBonuses = new OrientCard(
      DeckColor.REDL3,
      BonusColor.BLUE,
      Arrays.asList(2,0,0,0,0),
      3,
      OrientCardType.DISCARD_BONUSES,
      "Discard1.png",
      "BLUE,2,0,0,0,0,3,DISCARD_BONUSES,Discard1.png"
  );

  /**
   * Helper method
   *
   * @param card card
   * @return string
   */
  private static String cardDataToString(BaseCard card){
    String bonus = String.valueOf(card.getBonusColor());
    String whiteCost = String.valueOf(card.getWhiteCost());
    String blueCost = String.valueOf(card.getBlueCost());
    String greenCost = String.valueOf(card.getGreenCost());
    String redCost = String.valueOf(card.getRedCost());
    String blackCost = String.valueOf(card.getBlackCost());
    String prestige = String.valueOf(card.getPrestigePoints());

    return bonus.concat(whiteCost).concat(blueCost).concat(greenCost).concat(redCost).concat(redCost).concat(blackCost).concat(prestige);
  }

  @Test
  public void orientCardConstructorTest() {
    BaseCard card = new OrientCard(
        DeckColor.REDL3,
        BonusColor.BLACK,
        Arrays.asList(6,3,0,0,1),
        0,
        OrientCardType.TAKE_L2_CARD,
        "L2Cascade1.png",
        "BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png"
    );

    assertEquals(card.getDeckColor(), DeckColor.REDL3);
    assertEquals(card.getType(), OrientCardType.TAKE_L2_CARD);
    assertEquals(card.getPrestigePoints(), 0);
    assertEquals(card.getBlueCost(), 3);
    assertEquals(card.getBlackCost(), 1);
    assertEquals(card.getBonusColor(), BonusColor.BLACK);
    assertEquals(card.getGreenCost(), 0);
    assertEquals(card.getRedCost(), 0);
    assertEquals(card.getWhiteCost(), 6);
    assertEquals(card.toString(), "BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png");

  }

  @Test
  public void getPair_setPair_hasPairTests() {
    assertFalse(pairCard.hasPair());
    assertThrows(AssertionError.class, () -> pairCard.getPair());

    pairCard.setPair(doubleBonus);
    assertTrue(pairCard.hasPair());
    assertSame(pairCard.getPair(), doubleBonus);

    assertThrows(AssertionError.class, () -> doubleBonus.getPair());
    assertThrows(AssertionError.class, () -> doubleBonus.setPair(l1cascade));

  }

  @Test
  public void getTypeTest() {
    assertEquals(pairCard.getType(), OrientCardType.PAIR_CARD);
    assertEquals(doubleBonus.getType(), OrientCardType.DOUBLE_BONUS);
    assertEquals(l1cascade.getType(), OrientCardType.TAKE_L1_CARD);
    assertEquals(l2cascade.getType(), OrientCardType.TAKE_L2_CARD);
    assertEquals(discardBonuses.getType(), OrientCardType.DISCARD_BONUSES);

  }

  @Test
  public void getDiscardColorTest() {

    OrientCard discardBonuses2 = new OrientCard(
        DeckColor.REDL3,
        BonusColor.GREEN,
        Arrays.asList(0,2,0,0,0),
        3,
        OrientCardType.DISCARD_BONUSES,
        "Discard1.png",
        "BLUE,0,2,0,0,0,3,DISCARD_BONUSES,Discard1.png"
    );

    OrientCard discardBonuses3 = new OrientCard(
        DeckColor.REDL3,
        BonusColor.RED,
        Arrays.asList(0,0,2,0,0),
        3,
        OrientCardType.DISCARD_BONUSES,
        "Discard1.png",
        "BLUE,0,0,2,0,0,3,DISCARD_BONUSES,Discard1.png"
    );

    OrientCard discardBonuses4 = new OrientCard(
        DeckColor.REDL3,
        BonusColor.BLACK,
        Arrays.asList(0,0,0,2,0),
        3,
        OrientCardType.DISCARD_BONUSES,
        "Discard1.png",
        "BLUE,0,0,0,2,0,3,DISCARD_BONUSES,Discard1.png"
    );

    OrientCard discardBonuses5 = new OrientCard(
        DeckColor.REDL3,
        BonusColor.WHITE,
        Arrays.asList(0,0,0,0,2),
        3,
        OrientCardType.DISCARD_BONUSES,
        "Discard1.png",
        "BLUE,0,0,0,0,2,3,DISCARD_BONUSES,Discard1.png"
    );

    OrientCard discardBonusesInvalid = new OrientCard(
        DeckColor.REDL3,
        BonusColor.BLUE,
        Arrays.asList(0,0,0,0,0),
        3,
        OrientCardType.DISCARD_BONUSES,
        "Discard1.png",
        "BLUE,0,0,0,0,0,3,DISCARD_BONUSES,Discard1.png"
    );

    assertEquals(discardBonuses.getDiscardColor(), BonusColor.WHITE);
    assertEquals(discardBonuses2.getDiscardColor(), BonusColor.BLUE);
    assertEquals(discardBonuses3.getDiscardColor(), BonusColor.GREEN);
    assertEquals(discardBonuses4.getDiscardColor(), BonusColor.RED);
    assertEquals(discardBonuses5.getDiscardColor(), BonusColor.BLACK);
    assertThrows(IllegalStateException.class, discardBonusesInvalid::getDiscardColor);
    assertThrows(AssertionError.class, pairCard::getDiscardColor);

  }



}
