package problem;


public class Main {

  public static void main(String[] args) {
    Options options = new Options();
    CommandLine commandLine;

    CommandLineParser commandLineParser = new DefaultParser();


    Option addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    Option todoTextOption = new Option("todo-text", true, false, "--todo-text <description of todo> "
        + "A description of the todo.");

    Option completedOption = new Option("completed", false, false, "--completed (Optional) Sets the completed status of a new todo to true.");

    Option dueOption = new Option("due", true, false, "--due (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.");

    Option sortByDateOption = new Option("sort-by-date", false, false, "--sort-by-date (Optional) If --display is provided, sort the list of todos by date order (ascending). be combined with --sort-by-priority.");

    Option sortByPriorityOption = new Option("sort-by-priority", false, false, "--sort-by-priority (Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date.");


    OptionGroup bindingGroup = new OptionGroup(true);
    bindingGroup.addOption(addTodoOption, todoTextOption);

    OptionGroup exclusiveGroup = new OptionGroup(false);
    exclusiveGroup.addOption(sortByDateOption, sortByPriorityOption);

    Option csvFileOption = new Option("csv-file", true, true, "--csv-file <path/to/folder> "
        + "The CSV file containing the todos. This option is required.");

    options.addOptionGroup(bindingGroup);
    options.addOptionGroup(exclusiveGroup);
    options.addOption(csvFileOption);

    HelpFormatter formatter = new HelpFormatter();

    String examples = "--email --email-template email-template.txt --output-dir emails"
        + "--csv-file customer.csv"
        + "\n"
        + "--letter --letter-template letter-template.txt --output-dir letters"
        + "--csv-file customer.csv";
    formatter.setExamples(examples);
    /*
    try {
      commandLine = commandLineParser.parse(options, args);
//      System.out.println(commandLine);

      IOBridge io = new IOBridge(commandLine);
      io.processTodos();
    } catch (ParseException | java.text.ParseException | TodoNotFoundException e) {
      System.out.println("Error! " + e.getMessage());
      formatter.printHelp(options);
    }*/
  }
}