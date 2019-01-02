package ts.trainticket.domain;

import java.util.Date;


public class TripResponse {

    // private TripId tripId;
    private String tripId;

    private String trainTypeId;

    private String startingStation;

    private String terminalStation;

    private String startingTime;

    private String endTime;

    private String economyClass;

    private String confortClass;

    private String priceForEconomyClass;

    private String priceForConfortClass;

    public TripResponse() {
        //Default Constructor
    }

    public TripResponse(String tripId, String trainTypeId, String startingStation, String terminalStation, String startingTime, String endTime, String economyClass, String confortClass, String priceForEconomyClass, String priceForConfortClass) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.startingStation = startingStation;
        this.terminalStation = terminalStation;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
        this.priceForEconomyClass = priceForEconomyClass;
        this.priceForConfortClass = priceForConfortClass;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getTerminalStation() {
        return terminalStation;
    }

    public void setTerminalStation(String terminalStation) {
        this.terminalStation = terminalStation;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(String economyClass) {
        this.economyClass = economyClass;
    }

    public String getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(String confortClass) {
        this.confortClass = confortClass;
    }

    public String getPriceForEconomyClass() {
        return priceForEconomyClass;
    }

    public void setPriceForEconomyClass(String priceForEconomyClass) {
        this.priceForEconomyClass = priceForEconomyClass;
    }

    public String getPriceForConfortClass() {
        return priceForConfortClass;
    }

    public void setPriceForConfortClass(String priceForConfortClass) {
        this.priceForConfortClass = priceForConfortClass;
    }
}
