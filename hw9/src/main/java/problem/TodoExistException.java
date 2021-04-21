package problem;

public class TodoExistException extends ExecuteException {
  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not initialized,
   * and may subsequently be initialized by a call to {@link #initCause}.
   */
  public TodoExistException(String message) {
    super(message);
  }
}
