package pl.VideoRental.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.VideoRental.security.ApplicationUserRole;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser userFromDb = applicationUserRepository.findByUsername(username);

        List<String> roleList = new ArrayList<>();

        for (ApplicationUserRole role : userFromDb.getRoles()) {
            roleList.add(role.getName());
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(userFromDb.getUsername())
                .password(userFromDb.getPassword())
                .roles(roleList.toArray(new String[0]))
                .build();
    }



}

