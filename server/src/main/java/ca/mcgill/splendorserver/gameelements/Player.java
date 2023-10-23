package ca.mcgill.splendorserver.gameelements;

/**
 * Player class, contains relevant information, no methods yet.
 */
public class Player {
  private final Inventory inventory;
  private final String playerName;
  private final String playerColor;
  private int prestigePoints;
  private int playerNumber;

  /**
   * Constructor for Player.
   *
   * @param name the name of the player
   * @param color the color of the player
   * @param number the number of the player (1-4)
   */
  public Player(String name, String color, int number) {
    playerName = name;
    playerColor = color;
    prestigePoints = 0;
    playerNumber = number;
    inventory = new Inventory(this);

  }

  /**
   * Get player number.
   *
   * @return number of the player
   */
  public int getNumber() {
    return playerNumber;
  }

  /**
   * Get name.
   *
   * @return player name
   */
  public String getName() {
    return playerName;
  }

  /**
   * Get prestige points.
   *
   * @return prestige points
   */
  public int getPrestigePoints() {
    return prestigePoints;
  }

  /**
   * Increase prestige points.
   *
   * @param points the amount to increase by
   */
  public void increasePoints(int points) {
    prestigePoints += points;
  }

  /**
   * Decrease prestige points.
   *
   * @param points the amount to decrease by
   */
  public void decreasePoints(int points) {
    prestigePoints -= points;
  }

  /**
   * Add color tokens to player's inventory.
   *
   * @param color the color of the tokens
   * @param amount the amount of tokens
   */
  public void addColorTokens(int color, int amount) {
    inventory.addColorTokens(color, amount);
  }

  /**
   * Get a reference to the player's inventory.
   *
   * @return inventory
   */
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * Get color.
   *
   * @return player color
   */
  public String getPlayerColor() {
    return playerColor;
  }

  /**
   * Set player number.
   *
   * @param number the number to set to
   */
  public void setPlayerNumber(int number) {
    playerNumber = number;
  }


}
