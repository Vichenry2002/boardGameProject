package ca.mcgill.splendorserver.gameelements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generate random city.
 */
public class CityGenerator {

  private CityGenerator() {

  }

  /**
  * Generate random city.
  *
  * @return random city
  */
  //114 prestige points, 4 green bonuses
  //   * and 4 bonuses in any other color.
  public static City generateRandomCity() {
    List<City> cities = new ArrayList<>();
    cities.add(new City(11, 3, 3, 3, 0, 3, 0, 1));
    cities.add(new City(11, 3, 3, 3, 3, 0, 0, 2));
    cities.add(new City(12, 0, 0, 0, 0, 0, 6, 3));
    cities.add(new City(13, 2, 2, 2, 2, 2, 0, 4));
    cities.add(new City(13, 4, 0, 3, 0, 0, 0, 5));
    cities.add(new City(13, 0, 3, 0, 0, 4, 0, 6));
    cities.add(new City(13, 0, 0, 0, 4, 3, 0, 7));
    cities.add(new City(13, 0, 0, 4, 3, 0, 0, 8));
    cities.add(new City(13, 3, 4, 0, 0, 0, 0, 9));
    cities.add(new City(14, 2, 2, 2, 1, 1, 0, 10));
    cities.add(new City(14, 0, 0, 0, 4, 0, 4, 11));
    cities.add(new City(15, 0, 0, 0, 0, 0, 5, 12));
    cities.add(new City(16, 1, 1, 1, 1, 1, 0, 13));
    cities.add(new City(17, 0, 0, 0, 0, 0, 0, 14));

    Random random = new Random();
    int index = random.nextInt(14);
    return cities.get(index);
  }

  /**
   * Get city from city number.
   *
   * @param num is the city number
   * @return city
   */
  public static City getCityFromNumber(int num) {
    List<City> cities = new ArrayList<>();
    cities.add(new City(11, 3, 3, 3, 0, 3, 0, 1));
    cities.add(new City(11, 3, 3, 3, 3, 0, 0, 2));
    cities.add(new City(12, 0, 0, 0, 0, 0, 6, 3));
    cities.add(new City(13, 2, 2, 2, 2, 2, 0, 4));
    cities.add(new City(13, 4, 0, 3, 0, 0, 0, 5));
    cities.add(new City(13, 0, 3, 0, 0, 4, 0, 6));
    cities.add(new City(13, 0, 0, 0, 4, 3, 0, 7));
    cities.add(new City(13, 0, 0, 4, 3, 0, 0, 8));
    cities.add(new City(13, 3, 4, 0, 0, 0, 0, 9));
    cities.add(new City(14, 2, 2, 2, 1, 1, 0, 10));
    cities.add(new City(14, 0, 0, 0, 4, 0, 4, 11));
    cities.add(new City(15, 0, 0, 0, 0, 0, 5, 12));
    cities.add(new City(16, 1, 1, 1, 1, 1, 0, 13));
    cities.add(new City(17, 0, 0, 0, 0, 0, 0, 14));

    for (City city : cities) {
      if (city.getCityNumber() == num) {
        return city;
      }
    }

    return cities.get(0);
  }
}

