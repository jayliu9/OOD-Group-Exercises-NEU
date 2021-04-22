package problem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CommandLineTest {
  private CommandLine commandLine;
  private CommandLine nullType;
  private Option emailOpt;
  private Option emailTemplateOpt;
  private Option letterOpt;
  private Option letterTemplateOpt;
  private Option outputDirOpt;
  private Option csvFileOpt;

  @Before
  public void setUp() throws Exception {
    emailOpt = new Option("email", false, false, "--email Generate email messages. "
        + "If this option is provided, then --email-template must also be provided.");
    emailTemplateOpt = new Option("email-template", true, false, "--email-template <path/to/file> "
        + "A filename for the email template.");
    letterOpt = new Option("letter", false, false, "--letter Generate letters. If this option "
        + "is provided, then --letter-template must also be provided.");
    letterTemplateOpt = new Option("letter-template", true, false, "--letter-template <path/to/file> "
        + "A filename for the letter template.");
    outputDirOpt = new Option("output-dir", true, true, "--output-dir <path/to/folder> The folder "
        + "to store all generated");
    csvFileOpt = new Option("csv-file", true, true, "--csv-file <path/to/folder> The CSV file to "
        + "process. This option is required.");

    commandLine= new CommandLine();
    nullType = null;
    commandLine.addOption(emailOpt);
    commandLine.addOption(emailTemplateOpt);
    commandLine.addOption(letterOpt);
    commandLine.addOption(letterTemplateOpt);
  }

  @Test
  public void hasOption() {
    assertTrue(commandLine.hasOption("email"));
    assertFalse(commandLine.hasOption("output-dir"));
    commandLine.addOption(outputDirOpt);
    assertTrue(commandLine.hasOption("output-dir"));
  }

  @Test
  public void addOption() {
    commandLine.addOption(letterOpt);
    assertTrue(commandLine.hasOption("letter"));
  }

  @Test
  public void getOptions() {
    List<Option> allOptions = new ArrayList<>(Arrays.asList(emailOpt, emailTemplateOpt, letterOpt, letterTemplateOpt));
    assertEquals(allOptions, commandLine.getOptions());
  }

  @Test
  public void getOptionValue() {
    assertEquals(null, commandLine.getOptionValue("email"));
    assertEquals(null, commandLine.getOptionValue("csv-file"));
    assertEquals(null, commandLine.getOptionValue("email-template"));
  }

  @Test
  public void findOptions() {
    List<Option> foundOptions = new ArrayList<>(Arrays.asList(emailOpt, emailTemplateOpt));
    assertEquals(foundOptions, commandLine.findOptions("email"));
  }

  @Test
  public void testEquals() {
    assertTrue(commandLine.equals(commandLine));
    assertFalse(commandLine.equals(nullType));
    assertFalse(commandLine.equals(""));

    CommandLine that = new CommandLine();
    that.addOption(emailOpt);
    that.addOption(emailTemplateOpt);
    that.addOption(letterOpt);
    that.addOption(letterTemplateOpt);
    assertTrue(commandLine.equals(that));
  }

  @Test
  public void testHashCode() {
    CommandLine that = new CommandLine();
    that.addOption(emailOpt);
    that.addOption(emailTemplateOpt);
    that.addOption(letterOpt);
    that.addOption(letterTemplateOpt);
    assertTrue(commandLine.equals(that));
    assertTrue(commandLine.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "CommandLine{"
        + "options=["
        + "Option{opt='email', value='null', required=false, description='--email Generate email messages. If this option is provided, then --email-template must also be provided.', hasArg=false}, "
        + "Option{opt='email-template', value='null', required=false, description='--email-template <path/to/file> A filename for the email template.', hasArg=true}, "
        + "Option{opt='letter', value='null', required=false, description='--letter Generate letters. If this option is provided, then --letter-template must also be provided.', hasArg=false}, "
        + "Option{opt='letter-template', value='null', required=false, description='--letter-template <path/to/file> A filename for the letter template.', hasArg=true}]}";
    assertEquals(expected, commandLine.toString());
  }
}