package problem;

import java.util.Objects;

public class Residential extends AbstractProperty {

  private int numOfBedrooms;
  private double numOfBathrooms;

  public Residential(String address, int size, int numOfBedrooms, double numOfBathrooms)
      throws InvalidNumOfBedroomsException, InvalidNumOfBathroomsException {
    super(address, size);
    this.numOfBedrooms = this.validateNumOfBedrooms(numOfBedrooms);
    this.numOfBathrooms = this.validateNumOfBathrooms(numOfBathrooms);
  }

  /**
   * Checks that whether the numOfBedrooms is valid.
   * @param numOfBedrooms The numOfBedrooms to be checked.
   * @return The numOfBedrooms to be checked.
   * @throws InvalidNumOfBedroomsException if the numOfBedrooms is invalid
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
   * @throws InvalidNumOfBathroomsException if the numOfBathrooms is invalid
   */
  private double validateNumOfBathrooms(double numOfBathrooms) throws InvalidNumOfBathroomsException {
    if (numOfBathrooms >= 0) {
      return numOfBathrooms;
    }
    throw new InvalidNumOfBathroomsException();
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), numOfBedrooms, numOfBathrooms);
  }

  @Override
  public String toString() {
    return "Residential{" +
        "address='" + address + '\'' +
        ", size=" + size +
        ", numOfBedrooms=" + numOfBedrooms +
        ", numOfBathrooms=" + numOfBathrooms +
        '}';
  }
}
