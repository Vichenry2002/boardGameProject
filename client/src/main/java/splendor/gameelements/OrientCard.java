package splendor.gameelements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Card class for the cards belonging to the Orient extension which extends the BaseCard class.
 */
public class OrientCard extends BaseCard {
  private final OrientCardType type;
  private Optional<BaseCard> pair = Optional.empty();

  /**
   * Constructor for OrientCard class.
   *
   * @param paramDeckColor deck color (RedL1, RedL2 or RedL3
   * @param paramBonusColor color of the card bonus
   * @param paramCost list containing the costs of the card (can be tokens cost or discard costs)
   * @param paramPrestigePoints prestige points given by the card
   * @param paramType the specific type of the card
   * @param path path to the card image
   */
  protected OrientCard(DeckColor paramDeckColor,
                       BonusColor paramBonusColor,
                       List<Integer> paramCost,
                       int paramPrestigePoints,
                       OrientCardType paramType,
                       String path) {
    super(paramDeckColor, paramBonusColor, paramCost, paramPrestigePoints, path);
    System.out.println(path);
    type = paramType;
  }

  /**
   * Card constructor from string.
   *
   * @param cardString string containing card data
   * @param paramDeckColor color of the deck
   */
  public static OrientCard getOrientCard(String cardString, DeckColor paramDeckColor) {
    String[] cardData = cardString.split(",");
    List<Integer> cost = new ArrayList<>();
    for (int i = 1; i < 6; i++) {
      cost.add(Integer.valueOf(cardData[i]));
    }
    DeckColor deckColor = paramDeckColor;
    BonusColor bonusColor = BonusColor.valueOf(cardData[0]);
    int prestigePoints = Integer.parseInt(cardData[6]);
    String imagePath = cardData[8];
    OrientCardType type = OrientCardType.valueOf(cardData[7]);

    return new OrientCard(deckColor, bonusColor, cost, prestigePoints, type, imagePath);

  }

  /**
   * Returns the card that is paired to this card. Only used for cards of type PAIR_CARD or
   * TAKE_L1_CARD.
   *
   * @return the card paired to this card
   */
  public BaseCard getPair() {
    assert type == OrientCardType.PAIR_CARD || type == OrientCardType.TAKE_L1_CARD;
    assert pair.isPresent();
    return pair.get();
  }

  /**
   * Change the card that is paired to this card. Only used for cards of type PAIR_CARD or
   * TAKE_L1_CARD.
   *
   * @param newPair the card to pair this card to.
   */
  public void setPair(BaseCard newPair) {
    assert type == OrientCardType.PAIR_CARD || type == OrientCardType.TAKE_L1_CARD;
    assert pair.isEmpty();
    pair = Optional.of(newPair);
  }

  /**
   * Checks whether there is currently a paired card to the current one.
   *
   * @return true if there is a paired card, false otherwise
   */
  public boolean hasPair() {
    return pair.isPresent();
  }


  /**
   * Getter for the type of this card.
   *
   * @return orient card type
   */
  public OrientCardType getType() {
    return type;
  }

  /**
   * Get the color of the bonuses of the cards to discard in order to buy this card.
   *
   * @return bonus color
   */
  public BonusColor getDiscardColor() {
    assert type == OrientCardType.DISCARD_BONUSES;
    if (getWhiteCost() == 2) {
      return BonusColor.WHITE;
    } else if (getBlueCost() == 2) {
      return BonusColor.BLUE;
    } else if (getGreenCost() == 2) {
      return BonusColor.GREEN;
    } else if (getRedCost() == 2) {
      return BonusColor.RED;
    } else if (getBlackCost() == 2) {
      return BonusColor.BLACK;
    } else {
      throw new IllegalStateException("Wrong card data");
    }
  }
}
