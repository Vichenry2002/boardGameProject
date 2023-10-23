package splendor.client;

import java.util.List;
import java.util.Map;

/**
 * Class used to store session details when requesting the list of sessions from the
 * Lobby Service. Field names must match those on the Lobby Service.
 */
public class Session {

  public String creator;
  public GameParameters gameParameters;
  public boolean launched;
  public List<String> players;
  public String savegameid;
  public Map<String, String> playerLocations;


}
