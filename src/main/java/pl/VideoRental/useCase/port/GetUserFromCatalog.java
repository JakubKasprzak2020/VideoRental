package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

@Component
@RequiredArgsConstructor
public class GetUserFromCatalog {

    private final UserRepository userRepository;

    public User getById(long id) throws UserDoesNotExistException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));
    }

}
