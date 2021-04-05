package nonprofits;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents list of arguments parsed against a Options descriptor.
 * It allows querying of a boolean hasOption(String opt), in addition to retrieving the getOptionArg(String opt) for options requiring arguments.
 *
 * Additionally, any left-over or unrecognized arguments, are available for further processing.
 */
public class CommandLine {

  private List<Option> options = new ArrayList<>();

  public CommandLine() {
  }

  /**
   * Queries to see if an option has been set.
   * @param opt The name of the option.
   * @return true if set, false if not
   */
  public boolean hasOption(String opt)
  {
    return options.contains(resolveOption(opt));
  }


  /**
   * Retrieves the option object given the long or short option as a String
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

  protected void addOption(Option opt) {
    this.options.add(opt);
  }

  public List<Option> getOptions() {
    return this.options;
  }

  public String getOptionValue(String opt) {
    for (Option option : this.options) {
      if (opt.equals(option.getOpt()))
        return option.getArgName();
    }
    return null;
  }

  public List<Option> findOptions(String keyword) {
    List<Option> optList = new ArrayList<>();
    for (Option opt : this.options) {
      String optName = opt.getOpt();
      if(optName.contains(keyword))
        optList.add(opt);
    }
    return optList;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(options);
  }

  @Override
  public String toString() {
    return "CommandLine{" +
        "options=" + options +
        '}';
  }
}
