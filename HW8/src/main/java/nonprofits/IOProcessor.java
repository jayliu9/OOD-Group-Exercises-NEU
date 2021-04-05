package nonprofits;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOProcessor {

  private static final String TEMPLATE_MARK = "template";
  private static final String OUTPUT_MARK = "output-dir";
  private static final String INFO_FILE_MARK = "csv-file";

  private static final String CLASS_RE = "(\\w+)-";

  private List<Option> templateOptions;
  private String outputPath;
  private String infoPath;

  public IOProcessor(CommandLine cmd) {
    this.templateOptions = cmd.findOptions(TEMPLATE_MARK);
    this.outputPath = cmd.getOptionValue(OUTPUT_MARK);
    this.infoPath = cmd.getOptionValue(INFO_FILE_MARK);
  }

  /**
   * Reads the template from the given path
   * @param templatePath The path to template file
   * @return The template, represented as a String
   */
  private String readTemplate(String templatePath) {
    String msg = "";

    try (BufferedReader inputFile = new BufferedReader(new FileReader(templatePath))) {
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
    return msg.trim();
  }

  /**
   * Reads the information from the .csv file in the given path.
   * @param csvPath The .csv file path
   * @return A CSVProcessor that stores the information.
   */
  private CSVProcessor readCSV(String csvPath) {
    CSVProcessor processor = new CSVProcessor();

    try (BufferedReader inputFile = new BufferedReader(new FileReader(csvPath))) {
      String line;

      if ((line = inputFile.readLine()) != null) {
        processor.addHeader(line);
      }
      while ((line = inputFile.readLine()) != null) {
        processor.addInfo(line);
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
    return processor;
  }

  /**
   * Outputs the given message to a file in the given path.
   * @param msg The message to output
   * @param file The file object to write the given message
   */
  private void write(String msg, File file) {

    try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(file))) {
      outputFile.write(msg);
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

  public void generalFiles() {
    //process csv file.
    CSVProcessor csvInfo = this.readCSV(this.infoPath);
    // get headers and rows
    List<String> headers = csvInfo.getHeaders();
    List<List<String>> rows = csvInfo.getInfo();

    this.createFolder(this.outputPath);

    for (Option templateOpt : this.templateOptions) {
      String content = this.readTemplate(templateOpt.getArgName());
      Template template = new Template(content);

      for (List<String> eachRow : rows) {
        String modifiedMsg = template.replaceHolders(headers, eachRow);
        String newFile = this.generateFileName(templateOpt, headers, eachRow);
        File output = new File(this.outputPath, newFile);
        write(modifiedMsg, output);
      }
    }
  }

  private String generateFileName(Option templateOpt, List<String> headers, List<String> eachRow) {
    int firstNameIndex = headers.indexOf("first_name");
    int lastNameIndex = headers.indexOf("last_name");

    String firstName = eachRow.get(firstNameIndex);
    String lastName = eachRow.get(lastNameIndex);

    String optName = templateOpt.getOpt();
    Pattern p = Pattern.compile(CLASS_RE);
    Matcher m = p.matcher(optName);
    if (m.find()) {
      return firstName + "_" + lastName + "_" + m.group(1) + ".txt";
    }
    return null;
  }

  private void createFolder(String path) {
    File file = new File(path);
    if (!file.exists())
      file.mkdir();
  }
}
