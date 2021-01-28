package pl.VideoRental.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser userFromDb = applicationUserRepository.findByUsername(username);

        List<String> roleList = userFromDb.getRoles();

        return org.springframework.security.core.userdetails.User.builder()
                .username(userFromDb.getUsername())
                .password(userFromDb.getPassword())
                .roles(roleList.toArray(new String[0]))
                .build();
    }



}

