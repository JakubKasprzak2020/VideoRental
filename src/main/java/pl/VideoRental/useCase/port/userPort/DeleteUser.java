package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteUser {


    private final UserRepository userRepository;
    private final GetUserFromCatalog getUserFromCatalog;
    private final ApplicationUserRepository applicationUserRepository;

    public void deleteById(long id) {
        try {
           User user = getUserFromCatalog.getById(id);
            userRepository.deleteById(id);
            deleteApplicationUser(user.getEmail());
        } catch (UserDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteApplicationUser(String username){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        applicationUserRepository.delete(applicationUser);
    }


}
