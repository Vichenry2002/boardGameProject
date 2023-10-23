package ca.mcgill.splendorserver.gameelements;

import ca.mcgill.splendorserver.server.Game;

/**
 * Trading post type enum, parameters are, in this order: white cost, blue cost, green cost, red
 * cost, black cost, noble cost.
 */
public enum TradingPostType {
  /**
   * After you purchase 1 development card, take 1 gem token.
   */
  FREE_TOKEN_WITH_PURCHASE(1, 0, 0, 3, 0, 0),
  /**
   * When you take 2 gem tokens of the same color, take 1 gem token of another color.
   */
  EXTRA_TOKEN_OTHER_COLOR(2, 0, 0, 0, 0, 0),
  /**
   * Each of your gold tokens is worth 2 gem tokens of the same color.
   */
  DOUBLE_GOLD(0, 3, 0, 0, 1, 0),
  /**
   * Gives the player 5 prestige points.
   */
  FIVE_PRESTIGE_POINTS(0, 0, 5, 0, 0, 1),
  /**
   * Gives the player 1 prestige point for each unlocked trading post.
   */
  PRESTIGE_PER_POST(0, 0, 0, 0, 3, 0);

  private final int whiteCost;
  private final int blueCost;
  private final int greenCost;
  private final int redCost;
  private final int blackCost;
  private final int nobleCost;

  private TradingPostType(int whiteCost, int blueCost, int greenCost, int redCost, int blackCost,
                          int nobleCost) {
    this.blackCost = blackCost;
    this.blueCost = blueCost;
    this.greenCost = greenCost;
    this.redCost = redCost;
    this.whiteCost = whiteCost;
    this.nobleCost = nobleCost;
  }

  /**
   * Check if the player has enough resources to unlock the trading post.
   *
   * @param player the player to check
   * @return true if the player has enough resources to unlock the trading post, false otherwise
   */
  public boolean meetsRequirements(Player player) {
    return whiteCost <= player.getInventory().getWhiteBonus()
        && blueCost <= player.getInventory().getBlueBonus()
        && greenCost <= player.getInventory().getGreenBonus()
        && redCost <= player.getInventory().getRedBonus()
        && blackCost <= player.getInventory().getBlackBonus()
        && nobleCost <= player.getInventory().getOwnedNobles().size();
  }
}