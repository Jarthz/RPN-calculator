//this class is designed to inptepret the user input. Static methods as it does not impact other classes directly

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    //convert the input into valid and invalid tokens
    public static String[] parseInputString(String command) {

        //use a regex pattern to group the command input into a sub set of valid strings
        //regex grouping is one or many integers | or an operator | or d | or r | or #comments# | or one or many whitespace
        String regex = "(-?\\d+|[-+*/%^=]|d|r|#.*?#|\\s+)";

        //use the pattern matcher classes from the regex util to parse the command string and check if the input is valid pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);

        //create arraylist to hold valid tokens
        List<String> validTokens = new ArrayList<>();

        //int that will track positions between matches so that we can make mutliple matches and check for sequential in/valid matches
        int lastMatchEnd = 0;

        //find the matches
        while (matcher.find()) {
            //Check for invalid tokens between matches and print out to console

            //if matcher.start > last match end point then we've found a pattern
            if (matcher.start() > lastMatchEnd) {

                //create a string for any values between the end of the last good match and the beginning for the current match
                String invalidPart = command.substring(lastMatchEnd, matcher.start()).trim();

                //if that string is not empty, we found invalid input and print to console each char of the string
                if (!invalidPart.isEmpty()) {
                    for(int i = 0; i < invalidPart.length(); i++) {
                        System.out.println("Unrecognised operator or operand \"" + invalidPart.charAt(i) + "\".");
                    }
                }
            }

            //Add valid tokens and move the tracker forward to the end last match end
            String match = matcher.group(1);
            validTokens.add(match);
            lastMatchEnd = matcher.end();
        }

        //Check for any remaining invalid tokens that occur after the last match and before the end of the input command
        if (lastMatchEnd < command.length()) {
            String invalidPart = command.substring(lastMatchEnd).trim();
            if (!invalidPart.isEmpty()) {
                for(int i = 0; i < invalidPart.length(); i++) {
                    System.out.println("Unrecognised operator or operand \"" + invalidPart.charAt(i) + "\".");
                }
            }
        }

        //convert the array list to string array and return it to the method caller
        return validTokens.toArray(new String[0]);
    }
}
