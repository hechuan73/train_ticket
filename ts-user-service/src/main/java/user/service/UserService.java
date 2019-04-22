package user.service;

import user.dto.UserDto;
import user.entity.User;

import java.util.List;


public interface UserService {
    User saveUser(UserDto user);

    List<User> getAllUsers();

    User findByUserName(String userName);
}
