package nonprofits;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardCodingProcessor {

  private static final int ORI_VALUE = -1;
  private static final int FIRST_NAME_INDEX = 0;
  private static final int LAST_NAME_INDEX = 1;
  private static final int ADDRESS_INDEX = 2;
  private static final int CITY_INDEX = 3;
  private static final int STATE_INDEX = 4;
  private static final int ZIP_INDEX = 5;
  private static final String TEXT = "Everything in our store will be 20% off between now and the "
      + "end of April! Stock up on our logo mugs, T shirts, and water bottles to show your support "
      + "and help raise awareness. Our magnets, plushies, and picture books, also make great gifts "
      + "for the children in your life.\n\n"
      + "Remember, all proceeds go to support our work and, if we can reach our goal of $10,000 in "
      + "sales by the end of April, an anonymous donor has pledged to match every $1 you spend. "
      + "Want to help out but don’t want to buy stuff? Visit our website to make a donation.\n\n"
      + "Sincerely,\n\nNon-Profit Director";

  /**
   * Constructor for a LetterProcessor class.
   * @param filePath The file path to process.
   */
  public HardCodingProcessor(String filePath) {
    this.writeFile(this.getInfo(filePath));
  }

  /**
   *
   * @param filePath
   * @return
   */
  private List<List<String>> getInfo(String filePath) {
    List<List<String>> lines = new ArrayList<>();
    try (
        BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line = "";
      int firstNameIndex = ORI_VALUE;
      int lastNameIndex = ORI_VALUE;
      int addressIndex = ORI_VALUE;
      int cityIndex = ORI_VALUE;
      int stateIndex = ORI_VALUE;
      int zipIndex = ORI_VALUE;

      if ((line = reader.readLine()) != null) {
        List<String> headers = Arrays.asList(this.trimHeadTail(line).split("\",\""));

        firstNameIndex = headers.indexOf("first_name");
        lastNameIndex = headers.indexOf("last_name");
        addressIndex = headers.indexOf("address");
        cityIndex = headers.indexOf("city");
        stateIndex = headers.indexOf("state");
        zipIndex = headers.indexOf("zip");
      }

      while ((line = reader.readLine()) != null) {
        String[] data = this.trimHeadTail(line).split("\",\"");
        List<String> info = new ArrayList<>();
        info.add(data[firstNameIndex]);
        info.add(data[lastNameIndex]);
        info.add(data[addressIndex]);
        info.add(data[cityIndex]);
        info.add(data[stateIndex]);
        info.add(data[zipIndex]);
        lines.add(info);
      }

    } catch (FileNotFoundException e) {
      System.out.println("*** OUPS! A file was not found : " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Something went wrong! : " + e.getMessage());
      e.printStackTrace();
    }
    System.out.println(lines);
    return lines;
  }

  /**
   *
   * @param string
   * @return
   */
  private String trimHeadTail(String string) {
    return string.substring(1, string.length() - 1);
  }

  /**
   *
   * @param lines
   */
  private void writeFile(List<List<String>> lines) {
    for (List<String> line : lines) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(
          line.get(FIRST_NAME_INDEX) + "_" + line.get(LAST_NAME_INDEX) + "_letter.txt"))) {
        writer.write(this.generateTemplate(line) + TEXT);
      } catch (FileNotFoundException e) {
        System.out.println("*** OUPS! A file was not found : " + e.getMessage());
        e.printStackTrace();
      } catch (IOException e) {
        System.out.println("Something went wrong! : " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  /**
   *
   * @param line
   * @return
   */
  private String generateTemplate(List<String> line) {
    String msg = String.format("%s %s\n"
            + "%s\n"
            + "%s, %s, %s\n"
            + "Dear %s %s,\n"
        , line.get(FIRST_NAME_INDEX), line.get(LAST_NAME_INDEX),
        line.get(ADDRESS_INDEX), line.get(CITY_INDEX), line.get(STATE_INDEX), line.get(ZIP_INDEX),
        line.get(FIRST_NAME_INDEX), line.get(LAST_NAME_INDEX));
    return msg;
  }
}

