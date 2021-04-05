package nonprofits;

public class UnrecognizedOptionException extends ParseException {

  /** The  unrecognized option */
  private String option;

  /**
   * Construct a new <code>UnrecognizedArgumentException</code>
   * with the specified detail message.
   *
   * @param message the detail message
   */
  public UnrecognizedOptionException(String message)
  {
    super(message);
  }

  /**
   * Construct a new <code>UnrecognizedArgumentException</code>
   * with the specified option and detail message.
   *
   * @param message the detail message
   * @param option  the unrecognized option
   */
  public UnrecognizedOptionException(String message, String option) {
    this(message);
    this.option = option;
  }

  /**
   * Returns the unrecognized option.
   *
   * @return the related option
   */
  public String getOption()
  {
    return option;
  }
}

