package config.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public class Information2 {
    @Valid
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
