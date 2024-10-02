//create a class that is going to hold our data
//made as a class to adhere to Object Orientated Design

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.*;

public class Stack {

    private Deque<String> stack = new ArrayDeque<>();

    public void push(String value){
        stack.push(value);
    }

    public String pop() {
        return stack.pop();
    }

    public String peek() {
        return stack.peek();
    }

    //method to print out the contents of the stack in reverse order
    public String invertStackContents() {

        //create stringbuilder object instead of string array for efficiency
        StringBuilder stackElement = new StringBuilder();
        Iterator<String> reverse = stack.descendingIterator();

        //reverse loop through the stack
        while (reverse.hasNext()) {
            //add each element to the string builder and put in the line break so that the print call looks the same as srpn
            stackElement.append(reverse.next()).append("\n");

        }
        return stackElement.toString();
    }

    //method to print the stack in order
    public String getStackContents(){
        StringBuilder stackElement = new StringBuilder();
        Iterator<String> elements = stack.iterator();

        while (elements.hasNext()){
            stackElement.append(elements.next()).append("\n");
        }
        return stackElement.toString();
    }

    public int size(){
        return stack.size();
    }

    //to do create a stack max size

    public String getStack(){
     return stack.toString();
    }

}
