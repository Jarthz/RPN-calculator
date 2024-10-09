//this class is designed to inptepret the user input. Static methods as do no impact other classes directly

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser2 {

    public static String[] parseInputString(String command) {

        //use a regex pattern to split the command input into a sub set of valid strings

        /*
        leading integers positive or negative  | the operators | the d | r | = commands | # comments #
       first block:
       -5*-6= with everything but -5 being optional with the others must being in the pattern if they exist
       second block:
       -5*-6=


         */

        String regex = "(-?\\d+(?:[-+*/%^=]*-?\\d+)*=*|(?:[-+*/%^]=*)+-?\\d*=*|=|d|r|#\\s*.*?\\s*#)";

        String[] test = command.split(" ");
        System.out.println(Arrays.toString(test));

        String regex2 = "((?:[-+/%^=])|-?\\d+|d|r|#.*?#|\\s+)";

        //use the pattern matcher classes from the regex util to parse the command string and check if the input is valid pattern
        //Pattern pattern = Pattern.compile(regex);
        Pattern pattern = Pattern.compile(regex2);

       // Matcher matcher = pattern.matcher(command);
        Matcher matcher = pattern.matcher(command);

        List<String> dummyReg = new ArrayList<>();

        /*
        while(testMatch.find()) {
            String dummy = testMatch.group(1);
            dummyReg.add(dummy);

        }

        System.out.println("Test regex 2: " + dummyReg);
*/

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

    /*
    SRPN has many special cases where it will convert to an infix calculator or handle = differently

    these features are activated by whitespace

    Our firse parse of the command created an array with elements that have white space and no whitepace

    e.g. input -2+56= === +=-5= 1 + 2 = 5==
    will return from our first parse
    [-2+56=, =, =, =, +=-5=, 1, +, 2, =, 5==]

    we'll then handle each element here so:
    if it's -2+56=  that's infix and "=" special case, so we reorder the operators and operands and handle = directly
    === is fine and goes post fix
    +=-5= : this moved the first = to index 0, moves, sees -5= and prints 5, moves -5 to index 1, and moves + to index 2
    if it's a lone element it gets handled in post fix directly to the stack
    if it's 5== then it'll print 5 twice and then push 5 to stack

     */

//
//

    //create a method ot see if there's white space and then convert to infix if there's not

    public static String[] parseLoop(String[] input){
        List<String> processedTokens = new ArrayList<>();

        String[] operators = Operator.getOperators();
        System.out.println("input " + Arrays.toString(input));

        //for each element of the array string input, split it up again into array
        //e.g. 1+2, 2+5
        //str1 = 1,+,2
        //str2 = 2,+,5

        for(String str : input){
            String[] parts = str.split("(?<=[+*/%^=])|(?=[+*/%^=])|(?<=[^-\\d])-|(?=[^-\\d]-)");
            System.out.println("parts " + Arrays.toString(parts));

            /*//create our loop logic here with our rules
            if operator, = then =, operator, push to stack
            if number, = then print proceeding number and don't have = in stack
            if operator, operatnd, then operand, operator. push to stack
            if operand, operator, carry on

            base case it's a single value with space so push
            */

            //base case if there's just one token in the element add it to the arrary
            if(parts.length == 1){
                processedTokens.add(parts[0]);
            } else{
            //there's more than one token in the element so we need to loop through each token and apply our sort algorithm
                for(int i=0; i < parts.length; i++){
                    // if operator and followed by =
                    //create a token String to avoid confusion
                    String token = parts[i].trim();
                    boolean equalsShuffled = false;
                    int marker = 0;

                    //"=" as second token in element. If proceeded by operator then switch

                    //check if token is an operator
                    if(Operator.isOperator(token)){
                        System.out.println("token is operator " + token);
                        //check if it's followed by "=" sign
                        if(i + 1 < parts.length && parts[i + 1].equals("=") && equalsShuffled == false){
                            parts[i] = parts[i+1];
                            parts[i+1] = token;
                            System.out.println("token hit " + parts[i]);
                            equalsShuffled = true;
                            marker = i;


                        } else if(i + 1 < parts.length && parts[i + 1].equals("=") && equalsShuffled == true){
                            parts[marker + 1] = parts[i+1];


                        }
                        //processedTokens.add(token);

                    }

                }

            //once sorted, add
                processedTokens.add(Arrays.toString(parts));
            }


        }


        System.out.println("test processed array" + processedTokens);

        return processedTokens.toArray(new String[0]);
    }

    public static String[] stringParse(String input){
        List<String> processedTokens = new ArrayList<>();

        //break down each sub array

        String regex = "(\\d+|[-+*/%^=])";
        //"(-?\\d+(?:[+\\-*/%^=]-?\\d+)*=*|-?\\d*([-+*/%^]=*)+-?\\d*=*|=|d|r|#\\s*.*?\\s*#)";

        //use the pattern matcher classes from the regex util to parse the command string and check if the input is valid pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        //create arraylist  to hold valid tokens to operate over

        //To track positions between matches
        int lastMatchEnd = 0;

        while (matcher.find()) {
            //Add valid tokens
            String match = matcher.group(1);
            processedTokens.add(match);

        }


        return processedTokens.toArray(new String[0]);
    }

    public static void stringParseTest(String[] input){
        for(String str : input){
            System.out.println("nonsense " + Arrays.toString(stringParse(str)));
        }

    }

    public static String[] parse2(String[] input){
        //initialise an array to store our reordered input
        List<String> processedTokens = new ArrayList<>();

        // create an array of what are valid operators are from the operator class
        String[] operators = Operator.getOperators();
        System.out.println("input variable " + Arrays.toString(input));


        /* list of cases
        1+1= print 1 and do infix
        1+1  infix
        1+-2 infix
        ^= reorder =^
        2^= print 2
        operator digit reorder 2^
        3==== print 3
        ^2= print 2 reorder 2^

        can do
        1+1 : infix
        operator digit +2 = 2+
        3====
        2^=
        ^2print 2 reorder 2^
        ^= reorder =^
        1+-2
        -1+-2

        To do
        +=-5= , first = element push to stack as not proceeding digit
        ^^ create a placeholder token to print the variable proceeding so something like y-5
        5-5


        refactor all of it

         */



        for(String str : input) {
            boolean operatorFound = false;



            for (String operator : operators) {
                //if substring contains operator and digit
                if (str.contains(operator) && str.matches("-?\\d*((?:[-+*/%^=]-?\\d+)*=*)") && !str.matches("-\\d")) {
                    operatorFound = true;
                    System.out.println("infix foudn " + str);

                    //testinga  new matcher



                    //modify this to handle -x input
                    String[] infixSplit = str.split("(?<=[-+*/%^=()])|(?=[-+*/%^=()])");
                    String[] infixParts = NegativeConverter.negativeConvert(infixSplit);

                    //String[] infixParts = str.split("(?<=[+*/%^=])|(?=[+*/%^=])|(?<=[^-\\d])-|(?=[^-\\d]-)");
                    //String[] infixParts = str.split("(?<=[+*/%^=()])|(?=[+*/%^=()])|(?<=\\d])-(?=\\d)|(?<!\\d)-");

                    //String[] infixParts = str.split("(?<=[-+*/%^])|(?=[-+*/%^=])|(?<=\\d)(?=-)");

                    System.out.println("infix parts " + Arrays.toString(infixParts));

                    for (int i = 0; i < infixParts.length; i++) {
                        String token = infixParts[i];

                        if (Operator.isOperator(token) && i + 1 < infixParts.length && infixParts[i + 1].matches("(-?\\d+)")) {
                            //System.out.println("logic tesk " + token + " " + infixParts[i + 1]);
                            processedTokens.add(infixParts[i + 1]);
                            processedTokens.add(token);
                            i += 1;
                            // create case for operator followed by =

                            //   } else if (Operator.isOperator(token) && i + i < input.length && infixParts[i + 1].equals("=")){
                            //   for(int j = proce)

                        } else if (token.equals("=")) {
                            for (int j = processedTokens.size() - 1; j >= 0; j--) {
                                if (processedTokens.get(j).matches("(-?\\d+)")) {
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

            //equals follows a number
            if (str.contains("=") && str.matches(".*\\d+.*") && !operatorFound) {
                System.out.println("Equals found " + str);

                String[] equalsParts = str.split("(?<=[+*/%^=])|(?=[+*/%^=])|(?<=[^-\\d])-|(?=[^-\\d]-)");;

                System.out.println("equals parts " + Arrays.toString(equalsParts));

                for (String token : equalsParts) {
                    boolean foundValidEquals = false;
                    if (!token.equals("=")) {
                        processedTokens.add(token);
                    } else if (token.equals("=")) {
                        for (int j = processedTokens.size() - 1; j >= 0; j--) {
                            if (processedTokens.get(j).matches("(-?\\d+)")) {
                                System.out.println(processedTokens.get(j));
                                //variable to hold = and j
                                //move variable infront of j
                                String placeholder = "p " + processedTokens.get(j);
                                if(processedTokens.get(0).matches("=")) {
                                    processedTokens.add(1, placeholder);
                                } else{
                                    processedTokens.add(0, placeholder);
                                }
                                break;
                            } else {
                                processedTokens.add(0,token);
                            }
                        }
                    } else {
                        processedTokens.add(token);
                    }
                }

            //operator followed by =, convert to =, operator
            } else if(str.contains("=") && str.matches(".*[+-/%^].*") && !operatorFound){
                System.out.println("test " + str);

                String[] equalsParts = str.split("(?<=[+*/%^=])|(?=[+*/%^=])|(?<=[^-\\d])-|(?=[^-\\d]-)");

                for (String token : equalsParts) {
                    if (!token.equals("=")) {
                        processedTokens.add(token);
                    } else if (token.equals("=")) {
                        boolean foundDigit = false;
                        for (int j = processedTokens.size() - 1; j >= 0; j--) {
                            if (processedTokens.get(j).matches("(-?\\d+)")) {
                                System.out.println(processedTokens.get(j));
                                foundDigit = true;
                                break;
                            }
                        }
                        if(foundDigit == false){
                            processedTokens.add(0,token);
                        }

                    } else {
                        processedTokens.add(token);
                    }
                }

            }else if(!operatorFound){
                processedTokens.add(str.trim());
            }
        }


        return processedTokens.toArray(new String[0]);

    }



    public static String[] parse3(String[] input){
        //initialise an array to store our reordered input
        List<String> processedTokens = new ArrayList<>();

        // create an array of what are valid operators are from the operator class
        String[] operators = Operator.getOperators();


        for(String str : input) {
            //if this is a single token, push to array it's not special
            if (str.matches("-?\\d+|[-+*/%^=]")) {
                System.out.println("str singles test " + str);
                processedTokens.add(str);

            } else {
                boolean operatorFound = false;

                //split the string now

                String[] tokenised = stringParse(str);

                System.out.println("tokenised new " + Arrays.toString(tokenised));

                //create an infix converstion method
                //loop through each individual token of the infix

                String[] negativeTokens = NegativeConverter.negativeConvert(tokenised);


                System.out.println("negative array " + Arrays.toString(negativeTokens));

                String[] infixTokens = NegativeConverter.infixConvert(negativeTokens);

                System.out.println("infixed to postfix " + Arrays.toString(infixTokens));







                //equals follows a number
                if (str.contains("=") && str.matches(".*\\d+.*") && !operatorFound) {
                    System.out.println("Equals found " + str);

                    String[] equalsParts = str.split("(?<=[+*/%^=])|(?=[+*/%^=])|(?<=[^-\\d])-|(?=[^-\\d]-)");
                    ;

                    System.out.println("equals parts " + Arrays.toString(equalsParts));

                    for (String token : equalsParts) {
                        boolean foundValidEquals = false;
                        if (!token.equals("=")) {
                            processedTokens.add(token);
                        } else if (token.equals("=")) {
                            for (int j = processedTokens.size() - 1; j >= 0; j--) {
                                if (processedTokens.get(j).matches("(-?\\d+)")) {
                                    System.out.println(processedTokens.get(j));
                                    //variable to hold = and j
                                    //move variable infront of j
                                    String placeholder = "p " + processedTokens.get(j);
                                    if (processedTokens.get(0).matches("=")) {
                                        processedTokens.add(1, placeholder);
                                    } else {
                                        processedTokens.add(0, placeholder);
                                    }
                                    break;
                                } else {
                                    processedTokens.add(0, token);
                                }
                            }
                        } else {
                            processedTokens.add(token);
                        }
                    }

                    //operator followed by =, convert to =, operator
                } else if (str.contains("=") && str.matches(".*[+-/%^].*") && !operatorFound) {
                    System.out.println("test " + str);

                    String[] equalsParts = str.split("(?<=[+*/%^=])|(?=[+*/%^=])|(?<=[^-\\d])-|(?=[^-\\d]-)");

                    for (String token : equalsParts) {
                        if (!token.equals("=")) {
                            processedTokens.add(token);
                        } else if (token.equals("=")) {
                            boolean foundDigit = false;
                            for (int j = processedTokens.size() - 1; j >= 0; j--) {
                                if (processedTokens.get(j).matches("(-?\\d+)")) {
                                    System.out.println(processedTokens.get(j));
                                    foundDigit = true;
                                    break;
                                }
                            }
                            if (foundDigit == false) {
                                processedTokens.add(0, token);
                            }

                        } else {
                            processedTokens.add(token);
                        }
                    }

                } else if (!operatorFound) {
                    processedTokens.add(str.trim());
                }
            }

        }
        return processedTokens.toArray(new String[0]);

    }


}
