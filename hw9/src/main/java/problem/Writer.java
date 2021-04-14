package problem;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Writer {

  public void write(CommandLine commandLine, String filepath)
      throws problem.ParseException, ParseException {

    Reader reader = new Reader();

    ToDos toDos = reader.read(filepath);

    Processor processor = new Processor();
    toDos = processor.process(commandLine, toDos);


    try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(filepath))) {

      outputFile.write(reader.getHeaders() + System.lineSeparator());

      for (ToDo toDo : toDos.getTodoList()) {
        outputFile.write(this.formatter(toDo) + System.lineSeparator());
      }

    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

  /**
   * To\Do -> String
   * @param toDo
   * @return
   */
  private String formatter(ToDo toDo) {
    StringBuilder todoInfo = new StringBuilder();
    todoInfo.append("\"").append(toDo.getID()).append("\",");
    todoInfo.append("\"").append(toDo.getText()).append("\",");
    todoInfo.append("\"").append(toDo.getCompleted()).append("\",");
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    todoInfo.append("\"").append(sdf.format(toDo.getDue())).append("\",");
    todoInfo.append("\"").append(toDo.getPriority()).append("\",");
    todoInfo.append("\"").append(toDo.getCategory()).append("\"");
    return todoInfo.toString();
  }


}
