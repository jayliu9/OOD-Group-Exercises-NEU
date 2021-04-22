package problem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class DefaultExecutor take CommandLine as input, execute
 * command according the commands
 */
public class DefaultExecutor {
  private static final String CSV_FILE = "csv-file";
  private Map<String, String> commands = new HashMap<>();
  private String csvPath;
  private ToDos todos;

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
      this.commands.put(opt.getOpt(), opt.getArgName());
    }
  }

  /**
   * Iterates the command line to process every option, especially "add、 complete、 display“,
   * using Factory pattern to create action processor.
   * @throws ParseException parse exception
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
   * Write the information to .csv file in the given path.
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
   * ExecutorFactory generate different Executor according to the commands
   */
  private static class ExecutorFactory {
    private static final String ADD = "add-todo";
    private static final String COMPLETE = "complete-todo";
    private static final String DISPLAY = "display";

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
     * Processes the todo list and the csv file based on the command line arguments
     * @param commands
     * @param toDos
     * @throws ParseException
     * @throws java.text.ParseException
     */
    void execute(Map<String, String> commands, ToDos toDos)
        throws ParseException, ExecuteException;
  }

  /**
   * if command is not execute command, just skip it
   */
  private static class SkipExecutor implements Executor {

    @Override
    public void execute(Map<String, String> commands, ToDos toDos) {
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
     * Using Builder Pattern to create a new ToDo object to add
     * @param commands
     * @param todos
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @Override
    public void execute(Map<String, String> commands, ToDos todos)
        throws ExecuteException{

      ToDo toDo = this.buildTodo(commands, todos);
      this.checkDuplicate(toDo, todos);
      todos.addTodo(toDo);
    }

    /**
     * check if new todo exists in current todos, if exist throw exception
     * @param toDo new todo
     * @param todos current todo list
     * @throws TodoExistException
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
     * build a new todo
     * @param commands user input commands
     * @param todos current todo list
     * @return a new todo
     * @throws java.text.ParseException
     */
    private ToDo buildTodo(Map<String, String> commands, ToDos todos)
        throws ExecuteException {
      String text = commands.get(TEXT);
      ToDo.Builder builder = new ToDo.Builder(text);

      Iterator<Map.Entry<String, String>> it = commands.entrySet().iterator();
      while (it.hasNext()){
        Map.Entry<String, String> entry = it.next();
        switch (entry.getKey()) {
          case COMPLETED:
            builder.setCompleted(true);
            break;
          case DUE:
            try {
              String dueStr = entry.getValue();
              SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
              builder.addDue(sf.parse(dueStr));
              break;
            } catch (java.text.ParseException e) {
              throw new ExecuteException("The beginning of the specified string cannot be parsed.");
            }
          case PRIORITY:
            builder.addPriority(Integer.parseInt(entry.getValue()));
            break;
          case CATEGORY:
            builder.addCategory(entry.getValue());
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
   * complete todo, if todo has been completed, throw exception
   */
  private static class CompleteExecutor implements Executor {
    private static final String COMPLETE = "complete-todo";

    @Override
    public void execute(Map<String, String> commands, ToDos toDos)
        throws TodoNotFoundException, ToDoAlreadyCompletedException {

      Iterator<Map.Entry<String, String>> it = commands.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, String> entry = it.next();
        if (entry.getKey().equals(COMPLETE)) {
          int id = Integer.parseInt(entry.getValue());
          ToDo todo = toDos.findToDo(id);
          this.checkCompleted(todo);
          todo.setCompleted(true);
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
   * Print todo list under specific conditions
   */
  private static class DisplayExecutor implements Executor {
    private static final String INCOMPLETE = "show-incomplete";
    private static final String CATEGORY = "show-category";
    private static final String DATE = "sort-by-date";
    private static final String PRIORITY = "sort-by-priority";
    private List<ToDo> list;

    @Override
    public void execute(Map<String, String> commands, ToDos toDos) {
      list = toDos.getTodoList();
      generateList(commands);
      printList();
    }

    /**
     * generate display todo list according to commandline
     * @param commands user input commandline
     */
    private void generateList(Map<String, String> commands) {

      Iterator<Map.Entry<String, String>> it = commands.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, String> entry = it.next();
        switch (entry.getKey()) {
          case INCOMPLETE:
            filterByIncomplete(list);
            break;
          case CATEGORY:
            filterByCategory(list, entry.getValue());
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
     * print todo list, if no todo in list, return a message
     */
    private void printList() {
      if (list.size() == 0) {
        System.out.println("No such ToDo in current list");
      } else {
        list.forEach(System.out::println);
      }
    }

    /**
     * Sort the Todos by date (ascending);
     * @param toDoList todo list
     */
    private void sortByDate(List<ToDo> toDoList) {
      this.list = toDoList.stream()
          .sorted(new DateComparator())
          .collect(Collectors.toList());
    }

    /**
     * Sort the Todos by priority (ascending);
     * @param toDoList todo list
     */
    private void sortByPriority(List<ToDo> toDoList) {
      this.list = toDoList.stream()
          .sorted(new PriorityComparator())
          .collect(Collectors.toList());
    }

    /**
     * Filter Todos with specific category
     * @param toDoList todo list
     * @param category a category
     */
    public void filterByCategory(List<ToDo> toDoList, String category) {
      this.list = toDoList.stream()
          .filter(todo -> todo.getCategory() != null)
          .filter(todo -> todo.getCategory().equals(category))
          .collect(Collectors.toList());
    }

    /**
     * get incomplete Todos
     * @param toDoList todo list
     */
    public void filterByIncomplete(List<ToDo> toDoList) {
      this.list = toDoList.stream()
          .filter(todo -> !todo.getCompleted())
          .collect(Collectors.toList());
    }
  }
}
