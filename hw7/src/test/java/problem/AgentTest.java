package problem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AgentTest {

  Listing<AbstractProperty, AbstractContract> listing1;
  Listing<Residential, AbstractContract> listing2;
  Listing<AbstractProperty, AbstractContract> listing3;

  Agent<AbstractProperty, AbstractContract> agent1;
  Agent<Residential, AbstractContract> agent2;

  @Before
  public void setUp() throws Exception {
    listing1 = new Listing<>(new Commercial("No.1 Qinghua East Road", 220, 3, false), new Rental(600.0, true, 24));
    listing2 = new Listing<>(new Residential("No.2 Qinghua East Road", 120, 2, 1.5), new Sale(30000.0, true));
    listing3 = new Listing<>(new Residential("No.3 Qinghua East Road", 120, 2, 1.5), new Sale(30000.0, true));

    agent1 = new Agent<>("Agent1", 0.1);
    agent2 = new Agent<>("Agent2", 0.2);

    agent1.addListing(listing1);
    agent2.addListing(listing2);
  }

  @Test
  public void addListing() {
    agent1.addListing(listing3);
    assertEquals(2, agent1.getCurrentListings().size());
  }

  @Test
  public void completeListing() throws ListingNotFoundException {
    agent1.completeListing(listing1);
    assertEquals(1440.0, agent1.getTotalEarnings(), 0.01);
    agent2.completeListing(listing2);
    assertEquals(6000.0, agent2.getTotalEarnings(), 0.01);
  }

  @Test
  public void dropListing() throws ListingNotFoundException {
    agent1.dropListing(listing1);
    assertEquals(0, agent1.getCurrentListings().size());
  }

  @Test
  public void getTotalPortfolioValue() {
    agent1.addListing(listing3);
    assertEquals(4440.0, agent1.getTotalPortfolioValue(),0.01);
  }
}