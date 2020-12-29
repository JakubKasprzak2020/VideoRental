package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteUser {


    private final UserRepository userRepository;
    private final GetUserFromCatalog getUserFromCatalog;

    public void deleteById(long id) {
        try {
            getUserFromCatalog.getById(id);
            userRepository.deleteById(id);
        } catch (UserDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }




}
