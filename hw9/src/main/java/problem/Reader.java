package problem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Reader {

  private static final String SPLIT_REGEX = "\",\"";
  private String headers;
  private ToDos toDos = new ToDos();


  /**
   * Reads the information from the .csv file in the given path.
   * @param csvPath The .csv file path
   * @return A CSVProcessor that stores the information.
   */
  public ToDos read(String csvPath) {

    try (BufferedReader inputFile = new BufferedReader(new FileReader(csvPath))) {
      String line;

      if ((line = inputFile.readLine()) != null) {
        this.headers = line;
      }
      while ((line = inputFile.readLine()) != null) {
        this.toDos.add(this.formatter(line));
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return toDos;
  }

  /**
   * Converts a String to a To\Do instance.
   * @param info
   * @return
   */
  private ToDo formatter(String info) throws ParseException {
    String trimmed = this.trimHeadTail(info);
    String[] split = trimmed.split(SPLIT_REGEX);
    List<String> row = Arrays.asList(split);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date date = sdf.parse(row.get(3));
    return new ToDo.Builder(row.get(1)).
        setID(row.get(0))
        .setCompleted(Boolean.getBoolean(row.get(2)))
        .addDue(date)
        .addPriority(Integer.parseInt(row.get(4)))
        .addCategory(row.get(5)).build();
  }

  private String trimHeadTail(String s) {
    return s.substring(1, s.length() - 1);
  }

  public String getHeaders() {
    return this.headers;
  }

}
