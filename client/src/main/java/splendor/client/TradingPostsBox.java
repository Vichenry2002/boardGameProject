package splendor.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import splendor.gameelements.Player;
import splendor.gameelements.TradingPostType;

public class TradingPostsBox {

  private final Map<TradingPostType, Integer> tradingPostsMap = new HashMap<>();
  private final Map<TradingPostType, List<TradingPostShape>> tradingPostShapes = new HashMap<>();

  public TradingPostsBox() {
    for (TradingPostType type : TradingPostType.values()) {
      tradingPostsMap.put(type, 0);
      tradingPostShapes.put(type, new ArrayList<>());
    }
  }

  public void clearTradingPosts() {

    Platform.runLater(() -> {

      for (TradingPostType type : TradingPostType.values()) {
        tradingPostsMap.put(type, 0);
        tradingPostShapes.get(type).clear();
      }

    });
  }

  public void addTradingPost(TradingPostType type, String color) {

    Platform.runLater(() -> {

      System.out.println("AAAAAAAAAAAA");

      int x, y;
      x = baseX(type) + calculateX(tradingPostsMap.get(type));
      y = calculateY(tradingPostsMap.get(type));
      tradingPostsMap.put(type, tradingPostsMap.get(type) + 1);

      TradingPostShape newShape = new TradingPostShape(x, y, color);
      tradingPostShapes.get(type).add(newShape);
      GameBoardController.getInstance().addTradingPost(newShape);

    });

  }

  private int baseX(TradingPostType type) {
    return switch (type) {
      case FREE_TOKEN_WITH_PURCHASE -> 0;
      case EXTRA_TOKEN_OTHER_COLOR -> 141;
      case DOUBLE_GOLD -> 286;
      case FIVE_PRESTIGE_POINTS -> 431;
      case PRESTIGE_PER_POST -> 566;
    };
  }

  private int calculateX(int i) {
    return (i % 2) * 52;
  }

  private int calculateY(int i) {
    return (i / 2) * 57;
  }


}
