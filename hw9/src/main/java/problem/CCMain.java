package problem;


public class CCMain {
  public static void main(String[] args) throws ParseException {
    CommandLine commandLine = new CommandLine();

    Option csvFileOption = new Option("csv-file", true, true, "--csv-file");
    csvFileOption.setArgName("todos.csv");
    Option displayOption = new Option("display", false, false, "--display (Optional)");
    Option sortByDateOption = new Option("sort-by-date", false, false, "--sort-by-date (Optional)");
    Option sortByPriorityOption = new Option("sort-by-priority", false, false, "--sort-by-priority (Optional)");
    Option showInComplete = new Option("show-incomplete", false, false, "--show-incomplete");
    Option showCategory = new Option("show-category", true, false, "--show-category");
    showCategory.setArgName("home");

    Option completeOption = new Option("complete-todo", true, false, "--complete-todo (Optional)");
    completeOption.setArgName("7");

    Option addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    Option todoTextOption = new Option("todo-text", true, false, "--todo-text <description of todo> "
        + "A description of the todo.");
    todoTextOption.setArgName("eat peach");

    Option dueOption = new Option("due", true, false, "--due (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.");
    dueOption.setArgName("19/04/2021");

    Option categoryOption = new Option("category", true, false, "--category (Optional) Sets the category of a new todo");
    categoryOption.setArgName("home");


    commandLine.addOption(csvFileOption);
    /** display option */
    commandLine.addOption(displayOption);
    //commandLine.addOption(showInComplete);
    //commandLine.addOption(sortByDateOption);
    commandLine.addOption(sortByPriorityOption);
    //commandLine.addOption(showCategory);

    /** add option */
    commandLine.addOption(addTodoOption);
    commandLine.addOption(todoTextOption);
    commandLine.addOption(dueOption);
    commandLine.addOption(categoryOption);

    /** complete option */
    //commandLine.addOption(completeOption);


    HelpFormatter formatter = new HelpFormatter();

    String examples = "--add-todo --todo-text 'finish hw9' --completed --due '23/04/2021' --priority 1 --category 'school'"
        + "\n"
        + "--complete-todo 2 --complete-todo 5"
        + "\n"
        + "--display --show-incomplete --show-category school --sort-by-date/--sort-by-priority"
        + "\n"
        + "--csv-file todos.csv";
    formatter.setExamples(examples);


    try {
      DefaultExecutor defaultExecutor = new DefaultExecutor(commandLine);
      defaultExecutor.execute();
    } catch (ParseException | ExecuteException e) {
      System.out.println("Error! " + e.getMessage());
      //formatter.printHelp(options);
    }
  }
}
