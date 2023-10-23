package splendor.client.admin;

/**
 * Class to store information about a user when an admin request details about all users.
 */
public class User {
  public String name;
  public String password;
  public String preferredColour;
  public String role;

  /**
   * Constructor for User class, sets fields.
   *
   * @param name the name of the user
   * @param password the encrypted password of the user
   * @param preferredColour the colour of the user
   * @param role the role of the user
   */
  public User(String name, String password, String preferredColour, String role) {
    this.name = name;
    this.password = password;
    this.preferredColour = preferredColour;
    this.role = role;
  }

  /**
   * Getter for the name of the user.
   *
   * @return username
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the password of the user (encrypted).
   *
   * @return user password (encrypted)
   */
  public String getPassword() {
    return password;
  }

  /**
   * Getter for the prefered color of the user.
   *
   * @return hex string of the color
   */
  public String getPreferredColour() {
    return preferredColour;
  }

  /**
   * Getter for the role of the user (player, admin, service).
   *
   * @return role of the user.
   */
  public String getRole() {
    // Gets the last part of the role string received from the Lobby Service
    // ex. "PLAYER" if it was "ROLE_PLAYER"
    String roleStr = role.split("_")[1];
    // Keep only the first character in uppercase
    String result = roleStr.substring(0,1).toUpperCase()
        + roleStr.substring(1).toLowerCase();
    return result;
  }
}
