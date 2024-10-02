public class Main {

    public static void main(String[] args) {

        //create a stack to store the input
        Stack stack = new Stack();

        //create saturation class object to use specific methods to handle int saturation
        Saturation saturated = new Saturation();

        //create calculator object for my method calculations and pass our saturated object over
        Calculator calculator = new Calculator(saturated);

        Operator operator = new Operator();

        //create input buffer, passing over everything, the stack, calc and saturation checks
        InputBuffer input = new InputBuffer(stack, calculator, saturated, operator);

        //call the prompt input loop
        input.startInputLoop();



    }
}
