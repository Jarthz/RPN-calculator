//Class stores valid operator types and a static method that checks if something is an operator.
//Static methods as do no impact other classes directly and is general utility method

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

    //method that returns a numerical value to order the operators precedence (BODMAS)
    public static int opPrecedence(String op){

        //dunno why but seems to be in SRPN
        if(op.equals("-")){
            return 0;
        }

        if(op.equals("+")){
            return 1;
        }

        if(op.equals("*")) {
            return 2;
        }

        if(op.equals("/") | op.equals("%")) {
            return 3;
        }

        if(op.equals("^")) {
            return 4;
        }
//special case, SRPN always executes = over all other inputs
        if(op.equals("=")) {
            return 5;
        }

        if(op.equals("d")){
            return 0;
        }

        else {
            return -1;
        }
    }
}