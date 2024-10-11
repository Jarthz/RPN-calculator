import java.lang.Math;
import java.math.BigInteger;

//this class will do all our saturation handling for operators and operands as well as safely performing operations
public class Saturation {

    //create variable to hold the max and min integer saturation values
    private static final int MAX_INT = Integer.MAX_VALUE;
    private static final int MIN_INT = Integer.MIN_VALUE;


    private Saturation(){
        //no instances, immutable utility class
    }

    //method of if the input string is satruated or operator.
    public static Integer isSaturatedOrOperand(String input) {
        try {
            //try converting the string input into an arbitrarily large integer
            BigInteger bigValue = new BigInteger(input);

            //create variables in BigInteger type to hold the max and min int values
            BigInteger maxInt = BigInteger.valueOf(MAX_INT);
            BigInteger minInt = BigInteger.valueOf(MIN_INT);

            //if number > max int number return max int
            if (bigValue.compareTo(maxInt) > 0) {
                return MAX_INT;

                //if number < min int number return min int
            } else if (bigValue.compareTo(minInt) < 0) {
                return MIN_INT;

                //its value in range and cast as int
            } else {
                return bigValue.intValue();
            }
            //if it's not an operand it's an error, so return null
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

        //multiply two positive numbers
        if (left > 0 && right > 0) {
            if (left > Integer.MAX_VALUE / right) {
                return Integer.MAX_VALUE; // Overflow, return max value
            }
        }
        // If both left and right are negative
        else if (left < 0 && right < 0) {
            if (left < Integer.MAX_VALUE / right) {
                return Integer.MAX_VALUE; // Overflow (in negative multiplication), return max value
            }
        }
        // If left is negative and right is positive
        else if (left < 0 && right > 0) {
            if (left < Integer.MIN_VALUE / right) {
                return Integer.MIN_VALUE; // Underflow, return min value
            }
        }
        // If left is positive and right is negative
        else if (left > 0 && right < 0) {
            if (right < Integer.MIN_VALUE / left) {
                return Integer.MIN_VALUE; // Underflow, return min value
            }
        }

        //If no overflow or underflow, perform the multiplication
        return left * right;
    }

    //casting int here will handle the saturation
    public static int powerSaturated(int left, int right) {
        return (int) Math.pow(left, right);
    }
}
