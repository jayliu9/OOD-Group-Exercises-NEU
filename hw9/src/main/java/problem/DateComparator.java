package problem;

import java.util.Comparator;

public class DateComparator implements Comparator<ToDo> {

  @Override
  public int compare(ToDo o1, ToDo o2) {
    return o1.getDue().compareTo(o2.getDue());
  }
}
