package nonprofits;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CSVProcessorTest {

  CSVProcessor p;
  String headers;
  String aRow;
  String anotherRow;

  @Before
  public void setUp() throws Exception {
    p = new CSVProcessor();
    headers = "\"company_name\",\"address\",\"city\",\"county\",\"state\"";

    aRow = "\"Bolton, Wilbur Esq\",\"69734 E Carrillo St\",\"Mc Minnville\",\"Warren\",\"TN\"";
    anotherRow =  "\"Ledecky, David Esq\",\"1 Central Ave\",\"Stevens Point\",\"Portage\",\"WI\"";
  }

  @Test
  public void getHeaders() {
    p.addHeader(headers);
    List<String> processedHeaders = new ArrayList<>();
    processedHeaders.add("company_name");
    processedHeaders.add("address");
    processedHeaders.add("city");
    processedHeaders.add(("county"));
    processedHeaders.add("state");
    assertEquals(processedHeaders, p.getHeaders());
  }

  @Test
  public void getInfo() {
    p.addInfo(aRow);
    p.addInfo(anotherRow);

    List<String> aProcessedRow = new ArrayList<>();
    aProcessedRow.add("Bolton, Wilbur Esq");
    aProcessedRow.add("69734 E Carrillo St");
    aProcessedRow.add("Mc Minnville");
    aProcessedRow.add("Warren");
    aProcessedRow.add("TN");
    List<String> anotherProcessedRow = new ArrayList<>();
    anotherProcessedRow.add("Ledecky, David Esq");
    anotherProcessedRow.add("1 Central Ave");
    anotherProcessedRow.add("Stevens Point");
    anotherProcessedRow.add("Portage");
    anotherProcessedRow.add("WI");

    List<List<String>> test = new ArrayList<>();
    test.add(aProcessedRow);
    test.add(anotherProcessedRow);

    assertEquals(test, p.getInfo());
  }
}