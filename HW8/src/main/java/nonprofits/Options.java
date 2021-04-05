package nonprofits;

import java.util.ArrayList;
import java.util.Arrays;
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
  private final List<Object> requiredOpts = new ArrayList<>();
  private final Map<String, OptionGroup> optionGroups = new HashMap<>();

  public List<Option> getOptions() {
    return this.opts;
  }

  /**
   * Adds the specified option group.
   * When the group is added, its all options including keyOptions and valueOptions are automatically added to the opts List.
   *
   * @param group the OptionGroup that is to be added.
   */
  public void addOptionGroup(OptionGroup group) {
    for (Option option : group.getAllOptions()) {
      this.addOption(option);
      optionGroups.put(option.getOpt(), group);
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
    opts.add(opt);
  }

  /**
   * Returns the required options.
   *
   * @return the required options
   */
  public List<Object> getRequiredOptions() {
    return requiredOpts;
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
   * Returns the options starting with the name specified.
   *
   * @param opt the partial name of the option
   * @return the options matching the partial name specified, or an empty list if none matches
   */
  public List<String> getMatchingOptions(String opt) {
    opt = opt.substring(2, opt.length());
    List<String> matchingOpts = new ArrayList<>();
    for (Option option : this.opts) {
      if (opt.equals(option.getOpt()))
        return Arrays.asList(option.getOpt());
    }
    return matchingOpts;
  }

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
        .equals(optionGroups, options.optionGroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(opts, requiredOpts, optionGroups);
  }

  @Override
  public String toString() {
    return "Options{" +
        "opts=" + opts +
        ", requiredOpts=" + requiredOpts +
        ", optionGroups=" + optionGroups +
        '}';
  }
}