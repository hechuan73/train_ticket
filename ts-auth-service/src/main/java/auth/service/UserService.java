package auth.service;

import auth.dto.AuthDto;
import auth.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();


    User createDefaultAuthUser(AuthDto dto);

}
