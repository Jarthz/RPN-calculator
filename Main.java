public class Main {

    public static void main(String[] args) {

        //create a stack to store the input
        Stack stack = new Stack();

        //class for manipulating everything together
        CommandProcessor processor = new CommandProcessor(stack);

        //create input buffer, passing over the stack and calculator
        InputBuffer input = new InputBuffer(processor);

        //call the prompt input loop
        input.startInputLoop();



    }
}
