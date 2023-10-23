package splendor.client;

import java.util.ArrayList;
import java.util.List;

public class SaveGame {

  public String gamename;
  public List<String> players;
  public String savegameid;

  public String getGamename() {
    return gamename;
  }

  public List<String> getPlayers() {
    return new ArrayList<>(players);
  }

  public String getSavegameid() {
    return savegameid;
  }

}
