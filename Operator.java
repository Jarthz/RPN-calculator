// create a class of the valid operator types


public class Operator {
    private String[] operators;

    public Operator(){

    }

    public String[] getOperators() {
        String[] operators = {"+", "-", "/", "*", "%", "^"};
        return operators;
    }

    public boolean isOperator(String str){
        operators = getOperators();

        for(String operator : operators){
            if(str.equals(operator)){
                return true;
            }
        }
        return false;
    }

}
