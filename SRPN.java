import java.util.ArrayList;
import java.util.List;

public class SRPN {

    /*
    we're going to take a stack based approach to the problem
    we have an instance class for stack which handles all our stack logic
    this means we can use multiple unique separate stack objects but they have the same class structure
    we're going to create separate stacks, one for the operands, one for the operators
    this will allow us to do a hybrid of postfix and infix input
    */


    //create a stack object for operands
    //create a stack object for operators
    private Stack operands = new Stack();
    private Stack operators = new Stack();


    public SRPN(){
        this.operands = operands;
        this.operators = operators;
    }

    //this method is going to connect all our other methods and classes
    //this will ultimately parse the command for valid input, build stacks, and then execute the command
    public void processCommand(String input) {

        //convert the input to a string array of valid tokens using a regex pattern match
        String[] inputArray = CommandParser.parseInputString(input);

        //loop through the valid string pattern elements in the array and build our stacks using the method
        for (String str : inputArray) {
            buildStack(str);
        }

        //now finished looping through the input, calculate the stack
        //this repesents enter being the execute command
        calculateStack(operators, operands);
    }

    //method to put the stack together
    public void buildStack(String input){
        //create an array list that will be used to reoder the operators by order of the mathmatical precedence. BODMAS
        List<String> opBuffer = new ArrayList<>();

        //check if the str value is a number and saturated
        //below method returns null to value variable if neither condition
        Integer value = Saturation.isSaturatedOrOperand(input);

        //if above isn't null, it's a number, always push operands to the stack
        if(value != null){
            //operands to their own operand stack
            operands.push(String.valueOf(value));

        //check if the input is an operator using the isOperator method | or if the input is 'd' and handle that like an operator
        } else if(Operator.isOperator(input)| input.equals("d")) {

            //check the stack and see if any existing operators in the stack hve a higher precdence than the current operator
            while (!operators.isEmpty() && Operator.opPrecedence(operators.peek()) >= Operator.opPrecedence(input)) {
                //if it does, pop the higher order operator off the stack and add it to our buffer list.
                // Loop throuhg each operator in the stack poping each off if required and building the buffer
                opBuffer.add(operators.pop());
            }

            //push the current operator to the stack
            operators.push(String.valueOf(input));

            //push the buffer back on to the stack
            for (int i = opBuffer.size() - 1; i >= 0; i--) {
                operators.push(opBuffer.get(i));
            }

            //clear the buffer of values for safety.
            opBuffer.clear();

            /* ----------------------------------------------------------------------------------------
            "=" is special.
            1) SRPN is both an infix and postfix calculator depending on how you input commands
            2) when in infix mode, "=" has the highest precedence of operators

            " " whitespace is an execute command and will execute a hybrid infix/postfix
            enter is an execute command and will execute postfix

            This means input: 1+2= 3 + 2 /

            If we explain in postfix notation, will be executed as:
            1 2 = + 3 + 2 /

            the final result is
            print "2"
            3

            point 1) we use " " as an execution command and calculate what's in the stack immediately
            point 2) we print whatever is at the top of the operator stack as soon as we see = and before any calculation

            --------------------------------------------------------------------- */

        } else if(input.equals("=")){
            if(operands.isEmpty()){
                System.out.println("Stack empty.");
            } else {
                System.out.println(operands.peek());
            }

        //r pushes a hardcoded value from a list to the stack. Refer to class RValueList
        } else if(input.equals("r")){
            String rValue = RValueList.getNextValue();
            operands.push(String.valueOf(rValue));

        //If " " \\s is entered, call the calculate stack method to calculate what's in there immediately
            //remember this is a loop so means user can input combined infix and post fix notation and we will handle it
        } else if(input.trim().isEmpty()){
            //calculate the stack
            calculateStack(operators, operands);
        }
    }

    //method to calculate everything, passing into it the operators and operands
    public void calculateStack(Stack operators, Stack operands) {

        //evaluate if we can operate on it first
        //if there's no operators then nothing to do and we end the method
        while (!operators.isEmpty()) {

            //grab an operator to decide what we're doing
            String operator = operators.pop();

            //for d, print the stack contents in reverse order (bottom to top)
            if (operator.equals("d")) {
                //if the stack is empty print the min saturation number but don't push it to the stack
                if (operands.size() == 0) {
                    System.out.println("-2147483648");
                } else {
                    System.out.println(operands.invertStackContents());
                }
            } else {
                //control check, if we have an operator and only one operand print message and go to next operator
                if (operands.size() < 2) {
                    System.out.println("Stack underflow.");
                } else {

                    //remove top two string elements from stack and convert to ints
                    int right = Integer.parseInt(operands.pop());
                    int left = Integer.parseInt(operands.pop());

                    //calculate the result for top two elements and return it as Integer array.
                    //refer to Class Calculator and method calcOperator
                    //If we have more than 1 element in the returned array, we failed to do the calculation
                    //and we return the original operands back to the stack
                    Integer[] result = Calculator.calcOperator(operator, left, right);

                    //if calc returned something valid, add it to the stack
                    if (result != null) {
                        //if more than one element was returned, we didn't do a calc and need to push operands back
                        if (result.length > 1) {
                            operands.push(String.valueOf(result[0])); //push left
                            operands.push(String.valueOf(result[1])); //push right
                        } else {
                            //push the result onto the stack
                            operands.push(String.valueOf(result[0]));
                        }
                    }
                }
            }
        }
    }
}
