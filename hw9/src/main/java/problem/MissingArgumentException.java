package problem;

public class MissingArgumentException extends ParseException {

  public MissingArgumentException(Option option) {
    super("Missing argument for option: --" + option.getOpt());
  }
}
