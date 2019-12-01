package travelplan.entity;

import java.util.List;

/**
 * @author fdse
 */
public class TransferTravelResult {

    private List<TripResponse> firstSectionResult;

    private List<TripResponse> secondSectionResult;

    public TransferTravelResult() {
        //Default Constructor
    }

    public TransferTravelResult(List<TripResponse> firstSectionResult, List<TripResponse> secondSectionResult) {

        this.firstSectionResult = firstSectionResult;
        this.secondSectionResult = secondSectionResult;
    }

    public List<TripResponse> getFirstSectionResult() {
        return firstSectionResult;
    }

    public void setFirstSectionResult(List<TripResponse> firstSectionResult) {
        this.firstSectionResult = firstSectionResult;
    }

    public List<TripResponse> getSecondSectionResult() {
        return secondSectionResult;
    }

    public void setSecondSectionResult(List<TripResponse> secondSectionResult) {
        this.secondSectionResult = secondSectionResult;
    }
}
