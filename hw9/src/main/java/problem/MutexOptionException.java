package problem;

public class MutexOptionException extends ParseException{

  public MutexOptionException(Option givenOption, Option missingOption) {
    super(createMessage(givenOption, missingOption));
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
