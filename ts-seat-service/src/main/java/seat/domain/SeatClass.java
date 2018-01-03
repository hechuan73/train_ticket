package seat.domain;

public enum SeatClass {

    NONE        (0,"无座"),
    BUSINESS    (1,"商务座"),
    FIRSTCLASS  (2,"一等座"),
    SECONDCLASS (3,"二等座"),
    HARDSEAT    (4,"硬座"),
    SOFTSEAT    (5,"软座"),
    HARDBED     (6,"硬卧"),
    SOFTBED     (7,"软卧"),
    HIGHSOFTBED (8,"高级软卧");

    private int code;
    private String name;

    SeatClass(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code){
        SeatClass[] seatClassSet = SeatClass.values();
        for(SeatClass seatClass : seatClassSet){
            if(seatClass.getCode() == code){
                return seatClass.getName();
            }
        }
        return seatClassSet[0].getName();
    }
}
