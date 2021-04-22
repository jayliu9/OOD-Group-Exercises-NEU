package problem;

import java.util.Iterator;
import java.util.List;

public class MissingOptionException extends ParseException {

  public MissingOptionException(List<String> missingOptions) {
    super(createMessage(missingOptions));
  }

  /**
   * Build the exception message from the specified list of options.
   *
   * @param missingOptions the list of missing options and groups
   */
  private static String createMessage(List<String> missingOptions) {
    StringBuilder buf = new StringBuilder("Missing required option");
    buf.append(missingOptions.size() == 1 ? "" : "s");
    buf.append(": --");

    Iterator<?> it = missingOptions.iterator();
    while (it.hasNext()) {
      buf.append(it.next());
      if (it.hasNext()) {
        buf.append(", --");
      }
    }

    return buf.toString();
  }
}