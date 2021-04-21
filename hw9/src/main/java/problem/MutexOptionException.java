package problem;

public class MutexOptionException extends ParseException{
  public MutexOptionException(String message)
  {
    super(message);
  }

  public MutexOptionException(Option givenOption, Option missingOption) {
    this(createMessage(givenOption, missingOption));
  }

  private static String createMessage(Option givenOption, Option mutexOption) {
    StringBuilder buf = new StringBuilder();
    buf.append("--");
    buf.append(givenOption.getOpt());
    buf.append(" cannot combined with --");
    buf.append(mutexOption.getOpt());

    return buf.toString();
  }

}
