//this class is designed to inptepret the user input. Static methods as do no impact other classes directly

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    public static String[] parseInputString(String command) {

        //use a regex pattern to split the command input into a sub set of strings
        String regex = "(-?\\d+|[-+*/%^]|d|r|=|#\\s*.*?\\s*#|p)";

        //use the pattern matcher classes from the regex util to parse the command string and check if the input is valid pattern
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

}
