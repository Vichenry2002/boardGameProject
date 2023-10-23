package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.Inventory;
import ca.mcgill.splendorserver.gameelements.Noble;
import ca.mcgill.splendorserver.gameelements.TradingPostType;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store a limited amount of information about an inventory to be used when
 * providing clients with the state of the game.
 */
public class InventoryView {

  private List<String> allCards = new ArrayList<>();
  private List<String> cardDeckColors = new ArrayList<>();
  private List<String> reservedCards = new ArrayList<>();
  private List<String> reservedDeckColors = new ArrayList<>();
  private List<String> ownedNobles = new ArrayList<>();
  private List<String> reservedNobles = new ArrayList<>();
  private List<String> ownedTradePosts = new ArrayList<>();
  private int goldTokens;
  private int whiteTokens;
  private int blueTokens;
  private int greenTokens;
  private int redTokens;
  private int blackTokens;
  private int whiteBonus;
  private int blueBonus;
  private int greenBonus;
  private int redBonus;
  private int blackBonus;

  /**
   * Constructor for Inventory View.
   *
   * @param inventory the inventory from which to construct a view
   */
  public InventoryView(Inventory inventory) {
    for (BaseCard card : inventory.getAllCards()) {
      allCards.add(card.toString());
      cardDeckColors.add(card.getDeckColor().toString());
    }
    for (BaseCard card : inventory.getReservedCards()) {
      reservedCards.add(card.toString());
      reservedDeckColors.add(card.getDeckColor().toString());
    }
    for (Noble noble : inventory.getOwnedNobles()) {
      ownedNobles.add(noble.toString());
    }
    for (Noble noble : inventory.getReservedNobles()) {
      reservedNobles.add(noble.toString());
    }

    for (TradingPostType post : inventory.getTradePosts()) {
      ownedTradePosts.add(post.toString());
    }

    goldTokens = inventory.getGoldTokens();
    whiteTokens = inventory.getWhiteTokens();
    blueTokens = inventory.getBlueTokens();
    greenTokens = inventory.getGreenTokens();
    redTokens = inventory.getRedTokens();
    blackTokens = inventory.getBlackTokens();
    whiteBonus = inventory.getWhiteBonus();
    blueBonus = inventory.getBlueBonus();
    greenBonus = inventory.getGreenBonus();
    redBonus = inventory.getRedBonus();
    blackBonus = inventory.getBlackBonus();
  }

  /**
   * Get an ordered list containing the cards owned by the player.
   *
   * @return List of owned cards
   */
  public List<String> getAllCards() {
    return allCards;
  }

  /**
   * Get a list containing the cards reserved by the player.
   *
   * @return List of reserved cards
   */
  public List<String> getReservedCards() {
    return reservedCards;
  }

  /**
   * Get an ordered list containing the deck color of cards owned by the player.
   *
   * @return List of owned cards
   */
  public List<String> getCardDeckColors() {
    return cardDeckColors;
  }

  /**
   * Get a list containing the deck color of cards reserved by the player.
   *
   * @return List of reserved cards
   */
  public List<String> getReservedDeckColors() {
    return reservedDeckColors;
  }

  /**
   * Get a list containing the nobles owned by the player.
   *
   * @return List of nobles
   */
  public List<String> getOwnedNobles() {
    return ownedNobles;
  }

  /**
   * Get a list containing the trade posts owned by the player.
   *
   * @return List of trade posts.
   */
  public List<String> getOwnedTradePosts() {
    return ownedTradePosts;
  }

  /**
   * Get a list containing the nobles reserved by the player.
   *
   * @return List of nobles
   */
  public List<String> getReservedNobles() {
    return reservedNobles;
  }

  /**
   * Get amount of gold tokens in player's inventory.
   *
   * @return gold token amount
   */
  public int getGoldTokens() {
    return goldTokens;
  }

  /**
   * Get amount of white tokens in player's inventory.
   *
   * @return white token amount
   */
  public int getWhiteTokens() {
    return whiteTokens;
  }

  /**
   * Get amount of blue tokens in player's inventory.
   *
   * @return blue token amount
   */
  public int getBlueTokens() {
    return blueTokens;
  }

  /**
   * Get amount of green tokens in player's inventory.
   *
   * @return green token amount
   */
  public int getGreenTokens() {
    return greenTokens;
  }

  /**
   * Get amount of red tokens in player's inventory.
   *
   * @return red token amount
   */
  public int getRedTokens() {
    return redTokens;
  }

  /**
   * Get amount of black tokens in player's inventory.
   *
   * @return black token amount
   */
  public int getBlackTokens() {
    return blackTokens;
  }

  /**
   * Get white bonus amount in player's inventory.
   *
   * @return white bonus
   */
  public int getWhiteBonus() {
    return whiteBonus;
  }

  /**
   * Get blue bonus amount in player's inventory.
   *
   * @return blue bonus
   */
  public int getBlueBonus() {
    return blueBonus;
  }

  /**
   * Get green bonus amount in player's inventory.
   *
   * @return green bonus
   */
  public int getGreenBonus() {
    return greenBonus;
  }

  /**
   * Get red bonus amount in player's inventory.
   *
   * @return red bonus
   */
  public int getRedBonus() {
    return redBonus;
  }

  /**
   * Get black bonus amount in player's inventory.
   *
   * @return black bonus
   */
  public int getBlackBonus() {
    return blackBonus;
  }


  /**
   * Overrides the default equals method. Returns false if the object being
   * compared is of a different class. Returns true if the object being compared is this same
   * object or if all the fields are equal. Returns false otherwise.
   *
   * @param o the other object being compared
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    // Same object
    if (o == this) {
      return true;
    }

    // Wrong class
    if (!(o instanceof InventoryView)) {
      return false;
    }

    InventoryView other = (InventoryView) o;

    return allCards.equals(other.allCards)
        && cardDeckColors.equals(other.cardDeckColors)
        && reservedCards.equals(other.reservedCards)
        && reservedDeckColors.equals(other.reservedDeckColors)
        && ownedNobles.equals(other.ownedNobles)
        && reservedNobles.equals(other.reservedNobles)
        && goldTokens == other.goldTokens
        && whiteTokens == other.whiteTokens
        && blueTokens == other.blueTokens
        && greenTokens == other.greenTokens
        && redTokens == other.redTokens
        && blackTokens == other.blackTokens
        && whiteBonus == other.whiteBonus
        && blueBonus == other.blueBonus
        && greenBonus == other.greenBonus
        && redBonus == other.redBonus
        && blackBonus == other.blackBonus;

  }

}
