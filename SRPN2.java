//instance class that's going to do all the lifting connecting the user input, the stack, the operators, the calculator together

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SRPN2 {

    private Stack operands = new Stack();
    private Stack operators = new Stack();


    public SRPN2(){
        this.operands = operands;
        this.operators = operators;
    }

    //method that is going to parse the command and the process each input real time
    public void processCommand(String input) {

        //convert the input to a string array using a regex pattern match
        //this will help use break the input up into operators/operands etc
        System.out.println("command " + input);
        String[] testArray = CommandParser2.parseInputString(input);
        System.out.println("1st parsed array " + Arrays.toString(testArray));

        String[] inputArray = CommandParser2.parse2(testArray);
        System.out.println("final parsed array " + Arrays.toString(inputArray));

        //test


        //parse over the string again to handle edge cases where the SRPN actually does infix
        //String[] inputArray = CommandParser.parseSpecialCases(testArray);

        //String[] test = CommandParser.parseLoop(testArray);

        //loop through the valid string patterns in the array and perform their action
        for (String str : inputArray) {
            //method to perform operation on the command
            buildStack(str);
        }

        System.out.println("operator stack " + operators.getStack());
        System.out.println("operand stack " + operands.getStack());

        //calculate
    }

    public void buildStack(String input){
        List<String> opBuffer = new ArrayList<>();

        Integer value = Saturation.isSaturatedOrOperand(input);
        if(value != null){
            operands.push(String.valueOf(value));
        } else if(Operator.isOperator(input)| input.equals("d")) {
            while (!operators.isEmpty() && Operator.opPrecedence(operators.peek()) >= Operator.opPrecedence(input)) {
                opBuffer.add(operators.pop());
            }
            operators.push(String.valueOf(input));

            for (int i = opBuffer.size() - 1; i >= 0; i--) {
                operators.push(opBuffer.get(i));
            }

            opBuffer.clear();
        } else if(input.equals("=")){
            if(operands.isEmpty()){
                System.out.println("Stack empty.");
            }
            System.out.println(operands.peek());


        } else if(input.equals("r")){
            String rValue = RValueList.getNextValue();
            operands.push(String.valueOf(rValue));
        } else if(input.trim().isEmpty()){
            //calculate the stack
            calculateStack(operators, operands);
        }


    }

    public void calculateStack(Stack operators, Stack operands){
        //evaluate if we can operate on it first
        //if there's no operators then nothing to do go back to loop
        if(!operators.isEmpty()){

        }

        if(operands.size() < 2){
            System.out.println("Stack underflow.");
        } else {
            int right = Integer.parseInt(operands.pop());
            int left = Integer.parseInt(operands.pop());

        }

    }

}
