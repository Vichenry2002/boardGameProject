package splendor.gameelements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCard {

  private static final String[] greenCards = CardData.greenCards;
  private static final String[] yellowCards = CardData.yellowCards;
  private static final String[] blueCards = CardData.blueCards;
  private static final String[] redL1Cards = CardData.redL1Cards;
  private static final String[] redL2Cards = CardData.redL2Cards;
  private static final String[] redL3Cards = CardData.redL3Cards;
  private final DeckColor deckColor;
  private BonusColor bonusColor;


  private final List<Integer> cost;
  private final int thePrestigePoints;
  private final String imagePath;

  private static final Map<DeckColor, String[]> colorToDataMap;

  static {
    colorToDataMap = new HashMap<>();
    colorToDataMap.put(DeckColor.GREEN, greenCards);
    colorToDataMap.put(DeckColor.YELLOW, yellowCards);
    colorToDataMap.put(DeckColor.BLUE, blueCards);
    colorToDataMap.put(DeckColor.REDL1, redL1Cards);
    colorToDataMap.put(DeckColor.REDL2, redL2Cards);
    colorToDataMap.put(DeckColor.REDL3, redL3Cards);
  }


  protected BaseCard(DeckColor paramDeckColor,
                     BonusColor paramBonusColor,
                     List<Integer> paramCost,
                     int paramPrestigePoints,
                     String paramPath) {
    assert paramDeckColor != null
        && paramBonusColor != null
        && paramCost.size() == 5
        && paramPrestigePoints >= 0;
    deckColor = paramDeckColor;
    bonusColor = paramBonusColor;
    cost = new ArrayList<>(paramCost);
    thePrestigePoints = paramPrestigePoints;
    imagePath = paramPath;

  }

  public static List<BaseCard> getCards(DeckColor paramDeckColor, int paramDeckSize) {
    assert paramDeckColor != null;
    List<BaseCard> deckCards = new ArrayList<>();
    String[] cardDataArray = colorToDataMap.get(paramDeckColor);
    buildDeck(cardDataArray, deckCards, paramDeckColor, paramDeckSize);
    return deckCards;
  }

  private static void buildDeck(String[] paramCardSet, List<BaseCard> paramDeckCards,
      DeckColor paramDeckColor, int paramDeckSize) {
    for (String card : paramCardSet) {
      String[] cardData = card.split(",");
      List<Integer> cost = new ArrayList<>();
      for (int i = 1; i < 6; i++) {
        cost.add(Integer.valueOf(cardData[i]));
      }

      // base card
      if (cardData.length == 8) {
        BaseCard currentCard = new BaseCard(paramDeckColor,
            BonusColor.valueOf(cardData[0]),
            cost,
            Integer.parseInt(cardData[6]),
            cardData[7]);

        paramDeckCards.add(currentCard);
      } else {
        // orient card
        OrientCard currentCard = new OrientCard(paramDeckColor,
            BonusColor.valueOf(cardData[0]),
            cost,
            Integer.parseInt(cardData[6]),
            OrientCardType.valueOf(cardData[7]),
            cardData[8]);

        paramDeckCards.add(currentCard);
      }

    }
    Collections.shuffle(paramDeckCards);
    if (paramCardSet.length - paramDeckSize > 0) {
      paramDeckCards.subList(0, paramCardSet.length - paramDeckSize).clear();
    }
  }

  /**
   * Card constructor from string.
   *
   * @param cardString string containing card data
   * @param paramDeckColor color of the deck
   */
  public BaseCard(String cardString, DeckColor paramDeckColor) {
    String[] cardData = cardString.split(",");
    List<Integer> cost = new ArrayList<>();
    for (int i = 1; i < 6; i++) {
      cost.add(Integer.valueOf(cardData[i]));
    }
    deckColor = paramDeckColor;
    bonusColor = BonusColor.valueOf(cardData[0]);
    this.cost = cost;
    thePrestigePoints = Integer.parseInt(cardData[6]);
    imagePath = cardData[7];

  }

  public int getPrestigePoints() {
    return thePrestigePoints;
  }

  public BonusColor getBonusColor() {
    return bonusColor;
  }

  public int getWhiteCost() {
    return cost.get(0);
  }

  public int getBlueCost() {
    return cost.get(1);
  }

  public int getGreenCost() {
    return cost.get(2);
  }

  public int getRedCost() {
    return cost.get(3);
  }

  public int getBlackCost() {
    return cost.get(4);
  }

  public DeckColor getDeckColor() {
    return deckColor;
  }


  public String getPath() {
    switch (deckColor) {
      case GREEN -> {
        return "/Assets/GreenCards/".concat(imagePath);
      }
      case YELLOW -> {
        return "/Assets/OrangeCards/".concat(imagePath);
      }
      case BLUE -> {
        return "/Assets/BlueCards/".concat(imagePath);
      }
      default -> {
        return "/Assets/RedCards/".concat(imagePath);
      }
    }
  }

  public boolean canPurchase(List<Integer> paramColorTokens, int paramGoldTokens) {
    assert paramColorTokens.size() == 5 && paramGoldTokens >= 0;
    for (Integer token : paramColorTokens) {
      if (token < 0) {
        throw new IllegalStateException();
      }
    }
    for (int i = 0; i < 5; i++) {
      if (paramColorTokens.get(i) + paramGoldTokens < cost.get(i)) {
        return false;
      }
      paramGoldTokens = paramGoldTokens - Math.max(0, cost.get(i) - paramColorTokens.get(i));
    }
    return true;
  }


  /**
   * Return NONE as the type of this card to indicate that it is part of the base game.
   *
   * @return type NONE
   */
  public OrientCardType getType() {
    return OrientCardType.NONE;
  }

  /**
   * Changes the bonus color of a card.
   *
   * @param color the color to change to
   */
  public void setBonus(BonusColor color) {
    bonusColor = color;
  }


}
