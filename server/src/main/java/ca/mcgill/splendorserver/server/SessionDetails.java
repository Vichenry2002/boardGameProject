package ca.mcgill.splendorserver.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the details of a session received from the Lobby Service when launching a game.
 * Field names must match those in the body of the gameid put request?
 */
public class SessionDetails {
  //Field names must match those in the LauncherInfo class in the Lobby Service?

  private String gameServer;
  private String creator;
  private String savegame = "";
  private List<PlayerDetails> players = new ArrayList<>();

  /**
   * Constructor for the class containing the details of a session to be launched.
   * Used when launching a new game.
   *
   * @param gameName name of the game service
   * @param playerDetails list containing the details of all players in the session/game
   * @param creatorName the name of the player that created the current session/game
   */
  public SessionDetails(String gameName,
                        List<PlayerDetails> playerDetails,
                        String creatorName) {
    gameServer = gameName;
    players.addAll(playerDetails);
    creator = creatorName;
  }

  private SessionDetails() {

  }

  /**
   * Constructor for the class containing the details of a session to be launched.
   * Used when starting a game from a previously saved game.
   *
   * @param gameName name of the game service
   * @param playerDetails list containing the details of all players in the session/game
   * @param creatorName the name of the player that created the current session/game
   * @param save the id of the saved game from which to start a game
   */
  public SessionDetails(String gameName,
                        List<PlayerDetails> playerDetails,
                        String creatorName, String save) {
    gameServer = gameName;
    players.addAll(playerDetails);
    creator = creatorName;
    savegame = save;
  }

  /**
   * Getter for the id of the saved game from which this game was created.
   *
   * @return id of saved game if there was one, or an empty string otherwise
   */
  public String getSavegame() {
    return savegame;
  }

  /**
   * Getter for the list containing the details of the players in this game.
   *
   * @return list of players
   */
  public List<PlayerDetails> getPlayers() {
    return players;
  }
}
