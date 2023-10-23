//package ca.mcgill.splendorserver.server;
//
//import ca.mcgill.splendorserver.gameelements.Player;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.junit.Test;
//
//public class test {
//
//  @Test
//  public void test1() {
//
//    Player player1 = new Player("a", "", 1);
//    Player player2 = new Player("b", "", 2);
//    Player player3 = new Player("c", "", 3);
//    Player player4 = new Player("d", "", 4);
//    List<Player> players = new ArrayList<>();
//    players.add(player1);
//    players.add(player2);
//    //players.add(player3);
//    //players.add(player4);
//
//    Map<String,String> playerNames = new HashMap<>();
//    playerNames.put("Player1", "e");
//    playerNames.put("Player2", "d");
//    playerNames.put("Player3", "b");
//    playerNames.put("Player4", "y");
//
//    Savegame save = null;
//    try {
//      save = SaveGameManager.getInstance().getSaveFromId("2038438962");
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//    Game game = new Game(players, save);
//
//    GameState state = new GameState(game);
//
//    System.out.println(state.getCardsOnBoard());
//
//    //List<String> ordered = SplendorGameController.orderPlayers(players, playerNames);
//
//    //System.out.println(ordered);
//
//
//  }
//
//}
