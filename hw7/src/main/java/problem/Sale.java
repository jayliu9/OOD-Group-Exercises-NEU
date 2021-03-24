package problem;

public class Sale extends AbstractContract {

  public Sale(double askingPrice, boolean isNegotiable) throws InvalidAskingPriceException {
    super(askingPrice, isNegotiable);
  }
}
