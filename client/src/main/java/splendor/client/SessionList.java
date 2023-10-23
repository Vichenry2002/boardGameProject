package splendor.client;

import java.util.Map;

/**
 * Class used to store the list of sessions when requesting the list of sessions from the
 * Lobby Service. Field names must match those on the Lobby Service.
 */
public class SessionList {

  public Map<Long, Session> sessions;

}
