package problem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListingTest {
  private Listing<Residential, Sale> listing1;
  private Residential r1;
  private Residential r2;
  private Sale s1;
  private Sale s2;

  @Before
  public void setUp() throws Exception {
    r1 = new Residential("No.1001", 90, 2, 1.5);
    r2 = new Residential("No.1002", 100, 3, 2.0);

    s1 = new Sale(2000000.0, true);
    s2 = new Sale(1000000.0, false);

    listing1 = new Listing<>(r1, s1);
  }

  @Test
  public void testEquals() {
    assertTrue(listing1.equals(listing1));
    assertFalse(listing1.equals(null));
    assertFalse(listing1.equals(""));

    Listing<Residential, Sale> listing3 = new Listing<>(r1, s2);
    assertFalse(listing1.equals(listing3));

    Listing<Residential, Sale> listing4 = new Listing<>(r2, s1);
    assertFalse(listing1.equals(listing4));

    Listing<Residential, Sale> listing5 = new Listing<>(r1, s1);
    assertTrue(listing1.equals(listing5));
  }

  @Test
  public void testHashCode() {
    Listing<Residential, Sale> listing5 = new Listing<>(r1, s1);
    assertTrue(listing1.hashCode() == listing5.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem.Listing{property=problem.Residential{address='No.1001', size=90, numOfBedrooms=2, "
        + "numOfBathrooms=1.5}, contract=problem.Sale{askingPrice=2000000.0, isNegotiable=true}}";
    Assert.assertEquals(expected, listing1.toString());
  }
}