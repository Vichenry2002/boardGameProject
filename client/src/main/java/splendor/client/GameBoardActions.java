package splendor.client;

/**
 * Methods called from the GameBoardController when performing certain actions.
 */
public class GameBoardActions {

  /**
   * This method handles the logic when clicking on the '+' or '-' button of a token on the board.
   * Decides whether to select or deselect items depending on the Splendor game rules.
   *
   * @param color The color of the token clicked
   * @param button The button clicked (0 = +, 1 = -)
   */
  public static void changeTokens(int color, int button) {
    if (CardPopupController.getInstance().isVisible()) {
      return;
    }

    if (GameBoardController.getInstance().getSelectionStatus() == SelectionStatus.CHOOSE_FREE_TOKEN
        && button == 0) {
      PlayerSelfController.getInstance().setFreeToken(color);
      GameBoardController.getInstance().select("viewToken".concat(String.valueOf(color)));
      GameBoardController.getInstance().setSelectedTokens(color, 1);
      GameBoardController.getInstance().setSelectionStatus(SelectionStatus.NORMAL);
      PlayerSelfController.getInstance().confirmActionSetActive();
      return;
    }
    int board = Game.getInstance().colorTokens.get(color);
    int selected = GameBoardController.getInstance().getSelectedTokens(color);
    int selectedTotal = 0;
    int doubleSelection = 0;
    for (int i = 0; i < 5; i++) {
      selectedTotal = selectedTotal + GameBoardController.getInstance().getSelectedTokens(i);
      if (GameBoardController.getInstance().getSelectedTokens(i) == 2) {
        doubleSelection = 1;
      }
    }
    if (selected == 0 && board > 0 && selectedTotal < 3 && button == 0 && doubleSelection == 0) {
      if (selectedTotal == 0) {
        PlayerSelfController.getInstance().onClickDeselectAll();
      }
      GameBoardController.getInstance().deselectCards();
      PlayerSelfController.getInstance().resetExtra();
      GameBoardController.getInstance().deselect("viewTokenGold");
      GameBoardController.getInstance().setSelectedTokens(color, 1);
      if (selectedTotal == 2) {
        GameBoardController.getInstance().setSelectedAction(ActionType.TAKE_THREE_TOKENS);
        PlayerSelfController.getInstance().confirmActionSetActive();
      } else {
        PlayerSelfController.getInstance().confirmActionSetNotActive();
      }
      GameBoardController.getInstance().select("viewToken".concat(String.valueOf(color)));
    } else if (selected == 1 && board > 3 && selectedTotal == 1 && button == 0) {
      GameBoardController.getInstance().deselectCards();
      GameBoardController.getInstance().deselect("viewTokenGold");
      GameBoardController.getInstance().setSelectedTokens(color, 2);
      GameBoardController.getInstance().setSelectedAction(ActionType.TAKE_TWO_TOKENS);
      PlayerSelfController.getInstance().confirmActionSetActive();
    } else if (selected > 0 && button == 1) {
      GameBoardController.getInstance().setSelectedTokens(color, selected - 1);
      PlayerSelfController.getInstance().confirmActionSetNotActive();
      if (selected == 1) {
        GameBoardController.getInstance().deselect("viewToken".concat(String.valueOf(color)));
      }
    }
  }

  /**
   * Resets the selected tokens to 0.
   */
  public static void resetSelectedTokens() {
    GameBoardController.getInstance().setSelectedTokens(0, 0);
    GameBoardController.getInstance().setSelectedTokens(1, 0);
    GameBoardController.getInstance().setSelectedTokens(2, 0);
    GameBoardController.getInstance().setSelectedTokens(3, 0);
    GameBoardController.getInstance().setSelectedTokens(4, 0);
  }
}
