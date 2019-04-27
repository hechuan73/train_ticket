package auth.service;

import auth.dto.AuthDto;
import auth.entity.User;
import edu.fudan.common.util.Response;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User createDefaultAuthUser(AuthDto dto);

    Response deleteByUserId(UUID userId);

}
