package problem;


import java.util.Objects;

public class Listing<T extends AbstractProperty, U extends AbstractContract> {

  private T property;
  private U contract;

  public Listing(T property, U contract) {
    this.property = property;
    this.contract = contract;
  }

  public T getProperty() {
    return this.property;
  }

  public U getContract() {
    return this.contract;
  }

  public double getCommission() {
    return this.contract.getCommission();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Listing)) {
      return false;
    }
    Listing<?, ?> listing = (Listing<?, ?>) o;
    return Objects.equals(property, listing.property) && Objects
        .equals(contract, listing.contract);
  }

  @Override
  public int hashCode() {
    return Objects.hash(property, contract);
  }

  @Override
  public String toString() {
    return "Listing{" +
        "property=" + property +
        ", contract=" + contract +
        '}';
  }
}
