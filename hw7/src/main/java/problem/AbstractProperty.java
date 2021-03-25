package problem;

import java.util.HashMap;
import java.util.Objects;

/**
 * problem.AbstractProperty Class represent all types of properties.
 */
public abstract class AbstractProperty {
  protected String address;
  protected int size;

  /**
   * Constructor for the problem.AbstractProperty.
   *
   * @param address The address of the problem.AbstractProperty.
   * @param size    The property size of the problem.AbstractProperty.
   * @throws InvalidSizeException if the size is invalid
   */
  public AbstractProperty(String address, int size) throws InvalidSizeException {
    this.address = address;
    this.size = this.validateSize(size);
  }

  /**
   * Checks that whether the size is valid.
   * @param size The size to be checked.
   * @return The size to be checked.
   * @throws InvalidSizeException if the size is invalid.
   */
  private int validateSize(int size) throws InvalidSizeException {
    if (size >= 0) {
      return size;
    }
    throw new InvalidSizeException();
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
    if (!(o instanceof AbstractProperty)) {
      return false;
    }
    AbstractProperty that = (AbstractProperty) o;
    return size == that.size && Objects.equals(address, that.address);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(address, size);
  }
}
