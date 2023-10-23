package ca.mcgill.splendorserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.BaseDeck;
import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.DeckColor;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.OrientCard;
import ca.mcgill.splendorserver.gameelements.Player;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for ActionGenerator class
 */
public class TestActionGenerator {
  private Player p1 = new Player("p1", "1", 1);
  private Player p2 = new Player("p2", "1", 2);
  private Player p3 = new Player("p3", "1", 3);
  private Player p4 = new Player("p4", "1", 4);
  private List<Player> players = Arrays.asList(p1, p2, p3, p4);
  private Game game = new Game(players, Extensions.ORIENT);

  @Test
  public void generateActionListTest_cantBuy() {

    List<Long> actions = ActionGenerator.generateActionList(game, p1);
    assertTrue(actions.contains(1_000000_00_00_00_00_00L));
    assertTrue(actions.contains(406100000_00_00_00_00L));
    assertTrue(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p2);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p3);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p4);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

  }


  @Test
  public void generateActionListTest_canBuy() {

    BaseCard card = game.getCardsOnBoard().get(0).cardInSlot();
    p1.addColorTokens(0, card.getWhiteCost());
    p1.addColorTokens(1, card.getBlueCost());
    p1.addColorTokens(2, card.getGreenCost());
    p1.addColorTokens(3, card.getRedCost());
    p1.addColorTokens(4, card.getBlackCost());

    long buyActionId = 300000000_00_00_00_00_0_0L
        + card.getWhiteCost() * 10000000_00_00_00_00_0_0L
        + card.getBlueCost() * 1000000_00_00_00_00_0_0L
        + card.getGreenCost() * 100000_00_00_00_00_0_0L
        + card.getRedCost() * 10000_00_00_00_00_0_0L
        + card.getBlackCost() * 1000_00_00_00_00_0_0L;

    List<Long> actions = ActionGenerator.generateActionList(game, p1);
    assertTrue(actions.contains(100000000_00_00_00_00L));
    assertTrue(actions.contains(406100000_00_00_00_00L));
    assertTrue(actions.contains(212300000_00_00_00_00L));
    assertTrue(actions.contains(buyActionId));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p2);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(buyActionId));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p3);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(buyActionId));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p4);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(buyActionId));
    assertFalse(actions.contains(738490179_00_00_00_00L));

  }


  @Test
  public void generateActionListTest_reservesFull() {

    BaseCard card = game.getCardsOnBoard().get(0).cardInSlot();
    p1.getInventory().reserveCard(card);
    p1.getInventory().reserveCard(card);
    p1.getInventory().reserveCard(card);

    List<Long> actions = ActionGenerator.generateActionList(game, p1);
    assertTrue(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertTrue(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00_0_0L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p2);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00_0_0L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p3);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00_0_0L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

    actions = ActionGenerator.generateActionList(game, p4);
    assertFalse(actions.contains(100000000_00_00_00_00L));
    assertFalse(actions.contains(406000000_00_00_00_00L));
    assertFalse(actions.contains(212300000_00_00_00_00L));
    assertFalse(actions.contains(312340000_00_00_00_00_0_0L));
    assertFalse(actions.contains(738490179_00_00_00_00L));

  }

  @Test
  public void generateActionListTest_nobles() {
    game.setTurnStatus(TurnStatus.CHOOSE_NOBLE);
    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    assertFalse(actions.contains(50L));
    assertFalse(actions.contains(51L));
    assertFalse(actions.contains(52L));
    assertFalse(actions.contains(53L));
    assertFalse(actions.contains(54L));
    assertEquals(actions.size(), 0);
  }

  @Test
  public void generateActionListTest_reserve() {
    game.setTurnStatus(TurnStatus.CHOOSE_ACTION);
    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    assertTrue(actions.contains(400100000_00_00_00_00L));
    assertTrue(actions.contains(401100000_00_00_00_00L));
    assertTrue(actions.contains(402100000_00_00_00_00L));
    assertTrue(actions.contains(403100000_00_00_00_00L));

    assertFalse(actions.contains(400000000_00_00_00_00L));
    assertFalse(actions.contains(401000000_00_00_00_00L));
    assertFalse(actions.contains(402000000_00_00_00_00L));
    assertFalse(actions.contains(403000000_00_00_00_00L));

    game.getGameBoard().reduceColorToken(5, 5);

    actions = ActionGenerator.generateActionList(game, p1);

    assertFalse(actions.contains(400100000_00_00_00_00L));
    assertFalse(actions.contains(401100000_00_00_00_00L));
    assertFalse(actions.contains(402100000_00_00_00_00L));
    assertFalse(actions.contains(403100000_00_00_00_00L));

    assertTrue(actions.contains(400000000_00_00_00_00L));
    assertTrue(actions.contains(401000000_00_00_00_00L));
    assertTrue(actions.contains(402000000_00_00_00_00L));
    assertTrue(actions.contains(403000000_00_00_00_00L));
  }

  @Test
  public void generateActionListTest_buyCardOrient() {
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

    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    assertTrue(actions.contains(303200012_00_00_00_00_0_0L));
    assertTrue(actions.contains(303200012_01_00_00_00_0_0L));

    assertTrue(actions.contains(343001014_00_02_00_00_0_0L));
    assertTrue(actions.contains(343001014_00_00_00_00_0_0L));
    assertTrue(actions.contains(343001014_01_01_00_00_0_0L));

    assertTrue(actions.contains(363001016_05_00_00_00_0_0L));
    assertTrue(actions.contains(363001016_14_01_03_00_0_0L));
    assertTrue(actions.contains(363001016_06_00_00_00_0_0L));

  }

  @Test
  public void generateActionListTest_buyReservedCard1() {
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
    p1.getInventory().reserveCard(l1Cascade);
    p1.getInventory().reserveCard(l2Cascade);

    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    assertTrue(actions.contains(603200000_00_00_00_00_0_0L));
    assertTrue(actions.contains(603200000_01_00_00_00_0_0L));

    assertTrue(actions.contains(643001001_00_02_00_00_0_0L));
    assertTrue(actions.contains(643001001_00_00_00_00_0_0L));
    assertTrue(actions.contains(643001001_01_01_00_00_0_0L));

    assertTrue(actions.contains(663001002_05_00_00_00_0_0L));
    assertTrue(actions.contains(663001002_14_01_03_00_0_0L));
    assertTrue(actions.contains(663001002_06_00_00_00_0_0L));

  }

  @Test
  public void generateActionListTest_buyReservedCard2() {
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

    p1.getInventory().reserveCard(discardCard);
    p1.getInventory().reserveCard(card2);

    List<Long> actions = ActionGenerator.generateActionList(game, p1);

    assertTrue(actions.contains(303200012_00_00_00_00_0_0L));
    assertTrue(actions.contains(303200012_01_00_00_00_0_0L));

    assertTrue(actions.contains(343001014_00_02_00_00_0_0L));
    assertTrue(actions.contains(343001014_00_00_00_00_0_0L));
    assertTrue(actions.contains(343001014_01_01_00_00_0_0L));

    assertTrue(actions.contains(363001016_05_00_00_00_0_0L));
    assertTrue(actions.contains(363001016_14_01_03_00_0_0L));
    assertTrue(actions.contains(363001016_06_00_00_00_0_0L));



  }

}
