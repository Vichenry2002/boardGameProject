package splendor.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to store the list of saved games when requesting the list of saved games from the
 * Lobby Service. Field names must match those on the Lobby Service.
 */
public class SavedGamesList {

  public Map<String, SaveGame> savegames = new HashMap<>();

  public void updateList(SaveGame[] newList) {

    // Add new or updated saved games
    for (SaveGame save : newList) {

      savegames.put(save.savegameid, save);

    }

//    // Remove deleted saved games
//    for (Long key : savegames.keySet()) {
//
//      if (!newList.savegames.containsKey(key)) {
//        savegames.remove(key);
//      }
//
//    }


  }

}
