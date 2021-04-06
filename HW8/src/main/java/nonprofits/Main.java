package nonprofits;

public class Main {

  public static void main(String[] args) {
    Options options = new Options();
    CommandLine commandLine;

    CommandLineParser commandLineParser = new DefaultParser();

    Option emailOption = new Option("email", false, false, "--email Generate email messages. "
        + "If this option is provided, then --email-template must also be provided.");

    Option emailTemplateOption = new Option("email-template", true, false, "--email-template <path/to/file> "
        + "A filename for the email template.");

    Option letterOption = new Option("letter", false, false, "--letter Generate letters. If this option "
        + "is provided, then --letter-template must also be provided.");

    Option letterTemplateOption = new Option("letter-template", true, false, "--letter-template <path/to/file> "
        + "A filename for the letter template.");

    OptionGroup group = new OptionGroup();
    group.addOption(emailOption, emailTemplateOption);
    group.addOption(letterOption, letterTemplateOption);

    Option outputDirOption = new Option("output-dir", true, true, "--output-dir <path/to/folder> The folder "
        + "to store all generated");

    Option csvFileOption = new Option("csv-file", true, true, "--csv-file <path/to/folder> The CSV file to "
        + "process. This option is required.");

    options.addOptionGroup(group);
    options.addOption(outputDirOption);
    options.addOption(csvFileOption);

    HelpFormatter formatter = new HelpFormatter();

    String examples = "--email --email-template email-template.txt --output-dir emails"
        + "--csv-file customer.csv"
        + "\n"
        + "--letter --letter-template letter-template.txt --output-dir letters"
        + "--csv-file customer.csv";
    formatter.setExamples(examples);

    try {
      commandLine = commandLineParser.parse(options, args);
      IOProcessor io = new IOProcessor(commandLine);
      io.generalFiles();
    } catch (ParseException e) {
      System.out.println("Error! " + e.getMessage());
      formatter.printHelp(options);
    }
  }
}