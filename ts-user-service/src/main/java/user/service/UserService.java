package user.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import user.dto.UserDto;
import user.entity.User;

import java.util.UUID;


public interface UserService {
    Response saveUser(UserDto user, HttpHeaders headers);

    Response getAllUsers(HttpHeaders headers);

    User findByUserName(String userName, HttpHeaders headers);

    Response deleteUser(UUID userId, HttpHeaders headers);

    Response updateUser(UserDto user, HttpHeaders headers);
}
