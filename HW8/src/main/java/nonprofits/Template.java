package nonprofits;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a template.
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
    Pattern pattern = Pattern.compile(RE);
    Matcher m = pattern.matcher(placeholder);
    m.matches();
    return m.group(1);
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

  /**
   * Checks if two objects are equal
   * @param o the object to compare this to
   * @return true if these two objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Template template = (Template) o;
    return this.content.equals(template.content);
  }

  /**
   * Gets a hash code value for the object.
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.content);
  }

  /**
   * Creates a string representation of the Template.
   * @return a string representation of the Template.
   */
  @Override
  public String toString() {
    return "Template{" +
        "content='" + content +
        '}';
  }
}
