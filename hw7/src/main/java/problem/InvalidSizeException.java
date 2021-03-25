package problem;

public class InvalidSizeException extends Exception {


  public InvalidSizeException() {
    super("Invalid size, size should be non-negative!");
  }
}
