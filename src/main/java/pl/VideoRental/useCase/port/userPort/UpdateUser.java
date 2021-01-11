package pl.VideoRental.useCase.port.userPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

@Component
@RequiredArgsConstructor
public class UpdateUser {

    private final UserRepository userRepository;
    private final GetUserFromCatalog getUserFromCatalog;

    public void update(long id, User newUser){
        try {
            User oldUser = getUserFromCatalog.getById(id);
            oldUser.setName(newUser.getName());
            oldUser.setLastName(newUser.getLastName());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setAddress(newUser.getAddress());
            oldUser.setPassword(newUser.getPassword());
            oldUser.setUserType(newUser.getUserType());
            userRepository.save(oldUser);
        } catch (UserDoesNotExistException exception){
            System.out.println(exception.getMessage());
        }
    }



}
