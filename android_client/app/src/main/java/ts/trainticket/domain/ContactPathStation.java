package ts.trainticket.domain;


public class ContactPathStation {
    private String stationNumber;
    private String pathName;
    private String stationName;
    private String arriveTime;
    private String startTime;

    private String beginStation;
    private String endStation;

    public ContactPathStation() {
    }

    public ContactPathStation(String stationNumber, String pathName, String stationName, String arriveTime, String startTime, String beginStation, String endStation) {
        this.stationNumber = stationNumber;
        this.pathName = pathName;
        this.stationName = stationName;
        this.arriveTime = arriveTime;
        this.startTime = startTime;
        this.beginStation = beginStation;
        this.endStation = endStation;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getBeginStation() {
        return beginStation;
    }

    public void setBeginStation(String beginStation) {
        this.beginStation = beginStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
