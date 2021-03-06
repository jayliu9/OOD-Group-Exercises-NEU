package problem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Class DefaultExecutor take CommandLine as input, execute
 * command according the commands
 */
public class DefaultExecutor {
  private static final String CSV_FILE = "csv-file";
  private Map<String, List<String>> commands = new TreeMap<>();
  private String csvPath;
  private ToDos todos;

  /**
   * Constructor for an DefaultExecutor class.
   * @param cmd The user input command.
   */
  public DefaultExecutor(CommandLine cmd) {
    this.convertCommand(cmd);
    this.csvPath = cmd.getOptionValue(CSV_FILE);
    this.readCSV(cmd);
  }

  /**
   * convert CommandLine to a Map<command_name, command_argument>
   * @param cmd user input command
   */
  private void convertCommand(CommandLine cmd) {
    for (Option opt : cmd.getOptions()) {
      if (this.commands.containsKey(opt.getOpt())) {
        List<String> argList = this.commands.get(opt.getOpt());
        argList.add(opt.getArgName());
        this.commands.put(opt.getOpt(), argList);

      } else {
        List<String> newList = new ArrayList<>();
        newList.add(opt.getArgName());
        this.commands.put(opt.getOpt(), newList);
      }
    }
  }

  /**
   * Iterates the command line to process every option, especially "add、 complete、 display“,
   * using Factory pattern to create action processor.
   * @throws ParseException parse exception
   * @throws ExecuteException if there are any problems encountered while executing the command line.
   */
  public void execute()
      throws ParseException, ExecuteException {
    for (String key : this.commands.keySet()) {
      Executor executor = ExecutorFactory.makeExecutor(key);
      executor.execute(this.commands, this.todos);
    }
    this.updateCSV();
  }

  /**
   * Helper function to read csv file and decode string format to Todos instance
   * @param cmd
   */
  private void readCSV(CommandLine cmd) {
    this.todos = this.read(this.csvPath);
  }

  /**
   * Updates CSV file after processing
   */
  private void updateCSV() {
    String csv = this.todos.generateMsg();
    this.write(csv, this.csvPath);
  }

  /**
   * Reads the information from the .csv file in the given path.
   * @param csvPath The .csv file path
   * @return A CSVProcessor that stores the information.
   */
  private ToDos read(String csvPath) {
    ToDos todos = new ToDos();
    try (BufferedReader inputFile =new BufferedReader(new FileReader(csvPath))) {
      String line;
      // always skip first line, header has already been set in ToDos
      line = inputFile.readLine();
      while ((line = inputFile.readLine()) != null) {
        todos.readTodo(line);
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
    } catch (java.text.ParseException e) {
      e.printStackTrace();
    }
    return todos;
  }

  /**
   * Writes the information to .csv file in the given path.
   * @param msg the generated information
   * @param filepath The .csv file path
   */
  private void write(String msg, String filepath) {

    try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(filepath))) {
      outputFile.write(msg);
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

  /**
   * Using factory pattern generate different Executor according to the commands.
   */
  private static class ExecutorFactory {
    private static final String ADD = "add-todo";
    private static final String COMPLETE = "complete-todo";
    private static final String DISPLAY = "display";

    /**
     * Constructs ExecutorFactory.
     * @param commandName The command line token to handle.
     * @return different executors according to the commandName.
     */
    private static Executor makeExecutor(String commandName) {
      switch (commandName) {
        case ADD:
          return new AddExecutor();
        case COMPLETE:
          return new CompleteExecutor();
        case DISPLAY:
          return new DisplayExecutor();
      }
      return new SkipExecutor();
    }
  }

  /**
   * Executor interface manage different kind of Executor
   */
  private interface Executor {

    /**
     * Executes the toDos according to the specified commands.
     * @param commands The specified commands.
     * @param toDos The ToDos object.
     * @throws ParseException if there are any problems encountered while parsing the command line tokens.
     * @throws ExecuteException if there are any problems encountered while executing the commandline.
     */
    void execute(Map<String, List<String>> commands, ToDos toDos)
        throws ParseException, ExecuteException;
  }

  /**
   * If command is not execute command, just skip it
   */
  private static class SkipExecutor implements Executor {

    @Override
    public void execute(Map<String, List<String>> commands, ToDos toDos) {
      // do nothing
    }
  }

  /**
   * Add a new todo to todos
   */
  private static class AddExecutor implements Executor {
    private static final String TEXT = "todo-text";
    private static final String COMPLETED = "completed";
    private static final String DUE = "due";
    private static final String PRIORITY = "priority";
    private static final String CATEGORY = "category";

    /**
     * Executes the toDos according to the specified commands. Using Builder Pattern to create a new ToDo object to add
     * @param commands The specified commands.
     * @param todos The ToDos object.
     * @throws ExecuteException if there are any problems encountered while executing the commandline.
     */
    @Override
    public void execute(Map<String, List<String>> commands, ToDos todos)
        throws ExecuteException{

      ToDo toDo = this.buildTodo(commands, todos);
      this.checkDuplicate(toDo, todos);
      todos.addTodo(toDo);
    }

    /**
     * Checks if new to\do exists in current todos.
     * @param toDo A new todo to be checked.
     * @param todos The current to\do list.
     * @throws TodoExistException if the new to\do already exits in the current to\do list.
     */
    private void checkDuplicate(ToDo toDo, ToDos todos) throws TodoExistException {
      ToDo findTodo = todos.getTodoList().stream()
          .filter(todo -> todo.getText().equals(toDo.getText()))
          .findAny()
          .orElse(null);

      if(findTodo != null) {
        throw new TodoExistException("Todo " + "'" + findTodo.getText() + "'" + " has already exists");
      }
    }

    /**
     * Builds a new To\Do.
     * @param commands The user input commands.
     * @param todos The current to\do list.
     * @return A new to\do to be added.
     * @throws ExecuteException if there are any problems encountered while executing the commandline.
     */
    private ToDo buildTodo(Map<String, List<String>> commands, ToDos todos)
        throws ExecuteException {
      String text = commands.get(TEXT).get(commands.get(TEXT).size() - 1);
      ToDo.Builder builder = new ToDo.Builder(text);

      Iterator<Entry<String, List<String>>> it = commands.entrySet().iterator();
      while (it.hasNext()){
        Entry<String, List<String>> entry = it.next();
        switch (entry.getKey()) {
          case COMPLETED:
            builder.setCompleted(true);
            break;
          case DUE:
            try {
              String dueStr = entry.getValue().get(entry.getValue().size() - 1);
              SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
              builder.addDue(sf.parse(dueStr));
              break;
            } catch (java.text.ParseException e) {
              throw new ExecuteException("The beginning of the specified string cannot be parsed.");
            }
          case PRIORITY:
            builder.addPriority(Integer.parseInt(entry.getValue().get(entry.getValue().size() - 1)));
            break;
          case CATEGORY:
            builder.addCategory(entry.getValue().get(entry.getValue().size() - 1));
            break;
        }
      }
      // get max id of current todo list, and +1 as the ID of new todo
      int maxID = todos.getTodoList().stream()
          .max(new IdComparator())
          .map(ToDo::getID)
          .orElse(todos.getTodoList().size());
      builder.setID(maxID + 1);
      ToDo toDo = builder.build();

      return toDo;
    }
  }

  /**
   * A CompleteExecutor class, completing a To\Do.
   */
  private static class CompleteExecutor implements Executor {
    private static final String COMPLETE = "complete-todo";

    /**
     * Executes the toDos according to the specified commands.
     * @param commands The specified commands.
     * @param toDos The ToDos object.
     * @throws ExecuteException if there are any problems encountered while executing the commandline.
     */
    @Override
    public void execute(Map<String, List<String>> commands, ToDos toDos)
            throws TodoNotFoundException, ToDoAlreadyCompletedException {

      Iterator<Map.Entry<String, List<String>>> it = commands.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, List<String>> entry = it.next();
        if (entry.getKey().equals(COMPLETE)) {
          List<Integer> idList = entry.getValue().stream().mapToInt(Integer::parseInt).boxed().collect(
                  Collectors.toList());
          for (int id : idList) {
            ToDo todo = toDos.findToDo(id);
            this.checkCompleted(todo);
            todo.setCompleted(true);
          }
        }
      }
    }

    /**
     * check whether the todo is completed or not, if completed throw exception
     * @param todo a todo need complete
     * @throws ToDoAlreadyCompletedException
     */
    private void checkCompleted(ToDo todo) throws ToDoAlreadyCompletedException {
      if (todo.getCompleted()) {
        throw new ToDoAlreadyCompletedException("ToDo" + todo.getID() + " has already completed");
      }
    }
  }

  /**
   * Prints to\do list under specific conditions
   */
  private static class DisplayExecutor implements Executor {
    private static final String INCOMPLETE = "show-incomplete";
    private static final String CATEGORY = "show-category";
    private static final String DATE = "sort-by-date";
    private static final String PRIORITY = "sort-by-priority";
    private List<ToDo> list;

    @Override
    public void execute(Map<String, List<String>> commands, ToDos toDos) {
      list = toDos.getTodoList();
      generateList(commands);
      printList();
    }

    /**
     * Generates display to\do list according to commandline.
     * @param commands user input commandline
     */
    private void generateList(Map<String, List<String>> commands) {

      Iterator<Map.Entry<String, List<String>>> it = commands.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, List<String>> entry = it.next();
        switch (entry.getKey()) {
          case INCOMPLETE:
            filterByIncomplete(list);
            break;
          case CATEGORY:
            filterByCategory(list, entry.getValue().toString());
            break;
          case DATE:
            sortByDate(list);
            break;
          case PRIORITY:
            sortByPriority(list);
            break;
        }
      }
    }

    /**
     * Prints to\do list, if no to\do in list, return a message.
     */
    private void printList() {
      if (list.size() == 0) {
        System.out.println("No such ToDo in current list");
      } else {
        list.forEach(System.out::println);
      }
    }

    /**
     * Sorts the Todos by date (ascending);
     * @param toDoList to\do list
     */
    private void sortByDate(List<ToDo> toDoList) {
      this.list = toDoList.stream()
          .sorted(new DateComparator())
          .collect(Collectors.toList());
    }

    /**
     * Sorts the Todos by priority (ascending);
     * @param toDoList todo list
     */
    private void sortByPriority(List<ToDo> toDoList) {
      this.list = toDoList.stream()
          .sorted(new PriorityComparator())
          .collect(Collectors.toList());
    }

    /**
     * Filters Todos with specific category
     * @param toDoList to\do list
     * @param category a category
     */
    public void filterByCategory(List<ToDo> toDoList, String category) {
      this.list = toDoList.stream()
          .filter(todo -> todo.getCategory() != null)
          .filter(todo -> todo.getCategory().equals(category))
          .collect(Collectors.toList());
    }

    /**
     * Gets incomplete Todos.
     * @param toDoList to\do list
     */
    public void filterByIncomplete(List<ToDo> toDoList) {
      this.list = toDoList.stream()
          .filter(todo -> !todo.getCompleted())
          .collect(Collectors.toList());
    }
  }
}