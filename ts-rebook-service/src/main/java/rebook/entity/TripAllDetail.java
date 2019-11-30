package rebook.entity;

/**
 * @author fdse
 */
public class TripAllDetail {

    private TripResponse tripResponse;

    private Trip trip;

    public TripAllDetail() {
        //Default Constructor
    }



    public TripResponse getTripResponse() {
        return tripResponse;
    }

    public void setTripResponse(TripResponse tripResponse) {
        this.tripResponse = tripResponse;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
