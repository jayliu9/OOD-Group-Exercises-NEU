package problem;

import java.util.Comparator;

public class DateComparator implements Comparator<ToDo> {

  /**
   * Compares its two arguments for order.  Returns a negative integer,
   * zero, or a positive integer as the first argument is less than, equal
   * to, or greater than the second.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return a negative integer, zero, or a positive integer as the
   * first argument is less than, equal to, or greater than the
   * second.
   * @throws NullPointerException if an argument is null and this
   *                              comparator does not permit null arguments
   * @throws ClassCastException   if the arguments' types prevent them from
   *                              being compared by this comparator.
   */
  @Override
  public int compare(ToDo o1, ToDo o2) {
    if (o1.getDue() == null) return 1;
    if (o2.getDue() == null) return -1;
    return o1.getDue().compareTo(o2.getDue());
  }
}
