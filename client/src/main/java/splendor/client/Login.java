package splendor.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Class that handles login authentication.
 */
public class Login {


  /**
   * Function to get the token for a user.
   *
   * @return Token of a user
   * @throws UnirestException in case of communication error
   */
  public static String getUserAccessToken(String username, String password)
      throws UnirestException {


    String location = GlobalUserInfo.getLobbyServiceAddress() + "/oauth/token";

    String request = "grant_type=password&username=" + username + "&password=" + password;

    HttpResponse<String> response = Unirest.post(location)
        .header("Authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=")
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(request).asString();

    if (response.getStatus() == 200) {
      JsonObject tokenJson = JsonParser.parseString(response.getBody()).getAsJsonObject();
      String token = tokenJson.get("access_token").toString();
      String refreshToken = tokenJson.get("refresh_token").toString();

      String tokenFixed = token
          .replaceAll("\"", "");
          //.replace("+", "%2B");;

      String refreshTokenFixed = refreshToken
          .replaceAll("\"", "");
          //.replace("+", "%2B");;

      GlobalUserInfo.setToken(tokenFixed);
      GlobalUserInfo.setRefreshToken(refreshTokenFixed);
      return tokenFixed;
      //return token.replaceAll("\"", "");
    }

    else {
      return "error";
    }

  }

  /**
   * Gets the role of the user corresponding to the provided token. The role can be ROLE_PLAYER,
   * ROLE_ADMIN or ROLE_SERVICE. Returns an empty string if the token is not a valid token.
   *
   * @param token the authentication token of the user
   * @return the role of the user
   */
  public static String getRoleFromToken(String token) {
    try {
      String location = GlobalUserInfo.getLobbyServiceAddress() + "/oauth/role";
      String authorization = "Bearer " + token;

      HttpResponse<String> response = Unirest.get(location)
          .header("Authorization", authorization)
          .asString();

      RoleBody roleBody[] = new Gson().fromJson(response.getBody(), RoleBody[].class);

      return roleBody[0].authority;

    } catch (UnirestException e) {
      return "";
    }
  }

  /**
   * Inner class to store the role of a user when requesting the role of a user from a token.
   */
  public class RoleBody {
    /**
     * Role of the user corresponding to this instance.
     */
    public String authority;

    /**
     * Constructor for RoleBody inner class
     *
     * @param authority role of user corresponding to this instance
     */
    RoleBody(String authority) {
      this.authority = authority;
    }
  }

  /**
   * Function to get the token for a user from a refresh token
   *
   * @return Token of a user
   * @throws UnirestException in case of communication error
   */
  public static String refreshUserAccessToken(String refreshToken)
      throws UnirestException {


    String location = GlobalUserInfo.getLobbyServiceAddress() + "/oauth/token";

    String request = "grant_type=refresh_token&refresh_token=" + refreshToken;

    HttpResponse<String> response = Unirest.post(location)
        .header("Authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=")
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(request).asString();

    if (response.getStatus() == 200) {
      JsonObject tokenJson = JsonParser.parseString(response.getBody()).getAsJsonObject();
      String token = tokenJson.get("access_token").toString();
      String newRefreshToken = tokenJson.get("refresh_token").toString();

      String tokenFixed = token
          .replaceAll("\"", "");
          //.replace("+", "%2B");;

      String newRefreshTokenFixed = newRefreshToken
          .replaceAll("\"", "");
          //.replace("+", "%2B");;

      GlobalUserInfo.setToken(tokenFixed);
      GlobalUserInfo.setRefreshToken(newRefreshTokenFixed);
      return tokenFixed;
    }

    else {
      return "error";
    }

  }




}
