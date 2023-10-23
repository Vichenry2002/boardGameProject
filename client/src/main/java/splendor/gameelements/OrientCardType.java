package splendor.gameelements;

/**
 * Enum for the type of orient extension card.
 */
public enum OrientCardType {
  /**
   * A card of this type provides 2 gold tokens (not taken from the board), which must be
   * spent in a single purchase.
   */
  DOUBLE_GOLD,
  /**
   * A card of this type is paired to a card in the inventory of the player who purchases it at the
   * time of purchase.
   */
  PAIR_CARD,
  /**
   * A card of this type allows the player to grab a level 1 card for free at the time of purchase.
   */
  TAKE_L1_CARD,
  /**
   * A card of this type allows the player to grab a level 2 card for free at the time of purchase.
   */
  TAKE_L2_CARD,
  /**
   * A card of this type provides 2 bonuses of the associated color to use when purchasing cards.
   */
  DOUBLE_BONUS,
  /**
   * A card of this type allows the player to reserve a noble at the time of purchase.
   */
  RESERVE_NOBLE,
  /**
   * A card of this type must be purchased by discarding cards of the corresponding color from the
   * inventory instead of using tokens.
   */
  DISCARD_BONUSES,
  /**
   * A card of this type is not an orient extension card.
   */
  NONE
}
