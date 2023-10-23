package splendor.client;

/**
 * Contains details about the user that is currently logged in, about the current game/session
 * and the ip addresses.
 */
public class GlobalUserInfo {
  private static String token;
  private static String refreshToken;
  private static String username;
  private static String password;
  private static String role;
  private static String sessionID;
  private static String serverAddress = "http://10.122.33.82:8080/";
  private static String lobbyServiceAddress = "http://10.122.33.82:4242";
  private static String gameService;

  public static void updateIpAddress(String ipAddress) {
    serverAddress = "http://" + ipAddress + ":8080/";
    lobbyServiceAddress = "http://" + ipAddress + ":4242";
  }

  /**
   * Sets the username of the user that is currently logged in.
   *
   * @param username username of current user
   */
  public static void setUsername(String username) {
    GlobalUserInfo.username = username;
  }

  /**
   * Sets the password of the user that is currently logged in.
   *
   * @param password password of current user
   */
  public static void setPassword(String password) {
    GlobalUserInfo.password = password;
  }

  /**
   * Sets the role of the user that is currently logged in.
   *
   * @param role role of current user
   */
  public static void setRole(String role) {
    GlobalUserInfo.role = role;
  }

  /**
   * Sets the token of the user that is currently logged in.
   *
   * @param token role of current user
   */
  public static void setToken(String token) {
    GlobalUserInfo.token = token;
  }

  /**
   * Clears the information about the current user when logging out.
   */
  public static void clearInfo() {
    GlobalUserInfo.username = null;
    GlobalUserInfo.token = null;
    GlobalUserInfo.password = null;
    GlobalUserInfo.refreshToken = null;
  }

  /**
   * Getter for the username of the user that is currently logged in.
   *
   * @return username of current user
   */
  public static String getUsername() {
    assert username != null;
    return username;
  }

  /**
   * Getter for the password of the user that is currently logged in.
   *
   * @return password of current user
   */
  public static String getPassword() {
    assert password != null;
    return password;
  }

  /**
   * Getter for the role of the user that is currently logged in.
   *
   * @return role of current user
   */
  public static String getRole() {
    assert role != null;
    return role;
  }

  /**
   * Getter for the token of the user that is currently logged in.
   *
   * @return token of current user
   */
  public static String getToken() {
    assert token != null;
    return token;
  }

  public static String getSessionID() {
    return sessionID;
  }

  public static void setSessionID(String id) {
    sessionID = id;
  }

  public static String getLobbyServiceAddress() {
    return lobbyServiceAddress;
  }

  public static String getServerAddress() {
    return serverAddress;
  }

  /**
   * Sets the refresh token of the user that is currently logged in.
   *
   * @param refreshToken refresh token of current user
   */
  public static void setRefreshToken(String refreshToken) {
    GlobalUserInfo.refreshToken = refreshToken;
  }

  /**
   * Getter for the refresh token of the user that is currently logged in.
   *
   * @return refresh token of current user
   */
  public static String getRefreshToken() {
    return GlobalUserInfo.refreshToken;
  }

  public static String getGameService() {
    return GlobalUserInfo.gameService;
  }

  public static void setGameService(String gameService) {
    GlobalUserInfo.gameService = gameService;
  }

}

