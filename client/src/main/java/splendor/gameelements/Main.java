package splendor.gameelements;

/**
 * Only for testing, not important.
 */

public class Main {

  /**
   * TODO add description.
   *
   * @param args TODO add description
   */
  public static void main(String[] args) {
    BaseDeck deck = new BaseDeck(DeckColor.GREEN, 4);
    while (!deck.isEmpty()) {
      BaseCard nextCard = deck.draw();
      System.out.print(nextCard.getWhiteCost());
      System.out.print(nextCard.getBlueCost());
      System.out.print(nextCard.getGreenCost());
      System.out.print(nextCard.getRedCost());
      System.out.println(nextCard.getBlackCost());

    }
  }
}
