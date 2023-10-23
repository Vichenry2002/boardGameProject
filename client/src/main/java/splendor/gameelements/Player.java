package splendor.gameelements;

import java.util.HashSet;
import java.util.Set;
import splendor.client.Game;
import splendor.client.GlobalUserInfo;

/**
 * Player class, contains relevant information, no methods yet.
 */
public class Player {

  private Inventory inventory;
  private String name;
  private String color;
  private int prestigePoints;
  private int number; //1 to 4
  private final Set<TradingPostType> tradingPosts = new HashSet<>();

  /**
   * Constructor for Player class. Starts with an empty inventory.
   *
   * @param paramName the name of the player
   * @param paramColor the preferred color of the player
   * @param paramNumber the number of the player
   */
  public Player(String paramName, String paramColor, int paramNumber) {
    name = paramName;
    color = paramColor;
    prestigePoints = 0;
    number = paramNumber;
    inventory = new Inventory(this);

  }

  /**
   * Getter for the player's number.
   *
   * @return player number
   */
  public int getNumber() {
    return number;
  }

  /**
   * Getter for the player's name.
   *
   * @return player name
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the player's points.
   *
   * @return prestige points
   */
  public int getPrestigePoints() {
    return prestigePoints;
  }

  /**
   * Increases the player's points by a given amount.
   *
   * @param points the amount to increase by
   */
  public void increasePoints(int points) {
    prestigePoints += points;
  }

  /**
   * Adds tokens of a specific color to the player's inventory.
   *
   * @param color the color of the token
   * @param amount the amount to add
   */
  public void addColorTokens(int color, int amount) {
    inventory.addColorTokens(color, amount);
  }

  /**
   * Gets a reference to the player's inventory.
   *
   * @return player's inventory
   */
  public Inventory getInventory() {
    return inventory;
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
   * Checks if the player has a trading post of a specific type.
   *
   * @param type the type of trading post
   * @return true if the player has the trading post, false otherwise
   */
  public boolean hasTradingPost(TradingPostType type) {
    boolean has;
    if (GlobalUserInfo.getGameService().equals("splendor_trading")) {
      has = tradingPosts.contains(type);
      if (has) {
        System.out.println("player number: " + number + "has trading post " + type.toString());
      } else {
        System.out.println("player number: " + number + "does not have trading post " + type.toString());
      }
      return has;
    }
    System.out.println("player number: " + number + "does not have trading post " + type.toString());
    return false;
  }

  /**
   * Adds a trading post to the player.
   *
   * @param type the type of trading post
   */
  public void addTradingPost(TradingPostType type) {
    System.out.println("Adding trading Post: " + type.toString() + "player: " + number);
    tradingPosts.add(type);
  }

  /**
   * Removes a trading post from the player.
   *
   * @param type the type of trading post
   */
  public void removeTradingPost(TradingPostType type) {
    tradingPosts.remove(type);
  }

  /**
   * Clears all trading posts from the player.
   */
  public void clearTradingPosts() {
    System.out.println("Clearing player : " + number );
    tradingPosts.clear();
  }


  public String getColor() {
    return color;
  }


}
