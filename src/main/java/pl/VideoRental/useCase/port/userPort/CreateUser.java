package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.domain.UserType;
import pl.VideoRental.security.ApplicationUserRole;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateUser {

    private final UserRepository userRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public User create(UserSignInData userSignInData) {
        User user = User.builder()
                .name(userSignInData.getName())
                .lastName(userSignInData.getLastName())
                .password(userSignInData.getPassword())
                .email(userSignInData.getEmail())
                .address(userSignInData.getAddress())
                .userType(UserType.REGULAR)
                .build();
        userRepository.save(user);
        return user;
    }

    //TODO - it's not used yet, password should be encrypt - actualy a User also should heve encrypted password
    public ApplicationUser createApplicationUser(User user) {
        Set<ApplicationUserRole> roles = Collections.singleton(ApplicationUserRole.USER);
        ApplicationUser applicationUser = ApplicationUser.builder()
                .password(user.getPassword())
                .username(user.getEmail())
                .roles(roles)
                .build();
        applicationUserRepository.save(applicationUser);
        return applicationUser;
    }


}
