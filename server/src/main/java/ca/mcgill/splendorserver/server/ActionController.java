package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.BaseDeck;
import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.City;
import ca.mcgill.splendorserver.gameelements.CitySlot;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Noble;
import ca.mcgill.splendorserver.gameelements.NobleSlot;
import ca.mcgill.splendorserver.gameelements.OrientCard;
import ca.mcgill.splendorserver.gameelements.OrientCardType;
import ca.mcgill.splendorserver.gameelements.Player;
import ca.mcgill.splendorserver.gameelements.TradingPostType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to perform splendor game actions.
 */
public class ActionController {

  private ActionController() {
    Long i = 3823456_00_11_22_33_44_5_6L;
  }

  /**
   * Static method to perform a splendor game action.
   *
   * @param actionId the action to be performed, encoded as a long as follows:
   *                 actionId mapping:
   *                 Select token color --> 1[color]
   *                 Select token colors --> 2[color][color][color]
   *                 3_abcdeg_cc_e1_e2_e3_e4 (17 digits)
   *                 Buy card --> 3[# of white coins][# of blue coins][# of green coins]
   *                 [# of red coins][# of black coins][# of gold coins][card #]
   *                 [extra 1 #][extra 2 #][extra 3 #][extra 4 #]
   *                 [# of gold cards][color of token to take with trading post]
   *                 if purchased card is of type PAIR_CARD:
   *                 extra 1 # : card to pair to in inventory
   *                 if purchased card is of type TAKE_L1_CARD:
   *                 extra 1 # : card to pair to in inventory
   *                 extra 2 # : free level 1 card
   *                 if extra 2 is of type PAIR_CARD:
   *                 extra 3 # : card to pair to in inventory
   *                 if purchased card is of type RESERVE_NOBLE:
   *                 extra 1 # : noble to reserve
   *                 if purchased card is of type TAKE_L2_CARD:
   *                 extra 1 # : free level 2 card
   *                 if extra 1 is of type TAKE_L1_CARD:
   *                 extra 2 # : card to pair to in inventory
   *                 extra 3 # : free level 1 card
   *                 if extra 3 is of type PAIR_CARD:
   *                 extra 4 # : card to pair to in inventory
   *                 if extra 1 is of type RESERVE_NOBLE:
   *                 extra 2 # : noble to reserve
   *                 if purchased card is of type DISCARD_BONUSES:
   *                 # of [color] coins is 0
   *                 extra 1 # : card to discard in inventory
   *                 extra 2 # : card to discard in inventory
   *                 extra 3 # : number of cards to discard (1 or 2)
   *                 Reserve card --> 4[card #][gold token amount]
   *                 Choose noble --> 5[noble #] (noble # = 0 to 4 from left to right)
   *                 color mapping:
   *                 white --> 0
   *                 blue --> 1
   *                 green --> 2
   *                 red --> 3
   *                 black --> 4
   *                 gold --> 5
   * @param player   the player who performs the action
   * @param game     the game in which the action is performed
   */
  public static void performAction(Player player, Game game, long actionId) {

    String actionIdStr = Long.toString(actionId);

    String whichAction = actionIdStr.substring(0, 1);

    if (whichAction.equals("1")) {

      if (game.getTurnStatus() != TurnStatus.CHOOSE_ACTION) {
        return;
      }

      // Take 2 tokens

      int colorToken = Integer.parseInt(actionIdStr.substring(1, 2));
      player.addColorTokens(colorToken, 2);
      game.getGameBoard().reduceColorToken(colorToken, 2);

      // if player has the 2nd post: get a free 3rd token
      if (player.getInventory().hasTradePost(TradingPostType.EXTRA_TOKEN_OTHER_COLOR)) {
        int colorToken2 = Integer.parseInt(actionIdStr.substring(2, 3));
        player.addColorTokens(colorToken2, 1);
        game.getGameBoard().reduceColorToken(colorToken2, 1);
      }


    } else if (whichAction.equals("2")) {

      if (game.getTurnStatus() != TurnStatus.CHOOSE_ACTION) {
        return;
      }

      // Take 3 tokens

      int colorToken1 = Integer.parseInt(actionIdStr.substring(1, 2));
      int colorToken2 = Integer.parseInt(actionIdStr.substring(2, 3));
      int colorToken3 = Integer.parseInt(actionIdStr.substring(3, 4));
      player.addColorTokens(colorToken1, 1);
      player.addColorTokens(colorToken2, 1);
      player.addColorTokens(colorToken3, 1);
      game.getGameBoard().reduceColorToken(colorToken1, 1);
      game.getGameBoard().reduceColorToken(colorToken2, 1);
      game.getGameBoard().reduceColorToken(colorToken3, 1);

    } else if (whichAction.equals("3")) {

      if (game.getTurnStatus() != TurnStatus.CHOOSE_ACTION) {
        return;
      }

      // Buy card
      int cardNum = Integer.parseInt(actionIdStr.substring(7, 9));
      BaseCard card = game.getCardsOnBoard().get(cardNum).cardInSlot();

      System.out.println(card.getClass().toString());
      System.out.println(card.getType().toString());
      System.out.println(card);

      boolean isDiscard = card.getClass() == OrientCard.class
          && card.getType() == OrientCardType.DISCARD_BONUSES;

      int extra1 = Integer.parseInt(actionIdStr.substring(9, 11));
      int extra2 = Integer.parseInt(actionIdStr.substring(11, 13));
      int extra3 = Integer.parseInt(actionIdStr.substring(13, 15));
      int extra4 = Integer.parseInt(actionIdStr.substring(15, 17));
      int doubleGold = Integer.parseInt(actionIdStr.substring(17, 18));
      int freeToken = Integer.parseInt(actionIdStr.substring(18, 19));

      // Purchased card is of type DISCARD_BONUSES
      if (isDiscard) {
        BaseCard discard1 = player.getInventory().getAllCards().get(extra1);
        BaseCard discard2 = player.getInventory().getAllCards().get(extra2);
        //add to inventory
        player.getInventory().addCard(card);
        //discard
        player.getInventory().discard(discard1);
        if (extra3 == 2) {
          player.getInventory().discard(discard2);
        }
        //Remove from card slot
        game.getCardsOnBoard().get(cardNum).remove();
        //Refill card slot
        game.getCardsOnBoard().get(cardNum).refill();

      } else {

        // Purchased card is not of type DISCARD_BONUSES
        List<Integer> cost = new ArrayList<>();
        int numWhite = Integer.parseInt(actionIdStr.substring(1, 2));
        cost.add(numWhite);
        int numBlue = Integer.parseInt(actionIdStr.substring(2, 3));
        cost.add(numBlue);
        int numGreen = Integer.parseInt(actionIdStr.substring(3, 4));
        cost.add(numGreen);
        int numRed = Integer.parseInt(actionIdStr.substring(4, 5));
        cost.add(numRed);
        int numBlack = Integer.parseInt(actionIdStr.substring(5, 6));
        cost.add(numBlack);

        int numGold = Integer.parseInt(actionIdStr.substring(6, 7));


        List<Integer> tokenAmounts = Arrays.asList(
            Math.max(0, numWhite - player.getInventory().getWhiteBonus()),
            Math.max(0, numBlue - player.getInventory().getBlueBonus()),
            Math.max(0, numGreen - player.getInventory().getGreenBonus()),
            Math.max(0, numRed - player.getInventory().getRedBonus()),
            Math.max(0, numBlack - player.getInventory().getBlackBonus())
        );

        //Update player's inventory
        player.getInventory().removeColorTokens(0, tokenAmounts.get(0));
        player.getInventory().removeColorTokens(1, tokenAmounts.get(1));
        player.getInventory().removeColorTokens(2, tokenAmounts.get(2));
        player.getInventory().removeColorTokens(3, tokenAmounts.get(3));
        player.getInventory().removeColorTokens(4, tokenAmounts.get(4));
        player.getInventory().removeGoldToken(numGold);
        //Add coins back to game board
        game.getGameBoard().addColorToken(0, tokenAmounts.get(0));
        game.getGameBoard().addColorToken(1, tokenAmounts.get(1));
        game.getGameBoard().addColorToken(2, tokenAmounts.get(2));
        game.getGameBoard().addColorToken(3, tokenAmounts.get(3));
        game.getGameBoard().addColorToken(4, tokenAmounts.get(4));
        game.getGameBoard().addColorToken(5, numGold);

        //Remove DOUBLE_GOLD cards spent
        player.getInventory().removeGoldCards(doubleGold);

        //Add free token from trading posts
        if (player.getInventory().hasTradePost(TradingPostType.FREE_TOKEN_WITH_PURCHASE)) {
          player.addColorTokens(freeToken, 1);
          game.getGameBoard().reduceColorToken(freeToken, 1);
        }

        if (card.getType() == OrientCardType.PAIR_CARD) {
          BaseCard pair = player.getInventory().getAllCards().get(extra1);
          ((OrientCard) card).setPair(pair);
          card.setBonus(pair.getBonusColor());
        }

        if (card.getType() == OrientCardType.RESERVE_NOBLE) {
          NobleSlot slot = game.getNoblesOnBoard().get(extra1);
          player.getInventory().reserveNoble(slot.removeNoble());
        }

        if (card.getType() == OrientCardType.TAKE_L1_CARD) {
          BaseCard pair = player.getInventory().getAllCards().get(extra1);
          ((OrientCard) card).setPair(pair);
          card.setBonus(pair.getBonusColor());
          CardSlot freeCard = game.getCardsOnBoard().get(extra2);
          if (freeCard.cardInSlot().getType() == OrientCardType.PAIR_CARD) {
            BaseCard pair2 = player.getInventory().getAllCards().get(extra3);
            ((OrientCard) freeCard.cardInSlot()).setPair(pair2);
            freeCard.cardInSlot().setBonus(pair2.getBonusColor());
          }
          player.getInventory().addCard(freeCard.remove());
          freeCard.refill();
        }

        if (card.getType() == OrientCardType.TAKE_L2_CARD) {
          CardSlot freeCard = game.getCardsOnBoard().get(extra1);
          if (freeCard.cardInSlot().getType() == OrientCardType.TAKE_L1_CARD) {
            BaseCard pair = player.getInventory().getAllCards().get(extra2);
            ((OrientCard) freeCard.cardInSlot()).setPair(pair);
            freeCard.cardInSlot().setBonus(pair.getBonusColor());
            CardSlot freeCard2 = game.getCardsOnBoard().get(extra3);
            if (freeCard2.cardInSlot().getType() == OrientCardType.PAIR_CARD) {
              BaseCard pair2 = player.getInventory().getAllCards().get(extra4);
              ((OrientCard) freeCard2.cardInSlot()).setPair(pair2);
              freeCard2.cardInSlot().setBonus(pair2.getBonusColor());
            }
            player.getInventory().addCard(freeCard2.remove());
            freeCard2.refill();
          } else if (freeCard.cardInSlot().getType() == OrientCardType.RESERVE_NOBLE) {
            NobleSlot slot = game.getNoblesOnBoard().get(extra2);
            player.getInventory().reserveNoble(slot.removeNoble());
          }
          player.getInventory().addCard(freeCard.remove());
          freeCard.refill();
        }


        //If can purchase --> add to inventory
        player.getInventory().addCard(card);
        //Remove from card slot
        game.getCardsOnBoard().get(cardNum).remove();
        //Refill card slot
        game.getCardsOnBoard().get(cardNum).refill();


      }

    } else if (whichAction.equals("4")) {

      if (game.getTurnStatus() != TurnStatus.CHOOSE_ACTION) {
        return;
      }

      // Reserve card

      int cardNum = Integer.parseInt(actionIdStr.substring(1, 3));
      int chosenDeck = Integer.parseInt(actionIdStr.substring(4, 5));
      if (chosenDeck != 0) {
        //get appropriate deck from color
        BaseDeck deck = game.getGameBoard().getDeck(chosenDeck);
        //draw a card from that deck
        BaseCard card = deck.draw();
        //Update inventory (add card to reserved, add gold coins)
        player.getInventory().reserveCard(card);
        System.out.println("backend doin tha work");

      } else {
        BaseCard card = game.getCardsOnBoard().get(cardNum).cardInSlot();
        //Update inventory (add card to reserved, add gold coins)
        player.getInventory().reserveCard(card);
        //Remove from card slot
        game.getCardsOnBoard().get(cardNum).remove();
        //Refill card slot
        game.getCardsOnBoard().get(cardNum).refill();
      }

      int withGold = Integer.parseInt(actionIdStr.substring(3, 4));

      if (withGold == 1) {
        player.getInventory().addGoldToken(1);
        //Update game board
        game.getGameBoard().reduceColorToken(5, 1);
      }
    } else if (whichAction.equals("6")) {
      //Get the card
      int cardNum = Integer.parseInt(actionIdStr.substring(7, 9));
      BaseCard card = player.getInventory().getReservedCards().get(cardNum);

      System.out.println(card.toString());

      boolean isDiscard = card.getClass() == OrientCard.class
              && card.getType() == OrientCardType.DISCARD_BONUSES;

      System.out.println("isDiscard : " + isDiscard);

      int extra1 = Integer.parseInt(actionIdStr.substring(9, 11));
      int extra2 = Integer.parseInt(actionIdStr.substring(11, 13));
      int extra3 = Integer.parseInt(actionIdStr.substring(13, 15));
      int extra4 = Integer.parseInt(actionIdStr.substring(15, 17));
      int doubleGold = Integer.parseInt(actionIdStr.substring(17, 18));
      int freeToken = Integer.parseInt(actionIdStr.substring(18, 19));

      // Purchased card is of type DISCARD_BONUSES
      if (isDiscard) {
        BaseCard discard1 = player.getInventory().getAllCards().get(extra1);
        BaseCard discard2 = player.getInventory().getAllCards().get(extra2);

        System.out.println(discard1);
        System.out.println(discard2);
        //khabiir first

        //add to inventory
        player.getInventory().addCard(card);
        //discard
        player.getInventory().discard(discard1);
        if (extra3 == 2) {
          player.getInventory().discard(discard2);
        }

      } else {
        // Purchased card is not of type DISCARD_BONUSES
        List<Integer> cost = new ArrayList<>();
        int numWhite = Integer.parseInt(actionIdStr.substring(1, 2));
        cost.add(numWhite);
        int numBlue = Integer.parseInt(actionIdStr.substring(2, 3));
        cost.add(numBlue);
        int numGreen = Integer.parseInt(actionIdStr.substring(3, 4));
        cost.add(numGreen);
        int numRed = Integer.parseInt(actionIdStr.substring(4, 5));
        cost.add(numRed);
        int numBlack = Integer.parseInt(actionIdStr.substring(5, 6));
        cost.add(numBlack);

        int numGold = Integer.parseInt(actionIdStr.substring(6, 7));


        List<Integer> tokenAmounts = Arrays.asList(
                Math.max(0, numWhite - player.getInventory().getWhiteBonus()),
                Math.max(0, numBlue - player.getInventory().getBlueBonus()),
                Math.max(0, numGreen - player.getInventory().getGreenBonus()),
                Math.max(0, numRed - player.getInventory().getRedBonus()),
                Math.max(0, numBlack - player.getInventory().getBlackBonus())
        );

        //Update player's inventory
        player.getInventory().removeColorTokens(0, tokenAmounts.get(0));
        player.getInventory().removeColorTokens(1, tokenAmounts.get(1));
        player.getInventory().removeColorTokens(2, tokenAmounts.get(2));
        player.getInventory().removeColorTokens(3, tokenAmounts.get(3));
        player.getInventory().removeColorTokens(4, tokenAmounts.get(4));
        player.getInventory().removeGoldToken(numGold);
        //Add coins back to game board
        game.getGameBoard().addColorToken(0, tokenAmounts.get(0));
        game.getGameBoard().addColorToken(1, tokenAmounts.get(1));
        game.getGameBoard().addColorToken(2, tokenAmounts.get(2));
        game.getGameBoard().addColorToken(3, tokenAmounts.get(3));
        game.getGameBoard().addColorToken(4, tokenAmounts.get(4));
        game.getGameBoard().addColorToken(5, numGold);

        //Remove DOUBLE_GOLD cards spent
        player.getInventory().removeGoldCards(doubleGold);

        //Add free token from trading posts
        // TODO : change if condition to check whether the player has unlocked the corresponding
        // TODO : trading post
        if (false) {
          player.addColorTokens(freeToken, 1);
          game.getGameBoard().reduceColorToken(freeToken, 1);
        }

        if (card.getType() == OrientCardType.PAIR_CARD) {
          BaseCard pair = player.getInventory().getAllCards().get(extra1);
          ((OrientCard) card).setPair(pair);
          card.setBonus(pair.getBonusColor());
        }

        if (card.getType() == OrientCardType.RESERVE_NOBLE) {
          NobleSlot slot = game.getNoblesOnBoard().get(extra1);
          player.getInventory().reserveNoble(slot.removeNoble());
        }

        if (card.getType() == OrientCardType.TAKE_L1_CARD) {
          BaseCard pair = player.getInventory().getAllCards().get(extra1);
          ((OrientCard) card).setPair(pair);
          card.setBonus(pair.getBonusColor());
          CardSlot freeCard = game.getCardsOnBoard().get(extra2);
          if (freeCard.cardInSlot().getType() == OrientCardType.PAIR_CARD) {
            BaseCard pair2 = player.getInventory().getAllCards().get(extra3);
            ((OrientCard) freeCard.cardInSlot()).setPair(pair2);
            freeCard.cardInSlot().setBonus(pair2.getBonusColor());
          }
          player.getInventory().addCard(freeCard.remove());
          freeCard.refill();
        }

        if (card.getType() == OrientCardType.TAKE_L2_CARD) {
          CardSlot freeCard = game.getCardsOnBoard().get(extra1);
          if (freeCard.cardInSlot().getType() == OrientCardType.TAKE_L1_CARD) {
            BaseCard pair = player.getInventory().getAllCards().get(extra2);
            ((OrientCard) freeCard.cardInSlot()).setPair(pair);
            freeCard.cardInSlot().setBonus(pair.getBonusColor());
            CardSlot freeCard2 = game.getCardsOnBoard().get(extra3);
            if (freeCard2.cardInSlot().getType() == OrientCardType.PAIR_CARD) {
              BaseCard pair2 = player.getInventory().getAllCards().get(extra4);
              ((OrientCard) freeCard2.cardInSlot()).setPair(pair2);
              freeCard2.cardInSlot().setBonus(pair2.getBonusColor());
            }
            player.getInventory().addCard(freeCard2.remove());
            freeCard2.refill();
          } else if (freeCard.cardInSlot().getType() == OrientCardType.RESERVE_NOBLE) {
            NobleSlot slot = game.getNoblesOnBoard().get(extra2);
            player.getInventory().reserveNoble(slot.removeNoble());
          }
          player.getInventory().addCard(freeCard.remove());
          freeCard.refill();
        }

        //If can purchase --> add to inventory and remove from reserved cards
        player.getInventory().addCard(card);
        player.getInventory().getReservedCards().remove(card);
        System.out.println("Purchase successful");
      }
    } else if (whichAction.equals("5")) {

      // Take or reserve noble

      int nobleNumber = Integer.parseInt(actionIdStr.substring(1, 2));

      if (nobleNumber < 0 || nobleNumber > 4) {
        return;
      }

      // action is to take a noble
      if (game.getTurnStatus() == TurnStatus.CHOOSE_NOBLE) {
        NobleSlot nobleChosen = game.getGameBoard().getNobleSlots().get(nobleNumber);

        // check that there is a noble
        if (!nobleChosen.hasNoble()) {
          return;
        }

        // check that the player can take the noble
        if (!canTakeNoble(player, nobleChosen.nobleInSlot())) {
          return;
        }

        player.getInventory().addNoble(nobleChosen.removeNoble());
        checkTradingPosts(player, game);
        game.nextPlayer();
        // TODO : Check if game over

        game.setTurnStatus(TurnStatus.CHOOSE_ACTION);
        return;
      }

    }

    if (!whichAction.equals("5")) {
      List<NobleSlot> availableNobles = getAvailableNobles(game, player);

      if (availableNobles.size() == 0) {
        game.nextPlayer();
      } else {
        game.setTurnStatus(TurnStatus.CHOOSE_NOBLE);
      }
    }

    //Adding relevant trading posts
    checkTradingPosts(player, game);


    //Checking if eligible for City and removing if so.
    if (game.getExtensions() == Extensions.ORIENT_CITIES
        || game.getExtensions() == Extensions.ORIENT_TRADING_CITIES) {
      for (CitySlot citySlot : game.getGameBoard().getCitySlots()) {
        if (citySlot.hasCard()) {
          City currCity = citySlot.cityInSlot();
          if (currCity.eligible(player)) {
            player.getInventory().setOwnedCity(currCity);
            citySlot.remove();
          }
        }
      }
    }

    //Checkin end of game for Orient + Trading
    if ((game.getExtensions().equals(Extensions.ORIENT)
        || game.getExtensions().equals(Extensions.ORIENT_TRADING))
        && player.getNumber() == game.getPlayers().size()) {

      List<Player> winner = new ArrayList<>();

      for (Player p : game.getPlayers()) {
        if (p.getPrestigePoints() >= 15) {
          winner.add(p);
        }
      }
      Player singleWinner = null;

      if (winner.size() == 1) {
        singleWinner = winner.get(0);
      } else {
        int minDevCards = 100;
        for (Player potentialWinner : winner) {
          if (potentialWinner.getInventory().getTotalBonusCards() < minDevCards) {
            singleWinner = potentialWinner;
          }
        }
      }

      if (singleWinner != null) {
        game.setTurnStatus(TurnStatus.GAME_OVER);
        game.setWinningPlayer(singleWinner.getNumber());
      }

    }

    //Checking end of game for Cities
    if (game.getExtensions() == Extensions.ORIENT_CITIES
        || game.getExtensions() == Extensions.ORIENT_TRADING_CITIES) {
      if (player.getNumber() == game.getPlayers().size()) {
        List<Player> winners = new ArrayList<>();
        for (Player p : game.getPlayers()) {
          if (p.getInventory().hasCity()) {
            winners.add(p);
          }
        }
        if (winners.size() != 0) {
          int maxPrestige = 0;
          Player winner;

          for (Player win : winners) {
            if (win.getPrestigePoints() > maxPrestige) {
              winner = win;
              maxPrestige = win.getPrestigePoints();
              game.setWinningPlayer(winner.getNumber());
            }
          }
          game.setTurnStatus(TurnStatus.GAME_OVER);
        }
      }

    }
    System.out.println(game.getTurnStatus());
  }

  private static void checkTradingPosts(Player player, Game game) {
    if (game.getExtensions() == Extensions.ORIENT_TRADING_CITIES
        || game.getExtensions() == Extensions.ORIENT_TRADING) {

      for (TradingPostType tp : TradingPostType.values()) {
        if (tp.meetsRequirements(player) && !player.getInventory().hasTradePost(tp)) {
          player.getInventory().addTradePosts(tp);
        }
        if (!tp.meetsRequirements(player) && player.getInventory().hasTradePost(tp)) {
          player.getInventory().removeTradePosts(tp);
        }
      }
    }
  }

  /**
   * Get a list of the nobles that can be taken by a player.
   *
   * @param game   the game being played
   * @param player the player to get the list of available nobles for
   * @return list of NobleSlot objects which contain nobles that the player can take
   */
  public static List<NobleSlot> getAvailableNobles(Game game, Player player) {
    List<NobleSlot> availableNobles = new ArrayList<>();
    for (NobleSlot slot : game.getNoblesOnBoard()) {
      if (slot.hasNoble()) {
        if (canTakeNoble(player, slot.nobleInSlot())) {
          availableNobles.add(slot);
        }
      }
    }
    return availableNobles;
  }

  /**
   * This method checks if a player meets the conditions to take a given noble.
   *
   * @param player the player performing the action
   * @param noble  the noble currently being checked
   * @return true if the player meets the requirements, otherwise false
   */
  public static boolean canTakeNoble(Player player, Noble noble) {
    return player.getInventory().getRedBonus() >= noble.getRedCost()
        && player.getInventory().getBlackBonus() >= noble.getBlackCost()
        && player.getInventory().getBlueBonus() >= noble.getBlueCost()
        && player.getInventory().getWhiteBonus() >= noble.getWhiteCost()
        && player.getInventory().getGreenBonus() >= noble.getGreenCost();
  }
}
