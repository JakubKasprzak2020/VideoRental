package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;
import pl.VideoRental.useCase.exception.UserDoesNotExist;

@Component
@RequiredArgsConstructor
public class GetUserFromCatalog {

    private final UserRepository userRepository;

    public User getById(long id) throws UserDoesNotExist {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExist(id));
    }

}
