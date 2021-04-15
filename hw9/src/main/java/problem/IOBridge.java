package problem;

/**
 * This class builds the communication between cmd and I/O system
 */
public class IOBridge {
    private static final String CSV_FILE = "csv-file";
    private CommandLine cmd;
    private String csvPath;
    private ToDos todos;

    public IOBridge(CommandLine cmd) {
        this.cmd = cmd;
        this.csvPath = cmd.getOptionValue(CSV_FILE);
        this.readCSV(cmd);
    }


    /**
     * Iterates the command line to process every option, especially "add、 complete、 display“,
     * using Factory pattern to create action processor.
     * @throws ParseException
     * @throws java.text.ParseException
     */
    public void processTodos() throws ParseException, TodoNotFoundException, java.text.ParseException {
        for (Option opt : this.cmd.getOptions()) {
            IProcessor processor = ProcessorFactory.makeProcessor(opt.getOpt());
            processor.process(this.cmd, this.todos);
        }
        this.updateCSV();
    }

    /**
     * Helper function to read csv file -> Todos
     * @param cmd
     */
    private void readCSV(CommandLine cmd) {
        this.todos = Reader.read(this.csvPath);
    }

    /**
     * Updates CSV file after processing
     */
    private void updateCSV() {
        String csv = this.todos.generateMsg();
        Writer.write(csv, this.csvPath);
    }
}


