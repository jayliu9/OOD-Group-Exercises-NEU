package nonprofits;

public class MissingArgumentException extends ParseException {

  private Option option;

  public MissingArgumentException(String message) {
    super(message);
  }

  public MissingArgumentException(Option option) {
    this("Missing argument for option: --" + option.getOpt());
    this.option = option;
  }

  public Option getOption() {
    return option;
  }
}
