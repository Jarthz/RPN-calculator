// instance class that's going to do all the lifting connecting the user input, the stack, the operators, the calculator together

public class CommandProcessor {

    private Stack stack;

    public CommandProcessor(Stack stack){
        this.stack = stack;
    }

    public void processInput(String input) {

        //check if input is operand and/or saturated. Return null if invalid
        Integer value = Saturation.isSaturatedOrOperand(input);

        //push all valid integers/saturated values straight to stack
        if(value != null) {
            stack.push(String.valueOf(value));
        }

        // check now if it's an operator we've defined int he calculator class
        if(Operator.isOperator(input)) {

            //error message if you don't have enough operands in the stack to apply the operator against
            if(stack.size() < 2){
                System.out.println("Stack underflow.");
            } else {

                //convert top two string input elements to ints
                int right = Integer.parseInt(stack.pop());
                int left = Integer.parseInt(stack.pop());

                //calculate the result and return it as Integer array. If we have more than 1 element in the array, we failed to do the calculation and we return the values back to the stack

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
            System.out.println(stack.invertStackContents());
        }

        // = means look at the top of the stack
        if(input.equals("=")) {
            System.out.println(stack.peek());
        }

        //r input returns a hardcoded value from a list object and wraps around the list and pushes the returned value to stack
        if(input.equals("r")) {
            String rValue = RValueList.getNextValue();
            stack.push(String.valueOf(rValue));
        }
    }

}
