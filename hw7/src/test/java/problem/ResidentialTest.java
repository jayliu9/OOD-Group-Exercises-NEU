package problem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResidentialTest {
  private Residential r1;

  @Before
  public void setUp() throws Exception {
    r1 = new Residential("No.1001", 90, 2, 1.5);
  }

  @Test
  public void testEquals()
      throws InvalidNumOfBedroomsException, InvalidSizeException, InvalidNumOfBathroomsException {
    assertTrue(r1.equals(r1));
    assertFalse(r1.equals(null));
    assertFalse(r1.equals(""));

    Residential r2 = new Residential("No.1004", 80, 2, 1.5);
    assertFalse(r1.equals(r2));

    Residential r3 = new Residential("No.1001", 90, 1, 1.5);
    assertFalse(r1.equals(r3));

    Residential r4 = new Residential("No.1001", 90, 2, 2.0);
    assertFalse(r1.equals(r4));

    Residential r5 = new Residential("No.1001", 90, 2, 1.5);
    assertTrue(r1.equals(r5));
  }

  @Test
  public void testHashCode()
      throws InvalidNumOfBedroomsException, InvalidSizeException, InvalidNumOfBathroomsException {
    Residential r5 = new Residential("No.1001", 90, 2, 1.5);
    assertTrue(r1.hashCode() == r5.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem.Residential{address='No.1001', size=90, numOfBedrooms=2, numOfBathrooms=1.5}";
    Assert.assertEquals(expected, r1.toString());
  }

  @Test(expected = InvalidSizeException.class)
  public void invalidSize()
      throws InvalidNumOfBedroomsException, InvalidSizeException, InvalidNumOfBathroomsException {
    Residential r2 = new Residential("No.1001", -10, 2, 1.0);
  }

  @Test(expected = InvalidNumOfBedroomsException.class)
  public void invalidNumOfBedrooms()
      throws InvalidNumOfBedroomsException, InvalidSizeException, InvalidNumOfBathroomsException {
    Residential r2 = new Residential("No.1001", 90, -2, 1.0);
  }

  @Test(expected = InvalidNumOfBathroomsException.class)
  public void invalidNumOfBathrooms()
      throws InvalidNumOfBedroomsException, InvalidSizeException, InvalidNumOfBathroomsException {
    Residential r2 = new Residential("No.1001", 90, 2, -1.0);
  }
}