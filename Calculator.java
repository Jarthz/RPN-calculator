import java.util.Deque;

public class Calculator {

    private Saturation saturated;

    public Calculator(Saturation saturated) {
        this.saturated = saturated;
    }


    public Integer calcOperator(Stack stack, String operator, int left, int right) {
        switch (operator) {
            case "+":
                return saturated.addSaturated(left, right);

            case "-":
                return saturated.minusSaturated(left, right);

            case "*":
                return saturated.multiplySaturated(left, right);

                //unique handling of divide by zero. We want to maintain the stack as if we didn't perform operation
            case "/":
                if(right == 0){
                    System.out.println("Divide by 0.");
                    //add the integers back into the stack
                    stack.push(String.valueOf(left));
                    stack.push(String.valueOf(right));

                    //return nothing to the input screen so the stack is unchanged
                    return null;
                }
                return left / right;

            case "^":
                if(right < 0){
                    System.out.println("Negative power.");

                    //same as divide by zero, srpn pushes the operands back tot he stack and returns nothing
                    stack.push(String.valueOf(left));
                    stack.push(String.valueOf(right));

                    return null;
                }
                return saturated.powerSaturated(left, right);

            case "%":
                if(right == 0){
                    System.out.println("Floating point exception (core dumped)");
                    System.exit(1);
                }
                return left % right;

            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

  /*  public int getSaturated(int value){
        if(value > 2147483647){
            return 2147483647;
        }
        return value;
    } */


}
