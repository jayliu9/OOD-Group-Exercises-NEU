package problem;

public class MissingArgumentException extends ParseException {

  public MissingArgumentException(String message) {
    super(message);
  }

  public MissingArgumentException(Option option) {
    this("Missing argument for option: --" + option.getOpt());
  }
}
