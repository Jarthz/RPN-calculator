

import java.util.ArrayList;
import java.util.List;

public class NegativeConverter {

    public static String[] negativeConvert(String[] input){
        List<String> converted = new ArrayList<>();

        for(int i = 0; i< input.length; i++){
            String token = input[i];

            //convert back to having negative numbers
            if(i==0 && token.equals("-") && input.length > 1 && input[i+1].matches("\\d+")){
                String buffer = String.join("",token,input[i+1]);
                converted.add(buffer);
                i+=1;
            } else if(i>0 && token.equals("-") && input[i+1].matches("\\d+") && Operator.isOperator(input[i-1])){
                String buffer = String.join("", token, input[i+1]);
                converted.add(buffer);
                i+=1;
            } else{
                converted.add(token);
            }

        }

        return converted.toArray(new String[0]);
    }

    //convert infix to post fix
    public static String[] infixConvert(String[] input){
            List<String> infixArray = new ArrayList<>();


            for(int i = 0; i< input.length; i++){
                String token = input[i];

                //always push operand on straight away
                if(token.matches("-?\\d")){
                    infixArray.add(token);

                //if operatror, push trailing operand first
                } else if (Operator.isOperator(token) && i < input.length){
                    infixArray.add(input[i+1]);
                    infixArray.add(token);
                    i++;

                    //if = not and following operator, switch
                } else if(i > 0 && token.equals("=") && Operator.isOperator(input[i-1])){
                    infixArray.add(input[i-1]);
                    infixArray.add(token);
                    i++;
                }
            }

            return infixArray.toArray(infixArray.toArray(new String[0]));
    }

}
