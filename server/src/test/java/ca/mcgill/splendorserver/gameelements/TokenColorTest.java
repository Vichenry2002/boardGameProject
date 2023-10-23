package ca.mcgill.splendorserver.gameelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * TokenColor Junit Tests
 */
public class TokenColorTest {

  @Test
  public void tokenColorTest(){
    TokenColor color = TokenColor.BLACK;
    assertEquals(TokenColor.BLACK, color);
    assertNotEquals(TokenColor.WHITE, color);
    assertNotEquals(TokenColor.GREEN, color);
    assertNotEquals(TokenColor.GOLD, color);
    assertNotEquals(TokenColor.BLUE, color);
    assertNotEquals(TokenColor.WHITE, color);

  }

}
