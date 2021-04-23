package problem;

public interface CommandLineParser {

  /**
   * Parse the arguments according to the specified options.
   * @param options The specified Options.
   * @param arguments The command line arguments.
   * @return The list of option and value tokens.
   * @throws ParseException if there are any problems encountered while parsing the command line tokens.
   */
  CommandLine parse(Options options, String[] arguments) throws ParseException;
}
