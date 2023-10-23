package ca.mcgill.splendorserver.server.gameservicedetails;

/**
 * Abstract class used to store information about the Splendor game services, which will be used
 * by each of the available combinations of extensions.
 * This information will be sent to the Lobby Service when registering.
 */
public abstract class GameServiceDetails {

  /**
   * Location of game.
   */
  //protected String location = "http://host.docker.internal:8080/";
  protected String location = "http://splendor:8080/";

  /**
   * Maximum number of players in a game.
   */
  protected int maxSessionPlayers = 4;

  /**
   * Minimum amount of players required to start a game.
   */
  protected int minSessionPlayers = 2;

  /**
   * Name of the game.
   */
  protected String name = "splendor_";

  /**
   * Display name of the game.
   */
  protected String displayName = "splendor_";

  /**
   * Can be supported on web.
   */
  protected String webSupport = "false";

  /**
   * Getter for the maximum number of players allowed in a Splendor game.
   *
   * @return max number of players (4)
   */
  public int getMaxSessionPlayers() {
    return maxSessionPlayers;
  }

  /**
   * Getter for the minimum number of players allowed in a Splendor game.
   *
   * @return min number of players (2)
   */
  public int getMinSessionPlayers() {
    return minSessionPlayers;
  }

  /**
   * Getter for the name of the game service.
   *
   * @return name of the game service (splendor)
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the display name of the game service.
   *
   * @return display name of the game service (splendor)
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Getter for the location / ip address of the game service.
   *
   * @return server ip address
   */
  public String getLocation() {
    return location;
  }

  /**
   * Check if the service has web support. Don't know what it's for, but it was included in
   * the Lobby Service API documentation, and it is needed to register the game service.
   *
   * @return true
   */
  public boolean isWebSupport() {
    return false;
  }

  /**
   * Javadoc being stupid.
   */
  protected GameServiceDetails() {}

}
