package travel.entity;

import java.util.Date;

public class SoldTicket {

    private Date travelDate;

    private String trainNumber;

    public SoldTicket(){
        //Default Constructor
    }

    public SoldTicket(Date travelDate, String trainNumber) {
        this.travelDate = travelDate;
        this.trainNumber = trainNumber;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
}
