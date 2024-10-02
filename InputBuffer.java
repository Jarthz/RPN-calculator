import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

//because we want the input buffer to continually run, we're going to have to put the stack inside the input buffer, rather than constantly call a return
//this also means we have to do our calculations 'live'

public class InputBuffer {

    //variables
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Stack stack;
    private Calculator calculator;
    private Saturation saturated;
    private Operator operator;

    //constructor
    public InputBuffer(Stack stack, Calculator calculator, Saturation saturated, Operator operator) {
        this.stack = stack;
        this.calculator = calculator;
        this.saturated = saturated;
        this.operator = operator;
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

                //collect each command and parse the input based on a REGEX below. If valid, return into an array
                String[] inputArray = inputString(command);

                //pass each input command individually from the inputarray to our processing method
                for (String str : inputArray){
                    processInput(str);
                }

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    //method array for handling where the user enteres loads of commands at once

    public String[]inputString(String command) {
        String regex = "(-?\\d+|[-+*/%^]|d|=|#\\s*.*?\\s*#)"; //the patern is whole numbers, operators, 'd', and '='


        //use the pattern matcher classes from the regex util to parse the string and check if the input is valid type
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);


        //create arraylist  to hold valid tokens to operate over

        List<String> validTokens = new ArrayList<>();

        // To track positions between matches
        int lastMatchEnd = 0;

        while (matcher.find()) {
            // Check for invalid tokens between matches
            if (matcher.start() > lastMatchEnd) {
                String invalidPart = command.substring(lastMatchEnd, matcher.start()).trim();
                if (!invalidPart.isEmpty()) {
                    System.out.println("Unrecognised operator or operand \"" + invalidPart + "\".");
                }
            }
            // Add valid tokens
            String match = matcher.group(1);
            validTokens.add(match);
            lastMatchEnd = matcher.end();
        }

        // Check for any remaining invalid tokens after the last match
        if (lastMatchEnd < command.length()) {
            String invalidPart = command.substring(lastMatchEnd).trim();
            if (!invalidPart.isEmpty()) {
                System.out.println("Unrecognised operator or operand \"" + invalidPart + "\".");
            }
        }

        return validTokens.toArray(new String[0]);

    }




    //method for processing the input
    private void processInput(String input) {

        //check if input is operand and/or saturated. Return null if invalid
        Integer value = saturated.isSaturatedOrOperand(input);

        //push all valid integers/saturated values straight to stack
        if(value != null) {
            stack.push(String.valueOf(value));
        }


        // check now if it's an operator we've defined int he calculator class
        if(operator.isOperator(input)) {

            //error message if you don't have enough operands in the stack to apply the operator against
            if(stack.size() < 2){
                System.out.println("Stack underflow.");
            } else {

                //TO DO REFACTOR THIS SO DOESN'T POP IN THE BUFFER


                //perform the calculation immediately

                //convert top two string input elements to ints
                int right = Integer.parseInt(stack.pop());
                int left = Integer.parseInt(stack.pop());

                //calculate the result and return it as Integer
                //divide by 0 has a special use case so we return null and hence Integer rather than int
                Integer result = calculator.calcOperator(stack, input, left, right);

                //if calc returned something valid, add it to the stack
                //this handles the divide by 0 problem
                if (result != null) {

                    //push the result onto the stack
                    stack.push(String.valueOf(result));
                }
            }
        }

        //d here means show us the stack
        if(input.equals("d")) {
            System.out.println(stack.invertStackContents());
        }

        // = means look at the top of the stack
        if(input.equals("=")) {
            System.out.println(stack.peek());
        }
    }


}