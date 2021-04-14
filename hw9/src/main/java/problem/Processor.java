package problem;

public class Processor {

  public Processor() {
  }

  public ToDos process(CommandLine commandLine, ToDos toDos) throws ParseException, java.text.ParseException {

    IProcessor addOptionProcessor = new AddOptionProcessor();
    IProcessor completeOptionProcessor = new CompleteOptionProcessor();
    IProcessor displayProcessor = new DisplayProcessor();


    if (commandLine.hasOption("add-todo")) {
      return addOptionProcessor.process(commandLine, toDos);
    }
    if (commandLine.hasOption("complete-todo")) {
      return completeOptionProcessor.process(commandLine, toDos);
    }
    if (commandLine.hasOption("display")) {
      return displayProcessor.process(commandLine, toDos);
    }

    return toDos;
  }
}
