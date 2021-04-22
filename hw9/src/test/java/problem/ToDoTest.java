package problem;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ToDoTest {

  ToDo toDo;
  ToDo same;
  ToDo nullType;
  String diffType;
  ToDo diffText;
  ToDo diffCompleted;
  ToDo diffDue;
  ToDo diffPriority;
  ToDo diffCategory;


  @Before
  public void setUp() throws Exception {
    String s = "02/28/2020";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date date = sdf.parse(s);
    ToDo.Builder builder = new ToDo.Builder("Mail passport");

    toDo = builder.setID(2).addDue(date).addPriority(1).setCompleted(false).build();
    same = builder.setID(2).addDue(date).addPriority(1).setCompleted(false).build();
    nullType = null;
    diffType = "toDo";
    ToDo.Builder anotherBuilder = new ToDo.Builder("Mail letter");
    diffText = anotherBuilder.setID(2).addDue(date).addPriority(1).setCompleted(false).build();
    diffCompleted = builder.setID(2).addDue(date).addPriority(1).setCompleted(true).build();

    String time = "03/28/2020";
    SimpleDateFormat diffSdf = new SimpleDateFormat("MM/dd/yyyy");
    Date diffDate = diffSdf.parse(time);
    diffDue = builder.setID(2).addDue(diffDate).addPriority(1).setCompleted(false).build();

    diffPriority = builder.setID(2).addDue(date).addPriority(2).setCompleted(false).build();

    diffCategory = builder.setID(2).addDue(date).addPriority(1).setCompleted(false)
        .addCategory("home").build();
  }

  @Test
  public void setCompleted() {
    toDo.setCompleted(true);
    assertTrue(toDo.getCompleted());
  }

  @Test
  public void testEquals() {
    assertTrue(toDo.equals(toDo));
    assertTrue(toDo.equals(same));
    assertFalse(toDo.equals(diffCategory));
    assertFalse(toDo.equals(diffCompleted));
    assertFalse(toDo.equals(diffDue));
    assertFalse(toDo.equals(diffPriority));
    assertFalse(toDo.equals(diffText));
    assertFalse(toDo.equals(diffType));
    assertFalse(toDo.equals(nullType));
  }

  @Test
  public void testToString() {
    assertEquals("ToDo{ID='2', text='Mail passport', completed=false, " +
        "due=Fri Feb 28 00:00:00 CST 2020, priority=1, category='null'}", toDo.toString());
  }
}