public class Main {

    public static void main(String[] args) {

        //create a stack to store the input
        Stack stack = new Stack();

        //create calculator object for my method calculations
        //this will modify the stack object we've initialised
        Calculator calculator = new Calculator();

        //create input buffer, passing over the stack and calculator
        InputBuffer input = new InputBuffer(stack, calculator);

        //call the prompt input loop
        input.startInputLoop();



    }
}
