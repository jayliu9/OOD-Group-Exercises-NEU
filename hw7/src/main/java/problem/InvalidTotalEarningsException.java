package problem;

public class InvalidTotalEarningsException extends Exception {

  public InvalidTotalEarningsException() {
    super("Invalid total earnings. Earning should be a non-negative number!");
  }
}
