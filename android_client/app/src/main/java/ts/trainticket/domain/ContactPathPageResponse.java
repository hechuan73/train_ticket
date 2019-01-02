package ts.trainticket.domain;

import java.util.List;


public class ContactPathPageResponse {
    private boolean status;
    private String msg;
    private int currentPageNum;
    private int pageSize;
    private int totalPageNum;
    private int totalCount;
    private List<ContactPath> contactPathList;

    public ContactPathPageResponse() {
    }

    public ContactPathPageResponse(boolean status, String msg, List<ContactPath> contactPathList) {
        this.status = status;
        this.msg = msg;
        this.contactPathList = contactPathList;
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

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ContactPath> getContactPathList() {
        return contactPathList;
    }

    public void setContactPathList(List<ContactPath> contactPathList) {
        this.contactPathList = contactPathList;
    }
}
