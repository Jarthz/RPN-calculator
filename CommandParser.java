//this class is designed to inptepret the user input. Static methods as do no impact other classes directly

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    public static String[] parseInputString(String command) {

        //use a regex pattern to split the command input into a sub set of strings

        /*
        leading integers + or - | the operators | the d | r | = commands | # comments #
         */
        //String regex = "(-?\\d+(?:[+\\-*/%^]\\d+)*|[-+*/%^=]|d|r|#\\s*.*?\\s*#|[^\\s=]+=[^\\s]*)";
        String regex = "(-?\\d+(?:[+\\-*/%^]-?\\d+|-?\\d=+)+|[-+*/%^=]|d|r|#\\s*.*?\\s*#|(-?\\d+([+\\-*/%^=])*))";


        //use the pattern matcher classes from the regex util to parse the command string and check if the input is valid pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);

        //create arraylist  to hold valid tokens to operate over
        List<String> validTokens = new ArrayList<>();

        //To track positions between matches
        int lastMatchEnd = 0;

        while (matcher.find()) {
            //Check for invalid tokens between matches and print out to console
            if (matcher.start() > lastMatchEnd) {
                String invalidPart = command.substring(lastMatchEnd, matcher.start()).trim();
                if (!invalidPart.isEmpty()) {
                    System.out.println("Unrecognised operator or operand \"" + invalidPart + "\".");
                }
            }

            //Add valid tokens
            String match = matcher.group(1);
            validTokens.add(match);
            lastMatchEnd = matcher.end();
        }

        //Check for any remaining invalid tokens after the last match
        if (lastMatchEnd < command.length()) {
            String invalidPart = command.substring(lastMatchEnd).trim();
            if (!invalidPart.isEmpty()) {
                System.out.println("Unrecognised operator or operand \"" + invalidPart + "\".");
            }
        }

        return validTokens.toArray(new String[0]);
    }
//SRPN has special cases where it will handle unique inputs and do unique stuff
// we reparse the input string now we've split it to identify these special cases

    //create a method ot see if there's white space and then convert to infix if there's not

    public static String[] parse2(String[] input){
        List<String> processedTokens = new ArrayList<>();


        String[] operators = Operator.getOperators();
        //System.out.println("input variable " + Arrays.toString(input));

        for(String str : input){
            boolean operatorFound = false;

            for(String operator : operators) {
                if (str.contains(operator) && str.matches(".*\\d+.*")) {
                    operatorFound = true;
                    //System.out.println("infix foudn " + str);
                    //do something

                    String[] infixParts = str.split("(?<=[-+*/%^])|(?=[-+*/%^=])");

                    //System.out.println("infix parts " + Arrays.toString(infixParts));

                    for (int i = 0; i < infixParts.length; i++) {
                        String token = infixParts[i];

                        if (Operator.isOperator(token) && i + 1 < infixParts.length && infixParts[i + 1].matches("(-?\\d+)")) {
                            //System.out.println("logic tesk " + token + " " + infixParts[i + 1]);
                            processedTokens.add(infixParts[i + 1]);
                            processedTokens.add(token);
                            i += 1;

                        } else if(token.equals("=")){
                            for(int j = processedTokens.size() - 2; j >= 0; j--){
                                if(processedTokens.get(j).matches("(-?\\d+)")){
                                    System.out.println(processedTokens.get(j));
                                    break;
                                }
                            }


                        } else {
                            processedTokens.add(token);
                        }
                    }
                    break;
                }
            }
            if (str.contains("=") && str.matches(".*\\d+.*") && !operatorFound) {
               // System.out.println("Equals found " + str);

                String[] equalsParts = str.split("(?<=[-+*/%^])|(?=[-+*/%^=])");

                //System.out.println("equals parts " + Arrays.toString(equalsParts));

                for (String token : equalsParts) {
                    if(!token.equals("=")){
                        processedTokens.add(token);
                    } else if(token.equals("=")){
                        for(int j = processedTokens.size() - 1; j >= 0; j--){
                            if(processedTokens.get(j).matches("(-?\\d+)")){
                                System.out.println(processedTokens.get(j));
                                break;
                            }
                        }
                    } else {
                        processedTokens.add(token);
                    }
                }
            } else if(!operatorFound){
                processedTokens.add(str.trim());
            }
        }


            /*

            } else if (operatorFound == false){
                processedTokens.add(str);
                System.out.println("add to stack " + str);
            }
*/



        return processedTokens.toArray(new String[0]);

    }


    public static String[] parseSpecialCases(String[] input){
        List<String> proccessedTokens = new ArrayList<>();

        for(int i = 0; i < input.length; i++){
            String token = input[i];

            //TO DO fix this
            if(token.equals("^") && i + 1 < input.length && input[i+1].matches("\\^(-?\\d+)==")){
                String exponent = input[i+1];
                proccessedTokens.add(exponent);
                proccessedTokens.add(token);
                i++;
            } else {
                proccessedTokens.add(token);
            }
        }
        return proccessedTokens.toArray(new String[0]);
    }

}
