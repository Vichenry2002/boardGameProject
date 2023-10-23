package splendor.client;

/**
 * Class used to store game parameters from a session when requesting the list of sessions from the
 * Lobby Service. Field names must match those on the Lobby Service.
 */
public class GameParameters {

  public String location;
  public int maxSessionPlayers;
  public int minSessionPlayers;
  public String name;
  public String webSupport;


}
