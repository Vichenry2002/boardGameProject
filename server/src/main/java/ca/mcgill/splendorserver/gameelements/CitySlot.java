package ca.mcgill.splendorserver.gameelements;

import java.util.Optional;

/**
 * City slot class which contains either 0 or 1 city.
 */
public class CitySlot {
  /**
  * City.
  */
  protected Optional<City> city;

  /**
   * Constructor for city slot class.
   *
   * @param currCity city in the city slot.
   */
  public CitySlot(City currCity) {
    city = Optional.of(currCity);
  }

  /**
   * Constructor for city slot class.
   *
   */
  public CitySlot() {
    city = Optional.empty();
  }

  /**
   * Check if there is a city in the city slot.
   *
   * @return true if there is a city, false if there isn't
   */
  public boolean hasCard() {
    return city.isPresent();
  }

  /**
   * Removes the city in the city slot.
   *
   * @return the city that was in the card slot
   */
  public City remove() {
    assert city.isPresent();
    City currentCity = city.get();
    city = Optional.empty();
    return currentCity;
  }

  /**
   * Get the city in the city slot but do not remove it.
   *
   * @return the city in the city slot
   */
  public City cityInSlot() {
    assert city.isPresent();
    return city.get(); //.orElse(null);
  }

  /**
   * Get the city number of the slot inside.
   *
   * @return Get city number, -1 if no city.
   */
  public int getCityNumber() {
    return city.map(City::getCityNumber).orElse(-1);
  }

}
