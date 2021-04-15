package problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayProcessor implements IProcessor {

  @Override
  public void process(CommandLine commandLine, ToDos toDos) throws ParseException, java.text.ParseException {
  }

  /**
   * Sort the Todos by date (ascending);
   */
  private void displayTodosByDate(List<ToDo> oriTodos) {
    Collections.sort(oriTodos, new DateComparator());
  }

  /**
   * Sort the Todos by priority (ascending);
   */
  private void displayTodosByPriority(List<ToDo> oriTodos) {
    Collections.sort(oriTodos, new PriorityComparator());
  }

  public List<ToDo> displayTodosByCategory(String category, List<ToDo> todoList) {
    List<ToDo> oneCategoryTodos = new ArrayList<>();
    for (ToDo toDo : todoList) {
      if (!toDo.getCategory().equals(category)) {
        oneCategoryTodos.add(toDo);
      }
    }
    return oneCategoryTodos;
  }

  public List<ToDo> displayIncompleteTodos(List<ToDo> todoList) {
    List<ToDo> incompleteTodos = new ArrayList<>();
    for (ToDo toDo : todoList) {
      if (!toDo.isCompleted()) {
        incompleteTodos.add(toDo);
      }
    }
    return incompleteTodos;
  }
}
