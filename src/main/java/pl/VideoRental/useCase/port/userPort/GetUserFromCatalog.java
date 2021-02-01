package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

@Component
@RequiredArgsConstructor
public class GetUserFromCatalog {

    private final UserRepository userRepository;

    public User getById(long id) throws UserDoesNotExistException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));
    }

    //TODO - needs test method
    public User getByEmail(String email) throws UserDoesNotExistException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserDoesNotExistException(email));
    }

    //TODO - probably this method is redundant
    public User getByApplicationUser(ApplicationUser applicationUser) {
        String email = applicationUser.getUsername();
        return userRepository.findByEmail(email).orElse(null);
    }

}
