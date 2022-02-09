package problem;

public class Main {

  public static void main(String[] args) {
    CommandLine commandLine;

    CommandLineParser commandLineParser = new DefaultParser();

    Options options = UserDefinedOption.defineOption();

    HelpFormatter formatter = new HelpFormatter();

    String examples =
        "--add-todo --todo-text 'finish hw9' --completed --due '23/04/2021' --priority 1 --category 'school'"
            + "\n"
            + "--complete-todo 2 --complete-todo 5"
            + "\n"
            + "--display --show-incomplete --show-category school --sort-by-date/--sort-by-priority"
            + "\n"
            + "--csv-file todos.csv";
    formatter.setExamples(examples);

    try {
      commandLine = commandLineParser.parse(options, args);
      DefaultExecutor defaultExecutor = new DefaultExecutor(commandLine);
      defaultExecutor.execute();
    } catch (ParseException | ExecuteException e) {
      e.printStackTrace();
    }
  }

}