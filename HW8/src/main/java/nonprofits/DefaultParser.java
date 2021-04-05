package nonprofits;

import java.util.ArrayList;
import java.util.List;

public class DefaultParser implements CommandLineParser{

  protected CommandLine cmd;
  protected Options options;
  protected Option currentOption;
  protected String currentToken;
  protected List<Object> expectedOpts;

  @Override
  public CommandLine parse(Options options, String[] arguments) throws ParseException {
    this.options = options;
    currentOption = null;
    expectedOpts = new ArrayList<>(options.getRequiredOptions());

    cmd = new CommandLine();

    if (arguments != null) {
      for (String argument : arguments) {
        this.handleToken(argument);
      }
    }

    this.checkGroup();
    this.checkRequiredArgs();
    this.checkRequiredOptions();

//    if (!cmd.hasOption("letter") && !cmd.hasOption("email")) {
//      throw new MissingOptionException("at least has --email or --letter option");
//    }
    return cmd;
  }

  /**
   * Handle any command line token.
   * @param token the command line token to handle
   * @throws ParseException
   */
  private void handleToken(String token) throws ParseException {
    if (token.startsWith("--")) {
      currentToken = token.substring(2, token.length());
    }
    if (currentOption == null && !token.startsWith("--")) {
      throw new ParseException("option should start with --: " + token);
    } else if (currentOption != null && currentOption.acceptsArg()) {
      this.handleOptionValue(token);
    } else if (token.startsWith("--")) {
      this.handleCommandOption(token);
    } else {
      this.handleUnknownToken(token);
    }

    if (currentOption != null && !currentOption.acceptsArg()) {
      currentOption = null;
    }
  }

  /**
   * Throw a MissingArgumentException if the current option didn't receive argument as expected.
   * @throws MissingArgumentException if the current option didn't receive argument as expected.
   */
  private void checkRequiredArgs() throws MissingArgumentException {
    if (currentOption != null && currentOption.acceptsArg()) {
      throw new MissingArgumentException(currentOption);
    }
  }

  /**
   * Checks whether or not the options in commandline satisfy the needs of OptionGroup.
   * @throws MissingBindingOptionException if a keyOption in one group in the options is provided but its corresponding valueOption is not.
   * @throws MissingOptionException if all keyOptions in one group in the options are not provided.
   */
  private void checkGroup() throws MissingBindingOptionException, MissingOptionException {
    for (Option option : cmd.getOptions()) {
      for (OptionGroup group : options.getOptionGroups()) {
        String optionName = option.getOpt();
        Option bindingOption = group.getValueOption(option);
        if (group.containsKeyOption(optionName) && !cmd.hasOption(bindingOption.getOpt())) {
          throw new MissingBindingOptionException(option, bindingOption);
        }

        if (!this.containsGroupKeyOption(group)){
          throw new MissingOptionException(group);
        }
      }
    }
  }

  private boolean containsGroupKeyOption(OptionGroup group) {
    for (Option keyOption : group.getAllKeyOptions()) {
      if (cmd.getOptions().contains(keyOption)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Throws a MissingOptionException if all of the required options are not present.
   * @throws MissingOptionException if all of the required options are not present.
   */
  private void checkRequiredOptions() throws ParseException {
    if (!expectedOpts.isEmpty()) {
      throw new MissingOptionException(expectedOpts);
    }
  }

  private void updateRequiredOptions(Option option) {
    if (option.isRequired()) {
      expectedOpts.remove(option.getOpt());
    }
  }

  private void handleOptionValue(String token) throws ParseException {
    if (token.startsWith("--")) {
      throw new ParseException("Option value cannot start with --: " + token);
    }
    currentOption.setArgName(token);
    currentOption = null;
  }

  private void handleUnknownToken(String token) throws ParseException {
    if (token.startsWith("-") && token.length() > 1) {
      throw new UnrecognizedOptionException("Unrecognized option: " + token);
    }
  }

  private void handleCommandOption(String token) throws ParseException {
    List<String> matchingOpts = options.getMatchingOptions(token);
    if (matchingOpts.isEmpty()) {
      handleUnknownToken(currentToken);
    } else if (matchingOpts.size() > 1) {
      throw new AmbiguousOptionException(token, matchingOpts);
    } else {
      handleOption(options.getOption(matchingOpts.get(0)));
    }
  }

  private void handleOption(Option option) throws ParseException {
    // check the previous option before handling the next one
    checkRequiredArgs();
    updateRequiredOptions(option);
    cmd.addOption(option);
    if (option.acceptsArg()) {
      currentOption = option;
    } else {
      currentOption = null;
    }
  }
}