package problem;

public class CompleteOptionProcessor implements IProcessor {

  @Override
  public void process(CommandLine commandLine, ToDos toDos)
          throws ParseException, java.text.ParseException, TodoNotFoundException {
    String iD = commandLine.getOptionValue("complete-todo");
    ToDo todo = toDos.findToDo(iD);
    todo.setCompleted(true);
  }
}
