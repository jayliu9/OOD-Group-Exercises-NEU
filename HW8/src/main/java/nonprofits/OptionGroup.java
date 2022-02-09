package nonprofits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An OptionGroup class, consisting of a map of keyOptions and corresponding valueOptions.
 * If one keyOption is provided, its corresponding valueOption must be provided.
 */
public class OptionGroup {

  private Map<Option, Option> optionGroup = new HashMap<>();

  /**
   * Adds a pair of options to the the option Group.
   * @param optionA The keyOption.
   * @param optionB The corresponding valueOption.
   */
  public void addOption(Option optionA, Option optionB) {
    this.optionGroup.put(optionA, optionB);
  }

  /**
   * Returns a list that contains all options in the group, include keyOptions and valueOptions.
   * @return a list that contains all options in the group, include keyOptions and valueOptions.
   */
  public List<Option> getAllOptions() {
    List<Option> allOptions = new ArrayList<>();
    for (Map.Entry<Option, Option> entry : optionGroup.entrySet()) {
      allOptions.add(entry.getKey());
      allOptions.add(entry.getValue());
    }
    return allOptions;
  }

  /**
   * Checks whether or not the keyOption set contains the specified option.
   * @param option The specified option.
   * @return true if the keyOption set contains the specified option; false otherwise.
   */
  public boolean containsKeyOption(String option) {
    for (Option opt : this.optionGroup.keySet()) {
      if (opt.getOpt().equals(option)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the corresponding valueOption according to the given keyOption.
   * @param keyOption the given keyOption.
   * @return the corresponding valueOption.
   */
  public Option getValueOption(Option keyOption) {
    return this.optionGroup.get(keyOption);
  }

  /**
   * Returns a set that contains all the keyOptions.
   * @return a set that contains all the keyOptions.
   */
  public Set<Option> getAllKeyOptions() {
    return this.optionGroup.keySet();
  }

  /**
   * Returns a List that contains all the keyOptions' name.
   * @return a List that contains all the keyOptions' name.
   */
  public List<String> getAllKeyOptionsNames() {
    List<String> keyOptionsNames = new ArrayList<>();
    for (Option option : this.optionGroup.keySet()) {
      keyOptionsNames.add(option.getOpt());
    }
    return keyOptionsNames;
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
    if (!(o instanceof OptionGroup)) {
      return false;
    }
    OptionGroup group = (OptionGroup) o;
    return Objects.equals(optionGroup, group.optionGroup);
  }

  /**
   * Gets a hash code value for the object.
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(optionGroup);
  }

  /**
   * Creates a string representation of the OptionGroup.
   * @return a string representation of the OptionGroup.
   */
  @Override
  public String toString() {
    return "OptionGroup{" +
        "optionGroup=" + optionGroup +
        '}';
  }
}
