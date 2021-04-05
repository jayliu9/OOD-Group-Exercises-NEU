package nonprofits;

import java.util.Iterator;
import java.util.List;

public class MissingOptionException extends ParseException {

  /** The list of missing options and groups */
  private List missingOptions;

  /**
   * Construct a new <code>MissingSelectedException</code>
   * with the specified detail message.
   *
   * @param message the detail message
   */
  public MissingOptionException(String message)
  {
    super(message);
  }

  /**
   * Constructs a new <code>MissingSelectedException</code> with the
   * specified list of missing options.
   *
   * @param missingOptions the list of missing options and groups

   */
  public MissingOptionException(List missingOptions) {
    this(createMessage(missingOptions));
    this.missingOptions = missingOptions;
  }

  public MissingOptionException(OptionGroup group) {
    this(createMessage(group));
  }


  /**
   * Returns the list of options or option groups missing in the command line parsed.
   *
   * @return the missing options, consisting of String instances for simple
   *         options, and OptionGroup instances for required option groups.
   * @since 1.2
   */
  public List getMissingOptions()
  {
    return missingOptions;
  }

  /**
   * Build the exception message from the specified list of options.
   *
   * @param missingOptions the list of missing options and groups
   */
  private static String createMessage(List<?> missingOptions) {
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

