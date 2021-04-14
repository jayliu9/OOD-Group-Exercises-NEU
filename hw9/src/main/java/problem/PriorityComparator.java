package problem;

import java.util.Comparator;

public class PriorityComparator implements Comparator<ToDo> {

  @Override
  public int compare(ToDo o1, ToDo o2) {
    return o1.getPriority() - o2.getPriority();
  }
}
