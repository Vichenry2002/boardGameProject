package splendor.client.admin;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import splendor.client.Game;
import splendor.client.GlobalUserInfo;
import splendor.client.Login;

/**
 * Class which sends HTTP requests to the Lobby Service related to the admin menu.
 */
public class AdminRequest {

  /**
   * Sends a REST GET request to the users endpoint of the Lobby Service to obtain a list of the
   * users registered with the lobby service.
   *
   * @return list of User objects containing information about each user
   * @throws UnirestException if the Lobby Service could not be reached
   */
  public static User[] getUsers() throws UnirestException {
    String location = GlobalUserInfo.getLobbyServiceAddress().concat("/api/users");
    com.mashape.unirest.http.HttpResponse<String> response = null;
    int status;

    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
          GlobalUserInfo.getUsername(),
          GlobalUserInfo.getPassword());
      //.replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    response = Unirest.get(location).queryString("access_token", accessToken).asString();
    status = response.getStatus();

    System.out.println("GET users ::  status: " + status + " - message: " + response.getBody());

    if (status == 200) {
      String responseString = response.getBody();
      User[] users = new Gson().fromJson(responseString, User[].class);

      return users;
    } else {
      System.out.println("Error getting list of users | invalid admin credentials");
      return null;
    }
  }

  /**
   * Sends a REST GET request to the user endpoint of the Lobby Service to obtain the details of a
   * user registered with the lobby service.
   *
   * @param username the name of the user to get the details of
   * @return User object containing information about a user
   * @throws UnirestException if the Lobby Service could not be reached
   */
  public static User getUser(String username) throws UnirestException {
    String location = GlobalUserInfo.getLobbyServiceAddress()
        .concat("/api/users/")
        .concat(username);
    com.mashape.unirest.http.HttpResponse<String> response = null;
    int status;

    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
              .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    response = Unirest.get(location).queryString("access_token", accessToken).asString();
    status = response.getStatus();

    if (status == 200) {
      String responseString = response.getBody();
      User user = new Gson().fromJson(responseString, User.class);

      return user;

    } else {
      System.out.println("Error getting user | invalid admin credentials");
      return null;
    }
  }

  /**
   * Sends a REST PUT request to the user endpoint of the Lobby Service to register a new user with
   * the Lobby Service
   *
   * @param username the name of the user
   * @param password the password of the user
   * @param color the preferred color of the user
   * @param role the role of the user (player, admin, service)
   */
  public static void createUser(String username, String password, String color, String role) {
    User user = new User(username, password, color, role);

    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
              .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    try {
      String location = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/users/" + username
          + "?access_token=" + accessToken;
      String requestBody = new Gson().toJson(user);

      com.mashape.unirest.http.HttpResponse<String> response = Unirest.put(location)
          .header("Content-Type", "application/json")
          .body(requestBody)
          .asString();

      System.out.println("PUT user ::  status: "
          + response.getStatus() + " - message: " + response.getBody());

    } catch (UnirestException e) {
      System.out.println("Lobby Service could not be reached");
    }
  }

  /**
   * Sends a REST DELETE request to the user endpoint of the Lobby Service to delete an existing
   * user.
   *
   * @param username the name of the user
   */
  public static void deleteUser(String username) {
    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
          .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    try {
      String location = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/users/" + username
          + "?access_token=" + accessToken;

      com.mashape.unirest.http.HttpResponse<String> response = Unirest.delete(location).asString();
    } catch (UnirestException e) {
      System.out.println("Lobby Service could not be reached");
    }
  }

  /**
   * Sends a REST POST request to the password endpoint of the Lobby Service to change the password
   * of a user
   *
   * @param username the name of the user to update the password of
   * @param newPassword the new password
   */
  public static void changePassword(String username, String newPassword) {
    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
          .replace("+", "%2B");
//        accessToken = Login.refreshUserAccessToken(refreshToken)
//            .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    String urlStr = GlobalUserInfo.getLobbyServiceAddress()
        + "/api/users/" + username
        + "/password"
        + "?access_token=" + accessToken;

    String requestBody = (new PasswordRequestBody(newPassword, "")).asJson();

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(urlStr))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .header("Content-Type","application/json")
        .build();

    HttpResponse<String> response = null;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
    
  }

  /**
   * Sends a REST GET request to the colour endpoint of the Lobby Service to get the preferred
   * colour of a user.
   *
   * @param username the name of the user
   * @return hex string of the colour
   * @throws UnirestException if the Lobby Service could not be reached
   */
  public static String getColour(String username) throws UnirestException {
    String location = GlobalUserInfo.getLobbyServiceAddress()
        .concat("/api/users/")
        .concat(username)
        .concat("/colour");
    com.mashape.unirest.http.HttpResponse<String> response = null;
    int status;

    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
          .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    response = Unirest.get(location).queryString("access_token", accessToken).asString();
    status = response.getStatus();

    if (status == 200) {
      String responseString = response.getBody();
      ColourRequestBody colourRequestBody = new Gson()
          .fromJson(responseString, ColourRequestBody.class);

      String colour = colourRequestBody.colour;
      return colour;

    } else {
      System.out.println("Error getting user | invalid admin credentials");
      return null;
    }
  }

  /**
   * Sends a REST POST request to the colour endpoint of the Lobby Service to change the preferred
   * colour of a user.
   *
   * @param username the name of the user
   * @param newColour the colour to change to
   */
  public static void changeColour(String username, String newColour) {
    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
          .replace("+", "%2B");
//        accessToken = Login.refreshUserAccessToken(refreshToken)
//            .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    String urlStr = GlobalUserInfo.getLobbyServiceAddress()
        + "/api/users/" + username
        + "/colour"
        + "?access_token=" + accessToken;

    String requestBody = (new ColourRequestBody(newColour)).asJson();

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(urlStr))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .header("Content-Type","application/json")
        .build();

    HttpResponse<String> response = null;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("POST colour ::  status: "
          + response.statusCode() + " - message: " + response.body());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }

  }

  /**
   * Sends a REST DELETE request to the gameservice endpoint of the Lobby Service to unregister a
   * game service.
   *
   * @param serviceName the name of the game service to unregister
   */
  public static void deleteService(String serviceName) {
    String refreshToken = GlobalUserInfo.getRefreshToken();
    String accessToken = null;
    try {
      accessToken = Login.getUserAccessToken(
              GlobalUserInfo.getUsername(),
              GlobalUserInfo.getPassword())
          .replace("+", "%2B");
    } catch (UnirestException ex) {
      throw new RuntimeException(ex);
    }

    try {
      String location = GlobalUserInfo.getLobbyServiceAddress()
          + "/api/gameservices/" + serviceName
          + "?access_token=" + accessToken;

      com.mashape.unirest.http.HttpResponse<String> response = Unirest.delete(location).asString();
      System.out.println(location);
      System.out.println("DELETE gameservice request :: Status: "
          + response.getStatus() + " - Body: " + response.getBody()) ;
    } catch (UnirestException e) {
      System.out.println("Lobby Service could not be reached");
    }
  }

  /**
   * Sends a REST GET request to the gameservices endpoint of the Lobby Service to get a list of
   * all the game services that are currently registered.
   *
   * @return list of GameService objects containing the information of each game service
   * @throws UnirestException if  the Lobby Service could not be reached
   */
  public static List<GameService> getServices() throws UnirestException {
    String location = GlobalUserInfo.getLobbyServiceAddress()
        .concat("/api/gameservices");
    com.mashape.unirest.http.HttpResponse<String> response = null;

    response = Unirest.get(location).asString();
    String responseString = response.getBody();

    GameServiceName[] names = new Gson().fromJson(responseString, GameServiceName[].class);

    List<GameService> services = new ArrayList<>();
    for (GameServiceName serviceName : names) {
      services.add(getService(serviceName.getName()));
    }

    return services;
  }

  /**
   * Sends a REST GET request to the gameservice endpoint of the Lobby Service to get the details
   * of a registered game service.
   *
   * @param gameService the name of the game service.
   * @return GameService object containing the information of the game service
   * @throws UnirestException if the Lobby Service could not be reached
   */
  public static GameService getService(String gameService) throws UnirestException {
    String location = GlobalUserInfo.getLobbyServiceAddress()
        .concat("/api/gameservices/")
        .concat(gameService);
    com.mashape.unirest.http.HttpResponse<String> response = null;

    response = Unirest.get(location).asString();
    String responseString = response.getBody();

    GameService service = new Gson().fromJson(responseString, GameService.class);
    return service;
  }

}
