package pl.VideoRental.useCase.port.getAllUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllUsers {

    private final UserRepository userRepository;

    public List<User> getAll(){
        List <User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }


}
