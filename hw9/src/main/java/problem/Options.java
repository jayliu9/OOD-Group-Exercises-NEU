package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An Options class, can contain Option and OptionGroup class.
 */
public class Options {

  private final List<Option> opts = new ArrayList<>();
  private final List<String> requiredOpts = new ArrayList<>();
  private final Map<String, OptionGroup> optionGroups = new HashMap<>();
  private final Map<String, OptionSeries> optionSeries = new HashMap<>();
  private static final int SECOND_CHARACTER = 2;

  /**
   * Gets all the options.
   *
   * @return all the options.
   */
  public List<Option> getOptions() {
    return this.opts;
  }

  /**
   * Adds the specified option group. When the group is added, its all options including keyOptions
   * and valueOptions are automatically added to the opts List.
   *
   * @param group the OptionGroup that is to be added.
   */
  public void addOptionGroup(OptionGroup group) {
    for (Option option : group.getAllOptions()) {
      this.addOption(option);
      this.optionGroups.put(option.getOpt(), group);
    }
  }

  /**
   * Adds the specified option group. When the group is added, its all options including keyOptions
   * and valueOptions are automatically added to the opts List.
   *
   * @param series the OptionSeries that is to be added.
   */
  public void addOptionSeries(OptionSeries series) {
    for (Option option : series.getAllOptions()) {
      this.addOption(option);
      this.optionSeries.put(option.getOpt(), series);
    }
  }

  /**
   * Adds an option instance.
   *
   * @param opt the option that is to be added.
   */
  public void addOption(Option opt) {
    String optName = opt.getOpt();

    if (opt.isRequired()) {
      if (requiredOpts.contains(optName)) {
        requiredOpts.remove(requiredOpts.indexOf(optName));
      }
      requiredOpts.add(optName);
    }
    // add opt when its opt name is unique.
    if (!this.isDuplicated(optName)) {
      opts.add(opt);
    }
  }

  /**
   * check if the optName has already existed
   *
   * @param optName option name
   * @return true if there it is duplicated false otherwise
   */
  private boolean isDuplicated(String optName) {
    for (Option option : this.opts) {
      if (option.getOpt().equals(optName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the required options.
   *
   * @return the required options
   */
  public List<String> getRequiredOptions() {
    return this.requiredOpts;
  }

  /**
   * Retrieve the Option matching the name specified.
   *
   * @param opt the name of the Option
   * @return the option represented by opt
   */
  public Option getOption(String opt) {
    for (Option option : this.opts) {
      if (option.getOpt().equals(opt)) {
        return option;
      }
    }
    return null;
  }

  /**
   * Lists the OptionGroups that are members of this Options instance.
   *
   * @return a Collection of OptionGroup instances.
   */
  public Set<OptionGroup> getOptionGroups() {
    return new HashSet<>(optionGroups.values());
  }

/**
   * Lists the OptionSeries that are members of this Options instance.
   *
   * @return a Collection of OptionSeries instances.
   */
  public Set<OptionSeries> getOptionSeries() {
    return new HashSet<>(optionSeries.values());
  }

  /**
   * Returns the option with the name specified.
   *
   * @param opt the name of the option
   * @return the option matching the name specified, or null if none matches.
   */
  public Option getMatchingOption(String opt) {
    String trimmedOpt = opt.substring(SECOND_CHARACTER, opt.length());
    for (Option option : this.getOptions()) {
      if (trimmedOpt.equals(option.getOpt())) {
        return option;
      }
    }
    return null;
  }

  /**
   * Checks if two objects are equal
   * @param o the object to compare this to
   * @return true if these two objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Options)) {
      return false;
    }
    Options options = (Options) o;
    return Objects.equals(opts, options.opts) && Objects
        .equals(requiredOpts, options.requiredOpts) && Objects
        .equals(optionGroups, options.optionGroups) && Objects
        .equals(optionSeries, options.optionSeries);
  }

  /**
   * Gets a hash code value for the object.
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(opts, requiredOpts, optionGroups, optionSeries);
  }

  /**
   * Creates a string representation of the Options.
   * @return a string representation of the Options.
   */
  @Override
  public String toString() {
    return "Options{" +
        "opts=" + opts +
        ", requiredOpts=" + requiredOpts +
        ", optionGroups=" + optionGroups +
        ", optionSeries=" + optionSeries +
        '}';
  }
}