package problem;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class representing a problem.Listing made up of the property
 * and the contract.
 *
 * @param <T> the property
 * @param <U> the contract
 */
public class Listing<T extends AbstractProperty, U extends AbstractContract> {
  private T property;
  private U contract;
  /**
   * Instantiates a new problem.Listing.
   *
   * @param property the property
   * @param contract the contract
   */
  public Listing(T property, U contract) {
    this.property = property;
    this.contract = contract;
  }

  /**
   * Gets the contract.
   * @return the contract.
   */
  public U getContract() {
    return this.contract;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Listing<?, ?> listing = (Listing<?, ?>) o;
    return Objects.equals(property, listing.property) &&
        Objects.equals(contract, listing.contract);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(property, contract);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "problem.Listing{" +
        "property=" + property +
        ", contract=" + contract +
        '}';
  }
}
