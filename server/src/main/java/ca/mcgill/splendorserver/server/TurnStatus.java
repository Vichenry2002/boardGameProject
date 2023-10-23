package ca.mcgill.splendorserver.server;

/**
 * Enum for turn status.
 */
public enum TurnStatus {
  /**
   * Waiting for player to perform their turn's action.
   */
  CHOOSE_ACTION,
  /**
   * Waiting for the current player to choose a noble after
   * they have performed an action and a noble is available.
   */
  CHOOSE_NOBLE,
  /**
   * Game is over, no more actions can be performed.
   */
  GAME_OVER
}
