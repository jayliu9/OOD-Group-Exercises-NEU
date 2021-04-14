package problem;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A formatter of help messages for command line options.
 */
public class HelpFormatter {
  public static final String DEFAULT_SYNTAX_PREFIX = "USAGE: ";
  public static final String DEFAULT_EXAMPLE_PREFIX = "EXAMPLE: ";
  public static final String DEFAULT_SEPARATOR = "\n";

  protected  String examples;
  protected  StringBuilder msg;

  public HelpFormatter() {
    // do nothing
  }

  /**
   * set examples of the usage
   * @param string examples
   */
  public void setExamples(String string) {
    examples = string;
  }

  /**
   * append examples to command message
   */
  private void appendExample() {
    if (examples != null) {
      msg.append(DEFAULT_EXAMPLE_PREFIX + DEFAULT_SEPARATOR);
      msg.append(examples + "\n");
    }
  }

  /**
   * append option description to command message, order doesn't matter
   * @param options
   */
  private void appendOption(Options options) {
    List<Option> optList = new ArrayList<>(options.getOptions());
    for (Iterator<Option> it = optList.iterator(); it.hasNext();) {
      Option option = it.next();
      msg.append(option.getDescription() + DEFAULT_SEPARATOR);
    }
  }

  /**
   * print help messages
   * @param options all candidate options
   */
  public void printHelp(Options options) {
    PrintWriter pw = new PrintWriter(System.out);
    printUsage(pw, options);
    pw.flush();
  }

  /**
   * print usage message, including option description and usage examples
   * @param pw print writer
   * @param options all candidate options
   */
  private void printUsage(PrintWriter pw, Options options) {
    msg = new StringBuilder().append(DEFAULT_SYNTAX_PREFIX + DEFAULT_SEPARATOR);
    appendOption(options);
    appendExample();
    pw.println(msg.toString());
  }
}
