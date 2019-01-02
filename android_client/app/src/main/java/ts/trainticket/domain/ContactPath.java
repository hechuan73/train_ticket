package ts.trainticket.domain;


public class ContactPath {
    private String pathName;

    private String pathDate;
    private String pathArriveDate;
    private String totalTime;

    private String startStation;
    private int startNumber;
    private String startTime;

    private String arriveStation;
    private int arriveNumber;
    private String arriveTime;
    private int[] seats = new int[2];
    private double[] prices = new double[2];
    public ContactPath() {
    }

    public ContactPath(String pathName, String startStation, int startNumber, String startTime, String arriveStation, int arriveNumber, String arriveTime) {
        this.pathName = pathName;
        this.startStation = startStation;
        this.startNumber = startNumber;
        this.startTime = startTime;
        this.arriveStation = arriveStation;
        this.arriveNumber = arriveNumber;
        this.arriveTime = arriveTime;
    }

    public ContactPath(String pathName, String pathDate, String pathArriveDate, String totalTime, String startStation, int startNumber, String startTime, String arriveStation, int arriveNumber, String arriveTime, int[] seats, double[] prices) {
        this.pathName = pathName;
        this.pathDate = pathDate;
        this.pathArriveDate = pathArriveDate;
        this.totalTime = totalTime;
        this.startStation = startStation;
        this.startNumber = startNumber;
        this.startTime = startTime;
        this.arriveStation = arriveStation;
        this.arriveNumber = arriveNumber;
        this.arriveTime = arriveTime;
        this.seats = seats;
        this.prices = prices;
    }

    public String getPathArriveDate() {
        return pathArriveDate;
    }

    public void setPathArriveDate(String pathArriveDate) {
        this.pathArriveDate = pathArriveDate;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getArriveNumber() {
        return arriveNumber;
    }

    public void setArriveNumber(int arriveNumber) {
        this.arriveNumber = arriveNumber;
    }

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public int[] getSeats() {
        return seats;
    }

    public void setSeats(int[] seats) {
        this.seats = seats;
    }

    public double[] getPrices() {
        return prices;
    }

    public void setPrices(double[] prices) {
        this.prices = prices;
    }

    public String getPathDate() {
        return pathDate;
    }

    public void setPathDate(String pathDate) {
        this.pathDate = pathDate;
    }
}
