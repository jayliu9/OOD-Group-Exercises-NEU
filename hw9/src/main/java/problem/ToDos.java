package problem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import problem.ToDo.Builder;

/**
 * This class stores information from csv file, including headers and todolist
 */
public class ToDos {

  private static final String SPLIT_REGEX = "\",\"";

  private String headers;
  private List<ToDo> todoList;

  public ToDos() {
    this.headers = null;
    this.todoList = new ArrayList<>();
  }

  public List<ToDo> getTodoList() {
    return this.todoList;
  }


  /**
   * Reads a ToDo object from a line
   * @param line
   * @throws ParseException
   */
  public void readTodo(String line) throws ParseException {
    this.todoList.add(this.storeFormatter(line));
  }


  /**
   * Adds a ToDo instance to ToDos list
   * @param todo
   */
  public void add(ToDo todo){
    this.todoList.add(todo);
  }

  public void setHeaders(String headers) {
    this.headers = headers;
  }

  public String getHeaders() {
    return this.headers;
  }

  /**
   * Converts a String to a To\Do instance.
   * @param info
   * @return
   */
  private ToDo storeFormatter(String info) throws ParseException {
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

  /**
   * To\Do -> String
   * @param toDo
   * @return
   */
  private String outputFormatter(ToDo toDo) {
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

  public String generateMsg() {
    String msg = this.headers + System.lineSeparator();
    for (ToDo todo : this.todoList) {
      msg += this.outputFormatter(todo) + System.lineSeparator();
    }
    return msg;
  }

  public ToDo findToDo(String iD) throws TodoNotFoundException {
    for (ToDo toDo : this.todoList) {
      if (toDo.getID().equals(iD))
        return toDo;
    }
    throw new TodoNotFoundException(iD + " is not existing!");
  }
}

