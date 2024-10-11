//create a stack class that is going to hold our data

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.*;

//Encapsulation, we are going to use mutliple stacks so we make this an instance class

public class Stack {
    //create a variable to hold the limit size of the stack
    private Integer maxStackSize;

    //pass the max stack size when creating the object
    public Stack(Integer maxStackSize){
        this.maxStackSize = maxStackSize;
    }

    //create stack object using Deque
    private Deque<String> stack = new ArrayDeque<>();

    //method of adding to the stack
    public void push(String value){
        //check the size of the stack and print a message if we attempt to push past that
        //if the max stack variable is null then this check isn't applicable as it was purposely unassigned
        if(maxStackSize != null && stack.size() == maxStackSize){
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
