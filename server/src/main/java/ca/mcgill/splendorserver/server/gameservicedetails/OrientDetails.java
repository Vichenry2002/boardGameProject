package ca.mcgill.splendorserver.server.gameservicedetails;

/**
 * Class used to store information about the Splendor Orient game service.
 * This information will be sent to the Lobby Service when registering.
 */
public class OrientDetails extends GameServiceDetails {

  /**
   * Default constructor for OrientDetails class.
   */
  public OrientDetails() {
    location = super.location + "splendor_orient";
    name = super.name + "orient";
    displayName = super.displayName + "orient";
  }

}
