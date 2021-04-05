package nonprofits;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class representing common fields and behaviors of a template.
 */
public class Template {

  private static final String RE = "\\[\\[(\\w+)]]";
  private String content;
  private Set<String> placeholders;

  /**
   * Constructor for the AbstractTemplate class
   * @param content The content of the template
   */
  public Template(String content) {
    this.content = content;
    this.placeholders = new HashSet<>();
    this.findPlaceholders();
  }

  /**
   * Gets the content of the template
   * @return The content of the template
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Finds all the placeholders in the templates and stores in a list of string.
   */
  private void findPlaceholders() {
    Pattern pattern = Pattern.compile(RE);
    Matcher m = pattern.matcher(this.content);
    while (m.find()) {
      this.placeholders.add(m.group());
    }
  }

  /**
   * Helper function that extracts the words in the placeholder.
   * @param placeholder The placeholder to process
   * @return The words in the placeholder.
   */
  private String processHolder(String placeholder) {
    String processed = "";
    Pattern pattern = Pattern.compile(RE);
    Matcher m = pattern.matcher(placeholder);
    if (m.matches()) {
      processed = m.group(1);
    }
    return processed;
  }

  /**
   * Replaces the placeholders in the template with the specified information.
   * @param headers The header line of the .csv file
   * @param infoRow The row of detailed information of the .csv file
   * @return The template after proper replacement.
   */
  public String replaceHolders(List<String> headers, List<String> infoRow) {
    String afterReplaced = this.content;
    for (String placeholder : this.placeholders) {
      Integer index = headers.indexOf(this.processHolder(placeholder));
      afterReplaced = afterReplaced.replace(placeholder, infoRow.get(index));
    }
    return afterReplaced;
  }
}
