package travel2.domain;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;

public class GetTrainTypeInformation {
    
    @Valid
    @Id
    private String id;      //车型ID，每个车型一个ID，比如某个型号的动车

    public GetTrainTypeInformation(){
        //Default Constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
