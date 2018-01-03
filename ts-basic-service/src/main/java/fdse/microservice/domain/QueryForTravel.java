package fdse.microservice.domain;

import java.util.Date;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public class QueryForTravel {

    private Trip trip;
    private String startingPlace;
    private String endPlace;
    private Date departureTime;

    public QueryForTravel(){}

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
