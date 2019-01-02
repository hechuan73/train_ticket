package ts.trainticket.utils;

public class ServerConstValues {


    /**
     *  passenger type
     */

    public static final String[] EASY_PASSENGER_TYPES = {
            "Adult",
            "Student",
            "Children",
            "The remnant Army"
    };




    public static final String[]  SEAT_TYPES = {
            "1st-class",
            "2rd-class",
    };


    public static final int getSeatType(String seatType) {
        seatType = seatType.trim();
        if ("1st-class".equals(seatType))
            return 2;
        if ("2rd-class".equals(seatType))
            return 3;
        return 0;
    }

}
