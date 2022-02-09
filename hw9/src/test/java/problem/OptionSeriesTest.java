package problem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OptionSeriesTest {

  private Option addTodoOption;
  private Option todoTextOption;
  private Option completedOption;
  private Option dueOption;
  private Option priorityOption;
  private Option categoryOption;
  private Option displayOption;
  private Option showIncompleteOption;
  private Option showCategoryOption;
  private Option sortByDateOption;
  private Option sortByPriorityOption;
  private OptionSeries optionSeries;
  private OptionSeries nullType;

  @Before
  public void setUp() throws Exception {
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

    optionSeries = new OptionSeries();
    optionSeries.addOption(addTodoOption,
        Arrays.asList(todoTextOption, completedOption, dueOption, priorityOption, categoryOption));

    nullType = null;
  }

  @Test
  public void addOption() {
    optionSeries.addOption(displayOption, Arrays
        .asList(showIncompleteOption, showCategoryOption, sortByDateOption, sortByPriorityOption));
    assertEquals(displayOption, optionSeries.getKeyOption(showCategoryOption));
  }

  @Test
  public void getAllOptions() {
    List<Option> allOptions = new ArrayList<>();
    allOptions.add(addTodoOption);
    allOptions.add(todoTextOption);
    allOptions.add(completedOption);
    allOptions.add(dueOption);
    allOptions.add(priorityOption);
    allOptions.add(categoryOption);
    assertEquals(allOptions, optionSeries.getAllOptions());
  }

  @Test
  public void getKeyOption() {
    assertEquals(addTodoOption, optionSeries.getKeyOption(todoTextOption));
    assertNull(optionSeries.getKeyOption(showCategoryOption));

  }

  @Test
  public void containsValueOption() {
    assertTrue(optionSeries.containsValueOption(todoTextOption));
    assertFalse(optionSeries.containsValueOption(showCategoryOption));
  }

  @Test
  public void testEquals() {
    assertTrue(optionSeries.equals(optionSeries));
    assertFalse(optionSeries.equals(nullType));
    assertFalse(optionSeries.equals(""));

    OptionSeries that = new OptionSeries();
    that.addOption(addTodoOption,
        Arrays.asList(todoTextOption, completedOption, dueOption, priorityOption, categoryOption));
    assertTrue(optionSeries.equals(that));
  }

  @Test
  public void testHashCode() {
    OptionSeries that = new OptionSeries();
    that.addOption(addTodoOption,
        Arrays.asList(todoTextOption, completedOption, dueOption, priorityOption, categoryOption));
    assertTrue(optionSeries.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "OptionSeries{"
        + "optionSeries={"
        + "Option{opt='add-todo', value='null', required=false, description='--add-todo Add a new todo. If this option is provided, then --todo-text must also be provided.', hasArg=false}"
        + "=[Option{opt='todo-text', value='null', required=false, description='--todo-text <description of todo> A description of the todo.', hasArg=true}, "
        + "Option{opt='completed', value='null', required=false, description='--completed (Optional) Sets the completed status of a new todo to true.', hasArg=false}, "
        + "Option{opt='due', value='null', required=false, description='--due <due date> (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.', hasArg=true}, "
        + "Option{opt='priority', value='null', required=false, description='--priority <1, 2, or 3> (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.', hasArg=true}, "
        + "Option{opt='category', value='null', required=false, description='--category <a category name> (Optional) Sets the category of a new todo. The value can be any String. Categories do not need to be pre-defined.', hasArg=true}]}}";
    assertEquals(expected, optionSeries.toString());
  }
}