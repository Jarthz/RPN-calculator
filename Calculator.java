//class to handle all the math logic here

public class Calculator {

    private Calculator() {

    }
    //switch cases for every type of operator we received
    //we'll take as input the stack, the operator
    public static Integer[] calcOperator(String operator, int left, int right) {
        switch (operator) {
            case "+":
                return new Integer[]{Saturation.addSaturated(left, right)};

            case "-":
                return new Integer[]{Saturation.minusSaturated(left, right)};

            case "*":
                return new Integer[]{Saturation.multiplySaturated(left, right)};

                //unique handling of divide by zero. We want to maintain the stack as if we didn't perform operation
            case "/":
                if(right == 0){
                    System.out.println("Divide by 0.");
                    //return the un adjusted values back
                    return new Integer[]{left, right};
                }
                return new Integer[]{left / right};

            case "^":
                if(right < 0){
                    System.out.println("Negative power.");
                    //return the unadjstued values back
                    return new Integer[]{left, right};
                }
                return new Integer[]{Saturation.powerSaturated(left, right)};

            case "%":
                    //SRPN when right is 0 will print a msg and teminate the program
                if(right == 0){
                    System.out.println("Floating point exception (core dumped)");
                    System.exit(1);

                    //SRPN does soemthing wierd where is the left is 0 it'll print a msg and do nothing, even though the operation is actually valid
                } else if (left == 0){
                    System.out.println("Divide by 0.");
                    //return unadjsuted back
                    return new Integer[]{left, right};
            }
                return new Integer[]{left % right};

                //this should never happen
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

}
