package ts.trainticket.domain;


public class Ticket {

    public static final String[] EASY_SEAT_TYPES = {
            "2rd-class",
            "1st-class",
    };


    public static final String[] FULL_SEAT_TYPES = {
            "second-class seat",
            "first-class seat",

    };

    public static final String[] ORDER_STATE = {
            "Not Paid",
            "Paid & Not Collected",
            "Collected",
            "Cancel & Rebook",
            "Canceled",
            "Refunded",
            "Used",
            "other"
    };


    private String pathName;
    private String pathStartDate;
    private int stationNumber;
    private int seatType;
    private int leftTickets;

    public Ticket() {
    }

    public Ticket(String pathName, String pathStartDate, int stationNumber, int seatType, int leftTickets) {
        this.pathName = pathName;
        this.pathStartDate = pathStartDate;
        this.stationNumber = stationNumber;
        this.seatType = seatType;
        this.leftTickets = leftTickets;
    }


    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathStartDate() {
        return pathStartDate;
    }

    public void setPathStartDate(String pathStartDate) {
        this.pathStartDate = pathStartDate;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    public int getLeftTickets() {
        return leftTickets;
    }

    public void setLeftTickets(int leftTickets) {
        this.leftTickets = leftTickets;
    }
}
