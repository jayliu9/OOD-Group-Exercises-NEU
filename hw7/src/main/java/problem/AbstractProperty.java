package problem;

import java.util.Objects;

public abstract class AbstractProperty {

  protected String address;
  protected int size;

  public AbstractProperty(String address, int size) {
    this.address = address;
    this.size = size;
  }

  public String getAddress() {
    return this.address;
  }

  public int getSize() {
    return this.size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractProperty)) {
      return false;
    }
    AbstractProperty property = (AbstractProperty) o;
    return size == property.size && Objects.equals(address, property.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, size);
  }

  @Override
  public String toString() {
    return "Property{" +
        "address='" + address + '\'' +
        ", size=" + size +
        '}';
  }
}
