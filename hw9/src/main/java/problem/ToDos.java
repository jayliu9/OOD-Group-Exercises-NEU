package problem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class stores information from csv file, including headers and todolist
 */
public class ToDos {

  private static final String SPLIT_REGEX = "\",\"";
  private static final String HEADER = "\"id\", \"text\", \"completed\", \"due\", \"priority\", \"category\"";

  private String headers;
  private List<ToDo> todoList;

  /**
   * Constructor for a ToDos class.
   */
  public ToDos() {
    this.headers = HEADER;
    this.todoList = new ArrayList<>();
  }

  /**
   * Gets the to\do list.
   * @return the to\do list.
   */
  public List<ToDo> getTodoList() {
    return this.todoList;
  }

  /**
   * Reads a To\Do object from a line.
   * @param line the given String line.
   * @throws ParseException if there are any problems encountered while parsing the due date.
   */
  public void readTodo(String line) throws ParseException {
    this.todoList.add(this.storeFormatter(line));
  }


  /**
   * Adds a To\Do instance to ToDos list
   * @param todo The ToDo to be added.
   */
  public void addTodo(ToDo todo){
    this.todoList.add(todo);
  }

  /**
   * Converts a String to a To\Do instance.
   * @param info The String to be converted.
   * @return a To\Do instance converted from a String.
   * @throws ParseException if there are any problems encountered while parsing the due date.
   */
  private ToDo storeFormatter(String info) throws ParseException {
    String trimmed = this.trimHeadTail(info);
    String[] split = trimmed.split(SPLIT_REGEX);
    List<String> row = Arrays.asList(split);

    // decode csv todo list(A String) to valid field value for ToDo builder
    String text = row.get(1);
    Integer id = idFormatter(row.get(0));
    Date date = dateFormatter(row.get(3));
    Integer priority = priorityFormatter(row.get(4));
    Boolean complete = completeFormatter(row.get(2));
    String category = categoryFormatter(row.get(5));

    return new ToDo.Builder(text)
        .setID(id)
        .setCompleted(complete)
        .addDue(date)
        .addPriority(priority)
        .addCategory(category).build();
  }

  /**
   * Validation of id String.
   * @param s The String to be validated.
   * @return An Integer converted from a String.
   */
  private Integer idFormatter(String s) {
    return Integer.valueOf(s);
  }

  /**
   * Validation of date String.
   * @param s The String to be validated.
   * @return null if the string is "?", a Date converted from a String otherwise.
   * @throws ParseException if there are any problems encountered while parsing the due date.
   */
  private Date dateFormatter(String s) throws ParseException {
    if (s.equals("?")) {
      return null;
    }
    Date date;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    date = sdf.parse(s);
    return date;
  }

  /**
   * Validation of priority String.
   * @param s The String to be validated.
   * @return null if the string is "?", an Integer converted from a String otherwise.
   */
  private Integer priorityFormatter(String s) {
    if (s.equals("?")) {
      return null;
    }
    return Integer.valueOf(s);
  }

  /**
   * Validation of complete String. Converts String to Boolean.
   * @param s The String to be validated.
   * @return a Boolean converted from a String.
   */
  private Boolean completeFormatter(String s) {
    return Boolean.parseBoolean(s);
  }

  /**
   * Validation of category String.
   * @param s The String to be validated.
   * @return null if the string is "?", s otherwise.
   */
  private String categoryFormatter(String s) {
    if (s.equals("?")) {
      return null;
    }
    return s;
  }

  /**
   * Removes the head quote and the tail quote from the line of csv file.
   * @param s The line to remove
   * @return The line without head quote and tail quote.
   */
  private String trimHeadTail(String s) {
    return s.substring(1, s.length() - 1);
  }

  /**
   * Converts a To\Do instance to a String.
   * @param toDo A ToDo instance.
   * @return A String converted from a To\Do instance.
   */
  private String outputFormatter(ToDo toDo) {
    StringBuilder todoInfo = new StringBuilder();
    todoInfo.append("\"").append(toDo.getID()).append("\",");
    todoInfo.append("\"").append(toDo.getText()).append("\",");
    todoInfo.append("\"").append(toDo.getCompleted()).append("\",");

    if (toDo.getDue() == null) {
      todoInfo.append("\"").append("?").append("\",");
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      todoInfo.append("\"").append(sdf.format(toDo.getDue())).append("\",");
    }

    if (toDo.getPriority() == null) {
      todoInfo.append("\"").append("?").append("\",");
    } else {
      todoInfo.append("\"").append(toDo.getPriority()).append("\",");
    }

    if (toDo.getCategory() == null) {
      todoInfo.append("\"").append("?").append("\"");
    } else {
      todoInfo.append("\"").append(toDo.getCategory()).append("\"");
    }

    return todoInfo.toString();
  }

  /**
   * Generates message that contains headers and all ToDos.
   * @return message that contains headers and all ToDos.
   */
  public String generateMsg() {
    StringBuilder msg = new StringBuilder(this.headers + System.lineSeparator());
    for (ToDo todo : this.todoList) {
      msg.append(this.outputFormatter(todo)).append(System.lineSeparator());
    }
    return msg.toString();
  }

  /**
   * Finds To\Do that satisfies the specified ID.
   * @param iD The specified ID.
   * @return The To\DO that satisfies the specified ID.
   * @throws TodoNotFoundException if there no To\Do satisfies the specified ID.
   */
  public ToDo findToDo(Integer iD) throws TodoNotFoundException {
    for (ToDo toDo : this.todoList) {
      if (toDo.getID().equals(iD))
        return toDo;
    }
    throw new TodoNotFoundException("ToDo" + iD + " not exists!");
  }

  /**
   * Checks if two objects are equal
   * @param o the object to compare this to
   * @return true if these two objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ToDos toDos = (ToDos) o;
    return this.todoList.equals(toDos.todoList);
  }

  /**
   * Gets a hash code value for the object.
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.todoList);
  }
}