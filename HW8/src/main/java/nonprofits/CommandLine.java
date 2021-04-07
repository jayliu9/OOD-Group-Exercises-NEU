package nonprofits;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents list of arguments parsed against a Options descriptor. It allows querying of a boolean
 * hasOption(String opt), in addition to retrieving the getOptionArg(String opt) for options
 * requiring arguments.
 * <p>
 * Additionally, any left-over or unrecognized arguments, are available for further processing.
 */
public class CommandLine {

  private List<Option> options = new ArrayList<>();

  public CommandLine() {
  }

  /**
   * Queries to see if an option has been set.
   *
   * @param opt The name of the option.
   * @return true if set, false if not
   */
  public boolean hasOption(String opt) {
    return options.contains(resolveOption(opt));
  }


  /**
   * Retrieves the option object given the long or short option as a String
   *
   * @param opt The name of the option.
   * @return Canonicalized option
   */
  private Option resolveOption(String opt) {
    for (Option option : this.options) {
      if (opt.equals(option.getOpt())) {
        return option;
      }
    }
    return null;
  }

  /**
   * Adds an Option to the option list in the commandline.
   *
   * @param opt the Option to be added.
   */
  public void addOption(Option opt) {
    this.options.add(opt);
  }

  /**
   * Gets a list of all options in the commandline.
   *
   * @return a list of all options in the commandline.
   */
  public List<Option> getOptions() {
    return this.options;
  }

  /**
   * Gets the value of the option according to its option name.
   *
   * @param opt the name of the option
   * @return the value of the option according to its option name.
   */
  public String getOptionValue(String opt) {
    for (Option option : this.options) {
      if (opt.equals(option.getOpt())) {
        return option.getArgName();
      }
    }
    return null;
  }

  /**
   * Gets a list of all options which contains the given keyword.
   *
   * @param keyword the given keyword
   * @return a list of all options which contains the given keyword.
   */
  public List<Option> findOptions(String keyword) {
    List<Option> optList = new ArrayList<>();
    for (Option opt : this.options) {
      String optName = opt.getOpt();
      if (optName.contains(keyword)) {
        optList.add(opt);
      }
    }
    return optList;
  }

  /**
   * Checks if two objects are equal
   *
   * @param o the object to compare this to
   * @return true if these two objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CommandLine)) {
      return false;
    }
    CommandLine that = (CommandLine) o;
    return Objects.equals(options, that.options);
  }

  /**
   * Gets a hash code value for the object.
   *
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(options);
  }

  /**
   * Creates a string representation of the CommandLine.
   *
   * @return a string representation of the CommandLine.
   */
  @Override
  public String toString() {
    return "CommandLine{" +
        "options=" + options +
        '}';
  }
}