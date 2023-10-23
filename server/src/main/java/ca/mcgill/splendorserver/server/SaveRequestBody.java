package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.Extensions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class sent to the lobby service as a json to add a saved game.
 */
public class SaveRequestBody {

  /**
   * Name of the game service.
   */
  public String gamename;
  /**
   * Player names.
   */
  public List<String> players = new ArrayList<>();
  /**
   * Id of the saved game.
   */
  public String savegameid;

  /**
   * Constructor for SaveRequestBody class.
   *
   * @param gamename game service name
   * @param players names of the players in the game
   * @param savegameid id of the saved game
   */
  public SaveRequestBody(String gamename, List<String> players, String savegameid) {
    this.gamename = gamename;
    this.players = players;
    this.savegameid = savegameid;
  }

  /**
   * Constructor for SaveRequestBody class from a Savegame object.
   *
   * @param save savegame object
   * @param id id of the saved game
   */
  public SaveRequestBody(Savegame save, String id) {
    Extensions extensions = save.getExtensions();
    switch (extensions) {
      case ORIENT -> gamename =  "splendor_orient";
      case ORIENT_CITIES -> gamename = "splendor_cities";
      case ORIENT_TRADING -> gamename = "splendor_trading";
      default -> throw new IllegalStateException("could not create save request");
    }

    for (String p : save.getPlayerNames().values()) {
      this.players.add(p);
    }

    savegameid = id;
  }

}
