package ca.mcgill.splendorserver.gameelements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Card class for the main game, includes green, yellow and blue cards. Orient extension cards
 * extend this class.
 */
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
  private final int prestigePoints;
  private final String imagePath;
  /**
   * Card data.
   */
  protected String cardData;
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

  /**
   * Constructor for BaseCard.
   *
   * @param paramDeckColor deck color
   * @param paramBonusColor color of the card bonus
   * @param paramCost list containing the costs of the card
   * @param paramPrestigePoints prestige points given by the card
   * @param path path to the card image
   * @param data string containing the card data
   */
  protected BaseCard(DeckColor paramDeckColor, BonusColor paramBonusColor, List<Integer> paramCost,
                   int paramPrestigePoints, String path, String data) {
    assert paramDeckColor != null && paramBonusColor != null
        && paramCost.size() == 5 && paramPrestigePoints >= 0;
    deckColor = paramDeckColor;
    bonusColor = paramBonusColor;
    cost = new ArrayList<>(paramCost);
    prestigePoints = paramPrestigePoints;
    imagePath = path;
    cardData = data;
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
    prestigePoints = Integer.parseInt(cardData[6]);
    imagePath = cardData[7];
    this.cardData = cardString;

  }

  /**
   * Get a list containing paramDeckSize cards with the color assigned by paramDeckColor.
   *
   * @param paramDeckColor deck color
   * @param paramDeckSize number of cards to put in deck
   * @return a list of cards
   */
  public static List<BaseCard> getCards(DeckColor paramDeckColor, int paramDeckSize) {
    assert paramDeckColor != null;
    List<BaseCard> deckCards = new ArrayList<>();
    String[] cardDataArray = colorToDataMap.get(paramDeckColor);
    buildDeck(cardDataArray, deckCards, paramDeckColor, paramDeckSize);
    return deckCards;
  }

  /**
   * Helper method called by getCards.
   *
   * @param cardSet set of cards used to build the deck (green cards, etc.)
   * @param paramDeckCards passed reference to the list of cards
   * @param paramDeckColor deck color
   * @param paramDeckSize number of cards wanted in the deck
   */
  private static void buildDeck(String[] cardSet, List<BaseCard> paramDeckCards,
                                DeckColor paramDeckColor, int paramDeckSize) {
    for (String card : cardSet) {
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
            cardData[7],
            card);

        paramDeckCards.add(currentCard);
      } else {
        // orient card
        OrientCard currentCard = new OrientCard(paramDeckColor,
            BonusColor.valueOf(cardData[0]),
            cost,
            Integer.parseInt(cardData[6]),
            OrientCardType.valueOf(cardData[7]),
            cardData[8],
            card);

        paramDeckCards.add(currentCard);
      }
    }
    Collections.shuffle(paramDeckCards);
    if (cardSet.length - paramDeckSize > 0) {
      paramDeckCards.subList(0, cardSet.length - paramDeckSize).clear();
    }
  }

  /**
   * Getter method for prestige points.
   *
   * @return the amount of prestige points given by the card
   */
  public int getPrestigePoints() {
    return prestigePoints;
  }

  /**
   * Getter method for bonus color.
   *
   * @return the color of the card bonus
   */
  public BonusColor getBonusColor() {
    return bonusColor;
  }

  /**
   * Method to get the cost in white tokens of the card.
   *
   * @return white cost
   */
  public int getWhiteCost() {
    return cost.get(0);
  }

  /**
   * Method to get the cost in blue tokens of the card.
   *
   * @return blue cost
   */
  public int getBlueCost() {
    return cost.get(1);
  }

  /**
   * Method to get the cost in green tokens of the card.
   *
   * @return green cost
   */
  public int getGreenCost() {
    return cost.get(2);
  }

  /**
   * Method to get the cost in red tokens of the card.
   *
   * @return red cost
   */
  public int getRedCost() {
    return cost.get(3);
  }

  /**
   * Method to get the cost in black tokens of the card.
   *
   * @return black cost
   */
  public int getBlackCost() {
    return cost.get(4);
  }

  /**
   * Get the deck color of the card.
   *
   * @return the color of the deck that the card is part of.
   */
  public DeckColor getDeckColor() {
    return deckColor;
  }

  /**
   * Check if the card can be purchased with the given amounts of tokens.
   *
   * @param availableColorTokens number of regular tokens available, elements in the list are in the
   *                     following order: white, blue, green, red, black
   * @param availableGoldTokens number of gold tokens available
   * @return true if it is possible to buy the card with the given tokens, false otherwise
   */
  public boolean canPurchase(List<Integer> availableColorTokens, int availableGoldTokens) {
    assert availableColorTokens.size() == 5 && availableGoldTokens >= 0;
    for (Integer token : availableColorTokens) {
      if (token < 0) {
        throw new IllegalStateException();
      }
    }
    for (int i = 0; i < 5; i++) {
      if (availableColorTokens.get(i) + availableGoldTokens < cost.get(i)) {
        return false;
      }
      availableGoldTokens = availableGoldTokens - Math.max(0, cost.get(i)
          - availableColorTokens.get(i));
    }
    return true;
  }

  /**
   * TODO.
   *
   * @return TODO
   */
  @Override
  public String toString() {
    return cardData;
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
