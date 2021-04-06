package nonprofits;

import java.util.Objects;

public class Option {

  private String opt;
  private String value;
  private boolean required;
  private String description;
  private boolean hasArg;

  /**
   * Constructor for an Option class.
   *
   * @param opt         The name of the option.
   * @param hasArg      Specifies whether the Option takes an argument or not.
   * @param required    Specifies whether this option is required to be present.
   * @param description The description of the option.
   */
  public Option(String opt, boolean hasArg, boolean required, String description) {
    this.opt = opt;
    this.hasArg = hasArg;
    this.required = required;
    this.description = description;
  }

  /**
   * Gets the name of this Option.
   *
   * @return the name of this Option.
   */
  public String getOpt() {
    return this.opt;
  }

  /**
   * Gets the display name for the argument value.
   *
   * @return the display name for the argument value.
   */
  public String getArgName() {
    return this.value;
  }

  public String getDescription() {
    return this.description;
  }

  /**
   * Queries to see if this Option is mandatory.
   *
   * @return boolean flag indicating whether this Option is mandatory.
   */
  public boolean isRequired() {
    return this.required;
  }

  /**
   * Sets whether this Option is mandatory.
   *
   * @param required specifies whether this Option is mandatory.
   */
  public void setRequired(boolean required) {
    this.required = required;
  }

  /**
   * Set the value to this Option. If the option cannot accept argument,then add the value. 
   * Otherwise, throw a runtime exception.
   * @param value The value to be set to this Option
   * @throws ParseException if Option can't accept argument, throw Exception
   */
  public void setArgName(String value) throws ParseException {
    if (!acceptsArg()) {
      throw new ParseException("Cannot add value: " + value);
    }
    this.value = value;
  }

  /**
   * Tells if the option can accept an argument.
   * @return true if the option can accept an argument; false otherwise.
   */
  public boolean acceptsArg() {
    return this.hasArg;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Option)) {
      return false;
    }
    Option option = (Option) o;
    return required == option.required && hasArg == option.hasArg && Objects
        .equals(opt, option.opt) && Objects.equals(value, option.value)
        && Objects.equals(description, option.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(opt, value, required, description, hasArg);
  }

  @Override
  public String toString() {
    return "Option{" +
        "opt='" + opt + '\'' +
        ", value='" + value + '\'' +
        ", required=" + required +
        ", description='" + description + '\'' +
        ", hasArg=" + hasArg +
        '}';
  }
}
