package problem;

public interface IProcessor {

  public ToDos process(CommandLine commandLine, ToDos toDos)
      throws ParseException, java.text.ParseException;


}
