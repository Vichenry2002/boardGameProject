package ca.mcgill.splendorserver.server;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * JUnit tests for SessionDetails class.
 */
public class TestSessionDetails {

  @Test
  public void saveGameConstructorTest() {
    List<PlayerDetails> playersDetails = Arrays.asList(
        new PlayerDetails("p1", "1"),
        new PlayerDetails("p2", "2")
    );
    SessionDetails session = new SessionDetails(
        "name", playersDetails, "p1", "save"
    );
  }

}
