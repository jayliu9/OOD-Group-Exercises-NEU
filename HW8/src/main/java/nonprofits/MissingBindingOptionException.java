package nonprofits;

public class MissingBindingOptionException extends ParseException {

  private Option givenOption;
  private Option missingOption;

  public MissingBindingOptionException(String message)
  {
    super(message);
  }

  public MissingBindingOptionException(Option givenOption, Option missingOption) {
    this(createMessage(givenOption, missingOption));
    this.givenOption = givenOption;
    this.missingOption = missingOption;
  }

  private static String createMessage(Option givenOption, Option missingOption) {
    StringBuilder buf = new StringBuilder();
    buf.append("Error! --");
    buf.append(givenOption.getOpt());
    buf.append(" provided but no --");
    buf.append(missingOption.getOpt());
    buf.append(" was given.");

    return buf.toString();
  }
}

