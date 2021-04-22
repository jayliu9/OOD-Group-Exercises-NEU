package problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class OptionGroupTest {

  private Option addTodoOption;
  private Option todoTextOption;
  private OptionGroup bindingOptionGroup;
  private OptionGroup exclusiveOptionGroup;
  private OptionGroup nullType;

  @Before
  public void setUp() throws Exception {
    addTodoOption = new Option("add-todo", false, false, "--add-todo Add a new todo. "
        + "If this option is provided, then --todo-text must also be provided.");

    todoTextOption = new Option("todo-text", true, false,
        "--todo-text <description of todo> "
            + "A description of the todo.");

    bindingOptionGroup = new OptionGroup(true);

    exclusiveOptionGroup = new OptionGroup(false);

    nullType = null;
  }

  @Test
  public void isBinding() {
    assertTrue(bindingOptionGroup.isBinding());
    assertFalse(exclusiveOptionGroup.isBinding());
  }

  @Test
  public void addOption() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    assertEquals(todoTextOption, bindingOptionGroup.getValueOption(addTodoOption));
  }

  @Test
  public void getAllOptions() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    List<Option> allOptions = new ArrayList<>();
    allOptions.add(addTodoOption);
    allOptions.add(todoTextOption);
    assertEquals(allOptions, bindingOptionGroup.getAllOptions());
  }

  @Test
  public void containsKeyOption() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    assertTrue(bindingOptionGroup.containsKeyOption("add-todo"));
  }

  @Test
  public void containsValueOption() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    assertTrue(bindingOptionGroup.containsValueOption("todo-text"));
  }

  @Test
  public void getKeyOption() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    assertEquals(addTodoOption, bindingOptionGroup.getKeyOption(todoTextOption));
  }

  @Test
  public void getValueOption() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    assertEquals(todoTextOption, bindingOptionGroup.getValueOption(addTodoOption));
  }

  @Test
  public void testEquals() {
    assertFalse(bindingOptionGroup.equals(exclusiveOptionGroup));

    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    exclusiveOptionGroup.addOption(addTodoOption, todoTextOption);

    assertTrue(bindingOptionGroup.equals(bindingOptionGroup));
    assertFalse(bindingOptionGroup.equals(nullType));
    assertFalse(bindingOptionGroup.equals(""));

    OptionGroup that = new OptionGroup(true);
    that.addOption(addTodoOption, todoTextOption);
    assertTrue(bindingOptionGroup.equals(that));

    assertFalse(bindingOptionGroup.equals(exclusiveOptionGroup));
  }

  @Test
  public void testHashCode() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    OptionGroup that = new OptionGroup(true);
    that.addOption(addTodoOption, todoTextOption);
    assertTrue(bindingOptionGroup.hashCode() == that.hashCode());
  }

  @Test
  public void testToString() {
    bindingOptionGroup.addOption(addTodoOption, todoTextOption);
    String expected = "OptionGroup{"
        + "binding=true, "
        + "optionGroup={"
        + "Option{opt='add-todo', value='null', required=false, description='--add-todo Add a new todo. If this option is provided, then --todo-text must also be provided.', hasArg=false}"
        + "=Option{opt='todo-text', value='null', required=false, description='--todo-text <description of todo> A description of the todo.', hasArg=true}}}";
    assertEquals(expected, bindingOptionGroup.toString());
  }
}