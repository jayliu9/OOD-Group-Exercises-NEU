package problem;

import java.util.HashMap;
import java.util.Objects;

public class Residential extends AbstractProperty {

  private int numOfBedrooms;
  private double numOfBathrooms;

  /**
   * Constructor for the problem.Residential property.
   *
   * @param address The address if the problem.Residential Property.
   * @param size    The size if the problem.Residential Property.
   * @param numOfBedrooms The bedrooms number if the problem.Residential Property.
   * @param numOfBathrooms The bathroom number if the problem.Residential Property.
   * @throws InvalidNumOfBedroomsException if the numOfBedrooms is invalid.
   * @throws InvalidNumOfBathroomsException if the numOfBathrooms is invalid.
   * @throws InvalidSizeException if the size is invalid.
   */
  public Residential(String address, int size, int numOfBedrooms, double numOfBathrooms)
      throws InvalidSizeException, InvalidNumOfBedroomsException, InvalidNumOfBathroomsException {
    super(address, size);
    this.numOfBedrooms = this.validateNumOfBedrooms(numOfBedrooms);
    this.numOfBathrooms = this.validateNumOfBathrooms(numOfBathrooms);
  }

  /**
   * Checks that whether the numOfBedrooms is valid.
   * @param numOfBedrooms The numOfBedrooms to be checked.
   * @return The numOfBedrooms to be checked.
   * @throws InvalidNumOfBedroomsException if the numOfBedrooms is invalid.
   */
  private int validateNumOfBedrooms(int numOfBedrooms) throws InvalidNumOfBedroomsException {
    if (numOfBedrooms >= 0) {
      return numOfBedrooms;
    }
    throw new InvalidNumOfBedroomsException();
  }

  /**
   * Checks that whether the numOfBathrooms is valid.
   * @param numOfBathrooms The numOfBathrooms to be checked.
   * @return The numOfBathrooms to be checked.
   * @throws InvalidNumOfBathroomsException if the numOfBathrooms is invalid.
   */
  private double validateNumOfBathrooms(double numOfBathrooms) throws InvalidNumOfBathroomsException {
    if (numOfBathrooms >= 0) {
      return numOfBathrooms;
    }
    throw new InvalidNumOfBathroomsException();
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
    if (!(o instanceof Residential)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Residential that = (Residential) o;
    return numOfBedrooms == that.numOfBedrooms
        && Double.compare(that.numOfBathrooms, numOfBathrooms) == 0;
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), numOfBedrooms, numOfBathrooms);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "problem.Residential{" +
        "address='" + address + '\'' +
        ", size=" + size +
        ", numOfBedrooms=" + numOfBedrooms +
        ", numOfBathrooms=" + numOfBathrooms +
        '}';
  }
}
