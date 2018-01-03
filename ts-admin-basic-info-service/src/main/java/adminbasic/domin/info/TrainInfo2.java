package adminbasic.domin.info;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public class TrainInfo2 {

    private String id;      //车型ID，每个车型一个ID，比如某个型号的动车

    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }



    public TrainInfo2(){
        //Default Constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
