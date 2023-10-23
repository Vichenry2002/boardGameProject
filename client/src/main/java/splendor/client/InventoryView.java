package splendor.client;

import java.util.ArrayList;
import java.util.List;
import splendor.gameelements.Inventory;

/**
 * Class used to store a limited amount of information about an inventory to be used when
 * providing clients with the state of the game.
 */
public class InventoryView {

  public List<String> allCards = new ArrayList<>();
  public List<String> cardDeckColors = new ArrayList<>();
  public List<String> reservedCards = new ArrayList<>();
  public List<String> reservedDeckColors = new ArrayList<>();
  public List<String> ownedNobles = new ArrayList<>();
  public List<String> reservedNobles = new ArrayList<>();
  public List<String> ownedTradePosts = new ArrayList<>();
  public int goldTokens;
  public int whiteTokens;
  public int blueTokens;
  public int greenTokens;
  public int redTokens;
  public int blackTokens;
  public int whiteBonus;
  public int blueBonus;
  public int greenBonus;
  public int redBonus;
  public int blackBonus;

  /**
   * Constructor for and Inventory View from an empty Inventory.
   *
   * @param inventory the inventory from which to construct a view
   */
  public InventoryView(Inventory inventory) {
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
   * Get an ordered list containing the color of the deck of cards owned by the player.
   *
   * @return List of owned cards
   */
  public List<String> getCardDeckColors() {
    return cardDeckColors;
  }

  /**
   * Get a list containing the color of the deck of cards reserved by the player.
   *
   * @return List of reserved cards
   */
  public List<String> getReservedDeckColors() {
    return reservedDeckColors;
  }

  /**
   * Get an ordered list containing nobles owned by the player.
   *
   * @return List of owned nobles
   */
  public List<String> getOwnedNobles() {
    return ownedNobles;
  }

  /**
   * Get an ordered list containing trading posts owned by the player.
   *
   * @return List of owned trading posts.
   */
  public List<String> getOwnedTradePosts() {
    return ownedTradePosts;
  }

  /**
   * Get a list containing the nobles reserved by the player.
   *
   * @return List of reserved nobles
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


  public void setAllCards(List<String> allCards) {
    this.allCards = allCards;
  }

  public void setBlackBonus(int blackBonus) {
    this.blackBonus = blackBonus;
  }

  public void setBlackTokens(int blackTokens) {
    this.blackTokens = blackTokens;
  }

  public void setBlueBonus(int blueBonus) {
    this.blueBonus = blueBonus;
  }

  public void setBlueTokens(int blueTokens) {
    this.blueTokens = blueTokens;
  }

  public void setGoldTokens(int goldTokens) {
    this.goldTokens = goldTokens;
  }

  public void setGreenBonus(int greenBonus) {
    this.greenBonus = greenBonus;
  }

  public void setGreenTokens(int greenTokens) {
    this.greenTokens = greenTokens;
  }

  public void setRedBonus(int redBonus) {
    this.redBonus = redBonus;
  }

  public void setRedTokens(int redTokens) {
    this.redTokens = redTokens;
  }

  public void setReservedCards(List<String> reservedCards) {
    this.reservedCards = reservedCards;
  }

  public void setWhiteBonus(int whiteBonus) {
    this.whiteBonus = whiteBonus;
  }

  public void setWhiteTokens(int whiteTokens) {
    this.whiteTokens = whiteTokens;
  }

  public void setCardDeckColors(List<String> cardDeckColors) {
    this.cardDeckColors = cardDeckColors;
  }

  public void setOwnedNobles(List<String> ownedNobles) {
    this.ownedNobles = ownedNobles;
  }

  public void setReservedDeckColors(List<String> reservedDeckColors) {
    this.reservedDeckColors = reservedDeckColors;
  }

  public void setReservedNobles(List<String> reservedNobles) {
    this.reservedNobles = reservedNobles;
  }

}
