package problem;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Writer {

  private Writer() {
  }

  public static void write(String msg, String filepath) {
/*
    Reader reader = new Reader();

    ToDos toDos = reader.read(filepath);

    Processor processor = new Processor();
    toDos = processor.process(commandLine, toDos);
*/

    try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(filepath))) {

      outputFile.write(msg);

    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }
}
