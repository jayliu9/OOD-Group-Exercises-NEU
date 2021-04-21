package problem;

public class ToDoAlreadyCompletedException extends ExecuteException {

  public ToDoAlreadyCompletedException(String message) {
    super(message);
  }
}
