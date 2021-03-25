package problem;

import java.util.HashMap;
import java.util.Objects;

public class Commercial extends AbstractProperty {

  private Integer numOfOffices;
  private Boolean isSuitableForRetail;

  /**
   * Constructor for the problem.Commercial.
   *
   * @param address             The address of the problem.Commercial.
   * @param size                The size of the problem.Commercial.
   * @param numOfOffices        The office number of the problem.Commercial.
   * @param isSuitableForRetail If the property is suitable for retail.
   * @throws InvalidNumOfOfficesException if the number of offices is invalid.
   * @throws InvalidSizeException if the size is invalid.
   */
  public Commercial(String address, Integer size, Integer numOfOffices, Boolean isSuitableForRetail)
      throws InvalidNumOfOfficesException, InvalidSizeException {
    super(address, size);
    this.numOfOffices = this.validateNumOfOffices(numOfOffices);
    this.isSuitableForRetail = isSuitableForRetail;
  }

  /**
   * Checks that whether the numOfOffices is valid.
   *
   * @param numOfOffices The numOfOffices to be checked.
   * @return The numOfOffices to be checked.
   * @throws InvalidNumOfOfficesException if the numOfOffices is invalid
   */
  private Integer validateNumOfOffices(Integer numOfOffices) throws InvalidNumOfOfficesException {
    if (numOfOffices >= 0) {
      return numOfOffices;
    }
    throw new InvalidNumOfOfficesException();
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    Commercial that = (Commercial) o;
    return numOfOffices == that.numOfOffices && isSuitableForRetail == that.isSuitableForRetail;
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), numOfOffices, isSuitableForRetail);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "problem.Commercial{" +
        "address='" + address + '\'' +
        ", size=" + size +
        ", numOfOffices=" + numOfOffices +
        ", isSuitableForRetail=" + isSuitableForRetail +
        '}';
  }
}
