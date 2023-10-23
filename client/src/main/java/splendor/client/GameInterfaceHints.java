package splendor.client;

import javafx.scene.control.Label;

public class GameInterfaceHints {

  private static TurnStatus   turnStatus;


  public static void signalChange() {
    SelectionStatus status = GameBoardController.getInstance().getSelectionStatus();

    if (status == SelectionStatus.CLICK_L1) {
      clickL1Card();
    } else if (status == SelectionStatus.CLICK_L2) {
      clickL2Card();
    } else if (status == SelectionStatus.CLICK_DISCARD) {
      chooseDiscard();
    } else if (status == SelectionStatus.CLICK_NOBLE) {
      chooseNobleR();
    } else if (status == SelectionStatus.CHOOSE_FREE_TOKEN) {
      freeToken();
    } else if (status == SelectionStatus.CLICK_PAIR || status == SelectionStatus.CLICK_PAIR_L1) {
      clickCardInInventory();
    } else if (turnStatus == TurnStatus.NOBLE) {
      chooseNobleC();
    } else if (turnStatus == TurnStatus.CITY) {
      chooseCity();
    } else {
      hide();
    }
  }

  public static void changeTurnStatus(TurnStatus newStatus) {
    turnStatus = newStatus;
    signalChange();
  }

  private static void clickCardInInventory() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose a previously purchased card to pair this card to");
    hint.setVisible(true);
  }

  private static void clickL1Card() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose a level 1 card to take");
    hint.setVisible(true);
  }

  private static void clickL2Card() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose a level 2 card to take");
    hint.setVisible(true);
  }

  private static void chooseNobleR() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose a noble to reserve");
    hint.setVisible(true);
  }

  private static void chooseNobleC() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose a noble to claim");
    hint.setVisible(true);
  }

  private static void chooseCity() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose a city to claim");
    hint.setVisible(true);
  }

  private static void chooseDiscard() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Choose bonuses to discard");
    hint.setVisible(true);
  }

  private static void freeToken() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setText("Trading post: choose a free token");
    hint.setVisible(true);
  }

  private static void hide() {
    Label hint = GameBoardController.getInstance().hint;
    hint.setVisible(false);
  }

}
