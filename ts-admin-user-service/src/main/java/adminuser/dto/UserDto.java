package adminuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userName;

    private String password;

    private int gender;

    private int documentType;

    private String documentNum;

    private String email;
}
