package splendor.gameelements;

import java.util.Optional;

/**
 * TODO add description.
 */
public class CardSlot {

  protected Optional<BaseCard> card = Optional.empty();

  protected BaseDeck deck;

  public CardSlot(BaseDeck paramDeck) {
    deck = paramDeck;
  }

  public boolean hasCard() {
    return card.isPresent();
  }

  /**
   * TODO add description.
   *
   * @return TODO add description
   */
  public BaseCard remove() {
    assert hasCard();
    BaseCard currentCard = card.get();
    card = Optional.empty();
    return currentCard;
  }

  public void refill() {
    assert (card.isEmpty());
    if (!deck.isEmpty()){
      card = Optional.of(deck.draw());
    }
  }

  public BaseCard getCard() {
    assert card.isPresent();
    return card.get();
  }

  public void setCard(BaseCard newCard) {
    card = Optional.of(newCard);
  }

}
