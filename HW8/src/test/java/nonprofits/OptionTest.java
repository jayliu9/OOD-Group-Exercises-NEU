package nonprofits;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OptionTest {
  private Option option;
  private Option nullType;

  @Before
  public void setUp() throws Exception {
    option = new Option("output-dir", true, true, "set output dir.");
    nullType = null;
  }

  @Test
  public void getOpt() {
    assertEquals("output-dir", option.getOpt());
  }

  @Test
  public void getDescription() {
    String msg = "set output dir.";
    assertEquals(msg, option.getDescription());
  }

  @Test
  public void isRequired() {
    assertTrue(option.isRequired());
  }

  @Test
  public void setRequired() {
    option.setRequired(false);
    assertFalse(option.isRequired());
  }

  @Test
  public void setArgName() throws ParseException {
    option.setArgName("email");
    assertEquals("email", option.getArgName());
  }

  @Test(expected = ParseException.class)
  public void setArgNameFail() throws ParseException {
    Option that = new Option("output-dir", false, true, "set output dir.");
    that.setArgName("email");
  }


  @Test
  public void acceptsArg() {
    assertTrue(option.acceptsArg());
  }

  @Test
  public void testEquals() {
    assertTrue(option.equals(option));
    assertFalse(option.equals(nullType));
    assertFalse(option.equals(""));
    Option that = new Option("output-dir", true, true, "set output dir.");
    assertTrue(option.equals(that));
  }


  @Test
  public void testNotEquals() throws ParseException {
    Option optNotEqual = new Option("csv-file", true, true, "set output dir.");
    assertFalse(option.equals(optNotEqual));

    Option hasArgNotEqual = new Option("output-dir", false, true, "set output dir.");
    assertFalse(option.equals(hasArgNotEqual));

    Option requiredNotEqual = new Option("output-dir", true, false, "set output dir.");
    assertFalse(option.equals(requiredNotEqual));

    Option descriptionNotEqual = new Option("output-dir", true, true, "output dir.");
    assertFalse(option.equals(descriptionNotEqual));

    Option valueNotEqual = new Option("output-dir", true, true, "output dir.");
    valueNotEqual.setArgName("email");
    assertFalse(option.equals(valueNotEqual));
  }

  @Test
  public void testHashCode() {
    Option that = new Option("output-dir", true, true, "set output dir.");
    assertTrue(option.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "Option{opt='output-dir', value='null', required=true, description='set output dir.',"
        + " hasArg=true}";
    assertEquals(expected, option.toString());
  }
}