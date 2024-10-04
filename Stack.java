//create a class that is going to hold our data


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.*;


public class Stack {

    private int maxStackSize = 23; //hard coded not great but the original SRPN has this

    private Deque<String> stack = new ArrayDeque<>();

    //method of adding to the stack
    public void push(String value){
        //there's a max size limit in srpn stack of 23 so we'll check the size and print a message if we get past that
        if(stack.size() == 23){
            System.out.println("Stack overflow.");
        }
        else stack.push(value);
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

    //used to check how big the stack is
    public int size(){
        return stack.size();
    }

    //returns the whole stack. Not used now but can be good for testing
    public String getStack(){
     return stack.toString();
    }

}
