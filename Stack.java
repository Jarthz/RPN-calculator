//create a stack class that is going to hold our data

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.*;

//Encapsulation, we are going to use mutliple stacks so we make this an instance class

public class Stack {

    //hard coded max size of the stack. This allows for 'stack overflow'.
    private int maxStackSize = 23;

    //create stack objct using Deque
    private Deque<String> stack = new ArrayDeque<>();

    //method of adding to the stack
    public void push(String value){
        //there's a max size limit in srpn stack of 23 so we'll check the size and print a message if we get past that
        if(stack.size() == 23){
            System.out.println("Stack overflow.");
        }
        else stack.push(value);
    }

    //general utility methods
    public String pop() {
        return stack.pop();
    }

    public String peek() {
        return stack.peek();
    }


    public int size(){
        return stack.size();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    //returns the whole stack. Not used now but was vital to see inside the stack during development
    public String getStack(){
        return stack.toString();
    }

    //method to print out the contents of the stack in reverse order
    public String invertStackContents() {

        //create stringbuilder object instead of string array for efficiency
        StringBuilder stackElements = new StringBuilder();
        Iterator<String> reverse = stack.descendingIterator();

        //reverse loop through the stack
        while (reverse.hasNext()) {
            //add each element to the string builder and put in the line break so that the print call looks the same as srpn
            stackElements.append(reverse.next()).append("\n");
        }

        //remove the last element from the stack elements because that's what SRPN does. (removes /n)
        int last = stackElements.length();
        stackElements.deleteCharAt(last-1);
        return stackElements.toString();
    }
}
