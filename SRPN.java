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
        //this represents enter being the execute command
        calculateStack(operators, operands);
    }

    //method to put the stack together
    public void buildStack(String input){
        //check if the str value is a number and saturated
        //below method returns null to value variable if neither condition
        Integer value = Saturation.isSaturatedOrOperand(input);

        //if above isn't null, it's a number, always push operands to the stack
        if(value != null){
            //operands to their own operand stack
            operands.push(String.valueOf(value));

        //check if the input is an operator using the isOperator method
        } else if(Operator.isOperator(input)) {

            /* this is our infix logic handler
            when inputting postfix commands, the operator stack is only max size 1. This is because post fix calculates all operators immediately when user
            presses enter, never leaving a value in the operator stack

            below condition is only true therefore when parsing an infix string (no whitespaces).

            It then checks the operator precedence mapped in the operator class. If the current operator < prior,
            we calculate the prior operator BEFORE pushing the current onto the stack.

            e.g. 2*3+ would see + < * and therefore calculate 2*3 first and the push + to the stack.
            if you expand to 2*3+4*5 you do above and have 6 in the stack, + in the stack, 4, 5 and *.
            The stack is called to calculate one final time when enter is pressed doing 20 + 6 in method processCommand

             */

            //check the stack and see if any existing operators in the stack hve a higher precdence than the current operator
            while (!operators.isEmpty() && Operator.opPrecedence(operators.peek()) >= Operator.opPrecedence(input)) {
                //if it does, we calculate the stack first before pushing the lower order operator on
                //this handles infix where 2*3+4*5 = 26.
                calculateStack(operators, operands);
            }

            //push the current operator to the stack
            operators.push(String.valueOf(input));

            /* ----------------------------------------------------------------------------------------
            "=" is special.
            1) SRPN is both an infix and postfix calculator depending on how you input commands
            2) when in infix mode, "=" has the highest precedence of operators

            " " whitespace is an execute command and will execute a hybrid infix/postfix
            enter is an execute command and will execute postfix

            This means we can input both eg: 1+2= 3 + 2 /

            If we explain in postfix notation, will be executed as:
            1, 2, =, +, 3, +, 2, /  which is 6/2

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
        } else if(input.equals("r")) {
            String rValue = RValueList.getNextValue();
            operands.push(String.valueOf(rValue));

            /* -------------

            d is the lowest precedent operator but it is executed immediately when seen in the stack. This means it cannot be
            handled in the same way as other operators as they do not directly call the calculation method.
            We could either see it, calculate stack, push it to stack, calculate stack again,
            or: see it, calc stack, print stack and never push it on.

            we identify d, calculate whatever is in the stack, then print the stack contents without pushing

            does something also unique that if the stack is empty it'll print negative saturation but not push to stack

             ----------*/

        } else if(input.equals("d")){
            calculateStack(operators, operands);
            if (operands.size() == 0) {
                System.out.println("-2147483648");
            } else {
                System.out.println(operands.invertStackContents());
            }

        //If " " \\s is entered, call the calculate stack method to calculate what's in there immediately
            //this is a loop so means user can input combined infix and post fix notation and we will handle it
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

                //control check, if we have an operator and <2 operand print message and go to next operator
                if (operands.size() < 2) {
                    System.out.println("Stack underflow.");
                } else {

                    //remove top two string elements from stack operands and convert to ints
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
