package ca.mcgill.splendorserver.gameelements;

import java.util.ArrayList;
import java.util.List;

/**
 * Deck class for the main game, includes green, yellow and blue cards.
 */
public class BaseDeck {
  private final DeckColor deckColor;
  private final List<BaseCard> cards;

  /**
   * Constructor for BaseDeck.
   *
   * @param paramDeckColor color of the deck
   * @param deckSize number of cards in the deck
   */
  public BaseDeck(DeckColor paramDeckColor, int deckSize) {
    assert paramDeckColor != null;
    if (paramDeckColor == DeckColor.GREEN
        || paramDeckColor == DeckColor.BLUE
        || paramDeckColor == DeckColor.YELLOW) {
      cards = BaseCard.getCards(paramDeckColor, deckSize);
    } else {
      cards = OrientCard.getCards(paramDeckColor, deckSize);
    }

    deckColor = paramDeckColor;
  }

  /**
   * Constructor for BaseDeck from an existing list of cards.
   *
   * @param paramDeckColor color of the deck
   * @param cardsToAdd cards in the deck
   */
  public BaseDeck(DeckColor paramDeckColor, List<String> cardsToAdd) {
    assert paramDeckColor != null;

    cards = new ArrayList<>();

    if (paramDeckColor == DeckColor.GREEN
        || paramDeckColor == DeckColor.BLUE
        || paramDeckColor == DeckColor.YELLOW) {

      for (String card : cardsToAdd) {
        cards.add(new BaseCard(card, paramDeckColor));
      }
    } else {

      for (String card : cardsToAdd) {
        cards.add(OrientCard.getOrientCard(card, paramDeckColor));
      }
    }

    deckColor = paramDeckColor;
  }

  /**
   * Draw the top card of the deck and return it.
   *
   * @return the top card of the deck.
   */
  public BaseCard draw() {
    assert !isEmpty();
    return cards.remove(0);
  }

  /**
   * Get the color of the deck.
   *
   * @return deck color.
   */
  public DeckColor getDeckColor() {
    return deckColor;
  }

  /**
   * Check how many cards are left in the deck.
   *
   * @return number of cards left in the deck.
   */
  public int getSize() {
    return cards.size();
  }

  /**
   * Check if the deck is empty.
   *
   * @return true if no cards left in the deck, false if at least 1 card left in the deck
   */
  public boolean isEmpty() {
    return cards.size() == 0;
  }

  /**
   * Returns a list containing the data as strings of all cards in the deck.
   *
   * @return list containing the data as strings of all cards in the deck.
   */
  public List<String> asData() {
    List<String> cardData = new ArrayList<>();

    for (BaseCard card : cards) {
      cardData.add(card.toString());
    }

    return cardData;

  }


}
