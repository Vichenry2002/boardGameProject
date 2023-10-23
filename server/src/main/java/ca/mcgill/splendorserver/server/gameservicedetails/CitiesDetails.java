package ca.mcgill.splendorserver.server.gameservicedetails;

/**
 * Class used to store information about the Splendor Cities game service (includes orient).
 * This information will be sent to the Lobby Service when registering.
 */
public class CitiesDetails extends GameServiceDetails {

  /**
   * Default constructor for OrientDetails class.
   */
  public CitiesDetails() {
    location = super.location + "splendor_cities";
    name = super.name + "cities";
    displayName = super.displayName + "cities";
  }

}
