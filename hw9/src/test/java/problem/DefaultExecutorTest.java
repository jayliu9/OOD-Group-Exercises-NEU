package problem;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DefaultExecutorTest {

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

  private DefaultExecutor defaultExecutor;
  private CommandLine commandLine;

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Before
  public void setUp() throws Exception {
    csvFileOption = new Option("csv-file", true, true, "--csv-file <path/to/folder> "
        + "The CSV file containing the todos. This option is required.");
    csvFileOption.setArgName(tempFolder.getRoot().getPath() + File.separator + "testInfo.csv");

    addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    todoTextOption = new Option("todo-text", true, false,
        "--todo-text <description of todo> "
            + "A description of the todo.");
    todoTextOption.setArgName("Clean the classroom");

    completedOption = new Option("completed", false, false,
        "--completed (Optional) Sets the completed status of a new todo to true.");

    dueOption = new Option("due", true, false,
        "--due <due date> (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.");
    dueOption.setArgName("02/28/2020");

    priorityOption = new Option("priority", true, false,
        "--priority <1, 2, or 3> (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.");
    priorityOption.setArgName("1");

    categoryOption = new Option("category", true, false,
        "--category <a category name> (Optional) Sets the category of a new todo. The value can be any String. Categories do not need to be pre-defined.");
    categoryOption.setArgName("school");

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

    commandLine = new CommandLine();
    commandLine.addOption(csvFileOption);

    String test = "\"id\", \"text\", \"completed\", \"due\", \"priority\", \"category\"" +
        System.lineSeparator() + "\"1\",\"Finish HW9\",\"false\",\"03/22/2020\",\"1\",\"school\"";
    File csvFile = new File(tempFolder.getRoot().getPath(), "testInfo.csv");

    try (BufferedWriter outputCsvFile = new BufferedWriter(new FileWriter(csvFile))) {
      outputCsvFile.write(test);
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

  @Test
  public void AddExecutor() throws ParseException, ExecuteException {
    commandLine.addOption(addTodoOption);
    commandLine.addOption(todoTextOption);
    commandLine.addOption(completedOption);
    commandLine.addOption(dueOption);
    commandLine.addOption(priorityOption);
    commandLine.addOption(categoryOption);
    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();

    String msg = "";
    String fileName = "testInfo.csv";
    File read = new File(tempFolder.getRoot().getPath(), fileName);
    try (BufferedReader inputFile = new BufferedReader(new FileReader(read))) {
      String line;
      while ((line = inputFile.readLine()) != null) {
        msg += line + System.lineSeparator();
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }

    msg = msg.trim();
    assertEquals("\"id\", \"text\", \"completed\", \"due\", \"priority\", \"category\"" +
        System.lineSeparator() +
        "\"1\",\"Finish HW9\",\"false\",\"03/22/2020\",\"1\",\"school\"" +
        System.lineSeparator() +
        "\"2\",\"Clean the classroom\",\"true\",\"02/28/2020\",\"1\",\"school\"", msg);
  }

  @Test (expected = ExecuteException.class)
  public void dateFormatError() throws ParseException, ExecuteException {
    Option newDueOption = new Option("due", true, false,
        "--due <due date> (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.");
    newDueOption.setArgName("Feb/28/2020");
    commandLine.addOption(newDueOption);
    commandLine.addOption(addTodoOption);
    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();
  }

  @Test
  public void completeExecutor() throws ParseException, ExecuteException {
//    commandLine.addOption(completeTodoOption);
//    defaultExecutor = new DefaultExecutor(commandLine);
//    defaultExecutor.execute();

  }

  @Test
  public void displayAll() throws ParseException, ExecuteException {

    commandLine.addOption(displayOption);

    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();
  }

  @Test
  public void showIncomplete() throws ParseException, ExecuteException {

    commandLine.addOption(displayOption);
    commandLine.addOption(showIncompleteOption);

    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();

  }

  @Test
  public void showCategory() throws ParseException, ExecuteException {

    commandLine.addOption(displayOption);
    commandLine.addOption(showCategoryOption);

    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();
  }

  @Test
  public void sortByDate() throws ParseException, ExecuteException {

    commandLine.addOption(displayOption);
    commandLine.addOption(sortByDateOption);

    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();
  }

  @Test
  public void sortByPriority() throws ParseException, ExecuteException {

    commandLine.addOption(displayOption);
    commandLine.addOption(sortByPriorityOption);

    defaultExecutor = new DefaultExecutor(commandLine);
    defaultExecutor.execute();
  }
}