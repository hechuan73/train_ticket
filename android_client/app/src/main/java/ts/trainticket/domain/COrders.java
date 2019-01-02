package ts.trainticket.domain;


public class COrders {

    private String orderId;
    private String userName;
    private String passengerName;
    private String pasIdCard;
    private String pathName;
    private String pathStartDate;
    private String pathArriveDate;
    private String buyTime;
    private String takeTime;
    private String arriveTime;
    private String startStationName;
    private String startNum;

    private String arriveStationName;
    private String arriveNum;

    private String pasType;
    private String seatType;
    private String ticketPrice;
    private String status;

    public COrders() {
    }

    public COrders(String userName, String passengerName, String pasIdCard, String pathName, String pathStartDate, String pathArriveDate, String buyTime, String takeTime, String arriveTime, String startStationName, String arriveStationName, String pasType, String seatType, String ticketPrice, String status) {
        this.userName = userName;
        this.passengerName = passengerName;
        this.pasIdCard = pasIdCard;
        this.pathName = pathName;
        this.pathStartDate = pathStartDate;
        this.pathArriveDate = pathArriveDate;
        this.buyTime = buyTime;
        this.takeTime = takeTime;
        this.arriveTime = arriveTime;
        this.startStationName = startStationName;
        this.arriveStationName = arriveStationName;
        this.pasType = pasType;
        this.seatType = seatType;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public COrders(String orderId, String userName, String passengerName, String pasIdCard, String pathName, String pathStartDate, String pathArriveDate, String buyTime, String takeTime, String arriveTime, String startStationName, String startNum, String arriveStationName, String arriveNum, String pasType, String seatType, String ticketPrice, String status) {
        this.orderId = orderId;
        this.userName = userName;
        this.passengerName = passengerName;
        this.pasIdCard = pasIdCard;
        this.pathName = pathName;
        this.pathStartDate = pathStartDate;
        this.pathArriveDate = pathArriveDate;
        this.buyTime = buyTime;
        this.takeTime = takeTime;
        this.arriveTime = arriveTime;
        this.startStationName = startStationName;
        this.startNum = startNum;
        this.arriveStationName = arriveStationName;
        this.arriveNum = arriveNum;
        this.pasType = pasType;
        this.seatType = seatType;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getArriveNum() {
        return arriveNum;
    }

    public void setArriveNum(String arriveNum) {
        this.arriveNum = arriveNum;
    }

    public String getPathArriveDate() {
        return pathArriveDate;
    }

    public void setPathArriveDate(String pathArriveDate) {
        this.pathArriveDate = pathArriveDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPasIdCard() {
        return pasIdCard;
    }

    public void setPasIdCard(String pasIdCard) {
        this.pasIdCard = pasIdCard;
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

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getArriveStationName() {
        return arriveStationName;
    }

    public void setArriveStationName(String arriveStationName) {
        this.arriveStationName = arriveStationName;
    }

    public String getPasType() {
        return pasType;
    }

    public void setPasType(String pasType) {
        this.pasType = pasType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
