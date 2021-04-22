package problem;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class OptionsTest {

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
  private Options nullType;

  @Before
  public void setUp() throws Exception {
    csvFileOption = new Option("csv-file", true, true, "--csv-file <path/to/folder> "
        + "The CSV file containing the todos. This option is required.");

    addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    todoTextOption = new Option("todo-text", true, false,
        "--todo-text <description of todo> "
            + "A description of the todo.");

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

    options.addOptionGroup(bindingOptionGroup);
    options.addOptionGroup(exclusiveOptionGroup);
    options.addOptionSeries(optionSeries);

    nullType = null;
  }

  @Test
  public void getOptions() {
    assertEquals(11, options.getOptions().size());
  }

//  @Test
//  public void addOptionGroup() {
//
//  }
//
//  @Test
//  public void addOptionSeries() {
//  }

  @Test
  public void addOption() {
    options.addOption(csvFileOption);
    options.addOption(csvFileOption);
    assertEquals(csvFileOption, options.getOption("csv-file"));
  }

  @Test
  public void getRequiredOptions() {
    options.addOption(csvFileOption);
    assertEquals(Collections.singletonList("csv-file"), options.getRequiredOptions());
  }

  @Test
  public void getOption() {
    assertEquals(addTodoOption, options.getOption("add-todo"));
    assertNull(options.getOption("csv-file"));
  }

  @Test
  public void getOptionGroups() {
    Set<OptionGroup> optionSet = new HashSet<>();
    optionSet.add(bindingOptionGroup);
    optionSet.add(exclusiveOptionGroup);
    assertEquals(optionSet, options.getOptionGroups());
  }

  @Test
  public void getOptionSeries() {
    Set<OptionSeries> optionSet = new HashSet<>();
    optionSet.add(optionSeries);
    assertEquals(optionSet, options.getOptionSeries());
  }

  @Test
  public void getMatchingOption() {
    assertEquals(showCategoryOption, options.getMatchingOption("--show-category"));
    assertEquals(null, options.getMatchingOption("show-category"));
  }

  @Test
  public void testEquals() {
    assertTrue(options.equals(options));
    assertFalse(options.equals(nullType));
    assertFalse(options.equals(""));

    Options that = new Options();
    that.addOptionGroup(bindingOptionGroup);
    that.addOptionGroup(exclusiveOptionGroup);
    that.addOptionSeries(optionSeries);
    assertTrue(options.equals(that));
  }

  @Test
  public void testOptionsNotEquals() {
    Options optionsNotEqual = new Options();
    optionsNotEqual.addOptionGroup(bindingOptionGroup);
    optionsNotEqual.addOptionGroup(exclusiveOptionGroup);
    optionsNotEqual.addOptionSeries(optionSeries);
    optionsNotEqual.addOption(csvFileOption);

    assertFalse(options.equals(optionsNotEqual));
  }

  @Test
  public void testGroupNotEquals() {

    Options groupNotEqual = new Options();
    groupNotEqual.addOptionSeries(optionSeries);

    assertFalse(options.equals(groupNotEqual));
  }

  @Test
  public void testSeriesNotEquals() {

    Options seriesNotEqual = new Options();
    OptionSeries diffSeries = new OptionSeries();
    seriesNotEqual.addOptionGroup(bindingOptionGroup);
    seriesNotEqual.addOptionGroup(exclusiveOptionGroup);

    diffSeries.addOption(addTodoOption,
        Arrays.asList(todoTextOption, completedOption, dueOption, priorityOption, categoryOption));
    diffSeries.addOption(displayOption, Arrays
        .asList(showIncompleteOption, showCategoryOption, sortByDateOption));

    seriesNotEqual.addOptionSeries(diffSeries);

    assertFalse(options.equals(seriesNotEqual));
  }

  @Test
  public void testHashCode() {
    Options that = new Options();
    that.addOptionGroup(bindingOptionGroup);
    that.addOptionGroup(exclusiveOptionGroup);
    that.addOptionSeries(optionSeries);
    assertTrue(options.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    Options newOptions = new Options();
    newOptions.addOption(csvFileOption);
    String expected = "Options{opts=[Option{opt='csv-file', value='null', required=true, description='--csv-file <path/to/folder> The CSV file containing the todos. This option is required.', hasArg=true}], requiredOpts=[csv-file], optionGroups={}, optionSeries={}}";
    assertEquals(expected, newOptions.toString());
  }
}