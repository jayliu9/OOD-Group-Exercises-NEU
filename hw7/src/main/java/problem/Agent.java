package problem;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Agent<T extends AbstractProperty, U extends AbstractContract> {

  private String name;
  private Map<T, U> currentListings;
  private double commissionRate;
  private double totalEarnings;

  public Agent(String name, double commissionRate)
      throws InvalidCommissionRateException {
    this.name = name;
    this.currentListings = new HashMap<>();
    this.commissionRate = this.validateCommissionRate(commissionRate);
    this.totalEarnings = 0;
  }

  /**
   * Checks that whether the commissionRate is valid.
   * @param commissionRate The commissionRate to be checked.
   * @return The commissionRate to be checked.
   * @throws InvalidCommissionRateException if the commissionRate is invalid.
   */
  private double validateCommissionRate(double commissionRate)
      throws InvalidCommissionRateException {
    if (commissionRate > 0 && commissionRate <= 1) {
      return commissionRate;
    }
    throw new InvalidCommissionRateException();
  }

  public String getName() {
    return this.name;
  }

  public Map<T, U> getCurrentListings() {
    return this.currentListings;
  }

  public double getCommissionRate() {
    return this.commissionRate;
  }

  public double getTotalEarnings() {
    return this.totalEarnings;
  }

  /**
   * Adds an(appropriate) listing to the Agent’s current listing collection.
   * @param listing A listing consisting of a property and a contract.
   */
  public void addListing(Listing<T, U> listing) {
    this.currentListings.put(listing.getProperty(), listing.getContract());
  }

  /**
   * This method will be called when an Agent successfully makes a sale / rental of one of their
   * listings. Assuming the listing is part of their collection, this method should remove the
   * Listing from their collection and add their commission earnings to their total earnings amount.
   * @param listing A listing consisting of a property and a contract.
   */
  public void completeListing(Listing<T, U> listing) throws ListingNotFoundException {
    this.dropListing(listing);
    double commission = this.commissionRate * listing.getCommission();
    this.totalEarnings += commission;
  }

  /**
   * Drop a listing from the Agent’s collection without adjusting their earnings.
   * @param listing A listing consisting of a property and a contract.
   * @throws ListingNotFoundException if the listing is not in the currentListings.
   */
  public void dropListing(Listing<T, U> listing) throws ListingNotFoundException {
    if (this.contains(listing)) {
      this.currentListings.remove(listing.getProperty(), listing.getContract());
    } else {
      throw new ListingNotFoundException();
    }
  }

  /**
   * Helper method that checks whether the currentListings contains a listing.
   * @param listing The listing to be checked.
   * @return True if the currentListings contains the listing, false otherwise.
   * @throws ListingNotFoundException if the listing is not in the currentListings.
   */
  private boolean contains(Listing<T, U> listing) {
    if (this.currentListings.containsKey(listing.getProperty()) &&
        this.currentListings.get(listing.getProperty()).equals(listing.getContract())) {
      return true;
    }
    return false;
  }

  /**
   * This returns the amount of money the Agent would make if they successfully completed all
   * listings in their collection.
   * @return the amount of money the Agent would make if they successfully completed all listings
   * in their collection.
   */
  public double getTotalPortfolioValue() {
    double totalPortfolioValue = 0;
    for (U value : this.currentListings.values()) {
      totalPortfolioValue += this.commissionRate * value.getCommission();
    }
    return totalPortfolioValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Agent)) {
      return false;
    }
    Agent<?, ?> agent = (Agent<?, ?>) o;
    return Double.compare(agent.commissionRate, commissionRate) == 0
        && Double.compare(agent.totalEarnings, totalEarnings) == 0 && Objects
        .equals(name, agent.name) && Objects.equals(currentListings, agent.currentListings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, currentListings, commissionRate, totalEarnings);
  }

  @Override
  public String toString() {
    return "Agent{" +
        "name='" + name + '\'' +
        ", currentListings=" + currentListings +
        ", commissionRate=" + commissionRate +
        ", totalEarnings=" + totalEarnings +
        '}';
  }
}
