package problem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommercialTest {
  private Commercial c1;

  @Before
  public void setUp() throws Exception {
    c1 = new Commercial("Building 8", 50, 1, false);
  }

  @Test
  public void testEquals() throws InvalidNumOfOfficesException, InvalidSizeException {
    assertTrue(c1.equals(c1));
    assertFalse(c1.equals(null));
    assertFalse(c1.equals(""));

    Commercial c2 = new Commercial("Building 10", 50, 1, false);
    assertFalse(c1.equals(c2));

    Commercial c3 = new Commercial("Building 8", 60, 1, false);
    assertFalse(c1.equals(c3));

    Commercial c4 = new Commercial("Building 8", 50, 2, false);
    assertFalse(c1.equals(c4));

    Commercial c5 = new Commercial("Building 8", 50, 1, true);
    assertFalse(c1.equals(c5));

    Commercial c6 = new Commercial("Building 8", 50, 1, false);
    assertTrue(c1.equals(c6));
  }

  @Test
  public void testHashCode() throws InvalidNumOfOfficesException, InvalidSizeException {
    Commercial c5 = new Commercial("Building 8", 50, 1, false);
    assertTrue(c1.hashCode() == c5.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem.Commercial{address='Building 8', size=50, numOfOffices=1, isSuitableForRetail=false}";
    Assert.assertEquals(expected, c1.toString());
  }

  @Test(expected = InvalidSizeException.class)
  public void invalidSize() throws InvalidNumOfOfficesException, InvalidSizeException {
    Commercial c2 = new Commercial("Building 10", -2, 2, false);
  }

  @Test(expected = InvalidNumOfOfficesException.class)
  public void invalidNumOfOffices() throws InvalidNumOfOfficesException, InvalidSizeException {
    Commercial c2 = new Commercial("Building 10", 2, -2, false);
  }

}