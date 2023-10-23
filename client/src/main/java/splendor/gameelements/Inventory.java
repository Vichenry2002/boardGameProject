package splendor.gameelements;


import java.util.ArrayList;
import java.util.List;
import splendor.client.InventorySelfController;
import splendor.client.PlayerOthersController;

/**
 * Inventory class for the main game, will use 1 for each player.
 */
public class Inventory {

  Player player;
  private List<Integer> colorTokens = new ArrayList<>();
  private int goldTokens = 0;

  private List<BaseCard> whiteBonusCards = new ArrayList<>();
  private List<BaseCard> blueBonusCards = new ArrayList<>();
  private List<BaseCard> greenBonusCards = new ArrayList<>();
  private List<BaseCard> redBonusCards = new ArrayList<>();
  private List<BaseCard> blackBonusCards = new ArrayList<>();
  private List<BaseCard> doubleGoldCards = new ArrayList<>();
  private List<BaseCard> reservedCards = new ArrayList<>();

  private List<BaseCard> allCards = new ArrayList<>();
  private List<Noble> ownedNobles = new ArrayList<>();
  private List<Noble> reservedNobles = new ArrayList<>();


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
    int doubleAmount = (int) whiteBonusCards.stream()
        .filter(card -> card.getType() == OrientCardType.DOUBLE_BONUS)
        .count();

    return whiteBonusCards.size() + doubleAmount;
  }

  /**
   * Get blue bonus.
   *
   * @return blue bonus amount
   */
  public int getBlueBonus() {
    int doubleAmount = (int) blueBonusCards.stream()
        .filter(card -> card.getType() == OrientCardType.DOUBLE_BONUS)
        .count();

    return blueBonusCards.size() + doubleAmount;
  }

  /**
   * Get green bonus.
   *
   * @return green bonus amount
   */
  public int getGreenBonus() {
    int doubleAmount = (int) greenBonusCards.stream()
        .filter(card -> card.getType() == OrientCardType.DOUBLE_BONUS)
        .count();

    return greenBonusCards.size() + doubleAmount;
  }

  /**
   * Get red bonus.
   *
   * @return red bonus amount
   */
  public int getRedBonus() {
    int doubleAmount = (int) redBonusCards.stream()
        .filter(card -> card.getType() == OrientCardType.DOUBLE_BONUS)
        .count();

    return redBonusCards.size() + doubleAmount;
  }

  /**
   * Get black bonus.
   *
   * @return black bonus amount
   */
  public int getBlackBonus() {
    int doubleAmount = (int) blackBonusCards.stream()
        .filter(card -> card.getType() == OrientCardType.DOUBLE_BONUS)
        .count();
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
   * Get green tokens.
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
    //updateInventoryCards();
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
   * Adds a noble to a player's inventory.
   *
   * @param nobleToAdd the noble to add to the inventory
   */
  public void addNoble(Noble nobleToAdd) {
    ownedNobles.add(nobleToAdd);
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
   * Updates the owned cards in the UI.
   */
  public void updateInventoryCards() {
    makeFullList();
    try {
      System.out.println("Path 1: " + allCards.get(0).getPath());
      InventorySelfController.getInstance().setImageOwned1(allCards.get(0).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned1("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 2: " + allCards.get(1).getPath());
      InventorySelfController.getInstance().setImageOwned2(allCards.get(1).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned2("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 3: " + allCards.get(2).getPath());
      InventorySelfController.getInstance().setImageOwned3(allCards.get(2).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned3("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 4: " + allCards.get(3).getPath());
      InventorySelfController.getInstance().setImageOwned4(allCards.get(3).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned4("/Assets/emptyImage.png");
    }

    try {
      System.out.println("Path 5: " + allCards.get(4).getPath());
      InventorySelfController.getInstance().setImageOwned5(allCards.get(4).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned5("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 6: " + allCards.get(5).getPath());
      InventorySelfController.getInstance().setImageOwned6(allCards.get(5).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned6("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 7: " + allCards.get(6).getPath());
      InventorySelfController.getInstance().setImageOwned7(allCards.get(6).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned7("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 8: " + allCards.get(7).getPath());
      InventorySelfController.getInstance().setImageOwned8(allCards.get(7).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned8("/Assets/emptyImage.png");
    }

    try {
      System.out.println("Path 9: " + allCards.get(8).getPath());
      InventorySelfController.getInstance().setImageOwned9(allCards.get(8).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned9("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 10: " + allCards.get(9).getPath());
      InventorySelfController.getInstance().setImageOwned10(allCards.get(9).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned10("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 11: " + allCards.get(10).getPath());
      InventorySelfController.getInstance().setImageOwned11(allCards.get(10).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned11("/Assets/emptyImage.png");
    }
    try {
      System.out.println("Path 12: " + allCards.get(11).getPath());
      InventorySelfController.getInstance().setImageOwned12(allCards.get(11).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwned12("/Assets/emptyImage.png");
    }

  }

  /**
   * Updates the reserved cards in the UI for self.
   */
  public void updateReserveCards() {
    try {
      InventorySelfController.getInstance().setImageReserved1(reservedCards.get(0).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageReserved1("/Assets/emptyImage.png");
    }
    try {
      InventorySelfController.getInstance().setImageReserved2(reservedCards.get(1).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageReserved2("/Assets/emptyImage.png");
    }
    try {
      InventorySelfController.getInstance().setImageReserved3(reservedCards.get(2).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageReserved3("/Assets/emptyImage.png");
    }
  }

  /**
   * Updates the reserved cards in the UI for other players.
   *
   * @param player the number of the player (local number)
   */
  public void updateReserveCardsOther(int player) {
    if (player == 2) {
      try {
        PlayerOthersController.getInstance().setImageP2Reserved1(reservedCards.get(0).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP2Reserved1("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP2Reserved2(reservedCards.get(1).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP2Reserved2("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP2Reserved3(reservedCards.get(2).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP2Reserved3("/Assets/emptyImage.png");
      }
    }
    else if (player == 3) {
      try {
        PlayerOthersController.getInstance().setImageP3Reserved1(reservedCards.get(0).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP3Reserved1("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP3Reserved2(reservedCards.get(1).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP3Reserved2("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP3Reserved3(reservedCards.get(2).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP3Reserved3("/Assets/emptyImage.png");
      }
    }
    else if (player == 4) {
      try {
        PlayerOthersController.getInstance().setImageP4Reserved1(reservedCards.get(0).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP4Reserved1("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP4Reserved2(reservedCards.get(1).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP4Reserved2("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP4Reserved3(reservedCards.get(2).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP4Reserved3("/Assets/emptyImage.png");
      }
    }

  }

  /**
   * Updates the owned nobles in the UI.
   */
  public void updateNobles() {
    try {
      InventorySelfController.getInstance().setImageOwnedNoble1(ownedNobles.get(0).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwnedNoble1("/Assets/emptyImage.png");
    }
    try {
      InventorySelfController.getInstance().setImageOwnedNoble2(ownedNobles.get(1).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageOwnedNoble2("/Assets/emptyImage.png");
    }
//    try {
//      InventorySelfController.getInstance().setImageOwnedNoble3(ownedNobles.get(2).getPath());
//    } catch (Exception e) {
//      InventorySelfController.getInstance().setImageOwnedNoble3("/Assets/emptyImage.png");
//    }
  }

  /**
   * Updates the reserved nobles in the UI.
   */
  public void updateReservedNobles() {
    try {
      InventorySelfController.getInstance().setImageReservedNoble1(reservedNobles.get(0).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageReservedNoble1("/Assets/emptyImage.png");
    }
    try {
      InventorySelfController.getInstance().setImageReservedNoble2(reservedNobles.get(1).getPath());
    } catch (Exception e) {
      InventorySelfController.getInstance().setImageReservedNoble2("/Assets/emptyImage.png");
    }
//    try {
//      InventorySelfController.getInstance().setImageOwnedNoble3(ownedNobles.get(2).getPath());
//    } catch (Exception e) {
//      InventorySelfController.getInstance().setImageOwnedNoble3("/Assets/emptyImage.png");
//    }
  }

  /**
   * Updates the owned nobles in the UI for other players.
   */
  public void updateNoblesOther(int player) {
    if (player == 2) {
      try {
        PlayerOthersController.getInstance().setImageP2OwnedNoble1(ownedNobles.get(0).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP2OwnedNoble1("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP2OwnedNoble2(ownedNobles.get(1).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP2OwnedNoble2("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP2OwnedNoble3(ownedNobles.get(2).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP2OwnedNoble3("/Assets/emptyImage.png");
      }
    }
    else if (player == 3) {
      try {
        PlayerOthersController.getInstance().setImageP3OwnedNoble1(ownedNobles.get(0).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP3OwnedNoble1("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP3OwnedNoble2(ownedNobles.get(1).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP3OwnedNoble2("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP3OwnedNoble3(ownedNobles.get(2).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP3OwnedNoble3("/Assets/emptyImage.png");
      }
    }
    else if (player == 4) {
      try {
        PlayerOthersController.getInstance().setImageP4OwnedNoble1(ownedNobles.get(0).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP4OwnedNoble1("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP4OwnedNoble2(ownedNobles.get(1).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP4OwnedNoble2("/Assets/emptyImage.png");
      }
      try {
        PlayerOthersController.getInstance().setImageP4OwnedNoble3(ownedNobles.get(2).getPath());
      } catch (Exception e) {
        PlayerOthersController.getInstance().setImageP4OwnedNoble3("/Assets/emptyImage.png");
      }
    }

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
   * Sets the token amount for a specific token color.
   *
   * @param color the color of the token
   * @param amount the amount to set to
   */
  public void setColorTokens(int color, int amount) {
    colorTokens.set(color, amount);
  }

  /**
   * Sets the token amount for the gold token.
   *
   * @param amount the amount to set to
   */
  public void setGoldTokens(int amount) {
    goldTokens = amount;
  }

  /**
   * Remove all cards from the player's inventory.
   */
  public void emptyCards() {
    whiteBonusCards = new ArrayList<>();
    blueBonusCards = new ArrayList<>();
    greenBonusCards = new ArrayList<>();
    redBonusCards = new ArrayList<>();
    blackBonusCards = new ArrayList<>();
    doubleGoldCards = new ArrayList<>();
    allCards = new ArrayList<>();
  }

  /**
   * Remove all cards from the player's reserved cards.
   */
  public void emptyReserve() {
    reservedCards = new ArrayList<>();
  }

  /**
   * Remove all nobles owned by the player.
   */
  public void emptyNobles() {
    ownedNobles = new ArrayList<>();
  }

  /**
   * Remove all nobles reserved by the player.
   */
  public void emptyReservedNobles() {
    reservedNobles = new ArrayList<>();
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
   * Method to check the amount of cards of type DOUBLE_GOLD in the player's inventory.
   *
   * @return amount of cards of type DOUBLE_GOLD
   */
  public int getGoldCardAmount() {
    return doubleGoldCards.size();
  }

  /**
   * Method to add a noble to the list of reserved nobles.
   *
   * @param nobleToReserve the noble to be added
   */
  public void reserveNoble(Noble nobleToReserve) {
    reservedNobles.add(nobleToReserve);
  }

}