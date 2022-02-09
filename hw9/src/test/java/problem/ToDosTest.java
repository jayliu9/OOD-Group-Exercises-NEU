package problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class ToDosTest {

  ToDos toDos;
  String toDo1;
  ToDos nullType;
  ToDos diffList;
  ToDos same;

  @Before
  public void setUp() throws Exception {
    toDo1 = "\"2\",\"Mail passport\",\"false\",\"02/28/2020\",\"1\",\"?\"";
    toDos = new ToDos();
    toDos.readTodo(toDo1);

    same = new ToDos();
    same.readTodo(toDo1);
    nullType = null;
    String diff = "\"3\",\"Study for finals\",\"false\",\"03/22/2020\",\"2\",\"school\"";
    diffList = new ToDos();
    diffList.readTodo(diff);
  }

  @Test
  public void getTodoList() throws ParseException {
    String s = "02/28/2020";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date date = sdf.parse(s);
    ToDo.Builder builder = new ToDo.Builder("Mail passport");
    ToDo toDo = builder.setID(2).addDue(date).addPriority(1).setCompleted(false).build();

    ToDos newToDos = new ToDos();
    newToDos.addTodo(toDo);

    assertEquals(newToDos.getTodoList(), toDos.getTodoList());
  }

  @Test
  public void generateMsg() throws ParseException {
    String toDo2 = "\"5\",\"Buy yarn for blanket, stuffed toy\",\"false\",\"?\",\"1\",\"home\"";
    String toDo3 = "\"4\",\"Clean the house\",\"false\",\"03/22/2020\",\"?\",\"home\"";
    toDos.readTodo(toDo2);
    toDos.readTodo(toDo3);
    String msg = "\"id\", \"text\", \"completed\", \"due\", \"priority\", \"category\""
        + System.lineSeparator()
        + "\"2\",\"Mail passport\",\"false\",\"02/28/2020\",\"1\",\"?\""
        + System.lineSeparator()
        + "\"5\",\"Buy yarn for blanket, stuffed toy\",\"false\",\"?\",\"1\",\"home\""
        + System.lineSeparator()
        + "\"4\",\"Clean the house\",\"false\",\"03/22/2020\",\"?\",\"home\""
        + System.lineSeparator();
    assertEquals(msg, toDos.generateMsg());
  }

  @Test
  public void findToDo() throws ParseException, TodoNotFoundException {
    String s = "02/28/2020";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date date = sdf.parse(s);
    ToDo.Builder builder = new ToDo.Builder("Mail passport");
    ToDo toDo = builder.setID(2).addDue(date).addPriority(1).setCompleted(false).build();

    assertEquals(toDo, toDos.findToDo(2));
  }

  @Test(expected = TodoNotFoundException.class)
  public void notFound() throws TodoNotFoundException {
    toDos.findToDo(1);
  }

  @Test
  public void testEquals() {
    assertTrue(toDos.equals(toDos));
    assertTrue(toDos.equals(same));
    assertFalse(toDos.equals(diffList));
    assertFalse(toDos.equals(toDo1));
    assertFalse(toDos.equals(nullType));
  }

  @Test
  public void testHashCode() {
    assertTrue(toDos.hashCode() == same.hashCode());
  }
}