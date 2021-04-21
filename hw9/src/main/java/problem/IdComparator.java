package problem;

import java.util.Comparator;

public class IdComparator implements  Comparator<ToDo> {

  @Override
  public int compare(ToDo o1, ToDo o2) {
    return o1.getID() - o2.getID();
  }
}