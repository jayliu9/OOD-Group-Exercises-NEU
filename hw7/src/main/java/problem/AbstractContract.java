package problem;

import java.util.Objects;

public abstract class AbstractContract {

  protected double askingPrice;
  protected boolean isNegotiable;

  public AbstractContract(double askingPrice, boolean isNegotiable)
      throws InvalidAskingPriceException {
    this.askingPrice = this.validateAskingPrice(askingPrice);
    this.isNegotiable = isNegotiable;
  }

  /**
   * Checks that whether the askingPrice is valid.
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

  public double getAskingPrice() {
    return this.askingPrice;
  }

  public boolean isNegotiable() {
    return this.isNegotiable;
  }

  public double getCommission() {
    return this.askingPrice;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(askingPrice, isNegotiable);
  }

  @Override
  public String toString() {
    return "AbstractContract{" +
        "askingPrice=" + askingPrice +
        ", isNegotiable=" + isNegotiable +
        '}';
  }
}
