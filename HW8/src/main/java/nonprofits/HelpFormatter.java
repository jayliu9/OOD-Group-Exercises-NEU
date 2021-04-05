package nonprofits;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HelpFormatter {
  public static final String DEFAULT_SYNTAX_PREFIX = "USAGE: ";
  public static final String DEFAULT_EXAMPLE_PREFIX = "EXAMPLE: ";
  public static final String DEFAULT_SEPARATOR = "\n";

  protected String examples;
  protected StringBuilder msg;

  public HelpFormatter() {
    // do nothing
  }

  public void setExamples(String string) {
    examples = string;
  }

  private void appendExample() {
    if (examples != null) {
      msg.append(DEFAULT_EXAMPLE_PREFIX + DEFAULT_SEPARATOR);
      msg.append(examples + "\n");
    }
  }

  private void appendOption(Options options) {
    List<Option> optList = new ArrayList<>(options.getOptions());
    for (Iterator<Option> it = optList.iterator(); it.hasNext();) {
      Option option = it.next();
      msg.append(option.getDescription() + DEFAULT_SEPARATOR);
    }
  }

  public void printHelp(Options options) {
    PrintWriter pw = new PrintWriter(System.out);
    printUsage(pw, options);
    pw.flush();
  }

  public void printUsage(PrintWriter pw, Options options) {
    msg = new StringBuilder().append(DEFAULT_SYNTAX_PREFIX + DEFAULT_SEPARATOR);
    appendOption(options);
    appendExample();
    pw.println(msg.toString());
  }
}
