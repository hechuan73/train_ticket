package auth.security;

import auth.constant.InfoConstant;
import auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("userDetailServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("UsernamePasswordAuthenticationToken  username :" + s);
        UserDetails userDetails = userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format(InfoConstant.USER_NAME_NOT_FOUND_1, s)
                ));
        return userDetails;
    }
}
