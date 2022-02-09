package problem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentalTest {
  private Rental ren1;

  @Before
  public void setUp() throws Exception {
    ren1 = new Rental(1500.0, false, 12);
  }

  @Test
  public void testEquals() throws InvalidTermException, InvalidAskingPriceException {
    assertTrue(ren1.equals(ren1));
    assertFalse(ren1.equals(null));
    assertFalse(ren1.equals(""));

    Rental ren2 = new Rental(1500.0, false, 8);
    assertFalse(ren1.equals(ren2));

    Rental ren3 = new Rental(2500.0, false, 12);
    assertFalse(ren1.equals(ren3));

    Rental ren4 = new Rental(1500.0, true, 12);
    assertFalse(ren1.equals(ren4));

    Rental ren5 = new Rental(1500.0, false, 12);
    assertTrue(ren1.equals(ren5));
  }

  @Test
  public void testHashCode() throws InvalidTermException, InvalidAskingPriceException {
    Rental ren5 = new Rental(1500.0, false, 12);
    assertTrue(ren1.hashCode() == ren5.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem.Rental{askingPrice=1500.0, isNegotiable=false, term=12}";
    Assert.assertEquals(expected, ren1.toString());
  }

  @Test(expected = InvalidTermException.class)
  public void invalidTerm() throws InvalidTermException, InvalidAskingPriceException {
    Rental ren = new Rental(1500.0, false, 0);
  }

  @Test(expected = InvalidAskingPriceException.class)
  public void invalidAskingPrice() throws InvalidTermException, InvalidAskingPriceException {
    Rental ren = new Rental(-1000.0, false, 0);
  }
}