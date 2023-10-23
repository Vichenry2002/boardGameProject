package ca.mcgill.splendorserver.gameelements;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Inventory class for the main game, will use 1 for each player.
 */
public class Inventory {
  private final List<Integer> colorTokens = new ArrayList<>();
  private final List<BaseCard> whiteBonusCards = new ArrayList<>();
  private final List<BaseCard> blueBonusCards = new ArrayList<>();
  private final List<BaseCard> greenBonusCards = new ArrayList<>();
  private final List<BaseCard> redBonusCards = new ArrayList<>();
  private final List<BaseCard> blackBonusCards = new ArrayList<>();
  private final List<BaseCard> doubleGoldCards = new ArrayList<>();
  private final List<BaseCard> reservedCards = new ArrayList<>();
  private final List<Noble> ownedNobles = new ArrayList<>();
  private final List<Noble> reservedNobles = new ArrayList<>();

  private Optional<City> ownedCity = Optional.empty();
  private Set<TradingPostType> availablePosts = new HashSet<>();
  Player player;
  private int goldTokens = 0;
  private List<BaseCard> allCards = new ArrayList<>();


  /**
   * Constructor for inventory class.
   *
   * @param paramPlayer the player to whom this inventory belongs
   */
  public Inventory(Player paramPlayer) {
    for (int i = 0; i < 5; i++) {
      colorTokens.add(0);
    }
    player = paramPlayer;
  }

  /**
   * Get white bonus.
   *
   * @return white bonus amount
   */
  public int getWhiteBonus() {
    int doubleAmount = (int) whiteBonusCards.stream().filter(
        card -> card.getType() == OrientCardType.DOUBLE_BONUS).count();
    return whiteBonusCards.size() + doubleAmount;
  }

  /**
   * Get blue bonus.
   *
   * @return blue bonus amount
   */
  public int getBlueBonus() {
    int doubleAmount = (int) blueBonusCards.stream().filter(
        card -> card.getType() == OrientCardType.DOUBLE_BONUS).count();
    return blueBonusCards.size() + doubleAmount;
  }

  /**
   * Get green bonus.
   *
   * @return green bonus amount
   */
  public int getGreenBonus() {
    int doubleAmount = (int) greenBonusCards.stream().filter(
        card -> card.getType() == OrientCardType.DOUBLE_BONUS).count();
    return greenBonusCards.size() + doubleAmount;
  }

  /**
   * Get red bonus.
   *
   * @return red bonus amount
   */
  public int getRedBonus() {
    int doubleAmount = (int) redBonusCards.stream().filter(
        card -> card.getType() == OrientCardType.DOUBLE_BONUS).count();
    return redBonusCards.size() + doubleAmount;
  }

  /**
   * Get black bonus.
   *
   * @return black bonus amount
   */
  public int getBlackBonus() {
    int doubleAmount = (int) blackBonusCards.stream().filter(
        card -> card.getType() == OrientCardType.DOUBLE_BONUS).count();
    return blackBonusCards.size() + doubleAmount;
  }

  /**
   * Get white tokens.
   *
   * @return white token amount
   */
  public int getWhiteTokens() {
    return colorTokens.get(0);
  }

  /**
   * Get blue tokens.
   *
   * @return blue token amount
   */
  public int getBlueTokens() {
    return colorTokens.get(1);
  }

  /**
   * Ger green tokens.
   *
   * @return green token amount
   */
  public int getGreenTokens() {
    return colorTokens.get(2);
  }

  /**
   * Get red tokens.
   *
   * @return red token amount
   */
  public int getRedTokens() {
    return colorTokens.get(3);
  }

  /**
   * Get black tokens.
   *
   * @return black token amount
   */
  public int getBlackTokens() {
    return colorTokens.get(4);
  }

  /**
   * Get gold tokens.
   *
   * @return gold token amount.
   */
  public int getGoldTokens() {
    return goldTokens;
  }

  /**
   * Method to add a card to the player's inventory.
   *
   * @param cardToAdd the card to be added
   */
  public void addCard(BaseCard cardToAdd) {
    player.increasePoints(cardToAdd.getPrestigePoints());
    switch (cardToAdd.getBonusColor()) {
      case WHITE -> whiteBonusCards.add(cardToAdd);
      case BLUE -> blueBonusCards.add(cardToAdd);
      case GREEN -> greenBonusCards.add(cardToAdd);
      case RED -> redBonusCards.add(cardToAdd);
      case BLACK -> blackBonusCards.add(cardToAdd);
      case GOLD -> doubleGoldCards.add(cardToAdd);
      default -> throw new IllegalStateException();
    }
  }

  /**
   * Method to add a noble to the player's inventory.
   *
   * @param noble the noble to be added.
   */
  public void addNoble(Noble noble) {
    player.increasePoints(noble.getPrestigePoints());
    ownedNobles.add(noble);
  }

  /**
   * Method to add tokens to the player's inventory.
   *
   * @param color  color of the tokens to add
   * @param amount amount of tokens to add
   */
  public void addColorTokens(int color, int amount) {
    colorTokens.set(color, colorTokens.get(color) + amount);
  }

  /**
   * Method to add gold tokens to the player's inventory.
   *
   * @param amount amount of gold tokens to add
   */
  public void addGoldToken(int amount) {
    goldTokens += amount;
  }

  /**
   * Method to add a card to the list of reserved cards.
   *
   * @param cardToReserve the card to be added
   */
  public void reserveCard(BaseCard cardToReserve) {
    reservedCards.add(cardToReserve);
    //updateReserveCards();
  }

  /**
   * Method to add a card to the list of reserved nobles.
   *
   * @param nobleToReserve the noble to be added
   */
  public void reserveNoble(Noble nobleToReserve) {
    reservedNobles.add(nobleToReserve);
  }

  /**
   * Remove tokens from inventory.
   *
   * @param color color of the tokens to remove.
   * @param amount amount of tokens to remove.
   */
  public void removeColorTokens(int color, int amount) {
    colorTokens.set(color, colorTokens.get(color) - amount);
  }

  /**
   * Remove gold tokens from inventory.
   *
   * @param amount amount of gold tokens to remove
   */
  public void removeGoldToken(int amount) {
    goldTokens = goldTokens - amount;
  }

  /**
   * May not need this (keep for now).
   */
  public void makeFullList() {
    allCards = new ArrayList<>();
    allCards.addAll(whiteBonusCards);
    allCards.addAll(blueBonusCards);
    allCards.addAll(greenBonusCards);
    allCards.addAll(redBonusCards);
    allCards.addAll(blackBonusCards);
    allCards.addAll(doubleGoldCards);
  }

  /**
   * Makes a list containing all the cards owned by the player to whom the inventory belongs,
   * ordered by bonus color and returns it.
   *
   * @return list of all owned cards
   */
  public List<BaseCard> getAllCards() {
    makeFullList();
    return allCards;
  }

  /**
   * Number of dev cards purchased.
   *
   * @return number of dev cards purchased
   */
  public int getTotalBonusCards() {
    int count = 0;
    count += whiteBonusCards.size();
    count += greenBonusCards.size();
    count += blackBonusCards.size();
    count += blueBonusCards.size();
    count += redBonusCards.size();
    return count;
  }

  /**
   * Getter for the list of cards reserved by the player to whom the inventory belongs.
   *
   * @return list of reserved cards
   */
  public List<BaseCard> getReservedCards() {
    return reservedCards;
  }

  /**
   * Getter for the nobles in the player's inventory.
   *
   * @return list of nobles in the player's inventory
   */
  public List<Noble> getOwnedNobles() {
    return ownedNobles;
  }

  /**
   * Getter for the reserved nobles in the player's inventory.
   *
   * @return list of reserved nobles in the player's inventory
   */
  public List<Noble> getReservedNobles() {
    return reservedNobles;
  }

  /**
   * Adds a city to the player's inventory.
   *
   * @param city city to add
   */
  public void setOwnedCity(City city) {
    ownedCity = Optional.of(city);
  }

  /**
   * Getter for the city owned by the player.
   *
   * @return city owned by the player
   */
  public City getOwnedCity() {
    assert hasCity();
    return ownedCity.get();
  }

  /**
   * Checks if the player has a city currently.
   *
   * @return true if the player owns a city, false otherwise
   */
  public boolean hasCity() {
    return ownedCity.isPresent();
  }

  /**
   * Discard a card from the inventory.
   *
   * @param card the card to discard
   */
  public void discard(BaseCard card) {
    player.decreasePoints(card.getPrestigePoints());
    switch (card.getBonusColor()) {
      case WHITE -> whiteBonusCards.remove(card);
      case BLUE -> blueBonusCards.remove(card);
      case GREEN -> greenBonusCards.remove(card);
      case RED -> redBonusCards.remove(card);
      case BLACK -> blackBonusCards.remove(card);
      case GOLD -> doubleGoldCards.remove(card);
      default -> throw new IllegalStateException();
    }
    makeFullList();
  }

  /**
   * Getter for the trade posts in the player's inventory.
   *
   * @return list of trade posts in the player's inventory
   */
  public Set<TradingPostType> getTradePosts() {
    return availablePosts;
  }

  /**
   * Adds a trading post to the player's inventory.
   *
   * @param tradingPosts the trading post to add
   */
  public void addTradePosts(TradingPostType tradingPosts) {
    if (availablePosts.contains(TradingPostType.PRESTIGE_PER_POST)) {
      player.increasePoints(1);
    }
    availablePosts.add(tradingPosts);
    if (tradingPosts == TradingPostType.FIVE_PRESTIGE_POINTS) {
      player.increasePoints(5);
    }
    if (tradingPosts == TradingPostType.PRESTIGE_PER_POST) {
      player.increasePoints(availablePosts.size());
    }
  }

  /**
   * Removes a trading post from the player's inventory.
   *
   * @param tradingPosts trading post to remove
   */
  public void removeTradePosts(TradingPostType tradingPosts) {
    if (availablePosts.contains(TradingPostType.PRESTIGE_PER_POST)) {
      player.decreasePoints(1);
    }
    availablePosts.remove(tradingPosts);
    if (tradingPosts == TradingPostType.FIVE_PRESTIGE_POINTS) {
      player.decreasePoints(5);
    }
    if (tradingPosts == TradingPostType.PRESTIGE_PER_POST) {
      player.decreasePoints(availablePosts.size());
    }
  }

  /**
   * Checks if the player has a trading post of a certain type.
   *
   * @param tradingPost type of trading post to check for
   * @return true if the player has the trading post, false otherwise
   */
  public boolean hasTradePost(TradingPostType tradingPost) {
    return availablePosts.contains(tradingPost);
  }

  /**
  * Method to check the amount of cards of type DOUBLE_GOLD in the player's inventory.
  *
  * @return amount of cards of type DOUBLE_GOLD
  */
  public int getGoldCardAmount() {
    return doubleGoldCards.size();
  }

  /**
   * Method to remove gold cards from the player's inventory.
   *
   * @param amount amount of gold cards to remove
   */
  public void removeGoldCards(int amount) {
    assert doubleGoldCards.size() >= amount;
    while (amount > 0) {
      doubleGoldCards.remove(0);
      amount -= 1;
    }
    makeFullList();
  }



}