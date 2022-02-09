package problem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AgentTest {
  private Listing<Residential, Sale> listing1;
  private Listing<Commercial, Rental> listing2;
  private Listing<Residential, Sale> listing3;
  private Residential r1;
  private Residential r2;
  private Commercial c1;
  private Sale s1;
  private Sale s2;
  private Rental ren1;

  private Agent<Listing<Residential, Sale>> lucy;
  private Agent<Listing<Commercial, Rental>> jimmy;

  @Before
  public void setUp() throws Exception {
    r1 = new Residential("No.1001", 90, 2, 1.5);
    r2 = new Residential("No.8888", 120, 3, 2.0);
    c1 = new Commercial("No.1002", 100, 2, true);

    s1 = new Sale(2000000.0, true);
    s2 = new Sale(750000.0, true);
    ren1 = new Rental(3000.0, false, 12);

    listing1 = new Listing<>(r1, s1);
    listing2 = new Listing<>(c1, ren1);
    listing3 = new Listing<>(r2, s2);

    lucy = new Agent<>("lucy", 0.05, 30000.0);
    jimmy = new Agent<>("jimmy", 0.06);
  }

  @Test
  public void addListing() {
    lucy.addListing(listing1);
    assertTrue(lucy.currentListings.size() == 1);
  }

  @Test
  public void dropListing() throws ListingNotFoundException {
    lucy.addListing(listing1);
    lucy.dropListing(listing1);
    assertTrue(lucy.currentListings.size() == 0);
  }

  @Test
  public void completeListing() throws ListingNotFoundException {
    lucy.addListing(listing1);
    lucy.completeListing(listing1);
    Assert.assertEquals(130000.0, lucy.totalEarnings, 0.0);
    assertTrue(lucy.currentListings.size() == 0);

    jimmy.addListing(listing2);
    jimmy.completeListing(listing2);
    Assert.assertEquals(2160.0, jimmy.totalEarnings, 0.0);
    assertTrue(jimmy.currentListings.size() == 0);
  }

  @Test
  public void getTotalPortfolioValue() {
    lucy.addListing(listing1);
    lucy.addListing(listing3);
    Assert.assertEquals(137500.0, lucy.getTotalPortfolioValue(), 0.0);

    jimmy.addListing(listing2);
    Assert.assertEquals(2160.0, jimmy.getTotalPortfolioValue(), 0.0);
  }

  @Test
  public void testEquals() throws InvalidCommissionRateException, InvalidTotalEarningsException {
    assertTrue(lucy.equals(lucy));
    assertFalse(lucy.equals(null));
    assertFalse(lucy.equals(""));

    Agent<Listing<Residential, Sale>> copy = new Agent<>("lucy", 0.05, 30000.0);
    assertTrue((lucy.equals(copy)));

    Agent<Listing<Residential, Sale>> lucy1 = new Agent<>("lucy1", 0.01, 30000.0);
    assertFalse((lucy.equals(lucy1)));

    Agent<Listing<Residential, Sale>> lucy2 = new Agent<>("lucy2", 0.05, 10000.0);
    assertFalse((lucy.equals(lucy2)));
  }

  @Test
  public void testHashCode() throws InvalidCommissionRateException, InvalidTotalEarningsException {
    Agent<Listing<Residential, Sale>> copy = new Agent<>("lucy", 0.05, 30000.0);
    assertTrue(lucy.hashCode() == copy.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem.Agent{name='lucy', currentListings=[], commissionRate=0.05, totalEarnings=30000.0}";
    Assert.assertEquals(expected, lucy.toString());
  }

  @Test(expected = InvalidCommissionRateException.class)
  public void invalidCommissionRate() throws InvalidCommissionRateException, InvalidTotalEarningsException {
    lucy = new Agent<>("lucy", -1.0, 30000.0);
  }

  @Test(expected = InvalidCommissionRateException.class)
  public void invalidCommissionRate1()
      throws InvalidCommissionRateException, InvalidTotalEarningsException {
    lucy = new Agent<>("lucy", 1.2, 30000.0);
  }

  @Test(expected = InvalidTotalEarningsException.class)
  public void invalidTotalEarnings()
      throws InvalidCommissionRateException, InvalidTotalEarningsException {
    lucy = new Agent<>("lucy", 0.05, -30000.0);
  }

  @Test(expected = ListingNotFoundException.class)
  public void listingNotFound() throws ListingNotFoundException {
    lucy.dropListing(listing3);
  }
}