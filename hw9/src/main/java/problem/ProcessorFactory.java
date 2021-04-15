package problem;

/**
 * Processor factory to instantiate the subclass of IProcessor according to the keyword in option name
 */
public class ProcessorFactory {

    private static final String ADD = "add-todo";
    private static final String COMPLETE = "complete-todo";
    private static final String DISPLAY = "display";

    public static IProcessor makeProcessor(String optName) {
        switch (optName) {
            case ADD:
                return new AddOptionProcessor();
            case COMPLETE:
                return new CompleteOptionProcessor();
            case DISPLAY:
                return new DisplayProcessor();
        }
        return new DefaultProcessor();
    }
}
