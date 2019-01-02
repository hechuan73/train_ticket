package ts.trainticket.domain;

import java.util.List;


public class COrdersPageResponse {
    private boolean status;
    private String msg;
    private int currentPageNum;
    private int pageSize;
    private int totalPageNum;
    private int totalCount;
    private List<COrders> cOrderses;

    public COrdersPageResponse() {
    }

    public COrdersPageResponse(boolean status, String msg, List<COrders> cOrderses) {
        this.status = status;
        this.msg = msg;
        this.cOrderses = cOrderses;
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

    public List<COrders> getcOrderses() {
        return cOrderses;
    }

    public void setcOrderses(List<COrders> cOrderses) {
        this.cOrderses = cOrderses;
    }
}
