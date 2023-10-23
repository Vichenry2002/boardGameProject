package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.BaseDeck;
import ca.mcgill.splendorserver.gameelements.DeckColor;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Player;
import ca.mcgill.splendorserver.server.gameservicedetails.CitiesDetails;
import ca.mcgill.splendorserver.server.gameservicedetails.OrientDetails;
import ca.mcgill.splendorserver.server.gameservicedetails.TradingDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import eu.kartoffelquadrat.asyncrestlib.BroadcastContentManager;
import eu.kartoffelquadrat.asyncrestlib.ResponseGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


/**
 * Unique controller to use with spring boot.
 */
@RestController
public class SplendorGameController {

  private static final Map<String, Game> gameMap = new HashMap<>();
  private static final Map<Long, BroadcastContentManager<GameState>> managerMap = new HashMap<>();
  private GameState currentGameState;
  //private static final String lobbyServiceLocation = "http://127.0.0.1:4242";
  private static final String lobbyServiceLocation = "http://lobby:4242";
  private SaveGameManager saveManager;
  static boolean ignoreRequests = true;

  /**
   * Default constructor for SplendorGameController class, do not call.
   */
  public SplendorGameController() {

  }

  /**
   * Only for testing. TODO : remove later
   *
   * @param gameid Only for testing
   * @return Only for testing
   */
  @GetMapping("/splendor_orient/api/games/{gameid}/gamestate/2")
  public GameState getGameState(@PathVariable("gameid") String gameid) {
    //Remove everything here except the last line later
    if (!gameMap.containsKey("12")) {
      List<Player> players = new ArrayList<>();
      Player player1 = new Player("p1", "1", 1);
      players.add(player1);
      players.add(new Player("p2", "2", 2));
      gameMap.put("12", new Game(players, Extensions.ORIENT));
      BaseCard card = new BaseDeck(DeckColor.GREEN, 1).draw();
      player1.getInventory().addCard(card);
    }

    managerMap.put(12L, new BroadcastContentManager<>(
        new GameState(gameMap.get(String.valueOf(12)))));

    return new GameState(gameMap.get(gameid));
  }

  /**
   * REST Get request for /splendor/api/{gameid}/gamestate.
   * If the game does not exist, send a BAD_REQUEST response.
   * If the hash in the parameter is equal to the hash of the current game state, wait until there
   * is a change before sending a response or wait until it times out after 30 seconds.
   * Otherwise, send an HTTP response containing a Json serialization of the game state with all
   * relevant information.
   *
   * @param gameid the id of the game from which to get the game state
   * @param hash has of the previous game state, can be empty
   * @return HTTP response containing the game state if the game exists
   */
  @GetMapping("/splendor_orient/api/games/{gameid}/gamestate")
  public DeferredResult<ResponseEntity<String>> getGameStateLongOrient(
      @PathVariable("gameid") String gameid,
      @RequestParam String hash) {

    if (gameMap.containsKey(gameid)) {
      return ResponseGenerator.getHashBasedUpdate(30000,
          managerMap.get(Long.valueOf(gameid)), hash);
    }
    DeferredResult<ResponseEntity<String>> badRequest = new DeferredResult<>();
    badRequest.setResult(
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with that id"));
    return badRequest;

  }

  /**
   * REST Get request for /splendor_trading/api/{gameid}/gamestate.
   * If the game does not exist, send a BAD_REQUEST response.
   * If the hash in the parameter is equal to the hash of the current game state, wait until there
   * is a change before sending a response or wait until it times out after 30 seconds.
   * Otherwise, send an HTTP response containing a Json serialization of the game state with all
   * relevant information.
   *
   * @param gameid the id of the game from which to get the game state
   * @param hash has of the previous game state, can be empty
   * @return HTTP response containing the game state if the game exists
   */
  @GetMapping("/splendor_trading/api/games/{gameid}/gamestate")
  public DeferredResult<ResponseEntity<String>> getGameStateLongTrading(
      @PathVariable("gameid") String gameid,
      @RequestParam String hash) {

    if (gameMap.containsKey(gameid)) {
      return ResponseGenerator.getHashBasedUpdate(30000,
          managerMap.get(Long.valueOf(gameid)), hash);
    }
    DeferredResult<ResponseEntity<String>> badRequest = new DeferredResult<>();
    badRequest.setResult(
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with that id"));
    return badRequest;

  }

  /**
   * REST Get request for /splendor_cities/api/{gameid}/gamestate.
   * If the game does not exist, send a BAD_REQUEST response.
   * If the hash in the parameter is equal to the hash of the current game state, wait until there
   * is a change before sending a response or wait until it times out after 30 seconds.
   * Otherwise, send an HTTP response containing a Json serialization of the game state with all
   * relevant information.
   *
   * @param gameid the id of the game from which to get the game state
   * @param hash has of the previous game state, can be empty
   * @return HTTP response containing the game state if the game exists
   */
  @GetMapping("/splendor_cities/api/games/{gameid}/gamestate")
  public DeferredResult<ResponseEntity<String>> getGameStateLongCities(
      @PathVariable("gameid") String gameid,
      @RequestParam String hash) {

    if (gameMap.containsKey(gameid)) {
      return ResponseGenerator.getHashBasedUpdate(30000,
          managerMap.get(Long.valueOf(gameid)), hash);
    }
    DeferredResult<ResponseEntity<String>> badRequest = new DeferredResult<>();
    badRequest.setResult(
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with that id"));
    return badRequest;

  }

  @PostConstruct
  private void postConstructMethod() {
    ignoreRequests = false;
    registerGameServiceOrient();
    registerGameServiceCities();
    registerGameServiceTradingPosts();
    startSaveManager();
  }

  private void startSaveManager() {
    try {
      saveManager = SaveGameManager.getInstance();
    } catch (IOException e) {
      System.out.println("Could not start save manager");
    }
  }



  /**
   * REST Put request for /splendor_orient/api/{gameid}.
   * Creates a new game with the id specified in gameid.
   *
   * @param gameid id of the game to create
   * @param gameDetails details about the game to create
   */
  @PutMapping("/splendor_orient/api/games/{gameid}")
  public void putGameIdOrient(@PathVariable long gameid,
                              @RequestBody SessionDetails gameDetails) {
    System.out.println("Game created with id " + gameid);
    List<Player> players = new ArrayList<>();
    players.add(new Player(gameDetails.getPlayers().get(0).getName(),
        gameDetails.getPlayers().get(0).getPreferredColour(), 1));
    players.add(new Player(gameDetails.getPlayers().get(1).getName(),
        gameDetails.getPlayers().get(1).getPreferredColour(), 2));
    if (gameDetails.getPlayers().size() > 2) {
      players.add(new Player(gameDetails.getPlayers().get(2).getName(),
          gameDetails.getPlayers().get(2).getPreferredColour(), 3));
    }
    if (gameDetails.getPlayers().size() > 3) {
      players.add(new Player(gameDetails.getPlayers().get(3).getName(),
          gameDetails.getPlayers().get(3).getPreferredColour(), 4));
    }

    if (gameDetails.getSavegame().equals("")) {
      gameMap.put(String.valueOf(gameid), new Game(players, Extensions.ORIENT));

      managerMap.put(gameid, new BroadcastContentManager<>(
          new GameState(gameMap.get(String.valueOf(gameid)))));

    } else {
      // Start game from a previously saved game
      String saveGameId = gameDetails.getSavegame();

      Savegame save;

      try {
        save = SaveGameManager.getInstance().getSaveFromId(saveGameId);
      } catch (IOException e) {
        System.out.println("Could not start game from save with id: " + saveGameId);
        return;
      }

      Map<String, String> playerNames = save.getPlayerNames();
      List<Player> orderedPlayers = orderPlayers(players, playerNames);

      gameMap.put(String.valueOf(gameid), new Game(orderedPlayers, save));

      managerMap.put(gameid, new BroadcastContentManager<>(
          new GameState(gameMap.get(String.valueOf(gameid)))));

    }
  }

  /**
   * Orders the list of players based on the order of players when it was saved.
   *
   * @param players the players in the game
   * @param playerNames the names of the players in the saved game
   * @return ordered list of players
   */
  private static List<Player> orderPlayers(List<Player> players, Map<String, String> playerNames) {
    List<Player> orderedPlayers = new ArrayList<>();
    for (Player p : players) {
      orderedPlayers.add(null);
    }

    for (Player p1 : players) {
      if (p1.getName().equals(playerNames.get("Player1"))) {
        orderedPlayers.set(0, p1);
        players.remove(p1);
        break;
      }
    }
    for (Player p2 : players) {
      if (p2.getName().equals(playerNames.get("Player2"))) {
        orderedPlayers.set(1, p2);
        players.remove(p2);
        break;
      }
    }
    if (players.size() > 2) {
      for (Player p3 : players) {
        if (p3.getName().equals(playerNames.get("Player3"))) {
          orderedPlayers.set(2, p3);
          players.remove(p3);
          break;
        }
      }
    }
    if (players.size() > 3) {
      for (Player p4 : players) {
        if (p4.getName().equals(playerNames.get("Player4"))) {
          orderedPlayers.set(3, p4);
          players.remove(p4);
          break;
        }
      }
    }

    for (Player p : players) {
      for (int i = 0; i < orderedPlayers.size(); i++) {
        if (orderedPlayers.get(i) == null) {
          orderedPlayers.set(i, p);
          break;
        }
      }
    }

    return orderedPlayers;
  }


  /**
   * REST Put request for /splendor_trading/api/{gameid}.
   * Creates a new game with the id specified in gameid.
   *
   * @param gameid id of the game to create
   * @param gameDetails details about the game to create
   */
  @PutMapping("/splendor_trading/api/games/{gameid}")
  public void putGameIdTrading(@PathVariable long gameid,
                              @RequestBody SessionDetails gameDetails) {
    System.out.println("Game created with id " + gameid);
    List<Player> players = new ArrayList<>();
    players.add(new Player(gameDetails.getPlayers().get(0).getName(),
        gameDetails.getPlayers().get(0).getPreferredColour(), 1));
    players.add(new Player(gameDetails.getPlayers().get(1).getName(),
        gameDetails.getPlayers().get(1).getPreferredColour(), 2));
    if (gameDetails.getPlayers().size() > 2) {
      players.add(new Player(gameDetails.getPlayers().get(2).getName(),
          gameDetails.getPlayers().get(2).getPreferredColour(), 3));
    }
    if (gameDetails.getPlayers().size() > 3) {
      players.add(new Player(gameDetails.getPlayers().get(3).getName(),
          gameDetails.getPlayers().get(3).getPreferredColour(), 4));
    }

    if (gameDetails.getSavegame().equals("")) {
      gameMap.put(String.valueOf(gameid), new Game(players, Extensions.ORIENT_TRADING));

      managerMap.put(gameid, new BroadcastContentManager<>(
          new GameState(gameMap.get(String.valueOf(gameid)))));

    } else {
      // Start game from a previously saved game
      String saveGameId = gameDetails.getSavegame();

      Savegame save;

      try {
        save = SaveGameManager.getInstance().getSaveFromId(saveGameId);
      } catch (IOException e) {
        System.out.println("Could not start game from save with id: " + saveGameId);
        return;
      }

      Map<String, String> playerNames = save.getPlayerNames();
      List<Player> orderedPlayers = orderPlayers(players, playerNames);

      gameMap.put(String.valueOf(gameid), new Game(orderedPlayers, save));

      managerMap.put(gameid, new BroadcastContentManager<>(
          new GameState(gameMap.get(String.valueOf(gameid)))));

    }
  }

  /**
   * REST Put request for /splendor_cities/api/{gameid}.
   * Creates a new game with the id specified in gameid.
   *
   * @param gameid id of the game to create
   * @param gameDetails details about the game to create
   */
  @PutMapping("/splendor_cities/api/games/{gameid}")
  public void putGameIdCities(@PathVariable long gameid,
                              @RequestBody SessionDetails gameDetails) {
    System.out.println("Game created with id " + gameid);
    List<Player> players = new ArrayList<>();
    players.add(new Player(gameDetails.getPlayers().get(0).getName(),
        gameDetails.getPlayers().get(0).getPreferredColour(), 1));
    players.add(new Player(gameDetails.getPlayers().get(1).getName(),
        gameDetails.getPlayers().get(1).getPreferredColour(), 2));
    if (gameDetails.getPlayers().size() > 2) {
      players.add(new Player(gameDetails.getPlayers().get(2).getName(),
          gameDetails.getPlayers().get(2).getPreferredColour(), 3));
    }
    if (gameDetails.getPlayers().size() > 3) {
      players.add(new Player(gameDetails.getPlayers().get(3).getName(),
          gameDetails.getPlayers().get(3).getPreferredColour(), 4));
    }

    if (gameDetails.getSavegame().equals("")) {
      gameMap.put(String.valueOf(gameid), new Game(players, Extensions.ORIENT_CITIES));

      managerMap.put(gameid, new BroadcastContentManager<>(
          new GameState(gameMap.get(String.valueOf(gameid)))));

    } else {
      // Start game from a previously saved game
      String saveGameId = gameDetails.getSavegame();

      Savegame save;

      try {
        save = SaveGameManager.getInstance().getSaveFromId(saveGameId);
      } catch (IOException e) {
        System.out.println("Could not start game from save with id: " + saveGameId);
        return;
      }

      Map<String, String> playerNames = save.getPlayerNames();
      List<Player> orderedPlayers = orderPlayers(players, playerNames);

      gameMap.put(String.valueOf(gameid), new Game(orderedPlayers, save));

      managerMap.put(gameid, new BroadcastContentManager<>(
          new GameState(gameMap.get(String.valueOf(gameid)))));

    }
  }




  /**
   * REST Delete request for /splendor_orient/api/{gameid}.
   * Remove the game associated with the specified gameid, if it exists.
   *
   * @param gameid id of the game to delete
   */
  @DeleteMapping("/splendor_orient/api/games/{gameid}")
  public void deleteGameIdOrient(@PathVariable long gameid) {
    gameMap.remove(String.valueOf(gameid));
  }

  /**
   * REST Delete request for /splendor_trading/api/{gameid}.
   * Remove the game associated with the specified gameid, if it exists.
   *
   * @param gameid id of the game to delete
   */
  @DeleteMapping("/splendor_trading/api/games/{gameid}")
  public void deleteGameIdTrading(@PathVariable long gameid) {
    gameMap.remove(String.valueOf(gameid));
  }

  /**
   * REST Delete request for /splendor_cities/api/{gameid}.
   * Remove the game associated with the specified gameid, if it exists.
   *
   * @param gameid id of the game to delete
   */
  @DeleteMapping("/splendor_cities/api/games/{gameid}")
  public void deleteGameIdCities(@PathVariable long gameid) {
    gameMap.remove(String.valueOf(gameid));
  }





  /**
   * REST Get request for /splendor_orient/api/{gameid}/players/{player}/actions.
   * Returns the list of all valid game actions for the player specified in the path.
   * If the player is not in the game or if the game does not exist, respond with BAD_REQUEST.
   * If the access_token does not belong to the player, respond with UNAUTHORIZED.
   * If the player is not the current player, the list returned will be empty.
   *
   * @param gameid the id of the game in which to get the lits of actions
   * @param player the player for which to get the actions
   * @param token the access token of the player sending the request
   * @return list of all valid actions
   */
  @GetMapping("/splendor_orient/api/games/{gameid}/players/{player}/actions")
  public ResponseEntity<String> getActionsOrient(@PathVariable("gameid") String gameid,
                                                 @PathVariable("player") String player,
                                                 @RequestParam(name = "access_token")
                                                   String token) {

    return getActions(gameid, player, token);

  }

  /**
   * REST Get request for /splendor_trading/api/{gameid}/players/{player}/actions.
   * Returns the list of all valid game actions for the player specified in the path.
   * If the player is not in the game or if the game does not exist, respond with BAD_REQUEST.
   * If the access_token does not belong to the player, respond with UNAUTHORIZED.
   * If the player is not the current player, the list returned will be empty.
   *
   * @param gameid the id of the game in which to get the lits of actions
   * @param player the player for which to get the actions
   * @param token the access token of the player sending the request
   * @return list of all valid actions
   */
  @GetMapping("/splendor_trading/api/games/{gameid}/players/{player}/actions")
  public ResponseEntity<String> getActionsTrading(@PathVariable("gameid") String gameid,
                                                 @PathVariable("player") String player,
                                                 @RequestParam(name = "access_token")
                                                    String token) {

    return getActions(gameid, player, token);

  }

  /**
   * REST Get request for /splendor_cities/api/{gameid}/players/{player}/actions.
   * Returns the list of all valid game actions for the player specified in the path.
   * If the player is not in the game or if the game does not exist, respond with BAD_REQUEST.
   * If the access_token does not belong to the player, respond with UNAUTHORIZED.
   * If the player is not the current player, the list returned will be empty.
   *
   * @param gameid the id of the game in which to get the lits of actions
   * @param player the player for which to get the actions
   * @param token the access token of the player sending the request
   * @return list of all valid actions
   */
  @GetMapping("/splendor_cities/api/games/{gameid}/players/{player}/actions")
  public ResponseEntity<String> getActionsCities(@PathVariable("gameid") String gameid,
                                                 @PathVariable("player") String player,
                                                 @RequestParam(name = "access_token")
                                                   String token) {

    return getActions(gameid, player, token);

  }

  /**
   * Helper method for GET actions rest endpoints.
   *
   * @param gameid the id of the game in which to get the lits of actions
   * @param player the player for which to get the actions
   * @param token the access token of the player sending the request
   * @return list of all valid actions
   */
  private ResponseEntity<String> getActions(String gameid, String player, String token) {

    if (!gameMap.containsKey(gameid)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No game with that id exists");
    }

    boolean isRealPlayer = false;
    Player playerObj = null;
    for (Player p : gameMap.get(gameid).getPlayers()) {
      if (p.getName().equals(player)) {
        isRealPlayer = true;
        playerObj = p;
      }
    }

    if (!isRealPlayer) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a real player");
    }

    String playerFromToken = getUsernameFromToken(token);

    if (!playerFromToken.equals(player) || playerFromToken.equals("")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Player doesn't match token");
    }

    Game game = gameMap.get(gameid);

    List<Long> actions = ActionGenerator.generateActionList(game, playerObj);

    String actionsString = new Gson().toJson(actions);

    return ResponseEntity.status(HttpStatus.OK).body(actionsString);

  }




  /**
   * REST Post request for /splendor_orient/api/{gameid}/players/{player}/actions/{action}.
   * Performs the action specified in action, if it is a valid action.
   * If the game does not exist or if the player is not the current player, does not perform any
   * action.
   * If the token does not belong to the player, does not perform any action.
   * After the action is performed, update the state of the game.
   *
   * @param gameid the id of the game in which to perform the action
   * @param player the player who performs the action
   * @param action long value representing the action to perform
   * @param token the access token of the player sending the request
   */
  @PostMapping("/splendor_orient/api/games/{gameid}/players/{player}/actions/{action}")
  public void postActionOrient(@PathVariable("gameid") String gameid,
                               @PathVariable("player") String player,
                               @PathVariable("action") long action,
                               @RequestParam(name = "access_token") String token) {

    postAction(gameid, player, action, token);

  }

  /**
   * REST Post request for /splendor_trading/api/{gameid}/players/{player}/actions/{action}.
   * Performs the action specified in action, if it is a valid action.
   * If the game does not exist or if the player is not the current player, does not perform any
   * action.
   * If the token does not belong to the player, does not perform any action.
   * After the action is performed, update the state of the game.
   *
   * @param gameid the id of the game in which to perform the action
   * @param player the player who performs the action
   * @param action long value representing the action to perform
   * @param token the access token of the player sending the request
   */
  @PostMapping("/splendor_trading/api/games/{gameid}/players/{player}/actions/{action}")
  public void postActionTrading(@PathVariable("gameid") String gameid,
                               @PathVariable("player") String player,
                               @PathVariable("action") long action,
                               @RequestParam(name = "access_token") String token) {

    postAction(gameid, player, action, token);

  }

  /**
   * REST Post request for /splendor_cities/api/{gameid}/players/{player}/actions/{action}.
   * Performs the action specified in action, if it is a valid action.
   * If the game does not exist or if the player is not the current player, does not perform any
   * action.
   * If the token does not belong to the player, does not perform any action.
   * After the action is performed, update the state of the game.
   *
   * @param gameid the id of the game in which to perform the action
   * @param player the player who performs the action
   * @param action long value representing the action to perform
   * @param token the access token of the player sending the request
   */
  @PostMapping("/splendor_cities/api/games/{gameid}/players/{player}/actions/{action}")
  public void postActionCities(@PathVariable("gameid") String gameid,
                               @PathVariable("player") String player,
                               @PathVariable("action") long action,
                               @RequestParam(name = "access_token") String token) {

    postAction(gameid, player, action, token);

  }

  /**
   * Helper method for POST actions rest endpoints.
   *
   * @param gameid the id of the game in which to perform the action
   * @param player the player who performs the action
   * @param action long value representing the action to perform
   * @param token the access token of the player sending the request
   */
  private void postAction(String gameid, String player, long action, String token) {
    System.out.println("Received action: " + action);

    // if the game doesn't exist
    if (!gameMap.containsKey(gameid)) {
      System.out.println("Error A : game does not exist");
      return;
    }

    Game game = gameMap.get(gameid);
    Player currPlayer = game.getPlayers().get(game.getCurrentPlayer() - 1);

    // if the player is not the current player
    if (!currPlayer.getName().equals(player)) {
      //System.out.println("Current Player: " + currPlayer);
      System.out.println("Current player name: " + currPlayer.getName()
          + " - Player sending request: " + player);
      System.out.println("Error B : Not current player");
      return;
    }

    // if the token doesn't match
    String playerFromToken = getUsernameFromToken(token);
    if (!playerFromToken.equals(player) || playerFromToken.equals("")) {
      System.out.println("Error C : token does not match");
      return;
    }

    // check if the action is valid
    List<Long> validActions = ActionGenerator.generateActionList(game, currPlayer);
    if (!validActions.contains(action)) {
      System.out.println("Error D : Action is not valid");
      return;
    }

    // everything looks good
    System.out.println("Performing action: " + action);
    ActionController.performAction(currPlayer, game, action);

    // this part needs testing
    managerMap.get(Long.valueOf(gameid)).updateBroadcastContent(
        new GameState(gameMap.get(gameid)));
    managerMap.get(Long.valueOf(gameid)).touch();

    //managerMap.put(Long.valueOf(gameid), new BroadcastContentManager<>(
    //    new GameState(gameMap.get(gameid))));
  }




  /**
   * Method to register the splendor game service which includes the orient extension
   * to the lobby service.
   */
  public void registerGameServiceOrient() {
    if (ignoreRequests) {
      return;
    }

    try {
      String accessToken = getAccessToken();
      String location = lobbyServiceLocation + "/api/gameservices/splendor_orient";
      OrientDetails details = new OrientDetails();
      String requestBody = new Gson().toJson(details);


      HttpResponse<String> response = Unirest.put(location)
          .header("Authorization", "Bearer " + accessToken)
          .header("Content-Type", "application/json").body(requestBody).asString();
      System.out.println(response.getStatus());
      System.out.println("Splendor (Orient) registered");
    } catch (UnirestException e) {
      System.out.println(e.getMessage());
      System.out.println("Lobby Service could not be reached");
    }

  }

  /**
   * Method to register the splendor game service which includes the orient and trading posts
   * extensions to the lobby service.
   */
  public void registerGameServiceTradingPosts() {
    if (ignoreRequests) {
      return;
    }

    try {
      String accessToken = getAccessToken();
      String location = lobbyServiceLocation + "/api/gameservices/splendor_trading";
      TradingDetails details = new TradingDetails();
      String requestBody = new Gson().toJson(details);


      HttpResponse<String> response = Unirest.put(location)
          .header("Authorization", "Bearer " + accessToken)
          .header("Content-Type", "application/json").body(requestBody).asString();
      System.out.println(response.getStatus());
      System.out.println("Splendor (Orient + Trading Posts) registered");
    } catch (UnirestException e) {
      System.out.println(e.getMessage());
      System.out.println("Lobby Service could not be reached");
    }

  }

  /**
   * Method to register the splendor game service which includes the orient and cities
   * extensions to the lobby service.
   */
  public void registerGameServiceCities() {
    if (ignoreRequests) {
      return;
    }

    try {
      String accessToken = getAccessToken();
      String location = lobbyServiceLocation + "/api/gameservices/splendor_cities";
      CitiesDetails details = new CitiesDetails();
      String requestBody = new Gson().toJson(details);


      HttpResponse<String> response = Unirest.put(location)
          .header("Authorization", "Bearer " + accessToken)
          .header("Content-Type", "application/json").body(requestBody).asString();
      System.out.println(response.getStatus());
      System.out.println("Splendor (Orient + Cities) registered");
    } catch (UnirestException e) {
      System.out.println(e.getMessage());
      System.out.println("Lobby Service could not be reached");
    }

  }




  private static String getAccessToken() throws UnirestException {
    if (ignoreRequests) {
      return "";
    }

    String location = lobbyServiceLocation + "/oauth/token";

    String parameter1 = "grant_type=password";
    String parameter2 = "&username=" + getUsername();
    String parameter3 = "&password=" + getPassword();

    String requestParameters = parameter1 + parameter2 + parameter3;

    HttpResponse<String> response = Unirest.post(location)
        .header("Authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=")
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(requestParameters).asString();

    JsonObject tokenJson = JsonParser.parseString(response.getBody()).getAsJsonObject();

    return tokenJson.get("access_token").toString().replaceAll("\"", "");
  }

  private String getUsernameFromToken(String token) {
    try {
      String location = lobbyServiceLocation + "/oauth/username";
      String authorization = "Bearer " + token;

      HttpResponse<String> response = Unirest.get(location)
          .header("Authorization", authorization)
          .asString();

      return response.getBody();

    } catch (UnirestException e) {
      return "";
    }
  }

  private static String getUsername() {
    return "xox";
  }

  private static String getPassword() {
    return "laaPhie*aiN0";
  }

  /**
   * Method to send a request to the Lobby Service to save a game.
   *
   * @param body the body of the request
   */
  public static void sendSaveRequest(SaveRequestBody body) {
    if (ignoreRequests) {
      return;
    }

    try {
      String accessToken = getAccessToken();
      String location = lobbyServiceLocation
          + "/api/gameservices/" + body.gamename
          + "/savegames/" + body.savegameid;

      String requestBody = new Gson().toJson(body);

      HttpResponse<String> response = Unirest.put(location)
          .header("Authorization", "Bearer " + accessToken)
          .header("Content-Type", "application/json").body(requestBody).asString();
      System.out.println(response.getStatus());
      System.out.println("Savegame added to Lobby Service with id: " + body.savegameid);
    } catch (UnirestException e) {
      System.out.println("Lobby Service could not be reached");
    }

  }

  /**
   * Method to send a request to the Lobby Service to delete a saved game.
   *
   * @param id the savegame id of the saved game to delete
   * @param gameService the name of the game service associated with this saved game
   */
  public static void sendDeleteSaveRequest(String id, String gameService) {
    if (ignoreRequests) {
      return;
    }

    try {
      String accessToken = getAccessToken();
      String location = lobbyServiceLocation
          + "/api/gameservices/" + gameService
          + "/savegames/" + id;

      HttpResponse<String> response = Unirest.delete(location)
          .header("Authorization", "Bearer " + accessToken)
          .asString();
      System.out.println(response.getStatus());
      System.out.println("Savegame deleted from Lobby Service with id: " + id);
    } catch (UnirestException e) {
      System.out.println("Lobby Service could not be reached");
    }

  }

  /**
   * REST POST request to save a game of the orient extension.
   *
   * @param gameid id of the game to save
   * @param token token of the player making the request
   */
  @PostMapping("/splendor_orient/api/games/{gameid}/savegames")
  public void postSaveOrient(@PathVariable("gameid") String gameid,
                             @RequestParam(name = "access_token") String token) {

    saveGame(gameid, token);

  }

  /**
   * REST POST request to save a game of the trading posts extension.
   *
   * @param gameid id of the game to save
   * @param token token of the player making the request
   */
  @PostMapping("/splendor_trading/api/games/{gameid}/savegames")
  public void postSaveTrading(@PathVariable("gameid") String gameid,
                              @RequestParam(name = "access_token") String token) {

    saveGame(gameid, token);

  }

  /**
   * REST POST request to save a game of the cities extension.
   *
   * @param gameid id of the game to save
   * @param token token of the player making the request
   */
  @PostMapping("/splendor_cities/api/games/{gameid}/savegames")
  public void postSave(@PathVariable("gameid") String gameid,
                       @RequestParam(name = "access_token") String token) {

    saveGame(gameid, token);

  }

  /**
   * Method to save game. Checks that the request is valid and if so calls the saveGame method in
   * the SaveGameManager class.
   *
   * @param gameid the id of the game to save (not the savegame id)
   * @param token token of the player making the request
   */
  private void saveGame(String gameid, String token) {
    if (!gameMap.containsKey(gameid)) {
      return;
    }
    Game game = gameMap.get(gameid);

    String playerName = getUsernameFromToken(token);
    boolean found = false;
    for (Player p : game.getPlayers()) {
      if (p.getName().equals(playerName)) {
        found = true;
        break;
      }
    }
    if (!found) {
      return;
    }

    Savegame save = new Savegame(game);
    try {
      String saveGameId = SaveGameManager.getInstance().saveGame(save);
    } catch (IOException e) {
      System.out.println("Could not save game: can't write to disk");
    }

  }





}
