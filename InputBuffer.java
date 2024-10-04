//user input functionality

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputBuffer {

    //variables
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private CommandProcessor processor;

    //constructor
    public InputBuffer(CommandProcessor processor) {
        this.processor = processor;
    }

    //method for getting input form the user
    public void startInputLoop() {
        try {
            while (true) {
                String command = reader.readLine();
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if (command == null) {
                    //Exit code 0 for a graceful exit
                    System.exit(0);
                }

                //collect each command and parse the input based on a REGEX. If valid input pattern, return into an array
                String[] inputArray = CommandParser.parseInputString(command);

                //pass each input command individually from the inputarray to our processing class
                for (String str : inputArray){
                    processor.processInput(str);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}