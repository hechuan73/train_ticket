package ts.trainticket.domain;

import java.util.Date;

public class OrderQueryInfo {
    private Date travelDateStart;

    private Date travelDateEnd;

    private Date boughtDateStart;

    private Date boughtDateEnd;

    private int state;

    private boolean enableTravelDateQuery;

    private boolean enableBoughtDateQuery;

    private boolean enableStateQuery;


    public OrderQueryInfo(){
        //Default Constructor
    }

    public OrderQueryInfo(Date travelDateStart, Date travelDateEnd, Date boughtDateStart, Date boughtDateEnd, int state, boolean enableTravelDateQuery, boolean enableBoughtDateQuery, boolean enableStateQuery) {
        this.travelDateStart = travelDateStart;
        this.travelDateEnd = travelDateEnd;
        this.boughtDateStart = boughtDateStart;
        this.boughtDateEnd = boughtDateEnd;
        this.state = state;
        this.enableTravelDateQuery = enableTravelDateQuery;
        this.enableBoughtDateQuery = enableBoughtDateQuery;
        this.enableStateQuery = enableStateQuery;
    }

    public Date getTravelDateStart() {
        return travelDateStart;
    }

    public Date getTravelDateEnd() {
        return travelDateEnd;
    }

    public Date getBoughtDateStart() {
        return boughtDateStart;
    }

    public Date getBoughtDateEnd() {
        return boughtDateEnd;
    }

    public int getState() {
        return state;
    }

    public void enableTravelDateQuery(Date startTime, Date endTime){
        enableTravelDateQuery = true;
        travelDateStart = startTime;
        travelDateEnd = endTime;
    }

    public void disableTravelDateQuery(){
        enableTravelDateQuery = false;
        travelDateStart = null;
        travelDateEnd = null;
    }

    public void enableBoughtDateQuery(Date startTime, Date endTime){
        enableBoughtDateQuery = true;
        boughtDateStart = startTime;
        boughtDateEnd = endTime;
    }

    public void disableBoughtDateQuery(){
        enableBoughtDateQuery = false;
        boughtDateStart = null;
        boughtDateEnd = null;
    }

    public void enableStateQuery(int targetStatus){
        enableStateQuery = true;
        state = targetStatus;
    }

    public void disableStateQuery(){
        enableTravelDateQuery = false;
        state = -1;
    }

    public boolean isEnableTravelDateQuery() {
        return enableTravelDateQuery;
    }

    public boolean isEnableBoughtDateQuery() {
        return enableBoughtDateQuery;
    }

    public boolean isEnableStateQuery() {
        return enableStateQuery;
    }

}
