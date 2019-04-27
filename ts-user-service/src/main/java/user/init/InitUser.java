package user.init;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.UserService;


import java.util.UUID;

@Component
public class InitUser implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @Override
    public void run(String... strings) throws Exception {
        User whetherExistUser = userRepository.findByUserName("fdse_microservices");

        UserDto user = UserDto.builder()
                .userName("fdse_microservices")
                .password("111111")
                .gender(1)
                .documentType(1)
                .documentNum("2135488099312X")
                .email("fdse_microservices@163.com").build();
        if (whetherExistUser == null) {
            userService.saveUser(user, new HttpHeaders());
        }
    }
}
