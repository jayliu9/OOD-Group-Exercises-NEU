package nonprofits;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class OptionGroupTest {

  private Option emailOption;
  private Option emailTemplateOption;
  private Option letterOption;
  private Option letterTemplateOption;
  private OptionGroup optionGroup;
  private OptionGroup nullType;

  @Before
  public void setUp() throws Exception {
    emailOption = new Option("email", false, false, "Generate email messages.");
    emailTemplateOption = new Option("email-template", true, false, "email template.");
    letterOption = new Option("letter", false, false, "Generate letter messages.");
    letterTemplateOption = new Option("letter-template", true, false, "letter template.");

    optionGroup = new OptionGroup();
    nullType = null;

    optionGroup.addOption(emailOption, emailTemplateOption);
    optionGroup.addOption(letterOption, letterTemplateOption);
  }

  @Test
  public void addOption() {
    optionGroup.addOption(emailOption, emailTemplateOption);
    optionGroup.addOption(letterOption, letterTemplateOption);
    assertEquals(emailTemplateOption, optionGroup.getValueOption(emailOption));
  }

  @Test
  public void getAllOptions() {
    List<Option> allOptions = new ArrayList<>();
    allOptions.add(emailOption);
    allOptions.add(emailTemplateOption);
    allOptions.add(letterOption);
    allOptions.add(letterTemplateOption);
    assertEquals(allOptions, optionGroup.getAllOptions());
  }

  @Test
  public void containsKeyOption() {
    assertTrue(optionGroup.containsKeyOption("email"));
    assertTrue(optionGroup.containsKeyOption("letter"));
  }

  @Test
  public void getValueOption() {
    assertEquals(letterTemplateOption, optionGroup.getValueOption(letterOption));
  }

  @Test
  public void getAllKeyOptions() {
    Set<Option> allKeyOptions = new HashSet<>();
    allKeyOptions.add(emailOption);
    allKeyOptions.add(letterOption);
    assertEquals(allKeyOptions, optionGroup.getAllKeyOptions());

  }

  @Test
  public void getAllKeyOptionsNames() {
    List<String> allKeyOptionsNames = new ArrayList<>(Arrays.asList("email", "letter"));
    assertEquals(allKeyOptionsNames, optionGroup.getAllKeyOptionsNames());
  }


  @Test
  public void testEquals() {
    assertTrue(optionGroup.equals(optionGroup));
    assertFalse(optionGroup.equals(nullType));
    assertFalse(optionGroup.equals(""));

    OptionGroup that = new OptionGroup();
    that.addOption(emailOption, emailTemplateOption);
    that.addOption(letterOption, letterTemplateOption);
    assertTrue(optionGroup.equals(that));
  }

  @Test
  public void testHashCode() {
    OptionGroup that = new OptionGroup();
    that.addOption(emailOption, emailTemplateOption);
    that.addOption(letterOption, letterTemplateOption);
    assertTrue(optionGroup.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "OptionGroup{"
        + "optionGroup={"
        + "Option{opt='email', value='null', required=false, description='Generate email messages.', hasArg=false}"
        + "=Option{opt='email-template', value='null', required=false, description='email template.', hasArg=true}, "
        + "Option{opt='letter', value='null', required=false, description='Generate letter messages.', hasArg=false}"
        + "=Option{opt='letter-template', value='null', required=false, description='letter template.', hasArg=true}}}";
    assertEquals(expected, optionGroup.toString());
  }
}

