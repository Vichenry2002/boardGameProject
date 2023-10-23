package ca.mcgill.splendorserver.server;

import ca.mcgill.splendorserver.gameelements.BaseCard;
import ca.mcgill.splendorserver.gameelements.BaseDeck;
import ca.mcgill.splendorserver.gameelements.BonusColor;
import ca.mcgill.splendorserver.gameelements.CardSlot;
import ca.mcgill.splendorserver.gameelements.DeckColor;
import ca.mcgill.splendorserver.gameelements.Extensions;
import ca.mcgill.splendorserver.gameelements.Noble;
import ca.mcgill.splendorserver.gameelements.NobleSlot;
import ca.mcgill.splendorserver.gameelements.OrientCard;
import ca.mcgill.splendorserver.gameelements.OrientCardType;
import ca.mcgill.splendorserver.gameelements.Player;
import ca.mcgill.splendorserver.gameelements.TradingPostType;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Class to generate the list of possible actions.
 */
public class ActionGenerator {

  private ActionGenerator() {

  }

  /**
   * Generates the full list of valid actions for a specific player and game.
   *
   * @param game the game in which the actions can be performed
   * @param player the player that performs the actions
   * @return the list of all valid actions
   */
  public static List<Long> generateActionList(Game game, Player player) {

    List<Long> actions = new ArrayList<>();
    System.out.println(game.getTurnStatus());

    // If not current player : return empty list
    if (!(game.getPlayers().get(game.getCurrentPlayer() - 1) == player)) {
      return actions;
    }

    if (game.getTurnStatus() == TurnStatus.CHOOSE_ACTION) {

      // add 2 token actions
      actions.addAll(getTwoTokens(game, player));

      // add 3 token actions
      actions.addAll(getThreeTokens(game, player));

      // add buy regular card actions
      actions.addAll(getBuyCards(game, player));

      // add reserve card actions
      actions.addAll(getReserveCards(game, player));

      //add reserve card from deck
      actions.addAll(getReservefromDeck(game, player));

      // add buyable reserved cards actions
      actions.addAll(getBuyReservedCards(game, player));
    }

    if (game.getTurnStatus() == TurnStatus.CHOOSE_NOBLE) {

      actions.addAll(getNobles(game, player));
      // add choose noble actions
      // TODO

    }



    return actions;
  }

  // Get two tokens of the same color
  // 1i0000000 a=color or 1ij000000 if trading post 2 unlocked for the player
  private static List<Long> getTwoTokens(Game game, Player player) {

    List<Long> twoTokensList = new ArrayList<>();

    // if player has the 2nd trading post, can also take a 3rd free token of another color
    int[] amountTokens = new int[] {game.getWhiteTokenAmount(), game.getBlueTokenAmount(),
        game.getGreenTokenAmount(), game.getRedTokenAmount(), game.getBlackTokenAmount()};
    if (player.getInventory().hasTradePost(TradingPostType.EXTRA_TOKEN_OTHER_COLOR)) {
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          if (i != j) {
            int amountI = amountTokens[i];
            int amountJ = amountTokens[j];
            if (amountI >= 4) {
              if (amountJ >= 1) {
                Long current = 100000000_00_00_00_00L
                    + i * 10000000_00_00_00_00L
                    + j * 1000000_00_00_00_00L;
                twoTokensList.add(current);
              }
            }
          }
        }
      }
    } else {
      if (game.getWhiteTokenAmount() >= 4) {
        twoTokensList.add(100000000_00_00_00_00L);
      }

      if (game.getBlueTokenAmount() >= 4) {
        twoTokensList.add(110000000_00_00_00_00L);
      }

      if (game.getGreenTokenAmount() >= 4) {
        twoTokensList.add(120000000_00_00_00_00L);
      }

      if (game.getRedTokenAmount() >= 4) {
        twoTokensList.add(130000000_00_00_00_00L);
      }

      if (game.getBlackTokenAmount() >= 4) {
        twoTokensList.add(140000000_00_00_00_00L);
      }
    }

    return twoTokensList;


  }

  // Get three tokens of different colors
  // 2abc00000 a,b,c=color
  private static List<Long> getThreeTokens(Game game, Player player) {

    List<Long> threeTokensList = new ArrayList<>();

    List<Integer> amounts = Arrays.asList(
        game.getWhiteTokenAmount(),
        game.getBlueTokenAmount(),
        game.getGreenTokenAmount(),
        game.getRedTokenAmount(),
        game.getBlackTokenAmount()
    );

    for (int a = 0; a < 3; a++) {
      for (int b = a + 1; b < 4; b++) {
        for (int c = b + 1; c < 5; c++) {

          if (amounts.get(a) >= 1 && amounts.get(b) >= 1 && amounts.get(c) >= 1) {

            Long current = 200000000_00_00_00_00L
                + a * 10000000_00_00_00_00L
                + b * 1000000_00_00_00_00L
                + c * 100000_00_00_00_00L;
            threeTokensList.add(current);

          }
        }
      }
    }

    return threeTokensList;
  }


  // Buy a regular card
  // 3abcdegxx a,b,c,d,e=color_tokens g=gold_tokens xx=card
  private static List<Long> getBuyCards(Game game, Player player) {

    List<Long> buyCardsList = new ArrayList<>();

    for (int i = 0; i < game.getCardsOnBoard().size(); i++) {

      CardSlot slot = game.getCardsOnBoard().get(i);

      if (slot.hasCard()) {
        BaseCard card = slot.cardInSlot();
        buyCardsList.addAll(getBuyCard(card, player, i, game));
      }
    }

    return buyCardsList;

  }

  // Buy a regular card
  // 3abcdegxx a,b,c,d,e=color_tokens g=gold_tokens xx=card
  private static List<Long> getBuyCard(BaseCard card, Player player, int cardNumber, Game game) {

    List<Long> buyCardList = new ArrayList<>();

    if (card.getClass() == OrientCard.class
        && ((OrientCard) card).getType() == OrientCardType.DISCARD_BONUSES) {
      buyCardList.addAll(getBuyCardDiscard(card, player, cardNumber));
      return buyCardList;
    }

    List<Integer> costs = Arrays.asList(
        card.getWhiteCost(),
        card.getBlueCost(),
        card.getGreenCost(),
        card.getRedCost(),
        card.getBlackCost()
    );

    int totalCost = costs.get(0) + costs.get(1) + costs.get(2) + costs.get(3) + costs.get(4);

    for (int a = 0; a <= costs.get(0); a++) {
      // The player needs to have enough white tokens/bonuses
      if (a > player.getInventory().getWhiteTokens()
          + player.getInventory().getWhiteBonus()) {
        break;
      }
      for (int b = 0; b <= costs.get(1); b++) {
        // The player needs to have enough blue tokens/bonuses
        if (b > player.getInventory().getBlueBonus()
            + player.getInventory().getBlueTokens()) {
          break;
        }
        for (int c = 0; c <= costs.get(2); c++) {
          // The player needs to have enough green tokens/bonuses
          if (c > player.getInventory().getGreenTokens()
              + player.getInventory().getGreenBonus()) {
            break;
          }
          for (int d = 0; d <= costs.get(3); d++) {
            // The player needs to have enough red tokens/bonuses
            if (d > player.getInventory().getRedTokens()
                + player.getInventory().getRedBonus()) {
              break;
            }
            for (int e = 0; e <= costs.get(4); e++) {
              // The player needs to have enough black tokens/bonuses
              if (e > player.getInventory().getBlackTokens()
                  + player.getInventory().getBlackBonus()) {
                break;
              }
              for (int g = 0; g <= 5; g++) {
                // The player needs to have enough gold tokens
                if (g > player.getInventory().getGoldTokens()) {
                  break;
                }
                for (int dg = 0; dg <= player.getInventory().getGoldCardAmount(); dg++) {
                  for (int freeT = 0; freeT <= 4; freeT++) {
                    // If no corresponding trading post unlocked, freeT can only be 0
                    if (!player.getInventory().hasTradePost(
                        TradingPostType.FREE_TOKEN_WITH_PURCHASE)
                        && freeT != 0) {
                      break;
                    }

                    // If the action involves taking a free token, make sure that there is a token
                    // to take
                    int[] colors = new int[] {a, b, c, d, e};
                    if (game.getGameBoard().getTokenAmount(freeT) == 0 && colors[freeT] == 0) {
                      if (player.getInventory()
                          .hasTradePost(TradingPostType.FREE_TOKEN_WITH_PURCHASE)) {
                        continue;
                      }
                    }

                    // Check if the player has the double gold trading post
                    boolean hasGoldTp = player.getInventory()
                        .hasTradePost(TradingPostType.DOUBLE_GOLD);
                    int intTp = hasGoldTp ? 1 : 0;

                    // Calculate gold token amounts
                    int goldValue = g * (1 + intTp);
                    int freeGoldTokens = dg * 2 * (1 + intTp);
                    int totalGold = goldValue + freeGoldTokens;

                    int smallestGoldUnit;
                    if (g > 0) {
                      smallestGoldUnit = 1 + intTp;
                    } else if (dg > 0) {
                      smallestGoldUnit = 2 * (1 + intTp);
                    } else {
                      smallestGoldUnit = 1;
                    }

                    // Check if the amounts and costs are valid
                    boolean validCosts = false;

                    // Not enough tokens
                    if (a + b + c + d + e + totalGold < totalCost) {
                      break;
                    }

                    // Too many excess gold tokens
                    // (ex. 1 extra token when using at least 1 gold token
                    // or 2 extra tokens when using 1 orient gold card)
                    if (a + b + c + d + e + totalGold >= totalCost + smallestGoldUnit) {
                      break;
                    }

                    int extra1 = 0;
                    int extra2 = 0;
                    int extra3 = 0;
                    int extra4 = 0;

                    Long current = 3_000000_00_00_00_00_00_0_0L
                        + a * 100000_00_00_00_00_00_0_0L
                        + b * 10000_00_00_00_00_00_0_0L
                        + c * 1000_00_00_00_00_00_0_0L
                        + d * 100_00_00_00_00_00_0_0L
                        + e * 10_00_00_00_00_00_0_0L
                        + g * 1_00_00_00_00_00_0_0L
                        + cardNumber * 1_00_00_00_00_0_0L
                        + dg * 10L
                        + freeT;

                    // type is PAIR_CARD
                    if (card.getClass() == OrientCard.class
                        && ((OrientCard) card).getType() == OrientCardType.PAIR_CARD) {
                      for (int i = 0; i < player.getInventory().getAllCards().size(); i++) {
                        extra1 = i;
                        buyCardList.add(current + extra1 * 1_00_00_00_0_0L);
                      }

                    } else if (card.getClass() == OrientCard.class
                        && ((OrientCard) card).getType() == OrientCardType.TAKE_L1_CARD) {
                      //type is TAKE_L1_CARD
                      for (int i = 0; i < player.getInventory().getAllCards().size(); i++) {
                        for (int j = 0; j < game.getCardsOnBoard().size(); j++) {
                          extra1 = i;
                          extra2 = j;
                          if (!game.getCardsOnBoard().get(j).hasCard()) {
                            continue;
                          }
                          BaseCard freeCard = game.getCardsOnBoard().get(j).cardInSlot();
                          if (freeCard.getDeckColor() != DeckColor.GREEN
                              && freeCard.getDeckColor() != DeckColor.REDL1) {
                            continue;
                          }
                          if (freeCard.getClass() == OrientCard.class
                              && ((OrientCard) freeCard).getType() == OrientCardType.PAIR_CARD) {
                            for (int k = 0; k < player.getInventory().getAllCards().size(); k++) {
                              extra3 = k;
                              if (i != k) {
                                buyCardList.add(current
                                    + extra1 * 1_00_00_00_0_0L
                                    + extra2 * 1_00_00_0_0L
                                    + extra3 * 1_00_0_0L);
                              }
                            }
                          } else {

                            buyCardList.add(current
                                + extra1 * 1_00_00_00_0_0L
                                + extra2 * 1_00_00_0_0L);

                          }

                        }
                      }

                    } else if (card.getClass() == OrientCard.class
                        && ((OrientCard) card).getType() == OrientCardType.RESERVE_NOBLE) {
                      // type is RESERVE_NOBLE
                      for (int i = 0; i < game.getNoblesOnBoard().size(); i++) {
                        extra1 = i;
                        if (game.getNoblesOnBoard().get(i).hasNoble()) {
                          buyCardList.add(current + extra1 * 1_00_00_00_0_0L);
                        }
                      }

                    } else if (card.getClass() == OrientCard.class
                        && ((OrientCard) card).getType() == OrientCardType.TAKE_L2_CARD) {
                      // type is TAKE_L2_CARD
                      for (int i = 0; i < game.getCardsOnBoard().size(); i++) {
                        extra1 = i;
                        if (!game.getCardsOnBoard().get(i).hasCard()) {
                          continue;
                        }
                        BaseCard freeCard = game.getCardsOnBoard().get(i).cardInSlot();
                        if (freeCard.getDeckColor() != DeckColor.YELLOW
                            && freeCard.getDeckColor() != DeckColor.REDL2) {
                          continue;
                        }
                        // free card is of type TAKE_L1_CARD
                        if (freeCard.getClass() == OrientCard.class
                            && ((OrientCard) freeCard).getType() == OrientCardType.TAKE_L1_CARD) {
                          for (int j = 0; j < player.getInventory().getAllCards().size(); j++) {
                            for (int k = 0; k < game.getCardsOnBoard().size(); k++) {
                              extra2 = j;
                              extra3 = k;
                              if (!game.getCardsOnBoard().get(k).hasCard()) {
                                continue;
                              }
                              BaseCard freeCard2 = game.getCardsOnBoard().get(k).cardInSlot();
                              if (freeCard2.getDeckColor() != DeckColor.GREEN
                                  && freeCard2.getDeckColor() != DeckColor.REDL1) {
                                continue;
                              }
                              if (freeCard2.getClass() == OrientCard.class
                                  && ((OrientCard) freeCard2).getType()
                                  == OrientCardType.PAIR_CARD) {
                                for (int l = 0; l < player.getInventory()
                                    .getAllCards().size(); l++) {
                                  extra4 = l;
                                  if (j != l) {
                                    buyCardList.add(current
                                        + extra1 * 1_00_00_00_0_0L
                                        + extra2 * 1_00_00_0_0L
                                        + extra3 * 1_00_0_0L
                                        + extra4 * 1_0_0L);
                                  }
                                }
                              } else {

                                buyCardList.add(current
                                    + extra1 * 1_00_00_00_0_0L
                                    + extra2 * 1_00_00_0_0L
                                    + extra3 * 1_00_0_0L);

                              }

                            }
                          }

                        } else if (freeCard.getClass() == OrientCard.class
                            && ((OrientCard) freeCard).getType() == OrientCardType.RESERVE_NOBLE) {
                          // free card is of type RESERVE_NOBLE
                          for (int j = 0; j < game.getNoblesOnBoard().size(); j++) {
                            extra2 = j;
                            if (game.getNoblesOnBoard().get(j).hasNoble()) {
                              buyCardList.add(current
                                  + extra1 * 1_00_00_00_0_0L
                                  + extra2 * 1_00_00_0_0L);
                            }
                          }
                        } else {

                          buyCardList.add(current
                              + extra1 * 1_00_00_00_0_0L);

                        }

                      }

                    } else {
                      buyCardList.add(current);
                    }


                  }
                }

              }
            }
          }
        }
      }
    }

    return buyCardList;
  }

  // Buy a card of type DISCARD_BONUSES
  // 3abcdegxxyyzz xx=card yy=discard1 zz=discard2
  private static List<Long> getBuyCardDiscard(BaseCard card, Player player, int cardNumber) {
    List<Long> buyCardList = new ArrayList<>();
    List<BaseCard> cards = player.getInventory().getAllCards();

    int cardsInInventory = cards.size();
    for (int card1 = 0; card1 < cardsInInventory; card1++) {
      BaseCard discard1 = cards.get(card1);
      BonusColor card1Bonus = discard1.getBonusColor();

      if (card1Bonus != ((OrientCard) card).getDiscardColor()) {
        continue;
      }

      boolean found = false;

      for (BaseCard c : cards) {
        if (isPair(c) && c.getBonusColor() == card1Bonus) {
          found = true;
          break;
        }
      }

      if (!found && discard1.getType() == OrientCardType.DOUBLE_BONUS) {

        long currentId = 3_000_000_00_00_00_00_00_0_0L
            + cardNumber * 1_00_00_00_00_0_0L
            + card1 * 1_00_00_00_0_0L
            + 1_00_0_0L;

        buyCardList.add(currentId);

        continue;
      }

      for (int card2 = 0; card2 < cardsInInventory; card2++) {
        if (card1 == card2) {
          continue;
        }

        BaseCard discard2 = cards.get(card2);
        BonusColor card2Bonus = discard2.getBonusColor();

        if (card1Bonus != card2Bonus || card1Bonus != ((OrientCard) card).getDiscardColor()) {
          continue;
        }

        long currentId = 3_000_000_00_00_00_00_00_0_0L
            + cardNumber * 1_00_00_00_00_0_0L
            + card1 * 1_00_00_00_0_0L
            + card2 * 1_00_00_0_0L
            + 2_00_0_0L;

        if (isPair(discard1) && isPair(discard2)) {
          buyCardList.add(currentId);
          continue;
        }

        boolean foundPair = false;

        for (BaseCard c : cards) {
          if (isPair(c) && c.getBonusColor() == card1Bonus && discard1 != c && discard2 != c) {
            foundPair = true;
            break;
          }
        }

        if (foundPair) {
          continue;
        }

        buyCardList.add(currentId);

      }
    }
    return buyCardList;

  }

  private static boolean isPair(BaseCard card) {
    if (card.getClass() != OrientCard.class) {
      return false;
    }
    OrientCardType type = ((OrientCard) card).getType();
    return type == OrientCardType.PAIR_CARD || type == OrientCardType.TAKE_L1_CARD;
  }

  // Reserve a card
  // 4xx000000 xx=card
  private static List<Long> getReserveCards(Game game, Player player) {

    List<Long> reserveCardsList = new ArrayList<>();

    if (player.getInventory().getReservedCards().size() == 3) {
      return reserveCardsList;
    }

    if (game.getGoldTokenAmount() > 0) {
      for (int i = 0; i < game.getCardsOnBoard().size(); i++) {

        if (game.getCardsOnBoard().get(i).hasCard()) {
          Long current = 400000000_00_00_00_00L
              + i * 1000000_00_00_00_00L
              + 100000_00_00_00_00L;
          reserveCardsList.add(current);
        }

      }
    } else {
      for (int i = 0; i < game.getCardsOnBoard().size(); i++) {

        if (game.getCardsOnBoard().get(i).hasCard()) {
          Long current = 400000000_00_00_00_00L
              + i * 1000000_00_00_00_00L;
          reserveCardsList.add(current);
        }


      }
    }


    return reserveCardsList;

  }

  // Buy a reserved card
  // 6abcdegxx a,b,c,d,e=color_tokens g=gold_tokens xx=card
  private static List<Long> getBuyReservedCards(Game game, Player player) {
    List<Long> buyableReservedCards = new ArrayList<>();
    //Loop through the player's reserved card, checking if the card is buyable
    // and adding the actions for each reserved cards
    int cardNumber = 0; //0-1-2
    for (BaseCard card : player.getInventory().getReservedCards()) {
      buyableReservedCards.addAll(getBuyReservedCard(card, player, cardNumber, game));
      cardNumber++;
    }

    return buyableReservedCards;
  }

  // Buy a reserved card
  // 6abcdegxx a,b,c,d,e=color_tokens g=gold_tokens xx=card
  private static List<Long> getBuyReservedCard(
          BaseCard card, Player player, int cardNumber, Game game) {
    List<Long> buyReservedCardList = new ArrayList<>();

    //Check if card is a discard bonus, if so use the GetBuyCardDiscard method
    if (card.getClass() == OrientCard.class
            && ((OrientCard) card).getType() == OrientCardType.DISCARD_BONUSES) {
      buyReservedCardList.addAll(getBuyReservedCardDiscard(card, player, cardNumber));
      return buyReservedCardList;
    }

    List<Integer> costs = Arrays.asList(
            card.getWhiteCost(),
            card.getBlueCost(),
            card.getGreenCost(),
            card.getRedCost(),
            card.getBlackCost()
    );

    int totalCost = costs.get(0) + costs.get(1) + costs.get(2) + costs.get(3) + costs.get(4);

    for (int a = 0; a <= costs.get(0); a++) {
      // The player needs to have enough white tokens/bonuses
      if (a > player.getInventory().getWhiteTokens()
              + player.getInventory().getWhiteBonus()) {
        break;
      }
      for (int b = 0; b <= costs.get(1); b++) {
        // The player needs to have enough blue tokens/bonuses
        if (b > player.getInventory().getBlueBonus()
                + player.getInventory().getBlueTokens()) {
          break;
        }
        for (int c = 0; c <= costs.get(2); c++) {
          // The player needs to have enough green tokens/bonuses
          if (c > player.getInventory().getGreenTokens()
                  + player.getInventory().getGreenBonus()) {
            break;
          }
          for (int d = 0; d <= costs.get(3); d++) {
            // The player needs to have enough red tokens/bonuses
            if (d > player.getInventory().getRedTokens()
                    + player.getInventory().getRedBonus()) {
              break;
            }
            for (int e = 0; e <= costs.get(4); e++) {
              // The player needs to have enough black tokens/bonuses
              if (e > player.getInventory().getBlackTokens()
                      + player.getInventory().getBlackBonus()) {
                break;
              }
              for (int g = 0; g <= 5; g++) {
                // The player needs to have enough gold tokens
                if (g > player.getInventory().getGoldTokens()) {
                  break;
                }
                for (int dg = 0; dg <= player.getInventory().getGoldCardAmount(); dg++) {
                  for (int freeT = 0; freeT <= 4; freeT++) {
                    // If no trading posts extension, freeT can only be 0
                    if (!player.getInventory()
                        .hasTradePost(TradingPostType.FREE_TOKEN_WITH_PURCHASE)
                            && freeT != 0) {
                      break;
                    }

                    // If the action involves taking a free token, make sure that there is a token
                    // to take
                    int[] colors = new int[] {a, b, c, d, e};
                    if (game.getGameBoard().getTokenAmount(freeT) == 0 && colors[freeT] == 0) {
                      if (player.getInventory()
                          .hasTradePost(TradingPostType.FREE_TOKEN_WITH_PURCHASE)) {
                        continue;
                      }
                    }

                    // TODO : Check if the player has the double gold trading post
                    boolean hasGoldTp = false;
                    int intTp = hasGoldTp ? 1 : 0;

                    // Calculate gold token amounts
                    int goldValue = g * (1 + intTp);
                    int freeGoldTokens = dg * 2 * (1 + intTp);
                    int totalGold = goldValue + freeGoldTokens;

                    int smallestGoldUnit;
                    if (g > 0) {
                      smallestGoldUnit = 1 + intTp;
                    } else if (dg > 0) {
                      smallestGoldUnit = 2 * (1 + intTp);
                    } else {
                      smallestGoldUnit = 1;
                    }

                    // Check if the amounts and costs are valid
                    boolean validCosts = false;

                    // Not enough tokens
                    if (a + b + c + d + e + totalGold < totalCost) {
                      break;
                    }

                    // Too many excess gold tokens
                    // (ex. 1 extra token when using at least 1 gold token
                    // or 2 extra tokens when using 1 orient gold card)
                    if (a + b + c + d + e + totalGold >= totalCost + smallestGoldUnit) {
                      break;
                    }

                    int extra1 = 0;
                    int extra2 = 0;
                    int extra3 = 0;
                    int extra4 = 0;

                    Long current = 6_000000_00_00_00_00_00_0_0L
                            + a * 100000_00_00_00_00_00_0_0L
                            + b * 10000_00_00_00_00_00_0_0L
                            + c * 1000_00_00_00_00_00_0_0L
                            + d * 100_00_00_00_00_00_0_0L
                            + e * 10_00_00_00_00_00_0_0L
                            + g * 1_00_00_00_00_00_0_0L
                            + cardNumber * 1_00_00_00_00_0_0L
                            + dg * 10L
                            + freeT;

                    // type is PAIR_CARD
                    if (card.getClass() == OrientCard.class
                            && ((OrientCard) card).getType() == OrientCardType.PAIR_CARD) {
                      for (int i = 0; i < player.getInventory().getAllCards().size(); i++) {
                        extra1 = i;
                        buyReservedCardList.add(current + extra1 * 1_00_00_00_0_0L);
                      }

                    } else if (card.getClass() == OrientCard.class
                            && ((OrientCard) card).getType() == OrientCardType.TAKE_L1_CARD) {
                      //type is TAKE_L1_CARD
                      for (int i = 0; i < player.getInventory().getAllCards().size(); i++) {
                        for (int j = 0; j < game.getCardsOnBoard().size(); j++) {
                          extra1 = i;
                          extra2 = j;
                          if (!game.getCardsOnBoard().get(j).hasCard()) {
                            continue;
                          }
                          BaseCard freeCard = game.getCardsOnBoard().get(j).cardInSlot();
                          if (freeCard.getDeckColor() != DeckColor.GREEN
                                  && freeCard.getDeckColor() != DeckColor.REDL1) {
                            continue;
                          }
                          if (freeCard.getClass() == OrientCard.class
                                  && ((OrientCard) freeCard).getType()
                                  == OrientCardType.PAIR_CARD) {
                            for (int k = 0; k < player.getInventory().getAllCards().size(); k++) {
                              extra3 = k;
                              if (i != k) {
                                buyReservedCardList.add(current
                                        + extra1 * 1_00_00_00_0_0L
                                        + extra2 * 1_00_00_0_0L
                                        + extra3 * 1_00_0_0L);
                              }
                            }
                          } else {
                            buyReservedCardList.add(current
                                    + extra1 * 1_00_00_00_0_0L
                                    + extra2 * 1_00_00_0_0L);
                          }
                        }
                      }
                    } else if (card.getClass() == OrientCard.class
                            && ((OrientCard) card).getType() == OrientCardType.RESERVE_NOBLE) {
                      // type is RESERVE_NOBLE
                      for (int i = 0; i < game.getNoblesOnBoard().size(); i++) {
                        extra1 = i;
                        if (game.getNoblesOnBoard().get(i).hasNoble()) {
                          buyReservedCardList.add(current + extra1 * 1_00_00_00_0_0L);
                        }
                      }
                    } else if (card.getClass() == OrientCard.class
                            && ((OrientCard) card).getType() == OrientCardType.TAKE_L2_CARD) {
                      // type is TAKE_L2_CARD
                      for (int i = 0; i < game.getCardsOnBoard().size(); i++) {
                        extra1 = i;
                        if (!game.getCardsOnBoard().get(i).hasCard()) {
                          continue;
                        }
                        BaseCard freeCard = game.getCardsOnBoard().get(i).cardInSlot();
                        if (freeCard.getDeckColor() != DeckColor.YELLOW
                                && freeCard.getDeckColor() != DeckColor.REDL2) {
                          continue;
                        }
                        // free card is of type TAKE_L1_CARD
                        if (freeCard.getClass() == OrientCard.class
                                && ((OrientCard) freeCard).getType()
                                == OrientCardType.TAKE_L1_CARD) {
                          for (int j = 0; j < player.getInventory().getAllCards().size(); j++) {
                            for (int k = 0; k < game.getCardsOnBoard().size(); k++) {
                              extra2 = j;
                              extra3 = k;
                              if (!game.getCardsOnBoard().get(k).hasCard()) {
                                continue;
                              }
                              BaseCard freeCard2 = game.getCardsOnBoard().get(k).cardInSlot();
                              if (freeCard2.getDeckColor() != DeckColor.GREEN
                                      && freeCard2.getDeckColor() != DeckColor.REDL1) {
                                continue;
                              }
                              if (freeCard2.getClass() == OrientCard.class
                                      && ((OrientCard) freeCard2).getType()
                                      == OrientCardType.PAIR_CARD) {
                                for (int l = 0; l < player.getInventory()
                                        .getAllCards().size(); l++) {
                                  extra4 = l;
                                  if (j != l) {
                                    buyReservedCardList.add(current
                                            + extra1 * 1_00_00_00_0_0L
                                            + extra2 * 1_00_00_0_0L
                                            + extra3 * 1_00_0_0L
                                            + extra4 * 1_0_0L);
                                  }
                                }
                              } else {
                                buyReservedCardList.add(current
                                        + extra1 * 1_00_00_00_0_0L
                                        + extra2 * 1_00_00_0_0L
                                        + extra3 * 1_00_0_0L);
                              }
                            }
                          }
                        } else if (freeCard.getClass() == OrientCard.class
                                && ((OrientCard) freeCard).getType()
                                == OrientCardType.RESERVE_NOBLE) {
                          // free card is of type RESERVE_NOBLE
                          for (int j = 0; j < game.getNoblesOnBoard().size(); j++) {
                            extra2 = j;
                            if (game.getNoblesOnBoard().get(j).hasNoble()) {
                              buyReservedCardList.add(current
                                      + extra1 * 1_00_00_00_0_0L
                                      + extra2 * 1_00_00_0_0L);
                            }
                          }
                        } else {
                          buyReservedCardList.add(current
                                  + extra1 * 1_00_00_00_0_0L);
                        }
                      }
                    } else {
                      buyReservedCardList.add(current);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return buyReservedCardList;
  }

  // Buy a reserved card of type DISCARD_BONUSES
  // 6abcdegxxyyzz xx=card yy=discard1 zz=discard2
  private static List<Long> getBuyReservedCardDiscard(
          BaseCard card, Player player, int cardNumber) {
    List<Long> buyReservedCardList = new ArrayList<>();
    List<BaseCard> cards = player.getInventory().getAllCards();

    int cardsInInventory = cards.size();
    for (int card1 = 0; card1 < cardsInInventory; card1++) {
      BaseCard discard1 = cards.get(card1);
      BonusColor card1Bonus = discard1.getBonusColor();

      if (card1Bonus != ((OrientCard) card).getDiscardColor()) {
        continue;
      }

      boolean found = false;

      for (BaseCard c : cards) {
        if (isPair(c) && c.getBonusColor() == card1Bonus) {
          found = true;
          break;
        }
      }

      if (!found && discard1.getType() == OrientCardType.DOUBLE_BONUS) {

        long currentId = 6_000_000_00_00_00_00_00_0_0L
                + cardNumber * 1_00_00_00_00_0_0L
                + card1 * 1_00_00_00_0_0L
                + 1_00_0_0L;

        buyReservedCardList.add(currentId);

        continue;
      }

      for (int card2 = 0; card2 < cardsInInventory; card2++) {
        if (card1 == card2) {
          continue;
        }

        BaseCard discard2 = cards.get(card2);
        BonusColor card2Bonus = discard2.getBonusColor();

        if (card1Bonus != card2Bonus || card1Bonus != ((OrientCard) card).getDiscardColor()) {
          continue;
        }

        long currentId = 6_000_000_00_00_00_00_00_0_0L
                + cardNumber * 1_00_00_00_00_0_0L
                + card1 * 1_00_00_00_0_0L
                + card2 * 1_00_00_0_0L
                + 2_00_0_0L;

        if (isPair(discard1) && isPair(discard2)) {
          buyReservedCardList.add(currentId);
          continue;
        }

        boolean foundPair = false;

        for (BaseCard c : cards) {
          if (isPair(c) && c.getBonusColor() == card1Bonus && discard1 != c && discard2 != c) {
            foundPair = true;
            break;
          }
        }

        if (foundPair) {
          continue;
        }

        buyReservedCardList.add(currentId);

      }
    }
    return buyReservedCardList;
  }

  // 4000x0000 where x represents the deck color
  private static Collection<Long> getReservefromDeck(Game game, Player player) {
    List<Long> reserveCardsDeckList = new ArrayList<>();

    if (player.getInventory().getReservedCards().size() == 3 || game.getGoldTokenAmount() == 0) {
      return reserveCardsDeckList;
    }
    if (game.getGoldTokenAmount() > 0) {
      for (int i = 1; i < 7; i++) {

        if (getDeckFromInt(game, i) == 0) {
          continue;
        }

        Long current = 400000000_00_00_00_00L
            + i *          10000_00_00_00_00L
            + +           100000_00_00_00_00L;
        reserveCardsDeckList.add(current);

      }

    } else {
      for (int i = 1; i < 7; i++) {

        if (getDeckFromInt(game, i) == 0) {
          continue;
        }

        Long current = 400000000_00_00_00_00L
            + i * 10000_00_00_00_00L;
        reserveCardsDeckList.add(current);

      }
    }
    return reserveCardsDeckList;
  }

  private static int getDeckFromInt(Game game, int i) {
    return switch (i) {
      case 1 -> game.getGreenDeckSize();
      case 2 -> game.getYellowDeckSize();
      case 3 -> game.getBlueDeckSize();
      case 4 -> game.getRedL1DeckSize();
      case 5 -> game.getRedL2DeckSize();
      case 6 -> game.getRedL3DeckSize();
      default -> 0;
    };
  }

  private static List<Long> getNobles(Game game, Player player) {
    List<Long> nobleActions = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      NobleSlot nobleSlot = game.getNoblesOnBoard().get(i);
      if (!nobleSlot.hasNoble()) {
        continue;
      }
      Noble noble = nobleSlot.nobleInSlot();
      if (ActionController.canTakeNoble(player, noble)) {
        nobleActions.add(50L + i);
      }
    }
    return nobleActions;
  }

}
