package ca.mcgill.splendorserver.gameelements;

import ca.mcgill.splendorserver.server.Savegame;
import java.util.ArrayList;
import java.util.List;

/**
 * Board class, 1 board per game.
 */
public class Board {

  private final List<CardSlot> cardSlots = new ArrayList<>();
  private final List<NobleSlot> nobleSlots = new ArrayList<>();
  private final List<CitySlot> citySlots = new ArrayList<>();
  private final BaseDeck greenDeck;
  private final BaseDeck yellowDeck;
  private final BaseDeck blueDeck;
  private final BaseDeck redL1Deck;
  private final BaseDeck redL2Deck;
  private final BaseDeck redL3Deck;
  private int whiteTokenAmount;
  private int blueTokenAmount;
  private int greenTokenAmount;
  private int redTokenAmount;
  private int blackTokenAmount;
  private int goldTokenAmount;


  /**
   * Board constructor.
   *
   * @param players the players that are part of the game
   */
  public Board(int players) {
    assert players > 1 && players < 5;
    greenDeck = new BaseDeck(DeckColor.GREEN, 40);
    yellowDeck = new BaseDeck(DeckColor.YELLOW, 30);
    blueDeck = new BaseDeck(DeckColor.BLUE, 20);
    redL1Deck = new BaseDeck(DeckColor.REDL1, 10);
    redL2Deck = new BaseDeck(DeckColor.REDL2, 10);
    redL3Deck = new BaseDeck(DeckColor.REDL3, 10);

    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(greenDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(yellowDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(blueDeck));
    cardSlots.add(new CardSlot(redL1Deck));
    cardSlots.add(new CardSlot(redL1Deck));
    cardSlots.add(new CardSlot(redL2Deck));
    cardSlots.add(new CardSlot(redL2Deck));
    cardSlots.add(new CardSlot(redL3Deck));
    cardSlots.add(new CardSlot(redL3Deck));

    //Adding 3 cities randomly on board without duplication.
    while (citySlots.size() != 3) {
      City currCity = CityGenerator.generateRandomCity();
      boolean contains = false;
      for (CitySlot citySlot : citySlots) {
        if (citySlot.cityInSlot().equals(currCity)) {
          contains = true;
          break;
        }
      }
      if (!contains) {
        citySlots.add(new CitySlot(currCity));
      }
    }


    List<Noble> nobles = Noble.getNobles(playersToNobleAmount(players));

    for (Noble noble : nobles) {
      nobleSlots.add(new NobleSlot(noble));
    }

    while (nobleSlots.size() < 5) {
      nobleSlots.add(new NobleSlot());
    }

    for (CardSlot slot : cardSlots) {
      slot.refill();
    }

    whiteTokenAmount = playersToTokenAmount(players);
    blueTokenAmount = playersToTokenAmount(players);
    greenTokenAmount = playersToTokenAmount(players);
    redTokenAmount = playersToTokenAmount(players);
    blackTokenAmount = playersToTokenAmount(players);
    goldTokenAmount = 5;


  }


  /**
   * Board constructor when creating a game from a previously saved game.
   *
   * @param players the number of players in the game
   * @param save    the saved game
   */
  public Board(int players, Savegame save) {
    assert players > 1 && players < 5;

    // Initialize decks
    greenDeck = new BaseDeck(DeckColor.GREEN, save.getGreenDeck());
    yellowDeck = new BaseDeck(DeckColor.YELLOW, save.getYellowDeck());
    blueDeck = new BaseDeck(DeckColor.BLUE, save.getBlueDeck());
    redL1Deck = new BaseDeck(DeckColor.REDL1, save.getRedL1Deck());
    redL2Deck = new BaseDeck(DeckColor.REDL2, save.getRedL2Deck());
    redL3Deck = new BaseDeck(DeckColor.REDL3, save.getRedL3Deck());

    // Initialize card slots
    for (int i = 0; i < 4; i++) {
      if (save.getCardsOnBoard().get(i).equals("Empty")) {
        cardSlots.add(new CardSlot(greenDeck));
        continue;
      }
      BaseCard card = new BaseCard(save.getCardsOnBoard().get(i), DeckColor.GREEN);
      cardSlots.add(new CardSlot(greenDeck, card));
    }
    for (int i = 4; i < 8; i++) {
      if (save.getCardsOnBoard().get(i).equals("Empty")) {
        cardSlots.add(new CardSlot(yellowDeck));
        continue;
      }
      BaseCard card = new BaseCard(save.getCardsOnBoard().get(i), DeckColor.YELLOW);
      cardSlots.add(new CardSlot(yellowDeck, card));
    }
    for (int i = 8; i < 12; i++) {
      if (save.getCardsOnBoard().get(i).equals("Empty")) {
        cardSlots.add(new CardSlot(blueDeck));
        continue;
      }
      BaseCard card = new BaseCard(save.getCardsOnBoard().get(i), DeckColor.BLUE);
      cardSlots.add(new CardSlot(blueDeck, card));
    }
    for (int i = 12; i < 14; i++) {
      if (save.getCardsOnBoard().get(i).equals("Empty")) {
        cardSlots.add(new CardSlot(redL1Deck));
        continue;
      }
      OrientCard card = OrientCard.getOrientCard(save.getCardsOnBoard().get(i), DeckColor.REDL1);
      cardSlots.add(new CardSlot(redL1Deck, card));
    }
    for (int i = 14; i < 16; i++) {
      if (save.getCardsOnBoard().get(i).equals("Empty")) {
        cardSlots.add(new CardSlot(redL2Deck));
        continue;
      }
      OrientCard card = OrientCard.getOrientCard(save.getCardsOnBoard().get(i), DeckColor.REDL2);
      cardSlots.add(new CardSlot(redL2Deck, card));
    }
    for (int i = 16; i < 18; i++) {
      if (save.getCardsOnBoard().get(i).equals("Empty")) {
        cardSlots.add(new CardSlot(redL3Deck));
        continue;
      }
      OrientCard card = OrientCard.getOrientCard(save.getCardsOnBoard().get(i), DeckColor.REDL3);
      cardSlots.add(new CardSlot(redL3Deck, card));
    }

    // Initialize noble slots
    for (String noble : save.getNoblesOnBoard()) {
      if (noble.equals("Empty")) {
        nobleSlots.add(new NobleSlot());
      } else {
        Noble newNoble = new Noble(noble);
        nobleSlots.add(new NobleSlot(newNoble));
      }
    }

    //Initialize city slots
    for (String city : save.getCitiesOnBoard()) {
      if (city.equals("-1")) {
        citySlots.add(new CitySlot());
      } else {
        citySlots.add(new CitySlot(CityGenerator.getCityFromNumber(Integer.parseInt(city))));
      }
    }

    // Initialize tokens
    whiteTokenAmount = save.getWhiteTokenAmount();
    blueTokenAmount = save.getBlueTokenAmount();
    greenTokenAmount = save.getGreenTokenAmount();
    redTokenAmount = save.getRedTokenAmount();
    blackTokenAmount = save.getBlackTokenAmount();
    goldTokenAmount = save.getGoldTokenAmount();

  }

  /**
   * A strategy type of method that just redistributes it's work to the already existing methods.
   *
   * @param color the integer representation of the color
   * @return the deck of the input color
   */

  public BaseDeck getDeck(int color) {
    assert (color < 7 && color > 0);
    switch (color) {
      case (1):
        return getGreenDeck();
      case (2):
        return getYellowDeck();
      case (3):
        return getBlueDeck();
      case (4):
        return getRedL1Deck();
      case (5):
        return getRedL2Deck();
      case (6):
        return getRedL3Deck();
      default:
        throw new IllegalStateException("color out of range");
    }


  }

  /**
   * Getter for the green deck that is on the board.
   *
   * @return green deck.
   */
  public BaseDeck getGreenDeck() {
    return greenDeck;
  }

  /**
   * Getter for the yellow deck that is on the board.
   *
   * @return yellow deck
   */
  public BaseDeck getYellowDeck() {
    return yellowDeck;
  }

  /**
   * Getter for the blue deck that is on the board.
   *
   * @return blue deck
   */
  public BaseDeck getBlueDeck() {
    return blueDeck;
  }

  /**
   * Getter for the red level 1 deck that is on the board.
   *
   * @return green deck.
   */
  public BaseDeck getRedL1Deck() {
    return redL1Deck;
  }

  /**
   * Getter for the red level 2 deck that is on the board.
   *
   * @return yellow deck
   */
  public BaseDeck getRedL2Deck() {
    return redL2Deck;
  }

  /**
   * Getter for the red level 3 deck that is on the board.
   *
   * @return blue deck
   */
  public BaseDeck getRedL3Deck() {
    return redL3Deck;
  }

  /**
   * Getter for the list containing all the card slots on the board.
   *
   * @return list of card slots
   */
  public List<CardSlot> getCardSlots() {
    return cardSlots;
  }

  /**
   * Getter for the list containing the nobles slots on the board.
   *
   * @return list of nobles
   */
  public List<NobleSlot> getNobleSlots() {
    return nobleSlots;
  }

  /**
   * Getter for the list containing the city slots on the board.
   *
   * @return list of city slots.
   */
  public List<CitySlot> getCitySlots() {
    return citySlots;
  }

  /**
   * Getter for the amount of white tokens on the board.
   *
   * @return white token amount
   */
  public int getWhiteTokenAmount() {
    return whiteTokenAmount;
  }

  /**
   * Getter for the amount of blue tokens on the board.
   *
   * @return blue token amount
   */
  public int getBlueTokenAmount() {
    return blueTokenAmount;
  }

  /**
   * Getter for the amount of green tokens on the board.
   *
   * @return green token amount
   */
  public int getGreenTokenAmount() {
    return greenTokenAmount;
  }

  /**
   * Getter for the amount of red tokens on the board.
   *
   * @return red token amount
   */
  public int getRedTokenAmount() {
    return redTokenAmount;
  }

  /**
   * Getter for the amount of black tokens on the board.
   *
   * @return black token amount
   */
  public int getBlackTokenAmount() {
    return blackTokenAmount;
  }

  /**
   * Getter for the amount of gold tokens on the board.
   *
   * @return gold token amount
   */
  public int getGoldTokenAmount() {
    return goldTokenAmount;
  }

  /**
   * Reduces the amount of tokens on the board of a particular color.
   *
   * @param color  the color of tokens to remove
   * @param number the amount of tokens to remove
   */
  public void reduceColorToken(int color, int number) {
    if (color == 0) {
      whiteTokenAmount -= number;
    } else if (color == 1) {
      blueTokenAmount -= number;
    } else if (color == 2) {
      greenTokenAmount -= number;
    } else if (color == 3) {
      redTokenAmount -= number;
    } else if (color == 4) {
      blackTokenAmount -= number;
    } else if (color == 5) {
      goldTokenAmount -= number;
    }
  }

  /**
   * Increase the amount of tokens on the board of a particular color.
   *
   * @param color  the color of the tokens to increase
   * @param number the amount of tokens to increase
   */
  public void addColorToken(int color, int number) {
    if (color == 0) {
      whiteTokenAmount += number;
    } else if (color == 1) {
      blueTokenAmount += number;
    } else if (color == 2) {
      greenTokenAmount += number;
    } else if (color == 3) {
      redTokenAmount += number;
    } else if (color == 4) {
      blackTokenAmount += number;
    } else if (color == 5) {
      goldTokenAmount += number;
    }
  }

  private int playersToTokenAmount(int players) {
    assert players > 1 && players < 5;
    if (players == 2) {
      return 4;
    } else if (players == 3) {
      return 5;
    } else {
      return 7;
    }
  }

  private int playersToNobleAmount(int players) {
    assert players > 1 && players < 5;
    if (players == 2) {
      return 3;
    } else if (players == 3) {
      return 4;
    } else {
      return 5;
    }
  }

  /**
   * Gets the token amount of a particular color.
   *
   * @param color the color of the token
   * @return the amount of tokens of the specified color
   */
  public int getTokenAmount(int color) {
    return switch (color) {
      case 0 -> whiteTokenAmount;
      case 1 -> blueTokenAmount;
      case 2 -> greenTokenAmount;
      case 3 -> redTokenAmount;
      case 4 -> blackTokenAmount;
      case 5 -> goldTokenAmount;
      default -> {
        throw new IllegalStateException("Invalid token color");
      }
    };
  }

}
