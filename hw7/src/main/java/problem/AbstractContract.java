package problem;

import java.util.HashMap;
import java.util.Objects;

/**
 * problem.AbstractContract represent all types of contract.
 */
public abstract class AbstractContract {

  protected double askingPrice;
  protected boolean isNegotiable;

  /**
   * Constructor for the Abstract contract.
   *
   * @param askingPrice  The asking price of the contract.
   * @param isNegotiable Whether or not the price is negotiable.
   * @throws InvalidAskingPriceException if the askingPrice is invalid
   */
  public AbstractContract(double askingPrice, boolean isNegotiable)
      throws InvalidAskingPriceException {
    this.askingPrice = this.validateAskingPrice(askingPrice);
    this.isNegotiable = isNegotiable;
  }

  /**
   * Checks that whether the askingPrice is valid.
   *
   * @param askingPrice The askingPrice to be checked.
   * @return The askingPrice to be checked.
   * @throws InvalidAskingPriceException if the askingPrice is invalid
   */
  private double validateAskingPrice(double askingPrice) throws InvalidAskingPriceException {
    if (askingPrice >= 0) {
      return askingPrice;
    }
    throw new InvalidAskingPriceException();
  }

  /**
   * Calculates the commission of this kind of contract.
   *
   * @return the commission of this kind of contract
   */
  protected abstract double getCommission();

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
    if (!(o instanceof AbstractContract)) {
      return false;
    }
    AbstractContract that = (AbstractContract) o;
    return Double.compare(that.askingPrice, askingPrice) == 0
        && isNegotiable == that.isNegotiable;
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(askingPrice, isNegotiable);
  }
}