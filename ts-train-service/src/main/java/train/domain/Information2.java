package train.domain;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public class Information2 {
    @Valid
    @Id
    private String id;      //车型ID，每个车型一个ID，比如某个型号的动车

    public Information2(){
        //Default Constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
