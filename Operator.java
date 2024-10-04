//valid operator types and a method that checks if something is an operator. Static methods as do no impact other classes directly

public class Operator {

    //static hardcoded list of operators we'll handle
    private static final String[] operators = {"+", "-", "/", "*", "%", "^"};

    //private constructor. This is immutable nor modifies other classes
    private Operator(){
    }

    //method to return these operators if requested
    public static String[] getOperators() {
        return operators;
    }

    //method to consume an input string and parse it to see if any value is an operator
    public static boolean isOperator(String str){
        for(String operator : operators){
            if(str.equals(operator)){
                return true;
            }
        }
        return false;
    }

}
