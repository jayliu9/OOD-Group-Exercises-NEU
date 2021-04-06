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

/**
 * Represents the processor which takes parsed command line arguments (and options) and outputs the
 * appropriate text files.
 */
public class IOProcessor {

  private static final String TEMPLATE_MARK = "template";
  private static final String OUTPUT_MARK = "output-dir";
  private static final String INFO_FILE_MARK = "csv-file";

  private static final String CLASS_RE = "(\\w+)-";

  private List<Option> templateOptions;
  private String outputPath;
  private String infoPath;

  /**
   * Constructor for the IOProcessor class.
   * @param cmd The commandLine object, which stores the parsed options.
   */
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

  /**
   * Generates the text files per row in the CSV file to the given folder, with all placeholders
   * replaced with the the appropriate value.
   */
  public void generalFiles() {
    //process csv file.
    CSVProcessor csvInfo = this.readCSV(this.infoPath);
    // get headers and rows
    List<String> headers = csvInfo.getHeaders();
    List<List<String>> rows = csvInfo.getInfo();

    this.createFolder(this.outputPath); // creates folder

    // search template options in commandline
    for (Option templateOpt : this.templateOptions) {
      String content = this.readTemplate(templateOpt.getArgName()); // get the template path from the argument of the option
      Template template = new Template(content); // create template object, ready to replace its placeholders.

      // replaces placeholders with the row in CSV file and output the text file in given folder
      for (List<String> eachRow : rows) {
        String modifiedMsg = template.replaceHolders(headers, eachRow); // replaces placeholders
        String newFile = this.generateFileName(templateOpt, headers, eachRow); // generate file name
        File output = new File(this.outputPath, newFile);  // File object to store folder path and file name
        write(modifiedMsg, output);  // write text file to the given folder
      }
    }
  }

  /**
   * Generates the name of the text file according to a support's first name, last name and
   * the type of the template.
   * @param templateOpt The template option
   * @param headers The headers in the CSV file
   * @param eachRow A single row in the CSV file
   * @return The name of the text file
   */
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

  /**
   * Creates the folder in the given path
   * @param path The path to create the folder
   */
  private void createFolder(String path) {
    File file = new File(path);
    if (!file.exists())
      file.mkdir();
  }
}
