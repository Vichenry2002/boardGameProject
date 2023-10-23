package ca.mcgill.splendorserver.server.gameservicedetails;

/**
 * Class used to store information about the Splendor Trading game service (includes orient).
 * This information will be sent to the Lobby Service when registering.
 */
public class TradingDetails extends GameServiceDetails {

  /**
   * Default constructor for OrientDetails class.
   */
  public TradingDetails() {
    location = super.location + "splendor_trading";
    name = super.name + "trading";
    displayName = super.displayName + "trading";
  }

}
