package fdse.microservice.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Chenjie Xu on 2017/5/22.
 */
public class QueryConfig {

    @Valid
    @NotNull
    private String name;

    public QueryConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
