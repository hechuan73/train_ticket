package travelplan.entity;

import java.util.ArrayList;

public class TransferTravelResult {

    private ArrayList<TripResponse> firstSectionResult;

    private ArrayList<TripResponse> secondSectionResult;

    public TransferTravelResult() {
        //Default Constructor
    }

    public TransferTravelResult(ArrayList<TripResponse> firstSectionResult, ArrayList<TripResponse> secondSectionResult) {

        this.firstSectionResult = firstSectionResult;
        this.secondSectionResult = secondSectionResult;
    }

    public ArrayList<TripResponse> getFirstSectionResult() {
        return firstSectionResult;
    }

    public void setFirstSectionResult(ArrayList<TripResponse> firstSectionResult) {
        this.firstSectionResult = firstSectionResult;
    }

    public ArrayList<TripResponse> getSecondSectionResult() {
        return secondSectionResult;
    }

    public void setSecondSectionResult(ArrayList<TripResponse> secondSectionResult) {
        this.secondSectionResult = secondSectionResult;
    }
}
