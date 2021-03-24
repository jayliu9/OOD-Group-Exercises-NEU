package problem;

import java.util.Objects;

public class Commercial extends AbstractProperty {

  private int numOfOffices;
  private boolean isSuitableForRetail;

  public Commercial(String address, int size, int numOfOffices, boolean isSuitableForRetail)
      throws InvalidNumOfOfficesException {
    super(address, size);
    this.numOfOffices = this.validateNumOfOffices(numOfOffices);
    this.isSuitableForRetail = isSuitableForRetail;
  }

  /**
   * Checks that whether the numOfOffices is valid.
   * @param numOfOffices The numOfOffices to be checked.
   * @return The numOfOffices to be checked.
   * @throws InvalidNumOfOfficesException if the numOfOffices is invalid
   */
  private int validateNumOfOffices(int numOfOffices) throws InvalidNumOfOfficesException {
    if (numOfOffices >= 0) {
      return numOfOffices;
    }
    throw new InvalidNumOfOfficesException();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Commercial)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Commercial that = (Commercial) o;
    return numOfOffices == that.numOfOffices && isSuitableForRetail == that.isSuitableForRetail;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), numOfOffices, isSuitableForRetail);
  }

  @Override
  public String toString() {
    return "Commercial{" +
        ", address='" + address + '\'' +
        ", size=" + size +
        "numOfOffices=" + numOfOffices +
        ", isSuitableForRetail=" + isSuitableForRetail +
        '}';
  }
}
