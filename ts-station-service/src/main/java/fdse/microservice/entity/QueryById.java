package fdse.microservice.entity;

public class QueryById {

    private String stationId;

    public QueryById() {
        //Default Constructor
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
