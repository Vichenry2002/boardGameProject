package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


/**
 * JUnit tests for BaseCard class
 */
public class TestBaseCard {

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
  public void getCardsTest_singleCard_allColors() {
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      assertEquals(cards.get(0).getDeckColor(), color);
      assertEquals(cards.size(), 1);
    }
  }

  @Test
  public void getCardsTest_5CardsBase(){

    DeckColor color = DeckColor.GREEN;
    List<BaseCard> cards = BaseCard.getCards(color, 5);
    assertEquals(cards.size(),5);
    for(int i=0;i<5;i++){
      assertEquals(cards.get(i).getDeckColor(),color);
    }
    for(int i=0;i<4;i++){
      for(int j=i+1;j<5;j++){
        assertNotEquals(cardDataToString(cards.get(i)),cardDataToString(cards.get(j)));
      }
    }

    color = DeckColor.BLUE;
    cards = BaseCard.getCards(color, 5);
    assertEquals(cards.size(),5);
    for(int i=0;i<5;i++){
      assertEquals(cards.get(i).getDeckColor(),color);
    }
    for(int i=0;i<4;i++){
      for(int j=i+1;j<5;j++){
        assertNotEquals(cardDataToString(cards.get(i)),cardDataToString(cards.get(j)));
      }
    }

    color = DeckColor.YELLOW;
    cards = BaseCard.getCards(color, 5);
    assertEquals(cards.size(),5);
    for(int i=0;i<5;i++){
      assertEquals(cards.get(i).getDeckColor(),color);
    }
    for(int i=0;i<4;i++){
      for(int j=i+1;j<5;j++){
        assertNotEquals(cardDataToString(cards.get(i)),cardDataToString(cards.get(j)));
      }
    }

  }

  @Test
  public void canPurchaseTest_noTokensFalse(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      assertFalse(cards.get(0).canPurchase(tokenList,0));
    }
  }

  @Test
  public void canPurchaseTest_exactColorTokensTrue(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(cards.get(0).getWhiteCost());
      tokenList.add(cards.get(0).getBlueCost());
      tokenList.add(cards.get(0).getGreenCost());
      tokenList.add(cards.get(0).getRedCost());
      tokenList.add(cards.get(0).getBlackCost());
      assertTrue(cards.get(0).canPurchase(tokenList,0));
    }
  }

  @Test
  public void canPurchaseTest_extraTokensTrue(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(cards.get(0).getWhiteCost()+1);
      tokenList.add(cards.get(0).getBlueCost()+1);
      tokenList.add(cards.get(0).getGreenCost()+2);
      tokenList.add(cards.get(0).getRedCost()+1);
      tokenList.add(cards.get(0).getBlackCost()+2);
      assertTrue(cards.get(0).canPurchase(tokenList,1));
    }
  }

  @Test
  public void canPurchaseTest_onlyGoldTokensTrue(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      assertTrue(cards.get(0).canPurchase(tokenList,
          cards.get(0).getWhiteCost()+
              cards.get(0).getBlueCost()+
              cards.get(0).getGreenCost()+
              cards.get(0).getRedCost()+
              cards.get(0).getBlackCost()
      ));
    }
  }

  @Test
  public void canPurchaseTest_missing1GoldFalse(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      assertFalse(cards.get(0).canPurchase(tokenList,
          cards.get(0).getWhiteCost()+
              cards.get(0).getBlueCost()+
              cards.get(0).getGreenCost()+
              cards.get(0).getRedCost()+
              cards.get(0).getBlackCost()-1
      ));
    }
  }

  @Test
  public void canPurchaseTest_missing1TokenFalse(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(cards.get(0).getWhiteCost());
      tokenList.add(cards.get(0).getBlueCost());
      tokenList.add(cards.get(0).getGreenCost());
      tokenList.add(cards.get(0).getRedCost());
      tokenList.add(cards.get(0).getBlackCost());
      for(int i=0;i<5;i++){
        if (tokenList.get(i) > 0){
          tokenList.set(i,tokenList.get(i)-1);
        }
      }
      assertFalse(cards.get(0).canPurchase(tokenList,0));
    }
  }

  @Test
  public void canPurchaseTest_negativeTokens(){
    for (DeckColor color : DeckColor.values()) {
      List<BaseCard> cards = BaseCard.getCards(color, 1);
      List<Integer> tokenList = new ArrayList<>();
      tokenList.add(-1);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);
      tokenList.add(0);

      assertThrows(IllegalStateException.class, () -> cards.get(0).canPurchase(tokenList, 0));
    }
  }


}
