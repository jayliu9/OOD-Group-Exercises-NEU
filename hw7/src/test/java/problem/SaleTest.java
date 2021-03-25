package problem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SaleTest {
  private Sale s1;

  @Before
  public void setUp() throws Exception {
    s1 = new Sale(2000000.0, true);
  }

  @Test
  public void testEquals() throws InvalidAskingPriceException {
    assertTrue(s1.equals(s1));
    assertFalse(s1.equals(null));
    assertFalse(s1.equals(""));

    Sale s2 = new Sale(1000000.0, true);
    assertFalse(s1.equals(s2));

    Sale s3 = new Sale(2000000.0, false);
    assertFalse(s1.equals(s3));

    Sale s4 = new Sale(2000000.0, true);
    assertTrue(s1.equals(s4));
  }

  @Test
  public void testHashCode() throws InvalidAskingPriceException {
    Sale s4 = new Sale(2000000.0, true);
    assertTrue(s1.hashCode() == s4.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem.Sale{askingPrice=2000000.0, isNegotiable=true}";
    Assert.assertEquals(expected, s1.toString());
  }
}