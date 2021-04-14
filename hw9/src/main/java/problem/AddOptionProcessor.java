package problem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOptionProcessor implements IProcessor {

  @Override
  public ToDos process(CommandLine commandLine, ToDos toDos) throws ParseException, java.text.ParseException {
    String text; // The only required parameter
    boolean completed = false; // Default false
    Date due = null;
    int priority = 3; // Default 3
    String category = null;


    if (!commandLine.hasOption("todo-text")) {
      throw new MissingOptionException("--todo-text is required");
    }
    text = commandLine.getOptionValue("todo-text");

    if (commandLine.hasOption("completed")) {
      completed = true;
    }
    if (commandLine.hasOption("due")) {
      String dueStr = commandLine.getOptionValue("due");
      SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
      due = sf.parse(dueStr);
    }
    if (commandLine.hasOption("priority")) {
      priority = Integer.parseInt(commandLine.getOptionValue("priority"));
    }
    if (commandLine.hasOption("category")) {
      category = commandLine.getOptionValue("category");
    }

    ToDo toDo = new ToDo.Builder(text).setCompleted(completed).addDue(due).addPriority(priority).addCategory(category).build();

    toDos.add(toDo);
    return toDos;
  }
}
