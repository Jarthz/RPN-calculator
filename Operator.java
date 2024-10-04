// create a class of the valid operator types

public class Operator {

    private static final String[] operators = {"+", "-", "/", "*", "%", "^"};

    private Operator(){

    }

    public static String[] getOperators() {
        //these operators defined here will need to be handled by the switch case in the calculator
        return operators;
    }

    public static boolean isOperator(String str){
        for(String operator : operators){
            if(str.equals(operator)){
                return true;
            }
        }
        return false;
    }

}
