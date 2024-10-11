//need a list  to hold all the values the SRPN has when user input r
//needs to be a list rather than another stack because it loops around at the end


import java.util.List;

public class RValueList {

//private static variables of the hard coded r string values and an index

    //global variable of the list
    private static final List<String> hardValueList = List.of(
        "1804289383", "846930886", "1681692777", "1714636915", "1957747793", "424238335", "719885386", "1649760492", "596516649", "1189641421", "1025202362", "1350490027", "783368690", "1102520059", "2044897763", "1967513926", "1365180540", "1540383426", "304089172", "1303455736", "35005211", "521595368", "1804289383"
        );

    //global variable for the class
    private static int currentIndex = 0;

    //private constructor. This is immutable nor modifies other classes
    private RValueList(){

    }

//public return method of the current element in the list to be pushed intot he stack elsewhere
    public static String getNextValue(){
        String value = hardValueList.get(currentIndex);

        //this is so we loop around the list again from the beginning once we reach the last element
        currentIndex = (currentIndex + 1) % hardValueList.size();
        return value;
    }


}
