package problem;

public interface IProcessor {

  /**
   * Processes the todo list and the csv file based on the command line arguments
   * @param commandLine
   * @param toDos
   * @throws ParseException
   * @throws java.text.ParseException
   */
  void process(CommandLine commandLine, ToDos toDos)
          throws ParseException, java.text.ParseException, TodoNotFoundException;


}
