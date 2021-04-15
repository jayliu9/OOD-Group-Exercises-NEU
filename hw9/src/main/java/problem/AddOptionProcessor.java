package problem;

import java.text.SimpleDateFormat;
import java.util.Date;
import problem.ToDo.Builder;

public class AddOptionProcessor implements IProcessor {

  /**
   * Using Builder Pattern to create a new ToDo object to add
   * @param commandLine
   * @param todos
   * @throws ParseException
   * @throws java.text.ParseException
   */
  @Override
  public void process(CommandLine commandLine, ToDos todos) throws ParseException, java.text.ParseException {
    String text = commandLine.getOptionValue("todo-text");
    ToDo.Builder builder = new ToDo.Builder(text);


    if (commandLine.hasOption("completed")) {
      builder.setCompleted(true);
    }
    if (commandLine.hasOption("due")) {
      String dueStr = commandLine.getOptionValue("due");
      SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
      builder.addDue(sf.parse(dueStr));
    }
    if (commandLine.hasOption("priority")) {
      builder.addPriority(Integer.parseInt(commandLine.getOptionValue("priority")));
    }
    if (commandLine.hasOption("category")) {
      builder.addCategory(commandLine.getOptionValue("category"));
    }

    ToDo toDo = builder.build();

    todos.add(toDo);
  }
}
