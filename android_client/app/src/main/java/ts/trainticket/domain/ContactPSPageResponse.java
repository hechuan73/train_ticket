package ts.trainticket.domain;

import java.util.List;


public class ContactPSPageResponse {
    private boolean status;
    private String msg;
    private int currentPageNum;
    private int pageSize;
    private int totalPageNum;
    private int totalCount;
    private List<ContactPathStation> cntactPathStation;

    public ContactPSPageResponse() {
    }

    public ContactPSPageResponse(boolean status, String msg, List<ContactPathStation> cntactPathStation) {
        this.status = status;
        this.msg = msg;
        this.cntactPathStation = cntactPathStation;
    }

    public ContactPSPageResponse(boolean status, String msg, int currentPageNum, int pageSize, int totalPageNum, int totalCount, List<ContactPathStation> cntactPathStation) {
        this.status = status;
        this.msg = msg;
        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;
        this.totalPageNum = totalPageNum;
        this.totalCount = totalCount;
        this.cntactPathStation = cntactPathStation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<ContactPathStation> getCntactPathStation() {
        return cntactPathStation;
    }

    public void setCntactPathStation(List<ContactPathStation> cntactPathStation) {
        this.cntactPathStation = cntactPathStation;
    }
}
