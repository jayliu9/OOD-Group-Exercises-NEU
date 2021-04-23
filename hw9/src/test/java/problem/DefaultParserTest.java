package problem;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class DefaultParserTest {

  private Option csvFileOption;
  private Option addTodoOption;
  private Option todoTextOption;
  private Option completedOption;
  private Option dueOption;
  private Option priorityOption;
  private Option categoryOption;
  private Option completeTodoOption;
  private Option displayOption;
  private Option showIncompleteOption;
  private Option showCategoryOption;
  private Option sortByDateOption;
  private Option sortByPriorityOption;
  private OptionGroup bindingOptionGroup;
  private OptionGroup exclusiveOptionGroup;
  private OptionSeries optionSeries;
  private Options options;
  private String[] arguments;
  private CommandLineParser commandLineParser;

  @Before
  public void setUp() throws Exception {

    options = new Options();

    csvFileOption = new Option("csv-file", true, true, "--csv-file <path/to/folder> "
        + "The CSV file containing the todos. This option is required.");
    csvFileOption.setArgName("todos.csv");

    addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    todoTextOption = new Option("todo-text", true, false,
        "--todo-text <description of todo> "
            + "A description of the todo.");
    todoTextOption.setArgName("A description of todo");

    completedOption = new Option("completed", false, false,
        "--completed (Optional) Sets the completed status of a new todo to true.");

    dueOption = new Option("due", true, false,
        "--due <due date> (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.");

    priorityOption = new Option("priority", true, false,
        "--priority <1, 2, or 3> (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.");

    categoryOption = new Option("category", true, false,
        "--category <a category name> (Optional) Sets the category of a new todo. The value can be any String. Categories do not need to be pre-defined.");

    completeTodoOption = new Option("complete-todo", true, false,
        "--complete-todo <id> Mark the Todo with the provided ID as complete.");
    completeTodoOption.setArgName("1");

    displayOption = new Option("display", false, false,
        "--display Display todos. If none of the following optional arguments are provided, displays all todos.");

    showIncompleteOption = new Option("show-incomplete", false, false,
        "--show-incomplete (Optional) If --display is provided, only incomplete todos should be displayed.");

    showCategoryOption = new Option("show-category", true, false,
        "--show-category <category> (Optional) If --display is provided, only todos with the given category should be displayed.");

    sortByDateOption = new Option("sort-by-date", false, false,
        "--sort-by-date (Optional) If --display is provided, sort the list of todos by date order (ascending). be combined with --sort-by-priority.");

    sortByPriorityOption = new Option("sort-by-priority", false, false,
        "--sort-by-priority (Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date.");

    bindingOptionGroup = new OptionGroup(true);
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);

    exclusiveOptionGroup = new OptionGroup(false);
    exclusiveOptionGroup.addOption(sortByDateOption, sortByPriorityOption);

    optionSeries = new OptionSeries();
    optionSeries.addOption(addTodoOption,
        Arrays.asList(todoTextOption, completedOption, dueOption, priorityOption, categoryOption));
    optionSeries.addOption(displayOption, Arrays
        .asList(showIncompleteOption, showCategoryOption, sortByDateOption, sortByPriorityOption));

    options = new Options();

    options.addOption(csvFileOption);
    options.addOptionGroup(bindingOptionGroup);
    options.addOptionGroup(exclusiveOptionGroup);
    options.addOption(completeTodoOption);
    options.addOptionSeries(optionSeries);

    commandLineParser = new DefaultParser();
    arguments = new String[]{"--csv-file", "todos.csv", "--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--show-incomplete"};
  }

  @Test
  public void parse() throws ParseException {
    CommandLine commandLine = new CommandLine();
    commandLine.addOption(csvFileOption);
    commandLine.addOption(addTodoOption);
    commandLine.addOption(todoTextOption);
    commandLine.addOption(completedOption);
    commandLine.addOption(completeTodoOption);
    commandLine.addOption(displayOption);
    commandLine.addOption(showIncompleteOption);

    assertEquals(commandLine, commandLineParser.parse(options, arguments));
  }

  @Test (expected = ParseException.class)
  public void parseNullString() throws ParseException {
    commandLineParser.parse(options, null);
  }

  @Test (expected = MissingOptionException.class)
  public void missingOneRequiredOption() throws ParseException {
    String[] missingCsvFileOption = new String[]{"--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--show-incomplete"};
    commandLineParser.parse(options, missingCsvFileOption);
  }

  @Test (expected = MissingOptionException.class)
  public void missingRequiredOptions() throws ParseException {
    Option requiredOption = new Option("required-option", false, true, "--required-option");
    options.addOption(requiredOption);
    String[] missingTwoOptions = new String[]{"--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--show-incomplete"};
    commandLineParser.parse(options, missingTwoOptions);
  }

  @Test (expected = MissingBindingOptionException.class)
  public void missingAddTodoOption() throws ParseException {
    String[] missingAddTodoOption = new String[]{"--csv-file", "todos.csv", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--show-incomplete"};
    commandLineParser.parse(options, missingAddTodoOption);
  }

  @Test (expected = MissingBindingOptionException.class)
  public void missingDisplayOption() throws ParseException {
    String[] missingDisplayOption = new String[]{"--csv-file", "todos.csv", "--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--show-incomplete"};
    commandLineParser.parse(options, missingDisplayOption);
  }

  @Test (expected = MissingBindingOptionException.class)
  public void nonCorrespondingOption() throws ParseException {
    String[] adTodoNonCorrespondingOption = new String[]{"--csv-file", "todos.csv", "--add-todo", "--completed", "--complete-todo", "1", "--display", "--show-incomplete"};
    commandLineParser.parse(options, adTodoNonCorrespondingOption);
  }

  @Test (expected = MissingArgumentException.class)
  public void csvFileMissingArgument() throws ParseException {
    String[] csvFileMissingArgument = new String[]{"--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--show-incomplete", "--csv-file"};
    commandLineParser.parse(options, csvFileMissingArgument);
  }

  @Test (expected = MissingArgumentException.class)
  public void todoTextMissingArgument() throws ParseException {
    String[] todoTextMissingArgument = new String[]{"--csv-file", "todos.csv", "--add-todo", "--todo-text", "--completed", "--complete-todo", "1", "--display", "--show-incomplete"};
    commandLineParser.parse(options, todoTextMissingArgument);
  }

  @Test (expected = MutexOptionException.class)
  public void mutexOptions1() throws ParseException {
    String[] mutexOptions = new String[]{"--csv-file", "todos.csv", "--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--sort-by-date", "--sort-by-priority"};
    commandLineParser.parse(options, mutexOptions);
  }

  @Test (expected = MutexOptionException.class)
  public void mutexOptions2() throws ParseException {
    String[] mutexOptions = new String[]{"--csv-file", "todos.csv", "--add-todo", "--todo-text", "A description of todo", "--completed", "--complete-todo", "1", "--display", "--sort-by-priority", "--sort-by-date"};
    commandLineParser.parse(options, mutexOptions);
  }

  @Test (expected = UnrecognizedOptionException.class)
  public void unrecognizedOption1() throws ParseException {
    String[] unrecognizedOption1 = new String[]{"unrecognizedOption"};
    commandLineParser.parse(options, unrecognizedOption1);
    String[] unrecognizedOption5 = new String[]{"-unrecognizedOption", "--unrecognizedOption", "--csv-file", "todos.csv"};
    commandLineParser.parse(options, unrecognizedOption5);
    String[] unrecognizedOption3 = new String[]{"--csv-file", "todos.csv", "--unrecognizedOption", "--add-todo", "--todo-text", "A description of todo"};
    commandLineParser.parse(options, unrecognizedOption3);
  }

  @Test (expected = UnrecognizedOptionException.class)
  public void unrecognizedOption2() throws ParseException {
    String[] unrecognizedOption = new String[]{"--unrecognizedOption"};
    commandLineParser.parse(options, unrecognizedOption);
  }
}