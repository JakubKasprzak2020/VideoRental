package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.authentication.*;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.domain.UserType;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CreateUser {

    private final UserRepository userRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(UserSignInData userSignInData) {
        User user = User.builder()
                .name(userSignInData.getName())
                .lastName(userSignInData.getLastName())
                .email(userSignInData.getEmail())
                .address(userSignInData.getAddress())
                .userType(UserType.REGULAR)
                .build();
        userRepository.save(user);
        createApplicationUser(userSignInData);
        return user;
    }

    private ApplicationUser createApplicationUser(UserSignInData userSignInData) {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .password(passwordEncoder.encode(userSignInData.getPassword()))
                .username(userSignInData.getEmail())
                .roles(Arrays.asList(ApplicationUserRole.USER.getName()))
                .build();
        applicationUserRepository.save(applicationUser);
        return applicationUser;
    }


}
