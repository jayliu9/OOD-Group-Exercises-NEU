package nonprofits;

public class Main {

  public static void main(String[] args) {
    Options options = new Options();
    CommandLine commandLine;

    CommandLineParser commandLineParser = new DefaultParser();

    Option op1 = new Option("nonprofits", false, false, "Generate email messages.");
    Option op11 = new Option("email-template", true, false, "email template.");

    Option op2 = new Option("letter", false, false, "Generate letter messages.");
    Option op22 = new Option("letter-template", true, false, "letter template.");

    OptionGroup group = new OptionGroup();
    group.addOption(op1, op11);
    group.addOption(op2, op22);

    Option op3 = new Option("output-dir", true, true, "set output dir.");
    Option op4 = new Option("csv-file", true, true, "set csv file.");

    options.addOptionGroup(group);
    options.addOption(op3);
    options.addOption(op4);

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