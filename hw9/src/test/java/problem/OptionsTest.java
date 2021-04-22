package problem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class OptionsTest {
  private Option op1;
  private Option op11;
  private Option op2;
  private Option op22;
  private Option op3;
  private Option op4;
  private OptionGroup optionGroup;

  private Options options;
  private Options nullType;

  @Before
  public void setUp() throws Exception {
    op1 = new Option("email", false, false, "Generate email messages.");
    op11 = new Option("email-template", true, false, "email template.");
    op2 = new Option("letter", false, false, "Generate letter messages.");
    op22 = new Option("letter-template", true, false, "letter template.");
    op3 = new Option("output-dir", true, true, "set output dir.");
    op4 = new Option("csv-file", true, true, "set csv file.");

    optionGroup = new OptionGroup(true);
    optionGroup.addOption(op1, op11);
    optionGroup.addOption(op2, op22);

    options = new Options();
    nullType = null;
  }

  @Test
  public void getOptions() {
    options.addOptionGroup(optionGroup);
    options.addOption(op3);
    options.addOption(op4);

    List<Option> optList = new ArrayList<>();
    optList.add(op1);
    optList.add(op11);
    optList.add(op2);
    optList.add(op22);
    optList.add(op3);
    optList.add(op4);

    assertEquals(optList, options.getOptions());
  }

  @Test
  public void addOptionGroup() {
    options.addOptionGroup(optionGroup);
    Set<OptionGroup> optionSet = new HashSet<>();
    optionSet.add(optionGroup);
    assertEquals(optionSet, options.getOptionGroups());
  }

  @Test
  public void addOption() {
    options.addOption(op3);
    options.addOption(op4);
    options.addOption(op4);
    assertEquals(op3, options.getOption("output-dir"));
  }

  @Test
  public void getRequiredOptions() {
    options.addOptionGroup(optionGroup);
    options.addOption(op3);
    options.addOption(op4);

    List<Object> optList = new ArrayList<>();
    optList.add("output-dir");
    optList.add("csv-file");

    assertEquals(optList, options.getRequiredOptions());
  }

  @Test
  public void getMatchingOptions() {
    options.addOption(op3);
    options.addOption(op4);
    assertEquals(op3, options.getMatchingOption("--output-dir"));
    assertEquals(null, options.getMatchingOption("--email"));
  }

  @Test
  public void getOption() {
    options.addOptionGroup(optionGroup);
    assertEquals(op1, options.getOption("email"));
    assertEquals(null, options.getOption("jimmy"));
  }


  @Test
  public void testEquals() {
    options.addOption(op1);
    assertTrue(options.equals(options));
    assertFalse(options.equals(nullType));
    assertFalse(options.equals(""));

    Options that = new Options();
    that.addOption(op1);
    assertTrue(options.equals(that));
  }


  @Test
  public void testOptionsNotEquals() {
    options.addOptionGroup(optionGroup);
    options.addOption(op3);
    options.addOption(op4);

    Options optionsNotEqual = new Options();
    optionsNotEqual.addOptionGroup(optionGroup);
    optionsNotEqual.addOption(op3);

    assertFalse(options.equals(optionsNotEqual));
  }


  @Test
  public void testGroupNotEquals() {
    options.addOptionGroup(optionGroup);
    options.addOption(op3);
    options.addOption(op4);

    Options groupNotEqual = new Options();

    groupNotEqual.addOption(op1);
    groupNotEqual.addOption(op11);
    groupNotEqual.addOption(op2);
    groupNotEqual.addOption(op22);
    groupNotEqual.addOption(op3);
    groupNotEqual.addOption(op4);
    assertFalse(options.equals(groupNotEqual));
  }


  @Test
  public void testHashCode() {
    options.addOption(op1);
    Options that = new Options();
    that.addOption(op1);
    assertTrue(options.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "Options{opts=[Option{opt='email', value='null', required=false, "
        + "description='Generate email messages.', hasArg=false}], requiredOpts=[], optionGroups={}}";
    options.addOption(op1);
    assertEquals(expected, options.toString());
  }
}