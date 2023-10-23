package splendor.gameelements;

/**
 * TODO add description.
 */
public record CardData() {

  public static final String[] greenCards = {
      "WHITE,0,0,4,0,0,1,White1.png",
      "BLUE,0,0,0,4,0,1,Blue1.png",
      "GREEN,0,0,0,0,4,1,Green1.png",
      "RED,4,0,0,0,0,1,Red1.png",
      "BLACK,0,4,0,0,0,1,Black1.png",

      "WHITE,3,1,0,0,1,0,White2.png",
      "BLUE,0,1,3,1,0,0,Blue2.png",
      "GREEN,1,3,1,0,0,0,Green2.png",
      "RED,1,0,0,1,3,0,Red2.png",
      "BLACK,0,0,1,3,1,0,Black2.png",

      "WHITE,0,3,0,0,0,0,White3.png",
      "BLUE,0,0,0,0,3,0,Blue3.png",
      "GREEN,0,0,0,3,0,0,Green3.png",
      "RED,3,0,0,0,0,0,Red3.png",
      "BLACK,0,0,3,0,0,0,Black3.png",

      "WHITE,0,2,2,0,1,0,White4.png",
      "BLUE,1,0,2,2,0,0,Blue4.png",
      "GREEN,0,1,0,2,2,0,Green4.png",
      "RED,2,0,1,0,2,0,Red4.png",
      "BLACK,2,2,0,1,0,0,Black4.png",

      "WHITE,0,2,0,0,2,0,White5.png",
      "BLUE,0,0,2,0,2,0,Blue5.png",
      "GREEN,0,2,0,2,0,0,Green5.png",
      "RED,2,0,0,2,0,0,Red5.png",
      "BLACK,2,0,2,0,0,0,Black5.png",

      "WHITE,0,1,2,1,1,0,White6.png",
      "BLUE,1,0,1,2,1,0,Blue6.png",
      "GREEN,1,1,0,1,2,0,Green6.png",
      "RED,2,1,1,0,1,0,Red6.png",
      "BLACK,1,2,1,1,0,0,Black6.png",

      "WHITE,0,0,0,2,1,0,White7.png",
      "BLUE,1,0,0,0,2,0,Blue7.png",
      "GREEN,2,1,0,0,0,0,Green7.png",
      "RED,0,2,1,0,0,0,Red7.png",
      "BLACK,0,0,2,1,0,0,Black7.png",

      "WHITE,0,1,1,1,1,0,White8.png",
      "BLUE,1,0,1,1,1,0,Blue8.png",
      "GREEN,1,1,0,1,1,0,Green8.png",
      "RED,1,1,1,0,1,0,Red8.png",
      "BLACK,1,1,1,1,0,0,Black8.png"

  };

  public static final String[] yellowCards = {
      "WHITE,0,0,3,2,2,1,White1.png",
      "BLUE,0,2,2,3,0,1,Blue1.png",
      "GREEN,2,3,0,0,2,1,Green1.png",
      "RED,2,0,0,2,3,1,Red1.png",
      "BLACK,3,2,2,0,0,1,Black1.png",
      //
      "WHITE,2,3,0,3,0,1,White2.png",
      "BLUE,0,2,3,0,3,1,Blue2.png",
      "GREEN,3,0,2,3,0,1,Green2.png",
      "RED,0,3,0,2,3,1,Red2.png",
      "BLACK,3,0,3,0,2,1,Black2.png",
      //
      "WHITE,6,0,0,0,0,3,White3.png",
      "BLUE,0,6,0,0,0,3,Blue3.png",
      "GREEN,0,0,6,0,0,3,Green3.png",
      "RED,0,0,0,6,0,3,Red3.png",
      "BLACK,0,0,0,0,6,3,Black3.png",
      //
      "WHITE,0,0,1,4,2,2,White4.png",
      "BLUE,2,0,0,1,4,2,Blue4.png",
      "GREEN,4,2,0,0,1,2,Green4.png",
      "RED,1,4,2,0,0,2,Red4.png",
      "BLACK,0,1,4,2,0,2,Black4.png",
      //
      "WHITE,0,0,0,5,0,2,White5.png",
      "BLUE,0,5,0,0,0,2,Blue5.png",
      "GREEN,0,0,5,0,0,2,Green5.png",
      "RED,0,0,0,0,5,2,Red5.png",
      "BLACK,5,0,0,0,0,2,Black5.png",
      "WHITE,0,0,0,5,3,2,White6.png",
      "BLUE,5,3,0,0,0,2,Blue6.png",
      "GREEN,0,5,3,0,0,2,Green6.png",
      "RED,3,0,0,0,5,2,Red6.png",
      "BLACK,0,0,5,3,0,2,Black6.png"
  };

  public static final String[] blueCards = {
      "WHITE,0,3,3,5,3,3,White1.png",
      "BLUE,3,0,3,3,5,3,Blue1.png",
      "GREEN,5,3,0,3,3,3,Green1.png",
      "RED,3,5,3,0,3,3,Red1.png",
      "BLACK,3,3,5,3,0,3,Black1.png",
      "WHITE,0,0,0,0,7,4,White2.png",
      "BLUE,7,0,0,0,0,4,Blue2.png",
      "GREEN,0,7,0,0,0,4,Green2.png",
      "RED,0,0,7,0,0,4,Red2.png",
      "BLACK,0,0,0,7,0,4,Black2.png",
      //
      "WHITE,3,0,0,3,6,4,White3.png",
      "BLUE,6,3,0,0,3,4,Blue3.png",
      "GREEN,3,6,3,0,0,4,Green3.png",
      "RED,0,3,6,3,0,4,Red3.png",
      "BLACK,0,0,3,6,3,4,Black3.png",
      //
      "WHITE,3,0,0,0,7,5,White4.png",
      "BLUE,7,3,0,0,0,5,Blue4.png",
      "GREEN,0,7,3,0,0,5,Green4.png",
      "RED,0,0,7,3,0,5,Red4.png",
      "BLACK,0,0,0,7,3,5,Black4.png"

  };

  /**
   * Red level 1 cards.
   */
  public static final String[] redL1Cards = {


      "GOLD,0,0,3,0,0,0,DOUBLE_GOLD,DoubleGold1.png",
      "GOLD,0,3,0,0,0,0,DOUBLE_GOLD,DoubleGold2.png",
      "GOLD,3,0,0,0,0,0,DOUBLE_GOLD,DoubleGold3.png",
      "GOLD,0,0,0,0,3,0,DOUBLE_GOLD,DoubleGold4.png",
      "GOLD,0,0,0,3,0,0,DOUBLE_GOLD,DoubleGold5.png",


      "NONE,0,3,2,0,0,0,PAIR_CARD,Pair1.png",
      "NONE,0,0,3,0,2,0,PAIR_CARD,Pair2.png",
      "NONE,2,0,0,0,3,0,PAIR_CARD,Pair3.png",
      "NONE,0,2,0,3,0,0,PAIR_CARD,Pair4.png",
      "NONE,3,0,0,2,0,0,PAIR_CARD,Pair5.png"

  };

  /**
   * Red level 2 cards.
   */
  public static final String[] redL2Cards = {

      "NONE,4,3,0,0,1,0,TAKE_L1_CARD,L1Cascade1.png",
      "NONE,0,0,4,3,1,0,TAKE_L1_CARD,L1Cascade2.png",
      "RED,2,2,2,0,2,1,RESERVE_NOBLE,ReserveNoble1.png",
      "GREEN,2,2,0,2,2,1,RESERVE_NOBLE,ReserveNoble2.png",
      "WHITE,0,2,2,2,2,1,RESERVE_NOBLE,ReserveNoble3.png",
      //
      "BLACK,0,3,0,4,0,0,DOUBLE_BONUS,DoubleBonus1.png",
      "RED,0,0,4,0,3,0,DOUBLE_BONUS,DoubleBonus2.png",
      "GREEN,4,0,0,3,0,0,DOUBLE_BONUS,DoubleBonus3.png",
      "WHITE,0,4,3,0,0,0,DOUBLE_BONUS,DoubleBonus4.png",
      "BLUE,3,0,0,0,4,0,DOUBLE_BONUS,DoubleBonus5.png",


  };

  /**
   * Red level 3 cards.
   */
  public static final String[] redL3Cards = {

      "BLACK,6,3,0,0,1,0,TAKE_L2_CARD,L2Cascade1.png",
      "RED,3,0,0,1,6,0,TAKE_L2_CARD,L2Cascade2.png",
      "GREEN,0,0,1,6,3,0,TAKE_L2_CARD,L2Cascade3.png",
      "BLUE,0,1,6,3,0,0,TAKE_L2_CARD,L2Cascade4.png",
      "WHITE,1,6,3,0,0,0,TAKE_L2_CARD,L2Cascade5.png",
      //
      "BLUE,2,0,0,0,0,3,DISCARD_BONUSES,Discard1.png",
      "WHITE,0,0,0,0,2,3,DISCARD_BONUSES,Discard2.png",

      "RED,0,0,2,0,0,3,DISCARD_BONUSES,Discard3.png",
      "BLACK,0,0,0,2,0,3,DISCARD_BONUSES,Discard4.png",
      "GREEN,0,2,0,0,0,3,DISCARD_BONUSES,Discard5.png",


  };

}
