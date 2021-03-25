package problem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing an problem.Agent responsible for adding listings to the company site.
 *
 * @param <T> the problem.Listing
 */
public class Agent<T extends Listing> {

  protected String name;
  protected Set<T> currentListings;
  protected Double commissionRate;
  protected Double totalEarnings;

  /**
   * Constructor for the problem.Agent.
   *
   * @param name           The name of agent
   * @param commissionRate The commission rate of the agent, represented as a Double between 0 and 1
   *                       (inclusive).This is the percentage of the contract amount that the agent
   *                       takes as payment if they successfully sell / rent the property in one of
   *                       their listings.
   * @throws InvalidCommissionRateException if the commissionRate is invalid.
   */
  public Agent(String name, Double commissionRate)
      throws InvalidCommissionRateException {
    this.name = name;
    this.currentListings = new HashSet<>();
    this.commissionRate = this.validateCommissionRate(commissionRate);
    this.totalEarnings = 0.0;
  }

  /**
   * Instantiates a new problem.Agent.
   *
   * @param name           The name of agent.
   * @param commissionRate The commission rate of the agent, represented as a Double between 0 and 1
   *                       (inclusive).This is the percentage of the contract amount that the agent
   *                       takes as payment if they successfully sell / rent the property in one of
   *                       their listings.
   * @param totalEarnings  The total earnings, represented as a non-negative Double. This is the
   *                       total amount the problem.Agent has earned from their sales / rentals.
   * @throws InvalidCommissionRateException if the commissionRate is invalid.
   * @throws InvalidTotalEarningsException  if the totalEarnings is invalid.
   */
  public Agent(String name, Double commissionRate, Double totalEarnings)
      throws InvalidCommissionRateException, InvalidTotalEarningsException {
    this.name = name;
    this.currentListings = new HashSet<>();
    this.commissionRate = this.validateCommissionRate(commissionRate);
    this.totalEarnings = this.validateTotalEarnings(totalEarnings);
  }

  /**
   * Checks whether or not the commissionRate is valid.
   *
   * @param commissionRate The commissionRate to be checked.
   * @return The commissionRate to be checked.
   * @throws InvalidCommissionRateException if the commissionRate is invalid.
   */
  private Double validateCommissionRate(Double commissionRate)
      throws InvalidCommissionRateException {
    if (commissionRate > 0 && commissionRate <= 1) {
      return commissionRate;
    }
    throw new InvalidCommissionRateException();
  }

  /**
   * Checks whether or not the totalEarnings is valid.
   *
   * @param totalEarnings The totalEarnings to be checked.
   * @return The totalEarnings to be checked.
   * @throws InvalidTotalEarningsException if the totalEarnings is invalid.
   */
  private Double validateTotalEarnings(Double totalEarnings) throws InvalidTotalEarningsException {
    if (totalEarnings >= 0) {
      return totalEarnings;
    }
    throw new InvalidTotalEarningsException();
  }

  /**
   * Adds an(appropriate) listing to the problem.Agent’s current listing collection.
   *
   * @param listing A listing consisting of a property and a contract.
   */
  public void addListing(T listing) {
    this.currentListings.add(listing);
  }

  /**
   * This method will be called when an problem.Agent successfully makes a sale / rental of one of their
   * listings. Assuming the listing is part of their collection, this method should remove the
   * problem.Listing from their collection and add their commission earnings to their total earnings
   * amount.
   *
   * @param listing A listing consisting of a property and a contract.
   * @throws ListingNotFoundException if the listing is not in the currentListings.
   */
  public void completeListing(T listing) throws ListingNotFoundException {
    this.dropListing(listing);
    this.totalEarnings += this.commissionRate * listing.getContract().getCommission();
  }

  /**
   * Drop a listing from the problem.Agent’s collection without adjusting their earnings.
   *
   * @param listing A listing consisting of a property and a contract.
   * @throws ListingNotFoundException if the listing is not in the currentListings.
   */
  public void dropListing(T listing) throws ListingNotFoundException {
    if (this.currentListings.contains(listing)) {
      this.currentListings.remove(listing);
    } else {
      throw new ListingNotFoundException();
    }
  }

  /**
   * This returns the amount of money the problem.Agent would make if they successfully completed all
   * listings in their collection.
   *
   * @return the amount of money the problem.Agent would make if they successfully completed all listings in
   * their collection.
   */
  public Double getTotalPortfolioValue() {
    Double totalPortfolioValue = 0.0;
    for (T item : this.currentListings) {
      totalPortfolioValue += this.commissionRate * item.getContract().getCommission();
    }
    return totalPortfolioValue;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Agent)) {
      return false;
    }
    Agent<?> agent = (Agent<?>) o;
    return Double.compare(agent.commissionRate, commissionRate) == 0
        && Double.compare(agent.totalEarnings, totalEarnings) == 0 && Objects
        .equals(name, agent.name) && Objects.equals(currentListings, agent.currentListings);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, currentListings, commissionRate, totalEarnings);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "problem.Agent{" +
        "name='" + name + '\'' +
        ", currentListings=" + currentListings +
        ", commissionRate=" + commissionRate +
        ", totalEarnings=" + totalEarnings +
        '}';
  }
}
