package adminuser.service;

import adminuser.dto.UserDto;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface AdminUserService {
    Response getAllUsers(HttpHeaders headers);

    Response deleteUser(String userId, HttpHeaders headers);

    Response updateUser(UserDto userDto, HttpHeaders headers);

    Response addUser(UserDto userDto, HttpHeaders headers);
}
