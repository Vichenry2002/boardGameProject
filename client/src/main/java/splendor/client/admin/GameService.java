package splendor.client.admin;

/**
 * Class to store information about a game service obtained from the Lobby Service.
 */
public class GameService {

  public String name;
  public String location;
  public String displayName;
  public int maxSessionPlayers;
  public int minSessionPlayers;
  public String webSupport;

  /**
   * Contructor for GameService class.
   *
   * @param name the name of the game service
   * @param location the ip address of the game service
   * @param displayName the display name of the game service
   * @param maxSessionPlayers the maximum number of players allowed in a game
   * @param minSessionPlayers the minimum number of players allowed in a game
   * @param webSupport web support (not used)
   */
  public GameService(String name, String location, String displayName, int maxSessionPlayers,
                     int minSessionPlayers, String webSupport) {
    this.displayName = displayName;
    this.name = name;
    this.minSessionPlayers = minSessionPlayers;
    this.maxSessionPlayers = maxSessionPlayers;
    this.webSupport = webSupport;
    this.location = location;
  }

  /**
   * Getter for the game service name.
   *
   * @return game service name
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for the game service name.
   *
   * @param name game service name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for the location service name.
   *
   * @return game service location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Setter for the game service location.
   *
   * @param location game service location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Getter for the game service display name.
   *
   * @return game service display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Setter for the game service display name.
   *
   * @param displayName game service display name
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Getter for the game service max players.
   *
   * @return max number of players allowed
   */
  public int getMaxSessionPlayers() {
    return maxSessionPlayers;
  }

  /**
   * Setter for the game service max players.
   *
   * @param maxSessionPlayers max number of players allowed
   */
  public void setMaxSessionPlayers(int maxSessionPlayers) {
    this.maxSessionPlayers = maxSessionPlayers;
  }

  /**
   * Getter for the game service min players.
   *
   * @return min number of players allowed
   */
  public int getMinSessionPlayers() {
    return minSessionPlayers;
  }

  /**
   * Setter for the game service min players.
   *
   * @param minSessionPlayers min number of players allowed
   */
  public void setMinSessionPlayers(int minSessionPlayers) {
    this.minSessionPlayers = minSessionPlayers;
  }

  /**
   * Getter for the game service webSupport.
   *
   * @return false
   */
  public String getWebSupport() {
    return webSupport;
  }

  /**
   * Setter for the game service webSupport.
   *
   * @param webSupport false
   */
  public void setWebSupport(String webSupport) {
    this.webSupport = webSupport;
  }

}
