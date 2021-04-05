package nonprofits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a processor that processes the information in .csv file and stores it.
 */
public class CSVProcessor {

  private static final String HEADER_REGEX = "\\b\\w+\\b";
  private static final String SPLIT_REGEX = "\",\"";

  private List<String> headers;
  private List<List<String>> info;

  /**
   * Constructor for the CSVProcessor class.
   */
  public CSVProcessor() {
    this.headers = new ArrayList<>();
    this.info = new ArrayList<>();
  }

  /**
   * Gets the processed headers in .csv file
   * @return A list of the processed headers.
   */
  public List<String> getHeaders() {
    return this.headers;
  }

  /**
   * Gets the processed detailed information in .csv file.
   * @return A nested list of rows of detailed information.
   */
  public List<List<String>> getInfo() {
    return this.info;
  }

  /**
   * Processes the header line and stores
   * @param headerLine The header line to store
   */
  public void addHeader(String headerLine) {
    Pattern pForHeader = Pattern.compile(HEADER_REGEX);
    Matcher m = pForHeader.matcher(headerLine);
    while (m.find()) {
      this.headers.add(m.group());
    }
  }

  /**
   * Processes the detailed information line and stores
   * @param line The line to store
   */
  public void addInfo(String line) {
    String trimmed = this.trimHeadTail(line);
    String[] split = trimmed.split(SPLIT_REGEX);
    List<String> row = Arrays.asList(split);
    this.info.add(row);
  }

  /**
   * Removes the head quote and the tail quote from the line of csv file.
   * @param s The line to remove
   * @return The line without head quote and tail quote.
   */
  private String trimHeadTail(String s) {
    return s.substring(1, s.length() - 1);
  }
}
