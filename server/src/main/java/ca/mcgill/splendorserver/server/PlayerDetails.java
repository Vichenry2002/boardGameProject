package ca.mcgill.splendorserver.server;

/**
 * Contains the details of a player received from the Lobby Service when launching a game.
 * Field names must match those in the body of the gameid put request?
 */
public class PlayerDetails {
  private String name;
  private String preferredColour;

  /**
   * Constructor for the class containing the details of a player obtained from the lobby service.
   *
   * @param playerName the name of the player
   * @param colour the preferred colour of the player
   */
  public PlayerDetails(String playerName, String colour) {
    name = playerName;
    preferredColour = colour;
  }

  private PlayerDetails() {

  }

  /**
   * Getter for the name of the player.
   *
   * @return player name
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the preferred colour of the player.
   *
   * @return preferred colour
   */
  public String getPreferredColour() {
    return preferredColour;
  }
}
