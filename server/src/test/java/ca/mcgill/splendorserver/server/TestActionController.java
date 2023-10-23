package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.BaseDeck;
import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.DeckColor;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.OrientCard;
import ca.mcgill.splendorserver.gameelements.Player;
import ca.mcgill.splendorserver.gameelements.TradingPostType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for ActionController class
 */
public class TestActionController {
  private Player p1 = new Player("p1", "1", 1);
  private Player p2 = new Player("p2", "1", 2);
  private Player p3 = new Player("p3", "1", 3);
  private Player p4 = new Player("p4", "1", 4);
  private List<Player> players = Arrays.asList(p1, p2, p3, p4);
  private Game game = new Game(players, Extensions.ORIENT);

  @Test
  public void performActionTest_takeTwoTokens() {

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

    ActionController.performAction(p1, game, 120000000_00_00_00_00L);

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 5);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 2);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

  }

  @Test
  public void performActionTest_takeThreeTokens() {

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

    ActionController.performAction(p1, game, 223400000_00_00_00_00L);

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 6);
    assertEquals(game.getRedTokenAmount(), 6);
    assertEquals(game.getBlackTokenAmount(), 6);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 1);
    assertEquals(p1.getInventory().getRedTokens(), 1);
    assertEquals(p1.getInventory().getBlackTokens(), 1);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

  }

  @Test
  public void performActionTest_purchaseCard() {

    BaseCard card = game.getCardsOnBoard().get(0).cardInSlot();
    p1.addColorTokens(0, card.getWhiteCost());
    p1.addColorTokens(1, card.getBlueCost()+2);
    p1.addColorTokens(2, card.getGreenCost());
    p1.addColorTokens(3, card.getRedCost()+1);
    p1.addColorTokens(4, card.getBlackCost()+1);

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), card.getWhiteCost());
    assertEquals(p1.getInventory().getBlueTokens(), card.getBlueCost()+2);
    assertEquals(p1.getInventory().getGreenTokens(), card.getGreenCost());
    assertEquals(p1.getInventory().getRedTokens(), card.getRedCost()+1);
    assertEquals(p1.getInventory().getBlackTokens(), card.getBlackCost()+1);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

    assertSame(game.getCardsOnBoard().get(0).cardInSlot(), card);
    assertEquals(p1.getInventory().getAllCards().size(), 0);

    long buyActionId = 300000000_00_00_00_00_0_0L
        + card.getWhiteCost() * 10000000_00_00_00_00_0_0L
        + card.getBlueCost() * 1000000_00_00_00_00_0_0L
        + card.getGreenCost() * 100000_00_00_00_00_0_0L
        + card.getRedCost() * 10000_00_00_00_00_0_0L
        + card.getBlackCost() * 1000_00_00_00_00_0_0L;

    ActionController.performAction(p1, game, buyActionId);

    assertEquals(game.getWhiteTokenAmount(), 7 + card.getWhiteCost());
    assertEquals(game.getBlueTokenAmount(), 7 + card.getBlueCost());
    assertEquals(game.getGreenTokenAmount(), 7 + card.getGreenCost());
    assertEquals(game.getRedTokenAmount(), 7 + card.getRedCost());
    assertEquals(game.getBlackTokenAmount(), 7 + card.getBlackCost());
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 2);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 1);
    assertEquals(p1.getInventory().getBlackTokens(), 1);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

    assertNotSame(game.getCardsOnBoard().get(0).cardInSlot(), card);
    assertEquals(p1.getInventory().getAllCards().size(), 1);

  }

  @Test
  public void performActionTest_reserveCard() {

    BaseCard card = game.getCardsOnBoard().get(0).cardInSlot();

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

    assertSame(game.getCardsOnBoard().get(0).cardInSlot(), card);
    assertEquals(p1.getInventory().getReservedCards().size(), 0);

    ActionController.performAction(p1, game, 400100000_00_00_00_00L);

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 4);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 1);

    assertNotSame(game.getCardsOnBoard().get(0).cardInSlot(), card);
    assertEquals(p1.getInventory().getReservedCards().size(), 1);

  }
  @Test
  public void performActionTest_reserveCardDeck() {

    //BaseCard card = game.getCardsOnBoard().get(0).cardInSlot();

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 5);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 0);

    //assertSame(game.getCardsOnBoard().get(0).cardInSlot(), card);
    assertEquals(p1.getInventory().getReservedCards().size(), 0);

    ActionController.performAction(p1, game, 400110000_00_00_00_00L);

    assertEquals(game.getWhiteTokenAmount(), 7);
    assertEquals(game.getBlueTokenAmount(), 7);
    assertEquals(game.getGreenTokenAmount(), 7);
    assertEquals(game.getRedTokenAmount(), 7);
    assertEquals(game.getBlackTokenAmount(), 7);
    assertEquals(game.getGoldTokenAmount(), 4);

    assertEquals(p1.getInventory().getWhiteTokens(), 0);
    assertEquals(p1.getInventory().getBlueTokens(), 0);
    assertEquals(p1.getInventory().getGreenTokens(), 0);
    assertEquals(p1.getInventory().getRedTokens(), 0);
    assertEquals(p1.getInventory().getBlackTokens(), 0);
    assertEquals(p1.getInventory().getGoldTokens(), 1);

    //assertNotSame(game.getCardsOnBoard().get(0).cardInSlot(), card);
    assertEquals(p1.getInventory().getReservedCards().size(), 1);

  }

  @Test
  public void performActionTest_manyActions_orient() {
    // Check that all valid actions (obtained from the action generator) can be performed with no
    // issues

    Player p1 = new Player("p1", "1", 1);
    Player p2 = new Player("p2", "1", 2);
    Player p3 = new Player("p3", "1", 3);
    Player p4 = new Player("p4", "1", 4);
    List<Player> players = Arrays.asList(p1, p2, p3, p4);
    Game game = new Game(players, Extensions.ORIENT);

    BaseDeck redL1 = game.getGameBoard().getRedL1Deck();
    BaseDeck redL2 = game.getGameBoard().getRedL2Deck();
    BaseDeck redL3 = game.getGameBoard().getRedL3Deck();

    OrientCard pairCard = OrientCard
        .getOrientCard("NONE,0,3,2,0,0,0,PAIR_CARD,Pair1.png",
            DeckColor.REDL1);
    CardSlot pairCardSlot = new CardSlot(redL1, pairCard);

    OrientCard l1Cascade = OrientCard
        .getOrientCard("NONE,4,3,0,0,1,0,TAKE_L1_CARD,L1Cascade1.png",
            DeckColor.REDL2);
    CardSlot l1CascadeSlot = new CardSlot(redL2, l1Cascade);

    OrientCard l2Cascade = OrientCard
        .getOrientCard("BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png",
            DeckColor.REDL3);
    CardSlot l2CascadeSlot = new CardSlot(redL3, l2Cascade);

    OrientCard discardCard = OrientCard
        .getOrientCard("BLUE,2,0,0,0,0,3,DISCARD_BONUSES,Discard1.png",
            DeckColor.REDL3);
    CardSlot discardSlot = new CardSlot(redL3, discardCard);

    game.getGameBoard().getCardSlots().set(12, pairCardSlot);
    game.getGameBoard().getCardSlots().set(14, l1CascadeSlot);
    game.getGameBoard().getCardSlots().set(16, l2CascadeSlot);
    game.getGameBoard().getCardSlots().set(17, discardSlot);

    BaseCard card1 = new BaseCard("WHITE,0,0,4,0,0,1,White1.png", DeckColor.GREEN);
    BaseCard card2 = new BaseCard("WHITE,6,0,0,0,0,3,White3.png", DeckColor.YELLOW);

    p1.getInventory().addCard(card1);
    p1.getInventory().addCard(card2);

    p1.addColorTokens(0, 7);
    p1.addColorTokens(1, 7);
    p1.addColorTokens(2, 7);
    p1.addColorTokens(3, 7);
    p1.addColorTokens(4, 7);

    p1.getInventory().reserveCard(l1Cascade);
    p1.getInventory().reserveCard(discardCard);

    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    Savegame savegame = new Savegame(game);

    for (Long action : actions) {
      ActionController.performAction(p1, game, action);
      game = new Game(new ArrayList<>(players), savegame);
    }
  }

  @Test
  public void performActionTest_manyActions_trading() {
    // Check that all valid actions (obtained from the action generator) can be performed with no
    // issues

    Player p1 = new Player("p1", "1", 1);
    Player p2 = new Player("p2", "1", 2);
    Player p3 = new Player("p3", "1", 3);
    Player p4 = new Player("p4", "1", 4);
    List<Player> players = Arrays.asList(p1, p2, p3, p4);
    Game game = new Game(new ArrayList<>(players), Extensions.ORIENT_TRADING);

    BaseDeck redL1 = game.getGameBoard().getRedL1Deck();
    BaseDeck redL2 = game.getGameBoard().getRedL2Deck();
    BaseDeck redL3 = game.getGameBoard().getRedL3Deck();

    OrientCard pairCard = OrientCard
        .getOrientCard("NONE,0,3,2,0,0,0,PAIR_CARD,Pair1.png",
            DeckColor.REDL1);
    CardSlot pairCardSlot = new CardSlot(redL1, pairCard);

    OrientCard l1Cascade = OrientCard
        .getOrientCard("NONE,4,3,0,0,1,0,TAKE_L1_CARD,L1Cascade1.png",
            DeckColor.REDL2);
    CardSlot l1CascadeSlot = new CardSlot(redL2, l1Cascade);

    OrientCard l2Cascade = OrientCard
        .getOrientCard("BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png",
            DeckColor.REDL3);
    CardSlot l2CascadeSlot = new CardSlot(redL3, l2Cascade);

    OrientCard discardCard = OrientCard
        .getOrientCard("BLUE,2,0,0,0,0,3,DISCARD_BONUSES,Discard1.png",
            DeckColor.REDL3);
    CardSlot discardSlot = new CardSlot(redL3, discardCard);

    game.getGameBoard().getCardSlots().set(12, pairCardSlot);
    game.getGameBoard().getCardSlots().set(14, l1CascadeSlot);
    game.getGameBoard().getCardSlots().set(16, l2CascadeSlot);
    game.getGameBoard().getCardSlots().set(17, discardSlot);

    BaseCard card1 = new BaseCard("WHITE,0,0,4,0,0,1,White1.png", DeckColor.GREEN);
    BaseCard card2 = new BaseCard("WHITE,6,0,0,0,0,3,White3.png", DeckColor.YELLOW);

    p1.getInventory().addCard(card1);
    p1.getInventory().addCard(card2);

    p1.addColorTokens(0, 7);
    p1.addColorTokens(1, 7);
    p1.addColorTokens(2, 7);
    p1.addColorTokens(3, 7);
    p1.addColorTokens(4, 7);

    p1.getInventory().reserveCard(l2Cascade);
    p1.getInventory().reserveCard(discardCard);
    p1.getInventory().reserveCard(l1Cascade);

    for (TradingPostType type : TradingPostType.values()) {
      p1.getInventory().addTradePosts(type);
    }

    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    Savegame savegame = new Savegame(game);

    for (Long action : actions) {
      ActionController.performAction(p1, game, action);
      game = new Game(new ArrayList<>(players), savegame);
    }
  }

  @Test
  public void performActionTest_manyActions_cities() {
    // Check that all valid actions (obtained from the action generator) can be performed with no
    // issues

    Player p1 = new Player("p1", "1", 1);
    Player p2 = new Player("p2", "1", 2);
    Player p3 = new Player("p3", "1", 3);
    Player p4 = new Player("p4", "1", 4);
    List<Player> players = Arrays.asList(p1, p2, p3, p4);
    Game game = new Game(players, Extensions.ORIENT_CITIES);

    BaseDeck redL1 = game.getGameBoard().getRedL1Deck();
    BaseDeck redL2 = game.getGameBoard().getRedL2Deck();
    BaseDeck redL3 = game.getGameBoard().getRedL3Deck();

    OrientCard pairCard = OrientCard
        .getOrientCard("NONE,0,3,2,0,0,0,PAIR_CARD,Pair1.png",
            DeckColor.REDL1);
    CardSlot pairCardSlot = new CardSlot(redL1, pairCard);

    OrientCard l1Cascade = OrientCard
        .getOrientCard("NONE,4,3,0,0,1,0,TAKE_L1_CARD,L1Cascade1.png",
            DeckColor.REDL2);
    CardSlot l1CascadeSlot = new CardSlot(redL2, l1Cascade);

    OrientCard l2Cascade = OrientCard
        .getOrientCard("BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png",
            DeckColor.REDL3);
    CardSlot l2CascadeSlot = new CardSlot(redL3, l2Cascade);

    OrientCard discardCard = OrientCard
        .getOrientCard("BLUE,2,0,0,0,0,3,DISCARD_BONUSES,Discard1.png",
            DeckColor.REDL3);
    CardSlot discardSlot = new CardSlot(redL3, discardCard);

    game.getGameBoard().getCardSlots().set(12, pairCardSlot);
    game.getGameBoard().getCardSlots().set(14, l1CascadeSlot);
    game.getGameBoard().getCardSlots().set(16, l2CascadeSlot);
    game.getGameBoard().getCardSlots().set(17, discardSlot);

    BaseCard card1 = new BaseCard("WHITE,0,0,4,0,0,1,White1.png", DeckColor.GREEN);
    BaseCard card2 = new BaseCard("WHITE,6,0,0,0,0,3,White3.png", DeckColor.YELLOW);

    p1.getInventory().addCard(card1);
    p1.getInventory().addCard(card2);

    p1.addColorTokens(0, 7);
    p1.addColorTokens(1, 7);
    p1.addColorTokens(2, 7);
    p1.addColorTokens(3, 7);
    p1.addColorTokens(4, 7);

    p1.getInventory().reserveCard(pairCard);
    p1.getInventory().reserveCard(l2Cascade);

    game.getGameBoard().reduceColorToken(5, game.getGoldTokenAmount());

    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    System.out.println(actions.size());

    Savegame savegame = new Savegame(game);

    for (Long action : actions) {
      ActionController.performAction(p1, game, action);
      game = new Game(new ArrayList<>(players), savegame);
    }
  }



}
