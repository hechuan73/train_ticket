package admintravel.domain.bean;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public class GetTrainTypeRequest {
    @Valid
    @Id
    private String id;      //车型ID，每个车型一个ID，比如某个型号的动车

    public GetTrainTypeRequest(){
        //Default Constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
