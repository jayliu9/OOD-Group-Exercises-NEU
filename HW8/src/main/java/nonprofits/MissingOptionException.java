package nonprofits;

import java.util.Iterator;
import java.util.List;

public class MissingOptionException extends ParseException {

  public MissingOptionException(String message) {
    super(message);
  }

  public MissingOptionException(List<String> missingOptions) {
    this(createMessage(missingOptions));
  }

  public MissingOptionException(OptionGroup group) {
    this(createMessage(group));
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

  private static String createMessage(OptionGroup group) {
    StringBuilder buf = new StringBuilder("At least has --");

    Iterator<?> it = group.getAllKeyOptionsNames().iterator();
    while (it.hasNext()) {
      buf.append(it.next());
      buf.append(" option");
      if (it.hasNext()) {
        buf.append(" or --");
      } else {
        buf.append(".");
      }
    }
    return buf.toString();
  }
}