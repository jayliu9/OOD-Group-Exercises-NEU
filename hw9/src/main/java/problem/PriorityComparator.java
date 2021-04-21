package problem;

import java.util.Comparator;

public class PriorityComparator implements Comparator<ToDo> {

  @Override
  public int compare(ToDo o1, ToDo o2) {
    if (o1.getPriority() == 4) return 1;
    if (o2.getPriority() == 4) return -1;
    return o1.getPriority() - o2.getPriority();
  }
}
