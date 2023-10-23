package ca.mcgill.splendorserver.gameelements;

/**
 * Class for cities.
 */
public class City {

  private final int prestigePoints;
  private final int redBonuses;
  private final int whiteBonuses;
  private final int blackBonuses;
  private final int greenBonuses;
  private final int blueBonuses;
  private final int generalBonus;
  private final int cityNumber;

  /**
   * Constructor prestige.
   *
   * @param prestige points
   * @param red bonuses
   * @param white bonuses
   * @param black bonuses
   * @param green bonuses
   * @param blue bonuses
   * @param general bonus
   * @param number number
   */
  public City(int prestige, int red, int white, int black, int green, int blue, int general,
              int number) {
    prestigePoints = prestige;
    redBonuses = red;
    whiteBonuses = white;
    blackBonuses = black;
    greenBonuses = green;
    blueBonuses = blue;
    generalBonus = general;
    cityNumber = number;
  }

  /**
   * Checks if player is eligible for city.
   *
   * @param player which can have city.
   * @return true if eligible, false otherwise.
   */
  public boolean eligible(Player player) {
    return player.getPrestigePoints() >= prestigePoints
        && player.getInventory().getRedBonus() >= redBonuses
        && player.getInventory().getWhiteBonus() >= whiteBonuses
        && player.getInventory().getBlackBonus() >= blackBonuses
        && player.getInventory().getGreenBonus() >= greenBonuses
        && player.getInventory().getBlueBonus() >= blueBonuses
        && checkGeneral(player);
  }

  /**
   * Getter for the city number.
   *
   * @return city number
   */
  public int getCityNumber() {
    return cityNumber;
  }

  private boolean checkGeneral(Player player) {
    if (generalBonus == 0) {
      return true;
    }
    return (redBonuses == 0 && player.getInventory().getRedBonus() >= generalBonus)
        || (whiteBonuses == 0 && player.getInventory().getWhiteBonus() >= generalBonus)
        || (blackBonuses == 0 && player.getInventory().getBlackBonus() >= generalBonus)
        || (greenBonuses == 0 && player.getInventory().getGreenBonus() >= generalBonus)
        || (blueBonuses == 0 && player.getInventory().getBlueBonus() >= generalBonus);
  }

  /**
   * Checks if two cities are equal.
   *
   * @param city which is compared.
   * @return boolean.
   */
  public boolean equals(City city) {
    return prestigePoints == city.getPrestigePoints() && redBonuses == city.getRedBonuses()
        && whiteBonuses == city.getWhiteBonuses() && blackBonuses == city.getBlackBonuses()
        && greenBonuses == city.getGreenBonuses() && blueBonuses == city.getBlueBonuses()
        && generalBonus == city.getGeneralBonus();
  }

  /**
   * Getter for the number of prestige points required.
   *
   * @return prestige points amount
   */
  public int getPrestigePoints() {
    return prestigePoints;
  }

  /**
   * Getter for the number of red bonus required.
   *
   * @return red bonus amount
   */
  public int getRedBonuses() {
    return redBonuses;
  }

  /**
   * Getter for the number of white bonus required.
   *
   * @return white bonuse amount
   */
  public int getWhiteBonuses() {
    return whiteBonuses;
  }

  /**
   * Getter for the number of black bonus required.
   *
   * @return black bonus amount
   */
  public int getBlackBonuses() {
    return blackBonuses;
  }

  /**
   * Getter for the number of green bonuses required.
   *
   * @return green bonus amount
   */
  public int getGreenBonuses() {
    return greenBonuses;
  }

  /**
   * Getter for the number of blue bonuses required.
   *
   * @return blue bonus amount
   */
  public int getBlueBonuses() {
    return blueBonuses;
  }

  /**
   * Getter for the number of general bonuses required.
   *
   * @return general bonus amount
   */
  public int getGeneralBonus() {
    return generalBonus;
  }
}
