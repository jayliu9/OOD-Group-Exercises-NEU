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

  public ToDos() {
    this.headers = HEADER;
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
  public void addTodo(ToDo todo){
    this.todoList.add(todo);
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


  private Integer idFormatter(String s) {
    return Integer.valueOf(s);
  }

  private Date dateFormatter(String s) throws ParseException {
    if (s.equals("?")) {
      return null;
    }
    Date date;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    date = sdf.parse(s);
    return date;
  }

  private Integer priorityFormatter(String s) {
    if (s.equals("?")) {
      return null;
    }
    return Integer.valueOf(s);
  }

  private Boolean completeFormatter(String s) {
    return Boolean.parseBoolean(s);
  }

  private String categoryFormatter(String s) {
    if (s.equals("?")) {
      return null;
    }
    return s;
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

  public String generateMsg() {
    String msg = this.headers + System.lineSeparator();
    for (ToDo todo : this.todoList) {
      msg += this.outputFormatter(todo) + System.lineSeparator();
    }
    return msg;
  }

  public ToDo findToDo(Integer iD) throws TodoNotFoundException {
    for (ToDo toDo : this.todoList) {
      if (toDo.getID() == iD)
        return toDo;
    }
    throw new TodoNotFoundException("ToDo" + iD + " not exists!");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ToDos toDos = (ToDos) o;
    return this.todoList.equals(toDos.todoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.todoList);
  }
}

