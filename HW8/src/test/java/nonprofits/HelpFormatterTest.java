package nonprofits;

import static org.junit.Assert.*;

import org.graalvm.compiler.nodes.calc.IntegerDivRemNode.Op;
import org.junit.Before;
import org.junit.Test;

public class HelpFormatterTest {
  private Option op1;
  private Option op11;
  private Options options;
  private HelpFormatter formatter;

  @Before
  public void setUp() throws Exception {
    op1 = new Option("email", false, false, "Generate email messages.");
    op11 = new Option("email-template", true, false, "email template.");
    options = new Options();
    options.addOption(op1);
    options.addOption(op11);
    formatter = new HelpFormatter();
  }

  @Test
  public void setExamples() {
    formatter.printHelp(options);
    String examples = "--email --email-template email-template.txt --output-dir emails"
        + "--csv-file customer.csv"
        + "\n";
    formatter.setExamples(examples);
    formatter.printHelp(options);
  }
}