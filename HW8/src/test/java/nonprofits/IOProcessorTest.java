package nonprofits;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class IOProcessorTest {

  CommandLine cmd;
  IOProcessor io;

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Before
  public void setUp() throws Exception {
    cmd = new CommandLine();
    Option op2 = new Option("letter", false, false, "Generate letter messages.");
    Option op22 = new Option("letter-template", true, false, "letter template.");
    Option op3 = new Option("output-dir", true, true, "set output dir.");
    Option op4 = new Option("csv-file", true, true, "set csv file.");
    op22.setArgName(tempFolder.getRoot().getPath() + File.separator + "test_text.txt");
    op3.setArgName(tempFolder.getRoot().getPath() + File.separator + "folder");
    op4.setArgName(tempFolder.getRoot().getPath() + File.separator + "testInfo.csv");
    cmd.addOption(op2);
    cmd.addOption(op22);
    cmd.addOption(op3);
    cmd.addOption(op4);

    io = new IOProcessor(cmd);
    String temp = "Dear [[first_name]] [[last_name]]" + System.lineSeparator() + "Good Morning!";
    String csv = "\"first_name\",\"last_name\"" + System.lineSeparator() + "\"Lenna\",\"Paprocki\"";
    File tempFile = new File(tempFolder.getRoot().getPath(), "test_text.txt");
    File csvFile = new File(tempFolder.getRoot().getPath(), "testInfo.csv");
    try (BufferedWriter outputTempFile = new BufferedWriter(new FileWriter(tempFile));
        BufferedWriter outputCsvFile = new BufferedWriter(new FileWriter(csvFile))) {
      outputTempFile.write(temp);
      outputCsvFile.write(csv);
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

  @Test
  public void testGenerateFiles() {
    io.generateFiles();
    String msg = "";
    String fileName = "Lenna_Paprocki_letter.txt";
    File read = new File(tempFolder.getRoot().getPath() + File.separator + "folder", fileName);
    try (BufferedReader inputFile = new BufferedReader(new FileReader(read))) {
      String line;
      while ((line = inputFile.readLine()) != null) {
        msg += line + System.lineSeparator();
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }

    msg = msg.trim();
    assertEquals("Dear Lenna Paprocki" + System.lineSeparator() + "Good Morning!", msg);
  }

  @Test
  public void CreateFilesInExistingFolder() throws ParseException {
    Option newOption = cmd.getOptions().get(2);
    newOption.setArgName(tempFolder.getRoot().getPath());
    IOProcessor newProcessor = new IOProcessor(cmd);
    newProcessor.generateFiles();
  }
}
