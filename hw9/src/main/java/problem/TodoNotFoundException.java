package problem;

public class TodoNotFoundException extends Exception {

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not initialized,
   * and may subsequently be initialized by a call to {@link #initCause}.
   */
  public TodoNotFoundException(String message) {
    super(message);
  }
}
