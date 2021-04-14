package problem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ToDos {

  private List<ToDo> todoList;

  public ToDos() {
    this.todoList = new ArrayList<>();
  }

  public List<ToDo> getTodoList() {
    return this.todoList;
  }


  public void add(ToDo todo){
    this.todoList.add(todo);
  }


  /**
   * The user set the completed status of an existing To\do to true.
   * @param toDo
   * @throws TodoNotFoundException
   */
  public void completeTodo(ToDo toDo) throws TodoNotFoundException {
    if (!this.todoList.contains(toDo)) {
      throw new TodoNotFoundException(toDo + " is not existing!");
    }
    toDo.setCompleted(true);
  }


  /**
   * Display Todos by default.
   * @return
   */
  public List<ToDo> displayTodosByDefault() {
    return this.todoList;
  }

  /**
   * Filter the list to only include incomplete Todos;
   */
  public List<ToDo> displayIncompleteTodos() {
    List<ToDo> incompleteTodos = new ArrayList<>();
    for (ToDo toDo : this.todoList) {
      if (!toDo.isCompleted()) {
        incompleteTodos.add(toDo);
      }
    }
    return incompleteTodos;
  }

  /**
   * Filter the list to only include Todos with a particular category;
   * @param category
   * @return
   */
  public List<ToDo> displayTodosByCategory(String category) {
    List<ToDo> oneCategoryTodos = new ArrayList<>();
    for (ToDo toDo : this.todoList) {
      if (!toDo.getCategory().equals(category)) {
        oneCategoryTodos.add(toDo);
      }
    }
    return oneCategoryTodos;
  }

  /**
   * Sort the Todos by date (ascending);
   */
  public List<ToDo> displayTodosByDate() {
    List<ToDo> oriTodos = this.todoList;
    Collections.sort(oriTodos, new DateComparator());
    return oriTodos;
  }

  /**
   * Sort the Todos by priority (ascending);
   */
  public List<ToDo> displayTodosByPriority() {
    List<ToDo> oriTodos = this.todoList;
    Collections.sort(oriTodos, new PriorityComparator());
    return oriTodos;
  }

}
