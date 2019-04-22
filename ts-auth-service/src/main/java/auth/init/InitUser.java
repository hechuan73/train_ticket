package auth.init;

import auth.entity.User;
import auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Component
public class InitUser implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Override
    public void run(String... strings) throws Exception {
        User whetherExistUser = userRepository.findByUsername("user").orElse(new User());
        if (whetherExistUser.getUsername() == null) {
            User user = User.builder()
                    .userId(UUID.randomUUID())
                    .username("user")
                    .password(passwordEncoder.encode("1234"))
                    .roles(new HashSet<>(Arrays.asList("ROLE_USER")))
                    .build();

            userRepository.save(user);
        }

        User whetherExistAdmin = userRepository.findByUsername("user").orElse(new User());
        if (whetherExistAdmin.getUsername() == null) {
            User admin = User.builder()
                    .userId(UUID.randomUUID())
                    .username("admin")
                    .password(passwordEncoder.encode("222222"))
                    .roles(new HashSet<>(Arrays.asList("ROLE_ADMIN")))
                    .build();

            userRepository.save(admin);
        }
    }
}
