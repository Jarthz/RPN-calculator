import java.lang.Math;

//static utility class so as to help impliment the saturation logic of the calculator

public class Saturation {
    private static final int MAX_INT = Integer.MAX_VALUE;
    private static final int MIN_INT = Integer.MIN_VALUE;

    //this class will do all our saturation handling for operators and operands

    private Saturation(){
        //no instances
    }

    //method of if the input string is satruated or operator
    public static Integer isSaturatedOrOperand(String input) {
        try {
            long longValue = Long.parseLong(input);

            //too big
            if (longValue > MAX_INT) {
                return MAX_INT;

                //too small
            } else if (longValue < MIN_INT) {
                return MIN_INT;

                //its value in range and cast as int
            } else {
                return (int) longValue;
            }
            //if it's not an operand it's an error
        } catch (NumberFormatException e) {
            return null;
        }
    }

    //method to safely add two ints together, or return the saturated value
    public static int addSaturated(int left, int right) {
        if (right > 0 && left > MAX_INT - right) {
            return MAX_INT;
        }
        if (right < 0 && left < MIN_INT - right) {
            return MIN_INT;
        }
        return left + right;
    }

    //method to safely deduct two ints, or return the saturated value
    public static int minusSaturated(int left, int right) {
        if (right > 0 && left < MIN_INT + right) {
            return MIN_INT;
        }
        if (right < 0 && left > MAX_INT + right) {
            return MAX_INT;
        }
        return left - right;
    }

    //method to mulitply two ints, or return the saturated value
    public static int multiplySaturated(int left, int right) {

        //SRPN pushes 0 to the stack when multiplying by 0
        if (left == 0 || right == 0) {
            return 0;
        }

        if (right > 0) {
            if (left > Integer.MAX_VALUE / right) {
                return Integer.MAX_VALUE; // Overflow, return max value
            }
        }
        // Check for underflow when both are negative
        else if (left < 0 && right < 0) {
            if (left < Integer.MAX_VALUE / right) {
                return Integer.MAX_VALUE; // Negative underflow, return max value
            }
        }

        // Check for negative overflow
        if (right < 0 && left < Integer.MIN_VALUE / right) {
            return Integer.MIN_VALUE; // Underflow, return min value
        }

        // Check for underflow for positive values
        if (left > 0 && right < Integer.MIN_VALUE / left) {
            return Integer.MIN_VALUE; // Underflow, return min value
        }

        // If no overflow or underflow, perform the multiplication
        return left * right;
    }

    public static int powerSaturated(int left, int right) {
        return (int) Math.pow(left, right);

    }

}
