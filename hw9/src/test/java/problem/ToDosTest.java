package problem;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ToDosTest {
    ToDos toDos;
    String toDo1 = "\"2\",\"Mail passport\",\"false\",\"02/28/2020\",\"1\",\"?\"";

    @Before
    public void setUp() throws Exception {
        toDos = new ToDos();
        toDos.readTodo(toDo1);
    }

    @Test
    public void getTodoList() throws ParseException {
        String s = "02/28/2020";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = sdf.parse(s);
        ToDo.Builder builder = new ToDo.Builder("Mail passport");
        ToDo toDo = builder.setID(2).addDue(date).addPriority(1).setCompleted(false).build();

        ToDos newList = new ToDos();
        newList.addTodo(toDo);

        assertEquals(newList, toDos);
    }

    @Test
    public void generateMsg() {
        String msg = "\"id\", \"text\", \"completed\", \"due\", \"priority\", \"category\"" + System.lineSeparator()
                + "\"2\",\"Mail passport\",\"false\",\"02/28/2020\",\"1\",\"?\"" + System.lineSeparator();
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
}