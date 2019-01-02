package ts.trainticket.domain;


public class QueryInfo {
    private String tripId;
    private String startingPlace;

    private String endPlace;

    private String departureTime;

    public QueryInfo(){
        //Default Constructor
    }

    public QueryInfo(String tripId, String startingPlace, String endPlace, String departureTime) {
        this.tripId = tripId;
        this.startingPlace = startingPlace;
        this.endPlace = endPlace;
        this.departureTime = departureTime;
    }

    public QueryInfo(String startingPlace, String endPlace , String departureTime ) {
        this.startingPlace = startingPlace;
        this.endPlace = endPlace;
        this.departureTime = departureTime;
    }

    public String getStartingPlace() {
        return startingPlace;
    }

    public void setStartingPlace(String startingPlace) {
        this.startingPlace = startingPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "tripId='" + tripId + '\'' +
                ", startingPlace='" + startingPlace + '\'' +
                ", endPlace='" + endPlace + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
