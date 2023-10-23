package ca.mcgill.splendorserver.gameelements;

import java.util.Optional;

/**
 * Card slot class which contains either 0 or 1 card.
 */
public class CardSlot {
  /**
   * Card.
   */
  protected Optional<BaseCard> card = Optional.empty();
  /**
   * Deck.
   */
  protected BaseDeck deck;

  /**
   * Constructor for card slot class.
   *
   * @param paramDeck Deck from where to refill the card slot
   */
  public CardSlot(BaseDeck paramDeck) {
    deck = paramDeck;
  }

  /**
   * Constructor for card slot class.
   *
   * @param paramDeck Deck from where to refill the card slot
   * @param newCard the card to add to the slot
   */
  public CardSlot(BaseDeck paramDeck, BaseCard newCard) {
    deck = paramDeck;
    card = Optional.of(newCard);
  }

  /**
   * Check if there is a card in the card slot.
   *
   * @return true if there is a card, false if there isn't
   */
  public boolean hasCard() {
    return card.isPresent();
  }

  /**
   * Removes the card in the card slot.
   *
   * @return the card that was in the card slot
   */
  public BaseCard remove() {
    assert card.isPresent();
    BaseCard currentCard = card.get();
    card = Optional.empty();
    return currentCard;
  }

  /**
   * Draw a card from the deck associated with this card slot and put it in the slot.
   */
  public void refill() {
    assert (card.isEmpty());
    if (!deck.isEmpty()) {
      card = Optional.of(deck.draw());
    }
  }

  /**
   * Get the card in the card slot but do not remove it.
   *
   * @return the card in the card slot
   */
  public BaseCard cardInSlot() {
    assert card.isPresent();
    return card.get(); //.orElse(null);
  }

  /**
   * TODO.
   *
   * @return TODO
   */
  public String getCardString() {
    if (card.isPresent()) {
      return card.get().toString();
    } else {
      return "Empty";
    }
  }




}
