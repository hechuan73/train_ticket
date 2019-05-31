package user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/***
 *  user-service register user , then send to auth-service
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    private String userId;
    private String userName;
    private String password;

}
