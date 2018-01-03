package consign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "consign_record")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsignRecord {
    @Id
    private UUID id;
    private UUID accountId;//办理托运的账户ID
    private String handleDate;//办理日期，一般早于order日期
    private String targetDate;//行李托运日期，和order上的日期一致
    private String from;//托运起始站，和order一致
    private String to;//托运终点站，和order一致
    private String consignee;//收件人
    private String phone;//收件人手机号码
    private double weight;//托运重量
    private double price;//价格

    public ConsignRecord(){

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
