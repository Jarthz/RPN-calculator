import java.util.ArrayDeque;
import java.util.Deque;

//evaluate a string and then assign each operands/operators to the tree

public class TreeBuilder {

//method
    public TreeNode buildTree(String expression) {
//create a stack collection using Deque interface. We'll use this to push/pop elements
        Deque<TreeNode> stack = new ArrayDeque<>();

//handle the expression and turn them into inputs
        String[] tokens = expression.split(" ");

        for(String token : tokens) {
            if(isOperator(token)) { //call this method that checks if the item in the string expression is an operator

// if it's an operator then pop the stack to get the two operands
                TreeNode right = stack.pop();
                TreeNode left = stack.pop();

//create the tree node with the operator on top and the integers as leaves
                TreeNode operatorNode = new TreeNode(token);
                operatorNode.left = left;
                operatorNode.right = right;

//push this tree onto our stack
                stack.push(operatorNode);
            }else if(isNumeric(token)){
                //push operands directly ontot he stack
                stack.push(new TreeNode(token));

            }else if(token.equals("d")){
                System.out.println(stack.toString());
            }
        }
        return stack.pop();
    }

//create a method the checks for an operator in the input
    private boolean isOperator(String token){
        String[] operators = {"+", "-", "/", "*", "%"};
        for(String operator : operators) {
            if(token.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    //create a method for if the value is numeric (we'll need this case because we're going to use 'd'

    private boolean isNumeric(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }



}
