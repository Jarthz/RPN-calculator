//instance class that's going to do all the lifting connecting the user input, the stack, the operators, the calculator together

import java.util.Arrays;

public class SRPN {

    private Stack stack = new Stack();


    public SRPN(){
        this.stack = stack;
    }

    //method that is going to parse the command and the process each input real time
    public void processCommand(String input) {

        //convert the input to a string array using a regex pattern match
        //this will help use break the input up into operators/operands etc
        System.out.println("command " + input);
        String[] testArray = CommandParser.parseInputString(input);
        System.out.println("1st parsed array " + Arrays.toString(testArray));



        //test
        String[] inputArray = CommandParser.parse2(testArray);
        System.out.println("final array " + Arrays.toString(inputArray));


        //parse over the string again to handle edge cases where the SRPN actually does infix
        //String[] inputArray = CommandParser.parseSpecialCases(testArray);

        //String[] test = CommandParser.parseLoop(testArray);

        //loop through the valid string patterns in the array and perform their action
        for (String str : inputArray) {
            //method to perform operation on the command
            calculateCommand(str);
        }

    }

    //utlity method, this is going to do all the connections between inputs, calculations, error handling and ouput
    public void calculateCommand(String input) {

        //check if input is operand and/or saturated. Return null if invalid
        Integer value = Saturation.isSaturatedOrOperand(input);

        //push all valid integers/saturated values straight to stack
        if(value != null) {
            stack.push(String.valueOf(value));
        }

        //check now if it's an operator we've defined int he calculator class
        if(Operator.isOperator(input)) {

            //TO DO
            // -------
            // ^x

            //error message if you don't have enough operands in the stack to apply the operator against
            if(stack.size() < 2){
                System.out.println("Stack underflow.");
            } else {

                //remove top two string elements from stack and convert to ints
                int right = Integer.parseInt(stack.pop());
                int left = Integer.parseInt(stack.pop());

                //calculate the result for top two elements and return it as Integer array. If we have more than 1 element in the array, we failed to do the calculation and we return the values back to the stack
                Integer[] result = Calculator.calcOperator(input, left, right);

                //if calc returned something valid, add it to the stack
                if (result != null) {
                    //if more than one element was returned, we didn't do a calc and need to push operands back
                    if(result.length>1){
                        stack.push(String.valueOf(result[0])); //push left
                        stack.push(String.valueOf(result[1])); //push right
                    } else {
                        //push the result onto the stack
                        stack.push(String.valueOf(result[0]));
                    }
                }
            }
        }

        //d here means show us the stack
        if(input.equals("d")) {
            //logic that if the stack is empty, print negative saturation but don't push to stack?!?!?!
            if(stack.size()==0){
                System.out.println("-2147483648");
            } else {
                System.out.println(stack.invertStackContents());
            }
        }

        //= means look at the top of the stack
        if(input.equals("=")) {
            //logic if stack is empty print out msg
            if(stack.size()==0){
                System.out.println("Stack empty.");
            } else {
                System.out.println(stack.peek());
            }
        }

        //r input returns a hardcoded value from a list object and wraps around the list and pushes the returned value to stack
        if(input.equals("r")) {
            String rValue = RValueList.getNextValue();
            stack.push(String.valueOf(rValue));
        }

        if(input.contains("p")) {
            String[] split = input.split("^p\\s");
            String number = split[1].trim();
            System.out.println(number);

        }

    }



}
