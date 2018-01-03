package adminbasic.domin.bean;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */

public class TrainType {

    private String id;      //车型ID，每个车型一个ID，比如某个型号的动车

    private int economyClass;   //普通座的座位数量

    private int confortClass;   //商务座的座位数量

    private int averageSpeed;

    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public TrainType(){
        //Default Constructor
    }

    public TrainType(String id, int economyClass, int confortClass) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
    }

    public TrainType(String id, int economyClass, int confortClass, int averageSpeed) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
        this.averageSpeed = averageSpeed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(int confortClass) {
        this.confortClass = confortClass;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
