package problem;

import java.util.HashMap;
import java.util.Objects;

public class Rental extends AbstractContract {

  protected int term;

  /**
   * Constructor for the problem.Rental.
   *
   * @param askingPrice  The asking price.
   * @param isNegotiable Whether or not the price is negotiable.
   * @param term         The contract months.
   * @throws InvalidTermException        if the term is invalid.
   * @throws InvalidAskingPriceException if the askingPrice is invalid.
   */
  public Rental(Double askingPrice, Boolean isNegotiable, int term)
      throws InvalidTermException, InvalidAskingPriceException {
    super(askingPrice, isNegotiable);
    this.term = this.validateTerm(term);
  }

  /**
   * Checks that whether the term is valid.
   *
   * @param term The term to be checked.
   * @return The term to be checked.
   * @throws InvalidTermException if the term is invalid
   */
  private int validateTerm(int term) throws InvalidTermException {
    if (term > 0) {
      return term;
    }
    throw new InvalidTermException();
  }

  /**
   * Calculates the commission of this kind of contract.
   *
   * @return the commission of this kind of contract
   */
  @Override
  protected double getCommission() {
    return this.askingPrice * this.term;
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
    if (!(o instanceof Rental)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Rental rental = (Rental) o;
    return term == rental.term;
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), term);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "problem.Rental{" +
        "askingPrice=" + askingPrice +
        ", isNegotiable=" + isNegotiable +
        ", term=" + term +
        '}';
  }
}
