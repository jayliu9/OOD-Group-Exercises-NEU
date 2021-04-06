package nonprofits;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TemplateTest {

  Template template;
  Template same;
  Template nullType;
  String diffType;
  Template diffContent;

  @Before
  public void setUp() throws Exception {
    String content = "Hello, [[first_name]] [[last_name]]!" + System.lineSeparator() + "Hello, [[first_name]] [[last_name]]!";
    template = new Template(content);

    same = new Template(content);
    nullType = null;
    diffType = "Template";
    diffContent = new Template("Good morning, [[first_name]] [[last_name]]!\nGood morning, [[first_name]] [[last_name]]!");
  }

  @Test
  public void getContent() {
    String testContent = "Hello, [[first_name]] [[last_name]]!" + System.lineSeparator() + "Hello, [[first_name]] [[last_name]]!";

    assertEquals(testContent, template.getContent());
  }

  @Test
  public void replaceHolders() {
    List<String> headers = new ArrayList<>();
    headers.add("first_name");
    headers.add("last_name");
    List<String> row = new ArrayList<>();
    row.add("Harry");
    row.add("Potter");

    String test = "Hello, Harry Potter!" + System.lineSeparator() + "Hello, Harry Potter!";

    assertEquals(test, template.replaceHolders(headers, row));
  }

  @Test
  public void testEquals() {

    assertTrue(template.equals(template));
    assertTrue(template.equals(same));
    assertFalse(template.equals(nullType));
    assertFalse(template.equals(diffType));
    assertFalse(template.equals(diffContent));
  }

  @Test
  public void testHashCode() {
    assertTrue(template.hashCode() == same.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("Template{content='Hello, [[first_name]] [[last_name]]!" +
        System.lineSeparator() + "Hello, [[first_name]] [[last_name]]!}", template.toString());
  }
}