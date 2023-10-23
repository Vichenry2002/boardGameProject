package splendor.gameelements;

import java.util.Optional;

/**
 * Noble slot class which contains either 0 or 1 noble.
 */
public class NobleSlot {

  private Optional<Noble> noble = Optional.empty();

  /**
   * Constructor to create a noble slot containing a noble.
   *
   * @param nobleToAdd noble which will be contained in the slot
   */
  public NobleSlot(Noble nobleToAdd) {
    noble = Optional.of(nobleToAdd);
  }

  /**
   * Constructor to create an empty noble slot, used if there are less than 4 players in a game.
   */
  public NobleSlot() {
    noble = Optional.empty();
  }

  /**
   * Removes the noble in the noble slot.
   *
   * @return the noble that was in the noble slot
   */
  public Noble removeNoble() {
    assert noble.isPresent();
    Noble currentNoble = noble.get();
    noble = Optional.empty();
    return currentNoble;

  }

  /**
   * Get the noble in the noble slot but do not remove it.
   *
   * @return the noble in the noble slot
   */
  public Noble nobleInSlot() {
    assert noble.isPresent();
    return noble.get(); //.orElse(null);
  }

  /**
   * Check if there is a card in the card slot.
   *
   * @return true if there is a card, false if there isn't
   */
  public boolean hasNoble() {
    return noble.isPresent();
  }

  /**
   * Method to get a string of the noble in the slot.
   *
   * @return string of the noble in the slot
   */
  public String getNobleString() {
    if (noble.isPresent()) {
      return noble.get().toString();
    } else {
      return "Empty";
    }
  }

  public void addNoble(Noble newNoble) {
    noble = Optional.of(newNoble);
  }

}
