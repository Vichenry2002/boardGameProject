package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.Extensions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * SaveGameManager class to manage the saved games. Handles adding and deleting saved games which
 * persist after restarting the server.
 */
public class SaveGameManager {

  private static SaveGameManager instance;
  private final String parentDirectory = "savedgames";
  private final List<String> savedGameIds = new ArrayList<>();

  /**
   * Method to get the SaveGameManager instance.
   *
   * @return unique instance of the SaveGameManager class
   * @throws IOException if something goes wrong when reading from files.
   */
  public static SaveGameManager getInstance() throws IOException {
    if (instance == null) {
      instance = new SaveGameManager();
    }
    return instance;
  }

  /**
   * Constructor for the saveGameManager class. Private to ensure that there is only one instance
   * of the saveGameManager class.
   *
   * @throws IOException if something goes wrong when reading from files.
   */
  private SaveGameManager() throws IOException {
    loadIdsFromFileSystem();
  }

  /**
   * Adds all valid saved game files in the savegame directory to the list of saved game ids.
   * Called only once from the constructor.
   *
   * @throws IOException if something goes wrong when reading from files.
   */
  private void loadIdsFromFileSystem() throws IOException {

    // Name of the directory where saved games are stored
    File dir = new File(parentDirectory);

    // List of files in the directory
    List<File> files = (List<File>) FileUtils.listFiles(dir,
        TrueFileFilter.INSTANCE,
        TrueFileFilter.INSTANCE);

    // Iterate over the files in the directory
    for (File file : files) {

      // Get the name of the file which corresponds to a saved game id
      String id = file.getName();

      // Check if the file is indeed a saved game and if so add it to the list of ids
      try {
        // Read the contents of the file to a string
        String content = FileUtils.readFileToString(file);

        // Convert the string to a json object
        JsonObject savedGameJson = JsonParser.parseString(content).getAsJsonObject();

        // Convert the json object to a savegame object
        Savegame save = new Gson().fromJson(content, Savegame.class);

        savedGameIds.add(id);

        SaveRequestBody body = new SaveRequestBody(save, id);
        SplendorGameController.sendSaveRequest(body);

        System.out.println("Created saved game from file: " + id);

      } catch (Exception e) {
        System.out.println("Failed to create saved game from file: " + id);
      }
    }

  }


  /**
   * Saves a game from a Savegame object. Generates a random unique id and adds it to the saved
   * games list. Creates a file in the savegames directory with the same name as the id of the
   * saved game.
   *
   * @param save Savegame object from which to create a saved game
   * @return id of the saved game
   * @throws IOException if something goes wrong when writing to the file
   */
  public String saveGame(Savegame save) throws IOException {
    String id = newId();

    // Create a file with the save game information and save it to disk.
    File newSave = new File(parentDirectory, id);
    String saveJson = new Gson().toJson(save);
    FileUtils.writeStringToFile(newSave, saveJson);

    savedGameIds.add(id);

    // Send a request to the lobby service to add the save game.
    SaveRequestBody body = new SaveRequestBody(save, id);
    SplendorGameController.sendSaveRequest(body);

    return id;

  }

  /**
   * Saves a game with the provided id. The id must be a string containing only letters and numbers.
   *
   * @param save the SaveGame object from which to save the game.
   * @param id the id to use for the saved game
   * @return true if the game is successfully saved and the id is valid, false otherwise
   * @throws IOException if something goes wrong when writing to disk
   */
  public boolean saveGame(Savegame save, String id) throws IOException {
    // Check that the id is valid.
    // The id must not be an existing id.
    if (existingId(id)) {
      return false;
    }
    // The id should contain at least 3 characters.
    if (id.length() < 3) {
      return false;
    }
    // The id should only contain letters and numbers.
    if (!StringUtils.isAlphanumeric(id)) {
      return false;
    }

    // Create a file with the save game information and save it to disk.
    File newSave = new File(parentDirectory, id);
    String saveJson = new Gson().toJson(save);
    FileUtils.writeStringToFile(newSave, saveJson);

    savedGameIds.add(id);

    // Send a request to the lobby service to add the save game.
    SaveRequestBody body = new SaveRequestBody(save, id);
    SplendorGameController.sendSaveRequest(body);

    return true;

  }

  /**
   * Deletes the saved game with the provided id if it exists. Removes it from the saved games
   * list and deletes the corresponding file in the savedgames folder.
   *
   * @param id the id of the game to delete
   * @throws IOException if something goes wrong when reading from disk
   */
  public void deleteSave(String id) throws IOException {

    if (!savedGameIds.contains(id)) {
      return;
    }

    Savegame save = getSaveFromId(id);
    Extensions extensions = save.getExtensions();
    String gameService;

    switch (extensions) {
      case ORIENT -> gameService = "splendor_orient";
      case ORIENT_CITIES -> gameService = "splendor_cities";
      case ORIENT_TRADING -> gameService = "splendor_trading";
      default -> throw new IllegalStateException("Extensions not supported");
    }

    savedGameIds.remove(id);

    File saveToDelete = new File(parentDirectory, id);
    FileUtils.deleteQuietly(saveToDelete);

    SplendorGameController.sendDeleteSaveRequest(id, gameService);

  }

  /**
   * Generates a new unique id.
   *
   * @return Unique id
   */
  private String newId() {

    Random random = new Random();
    int number = Math.abs(random.nextInt());

    while (existingId(String.valueOf(number))) {
      number = Math.abs(random.nextInt());
    }

    return String.valueOf(number);
  }

  /**
   * Checks if the given id is an existing save game id.
   *
   * @param id the id to check
   * @return true if the id exists as a saved game, false otherwise
   */
  public boolean existingId(String id) {
    return savedGameIds.contains(id);
  }

  /**
   * Gets a Savegame object from the provided id. The id must be an existing save game id.
   *
   * @param id the id of the saved game
   * @return Savegame object of the saved game matching the given id
   * @throws IOException if something goes wrong when reading from the file
   */
  public Savegame getSaveFromId(String id) throws IOException {
    assert existingId(id);

    File saveFile = new File(parentDirectory, id);

    String content = FileUtils.readFileToString(saveFile);

    return new Gson().fromJson(content, Savegame.class);

  }

}
