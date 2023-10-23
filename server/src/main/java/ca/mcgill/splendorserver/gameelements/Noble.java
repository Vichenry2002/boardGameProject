package ca.mcgill.splendorserver.gameelements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Noble class.
 */
public class Noble {

  private final List<Integer> costs;
  private final String filePath;
  private final int prestigePoints;
  private final String nobleString;


  /**
   * Constructor for Noble.
   *
   * @param whiteCost the amount of white bonuses required to take the noble
   * @param blueCost the amount of blue bonuses required to take the noble
   * @param greenCost the amount of green bonuses required to take the noble
   * @param redCost the amount of red bonuses required to take the noble
   * @param blackCost the amount of black bonuses required to take the noble
   * @param points the amount of prestige points given by the noble
   * @param path path to the noble image
   */
  public Noble(int whiteCost, int blueCost, int greenCost, int redCost,
               int blackCost, int points, String path) {

    costs = Arrays.asList(whiteCost,
        blueCost,
        greenCost,
        redCost,
        blackCost);
    filePath = path;
    prestigePoints = points;
    nobleString = String.valueOf(whiteCost).concat(",")
        .concat(String.valueOf(blueCost)).concat(",")
        .concat(String.valueOf(greenCost)).concat(",")
        .concat(String.valueOf(redCost)).concat(",")
        .concat(String.valueOf(blackCost)).concat(",")
        .concat(String.valueOf(points)).concat(",")
        .concat(path);

  }

  /**
   * Constructor to create a noble from a string containing the noble data.
   *
   * @param nobleData the string containing the noble data
   */
  public Noble(String nobleData) {
    String[] data = nobleData.split(",");

    costs = Arrays.asList(Integer.parseInt(data[0]),
        Integer.parseInt(data[1]),
        Integer.parseInt(data[2]),
        Integer.parseInt(data[3]),
        Integer.parseInt(data[4]));
    prestigePoints = Integer.parseInt(data[5]);
    filePath = data[6];
    nobleString = nobleData;

  }

  /**
   * Getter for the amount of white bonuses required to take the noble.
   *
   * @return white cost of the noble
   */
  public int getWhiteCost() {
    return costs.get(0);
  }

  /**
   * Getter for the amount of blue bonuses required to take the noble.
   *
   * @return blue cost of the noble
   */
  public int getBlueCost() {
    return costs.get(1);
  }

  /**
   * Getter for the amount of green bonuses required to take the noble.
   *
   * @return green cost of the noble
   */
  public int getGreenCost() {
    return costs.get(2);
  }

  /**
   * Getter for the amount of red bonuses required to take the noble.
   *
   * @return red cost of the noble
   */
  public int getRedCost() {
    return costs.get(3);
  }

  /**
   * Getter for the amount of black bonuses required to take the noble.
   *
   * @return black cost of the noble
   */
  public int getBlackCost() {
    return costs.get(4);
  }

  /**
   * Getter for the amount of prestige points given by the noble.
   *
   * @return prestige points given by the noble
   */
  public int getPrestigePoints() {
    return prestigePoints;
  }

  /**
   * Getter for the path to the image of the noble.
   *
   * @return image path
   */
  public String getPath() {
    return filePath;
  }

  /**
   * Returns a string containing the data of the noble.
   *
   * @return string containing the data of the noble
   */
  public String toString() {
    return nobleString;
  }

  /**
   * Method to get a list of nobles.
   *
   * @param amount the amount of nobles wanted in the list
   * @return list of nobles
   */
  public static List<Noble> getNobles(int amount) {
    List<Noble> nobles = new ArrayList<>();

    for (String noble : NobleData.nobles) {
      Noble nextNoble = new Noble(noble);
      nobles.add(nextNoble);

    }

    Collections.shuffle(nobles);
    nobles.subList(0, 17 - amount).clear();

    return  nobles;
  }






}
    