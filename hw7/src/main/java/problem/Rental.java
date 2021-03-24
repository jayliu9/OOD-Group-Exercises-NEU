package problem;

import java.util.Objects;

public class Rental extends AbstractContract {

  private int term;

  public Rental(double askingPrice, boolean isNegotiable, int term)
      throws InvalidAskingPriceException, InvalidTermException {
    super(askingPrice, isNegotiable);
    this.term = this.validateTerm(term);
  }

  /**
   * Checks that whether the term is valid.
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

  public double getCommission() {
    return super.getCommission() * this.term;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), term);
  }

  @Override
  public String toString() {
    return "Rental{" +
        "askingPrice=" + askingPrice +
        ", isNegotiable=" + isNegotiable +
        ", term=" + term +
        '}';
  }
}
