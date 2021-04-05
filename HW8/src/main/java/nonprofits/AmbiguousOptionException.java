package nonprofits;

import java.util.Collection;
import java.util.Iterator;

public class AmbiguousOptionException extends UnrecognizedOptionException {

  /** The list of options matching the partial name specified */
  private final Collection<String> matchingOptions;

  /**
   * Constructs a new AmbiguousOptionException.
   *
   * @param option          the partial option name
   * @param matchingOptions the options matching the name
   */
  public AmbiguousOptionException(String option, Collection<String> matchingOptions) {
    super(createMessage(option, matchingOptions), option);
    this.matchingOptions = matchingOptions;
  }

  /**
   * Returns the options matching the partial name.
   * @return a collection of options matching the name
   */
  public Collection<String> getMatchingOptions()
  {
    return matchingOptions;
  }

  /**
   * Build the exception message from the specified list of options.
   *
   * @param option
   * @param matchingOptions
   * @return
   */
  private static String createMessage(String option, Collection<String> matchingOptions) {
    StringBuilder buf = new StringBuilder("Ambiguous option: '");
    buf.append(option);
    buf.append("'  (could be: ");

    Iterator<String> it = matchingOptions.iterator();
    while (it.hasNext()) {
      buf.append("'");
      buf.append(it.next());
      buf.append("'");
      if (it.hasNext()) {
        buf.append(", ");
      }
    }
    buf.append(")");

    return buf.toString();
  }
}
