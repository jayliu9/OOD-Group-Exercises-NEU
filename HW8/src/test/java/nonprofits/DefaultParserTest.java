package nonprofits;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DefaultParserTest {

  private Options options;
  private String[] arguments;
  private Option emailOpt;
  private Option emailTemplateOpt;
  private Option letterOpt;
  private Option letterTemplateOpt;
  private Option outputDirOpt;
  private Option csvFileOpt;
  private OptionGroup group;
  private CommandLineParser commandLineParser;

  @Before
  public void setUp() throws Exception {
    options = new Options();
    emailOpt = new Option("email", false, false, "--email Generate email messages. "
        + "If this option is provided, then --email-template must also be provided.");
    emailTemplateOpt = new Option("email-template", true, false, "--email-template <path/to/file> "
        + "A filename for the email template.");

    letterOpt = new Option("letter", false, false, "--letter Generate letters. If this option "
        + "is provided, then --letter-template must also be provided.");
    letterTemplateOpt = new Option("letter-template", true, false, "--letter-template <path/to/file> "
        + "A filename for the letter template.");

    group = new OptionGroup();
    group.addOption(emailOpt, emailTemplateOpt);
    group.addOption(letterOpt, letterTemplateOpt);

    outputDirOpt = new Option("output-dir", true, true, "--output-dir <path/to/folder> The folder "
        + "to store all generated");
    csvFileOpt = new Option("csv-file", true, true, "--csv-file <path/to/folder> The CSV file to "
        + "process. This option is required.");

    options.addOptionGroup(group);
    options.addOption(outputDirOpt);
    options.addOption(csvFileOpt);

    commandLineParser = new DefaultParser();
    arguments = new String[]{"--email", "--email-template", "email-template.txt", "--letter", "--letter-template", "letter-template.txt", "--output-dir", "test", "--csv-file", "supporters.csv"};
  }

  @Test
  public void parse() throws ParseException {
    CommandLine commandLine = new CommandLine();
    commandLine.addOption(emailOpt);
    commandLine.addOption(emailTemplateOpt);
    commandLine.addOption(letterOpt);
    commandLine.addOption(letterTemplateOpt);
    commandLine.addOption(outputDirOpt);
    commandLine.addOption(csvFileOpt);

    assertEquals(commandLine, commandLineParser.parse(options, arguments));
  }

  @Test (expected = ParseException.class)
  public void parseNullString() throws ParseException {
    commandLineParser.parse(options, null);
  }

  @Test (expected = MissingOptionException.class)
  public void missingOneRequiredOption() throws ParseException {
    String[] missingOutputDirOption = new String[]{"--email", "--email-template", "email-template.txt", "--letter", "--letter-template", "letter-template.txt", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, missingOutputDirOption);
    String[] missingCsvFileOption = new String[]{"--email", "--email-template", "email-template.txt", "--letter", "--letter-template", "letter-template.txt", "--output-dir", "letters"};
    commandLineParser.parse(options, missingCsvFileOption);
  }

  @Test (expected = MissingOptionException.class)
  public void missingAllRequiredOption() throws ParseException {
    String[] missingOutputDirAndCsvFileOptions = new String[]{"--email", "--email-template", "email-template.txt"};
    commandLineParser.parse(options, missingOutputDirAndCsvFileOptions);
  }


  @Test (expected = MissingOptionException.class)
  public void missingEmailAndLetterOption() throws ParseException {
    String[] missingEmailAndLetterOption = new String[]{"--email-template", "email-template.txt",
        "--letter-template", "letter-template.txt", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, missingEmailAndLetterOption);
  }

  @Test (expected = MissingBindingOptionException.class)
  public void nonCorrespondingOption() throws ParseException {
    String[] emailNonCorrespondingOption = new String[]{"--email", "--letter-template", "letter-template.txt", "--output-dir", "letters", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, emailNonCorrespondingOption);
    String[] letterNonCorrespondingOption = new String[]{"--letter", "--email-template", "email-template.txt", "--output-dir", "emails", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, letterNonCorrespondingOption);
    String[] emailLetterNonCorrespondingOption = new String[]{"--email", "--letter"};
    commandLineParser.parse(options, emailLetterNonCorrespondingOption);
  }

  @Test (expected = MissingArgumentException.class)
  public void missingArgument1() throws ParseException {
    String[] csvFileMissingArgument = new String[]{"--letter", "--letter-template", "letter-template.txt", "--output-dir", "letters", "--csv-file"};
    commandLineParser.parse(options, csvFileMissingArgument);
  }

  @Test (expected = MissingArgumentException.class)
  public void missingArgument2() throws ParseException {
    String[] emailTemplateMissingArgument = new String[]{"--email", "--email-template", "--output-dir", "emails", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, emailTemplateMissingArgument);
    String[] letterTemplateMissingArgument = new String[]{"--letter", "--letter-template", "--output-dir", "letters", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, letterTemplateMissingArgument);
    String[] outputDirMissingArgument = new String[]{"--letter", "--letter-template", "letter-template.txt", "--output-dir", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, outputDirMissingArgument);
  }

  @Test (expected = UnrecognizedOptionException.class)
  public void unrecognizedOption1() throws ParseException {
    String[] unrecognizedOption1 = new String[]{"unrecognizedOption"};
    commandLineParser.parse(options, unrecognizedOption1);
    String[] unrecognizedOption5 = new String[]{"-unrecognizedOption", "--unrecognizedOption", "email-template.txt", "--output-dir", "emails", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, unrecognizedOption5);
    String[] unrecognizedOption3 = new String[]{"--email", "--unrecognizedOption", "email-template.txt", "--output-dir", "emails", "--csv-file", "supporters.csv"};
    commandLineParser.parse(options, unrecognizedOption3);
  }

  @Test (expected = UnrecognizedOptionException.class)
  public void unrecognizedOption2() throws ParseException {
    String[] unrecognizedOption = new String[]{"--unrecognizedOption"};
    commandLineParser.parse(options, unrecognizedOption);
  }
}