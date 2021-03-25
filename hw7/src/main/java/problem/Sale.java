package problem;

public class Sale extends AbstractContract {

  /**
   * Constructor for the problem.Sale Contract.
   *
   * @param askingPrice  The asking price of the problem.Sale Contract.
   * @param isNegotiable Whether or not the price is negotiable.
   * @throws InvalidAskingPriceException if the askingPrice is invalid
   */
  public Sale(Double askingPrice, Boolean isNegotiable) throws InvalidAskingPriceException {
    super(askingPrice, isNegotiable);
  }

  /**
   * Calculates the commission of this kind of contract.
   * @return the commission of this kind of contract
   */
  @Override
  protected Double getCommission() {
    return this.askingPrice;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "problem.Sale{" +
        "askingPrice=" + askingPrice +
        ", isNegotiable=" + isNegotiable +
        '}';
  }
}
