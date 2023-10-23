package splendor.gameelements;

import java.util.List;

public class BaseDeck {

  private final DeckColor deckColor;
  private List<BaseCard> cards;


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

  public BaseCard draw() {
    assert !isEmpty();
    return cards.remove(0);
  }

  public DeckColor getDeckColor() {
    return deckColor;
  }

  public int getSize() {
    return cards.size();
  }

  public boolean isEmpty() {
    return cards.size() == 0;
  }


}
