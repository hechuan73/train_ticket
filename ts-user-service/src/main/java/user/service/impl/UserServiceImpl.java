package user.service.impl;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import user.dto.AuthDto;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.UserService;


import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String AUHT_SERVICE_URI = "http://ts-auth-service:12340/api/v1/users";

    @Override
    public Response saveUser(UserDto userDto, HttpHeaders headers) {
        log.info("Save User Name ：" + userDto.getUserName());
        UUID userId = userDto.getUserId();
        if (userDto.getUserId() == null)
            userId = UUID.randomUUID();

        User user = User.builder()
                .userId(userId)
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .documentType(userDto.getDocumentType())
                .documentNum(userDto.getDocumentNum())
                .email(userDto.getEmail()).build();

        // avoid same user name
        User user1 = userRepository.findByUserName(userDto.getUserName());
        if (user1 == null) {
            createDefaultAuthUser(AuthDto.builder().userId(UUID.randomUUID() +"")
                    .userName(user.getUserName())
                    .password(user.getPassword()).build(), headers);

            User userSaveResult = userRepository.save(user);
            log.info("Send authorization message to ts-auth-service....");

            return new Response<>(1, "REGISTER USER SUCCESS", userSaveResult);
        } else {
            return new Response(0, "USER HAS ALREADY EXISTS", null);
        }
    }

    private void createDefaultAuthUser(AuthDto dto, HttpHeaders headers) {
        log.info("CALL TO AUTH");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity  httpEntity = new HttpEntity(dto, headers);
        restTemplate.exchange(AUHT_SERVICE_URI + "/auth",
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }

    @Override
    public Response getAllUsers(HttpHeaders headers) {
        List<User> users = userRepository.findAll();
        if (users != null && users.size() > 0)
            return new Response<>(1, "Success", users);
        return new Response<>(0, "NO User", null);
    }

    @Override
    public Response findByUserName(String userName, HttpHeaders headers) {
        User user = userRepository.findByUserName(userName);
        if(user != null)
            return new Response<>(1, "Find User Success", user);
        return new Response<>(0, "No User", null);
    }

    @Override
    public Response findByUserId(String userId, HttpHeaders headers) {
        User user = userRepository.findByUserId(UUID.fromString(userId));
        if(user != null)
            return new Response<>(1, "Find User Success", user);
        return new Response<>(0, "No User", null);
    }

    @Override
    public Response deleteUser(UUID userId, HttpHeaders headers) {
        log.info("DELETE USER BY ID :" + userId);
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            // first  only admin token can delete success
            deleteUserAuth(userId, headers);
            // second
            userRepository.deleteByUserId(userId);
            log.info("DELETE SUCCESS");
            return new Response<>(1, "DELETE SUCCESS", null);
        } else {
            return new Response<>(0, "USER NOT EXISTS", null);
        }
    }

    @Override
    public Response updateUser(UserDto userDto, HttpHeaders headers) {
        log.info("UPDATE USER :" + userDto.toString());
        User oldUser = userRepository.findByUserName(userDto.getUserName());
        if (oldUser != null) {
            User newUser = oldUser.builder().email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .userName(userDto.getUserName())
                    .gender(userDto.getGender())
                    .documentNum(userDto.getDocumentNum())
                    .documentType(userDto.getDocumentType()).build();
            userRepository.save(newUser);
            return new Response<>(1, "SAVE USER SUCCESS", newUser);
        } else {
            return new Response(0, "USER NOT EXISTS", null);
        }
    }

    public void deleteUserAuth(UUID userId, HttpHeaders headers) {
        log.info("DELETE USER BY ID :" + userId);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Response> httpEntity = new HttpEntity<>(headers);
        restTemplate.exchange(AUHT_SERVICE_URI + "/users/" + userId,
                HttpMethod.DELETE,
                httpEntity,
                Response.class);
        log.info("DELETE USER AUTH SUCCESS");
    }
}
