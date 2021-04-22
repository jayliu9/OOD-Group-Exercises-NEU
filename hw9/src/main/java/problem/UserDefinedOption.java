package problem;

import java.util.Arrays;

public class UserDefinedOption {

  public static Options defineOption() {

    Options options = new Options();

    Option csvFileOption = new Option("csv-file", true, true, "--csv-file <path/to/folder> "
        + "The CSV file containing the todos. This option is required.");

    Option addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    Option todoTextOption = new Option("todo-text", true, false,
        "--todo-text <description of todo> "
            + "A description of the todo.");

    Option completedOption = new Option("completed", false, false,
        "--completed (Optional) Sets the completed status of a new todo to true.");

    Option dueOption = new Option("due", true, false,
        "--due <due date> (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.");

    Option priorityOption = new Option("priority", true, false,
        "--priority <1, 2, or 3> (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.");

    Option categoryOption = new Option("category", true, false,
        "--category <a category name> (Optional) Sets the category of a new todo. The value can be any String. Categories do not need to be pre-defined.");

    Option completeTodoOption = new Option("complete-todo", true, false,
        "--complete-todo <id> Mark the Todo with the provided ID as complete.");

    Option displayOption = new Option("display", false, false,
        "--display Display todos. If none of the following optional arguments are provided, displays all todos.");

    Option showIncompleteOption = new Option("show-incomplete", false, false,
        "--show-incomplete (Optional) If --display is provided, only incomplete todos should be displayed.");

    Option showCategoryOption = new Option("show-category", true, false,
        "--show-category <category> (Optional) If --display is provided, only todos with the given category should be displayed.");

    Option sortByDateOption = new Option("sort-by-date", false, false,
        "--sort-by-date (Optional) If --display is provided, sort the list of todos by date order (ascending). be combined with --sort-by-priority.");

    Option sortByPriorityOption = new Option("sort-by-priority", false, false,
        "--sort-by-priority (Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date.");

    OptionGroup bindingGroup = new OptionGroup(true);
    bindingGroup.addOption(addTodoOption, todoTextOption);

    OptionGroup exclusiveGroup = new OptionGroup(false);
    exclusiveGroup.addOption(sortByDateOption, sortByPriorityOption);

    OptionSeries addTodoSeries = new OptionSeries();
    addTodoSeries.addOption(addTodoOption,
        Arrays.asList(todoTextOption, completedOption, dueOption, priorityOption, categoryOption));

    OptionSeries displaySeries = new OptionSeries();
    displaySeries.addOption(displayOption, Arrays
        .asList(showIncompleteOption, showCategoryOption, sortByDateOption, sortByPriorityOption));

    options.addOption(csvFileOption);
    options.addOptionGroup(bindingGroup);
    options.addOptionGroup(exclusiveGroup);
    options.addOptionSeries(addTodoSeries);
    options.addOption(completeTodoOption);
    options.addOptionSeries(displaySeries);

    return options;
  }
}
