package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OptionSeries {

  private Map<Option, List<Option>> optionSeries = new HashMap<>();

  /**
   * Adds a pair of options to the the option Series.
   * @param option The keyOption.
   * @param optionalOptions The corresponding list of valueOptions.
   */
  public void addOption(Option option, List<Option> optionalOptions) {
    this.optionSeries.put(option, optionalOptions);
  }

  /**
   * Returns a list that contains all options in the optionSeries, include keyOptions and list of valueOptions.
   * @return a list that contains all options in the optionSeries, include keyOptions and list of valueOptions.
   */
  public List<Option> getAllOptions() {
    List<Option> allOptions = new ArrayList<>();
    for (Map.Entry<Option, List<Option>> entry : this.optionSeries.entrySet()) {
      allOptions.add(entry.getKey());
      allOptions.addAll(entry.getValue());
    }
    return allOptions;
  }

  /**
   * Gets the corresponding keyOption according to the given valueOption.
   * @param valueOption the given valueOption.
   * @return the corresponding valueOption.
   */
  public Option getKeyOption(Option valueOption) {
    for (Option keyOption : this.optionSeries.keySet()) {
      if (this.optionSeries.get(keyOption).contains(valueOption)) {
        return keyOption;
      }
    }
    return null;
  }

  /**
   * Checks whether or not the valueOption set contains the specified option.
   * @param valueOption The specified valueOption.
   * @return true if the valueOption set contains the specified option; false otherwise.
   */
  public boolean containsValueOption(Option valueOption) {
    for (List<Option> valueOptions : this.optionSeries.values()) {
      if (valueOptions.contains(valueOption)) {
        return true;
      }
    }
    return false;
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
    if (!(o instanceof OptionSeries)) {
      return false;
    }
    OptionSeries that = (OptionSeries) o;
    return Objects.equals(optionSeries, that.optionSeries);
  }

  /**
   * Gets a hash code value for the object.
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(optionSeries);
  }

  /**
   * Creates a string representation of the OptionSeries.
   * @return a string representation of the OptionSeries.
   */
  @Override
  public String toString() {
    return "OptionSeries{" +
        "optionSeries=" + optionSeries +
        '}';
  }
}
