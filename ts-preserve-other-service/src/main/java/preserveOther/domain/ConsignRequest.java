package preserveOther.domain;

import java.util.UUID;

public class ConsignRequest {

    private UUID id;//更新记录使用
    private UUID accountId;//办理托运的账户ID
    private String handleDate;//办理日期，一般早于order日期
    private String targetDate;//行李托运日期，和order上的日期一致
    private String from;//托运起始站，和order一致
    private String to;//托运终点站，和order一致
    private String consignee;//收件人
    private String phone;//收件人手机号码
    private double weight;//托运重量
    private boolean isWithin;//是否在省内

    public ConsignRequest(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isWithin() {
        return isWithin;
    }

    public void setWithin(boolean within) {
        isWithin = within;
    }
}
