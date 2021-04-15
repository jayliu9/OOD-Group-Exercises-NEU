package problem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class Reader {

  private Reader() {
  }

  /**
   * Reads the information from the .csv file in the given path.
   * @param csvPath The .csv file path
   * @return A CSVProcessor that stores the information.
   */
  public static ToDos read(String csvPath) {
    ToDos todos = new ToDos();

    try (BufferedReader inputFile = new BufferedReader(new FileReader(csvPath))) {
      String line;
      if ((line = inputFile.readLine()) != null) {
        todos.setHeaders(line);
      }
      while ((line = inputFile.readLine()) != null) {
        todos.readTodo(line);
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
    return todos;
  }


}
